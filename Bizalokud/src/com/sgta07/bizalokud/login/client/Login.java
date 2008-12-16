package com.sgta07.bizalokud.login.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.WaitConfig;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.WindowListenerAdapter;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.VType;
import com.gwtext.client.widgets.form.ValidationException;
import com.gwtext.client.widgets.form.Validator;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Login implements EntryPoint {

	private boolean logeatuta;

	private Logeable jabea;
	
	private TextField erabiltzaileaTextField;
	private TextField pasahitzaTextField;

	public Login() {
		super();
	}
	
	public Login(Logeable owner) {
		super();
		this.jabea = owner;
	}
	
	public void onModuleLoad() {
		logeatuta = false;
	}

	public void erakutsi() {
		final Window window = new Window();
		window.setTitle("Login");
		window.setLayout(new FitLayout());
		window.setMaximizable(false);
		window.setModal(true);

		window.setWidth(320);
		window.setHeight(200);

		window.setBorder(false);
		window.setResizable(false);
		window.setClosable(false);
		window.setPaddings(5);
		window.setButtonAlign(Position.CENTER);

		window.setCloseAction(Window.HIDE);
		
		window.addListener(new WindowListenerAdapter(){
			
			public boolean doBeforeHide(Component component) {
				erabiltzaileaTextField.reset();
				pasahitzaTextField.reset();
				return super.doBeforeHide(component);
			}
		});
		
		window.setPlain(true);

		final FormPanel formPanel = new FormPanel();
		formPanel.setFrame(true);
		formPanel.setLabelWidth(75);

		formPanel.setAutoWidth(true);
		formPanel.setAutoHeight(true);

		FieldSet fieldSet = new FieldSet("Sartu zure NAN eta pasahitza");
		fieldSet.setAutoHeight(true);
		fieldSet.setAutoWidth(true);

		erabiltzaileaTextField = new TextField("NAN", "user",
				150);
		erabiltzaileaTextField.setAllowBlank(false);
		erabiltzaileaTextField.setBlankText("NAN zenbakia sartu behar duzu");
		erabiltzaileaTextField.setValidator(new Validator() {
			public boolean validate(String value) throws ValidationException {
				if (value.length() != 9)
					throw new ValidationException(
							"NANak 8 digitu eta hizki bat eduki behar du.");
				if (!Character.isLetter(value.charAt(8)))
					throw new ValidationException(
							"NANak 8 digitu eta hizki bat eduki behar du.");
				try {
					Integer.parseInt(value.substring(0, 8));
				} catch (NumberFormatException e) {
					throw new ValidationException(
							"NANak 8 digitu eta hizki bat eduki behar du.");
				}
				return true;
			}
		});
//		erabiltzaileaTextField.setValidationEvent(false);

		pasahitzaTextField = new TextField("Pasahitza", "pass",
				150);
		pasahitzaTextField.setPassword(true);
		pasahitzaTextField.setAllowBlank(false);
		pasahitzaTextField.setBlankText("Pasahitza sartu behar duzu.");
		pasahitzaTextField.setVtype(VType.ALPHANUM);
		pasahitzaTextField
				.setVtypeText("Pasahitzak bakarrik hizkiak, zenbakiak eta _ eduki ditzake");
//		pasahitzaTextField.setValidationEvent(false);

		fieldSet.add(erabiltzaileaTextField);
		fieldSet.add(pasahitzaTextField);

		formPanel.add(fieldSet);

		final Button sartuBtn = new Button("Sartu",
				new ButtonListenerAdapter() {
					public void onClick(Button button, EventObject e) {
						if (!erabiltzaileaTextField.getText().isEmpty()
								&& !pasahitzaTextField.getText().isEmpty()) {
							// Bi testu kutxak balidatzeko emaitzak aldagaietan
							// gorde behar dira
							boolean validErab = erabiltzaileaTextField
									.validate();
							boolean validPasa = pasahitzaTextField.validate();
							if (validErab && validPasa) {

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
										getSha1(pasahitzaTextField.getText()),
										new AsyncCallback<LoginInfo>() {

											public void onFailure(
													Throwable caught) {
												MessageBox.hide();
												MessageBox
														.show(new MessageBoxConfig() {
															{
																setTitle("Errorea komunikazioan");
																setMsg("Ezin izan da zerbitzariarekin komunikazioa ezarri.");
																setButtons(MessageBox.OK);
																setIconCls(MessageBox.ERROR);
															}
														});
												logeatuta = false;
											}

											public void onSuccess(
													final LoginInfo result) {
												MessageBox.hide();
												if (result.isBaimena()) {
													window.hide();
													logeatuta = true;
													jabea
															.setErabiltzaileDatuak(
																	result
																			.getNan(),
																	result
																			.getIzena(),
																	result
																			.getAbizenak(),
																	result
																			.getEPosta(),
																	result
																			.getTelefonoa(),
																	result
																			.isAdmin());
												} else {
													MessageBox
															.show(new MessageBoxConfig() {
																{
																	setTitle("Login okerra");
																	setMsg(result
																			.getErroreMezua());
																	setButtons(MessageBox.OK);
																	setIconCls(MessageBox.ERROR);
																}
															});
													logeatuta = false;
												}
											}
										});
							}

						} else {
							MessageBox.show(new MessageBoxConfig() {
								{
									setTitle("Login okerra");
									setMsg("Erabiltzaile izena edo pasahitza ez duzu sartu.");
									setButtons(MessageBox.OK);
									setIconCls(MessageBox.ERROR);
								}
							});
							logeatuta = false;
						}
					}
				});

		formPanel.addButton(sartuBtn);

		window.add(formPanel);

		window.show();
	}

	public boolean isLogeatuta() {
		return logeatuta;
	}

	public void saioaAmaitu() {
		LoginService.Util.getInstance().saioaAmaitu(
				new AsyncCallback<Boolean>() {

					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						jabea.saioaAmaitutzatEman(false);
					}

					public void onSuccess(Boolean result) {
						jabea.saioaAmaitutzatEman(result);
					}
				});
	}

	// SHA1 hasha sortuko duen javascript funtzioari (js/crypto/sha1.js)
	// deintzen dio.
	public native String getSha1(String str)/*-{
			return $wnd.SHA1(str);
		}-*/;
}
