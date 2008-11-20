package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.layout.AccordionLayout;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gunea implements EntryPoint {
	public void onModuleLoad() {
		Panel panelNagusia = new Panel();
		panelNagusia.setBorder(false);
		//Inguruarekiko uzten duen margina
		panelNagusia.setPaddings(0);
		panelNagusia.setLayout(new FitLayout());

		Panel borderPanel = new Panel();
		borderPanel.setLayout(new BorderLayout());

		// Iparraldeko panela: Aplikazioaren titulua, gunea eta kautotutako erabiltzailearen informazioa
		// raw html
		BoxComponent northPanel = new BoxComponent();
		northPanel.setEl(new HTML("<p>north - generally for menus, toolbars"
				+ " and/or advertisements</p>").getElement());
		northPanel.setHeight(32);
		borderPanel.add(northPanel, new BorderLayoutData(RegionPosition.NORTH));

		// Egoaldeko panela: Azken minutuko informazioak jasotzeko
		Panel southPanel = new HTMLPanel("<br>" +
										 "<li>Amarako gunea bihartik aurrera itxita egongo da, konponketak egiteko!</li> " +
										 "<li>Groseko obrak direla eta, Sagues-Bulevard ibilbidea ez hartzea gomendatzen da</li>");
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
		// add east panel
		Panel eastPanel = new Panel();
		eastPanel.setTitle("East Side");
		eastPanel.setCollapsible(true);
		eastPanel.setWidth(225);
		eastPanel.setLayout(new FitLayout());

		BorderLayoutData eastData = new BorderLayoutData(RegionPosition.EAST);
		eastData.setSplit(true);
		eastData.setMinSize(175);
		eastData.setMaxSize(400);
		eastData.setMargins(new Margins(0, 0, 5, 0));

		borderPanel.add(eastPanel, eastData);

		TabPanel tabPanel = new TabPanel();
		tabPanel.setBorder(false);
		tabPanel.setActiveTab(1);

		Panel tabOne = new Panel();
		tabOne.setHtml("<p>A TabPanel component can be a region.</p>");
		tabOne.setTitle("A Tab");
		tabOne.setAutoScroll(true);
		tabPanel.add(tabOne);

		PropertyGridPanel propertyGrid = new PropertyGridPanel();
		propertyGrid.setTitle("Property Grid");

		Map source = new HashMap();
		source.put("(name)", "Properties Grid");
		source.put("grouping", Boolean.FALSE);
		source.put("autoFitColumns", Boolean.TRUE);
		source.put("productionQuality", Boolean.FALSE);
		source.put("created", new Date());
		source.put("tested", Boolean.FALSE);
		source.put("version", new Float(0.1f));
		source.put("borderWidth", new Integer(1));

		propertyGrid.setSource(source);

		tabPanel.add(propertyGrid);
		eastPanel.add(tabPanel);
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

		TabPanel centerPanel = new TabPanel();
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

		centerPanel.add(centerPanelTwo);
		centerPanel.add(centerPanelOne);
		
		BorderLayoutData centerData = new BorderLayoutData(RegionPosition.CENTER);
		//centerData.setSplit(true);
		centerData.setMargins(new Margins(0, 0, 5, 0));
		
//		borderPanel.add(centerPanel,
//				new BorderLayoutData(RegionPosition.CENTER));
		borderPanel.add(centerPanel,centerData);

		panelNagusia.add(borderPanel);

		new Viewport(panelNagusia);
	}
}
