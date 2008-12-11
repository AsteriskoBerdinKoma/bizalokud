package com.sgta07.bizalokud.gunea.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sgta07.bizalokud.gunea.client.AbisuInfo;
import com.sgta07.bizalokud.gunea.client.BizikletaInfo;
import com.sgta07.bizalokud.gunea.client.GuneInfo;
import com.sgta07.bizalokud.gunea.client.GuneaService;
import com.sgta07.bizalokud.gunea.client.Salbuespena;
import com.sgta07.bizalokud.zerbitzaria.db.Connector;

public class GuneaServiceImpl extends RemoteServiceServlet implements
		GuneaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Connector connector = new Connector();

	public boolean alokaDaiteke(int guneId) throws Salbuespena {

		int bizikletakLibre = 0;

		try {
			if (!connector.isConnectedToDatabase()){
				connector.connect();
			}

			String queryLibre = "SELECT COUNT(*) AS bizikletakLibre "
					+ "FROM bizileta "
					+ "WHERE alta = 'true' AND alokatuta = 'false' AND fk_uneko_gune_id = ?";
			String queryBidean = "SELECT COUNT(*) AS bizikletakBidean "
					+ "FROM ibilbidea "
					+ "WHERE bukatuta = 'false' AND fk_gune_hel_id = ?";

			PreparedStatement ps1 = connector.prepareStatement(queryLibre);
			ps1.setInt(1, guneId);
			ResultSet rs1 = ps1.executeQuery();
			PreparedStatement ps2 = connector.prepareStatement(queryBidean);
			ps2.setInt(1, guneId);
			ResultSet rs2 = ps2.executeQuery();
			if (rs1.next()) {
				bizikletakLibre += rs1.getInt("bizikletakLibre");
			}
			if (rs2.next()) {
				bizikletakLibre += rs2.getInt("bizikletakBidean");
			}

			rs1.close();
			rs2.close();
			ps1.close();
			ps2.close();
//			connector.close();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}

		return bizikletakLibre > 1;

	}

	public boolean helburuaAukeraDaiteke(int unekoGuneId, int helburuGuneId)
			throws Salbuespena {

		int tokiLibreak = 0;
		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();

			String queryBizikletaLibre = "SELECT COUNT(*) AS bizikletakLibre "
					+ "FROM bizileta "
					+ "WHERE alta = 'true' AND alokatuta = 'false' AND fk_uneko_gune_id = ?";
			PreparedStatement ps1 = connector
					.prepareStatement(queryBizikletaLibre);
			ps1.setInt(1, helburuGuneId);
			ResultSet rs1 = ps1.executeQuery();

			String queryTokiLibre = "SELECT COUNT(*) AS bideanDirenak "
					+ "FROM ibilbidea WHERE fk_gunehel_id = ? AND bukatuta = 0";
			PreparedStatement ps2 = connector.prepareStatement(queryTokiLibre);
			ps2.setInt(1, helburuGuneId);
			ResultSet rs2 = ps2.executeQuery();

			String queryGunekoEspazio = "SELECT toki_kop FROM gunea WHERE id=?";
			PreparedStatement ps3 = connector
					.prepareStatement(queryGunekoEspazio);
			ps3.setInt(1, helburuGuneId);
			ResultSet rs3 = ps3.executeQuery();

			if (rs1.next() && rs2.next() && rs3.next()) {
				tokiLibreak = rs3.getInt("toki_kop")
						- rs1.getInt("bizikletakLibre")
						+ rs2.getInt("bideanDirenak");
			}

			rs1.close();
			rs2.close();
			rs3.close();
			ps1.close();
			ps2.close();
			ps3.close();
//			connector.close();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}

		return tokiLibreak > 0;

	}

	public HashMap<Integer, GuneInfo> guneenZerrenda() throws Salbuespena {

		HashMap<Integer, GuneInfo> guneak = new HashMap<Integer, GuneInfo>();

		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();

			String query = "SELECT id, izena, helb, lat, lon FROM gunea";

			PreparedStatement ps = connector.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String izena = rs.getString("izena");
				String helbidea = rs.getString("helb");
				double lat = rs.getDouble("lat");
				double lon = rs.getDouble("lon");
				
				GuneInfo gunea = new GuneInfo(id, izena, helbidea);
				gunea.setLatLon(lat, lon);

				guneak.put(id, gunea);
			}

			rs.close();
			ps.close();
//			connector.close();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}
		
		return guneak;
	}

	public BizikletaInfo alokatu(int unekoGuneId, int helburuGuneId,
			String erabNan) throws Salbuespena {

		BizikletaInfo esleitutakoBizikleta;
		int result = -1;

		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();

			if (!alokaDaiteke(unekoGuneId))
				throw new Salbuespena(
						"Gune honatan ez daude bizikleta librerik, beraz momentuz ezingo dira alokatu.");
			if (!helburuaAukeraDaiteke(unekoGuneId, helburuGuneId))
				throw new Salbuespena(
						"Joan nahi duzun gunean ez dago leku librerik. Beste gune bat aukeratu mesedez.");

			// Jatorri gunea ibilbide honen helburu gunearen berdina duten
			// bizikletei lehentasuna emango zaie.
			String queryLibre = "SELECT * "
					+ "FROM bizileta "
					+ "WHERE alta = true AND alokatuta = false AND egoera = 'ondo' AND fk_uneko_gune_id = ? AND fk_jatorri_gune_id = ?";
			PreparedStatement ps1 = connector.prepareStatement(queryLibre);
			ps1.setInt(1, unekoGuneId);
			ps1.setInt(2, helburuGuneId);
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next())
				esleitutakoBizikleta = new BizikletaInfo(rs1.getInt("id"), rs1
						.getString("modeloa"), rs1.getString("kolorea"));
			else {
				String query = "SELECT * "
						+ "FROM bizileta "
						+ "WHERE alta = true AND alokatuta = false AND egoera = 'ondo' AND fk_uneko_gune_id = ?";
				PreparedStatement ps2 = connector.prepareStatement(query);
				ps2.setInt(1, unekoGuneId);
				ps2.setInt(2, helburuGuneId);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next())
					esleitutakoBizikleta = new BizikletaInfo(rs2.getInt("id"),
							rs2.getString("modeloa"), rs2.getString("kolorea"));
				else
					throw new Salbuespena(
							"Momentu honetan ezin dizugu bizikletarik esleitu. Barkatu eragozpenak");
				rs2.close();
				ps2.close();
			}

			if (esleitutakoBizikleta != null) {
				String queryIbilbide = "INSERT INTO ibilbidea SET hasiera_data=now(), fk_gunehas_id = ?, "
						+ "fk_gunehel_id = ?, fk_erab_nan = ?, fk_bizi_id = ?, bukatuta=0";

				PreparedStatement ps3 = connector
						.prepareStatement(queryIbilbide);
				ps3.setInt(1, unekoGuneId);
				ps3.setInt(2, helburuGuneId);
				ps3.setString(3, erabNan);
				ps3.setInt(6, esleitutakoBizikleta.getId());
				result = ps3.executeUpdate();
				ps3.close();

				if (result > 0) {
					String updateBizikleta = "UPDATE bizikleta SET alokatuta=true WHERE id=?";
					PreparedStatement ps4 = connector
							.prepareStatement(updateBizikleta);
					ps4.setInt(1, esleitutakoBizikleta.getId());
					ps4.executeUpdate();
					ps4.close();
				}
			}

			rs1.close();
			ps1.close();
//			connector.close();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}

		if (result > 0)
			return esleitutakoBizikleta;
		else
			throw new Salbuespena(
					"Errore bat egon da bizikleta alokatzerakoan. Barkatu eragozpenak.");
	}

	public GuneInfo getMyInfo() throws Salbuespena {
		GuneInfo emaitza = null;
		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();
			String query = "SELECT * FROM gunea WHERE ip=?";
			PreparedStatement ps = connector.prepareStatement(query);
			ps.setString(1, getThreadLocalRequest().getLocalAddr());

			ResultSet rs = ps.executeQuery();
			if (rs.next())
				emaitza = new GuneInfo(rs.getInt("id"), rs.getString("izena"),
						rs.getString("helb"));
//			connector.close();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}

		return emaitza;
	}

	public HashMap<Integer, AbisuInfo> getAbisuenZerrenda(String userNan)
			throws Salbuespena {
		HashMap<Integer, AbisuInfo> emaitza = new HashMap<Integer, AbisuInfo>();

		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();
			String query = "SELECT * FROM abisuak WHERE fk_erab_nan=?";
			PreparedStatement ps = connector.prepareStatement(query);
			ps.setString(1, userNan);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String temp = rs.getString("data");
				String[] s = temp.substring(0, temp.indexOf('.')).split(" ");
				String[] data = s[0].split("-");
				String[] ordua = s[1].split(":");

				Calendar cal = new GregorianCalendar(Integer.parseInt(data[0]),
                        Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                        Integer.parseInt(ordua[0]), Integer.parseInt(ordua[1]),
                        Integer.parseInt(ordua[2]));

				AbisuInfo aiu = new AbisuInfo(id, cal.getTime(), rs
						.getBoolean("irakurrita"), rs.getString("mota"), rs
						.getString("mezua"));
						
//				Date d = new Date(Integer.parseInt(data[0]), Integer
//						.parseInt(data[1]) - 1, Integer.parseInt(data[2]),
//						Integer.parseInt(ordua[0]), Integer.parseInt(ordua[1]),
//						Integer.parseInt(ordua[2]));
//				
//				AbisuInfo aiu = new AbisuInfo(id, d, rs
//						.getBoolean("irakurrita"), rs.getString("mota"), rs
//						.getString("mezua"));
				
				emaitza.put(id, aiu);
			}

//			connector.close();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}
		return emaitza;
	}
}
