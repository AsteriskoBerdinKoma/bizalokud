package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("GuneaService")
public interface GuneaService extends RemoteService {

	public boolean alokaDaiteke(int guneId, String userNan)
			throws Exception;

	public boolean helburuaAukeraDaiteke(int unekoGuneId, int helburuGuneId)
			throws Exception;

	public HashMap<Integer, String> guneenZerrenda(int guneId)
			throws Exception;

	public int alokatu(int unekoGuneId, int helburuGuneId, String erabNan)
			throws Exception;

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