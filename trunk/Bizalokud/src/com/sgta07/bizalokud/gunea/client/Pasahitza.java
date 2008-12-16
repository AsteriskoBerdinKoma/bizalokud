package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.ValidationException;
import com.gwtext.client.widgets.form.Validator;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;

public class Pasahitza extends BarnePanela {

	private final Panel panel;
	private TextField pZaharra;
	private TextField pBerria1;
	private TextField pBerria2;
	private ExtElement element;

	public Pasahitza(Gunea owner) {
		super(owner);
		panel = this;

		panel.setTitle("Pasahitz aldaketa");
		panel.setBorder(false);
		panel.setLayout(new BorderLayout());

		// Formularioa sortu
		FormPanel formPanel = new FormPanel();
		// formPanel.setAutoHeight(true);
		// formPanel.setAutoWidth(true);
		formPanel.setLabelWidth(150);
		formPanel.setPaddings(15);
		formPanel.expand();

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
		pBerria1
				.setMinLengthText("Pasahitzak gutxienez 6 karaktere eduki behar ditu");

		formPanel.add(pBerria1);

		pBerria2 = new TextField("Errepikatu pasahitz berria", "berria2", 210);
		pBerria2.setPassword(true);
		pBerria2.setAllowBlank(false);
		pBerria2.setBlankText("Pasahitz berria idatzi behar duzu!");
		pBerria2.setMinLength(6);
		pBerria2
				.setMinLengthText("Pasahitzak gutxienez 6 karaktere eduki behar ditu");
		pBerria2.setRegex(pBerria1.getText().trim());
		pBerria2.setValidator(new Validator() {
			public boolean validate(String value) throws ValidationException {
				if (!value.equals(pBerria1.getText()))
					return false;
				else
					return true;
			}

		});
		pBerria2.setInvalidText("Bi pasahitz berriak berdinak izan behar dira");
		formPanel.add(pBerria2);

		ToolbarButton gordeButton = new ToolbarButton("Gorde");
		ToolbarButton utziButton = new ToolbarButton("Utzi");

		gordeButton.addListener(new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				if (pZaharra.validate() && pBerria1.validate()
						&& pBerria2.validate()) {
					String zaharra = pZaharra.getText().trim();
					String berria = pBerria1.getText().trim();
					String nan = jabea.getErabNan();
					element = new ExtElement(getElement());
					element.mask("Pasahitza eguneratzen. Itxaron mesedez...",
							true);
					GuneaService.Util.getInstance().pasahitzaBerritu(nan,
							getSha1(zaharra), getSha1(berria),
							new AsyncCallback<Boolean>() {

								public void onFailure(final Throwable caught) {
									caught.printStackTrace();
									element.unmask();
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
									element.unmask();
									// Pasahitz zaharra gaizki edo NAN ez
									// baliozkoa
									if (!result) {
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
												pZaharra.reset();
												pBerria1.reset();
												pBerria2.reset();
												jabea.saioaAmaitutzatEman(true);
											}
										});
									}

								}

							});
				}
			}
		});

		utziButton.addListener(new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				pZaharra.reset();
				pBerria1.reset();
				pBerria2.reset();
				jabea.getCenterPanelCardLayout();
			}
		});

		Toolbar tb = new Toolbar();
		tb.addButton(gordeButton);
		tb.addFill();
		tb.addButton(utziButton);

		panel.add(formPanel, new BorderLayoutData(RegionPosition.CENTER));
		panel.setBottomToolbar(tb);

		this.addListener(new ComponentListenerAdapter() {
			public void onShow(Component component) {
				pZaharra.reset();
				pBerria1.reset();
				pBerria2.reset();
			}
		});
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
