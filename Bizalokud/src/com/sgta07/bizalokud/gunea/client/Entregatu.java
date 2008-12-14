package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.RowLayout;
import com.gwtext.client.widgets.layout.RowLayoutData;

public class Entregatu extends BarnePanela {

	private ExtElement element;
	private Panel panel1;
	private Label alokInfoLabel;
	private ToolbarButton entregatuButton;
	private Toolbar toolbar;

	public Entregatu(Gunea owner) {
		super(owner);
		
		sortuPanela();
		
		this.setTitle("Bizikleta entregatu");
		this.setLayout(new FitLayout());
		this.setBorder(false);
		this.setAutoScroll(true);
		this.setCollapsible(false);
		
		toolbar = new Toolbar();
		toolbar.addFill();
		setBottomToolbar(toolbar);
		
		entregatuButton = new ToolbarButton("Bizikleta entregatu",
				new ButtonListenerAdapter() {
					public void onClick(Button button, EventObject e) {
						element = new ExtElement(getElement());
						element.mask("Alokairua egiten. Itxaron mesedez.", true);
						GuneaService.Util.getInstance().bizikletaBueltatu(
								jabea.getErabNan(),
								new AsyncCallback<AlokairuInfo>() {

									public void onFailure(Throwable caught) {
										System.out.println(caught.getMessage());
										caught.printStackTrace();
										datuakReseteatu();
										alokInfoLabel
												.setHtml("<p style=\"font-size:x-large;\">Alokairuari ezin izan zaio amaiera eman, mesedez saiatu berriro beranduago</p><br><br>");
										toolbar.setVisible(false);
										element.unmask();
									}

									public void onSuccess(AlokairuInfo result) {
										String html = "";
										if (result != null) {
											if (result.isBukatuta()) {
												DateTimeFormat dtf = new DateTimeFormat(
														"yyyy/MM/dd HH:mm") {
												};
												String hasData = dtf
														.format(result
																.getHasieraData());
												String bukData = dtf
														.format(result
																.getBukaeraData());
												html = "<p style=\"font-size:x-large;\">Zure alokairua bukatu da</p><br><br>"
														+ "<p style=\"font-size:medium;\">"
														+ hasData
														+ " datan "
														+ result.getHasGune()
																.getIzena()
														+ " guenan bizikleta bat hartu zenuen eta "
														+ bukData
														+ " datan "
														+ result.getHelGune()
																.getIzena()
														+ " gunean entregatu duzu.</p>";
											} else
												html = "<p style=\"font-size:x-large;\">Alokairuari ezin izan zaio amaiera eman, mesedez saiatu berriro beranduago</p><br><br>";
										} else
											html = "<p style=\"font-size:x-large;\">Zure alokairua bukatu da</p><br><br>";
										alokInfoLabel.setHtml(html);
										toolbar.setVisible(false);
										element.unmask();
									}
								});
					}
				});
		entregatuButton.setId("entregatu");
		toolbar.addButton(entregatuButton);
		
		this.add(panel1);
		
		this.addListener(new ComponentListenerAdapter() {
			public void onShow(Component component) {
				element = new ExtElement(getElement());
				element.mask("Itxaron mesedez", true);
				datuakEguneratu();
			}
		});
	}

	public void sortuPanela() {
		panel1 = new Panel();
		panel1.setLayout(new RowLayout());
		panel1.setBorder(false);
		panel1.setId("panela");
		alokInfoLabel = new Label();
		alokInfoLabel.setStyle("background: white;");
		panel1.add(alokInfoLabel, new RowLayoutData());
	}

	public void datuakEguneratu() {
		if (jabea.isErabIdentif() && jabea.isGuneaIdentif()) {
			GuneaService.Util.getInstance().getAzkenAlokairuInfo(jabea.getErabNan(),
					new AsyncCallback<AlokairuInfo>() {

						public void onFailure(Throwable caught) {
							System.out.println(caught.getMessage());
							caught.printStackTrace();
							datuakReseteatu();
							alokInfoLabel
									.setHtml("<p style=\"font-size:medium;\" align=\"center\"><b>Momentu honetan ezin dira bizikletak entregatu. Mesedez saiatu berriz beranduago.</b></p>");
							toolbar.setVisible(false);
							element.unmask();
						}

						public void onSuccess(AlokairuInfo result) {
							String html = "";
							if (result != null) {
								DateTimeFormat dtf = new DateTimeFormat(
										"yyyy/MM/dd HH:mm") {
								};
								String hasData = dtf.format(result
										.getHasieraData());
								html = "<p style=\"font-size:x-large;\" align=\"center\">Zure uneko alokairuaren informazioa</p><br><br>"
										+ "<p style=\"font-size:medium;\">Hasiera gunea: "
										+ result.getHasGune().getIzena()
										+ " - "
										+ result.getHasGune().getHelbidea()
										+ "<br>"
										+ "Hasiera data: "
										+ hasData
										+ "<br>"
										+ "Bizikleta modeloa: "
										+ result.getBizikleta().getModeloa()
										+ "<br>"
										+ "Bizikleta kolorea: "
										+ result.getBizikleta().getKolorea()
										+ "</p><br>";
								if (result.getHelGune().getId() == jabea
										.getGuneId()) {
									html += "<p style=\"font-size:medium;\">Bizikleta orain entregatu nahi baduzu utzi ezazu bere lekuan eta sakatu tresna barrako \"Bizikleta entregatu\" botoia.</p>";
									toolbar.setVisible(true);
								} else {
									html += "<p style=\"font-size:medium;\">Alokatuta daukazun bizikleta ezin duzu gune honetan entregatu.<br>Entrega gunea hauxe da: <b>"
											+ result.getHelGune().getIzena()
											+ "</b> - "
											+ result.getHelGune().getHelbidea()
											+ ".</p><br><br>";
									toolbar.setVisible(false);
								}
							} else {
								html = "<p style=\"font-size:medium;\" align=\"center\">Momentu honetan ez daukazu bizikletarik alokatuta.</p><br><br>";
								toolbar.setVisible(false);
							}
							alokInfoLabel.setHtml(html);
							element.unmask();
						}
					});
		} else {
			toolbar.setVisible(false);
			element.unmask();
		}
	}

	public void datuakReseteatu() {

	}

}
