package com.sgta07.bizalokud.gunea.client;

import com.gwtext.client.widgets.Panel;

public abstract class BarnePanela extends Panel{

	Gunea jabea;
	
	public BarnePanela(Gunea owner) {
		super();
		this.jabea = owner;
	}
	
	public abstract void datuakEguneratu();
	
	public abstract void datuakReseteatu();
	
	
}
