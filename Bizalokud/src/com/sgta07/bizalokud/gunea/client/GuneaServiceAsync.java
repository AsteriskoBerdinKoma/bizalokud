package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GuneaServiceAsync {
	public void alokaDaiteke(int guneId, AsyncCallback<Boolean> callback);

	public void helburuaAukeraDaiteke(int unekoGuneId, int helburuGuneId, AsyncCallback<Boolean> callback);

	public void guneenZerrenda(AsyncCallback<HashMap<Integer, GuneInfo>> callback);

	public void getHelburuGunePosibleak(int unekoGuneId, AsyncCallback<HashMap<Integer, GuneInfo>> callback);
	
	public void alokatu(int unekoGuneId, int helburuGuneId, String erabNan, AsyncCallback<BizikletaInfo> callback);

	public void getMyInfo(AsyncCallback<GuneInfo> callback);
	
	public void getAbisuenZerrenda(String userNan, AsyncCallback<HashMap<Integer,AbisuInfo>> callback);

	public void erabiltzaileaAlokatuDu(String erabNan, AsyncCallback<Boolean> callback);
	
	public void pasahitzaBerritu(String userNan, String zaharra, String berria, AsyncCallback<Boolean> callback);
}
