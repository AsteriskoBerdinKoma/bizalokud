package com.sgta07.bizalokud.gunea.client;

import com.gwtext.client.widgets.Panel;

public class Entregatu extends EdukienPanela{

	@Override
	public Panel getViewPanel() {
		if (panel == null) {
            panel = new Panel();
		}
		return panel;
	}

}
