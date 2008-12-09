package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;
import com.gwtext.client.widgets.layout.CardLayout;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;

public class Alokatu extends Composite {

	private Panel first;

	public Alokatu() {
		super();
		Panel panel = new Panel();
		panel.setTitle("Alokatu");
		panel.setBorder(false);
		panel.setPaddings(15);

		final Panel wizardPanel = new Panel();
		wizardPanel.setTitle("Bizikleta Alokatu");
		wizardPanel.setLayout(new CardLayout());
		wizardPanel.setActiveItem(0);
		wizardPanel.setPaddings(15);

		ButtonListenerAdapter listener = new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				String btnID = button.getId();
				CardLayout cardLayout = (CardLayout) wizardPanel.getLayout();
				String panelID = cardLayout.getActiveItem().getId();

				if (btnID.equals("atzera")) {
					if (panelID.equals("card-3")) {
						cardLayout.setActiveItem(1);
					} else {
						cardLayout.setActiveItem(0);
					}
				} else {

					if (panelID.equals("guneaAukeratu")) {
						cardLayout.setActiveItem(1);
					} else {
						cardLayout.setActiveItem(2);
					}
				}
			}
		};

		Toolbar toolbar = new Toolbar();

		ToolbarButton backButton = new ToolbarButton("Atzera", listener);
		backButton.setId("atzera");
		toolbar.addButton(backButton);
		toolbar.addFill();

		ToolbarButton nextButton = new ToolbarButton("Jarraitu", listener);
		nextButton.setId("jarraitu");
		toolbar.addButton(nextButton);

		wizardPanel.setBottomToolbar(toolbar);

		Panel second = new Panel();
		second.setBorder(false);
		second.setId("card-2");
		second.setHtml("<p>Step 2 of 3</p>");

		Panel third = new Panel();
		third.setBorder(false);
		third.setId("card-3");
		third.setHtml("<h1>Congratulations!</h1><p>Step 3 of 3 - Complete</p>");

		sortuPanel1();

		wizardPanel.add(first);
		wizardPanel.add(second);
		wizardPanel.add(third);

		panel.add(wizardPanel);

		initWidget(panel);
		this.setVisible(true);
	}

	private void sortuPanel1() {
		final ExtElement element = new ExtElement(RootPanel.get().getElement());
		element.mask("Guneen informazioa jasotzen. Itxaron mesedez");

		first = new Panel();
		first.setSize("auto", "auto");
		first.setBorder(false);
		first.setAutoHeight(true);
		first.setId("guneaAukeratu");
		first.add(new Label("Aukeratu zein gunetara joan nahi duzun"));

		final Mapa mapa = new Mapa(500, 439);
		
		final Panel inner = new Panel();
		inner.setLayout(new ColumnLayout());

		final Panel gunePanel = new Panel();
		gunePanel.setAutoScroll(true);

		final GuneenLista guneak = new GuneenLista();
		guneak.setAutoWidth(true);
		guneak.setAutoHeight(true);
		final RowSelectionModel sm = new RowSelectionModel(true);
		sm.addListener(new RowSelectionListenerAdapter() {
			public void onRowSelect(RowSelectionModel sm, int rowIndex,
					Record record) {
				mapa.markaGehitu(record.getAsString("izena"), record
						.getAsString("helbidea"), record.getAsDouble("lat"),
						record.getAsDouble("lon"));
			}
		});
		guneak.setSelectionModel(sm);
		gunePanel.add(guneak);

		inner.add(gunePanel, new ColumnLayoutData(0.4));
		inner.add(mapa.getMapPanel(), new ColumnLayoutData(0.6));

		first.add(inner);
		
		GuneaService.Util.getInstance().guneenZerrenda(
				new AsyncCallback<HashMap<Integer, GuneInfo>>() {
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						element.unmask();
					}

					public void onSuccess(HashMap<Integer, GuneInfo> result) {
						Object[][] obj = new Object[result.size()][5];
						int i = 0;
						for (int key : result.keySet()) {
							obj[i][0] = key;
							obj[i][1] = result.get(key).getIzena();
							obj[i][2] = result.get(key).getHelbidea();
							obj[i][3] = result.get(key).getLat();
							obj[i][4] = result.get(key).getLon();
							mapa.markaGehitu(result.get(key));
							i++;
						}
						guneak.setGuneak(obj);
						sm.selectFirstRow();
						element.unmask();
					}

				});
	}
}
