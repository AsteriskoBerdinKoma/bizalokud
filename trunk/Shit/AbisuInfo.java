package com.sgta07.bizalokud.gunea.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AbisuInfo implements IsSerializable{
	
	private int id;
	private Date data;
	private boolean irakurrita;
	private String mota;
	private String mezua;
	
	public AbisuInfo(){
	}
	
	public AbisuInfo(int id, Date data,
			boolean irakurrita, String mota, String mezua) {
		super();
		this.id = id;
		this.data = data;
		this.irakurrita = irakurrita;
		this.mota = mota;
		this.mezua = mezua;
	}
	public int getId() {
		return id;
	}
	public Date getData() {
		return data;
	}
	public boolean isIrakurrita() {
		return irakurrita;
	}
	public void setIrakurrita(boolean irakurrita) {
		this.irakurrita = irakurrita;
	}
	public String getMota() {
		return mota;
	}
	public String getMezua() {
		return mezua;
	}
	
	
	

}
