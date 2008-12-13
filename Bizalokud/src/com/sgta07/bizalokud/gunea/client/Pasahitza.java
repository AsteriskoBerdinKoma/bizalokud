package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.FitLayout;

public class Pasahitza extends BarnePanela {

	private String userNan;
	private final Panel panel;
	private TextField pZaharra;
	private TextField pBerria1;
	private TextField pBerria2;

	public Pasahitza(Gunea owner) {
		super(owner);
		userNan = "";
		panel = this;

		panel.setTitle("Pasahitz aldaketa");
		panel.setLayout(new FitLayout());
		panel.setBorder(false);
		panel.setAutoScroll(true);
		panel.setCollapsible(false);

		// Formularioa sortu
		FormPanel formPanel = new FormPanel();
		formPanel.setHeight(500);
		formPanel.setWidth(800);
		formPanel.setLabelWidth(150);


		// FieldSetera eremuak gehitu
		pZaharra = new TextField("Pasahitz zaharra", "zaharra", 210);
		pZaharra.setPassword(true);
		pZaharra.setAllowBlank(false);
		pZaharra.setBlankText("Pasahitz zaharra idatzi behar duzu!");
		formPanel.add(pZaharra);

		pBerria1 = new TextField("Pasahitz berria", "berria1", 210);
		pBerria1.setPassword(true);
		pBerria1.setAllowBlank(false);
		pBerria1.setBlankText("Pasahitz berria idatzi behar duzu!");
		pBerria1.setMinLength(6);
		pBerria1.setMinLengthText("Pasahitzak gutxienez 6 karaktere eduki behar ditu");
		
		formPanel.add(pBerria1);

		pBerria2 = new TextField("Errepikatu pasahitz berria", "berria2", 210);
		pBerria2.setPassword(true);
		pBerria2.setAllowBlank(false);
		pBerria2.setBlankText("Pasahitz berria idatzi behar duzu!");
		pBerria2.setMinLength(6);
		pBerria2.setMinLengthText("Pasahitzak gutxienez 6 karaktere eduki behar ditu");
		pBerria2.setRegex(pBerria1.getText().trim());
		formPanel.add(pBerria2);
		
		Button gordeButton = new Button("Gorde");
		Button utziButton = new Button("Utzi");
		
		gordeButton.addListener(new ButtonListenerAdapter(){
			public void onClick(Button button, EventObject e) {
				pZaharra.validate();
				pBerria1.validate();
				pBerria2.validate();
				String zaharra = pZaharra.getText().trim();
				String berria = pBerria1.getText().trim();
				String nan = jabea.getErabNan();
				GuneaService.Util.getInstance().pasahitzaBerritu(nan, getSha1(zaharra), getSha1(berria), new AsyncCallback<Boolean>(){

					public void onFailure(final Throwable caught) {
						caught.printStackTrace();
						MessageBox.show(new MessageBoxConfig() {
							{
								setTitle("Errorea zerbitzaria atzitzean");
								setMsg(caught.getMessage());
								setButtons(MessageBox.OK);
								setIconCls(MessageBox.ERROR);
							}
						});
					}

					public void onSuccess(Boolean result) {
						//Pasahitz zaharra gaizki edo NAN ez baliozkoa
						if (!result){
							MessageBox.show(new MessageBoxConfig() {
								{
									setTitle("Errorea pasahitza eguneratzean");
									setMsg("Emandako datuak ez dira zuzenak");
									setButtons(MessageBox.OK);
									setIconCls(MessageBox.ERROR);
								}
							});
						} else {
							MessageBox.show(new MessageBoxConfig() {
								{
									setTitle("Pasahitza eguneraketa");
									setMsg("Zure pasahitza ondo eguneratu da");
									setButtons(MessageBox.OK);
									setIconCls(MessageBox.INFO);
								}
							});
						}
						
					}
					
				});
				
			}	
		});
		
		utziButton.addListener(new ButtonListenerAdapter(){
			public void onClick(Button button, EventObject e) {
				jabea.getCenterPanelCardLayout();
			}
		});
		
		formPanel.add(gordeButton);
		formPanel.add(utziButton);

		panel.add(formPanel);
	}

	public void datuakEguneratu() {
		// TODO Auto-generated method stub

	}

	public void datuakReseteatu() {
		// TODO Auto-generated method stub

	}
	
	// SHA1 hasha sortuko duen javascript funtzioari (js/crypto/sha1.js)
	// deintzen dio.
	public native String getSha1(String str)/*-{
								return $wnd.SHA1(str);
							}-*/;

}
