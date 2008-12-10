package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Froga extends Composite {

	public Froga() {

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		horizontalPanel.add(absolutePanel);

		final Button button = new Button();
		absolutePanel.add(button);
		button.setText("New Button");

		final AbsolutePanel absolutePanel_1 = new AbsolutePanel();
		horizontalPanel.add(absolutePanel_1);

		final Button button_1 = new Button();
		absolutePanel_1.add(button_1);
		button_1.setText("New Button");
	}

}
