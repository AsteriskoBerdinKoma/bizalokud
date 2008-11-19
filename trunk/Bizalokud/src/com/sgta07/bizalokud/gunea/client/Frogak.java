package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.core.client.EntryPoint;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.CardLayout;
import com.gwtext.client.widgets.layout.FitLayout;

public class Frogak {

	public void onModuleLoad() {
		Panel panel = new Panel();
		panel.setBorder(false);
		panel.setPaddings(15);
		panel.setLayout(new FitLayout());

		Panel borderPanel = new Panel();
		borderPanel.setLayout(new BorderLayout());

		// add north panel
		Panel northPanel = new Panel();
		northPanel.setHtml("<p><font size=20>Bizalokud</font></p>");
		northPanel.setHeight(45);
		northPanel.setBodyStyle("background-color:FFFF88");
		borderPanel.add(northPanel, new BorderLayoutData(RegionPosition.NORTH));

		// add south panel
		Panel southPanel = new Panel();
		southPanel.setHtml("<p>south panel</p>");
		southPanel.setHeight(100);
		southPanel.setBodyStyle("background-color:CDEB8B");
		southPanel.setCollapsible(true);
		southPanel.setTitle("South");

		BorderLayoutData southData = new BorderLayoutData(RegionPosition.SOUTH);
		southData.setMinSize(100);
		southData.setMaxSize(200);
		southData.setMargins(new Margins(0, 0, 0, 0));
		southData.setSplit(true);
		borderPanel.add(southPanel, southData);

		Panel westPanel = new Panel();
		westPanel.setHtml("<p>Item bat</p>");
		westPanel.setTitle("Menu nagusia");
		westPanel.setBodyStyle("background-color:EEEEEE");
		westPanel.setCollapsible(true);
		westPanel.setWidth(200);

		BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);
		westData.setSplit(false);
		westData.setMinSize(175);
		westData.setMaxSize(400);
		westData.setMargins(new Margins(0, 5, 0, 0));

		borderPanel.add(westPanel, westData);

		final Panel centerPanel = new Panel();
		centerPanel.setLayout(new CardLayout());
		centerPanel.setActiveItem(0);
		centerPanel.setPaddings(15);
		centerPanel.setTitle("Ekintzen leihoa");
		
		 ButtonListenerAdapter listener = new ButtonListenerAdapter() {  
			             public void onClick(Button button, EventObject e) {  
			                 String btnID = button.getId();  
			                 CardLayout cardLayout = (CardLayout) centerPanel.getLayout();  
			                 String panelID = cardLayout.getActiveItem().getId();  
			   
			                 if (btnID.equals("move-prev")) {  
			                     if (panelID.equals("card-3")) {  
			                         cardLayout.setActiveItem(1);  
			                     } else {  
		                         cardLayout.setActiveItem(0);  
		                     }  
		                 } else {  
			   
		                   if (panelID.equals("card-1")) {  
			                        cardLayout.setActiveItem(1);  
			                     } else {  
			                        cardLayout.setActiveItem(2);  
			                    }  
			                 }  
			             }  
			         };  
	
		
		centerPanel.setBodyStyle("background-color:C3D9FF");

		borderPanel.add(centerPanel,
				new BorderLayoutData(RegionPosition.CENTER));

		panel.add(borderPanel);

		Viewport viewport = new Viewport(panel);
	}
}