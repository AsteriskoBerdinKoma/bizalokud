package com.sgta07.bizalokud.login.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.WaitConfig;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.VType;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Login implements EntryPoint {

	public void onModuleLoad() {
		Panel panel = new Panel();
		panel.setBorder(false);
		panel.setPaddings(15);

		final FormPanel formPanel = new FormPanel(Position.RIGHT);
		formPanel.setFrame(true);
		formPanel.setTitle("Login");
		formPanel.setWidth(300);
		formPanel.setLabelWidth(75);
		formPanel.setBorder(false);

		// add some fields
		FieldSet fieldSet = new FieldSet("Sartu zure erabiltzailea eta pasahitza");
		final TextField erabiltzaileaTextField = new TextField("Erabiltzailea",
				"user", 150);
		erabiltzaileaTextField.setVtype(VType.ALPHANUM);
		erabiltzaileaTextField.setVtypeText("Erabiltzaile izenak bakarrik hizkiak, zenbakiak eta _ eduki ditzake");
		/*erabiltzaileaTextField.setValidator(new Validator() {
			public boolean validate(String value) throws ValidationException {
				System.out.println("balidatzen");
				if (erabiltzaileaTextField.getText().isEmpty())
					throw new ValidationException(
							"Erabiltzaile izena sartu behar duzu");
				else
					return true;
			}
		});*/
		erabiltzaileaTextField.setValidationEvent(false);
		
		final TextField pasahitzaTextField = new TextField("Pasahitza", "pass",
				150);
		pasahitzaTextField.setPassword(true);
		/*pasahitzaTextField.setValidator(new Validator() {
			public boolean validate(String value) throws ValidationException {
				System.out.println("balidatzen");
				if (erabiltzaileaTextField.getText().isEmpty())
					throw new ValidationException("Pasahitza sartu behar duzu");
				else
					return true;
			}
		});*/
		pasahitzaTextField.setVtype(VType.ALPHANUM);
		pasahitzaTextField.setVtypeText("Pasahitzak bakarrik hizkiak, zenbakiak eta _ eduki ditzake");
		pasahitzaTextField.setValidationEvent(false);
		
		fieldSet.add(erabiltzaileaTextField);
		fieldSet.add(pasahitzaTextField);

		formPanel.add(fieldSet);

		final Button sartuBtn = new Button("Sartu",
				new ButtonListenerAdapter() {
					public void onClick(Button button, EventObject e) {
						
						if(erabiltzaileaTextField.validate() &&
						pasahitzaTextField.validate()){
			
						MessageBox.show(new MessageBoxConfig() {
							{
								setTitle("Login");
								setMsg("Login egiten, mesedez itxaron...");
								setProgressText("Login egiten...");
								setWidth(300);
								setWait(true);
								setWaitConfig(new WaitConfig() {
									{
										setInterval(200);
									}
								});
							}
						});
						
						LoginService.Util.getInstance().login(
								erabiltzaileaTextField.getText(),
								pasahitzaTextField.getText(),
								new AsyncCallback<Boolean>() {
			
									public void onFailure(Throwable caught) {
										MessageBox.hide();
										MessageBox.show(new MessageBoxConfig() {
											{
												setTitle("Errorea komunikazioan");
												setMsg("Ezin izan da zerbitzariarekin komunikazioa ezarri.");
												setButtons(MessageBox.OK);
												setIconCls(MessageBox.ERROR);
											}
										});
										System.out
										.println(caught.getMessage());
									}
			
									public void onSuccess(Boolean result) {
										MessageBox.hide();
										if (result.booleanValue())
											System.out
													.println("Sartu zaitezke");
										else
											MessageBox
													.show(new MessageBoxConfig() {
														{
															setTitle("Login okerra");
															setMsg("Erabiltzaile izena edo pasahitza okerra da.");
															setButtons(MessageBox.OK);
															setIconCls(MessageBox.ERROR);
														}
													});
									}
								});
						}
						
					}
				});
		formPanel.addButton(sartuBtn);
		panel.add(formPanel);

		RootPanel.get().add(panel);
	}
}
