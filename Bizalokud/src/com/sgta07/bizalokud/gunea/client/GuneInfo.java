package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GuneInfo implements IsSerializable {

	private int id;
	private String izena;
	private String helbidea;

	public GuneInfo() {
	}

	public GuneInfo(int id, String izena, String helbidea) {
		this.id = id;
		this.izena = izena;
		this.helbidea = helbidea;
	}

	public int getId() {
		return id;
	}

	public String getHelbidea() {
		return helbidea;
	}

	public String getIzena() {
		return izena;
	}
}
