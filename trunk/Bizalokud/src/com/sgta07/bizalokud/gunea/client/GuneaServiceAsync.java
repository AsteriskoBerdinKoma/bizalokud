package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GuneaServiceAsync {

	public void alokaDaiteke(int guneId, String userNan, AsyncCallback<Boolean> callback);

	public void helburuaAukeraDaiteke(int unekoGuneId, int helburuGuneId, AsyncCallback<Boolean> callback);

	public void guneenZerrenda(int guneId, AsyncCallback<HashMap<Integer, String>> callback);

	public void alokatu(int unekoGuneId, int helburuGuneId, String erabNan, AsyncCallback<Integer> callback);
}
