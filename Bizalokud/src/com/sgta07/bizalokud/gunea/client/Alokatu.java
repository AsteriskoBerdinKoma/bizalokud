package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Function;
import com.gwtext.client.data.Record;
import com.gwtext.client.util.JavaScriptObjectHelper;
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

	Panel panel;

	private Mapa mapa;

	public Alokatu() {
		super();
		panel = new Panel();
		panel.setBorder(false);
		panel.setPaddings(15);

		mapa = new Mapa(500, 400);

		final Panel wizardPanel = new Panel();
		wizardPanel.setHeight(500);
		wizardPanel.setAutoWidth(true);
		wizardPanel.setTitle("Bizikleta Alokatu");
		wizardPanel.setLayout(new CardLayout());
		wizardPanel.setActiveItem(0);
		wizardPanel.setPaddings(15);

		// GuneaService.Util.getInstance().guneenZerrenda(guneId, callback)(
		// erabiltzaileaTextField.getText(),
		// getSha1(pasahitzaTextField.getText()),
		// new AsyncCallback<LoginInfo>()
		//				
		// GuneenLista guneZerrenda = new GuneenLista();

		ButtonListenerAdapter listener = new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				String btnID = button.getId();
				CardLayout cardLayout = (CardLayout) wizardPanel.getLayout();
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

		Toolbar toolbar = new Toolbar();

		ToolbarButton backButton = new ToolbarButton("Atzera", listener);
		backButton.setId("move-prev");
		toolbar.addButton(backButton);
		toolbar.addFill();

		ToolbarButton nextButton = new ToolbarButton("Jarraitu", listener);
		nextButton.setId("move-next");
		toolbar.addButton(nextButton);

		wizardPanel.setBottomToolbar(toolbar);

		Panel first = new Panel();
		first.setBorder(false);
		first.setAutoHeight(true);
		first.setId("guneaAukeratu");
		first.add(new Label("Aukeratu zein gunetara joan nahi duzun"));

		Panel inner = new Panel();
		inner.setHeight(450);
		inner.setLayout(new ColumnLayout());

		final Panel gunePanel = new Panel();
		gunePanel.setAutoScroll(true);
		gunePanel.setHeight(450);
		
		GuneaService.Util.getInstance().guneenZerrenda(
				new AsyncCallback<HashMap<Integer, GuneInfo>>() {
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					public void onSuccess(HashMap<Integer, GuneInfo> result) {
						Object[][] obj = new Object[result.size()][3];
						int i = 0;
						for (int key : result.keySet()) {
							mapa.addMarker(result.get(key).getHelbidea(), JavaScriptObjectHelper.createObject(), mapa);
							obj[i][0] = key;
							obj[i][1] = result.get(key).getIzena();
							obj[i][2] = result.get(key).getHelbidea();
							i++;
						}
						
						GuneenLista guneak = new GuneenLista(obj);
						guneak.setWidth("100%");
						guneak.setHeight("100%");
						final RowSelectionModel sm = new RowSelectionModel(true);
						sm.addListener(new RowSelectionListenerAdapter() {
							public void onRowSelect(RowSelectionModel sm, int rowIndex,
									Record record) {
								mapa.updateMap(record.getAsString("helbidea"),
										JavaScriptObjectHelper.createObject(), mapa);
							}
						});
						guneak.setSelectionModel(sm);
						// select the first row after a little delay so that the rows are
						// rendered
						guneak.doOnRender(new Function() {
							public void execute() {
								sm.selectFirstRow();
							}
						}, 10);
						gunePanel.add(guneak);
					}

				});
		
		inner.add(gunePanel, new ColumnLayoutData(0.4));
		inner.add(mapa.getMapPanel(), new ColumnLayoutData(0.6));

		first.add(inner);

		Panel second = new Panel();
		second.setBorder(false);
		second.setId("card-2");
		second.setHtml("<p>Step 2 of 3</p>");

		Panel third = new Panel();
		third.setBorder(false);
		third.setId("card-3");
		third.setHtml("<h1>Congratulations!</h1><p>Step 3 of 3 - Complete</p>");

		wizardPanel.add(first);
		wizardPanel.add(second);
		wizardPanel.add(third);

		panel.add(wizardPanel);
		
		initWidget(panel);
		this.setVisible(true);
	}

//	public Panel getPanel() {
//		return panel;
//	}
}
