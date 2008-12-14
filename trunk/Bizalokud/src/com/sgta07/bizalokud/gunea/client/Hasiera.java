package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.RowLayout;
import com.gwtext.client.widgets.layout.RowLayoutData;
import com.gwtext.client.widgets.map.GoogleMap;
import com.gwtext.client.widgets.map.LatLonPoint;
import com.gwtext.client.widgets.map.MapPanel;
import com.gwtext.client.widgets.map.Marker;

public class Hasiera extends BarnePanela {

	private MapPanel mapPanel;
	private Label guneIzenLabel;
	private Label guneHelbideLabel;
	private Label hasieraGuneLabel;
	private Label helburuGuneLabel;
	private Label hasDataLabel;
	private Label bizZenbLabel;
	private Label bizModeloLabel;
	private Label bizKoloreLabel;
	private ExtElement element;

	public Hasiera(Gunea owner) {
		super(owner);

		this.setTitle("Hasiera");
		this.setLayout(new RowLayout());

		Panel guneaPanel = new Panel("Gune honen informazioa");
		guneaPanel.setAutoScroll(true);
		guneaPanel.setFrame(false);
		guneaPanel.setBorder(true);
		guneaPanel.setLayout(new ColumnLayout());
		Panel azpipanel = new Panel();
		azpipanel.setLayout(new RowLayout());
		azpipanel.setFrame(false);
		azpipanel.setBorder(false);
		guneIzenLabel = new Label();
		guneHelbideLabel = new Label();
		azpipanel.add(guneIzenLabel, new RowLayoutData());
		azpipanel.add(guneHelbideLabel, new RowLayoutData());
		Panel mapaAzpiPanel = new Panel();
		mapaAzpiPanel.setFrame(false);
		mapaAzpiPanel.setBorder(false);
		mapPanel = new GoogleMap();
		mapPanel.setBorder(true);
		mapPanel.setFrame(true);
		mapPanel.addLargeControls();
		mapPanel.setSize(500, 400);
		mapaAzpiPanel.add(mapPanel);
		guneaPanel.add(azpipanel, new ColumnLayoutData(0.5));
		guneaPanel.add(mapaAzpiPanel, new ColumnLayoutData(0.5));

		Panel alokairuPanel = new Panel("Zure uneko alokairuaren informazioa");
		alokairuPanel.setAutoScroll(true);
		alokairuPanel.setLayout(new RowLayout());
		alokairuPanel.setFrame(false);
		alokairuPanel.setBorder(true);
		hasieraGuneLabel = new Label();
		helburuGuneLabel = new Label();
		hasDataLabel = new Label();
		bizZenbLabel = new Label();
		bizModeloLabel = new Label();
		bizKoloreLabel = new Label();
		alokairuPanel.add(hasieraGuneLabel, new RowLayoutData());
		alokairuPanel.add(helburuGuneLabel, new RowLayoutData());
		alokairuPanel.add(hasDataLabel, new RowLayoutData());
		alokairuPanel.add(bizZenbLabel, new RowLayoutData());
		alokairuPanel.add(bizModeloLabel, new RowLayoutData());
		alokairuPanel.add(bizKoloreLabel, new RowLayoutData());

		this.add(guneaPanel, new RowLayoutData());
		this.add(alokairuPanel, new RowLayoutData());

		this.addListener(new ComponentListenerAdapter() {

			public void onShow(Component component) {
				datuakEguneratu();
			}

		});
	}

	public void datuakEguneratu() {
		element = new ExtElement(getElement());
		element.mask("Guneen informazioa jasotzen. Itxaron mesedez", true);
		
		if (jabea.isGuneaIdentif()) {
			guneIzenLabel.setText("Gune honen izena: " + jabea.getGuneIzena());
			guneHelbideLabel.setText("Gune honen helbidea: "
					+ jabea.getGuneHelbidea());
			LatLonPoint latLonPoint = new LatLonPoint(jabea.getGuneLat(), jabea
					.getGuneLon());

			mapPanel.setCenterAndZoom(latLonPoint, 17);

			Marker m = new Marker(latLonPoint);
			mapPanel.setCenterAndZoom(latLonPoint, 17);
			m.setInfoBubble("<h1>" + jabea.getGuneIzena()
					+ "</h1><br><b>Helbidea:</b> " + jabea.getGuneHelbidea());
			mapPanel.addMarker(m);

			if (jabea.isErabIdentif()) {
				GuneaService.Util.getInstance().getAzkenAlokairuInfo(
						jabea.getErabNan(), new AsyncCallback<AlokairuInfo>() {

							public void onFailure(Throwable caught) {
								System.out.println(caught.getMessage());
								caught.printStackTrace();
								hasieraGuneLabel
										.setText("Ezin izan da alokairuen informazioa jaso.");
								helburuGuneLabel.setText("");
								hasDataLabel.setText("");
								bizZenbLabel.setText("");
								bizModeloLabel.setText("");
								bizKoloreLabel.setText("");
								element.unmask();
							}

							public void onSuccess(AlokairuInfo result) {
								if (result != null) {
									if (!result.isBukatuta()) {
										hasieraGuneLabel
												.setText("Hasiera gunea: "
														+ result.getHasGune()
																.getIzena()
														+ " - "
														+ result.getHasGune()
																.getHelbidea());
										helburuGuneLabel
												.setText("Helburu gunea: "
														+ result.getHelGune()
																.getIzena()
														+ " - "
														+ result.getHelGune()
																.getHelbidea());
										DateTimeFormat dtf = new DateTimeFormat(
												"HH:mm") {
										};
										String hasData = dtf.format(result
												.getHasieraData());
										hasDataLabel.setText("Hasiera data: "
												+ hasData);
										bizZenbLabel
												.setText("Bizikleta zenbakia: "
														+ result.getBizikleta()
																.getId());
										bizModeloLabel
												.setText("Bizikleta modeloa: "
														+ result.getBizikleta()
																.getModeloa());
										bizKoloreLabel
												.setText("Bizikleta kolorea: "
														+ result.getBizikleta()
																.getKolorea());
									} else {
										hasieraGuneLabel
												.setText("Momentu honetan ez daukazu bizikletarik alokatuta.");
										helburuGuneLabel.setText("");
										hasDataLabel.setText("");
										bizZenbLabel.setText("");
										bizModeloLabel.setText("");
										bizKoloreLabel.setText("");
									}
								} else {
									hasieraGuneLabel
											.setText("Momentu honetan ez daukazu bizikletarik alokatuta.");
									helburuGuneLabel.setText("");
									hasDataLabel.setText("");
									bizZenbLabel.setText("");
									bizModeloLabel.setText("");
									bizKoloreLabel.setText("");
								}
								element.unmask();
							}
						});
			} else
				element.unmask();
		} else
			element.unmask();
	}

	public void datuakReseteatu() {
	}
}
