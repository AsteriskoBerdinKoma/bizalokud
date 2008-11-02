package com.sgta07.bizalokud.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Login extends Composite {

	public Login() {

		final VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setStyleName("LoginPanel");

		final FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setSize("80%", "100%");

		final Label errorLabel = new Label("");
		errorLabel.setVisible(false);
		flexTable.setWidget(1, 0, errorLabel);
		errorLabel.setStyleName("ErrorLabel");

		final Label erabiltzaileaLabel = new Label("Erabiltzailea:");
		flexTable.setWidget(2, 0, erabiltzaileaLabel);
		erabiltzaileaLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setWidth(2, 0, "56px");

		final TextBox textBoxErabiltzailea = new TextBox();
		flexTable.setWidget(2, 1, textBoxErabiltzailea);
		flexTable.getCellFormatter().setWidth(2, 1, "130px");
		flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
		textBoxErabiltzailea.setWidth("100%");

		final Label pasahitzaLabel = new Label("Pasahitza:");
		flexTable.setWidget(3, 0, pasahitzaLabel);
		pasahitzaLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setWidth(3, 0, "75px");
		flexTable.getCellFormatter().setWidth(3, 1, "144px");
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.getCellFormatter().setWidth(0, 0, "231px");

		final PasswordTextBox textBoxPasahitza = new PasswordTextBox();
		flexTable.setWidget(3, 1, textBoxPasahitza);
		flexTable.getCellFormatter().setWidth(1, 0, "131px");
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 4);
		
		final Button sartuButton = new Button();
		flexTable.setWidget(4, 1, sartuButton);
		flexTable.getCellFormatter().setWidth(4, 1, "35px");
		flexTable.getCellFormatter().setHeight(4, 1, "12px");
		flexTable.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(5, 0, 4);
		flexTable.getCellFormatter().setWidth(5, 0, "80px");
		sartuButton.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				if(textBoxErabiltzailea.getText() == "" || textBoxPasahitza.getText() == ""){
					errorLabel.setText("Erabiltzaile izena edo pasahitza ez duzu sartu");
					errorLabel.setVisible(true);
				}else{
					errorLabel.setText("");
					errorLabel.setVisible(false);
					LoginService.Util.getInstance().login(textBoxErabiltzailea.getText(), textBoxPasahitza.getText(), new AsyncCallback<Boolean>(){

						public void onFailure(Throwable caught) {
							errorLabel.setText("Errorea komunikazioan");
							errorLabel.setVisible(true);
						}

						public void onSuccess(Boolean result) {
							if(result.booleanValue())
								errorLabel.setText("Erabiltzaile eta pasahitz zuzenak, sartu zaitezke!!");
							else
								errorLabel.setText("Erabiltzaile eta pasahitz okerrak, ezin zara sartu");
							errorLabel.setVisible(true);
						}});
				}
			}
		});
		flexTable.getCellFormatter().setHeight(5, 0, "19px");
		sartuButton.setText("Sartu");
	}

}
