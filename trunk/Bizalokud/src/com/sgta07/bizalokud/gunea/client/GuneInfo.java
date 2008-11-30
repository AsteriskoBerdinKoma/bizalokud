package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GuneInfo implements IsSerializable{

	private int id;
	private String helbidea;
	
	public GuneInfo() {
	}

	public GuneInfo(int id, String helbidea) {
		this.id = id;
		this.helbidea = helbidea;
	}

	public int getId() {
		return id;
	}

	public String getHelbidea() {
		return helbidea;
	}

	
}
