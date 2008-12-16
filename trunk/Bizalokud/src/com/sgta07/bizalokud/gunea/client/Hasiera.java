package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.core.Function;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.RowLayout;
import com.gwtext.client.widgets.layout.RowLayoutData;
import com.gwtext.client.widgets.map.GoogleMap;
import com.gwtext.client.widgets.map.LatLonPoint;
import com.gwtext.client.widgets.map.MapPanel;
import com.gwtext.client.widgets.map.Marker;

public class Hasiera extends BarnePanela {

	private MapPanel mapPanel;
	// private Label guneIzenLabel;
	// private Label guneHelbideLabel;
	// private Label hasieraGuneLabel;
	// private Label helburuGuneLabel;
	// private Label hasDataLabel;
	// private Label bizZenbLabel;
	// private Label bizModeloLabel;
	// private Label bizKoloreLabel;
	private ExtElement element;
	private boolean mapRendered;
	private Panel alokairuPanel;
	private Panel guneAzpipanel;

	public Hasiera(Gunea owner) {
		super(owner);

		this.setTitle("Hasiera");
		this.setLayout(new FitLayout());
		this.setPaddings(0);
		this.setAutoScroll(true);

		Panel panel = new Panel();
		panel.setLayout(new RowLayout());

		Panel guneaPanel = new Panel("Gune honen informazioa");
		guneaPanel.setAutoScroll(true);
		guneaPanel.setFrame(false);
		guneaPanel.setBorder(true);
		guneaPanel.setLayout(new ColumnLayout());
		guneAzpipanel = new Panel();
		guneAzpipanel.setFrame(false);
		guneAzpipanel.setBorder(false);
		sortuMapa();
		guneaPanel.add(guneAzpipanel, new ColumnLayoutData(0.4));
		guneaPanel.add(mapPanel, new ColumnLayoutData(0.6));

		alokairuPanel = new Panel("Zure uneko alokairuaren informazioa");
		alokairuPanel.setAutoScroll(true);
		alokairuPanel.setFrame(false);
		alokairuPanel.setBorder(true);

		panel.add(guneaPanel, new RowLayoutData(230));
		panel.add(alokairuPanel, new RowLayoutData());

		this.add(panel);

		this.addListener(new ComponentListenerAdapter() {

			public void onShow(Component component) {
				datuakEguneratu();
			}

		});
	}

	public void sortuMapa() {
		mapPanel = new GoogleMap();
		mapPanel.setBorder(false);
		mapPanel.setFrame(true);
		mapPanel.addLargeControls();
		mapPanel.setWidth(300);
		mapPanel.setHeight(200);

		mapRendered = false;

		mapPanel.addListener(MapPanel.MAP_RENDERED_EVENT, new Function() {
			public void execute() {
				mapRendered = true;
			}
		});

		mapPanel.addListener(new PanelListenerAdapter() {
			public void onResize(BoxComponent component, int adjWidth,
					int adjHeight, int rawWidth, int rawHeight) {

				if (mapRendered)
					mapPanel.resizeTo(mapPanel.getInnerWidth(), mapPanel
							.getInnerHeight());
			}
		});
	}

	public void datuakEguneratu() {
		element = new ExtElement(getElement());
		element.mask("Guneen informazioa jasotzen. Itxaron mesedez", true);

		if (jabea.isGuneaIdentif()) {
			guneAzpipanel.setHtml("<p><b>Gune honen izena:</b> "
					+ jabea.getGuneIzena() + "<br><b>Gune honen helbidea:</b> "
					+ jabea.getGuneHelbidea());

			LatLonPoint latLonPoint = new LatLonPoint(jabea.getGuneLat(), jabea
					.getGuneLon());
			Marker m = new Marker(latLonPoint);
			mapPanel.setCenterAndZoom(latLonPoint, 17);
			m.setInfoBubble("<h1>" + jabea.getGuneIzena()
					+ "<h1><br><b>Helbidea:</b> " + jabea.getGuneHelbidea());
			mapPanel.addMarker(m);

			if (jabea.isErabIdentif()) {
				GuneaService.Util.getInstance().getAzkenAlokairuInfo(
						jabea.getErabNan(), new AsyncCallback<AlokairuInfo>() {

							public void onFailure(Throwable caught) {
								System.out.println(caught.getMessage());
								caught.printStackTrace();
								alokairuPanel
										.setHtml("<p style=\"font-size:medium;\" align=\"center\"><b>Ezin izan da alokairuen informazioa jaso</b>");
								element.unmask();
							}

							public void onSuccess(AlokairuInfo result) {
								if (result != null) {
									if (!result.isBukatuta()) {
										DateTimeFormat dtf = new DateTimeFormat(
												"HH:mm") {
										};
										String hasData = dtf.format(result
												.getHasieraData());
										alokairuPanel
												.setHtml("<p ><b>Hasiera gunea:</b> "
														+ result.getHasGune()
																.getIzena()
														+ " - "
														+ result.getHasGune()
																.getHelbidea()
														+ "<br>"
														+ "<b>Helburu gunea:</b> "
														+ result.getHelGune()
																.getIzena()
														+ " - "
														+ result.getHelGune()
																.getHelbidea()
														+ "</p><br>"
														+ "<p><b>Hasiera data:</b> "
														+ hasData
														+ "</p><br>"
														+ "<p><b>Bizikleta zenbakia:</b> "
														+ result.getBizikleta()
																.getId()
														+ "<br>"
														+ "<b>Bizikleta modeloa:</b> "
														+ result.getBizikleta()
																.getModeloa()
														+ "<br>"
														+ "<b>Bizikleta kolorea:</b> "
														+ result.getBizikleta()
																.getKolorea()
														+ "</p>");
									} else {
										alokairuPanel
												.setHtml("<p style=\"font-size:medium;\" align=\"center\"><b>Momentu honetan ez daukazu bizikletarik alokatuta</b>");
									}
								} else {
									alokairuPanel
											.setHtml("<p style=\"font-size:medium;\" align=\"center\"><b>Momentu honetan ez daukazu bizikletarik alokatuta</b>");
								}
								element.unmask();
							}
						});
			} else{
				alokairuPanel
				.setHtml("");
				element.unmask();
			}
		} else
			element.unmask();
	}

	public void datuakReseteatu() {
	}
}
