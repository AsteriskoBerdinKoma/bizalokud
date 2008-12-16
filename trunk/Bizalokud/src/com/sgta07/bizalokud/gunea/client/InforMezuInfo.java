package com.sgta07.bizalokud.gunea.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InforMezuInfo implements IsSerializable{

	private int id;
	private Date data;
	private String mezua;
	
	public InforMezuInfo() {
	}

	public InforMezuInfo(int id, Date data, String mezua) {
		super();
		this.id = id;
		this.data = data;
		this.mezua = mezua;
	}

	public int getId() {
		return id;
	}

	public Date getData() {
		return data;
	}

	public String getMezua() {
		return mezua;
	}
	
}
