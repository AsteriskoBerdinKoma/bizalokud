package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.AccordionLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.sgta07.bizalokud.login.client.Login;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gunea implements EntryPoint {

	private GuneInfo gunea;
	private Login login;

	private Mapa mapa;

	public void onModuleLoad() {		
		mapa = new Mapa(500, 400);

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
		
		login = new Login();
		login.erakutsi();
		

		Panel panelNagusia = new Panel();
		panelNagusia.setBorder(false);
		panelNagusia.setId("panelPagusia");
		// Inguruarekiko uzten duen margina
		panelNagusia.setPaddings(0);
		panelNagusia.setLayout(new FitLayout());
		panelNagusia.setWidth("100%");
		panelNagusia.setHeight("100%");
		
		Panel borderPanel = new Panel();
		borderPanel.setLayout(new BorderLayout());

		// Iparraldeko panela: Aplikazioaren titulua, gunea eta kautotutako
		// erabiltzailearen informazioa
		// raw html
		// BoxComponent northPanel = new BoxComponent();
		// northPanel.setEl(new HTML("<p>north - generally for menus, toolbars"
		// + " and/or advertisements</p>").getElement());

		// northPanel.setHeight(32);
		// borderPanel.add(northPanel, new
		// BorderLayoutData(RegionPosition.NORTH));

		Image banner = new Image("images/banner.png");

		Panel bannerPanel = new Panel();
		bannerPanel.setBorder(false);
		bannerPanel.setCollapsible(false);
		bannerPanel.add(banner);
		bannerPanel.setAutoHeight(true);
		bannerPanel.setFrame(false);
		bannerPanel.setStyle("backgroundColor: #dfe8f6;");
		bannerPanel.setBodyStyle("backgroundColor: #dfe8f6;");

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

		/*
		 * // add east panel Panel eastPanel = new Panel();
		 * eastPanel.setTitle("East Side"); eastPanel.setCollapsible(true);
		 * eastPanel.setWidth(225); eastPanel.setLayout(new FitLayout());
		 * 
		 * BorderLayoutData eastData = new
		 * BorderLayoutData(RegionPosition.EAST); eastData.setSplit(true);
		 * eastData.setMinSize(175); eastData.setMaxSize(400);
		 * eastData.setMargins(new Margins(0, 0, 5, 0));
		 * 
		 * borderPanel.add(eastPanel, eastData);
		 * 
		 * TabPanel tabPanel = new TabPanel(); tabPanel.setBorder(false);
		 * tabPanel.setActiveTab(1);
		 * 
		 * Panel tabOne = new Panel();
		 * tabOne.setHtml("<p>A TabPanel component can be a region.</p>");
		 * tabOne.setTitle("A Tab"); tabOne.setAutoScroll(true);
		 * tabPanel.add(tabOne);
		 * 
		 * PropertyGridPanel propertyGrid = new PropertyGridPanel();
		 * propertyGrid.setTitle("Property Grid");
		 * 
		 * Map source = new HashMap(); source.put("(name)", "Properties Grid");
		 * source.put("grouping", Boolean.FALSE); source.put("autoFitColumns",
		 * Boolean.TRUE); source.put("productionQuality", Boolean.FALSE);
		 * source.put("created", new Date()); source.put("tested",
		 * Boolean.FALSE); source.put("version", new Float(0.1f));
		 * source.put("borderWidth", new Integer(1));
		 * 
		 * propertyGrid.setSource(source);
		 * 
		 * tabPanel.add(propertyGrid); eastPanel.add(tabPanel);
		 */

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

		final TabPanel centerPanel = new TabPanel();
		centerPanel.setDeferredRender(false);
		centerPanel.setActiveTab(0);

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
				centerPanel.activate(2);
			}
		}));
		centerPanelTwo.add(new Button("Abisuak", new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				Abisuak abisuak = new Abisuak("09760589X");
				
				abisuak.setTitle("Zure Abisuak");
				centerPanel.add(abisuak);
				centerPanel.setActiveTab(3);
			}
		}));

		centerPanel.add(centerPanelTwo);
		centerPanel.add(centerPanelOne);

		BorderLayoutData centerData = new BorderLayoutData(
				RegionPosition.CENTER);
		// centerData.setSplit(true);
		centerData.setMargins(new Margins(0, 0, 5, 0));

		// borderPanel.add(centerPanel,
		// new BorderLayoutData(RegionPosition.CENTER));

		borderPanel.add(centerPanel, centerData);

		panelNagusia.add(borderPanel, new AnchorLayoutData("100%"));

		new Viewport(panelNagusia);
	}

	protected void setGunea(GuneInfo gunea) {
		this.gunea = gunea;
		if (!gunea.hasLatLon())
			mapa.updateMap(gunea.getIzena(), gunea.getHelbidea(), JavaScriptObjectHelper.createObject(), mapa);
		else {
			mapa.markaGehitu(gunea);
			mapa.finkatu(gunea);
		}			
	}

	public String getHelbidea() {
		return this.gunea.getHelbidea();
	}
}
