package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sgta07.bizalokud.login.client.EzBaliozkoSaioaException;

@RemoteServiceRelativePath("GuneaService")
public interface GuneaService extends RemoteService {
	public boolean alokaDaiteke(int guneId) throws Salbuespena;

	public boolean helburuaAukeraDaiteke(int unekoGuneId, int helburuGuneId)
			throws Salbuespena;

	public HashMap<Integer, GuneInfo> guneenZerrenda() throws Salbuespena;

	public HashMap<Integer, GuneInfo> getHelburuGunePosibleak(int unekoGuneId) throws Salbuespena;
	
	public AlokairuInfo alokatu(int unekoGuneId, int helburuGuneId, String erabNan)
			throws Salbuespena, EzBaliozkoSaioaException;

	public GuneInfo getMyInfo() throws Salbuespena;
	
	public HashMap<Integer,AbisuInfo> getAbisuenZerrenda(String userNan) throws Salbuespena, EzBaliozkoSaioaException;

	public boolean erabiltzaileaAlokatuDu(String erabNan) throws Salbuespena, EzBaliozkoSaioaException;
	
	public boolean pasahitzaBerritu(String userNan, String zaharra, String berria) throws Salbuespena, EzBaliozkoSaioaException;
	
	public AlokairuInfo getAzkenAlokairuInfo(String erabNan) throws Salbuespena, EzBaliozkoSaioaException;
	
	public AlokairuInfo bizikletaBueltatu(String erabNan) throws Salbuespena, EzBaliozkoSaioaException;
	
	public boolean abisuaIrakurriDa(String erabNan, int id) throws Salbuespena, EzBaliozkoSaioaException;
	
	public DatuEstatistiko nireIbilbideakLortu(String erabNan) throws Salbuespena, EzBaliozkoSaioaException;
	
	public HashMap<Integer, InforMezuInfo> getInforMezuInfo() throws Salbuespena;
	
	public boolean erabDatuakEguneratu(HashMap<String,String> datuak, String erabNan) throws Salbuespena, EzBaliozkoSaioaException;
	
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
