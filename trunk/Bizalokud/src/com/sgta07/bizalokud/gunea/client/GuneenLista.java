package com.sgta07.bizalokud.gunea.client;

import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;

public class GuneenLista extends GridPanel {

	protected Store store;

	public GuneenLista(Object[][] obj) {
		super();
		setFrame(true);
		setStripeRows(true);

		setGuneak(obj);
	}

	public void setGuneak(Object[][] guneak) {
		RecordDef recordDef = new RecordDef(new FieldDef[] {
				new IntegerFieldDef("id"), new StringFieldDef("izena"),
				new StringFieldDef("helbidea") });

		MemoryProxy proxy = new MemoryProxy(guneak);

		ArrayReader reader = new ArrayReader(recordDef);
		store = new Store(proxy, reader);
		store.load();

		setStore(store);

		ColumnConfig[] columns = new ColumnConfig[] {
				// column ID is company which is later used in
				// setAutoExpandColumn
				new ColumnConfig("Gunea", "izena", 200, true, null, "izena"),
				new ColumnConfig("Helbidea", "helbidea", 200, true, null,
						"helbidea") };

		ColumnModel columnModel = new ColumnModel(columns);
		setColumnModel(columnModel);

		setAutoExpandColumn("izena");
	}

}
