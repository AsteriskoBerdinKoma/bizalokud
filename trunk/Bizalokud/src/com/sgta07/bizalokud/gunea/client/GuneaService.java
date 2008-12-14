package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("GuneaService")
public interface GuneaService extends RemoteService {
	public boolean alokaDaiteke(int guneId) throws Salbuespena;

	public boolean helburuaAukeraDaiteke(int unekoGuneId, int helburuGuneId)
			throws Salbuespena;

	public HashMap<Integer, GuneInfo> guneenZerrenda() throws Salbuespena;

	public HashMap<Integer, GuneInfo> getHelburuGunePosibleak(int unekoGuneId) throws Salbuespena;
	
	public AlokairuInfo alokatu(int unekoGuneId, int helburuGuneId, String erabNan)
			throws Salbuespena;

	public GuneInfo getMyInfo() throws Salbuespena;
	
	public HashMap<Integer,AbisuInfo> getAbisuenZerrenda(String userNan) throws Salbuespena;

	public boolean erabiltzaileaAlokatuDu(String erabNan) throws Salbuespena;
	
	public boolean pasahitzaBerritu(String userNan, String zaharra, String berria) throws Salbuespena;
	
	public AlokairuInfo getAzkenAlokairuInfo(String erabNan) throws Salbuespena;
	
	public AlokairuInfo bizikletaBueltatu(String erabNan) throws Salbuespena;
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GuneaServiceAsync instance;

		public static GuneaServiceAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(GuneaService.class);
			}
			return instance;
		}
	}
}
