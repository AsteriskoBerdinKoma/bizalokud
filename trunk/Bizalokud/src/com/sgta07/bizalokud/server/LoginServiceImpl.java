package com.sgta07.bizalokud.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sgta07.bizalokud.client.LoginService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean login(String user, String pass) {
		try {
			String JDBC_DRIVER = "com.mysql.jdbc.Driver";
			Class.forName(JDBC_DRIVER);
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/froga",
					"froga", "1234");
			Statement statement = connection.createStatement();
			
			String query = "SELECT pass FROM users WHERE user=?";
			PreparedStatement ps;
			ps = connection.prepareStatement(query);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String password = rs.getString("pass");
				statement.close();
				connection.close();

				if (password.equals(pass))
					return true;
				else
					return false;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
}
