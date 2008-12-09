package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.DateFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
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

public class Abisuak extends Composite {

	private String userNan;

	public Abisuak(String userNan) {
		Panel panel = new Panel();
		panel.setBorder(false);
		panel.setPaddings(15);
		this.userNan = userNan;

		GuneaService.Util.getInstance().getAbisuenZerrenda(userNan,
				new AsyncCallback<HashMap<Integer, AbisuInfo>>() {

					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					public void onSuccess(HashMap<Integer, AbisuInfo> result) {
						

					}

				});

		PagingMemoryProxy proxy = new PagingMemoryProxy(getCompanyData());
		RecordDef recordDef = new RecordDef(new FieldDef[] {
				new StringFieldDef("company"), new FloatFieldDef("price"),
				new FloatFieldDef("change"), new FloatFieldDef("pctChange"),
				new DateFieldDef("lastChanged", "n/j h:ia"),
				new StringFieldDef("symbol"), new StringFieldDef("industry") });

		ArrayReader reader = new ArrayReader(recordDef);
		final Store store = new Store(proxy, reader, true);

		ColumnConfig[] columns = new ColumnConfig[] {
				// column ID is company which is later used in
				// setAutoExpandColumn
				new ColumnConfig("Company", "company", 160, true, null,
						"company"), new ColumnConfig("Price", "price", 35),
				new ColumnConfig("Change", "change", 45),
				new ColumnConfig("% Change", "pctChange", 65),
				new ColumnConfig("Last Updated", "lastChanged", 65),
				new ColumnConfig("Industry", "industry", 60, true) };

		ColumnModel columnModel = new ColumnModel(columns);

		GridPanel grid = new GridPanel();
		grid.setStore(store);
		grid.setColumnModel(columnModel);

		grid.setFrame(true);
		grid.setStripeRows(true);
		grid.setAutoExpandColumn("company");
		grid.setWidth(600);
		grid.setHeight(250);
		grid.setTitle("Grid that pages Local / In-Memory data");
		grid.setAutoExpandColumn("company");

		final PagingToolbar pagingToolbar = new PagingToolbar(store);
		pagingToolbar.setPageSize(5);
		pagingToolbar.setDisplayInfo(true);
		pagingToolbar.setDisplayMsg("Displaying companies {0} - {1} of {2}");
		pagingToolbar.setEmptyMsg("No records to display");

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

		ToolTip toolTip = new ToolTip("Enter page size");
		toolTip.applyTo(pageSizeField);

		pagingToolbar.addField(pageSizeField);
		grid.setBottomToolbar(pagingToolbar);
		store.load(0, 5);

		panel.add(grid);

		RootPanel.get().add(panel);
	}

	private Object[][] getCompanyData() {
		return new Object[][] {
				new Object[] { "3m Co", new Double(71.72), new Double(0.02),
						new Double(0.03), "9/1 12:00am", "MMM", "Manufacturing" },
				new Object[] { "Alcoa Inc", new Double(29.01),
						new Double(0.42), new Double(1.47), "9/1 12:00am",
						"AA", "Manufacturing" },
				new Object[] { "Altria Group Inc", new Double(83.81),
						new Double(0.28), new Double(0.34), "9/1 12:00am",
						"MO", "Manufacturing" },
				new Object[] { "American Express Company", new Double(52.55),
						new Double(0.01), new Double(0.02), "9/1 12:00am",
						"AXP", "Finance" },
				new Object[] { "American International Group, Inc.",
						new Double(64.13), new Double(0.31), new Double(0.49),
						"9/1 12:00am", "AIG", "Services" },
				new Object[] { "AT&T Inc.", new Double(31.61),
						new Double(-0.48), new Double(-1.54), "9/1 12:00am",
						"T", "Services" },
				new Object[] { "Boeing Co.", new Double(75.43),
						new Double(0.53), new Double(0.71), "9/1 12:00am",
						"BA", "Manufacturing" },
				new Object[] { "Caterpillar Inc.", new Double(67.27),
						new Double(0.92), new Double(1.39), "9/1 12:00am",
						"CAT", "Services" },
				new Object[] { "Citigroup, Inc.", new Double(49.37),
						new Double(0.02), new Double(0.04), "9/1 12:00am", "C",
						"Finance" },
				new Object[] { "E.I. du Pont de Nemours and Company",
						new Double(40.48), new Double(0.51), new Double(1.28),
						"9/1 12:00am", "DD", "Manufacturing" } };
	}
}
