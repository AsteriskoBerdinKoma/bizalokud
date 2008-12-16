package com.sgta07.bizalokud.login.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sgta07.bizalokud.login.client.LoginInfo;
import com.sgta07.bizalokud.login.client.LoginService;
import com.sgta07.bizalokud.zerbitzaria.db.Connector;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Connector connector = new Connector();

	@Override
	public LoginInfo login(String user, String pass) {
		HttpSession session = this.getThreadLocalRequest().getSession();

		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();
		} catch (SQLException e) {

			e.printStackTrace();

			session.setAttribute("valid", false);
			return new LoginInfo("Errorea datu basera konektatzerakoan");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();

			session.setAttribute("valid", false);
			return new LoginInfo("Errorea datu basera konektatzerakoan");
		}

		try {
			String query = "SELECT * FROM erabiltzailea WHERE nan=?";
			PreparedStatement ps;
			ps = connector.prepareStatement(query);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String password = rs.getString("pasahitza");
				String nan = rs.getString("nan");
				String izena = rs.getString("izena");
				String abizenak = rs.getString("abizenak");
				String ePosta = rs.getString("eposta");
				String telefonoa = rs.getString("telefonoa");
				boolean isAltan = rs.getBoolean("alta");
				boolean isAdmin = rs.getString("mota").equals("admin");
				ps.close();
				connector.close();

				if (password.equals(pass)) {
					session.setAttribute("nan", nan);
					session.setAttribute("izena", izena);
					session.setAttribute("abuzenak", abizenak);
					session.setAttribute("isAdmin", isAdmin);
					session.setAttribute("altan", isAltan);
					if (isAltan) {
						session.setAttribute("valid", true);
						return new LoginInfo(nan, izena, abizenak, ePosta, telefonoa, isAdmin, true);
					} else {
						session.setAttribute("valid", false);
						return new LoginInfo("Kontu hau bajan emanda dago.");
					}
				} else{
					session.setAttribute("valid", false);
					return new LoginInfo(
							"Erabiltzaile izena edo pasahitza okerra da.");
				}
			} else{
				session.setAttribute("valid", false);
				return new LoginInfo(
				"Erabiltzaile izena edo pasahitza okerra da.");
			}
		} catch (SQLException e1) {

			e1.printStackTrace();

			session.setAttribute("valid", false);
			return new LoginInfo("Errorea datu basea atzitzerakoan");
		}
	}
}
