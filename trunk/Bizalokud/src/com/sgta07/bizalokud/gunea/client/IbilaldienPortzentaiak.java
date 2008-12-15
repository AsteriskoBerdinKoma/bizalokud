package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class IbilaldienPortzentaiak implements IsSerializable{
	private int hasierakoGuneId;
	private int helburuGuneId;
	private String hasierakoGuneIzena;
	private String helburuGuneIzena;
	private int egindakoAldiKop;
	private double portzentaia; 


	public IbilaldienPortzentaiak() {

	}


	public IbilaldienPortzentaiak(int hasierakoGuneId, int helburuGuneId,
			String hasierakoGuneIzena, String helburuGuneIzena,
			int egindakoAldiKop, double portzentaia) {
		super();
		this.hasierakoGuneId = hasierakoGuneId;
		this.helburuGuneId = helburuGuneId;
		this.hasierakoGuneIzena = hasierakoGuneIzena;
		this.helburuGuneIzena = helburuGuneIzena;
		this.egindakoAldiKop = egindakoAldiKop;
		this.portzentaia = portzentaia;
	}


	public int getHasierakoGuneId() {
		return hasierakoGuneId;
	}


	public void setHasierakoGuneId(int hasierakoGuneId) {
		this.hasierakoGuneId = hasierakoGuneId;
	}


	public int getHelburuGuneId() {
		return helburuGuneId;
	}


	public void setHelburuGuneId(int helburuGuneId) {
		this.helburuGuneId = helburuGuneId;
	}


	public String getHasierakoGuneIzena() {
		return hasierakoGuneIzena;
	}


	public void setHasierakoGuneIzena(String hasierakoGuneIzena) {
		this.hasierakoGuneIzena = hasierakoGuneIzena;
	}


	public String getHelburuGuneIzena() {
		return helburuGuneIzena;
	}


	public void setHelburuGuneIzena(String helburuGuneIzena) {
		this.helburuGuneIzena = helburuGuneIzena;
	}


	public int getEgindakoAldiKop() {
		return egindakoAldiKop;
	}


	public void setEgindakoAldiKop(int egindakoAldiKop) {
		this.egindakoAldiKop = egindakoAldiKop;
	}


	public double getPortzentaia() {
		return portzentaia;
	}


	public void setPortzentaia(double portzentaia) {
		this.portzentaia = portzentaia;
	}
	
	public void setPortzentaia(int alokairuKop) {
		this.portzentaia = (double)egindakoAldiKop / (double)alokairuKop;
	}

	
}