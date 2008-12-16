package com.sgta07.bizalokud.login.client;

public interface Logeable {

	public void setErabiltzaileDatuak(String nan, String izena, String abizenak, String eposta, String telefonoa, boolean adminDa);

	public void saioaAmaitutzatEman(boolean saioaAmaituta);
}
