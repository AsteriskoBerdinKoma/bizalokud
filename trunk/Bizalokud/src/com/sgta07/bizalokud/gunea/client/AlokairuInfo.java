package com.sgta07.bizalokud.gunea.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AlokairuInfo implements IsSerializable{

	private int id;
	private Date hasieraData;
	private Date bukaeraData;
	private String erabNan;
	private BizikletaInfo bizikleta;
	private GuneInfo hasGune;
	private GuneInfo helGune;
	private boolean bukatuta;
	
	public AlokairuInfo() {
	}

	public AlokairuInfo(int id, Date hasieraData, Date bukaeraData,
			String erabNan, BizikletaInfo bizikleta, GuneInfo hasGune,
			GuneInfo helGune, boolean bukatuta) {
		super();
		this.id = id;
		this.hasieraData = hasieraData;
		this.bukaeraData = bukaeraData;
		this.erabNan = erabNan;
		this.bizikleta = bizikleta;
		this.hasGune = hasGune;
		this.helGune = helGune;
		this.bukatuta = bukatuta;
	}

	public int getId() {
		return id;
	}

	public Date getHasieraData() {
		return hasieraData;
	}

	public Date getBukaeraData() {
		return bukaeraData;
	}

	public String getErabNan() {
		return erabNan;
	}

	public BizikletaInfo getBizikleta() {
		return bizikleta;
	}

	public GuneInfo getHasGune() {
		return hasGune;
	}

	public GuneInfo getHelGune() {
		return helGune;
	}

	public boolean isBukatuta() {
		return bukatuta;
	}
	
	
}
