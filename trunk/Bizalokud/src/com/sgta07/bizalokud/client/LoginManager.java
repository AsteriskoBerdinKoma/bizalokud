package com.sgta07.bizalokud.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LoginManager implements EntryPoint {
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setStyleName("gwt-RootPanel");

		final VerticalPanel verticalPanel_1 = new VerticalPanel();
		rootPanel.add(verticalPanel_1);
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_1.setSize("100%", "100%");

		final VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel_1.add(verticalPanel);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		final Label bizikletenAlokairuenKudeaketaLabel = new Label("Bizikleten alokairuen kudeaketa sisteman sartu");
		verticalPanel.add(bizikletenAlokairuenKudeaketaLabel);
		bizikletenAlokairuenKudeaketaLabel.setStyleName("gwt-RootPanel");
		bizikletenAlokairuenKudeaketaLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		final Login login = new Login();
		verticalPanel.add(login);
	}
}
