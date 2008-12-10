package com.sgta07.bizalokud.gunea.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.layout.AccordionLayout;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.CardLayout;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.RowLayout;
import com.sgta07.bizalokud.login.client.Logeable;
import com.sgta07.bizalokud.login.client.Login;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gunea implements EntryPoint, Logeable {

	private GuneInfo gunea;

	private String erabNan;
	private String erabIzena;
	private String erabAbizen;
	private boolean erabAdminDa;

	private Mapa mapa;
	private Label orduLabel;
	private Label erabDatuak;
	private Label guneIzenDatuak;
	private Label guneHelbideDatuak;

	public void onModuleLoad() {
		mapa = new Mapa();
		mapa.setSize(500, 400);

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
//		erabDatuak.setStyle("text-align: right;");
		guneIzenDatuak = new Label();
//		guneIzenDatuak.setStyle("text-align: right;");
		guneHelbideDatuak = new Label();
//		guneHelbideDatuak.setStyle("text-align: right;");
		
		orduLabel = new Label();
//		orduLabel.setStyle("text-align: right;");
		Timer timer = new Timer() {
			public void run() {
				DateTimeFormat dtf = new DateTimeFormat("HH:mm") {
				};
				String ordua = dtf.format(new Date());
				orduLabel.setText(ordua);
			}
		};

		timer.scheduleRepeating(1000);

		// Iparraldeko panela: Aplikazioaren titulua, gunea eta kautotutako
		// erabiltzailearen informazioa
		Image banner = new Image("images/banner.png");

		Panel datuak = new Panel();
		datuak.setTitle("Informazioa");
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

		// Egoaldeko panela: Azken minutuko informazioak jasotzeko
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

		final AccordionLayout accordion = new AccordionLayout(true);

		Panel westPanel = new Panel();
		westPanel.setTitle("Menu Nagusia");
		westPanel.setCollapsible(true);
		westPanel.setWidth(200);
		westPanel.setLayout(accordion);

		Panel navPanel = new Panel();
		navPanel.setHtml("<p>Hi. I'm the west panel.</p>");
		navPanel.setTitle("Ekintzak");
		navPanel.setBorder(false);
		navPanel.setIconCls("folder-icon");
		westPanel.add(navPanel);

		Panel settingsPanel = new Panel();
		settingsPanel.setHtml("<p>Some settings in here.</p>");
		settingsPanel.setTitle("Hobespenak");
		settingsPanel.setBorder(false);
		settingsPanel.setIconCls("settings-icon");
		westPanel.add(settingsPanel);

		BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);
		westData.setSplit(true);
		westData.setMinSize(175);
		westData.setMaxSize(400);
		westData.setMargins(new Margins(0, 5, 0, 0));

		borderPanel.add(westPanel, westData);

		final Panel centerPanel = new Panel();
		centerPanel.setLayout(new CardLayout());

		// final TabPanel centerPanel = new TabPanel();
		// centerPanel.setDeferredRender(false);
		// centerPanel.setActiveTab(0);

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
		centerPanelTwo.add(mapa.getMapPanel());
		centerPanelTwo.add(new Button("Alokatu", new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				Alokatu alokatu = new Alokatu();
				alokatu.setTitle("Alokatu");
				centerPanel.add(alokatu);
				// centerPanel.activate(2);
				centerPanel.setActiveItem(0);
			}
		}));
		centerPanelTwo.add(new Button("Abisuak", new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				Abisuak abisuak = new Abisuak("09760589X");

				abisuak.setTitle("Zure Abisuak");
				centerPanel.add(abisuak);
				// centerPanel.setActiveTab(3);
				centerPanel.setActiveItem(0);
			}
		}));

		Alokatu alokatu = new Alokatu();
		alokatu.setTitle("Alokatu");
		centerPanel.add(alokatu);
		// centerPanel.activate(2);
		centerPanel.setActiveItem(0);

		// centerPanel.add(centerPanelTwo);
		// centerPanel.add(centerPanelOne);

		BorderLayoutData centerData = new BorderLayoutData(
				RegionPosition.CENTER);
		// centerData.setSplit(true);
		centerData.setMargins(new Margins(0, 0, 5, 0));

		// borderPanel.add(centerPanel,
		// new BorderLayoutData(RegionPosition.CENTER));

		borderPanel.add(centerPanel, centerData);

		panelNagusia.add(borderPanel);

		new Viewport(panelNagusia);
	}

	protected void setGunea(GuneInfo gunea) {
		this.gunea = gunea;
		if (gunea != null) {
			guneIzenDatuak.setText(gunea.getIzena() + " gunean zaude");
			guneHelbideDatuak.setText("\n" + gunea.getHelbidea());
			if (!gunea.hasLatLon())
				mapa.updateMap(gunea.getIzena(), gunea.getHelbidea(),
						JavaScriptObjectHelper.createObject(), mapa);
			else {
				mapa.markaGehitu(gunea);
				mapa.finkatu(gunea);
			}
		}
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
		erabDatuak.setText("Kaixo " + izena + " " + abizenak);
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

	public Label getErabDatuak() {
		return erabDatuak;
	}

}
