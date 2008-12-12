package com.sgta07.bizalokud.gunea.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.gwtext.client.core.EventCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.Node;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.layout.AccordionLayout;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.CardLayout;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.RowLayout;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.event.TreeNodeListenerAdapter;
import com.sgta07.bizalokud.login.client.Logeable;
import com.sgta07.bizalokud.login.client.Login;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gunea implements EntryPoint, Logeable {

	// Zenbat segundu egon behar da aktibitaterik gabe sistematik irtetzeko
	private final static int IRTETEKO_DENBORA = 30;
	// Datuak zenbat segunduro eguneratuko diren
	private final static int DATU_EGUNERAKETA_DENBORA = 40;

	private GuneInfo gunea;

	private boolean isGuneaIdentif;
	private boolean isErabIdentif;
	
	private String erabNan;
	private String erabIzena;
	private String erabAbizen;
	private boolean erabAdminDa;

	// private Mapa mapa;
	private Label orduLabel;
	private Label erabDatuak;
	private Label guneIzenDatuak;
	private Label guneHelbideDatuak;

	// Aktibitaterik gabe egondako denbora
	private int ezAktKont = 0;
	// Eguneratu gabe egondako denbora
	private int eguneraketaKont = 0;
	
	private Alokatu alokatu;
	private Abisuak abisuak;

	// Menua hasieratzeko datuak
	private static Store store;
	private static MemoryProxy proxy;
	private static ArrayReader reader;
	
	//Panel nagusien aldagaiak
	private Panel centerPanel;
	private CardLayout cardLayout;

	public void onModuleLoad() {
		// mapa = new Mapa();
		// mapa.setSize(500, 400);
		
		sortuPanelak();

		GuneaService.Util.getInstance().getMyInfo(
				new AsyncCallback<GuneInfo>() {

					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						setGunea(null);
					}

					public void onSuccess(GuneInfo result) {
						try {
							setGunea(result);
						} catch (Exception e) {
							e.printStackTrace();
							setGunea(null);
						}

					}
				});

		Login login = new Login();
		login.erakutsi(this);

		Panel panelNagusia = new Panel();
		panelNagusia.setBorder(false);
		panelNagusia.setId("panelNagusia");
		// Inguruarekiko uzten duen margina
		panelNagusia.setPaddings(0);
		panelNagusia.setLayout(new FitLayout());
		panelNagusia.setWidth("100%");
		panelNagusia.setHeight("100%");

		Panel borderPanel = new Panel();
		borderPanel.setLayout(new BorderLayout());

		erabDatuak = new Label();
		// erabDatuak.setStyle("text-align: right;");
		guneIzenDatuak = new Label();
		// guneIzenDatuak.setStyle("text-align: right;");
		guneHelbideDatuak = new Label();
		// guneHelbideDatuak.setStyle("text-align: right;");

		orduLabel = new Label();
		// orduLabel.setStyle("text-align: right;");

		denboraHasi();

		// Iparraldeko panela: Aplikazioaren titulua, gunea eta kautotutako
		// erabiltzailearen informazioa
		Image banner = new Image("images/banner.png");

		Panel datuak = new Panel("Informazioa", 200, 100);
		datuak.setBorder(true);
		datuak.setFrame(true);
		datuak.setPaddings(10);
		datuak.setLayout(new RowLayout());
		datuak.add(erabDatuak);
		datuak.add(guneIzenDatuak);
		datuak.add(guneHelbideDatuak);
		datuak.add(orduLabel);
		datuak.setStyle("backgroundColor: #dfe8f6;");
		datuak.setBodyStyle("backgroundColor: #dfe8f6;");
		datuak.setStyle("vertical-align: middle;");

		Panel bannerPanel = new Panel();
		bannerPanel.setLayout(new ColumnLayout());
		bannerPanel.setBorder(false);
		bannerPanel.setCollapsible(false);
		bannerPanel.add(banner);
		bannerPanel.add(datuak);
		bannerPanel.setAutoHeight(true);
		bannerPanel.setFrame(false);
		bannerPanel.setStyle("backgroundColor: #dfe8f6;");
		bannerPanel.setBodyStyle("backgroundColor: #dfe8f6;");
		bannerPanel.setStyle("vertical-align: middle;");

		borderPanel
				.add(bannerPanel, new BorderLayoutData(RegionPosition.NORTH));

		// Hegoaldeko panela: Azken minutuko informazioak jasotzeko
		Panel southPanel = new HTMLPanel(
				"<br>"
						+ "<li>Amarako gunea bihartik aurrera itxita egongo da, konponketak egiteko!</li> "
						+ "<li>Groseko obrak direla eta, Sagues-Bulevard ibilbidea ez hartzea gomendatzen da</li>");
		southPanel.setHeight(100);
		southPanel.setCollapsible(false);
		southPanel.setTitle("Azken orduko informazioa");

		BorderLayoutData southData = new BorderLayoutData(RegionPosition.SOUTH);
		southData.setMinSize(100);
		southData.setMaxSize(200);
		southData.setMargins(new Margins(0, 0, 0, 0));
		southData.setSplit(true);
		borderPanel.add(southPanel, southData);

		BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);
		westData.setSplit(true);
		westData.setMinSize(175);
		westData.setMaxSize(400);
		westData.setMargins(new Margins(0, 5, 0, 0));

//		borderPanel.add(westPanel, westData);
		borderPanel.add(getMenuPanel(),westData);

		centerPanel = new Panel();
		cardLayout = new CardLayout();
		centerPanel.setLayout(cardLayout);

//		 final TabPanel centerPanel = new TabPanel();
//		 centerPanel.setDeferredRender(false);
//		 centerPanel.setActiveTab(0);

		Panel centerPanelOne = new HTMLPanel();
		centerPanelOne
				.setHtml("<br><br><p>Azkeneko bi ibilaldietan bizikleta egoera onean entregatu ez\n duzunez datorren astean ezingo duzu bizikletarik alokatu.</p>");
		centerPanelOne.setTitle("Abisua");
		centerPanelOne.setAutoScroll(true);
		centerPanelOne.setClosable(true);

		Panel centerPanelTwo = new HTMLPanel();
		centerPanelTwo
				.setHtml("<br><br><h1>Ongietorria Bizalokud sistemara!</h1>\n<br><p>Aukera ezazu menuko ekintza bat aplikazioa erabiltzen hasteko.");
		centerPanelTwo.setTitle("Ekintzak");
		centerPanelTwo.setAutoScroll(true);

		BorderLayoutData centerData = new BorderLayoutData(
				RegionPosition.CENTER);
		// centerData.setSplit(true);
		centerData.setMargins(new Margins(0, 0, 5, 0));
		borderPanel.add(centerPanel, centerData);

		panelNagusia.add(borderPanel);
		
		new Viewport(panelNagusia);
		
		centerPanel.add(abisuak);
		centerPanel.add(alokatu);

		

		// Aktibitatea dagoen edo ez detektatzen du. Aktibitaterik ez badago
		// loginetik irtengo da.
		panelNagusia.getEl().addListener("keydown", new EventCallback() {
			public void execute(EventObject e) {
				resetIdleTimer();
			}
		});

		panelNagusia.getEl().addListener("click", new EventCallback() {
			public void execute(EventObject e) {
				resetIdleTimer();
			}
		});
	}

	private void sortuPanelak() {
		abisuak = new Abisuak(this);
		abisuak.setId("abisuak-panel");
		alokatu = new Alokatu(this);
		alokatu.setId("alokatu-panel");
	}

	private void resetIdleTimer() {
		ezAktKont = 0;
	}

	private void denboraHasi() {

		Timer timer = new Timer() {
			public void run() {
				DateTimeFormat dtf = new DateTimeFormat("HH:mm") {
				};
				String ordua = dtf.format(new Date());
				orduLabel.setText(ordua);
				ezAktKont++;
				eguneraketaKont++;
				if (ezAktKont == (Gunea.IRTETEKO_DENBORA - 10)) {
					// TODO: Mezu kutxa erakutsi abisatzeko
				} else if (ezAktKont == Gunea.IRTETEKO_DENBORA) {
					// TODO: Deslogeatu
				}
				if (eguneraketaKont == Gunea.DATU_EGUNERAKETA_DENBORA) {
					// TODO: Datuak eguneratu
				}
			}
		};
		timer.scheduleRepeating(1000);
	}

	private void setGunea(GuneInfo gunea) {
		this.gunea = gunea;
		if (gunea != null) {
			this.isGuneaIdentif = true;
			// guneIzenDatuak.setText(gunea.getIzena() + " gunean zaude");
			// guneHelbideDatuak.setText(gunea.getHelbidea());
			guneIzenDatuak.setHtml("<b>" + gunea.getIzena()
					+ "</b> gunean zaude<br>");
			guneHelbideDatuak.setHtml("<b>" + gunea.getHelbidea() + "</b><br>");
			// if (!gunea.hasLatLon())
			// mapa.updateMap(gunea.getIzena(), gunea.getHelbidea(),
			// JavaScriptObjectHelper.createObject(), mapa);
			// else {
			// mapa.markaGehitu(gunea);
			// mapa.finkatu(gunea);
			// }
			
			eguneratuGuneDatuak();
		}
	}

	private void eguneratuGuneDatuak() {
		alokatu.datuakEguneratu();
	}

	public String getHelbidea() {
		return this.gunea.getHelbidea();
	}

	public void setErabiltzaileDatuak(String nan, String izena,
			String abizenak, boolean adminDa) {
		this.erabNan = nan;
		this.erabIzena = izena;
		this.erabAbizen = abizenak;
		this.erabAdminDa = adminDa;
		// erabDatuak.setText("Kaixo " + izena + " " + abizenak);
		erabDatuak.setHtml("Kaixo <b>" + izena + " " + abizenak + "</b><br>");
		this.isErabIdentif = true;
	}

	public String getErabNan() {
		return erabNan;
	}

	public String getErabIzena() {
		return erabIzena;
	}

	public String getErabAbizen() {
		return erabAbizen;
	}

	public boolean isAdmin() {
		return erabAdminDa;
	}

	public int getGuneId() {
		return gunea.getId();
	}

	public boolean isGuneaIdentif() {
		return isGuneaIdentif;
	}

	public boolean isErabIdentif() {
		return isErabIdentif;
	}
	
	public Panel getMenuPanel() {

		Panel westPanel = new Panel();
		westPanel.setLayout(new AccordionLayout(true));
		westPanel.setTitle("Menu Nagusia");
		westPanel.setCollapsible(true);
		westPanel.setWidth(200);

		Store store = getStore();

		Record[] records = store.getRecords();
		for (int i = 0; i < records.length; i++) {
			Record record = records[i];

			String id = record.getAsString("id");
			final String category = record.getAsString("kategoria");
			String title = record.getAsString("izenburua");
			final String iconCls = record.getAsString("iconCls");

			if (category == null) {
				Panel categoryPanel = new Panel();
				categoryPanel.setAutoScroll(true);
				categoryPanel.setLayout(new FitLayout());
				categoryPanel.setId(id + "-acc");
				categoryPanel.setTitle(title);
				categoryPanel.setIconCls(iconCls);
				westPanel.add(categoryPanel);
			} else {
				Panel categoryPanel = (Panel) westPanel.findByID(category
						+ "-acc");
				TreePanel treePanel = (TreePanel) categoryPanel
						.findByID(category + "-acc-tree");
				TreeNode root = null;
				if (treePanel == null) {
					treePanel = new TreePanel();
					treePanel.setAutoScroll(true);
					treePanel.setId(category + "-acc-tree");
					treePanel.setRootVisible(false);
					root = new TreeNode();
					treePanel.setRootNode(root);
					categoryPanel.add(treePanel);
				} else {
					root = treePanel.getRootNode();
				}

				TreeNode node = new TreeNode();
				node.setText(title);
				node.setId(id);
				if (iconCls != null)
					node.setIconCls(iconCls);
				root.appendChild(node);
				addNodeClickListener(node);
			}
		}
		return westPanel;
	}

	public static Store getStore() {
		if (store == null) {
			proxy = new MemoryProxy(getData());

			RecordDef recordDef = new RecordDef(
					new FieldDef[] { new StringFieldDef("id"),
							new StringFieldDef("kategoria"),
							new StringFieldDef("izenburua"),
							new StringFieldDef("iconCls"),
							new StringFieldDef("irudia") });

			reader = new ArrayReader(0, recordDef);
			store = new Store(proxy, reader);
			store.load();
		}
		return store;
	}

	private static Object[][] getData() {
		return new Object[][] {

				new Object[] { "ekintzak-kategoria", null, "Ekintzak","ekintza-kategoria-icon", null },
				new Object[] { "alokatu", "ekintzak-kategoria","Bizikleta Alokatu", null, null },
				new Object[] { "entregatu", "ekintzak-kategoria","Bizikleta Entregatu", null, null },

				new Object[] { "kontua-kategoria", null, "Nire Kontua","kontua-kategoria-icon", null },
				new Object[] { "estatistikak", "kontua-kategoria","Nire Ibilbideak", null, null },
				new Object[] { "pasahitza", "kontua-kategoria","Pasahitza Aldatu", null, null },
				new Object[] { "abisuak", "kontua-kategoria", "Nire Abisuak",null, null },
				new Object[] { "datuak", "kontua-kategoria", "Nire Datuak",null, null } };
	}
	
	private void addNodeClickListener(TreeNode node) {
		node.addListener(new TreeNodeListenerAdapter() {
			public void onClick(Node node, EventObject e) {
				
				if(node.getId().equals("abisuak")){
					cardLayout.setActiveItem("abisuak-panel");
				}
				if(node.getId().equals("alokatu")){
					cardLayout.setActiveItem("alokatu-panel");
				}
				if(node.getId().equals("entregatu")){
					centerPanel.setActiveItemID("entregatu-panel");
				}
				if(node.getId().equals("datuak")){
					centerPanel.setActiveItemID("datuak-panel");
				}
				if(node.getId().equals("pasahitza")){
					centerPanel.setActiveItemID("pasahitza-panel");
				}
			}
		});
	}
}
