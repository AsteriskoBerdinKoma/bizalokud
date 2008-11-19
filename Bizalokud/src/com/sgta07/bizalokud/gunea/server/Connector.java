package com.sgta07.bizalokud.gunea.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author SGTA07
 */
public class Connector {

	/**
	 * The jdbc driver used to connect to the database
	 */
	public static final String JDBC_DRIVER = ConnectionPref
			.getString("ConnectionPref.JDBC_DRIVER"); //$NON-NLS-1$
	/**
	 * The URL of the server where the database is hosted
	 */
	public static final String DATABASE_URL = ConnectionPref
			.getString("ConnectionPref.DATABASE_URL"); //$NON-NLS-1$

	private static final String USERNAME = ConnectionPref
			.getString("ConnectionPref.USER");

	private static final String PASSWORD = ConnectionPref
			.getString("ConnectionPref.PASSWORD");

	private static java.sql.Connection connection;
	private static java.sql.Statement statement;
	private static boolean connectedToDatabase = false;

	/**
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void connect() throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		Connector.connection = DriverManager.getConnection(DATABASE_URL,
				Connector.USERNAME, Connector.PASSWORD);
		Connector.statement = Connector.connection.createStatement();
		Connector.connectedToDatabase = true;
	}

	/**
	 * Datu basera konexioa itzultzen du.
	 * 
	 * @return Datu basera uneko konexioa
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection() throws SQLException,
			ClassNotFoundException {
		if (null == connection || !connectedToDatabase)
			connect();
		return connection;
	}

	/**
	 * Uneko konexioaren statement-a itzultzen du.
	 * 
	 * @return uneko konexioaren statement-a
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Statement getStatement() throws SQLException,
			ClassNotFoundException {
		if (null == statement || !connectedToDatabase)
			connect();
		return statement;
	}

	/**
	 * Datu basera konektatuta dagoen edo ez esaten du.
	 * 
	 * @return Datu basera konexioa badago true itzultzen du, bestela false.
	 */
	public static boolean isConnectedToDatabase() {
		return Connector.connectedToDatabase;
	}

	/**
	 * Datu basera konexioa ixten du.
	 * 
	 * @throws java.sql.SQLException
	 */
	public static void close() throws SQLException {
		Connector.statement.close();
		Connector.connection.close();
		Connector.connectedToDatabase = false;
	}
}
