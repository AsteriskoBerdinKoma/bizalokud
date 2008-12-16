package com.sgta07.bizalokud.gunea.client;

import com.gwtext.client.core.NameValuePair;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridView;
import com.gwtext.client.widgets.grid.PropertyGridPanel;
import com.gwtext.client.widgets.grid.event.PropertyGridPanelListener;
import com.gwtext.client.widgets.layout.FitLayout;

public class NireDatuak extends BarnePanela{
	
	private Panel panel;
	
	public NireDatuak(Gunea owner){
		super(owner);
		panel=this;
		
		panel.setTitle("Nire Informazioa");
		panel.setLayout(new FitLayout());
		panel.setBorder(false);
		panel.setAutoScroll(true);
		panel.setCollapsible(false);
		panel.setPaddings(15);
		         PropertyGridPanel grid = new PropertyGridPanel();  
		         grid.setId("props-grid");  
		         grid.setNameText("Properties Grid");  
		         grid.setWidth(300);  
		         grid.setAutoHeight(true);  
		         grid.setSorted(false);    
		   
		         GridView view = new GridView();  
		         view.setForceFit(true);  
		         view.setScrollOffset(2); // the grid will never have scrollbars  
		         grid.setView(view);  
		   
		         NameValuePair[] source = new NameValuePair[4];  
		         source[0] = new NameValuePair("Izena", "Zure Izena");  
		         source[1] = new NameValuePair("Abizenak", "Zure Abizenak");  
		         source[2] = new NameValuePair("ePosta", "Zure ePosta");  
		         source[3] = new NameValuePair("Telf. Zenbakia", "Zure Telefono Zenbakia");  
		   
		         grid.setSource(source); 
		         grid.addPropertyGridPanelListener(new PropertyGridPanelListener() {  
		             public boolean doBeforePropertyChange(PropertyGridPanel source, String recordID,  
		                                                     Object value, Object oldValue) {  
		                 return true;  
		             }  
		   
		             public void onPropertyChange(PropertyGridPanel source, String recordID, Object value,  
		                                                     Object oldValue) {  
		                 System.out.println(Format.format("Property '{0}' changed from {1} to {2}.", recordID,  
		                                                     String.valueOf(oldValue), String.valueOf(value)));  
		             }  
		         });  
		         panel.add(grid);
		         Toolbar tb = new Toolbar();
		         tb.addButton(new ToolbarButton("Aldaketak Gorde"));
		         tb.addFill();
		         tb.addButton(new ToolbarButton("Utzi"));
		         panel.setBottomToolbar(tb);
	
				
		
	}
	


	@Override
	public void datuakEguneratu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void datuakReseteatu() {
		// TODO Auto-generated method stub
		
	}

}
