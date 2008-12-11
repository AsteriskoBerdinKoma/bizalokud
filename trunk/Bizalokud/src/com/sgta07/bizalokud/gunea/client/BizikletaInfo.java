package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BizikletaInfo implements IsSerializable {

	private int id;
	private String modeloa;
	private String kolorea;
	
	public BizikletaInfo(){}

	public BizikletaInfo(int id, String modeloa, String kolorea) {
		this.id = id;
		this.modeloa = modeloa;
		this.kolorea = kolorea;
	}

	public int getId() {
		return id;
	}

	public String getModeloa() {
		return modeloa;
	}

	public String getKolorea() {
		return kolorea;
	}
	
}
