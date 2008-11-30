package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Froga extends Composite {

	public Froga() {

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);

		final Button button = new Button();
		horizontalPanel.add(button);
		button.setText("New Button");

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		horizontalPanel.add(absolutePanel);
	}

}
