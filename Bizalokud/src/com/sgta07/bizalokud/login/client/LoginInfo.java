package com.sgta07.bizalokud.login.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginInfo implements IsSerializable{
	
	private boolean baimena;
	private String erroreMezua;
	
	public LoginInfo(){}
	
	public LoginInfo(boolean baimena) {
		this.baimena = baimena;
	}

	public LoginInfo(String erroreMezua) {
		this.baimena = false;
		this.erroreMezua = erroreMezua;
	}

	public boolean isBaimena() {
		return baimena;
	}

	public String getErroreMezua() {
		return erroreMezua;
	}
}
