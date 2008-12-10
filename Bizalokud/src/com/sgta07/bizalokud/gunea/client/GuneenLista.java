package com.sgta07.bizalokud.gunea.client;

import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.ObjectFieldDef;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.RowSelectionModel;

public class GuneenLista extends GridPanel {

	private Store store;

	private ArrayReader reader;
	
	GridPanel grid;
	
	public GuneenLista() {
		super();
//		Panel panel = this;
//		panel.setWidth(300);
//		panel.setHeight(300);
//		panel.setBorder(false);
//		panel.setPaddings(15);
//		panel.setAutoScroll(true);
		
		RecordDef recordDef = new RecordDef(new FieldDef[] {
				new IntegerFieldDef("id"), new StringFieldDef("izena"),
				new StringFieldDef("helbidea"), new ObjectFieldDef("lat"), new ObjectFieldDef("lon")});

		MemoryProxy proxy = new MemoryProxy(new Object[0][]);
		
		reader = new ArrayReader(recordDef);
		store = new Store(proxy, reader, true);

		ColumnConfig[] columns = new ColumnConfig[] {
				new ColumnConfig("Gunea", "izena", 200, true, null, "izena"),
				new ColumnConfig("Helbidea", "helbidea", 200, true, null,
						"helbidea") };
		
		ColumnModel columnModel = new ColumnModel(columns);
		
		grid = this;
		grid.setStore(store);
		grid.setColumnModel(columnModel);
		
		grid.setFrame(true);
		grid.setStripeRows(true);
		grid.setAutoExpandColumn("helbidea");
		grid.setWidth(300);
		grid.setHeight(300);
		grid.setTitle("Zure abisuen zerrenda");
		
		store.load();
	}

	public void setGuneak(Object[][] guneak) {
		Object[][] data = guneak;
		MemoryProxy proxy = new MemoryProxy(data);
		
		Store storeTemp = new Store(proxy, reader);
		storeTemp.reload();
		store.removeAll();
		store.add(storeTemp.getRecords());
	}

}
