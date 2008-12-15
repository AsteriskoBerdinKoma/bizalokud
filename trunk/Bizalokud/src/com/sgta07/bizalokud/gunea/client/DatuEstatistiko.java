package com.sgta07.bizalokud.gunea.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatuEstatistiko implements IsSerializable {

	private int alokairuKop;
	private String alokairuLuzeenaDenbora;
	private String alokairuLuzeenaEguna;
	private int ibilaldiKop;
	private String egunAktiboenaDenbora;
	private String egunAktiboneaEguna;
	private Vector<IbilaldienPortzentaiak> ibilaldienPortzentaiak;

	public DatuEstatistiko() {
		super();
		this.alokairuKop=0;
		this.alokairuLuzeenaDenbora="00:00:00";
		this.alokairuLuzeenaEguna="Egunik ez";
		this.ibilaldiKop=0;
		this.egunAktiboenaDenbora="00:00:00";
		this.egunAktiboneaEguna="Egunik ez";
		this.ibilaldienPortzentaiak=new Vector<IbilaldienPortzentaiak>();
	}
	

	public DatuEstatistiko(int alokairuKop, String alokairuLuzeenaDenbora,
			String alokairuLuzeenaEguna, int ibilaldiKop,
			String egunAktiboenaDenbora, String egunAktiboneaEguna,
			Vector<IbilaldienPortzentaiak> ibilaldienPortzentaiak) {
		super();
		this.alokairuKop = alokairuKop;
		this.alokairuLuzeenaDenbora = alokairuLuzeenaDenbora;
		this.alokairuLuzeenaEguna = alokairuLuzeenaEguna;
		this.ibilaldiKop = ibilaldiKop;
		this.egunAktiboenaDenbora = egunAktiboenaDenbora;
		this.egunAktiboneaEguna = egunAktiboneaEguna;
		this.ibilaldienPortzentaiak = ibilaldienPortzentaiak;
	}


	public int getAlokairuKop() {
		return alokairuKop;
	}

	public String getAlokairuLuzeenaDenbora() {
		return alokairuLuzeenaDenbora;
	}

	public String getAlokairuLuzeenaEguna() {
		return alokairuLuzeenaEguna;
	}

	public int getIbilaldiKop() {
		return ibilaldiKop;
	}

	public String getEgunAktiboenaDenbora() {
		return egunAktiboenaDenbora;
	}

	public String getEgunAktiboneaEguna() {
		return egunAktiboneaEguna;
	}

	public Vector<IbilaldienPortzentaiak> getIbilaldienPortzentaiak() {
		return ibilaldienPortzentaiak;
	}
	
}
