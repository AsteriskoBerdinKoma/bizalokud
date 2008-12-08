package com.sgta07.bizalokud.gunea.client;

public class Salbuespena extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mezuGenerikoa = "Errorea zerbitzaria atzitzean.";
	private String erroreMezua = "";
	
	public Salbuespena(){
		super();
	}

	public Salbuespena(String message) {
		super(message);
		this.erroreMezua = message;
	}
	
	public Salbuespena(String message, Throwable cause) {
        super(message, cause);
        this.erroreMezua = message;
    }
	
	public Salbuespena(Throwable cause) {
        super(cause);
    }
	
	public String getMessage(){
		return this.erroreMezua;
	}

}
