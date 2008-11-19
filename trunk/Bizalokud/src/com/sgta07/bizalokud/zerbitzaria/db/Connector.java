package com.sgta07.bizalokud.zerbitzaria.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connector {

	/**
	 * The jdbc driver used to connect to the database
	 */
	private final String JDBC_DRIVER = ConnectionPref
			.getString("ConnectionPref.JDBC_DRIVER"); //$NON-NLS-1$
	/**
	 * The URL of the server where the database is hosted
	 */
	private final String DATABASE_URL = ConnectionPref
			.getString("ConnectionPref.DATABASE_URL"); //$NON-NLS-1$

	private final String USER = ConnectionPref.getString("ConnectionPref.USER");
	private final String PASS = ConnectionPref.getString("ConnectionPref.PASS");

	private Connection connection;
	private boolean connectedToDatabase = false;

	/**
	 * @throws ClassNotFoundException 
	 * @throws SQLException
	 */
	public void connect() throws ClassNotFoundException, SQLException{
		Class.forName(JDBC_DRIVER);
		this.connection = DriverManager.getConnection(DATABASE_URL, USER, PASS);
		this.connectedToDatabase = true;
	}
	
	public PreparedStatement prepareStatement(String query) throws SQLException{
		return connection.prepareStatement(query);
	}

	// /**
	// * Datu basera konektatzen da emandako erabiltzaile eta pasahitzarekin.
	// *
	// * @param user
	// * konexioa ezartzeko erabiltzaile izena
	// * @param pass
	// * konexioa ezartzeko erabiltzailearen pasahitza
	// * @throws ClassNotFoundException
	// * @throws SQLException
	// */
	// void connect(String user, String pass) throws SQLException,
	// ClassNotFoundException {
	// this.username = user;
	// this.password = pass;
	// this.connect();
	// }

	/**
	 * Datu basera konektatuta dagoen edo ez esaten du.
	 * 
	 * @return Datu basera konexioa badago true itzultzen du, bestela false.
	 */
	public boolean isConnectedToDatabase() {
		return this.connectedToDatabase;
	}

	/**
	 * Datu basera konexioa ixten du.
	 * 
	 * @throws java.sql.SQLException
	 */
	public void close() throws SQLException{
		this.connection.close();
		this.connectedToDatabase = false;
	}
}