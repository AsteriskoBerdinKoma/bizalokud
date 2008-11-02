package com.sgta07.bizalokud.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	
	public void login(String user, String pass, AsyncCallback<Boolean> callback);
}
