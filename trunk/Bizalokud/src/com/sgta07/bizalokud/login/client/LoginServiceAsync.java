package com.sgta07.bizalokud.login.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	
	public void login(String user, String pass, AsyncCallback<LoginInfo> callback);
}
