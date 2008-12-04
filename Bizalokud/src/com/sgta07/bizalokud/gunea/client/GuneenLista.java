package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
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

	public GuneenLista() {
		super();
		setFrame(true);
		setStripeRows(true);
		
		setGuneak(new Object[0][0]);
		
		GuneaService.Util.getInstance().guneenZerrenda(new AsyncCallback<HashMap<Integer,String>>(){
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
			}

			public void onSuccess(HashMap<Integer,String> result) {
				Object[][] obj = new Object[result.size()][2];
				int i = 0;
				for(int key: result.keySet()){
					obj[i][0] = key;
					obj[i][1] = result.get(key);
					i++;
				}
				setGuneak(obj);
			}
		
		});
	}
	
	public void setGuneak(Object[][] guneak){
		RecordDef recordDef = new RecordDef(new FieldDef[] {
				new IntegerFieldDef("id"), new StringFieldDef("gunea") });

		MemoryProxy proxy = new MemoryProxy(guneak);

		ArrayReader reader = new ArrayReader(recordDef);
		store = new Store(proxy, reader);
		store.load();

		setStore(store);

		ColumnConfig[] columns = new ColumnConfig[] {
		// column ID is company which is later used in setAutoExpandColumn
		new ColumnConfig("Gunea", "gunea", 200, true, null, "gunea") };

		ColumnModel columnModel = new ColumnModel(columns);
		setColumnModel(columnModel);
		
		setAutoExpandColumn("gunea");
	}

}
