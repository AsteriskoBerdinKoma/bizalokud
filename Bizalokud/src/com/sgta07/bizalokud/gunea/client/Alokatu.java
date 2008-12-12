package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.core.Function;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.Record;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
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

public class Alokatu extends BarnePanela {
	private Panel first;
	private Panel second;
	private Panel errorePanel;

	private Label alokatuaLabel;

	private MapPanel mapPanel;

	private GuneenLista guneak;

	private Toolbar toolbar;
	private ToolbarButton alokatuButton;

	private ExtElement element;

	private boolean sortua = false;
	
	private Label erroreLabel;
	
	HashMap<Integer, Marker> markak;

	public Alokatu(Gunea owner) {
		super(owner);

		markak = new HashMap<Integer, Marker>();
		
		setTitle("Bizikleta Alokatu");
		setLayout(new CardLayout());
		setBorder(false);
		setCollapsible(false);

		toolbar = new Toolbar();
		toolbar.addFill();
		setBottomToolbar(toolbar);

		sortuPanel1();
		sortuPanel2();
		createErrorePanel();

		ButtonListenerAdapter listener = new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				String btnID = button.getId();
				CardLayout cardLayout = (CardLayout) getLayout();
				String panelID = cardLayout.getActiveItem().getId();

				if (btnID.equals("alokatu")) {
					if (panelID.equals("firstPanel")) {
						final ExtElement element = new ExtElement(getElement());
						element
								.mask("Alokairua egiten. Itxaron mesedez.",
										true);

						GuneaService.Util.getInstance().alokatu(
								jabea.getGuneId(),
								guneak.getSelectionModel().getSelected()
										.getAsInteger("id"),
								jabea.getErabNan(),
								new AsyncCallback<BizikletaInfo>() {
									public void onFailure(final Throwable caught) {
										caught.printStackTrace();

										CardLayout cardLayout = (CardLayout) getLayout();
										cardLayout.setActiveItem(0);
										toolbar.setVisible(true);
										element.unmask();

										MessageBox.show(new MessageBoxConfig() {
											{
												setTitle("Bizikleta Alokairua");
												setMsg(caught.getMessage());
												setButtons(MessageBox.OK);
												setIconCls(MessageBox.ERROR);
											}
										});
									}

									public void onSuccess(BizikletaInfo result) {
										CardLayout cardLayout = (CardLayout) getLayout();
										String html = "<p style=\"font-size:x-large;\" align=\"center\">Alokairua ondo bete da</p><br><br>"
												+ "<p style=\"font-size:medium;\"><b>Esleitu zaizun bizikleta zenbakia:</b>"
												+ result.getId()
												+ "<br /><br />"
												+ "<b>Bizikletaren modeloa:</b>"
												+ result.getModeloa()
												+ "<br />"
												+ "<b>Bizikletaren kolorea:</b>"
												+ result.getKolorea() + "</p>";
										alokatuaLabel.setHtml(html);
										cardLayout.setActiveItem(1);
										element.unmask();
									}
								});
					}
				}
			}
		};

		alokatuButton = new ToolbarButton("Alokatu", listener);
		alokatuButton.setId("alokatu");
		toolbar.addButton(alokatuButton);

		add(first);
		add(second);
		add(errorePanel);

		setActiveItem(0);

		sortua = true;

		this.addListener(new ComponentListenerAdapter() {

			public void onHide(Component component) {
				if (sortua)
					datuakReseteatu();
			}

			public void onShow(Component component) {
				element = new ExtElement(getElement());
				element.mask("Itxaron mesedez", true);
				datuakEguneratu();
			}

		});
	}

	private void sortuPanel1() {
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
				markaGehitu(record.getAsInteger("id"), record.getAsString("izena"), record
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
		first.add(mapPanel, new BorderLayoutData(RegionPosition.CENTER));
		first.add(new Label("Aukeratu zein gunera joan nahi duzun"),
				new BorderLayoutData(RegionPosition.NORTH));
	}

	private void sortuPanel2() {
		second = new Panel();
		second.setLayout(new BorderLayout());
		second.setBorder(false);
		second.setId("secondPanel");
		alokatuaLabel = new Label();
		second.add(alokatuaLabel, new BorderLayoutData(RegionPosition.CENTER));
	}

	private void createErrorePanel() {
		errorePanel = new Panel();
		errorePanel.setLayout(new BorderLayout());
		errorePanel.setBorder(false);
		errorePanel.setId("errorePanel");
		erroreLabel = new Label();
		erroreLabel.setStyle("background: white;");
		errorePanel.add(erroreLabel, new BorderLayoutData(RegionPosition.CENTER));
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

			private native void doAddEventListener(String event,
					OneArgFunction listener) /*-{
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
		mapPanel.setId("mapPanel");
	}

	public void renderMap(JavaScriptObject jsObj) {
		double lat = Double.parseDouble(JavaScriptObjectHelper.getAttribute(
				jsObj, "lat"));
		double lon = Double.parseDouble(JavaScriptObjectHelper.getAttribute(
				jsObj, "lon"));
//		String izena = JavaScriptObjectHelper.getAttribute(jsObj, "izena");
//		String helbidea = JavaScriptObjectHelper
//				.getAttribute(jsObj, "helbidea");

		//markaGehitu(izena, helbidea, lat, lon);
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

	public void markaGehitu(GuneInfo gunea) {
		markaGehitu(gunea.getId(), gunea.getIzena(), gunea.getHelbidea(), gunea.getLat(),
				gunea.getLon());
	}

	public void markaGehitu(int id, String izena, String helbidea, double lat,
			double lon) {
		ExtElement map = ExtElement.get("mapPanel");
		if (map != null)
			map.mask("Mapa eguneratzen. Itxaron mesedez.", true);

		LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
		Marker m = new Marker(latLonPoint);
		mapPanel.setCenterAndZoom(latLonPoint, 17);
		m.setInfoBubble("<h1>" + izena + "</h1><br><b>Helbidea:</b> "
				+ helbidea);
		mapPanel.addMarker(m);

		markak.put(id, m);
		
		if (map != null)
			map.unmask();
	}
	
	public void markakKendu(){
		for(int id: markak.keySet()){
			mapPanel.removeMarker(markak.get(id));
			markak.remove(id);
		}
	}

	public void datuakEguneratu() {
		element = new ExtElement(getElement());
		element.mask("Guneen informazioa jasotzen. Itxaron mesedez", true);
		if (sortua && jabea.isGuneaIdentif()) {
//			markakKendu();
			CardLayout cardLayout = (CardLayout) getLayout();
			if (cardLayout.getActiveItem() != first) {
				setActiveItem(0);
				toolbar.setVisible(true);
			}
			if(jabea.isErabIdentif()){
				GuneaService.Util.getInstance().erabiltzaileaAlokatuDu(jabea.getErabNan(), new AsyncCallback<Boolean>(){

					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						caught.printStackTrace();
						datuakReseteatu();
						erroreLabel.setHtml("<p style=\"vertical-align: middle;\" align=\"center\"><b>Momentu honetan ezin dira bizikletak alokatu. Saiatu beranduago mesedez.</b></p>");
						setActiveItem(2);
						toolbar.setVisible(false);
						element.unmask();
					}

					public void onSuccess(Boolean result) {
						if (!result) {
							datuakReseteatu();
							erroreLabel.setHtml("<p style=\"vertical-align: middle;\" align=\"center\"><b>Momentu honetan bizikleta bat alokatuta daukazu. Bizikleta hori itzuli behar duzu beste alokairu bat egin baino lehen.</b></p>");
							setActiveItem(2);
							toolbar.setVisible(false);
						}
						element.unmask();
					}});
			}
			GuneaService.Util.getInstance().alokaDaiteke(jabea.getGuneId(),
					new AsyncCallback<Boolean>() {
						public void onFailure(final Throwable caught) {
							System.out.println(caught.getMessage());
							caught.printStackTrace();
							datuakReseteatu();
							erroreLabel.setHtml("<p style=\"vertical-align: middle;\" align=\"center\"><b>Momentu honetan ezin dira bizikletak alokatu. Saiatu beranduago mesedez.</b></p>");
							setActiveItem(2);
							toolbar.setVisible(false);
							element.unmask();
						}

						public void onSuccess(Boolean result) {
							if (!result) {
								datuakReseteatu();
								erroreLabel.setHtml("<p style=\"vertical-align: middle;\" align=\"center\"><b>Momentu honetan ezin dira bizikletak alokatu. Saiatu beranduago mesedez.</b></p>");
								setActiveItem(2);
								toolbar.setVisible(false);
							}
							element.unmask();
						}
					});

			GuneaService.Util.getInstance().getHelburuGunePosibleak(
					jabea.getGuneId(),
					new AsyncCallback<HashMap<Integer, GuneInfo>>() {
						public void onFailure(Throwable caught) {
							caught.getMessage();
							caught.printStackTrace();
							guneak.setGuneak(new Object[0][]);
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
							guneak.getSelectionModel().selectFirstRow();

							element.unmask();
						}

					});
		} else
			element.unmask();
	}

	public void datuakReseteatu() {
		if (sortua) {
			element = new ExtElement(getElement());
			element.mask("Guneen informazioa jasotzen. Itxaron mesedez", true);
			guneak.setGuneak(new Object[0][]);
//			markakKendu();
			setActiveItem(0);
			toolbar.setVisible(true);
		}
	}
}
