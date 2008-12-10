package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.DateFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtextux.client.data.PagingMemoryProxy;

public class Abisuak extends Panel {

	private String userNan;
	private Object[][] datuak;
	
	public Abisuak(){
		
	}

	public Abisuak(String nan) {
		super();
		userNan = nan;
		final Panel panel = this;
		panel.setBorder(false);
		panel.setPaddings(15);
		panel.setAutoScroll(true);
		
		GuneaService.Util.getInstance().getAbisuenZerrenda(userNan,
				new AsyncCallback<HashMap<Integer, AbisuInfo>>() {

					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					public void onSuccess(HashMap<Integer, AbisuInfo> result) {
						datuak = new Object[result.size()][4];
						int i = 0;
						for (int key : result.keySet()) {
							datuak[i][0] = key;
							datuak[i][1] = result.get(key).getData();
							datuak[i][2] = result.get(key).getMota();
							datuak[i][3] = result.get(key).getMezua();
							i++;
						}
						PagingMemoryProxy proxy = new PagingMemoryProxy(datuak);
						RecordDef recordDef = new RecordDef(new FieldDef[] {
								new IntegerFieldDef("id"), new DateFieldDef("data"),
								new StringFieldDef("mota") });

						ArrayReader reader = new ArrayReader(recordDef);
						final Store store = new Store(proxy, reader, true);
						ColumnConfig[] columns = new ColumnConfig[] {
								// column ID is company which is later used in
								// setAutoExpandColumn
								new ColumnConfig("Id", "id", 20, true),
								new ColumnConfig("Data", "data", 65, true),
								new ColumnConfig("Mota", "mota", 60, true, null, "mota") };

						ColumnModel columnModel = new ColumnModel(columns);

						GridPanel grid = new GridPanel();
						grid.setStore(store);
						grid.setColumnModel(columnModel);

						grid.setFrame(true);
						grid.setStripeRows(true);
						grid.setWidth(600);
						grid.setHeight(250);
						grid.setTitle("Zure abisuen zerrenda");
						grid.setAutoExpandColumn("mota");

						final PagingToolbar pagingToolbar = new PagingToolbar(store);
						pagingToolbar.setPageSize(5);
						pagingToolbar.setDisplayInfo(true);
						pagingToolbar.setDisplayMsg("Displaying companies {0} - {1} of {2}");
						pagingToolbar.setEmptyMsg("Ez daukazu abisurik");

						NumberField pageSizeField = new NumberField();
						pageSizeField.setWidth(40);
						pageSizeField.setSelectOnFocus(true);
						pageSizeField.addListener(new FieldListenerAdapter() {
							public void onSpecialKey(Field field, EventObject e) {
								if (e.getKey() == EventObject.ENTER) {
									int pageSize = Integer.parseInt(field.getValueAsString());
									pagingToolbar.setPageSize(pageSize);
								}
							}
						});

						ToolTip toolTip = new ToolTip("Sartu orriaren tamaina");
						toolTip.applyTo(pageSizeField);

						pagingToolbar.addField(pageSizeField);
						grid.setBottomToolbar(pagingToolbar);
						store.load(0, 5);
						System.out.println("4");
						panel.add(grid);

					}

				});
		
	}
}
