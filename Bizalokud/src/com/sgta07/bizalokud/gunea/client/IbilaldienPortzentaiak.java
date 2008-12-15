package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class IbilaldienPortzentaiak implements IsSerializable{
	private int gunehas_id;
	private int gunehel_id;
	private int temp2;
	private double temp3; 
	private String hasIzena;
	private String helIzena;

	public IbilaldienPortzentaiak() {

	}

	public IbilaldienPortzentaiak(int gunehas_id, int gunehel_id,
			int temp2, double temp3, String hasIzena, String helIzena) {
		this.gunehas_id = gunehas_id;
		this.gunehel_id = gunehel_id;
		this.temp2 = temp2;
		this.temp3 = temp3;
		this.hasIzena = hasIzena;
		this.helIzena = helIzena;
	}

	public int getGunehas_id() {
		return gunehas_id;
	}

	public int getGunehel_id() {
		return gunehel_id;
	}

	public int getTemp2() {
		return temp2;
	}

	public double getTemp3() {
		return temp3;
	}

	public String getHasIzena() {
		return hasIzena;
	}

	public String getHelIzena() {
		return helIzena;
	}

	public void setTmp2(int i) {
		this.temp2 = i;
	}

	public void setTmp3(double d) {
		this.temp3 = d;
	}	
}