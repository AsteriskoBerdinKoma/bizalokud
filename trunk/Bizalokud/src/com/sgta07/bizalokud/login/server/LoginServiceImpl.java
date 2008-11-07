package com.sgta07.bizalokud.login.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sgta07.bizalokud.login.client.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean login(String user, String pass) {
		HttpSession session = this.getThreadLocalRequest().getSession();
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
				if (password.equals(pass)){
					session.setAttribute("valid", true);
					return true;
				}else{
					session.setAttribute("valid", false);
					return false;
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		session.setAttribute("valid", false);
		return false;
	}
}
