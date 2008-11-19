package com.sgta07.bizalokud.zerbitzaria.db;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConnectionPref {
	private static final String BUNDLE_NAME = "com.sgta07.bizalokud.zerbitzaria.resources.properties.ConnectionPref"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private ConnectionPref() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
