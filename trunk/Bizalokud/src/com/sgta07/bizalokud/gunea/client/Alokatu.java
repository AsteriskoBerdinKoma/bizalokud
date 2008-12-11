package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.core.Function;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
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
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.CardLayout;
import com.gwtext.client.widgets.map.GoogleMap;
import com.gwtext.client.widgets.map.LatLonPoint;
import com.gwtext.client.widgets.map.MapPanel;
import com.gwtext.client.widgets.map.Marker;

public class Alokatu extends Panel {

	private Panel wizardPanel;
	
	private Panel first;
	private Panel second;
	
	private MapPanel mapPanel;
	
	private GuneenLista guneak;
	
	private ToolbarButton alokatuButton;
	
	private Gunea jabea;

	public Alokatu(Gunea owner) {
		super();
		
		this.jabea = owner;
		
		wizardPanel = this;
		wizardPanel.setTitle("Bizikleta Alokatu");
		wizardPanel.setLayout(new CardLayout());
		wizardPanel.setActiveItem(0);

		ButtonListenerAdapter listener = new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				String btnID = button.getId();
				CardLayout cardLayout = (CardLayout) wizardPanel.getLayout();
				String panelID = cardLayout.getActiveItem().getId();

				if (btnID.equals("Alokatu")) {
					if (panelID.equals("secondPanel")){
						cardLayout.setActiveItem(0);
					}
				} else if (btnID.equals("jarraitu")){
					if (panelID.equals("firstPanel")) {
						final ExtElement element = new ExtElement(RootPanel.get().getElement());
						element.mask("Alokairua egiten. Itxaron mesedez.", true);
						
						GuneaService.Util.getInstance().alokatu(jabea.getGuneId(), guneak.getSelectionModel().getSelected().getAsInteger("id"), jabea.getErabNan(), new AsyncCallback<BizikletaInfo>(){
							public void onFailure(Throwable caught) {
								caught.printStackTrace();
								
								CardLayout cardLayout = (CardLayout) wizardPanel.getLayout();
								cardLayout.setActiveItem(0);
								element.unmask();
								
								//TODO: Errore mezua erakutsi
							}

							public void onSuccess(BizikletaInfo result) {
								CardLayout cardLayout = (CardLayout) wizardPanel.getLayout();
								cardLayout.setActiveItem(1);
								element.unmask();
							}});
					}
				}
			}
		};

		Toolbar toolbar = new Toolbar();

		alokatuButton = new ToolbarButton("Alokatu", listener);
		alokatuButton.setId("alokatu");
		toolbar.addButton(alokatuButton);

		wizardPanel.setBottomToolbar(toolbar);

		sortuPanel1();
		sortuPanel2();

		wizardPanel.add(first);
		wizardPanel.add(second);
	}

	private void sortuPanel1() {
		final ExtElement element = new ExtElement(RootPanel.get().getElement());
		element.mask("Guneen informazioa jasotzen. Itxaron mesedez", true);
		
		createMapPanel();

		first = new Panel();
		first.setLayout(new BorderLayout());
		first.setBorder(false);
		first.setId("firstPanel");

		guneak = new GuneenLista();
		final RowSelectionModel sm = new RowSelectionModel(true);
		sm.addListener(new RowSelectionListenerAdapter() {
			public void onRowSelect(RowSelectionModel sm, int rowIndex,
					Record record) {
				markaGehitu(record.getAsString("izena"), record
						.getAsString("helbidea"), record.getAsDouble("lat"),
						record.getAsDouble("lon"));
			}
		});
		guneak.doOnRender(new Function() {
			public void execute() {
				sm.selectFirstRow();
			}
		}, 10);
		guneak.setSelectionModel(sm);

		BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);
		westData.setSplit(true);
		westData.setMargins(new Margins(0, 5, 0, 0));
		
		first.add(guneak, westData);
		first.add(mapPanel , new BorderLayoutData(RegionPosition.CENTER));
		first.add(new Label("Aukeratu zein gunera joan nahi duzun"), new BorderLayoutData(RegionPosition.NORTH));

		GuneaService.Util.getInstance().guneenZerrenda(
				new AsyncCallback<HashMap<Integer, GuneInfo>>() {
					public void onFailure(Throwable caught) {
						caught.getMessage();
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
							markaGehitu(result.get(key));
							i++;
						}
						guneak.setGuneak(obj);
						sm.selectFirstRow();
						element.unmask();
					}

				});
	}
	
	private void sortuPanel2() {
		second = new Panel();
		second.setLayout(new BorderLayout());
		second.setBorder(false);
		second.setId("secondPanel");
		
		String html = "<h1 align=\"center\">Alokairua ondo bete da</h1>" +
                    "<p style=\"font-size:large;\"><strong>Esleitu zaizun bizikleta zenbakia:</strong> 4<br /><br />" +
                      "<strong>Bizikletaren modeloa:</strong> Orbea" +
                      "<br />" +
                      "<strong>Bizikletaren kolorea:</strong> Gorria</p>";
		second.setHtml(html);
	}
	
	private void createMapPanel() {
		mapPanel = new GoogleMap() {

			public void addEventListener(final String event,
					final OneArgFunction listener) {
				if (!this.isRendered()) {
					addListener(MAP_RENDERED_EVENT, new Function() {
						public void execute() {
							doAddEventListener(event, listener);
						}
					});
				} else {
					doAddEventListener(event, listener);
				}
			}

			private native void doAddEventListener(String event, OneArgFunction listener) /*-{
				var map = this.@com.gwtext.client.widgets.map.MapPanel::mapJS;
				map.addEventListener(event, function(llp) {
					listener.@com.sgta07.bizalokud.gunea.client.OneArgFunction::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(llp);
				});
			}-*/;

			{
				addEventListener("click", new OneArgFunction() {
					public void execute(JavaScriptObject arg) {
						renderMap(arg);
					}
				});
			}
		};
		mapPanel.addLargeControls();
	}
	
	public void renderMap(JavaScriptObject jsObj) {
		double lat = Double.parseDouble(JavaScriptObjectHelper.getAttribute(
				jsObj, "lat"));
		double lon = Double.parseDouble(JavaScriptObjectHelper.getAttribute(
				jsObj, "lon"));
		String izena = JavaScriptObjectHelper.getAttribute(jsObj, "izena");
		String helbidea = JavaScriptObjectHelper.getAttribute(jsObj, "helbidea");
		
		markaGehitu(izena, helbidea, lat, lon);
		finkatu(lat, lon);
		// addListenerToMarker(m.toGoogle());
	}
	
	public void finkatu(GuneInfo gunea) {
		finkatu(gunea.getLat(), gunea.getLon());
	}
	
	public void finkatu(double lat, double lon) {
		ExtElement map = ExtElement.get("mapPanel");
		map.mask("Mapa eguneratzen. Itxaron mesedez.", true);
		
		LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
		mapPanel.setCenterAndZoom(latLonPoint, 17);
		
		map.unmask();
	}
	
	public void markaGehitu(GuneInfo gunea){
		markaGehitu(gunea.getIzena(), gunea.getHelbidea(), gunea.getLat(), gunea.getLon());
	}
	
	public void markaGehitu(String izena, String helbidea, double lat,
			double lon) {
//		ExtElement map = ExtElement.get("mapPanel");
//		map.mask("Mapa eguneratzen. Itxaron mesedez.", true);
		
		LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
		Marker m = new Marker(latLonPoint);
		mapPanel.setCenterAndZoom(latLonPoint, 17);
		m.setInfoBubble("<h1>" + izena + "</h1><br><b>Helbidea:</b> " + helbidea);
		mapPanel.addMarker(m);
		
//		map.unmask();
	}
}
