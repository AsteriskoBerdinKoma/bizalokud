package com.sgta07.bizalokud.gunea.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sgta07.bizalokud.gunea.client.AbisuInfo;
import com.sgta07.bizalokud.gunea.client.AlokairuInfo;
import com.sgta07.bizalokud.gunea.client.BizikletaInfo;
import com.sgta07.bizalokud.gunea.client.DatuEstatistiko;
import com.sgta07.bizalokud.gunea.client.GuneInfo;
import com.sgta07.bizalokud.gunea.client.GuneaService;
import com.sgta07.bizalokud.gunea.client.IbilaldienPortzentaiak;
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

		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();

			String queryLibre = "SELECT COUNT(*) AS bizikletakLibre "
					+ "FROM bizikleta "
					+ "WHERE alta = true AND alokatuta = false AND fk_uneko_gune_id = ?";
			PreparedStatement ps1 = connector.prepareStatement(queryLibre);
			ps1.setInt(1, guneId);
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next()) {
				if (rs1.getInt("bizikletakLibre") == 0) {
					rs1.close();
					ps1.close();
					return false;
				} else if (rs1.getInt("bizikletakLibre") == 1) {
					String queryBidean = "SELECT COUNT(*) AS bizikletakBidean "
							+ "FROM ibilbidea "
							+ "WHERE bukatuta = false AND fk_gunehel_id = ?";
					PreparedStatement ps2 = connector
							.prepareStatement(queryBidean);
					ps2.setInt(1, guneId);
					ResultSet rs2 = ps2.executeQuery();
					if (rs2.next()) {
						if (rs2.getInt("bizikletakBidean") >= 1) {
							rs1.close();
							ps1.close();
							rs2.close();
							ps2.close();
							return true;
						} else {
							rs1.close();
							ps1.close();
							rs2.close();
							ps2.close();
							return false;
						}
					} else {
						rs1.close();
						ps1.close();
						return false;
					}
				} else {
					rs1.close();
					ps1.close();
					return true;
				}
			} else {
				rs1.close();
				ps1.close();
				return false;
			}

		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}
	}

	public boolean helburuaAukeraDaiteke(int unekoGuneId, int helburuGuneId)
			throws Salbuespena {

		int tokiLibreak = 0;
		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();

			String queryBizikletaLibre = "SELECT COUNT(*) AS bizikletakLibre "
					+ "FROM bizikleta "
					+ "WHERE alta = true AND alokatuta = false AND fk_uneko_gune_id = ?";
			PreparedStatement ps1 = connector
					.prepareStatement(queryBizikletaLibre);
			ps1.setInt(1, helburuGuneId);
			ResultSet rs1 = ps1.executeQuery();

			String queryTokiLibre = "SELECT COUNT(*) AS bideanDirenak "
					+ "FROM ibilbidea WHERE fk_gunehel_id = ? AND bukatuta = false";
			PreparedStatement ps2 = connector.prepareStatement(queryTokiLibre);
			ps2.setInt(1, helburuGuneId);
			ResultSet rs2 = ps2.executeQuery();

			String queryGunekoEspazio = "SELECT toki_kop FROM gunea WHERE id=?";
			PreparedStatement ps3 = connector
					.prepareStatement(queryGunekoEspazio);
			ps3.setInt(1, helburuGuneId);
			ResultSet rs3 = ps3.executeQuery();

			if (rs1.next() && rs2.next() && rs3.next())
				tokiLibreak = rs3.getInt("toki_kop")
						- (rs1.getInt("bizikletakLibre") + rs2
								.getInt("bideanDirenak"));

			rs1.close();
			rs2.close();
			rs3.close();
			ps1.close();
			ps2.close();
			ps3.close();
			// connector.close();
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
			// connector.close();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}

		return guneak;
	}

	public HashMap<Integer, GuneInfo> getHelburuGunePosibleak(int unekoGuneId)
			throws Salbuespena {
		HashMap<Integer, GuneInfo> guneak = guneenZerrenda();
		HashMap<Integer, GuneInfo> emaitza = new HashMap<Integer, GuneInfo>();
		Set<Integer> keys = guneak.keySet();
		for (int id : keys) {
			System.out.println(id);
			if (helburuaAukeraDaiteke(unekoGuneId, id)) {
				emaitza.put(id, guneak.get(id));
				// guneak.remove(id);
				System.out.println(":" + id);
			}
		}

		return emaitza;
	}

	public AlokairuInfo alokatu(int unekoGuneId, int helburuGuneId,
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
					+ "FROM bizikleta "
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
						+ "FROM bizikleta "
						+ "WHERE alta = true AND alokatuta = false AND egoera = 'ondo' AND fk_uneko_gune_id = ?";
				PreparedStatement ps2 = connector.prepareStatement(query);
				ps2.setInt(1, unekoGuneId);
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
				ps3.setInt(4, esleitutakoBizikleta.getId());
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
			// connector.close();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}

		if (result > 0)
			return getAzkenAlokairuInfo(erabNan);
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
			if (rs.next()) {
				emaitza = new GuneInfo(rs.getInt("id"), rs.getString("izena"),
						rs.getString("helb"));
				emaitza.setLatLon(rs.getDouble("lat"), rs.getDouble("lon"));
			}
			// connector.close();
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

				// Date d = new Date(Integer.parseInt(data[0]), Integer
				// .parseInt(data[1]) - 1, Integer.parseInt(data[2]),
				// Integer.parseInt(ordua[0]), Integer.parseInt(ordua[1]),
				// Integer.parseInt(ordua[2]));
				//				
				// AbisuInfo aiu = new AbisuInfo(id, d, rs
				// .getBoolean("irakurrita"), rs.getString("mota"), rs
				// .getString("mezua"));

				emaitza.put(id, aiu);
			}

			// connector.close();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}
		return emaitza;
	}

	public boolean erabiltzaileaAlokatuDu(String erabNan) throws Salbuespena {
		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();
			String query = "SELECT COUNT(id) AS alokairuKop FROM ibilbidea WHERE fk_erab_nan = ? AND bukatuta = 0";
			PreparedStatement ps = connector.prepareStatement(query);
			ps.setString(1, erabNan);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return (rs.getInt("alokairuKop") == 0);
			else
				return false;
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}

	}

	public boolean pasahitzaBerritu(String userNan, String zaharra,
			String berria) throws Salbuespena {
		int rs = -1;
		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();
			String query = "UPDATE erabiltzailea SET pasahitza=? WHERE nan=? AND pasahitza=?";
			PreparedStatement ps = connector.prepareStatement(query);
			ps.setString(1, berria);
			ps.setString(2, userNan);
			ps.setString(3, zaharra);
			rs = ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}
		return rs > 0;
	}

	public AlokairuInfo getAzkenAlokairuInfo(String erabNan) throws Salbuespena {
		AlokairuInfo azkenAlokInfo = null;
		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();

			String query = "SELECT i.id AS id, i.hasiera_data AS hasieraData, i.bukaera_data AS bukaeraData, i.fk_erab_nan AS erabNan, b.id AS biziId, b.modeloa AS biziModelo, b.kolorea AS biziKolore, hasG.id AS hasGuneId, hasG.izena AS hasGuneIzen, hasG.helb AS hasGuneHelbide, helG.id AS helGuneId, helG.izena AS helGuneIzen, helG.helb AS helGuneHelbide, i.bukatuta AS bukatuta FROM (((ibilbidea i INNER JOIN gunea AS hasG ON hasG.id = i.fk_gunehas_id) INNER JOIN gunea AS helG ON helG.id = i.fk_gunehel_id) INNER JOIN bizikleta AS b ON b.id=i.fk_bizi_id) WHERE fk_erab_nan = ? ORDER BY hasieraData DESC LIMIT 1";
			PreparedStatement ps = connector.prepareStatement(query);
			ps.setString(1, erabNan);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String temp1 = rs.getString("hasieraData");

				String[] s1 = temp1.substring(0, temp1.indexOf('.')).split(" ");
				String[] hasData = s1[0].split("-");
				String[] hasOrdua = s1[1].split(":");
				Calendar hasCal = new GregorianCalendar(Integer
						.parseInt(hasData[0]), Integer.parseInt(hasData[1]),
						Integer.parseInt(hasData[2]), Integer
								.parseInt(hasOrdua[0]), Integer
								.parseInt(hasOrdua[1]), Integer
								.parseInt(hasOrdua[2]));

				Date hasieraData = hasCal.getTime();

				Date bukaeraData = null;

				String temp2 = rs.getString("bukaeraData");
				if (temp2 != null) {

					String[] s2 = temp2.substring(0, temp2.indexOf('.')).split(
							" ");
					String[] bukData = s2[0].split("-");
					String[] bukOrdua = s2[1].split(":");
					Calendar bukCal = new GregorianCalendar(Integer
							.parseInt(bukData[0]),
							Integer.parseInt(bukData[1]), Integer
									.parseInt(bukData[2]), Integer
									.parseInt(bukOrdua[0]), Integer
									.parseInt(bukOrdua[1]), Integer
									.parseInt(bukOrdua[2]));
					bukaeraData = bukCal.getTime();
				}
				String nan = rs.getString("erabNan");

				int biziId = rs.getInt("biziId");
				String biziModelo = rs.getString("biziModelo");
				String biziKolore = rs.getString("biziKolore");

				int hasGuneId = rs.getInt("hasGuneId");
				String hasGuneIzen = rs.getString("hasGuneIzen");
				String hasGuneHelbide = rs.getString("hasGuneHelbide");

				int helGuneId = rs.getInt("helGuneId");
				String helGuneIzen = rs.getString("helGuneIzen");
				String helGuneHelbide = rs.getString("helGuneHelbide");

				boolean bukatuta = rs.getBoolean("bukatuta");

				azkenAlokInfo = new AlokairuInfo(id, hasieraData, bukaeraData,
						nan, new BizikletaInfo(biziId, biziModelo, biziKolore),
						new GuneInfo(hasGuneId, hasGuneIzen, hasGuneHelbide),
						new GuneInfo(helGuneId, helGuneIzen, helGuneHelbide),
						bukatuta);
			}

			rs.close();
			ps.close();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}

		return azkenAlokInfo;
	}

	public AlokairuInfo bizikletaBueltatu(String erabNan) throws Salbuespena {
		try {

			System.out.println(1);

			if (!connector.isConnectedToDatabase())
				connector.connect();

			System.out.println(2);

			AlokairuInfo azkenAlokairu = getAzkenAlokairuInfo(erabNan);
			String sql1 = "UPDATE bizikleta SET alokatuta = false, fk_uneko_gune_id = ? WHERE id = ?";
			PreparedStatement ps1 = connector.prepareStatement(sql1);
			ps1.setInt(1, azkenAlokairu.getHelGune().getId());
			ps1.setInt(2, azkenAlokairu.getBizikleta().getId());
			int result = ps1.executeUpdate();

			System.out.println(3);

			if (result > 0) {
				String sql2 = "UPDATE ibilbidea SET bukaera_data = NOW(), bukatuta = true WHERE id = ?";
				PreparedStatement ps2 = connector.prepareStatement(sql2);
				ps2.setInt(1, azkenAlokairu.getId());
				ps2.executeUpdate();

				System.out.println(4);
			}
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}

		System.out.println(5);

		return getAzkenAlokairuInfo(erabNan);
	}

	public boolean abisuaIrakurriDa(String erabNan, int id) throws Salbuespena {
		int rs = -1;
		try {
			if (!connector.isConnectedToDatabase())
				connector.connect();
			String query = "UPDATE abisuak SET irakurrita=1 WHERE nan=? AND id=?";
			PreparedStatement ps = connector.prepareStatement(query);
			ps.setString(1, erabNan);
			ps.setInt(2, id);
			rs = ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}
		return rs > 0;
	}

	public DatuEstatistiko nireIbilbideakLortu(String erabNan)
			throws Salbuespena {
		DatuEstatistiko de = new DatuEstatistiko();
		Vector<IbilaldienPortzentaiak> kalkuluak = new Vector<IbilaldienPortzentaiak>();
		int alokatuKont = 0;
		boolean aurkitua = false;
		String alokairuLuzeenaEguna = "";
		String alokairuLuzeenaDenbora = "";
		String egunAktiboenaEguna = "";
		String egunAktiboenaDenbora = "";

		try {

			if (!connector.isConnectedToDatabase())
				connector.connect();

			String query = "SELECT i1.fk_gunehas_id as hasId, g1.izena as hasIzena, i1.fk_gunehel_id helId, g2.izena as helIzena "
					+ "FROM ibilbidea i1 INNER JOIN gunea g1 on i1.fk_gunehas_id=g1.id INNER JOIN gunea g2 on i1.fk_gunehel_id=g2.id "
					+ "WHERE fk_erab_nan = ? AND bukatuta = 1";
			PreparedStatement ps = connector.prepareStatement(query);
			ps.setString(1, erabNan);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				alokatuKont++;
				IbilaldienPortzentaiak temp = new IbilaldienPortzentaiak();
				temp.setHasierakoGuneId(rs.getInt("hasId"));
				temp.setHelburuGuneId(rs.getInt("helId"));
				temp.setHasierakoGuneIzena(rs.getString("hasIzena"));
				temp.setHelburuGuneIzena(rs.getString("helIzena"));
				for (IbilaldienPortzentaiak row : kalkuluak) {
					if (row.getHasierakoGuneId() == temp.getHasierakoGuneId() && row.getHelburuGuneId()==temp.getHelburuGuneId()) {
						row.setEgindakoAldiKop(row.getEgindakoAldiKop()+1);
						aurkitua = true;
						break;
					}
				}
				if (!aurkitua) {
					temp.setEgindakoAldiKop(1);
					kalkuluak.addElement(temp);
				}
				aurkitua = false;
			}
			for(IbilaldienPortzentaiak row: kalkuluak){
				double r = new Integer(row.getEgindakoAldiKop()).doubleValue()/new Integer(alokatuKont).doubleValue();
				Double p = new Double(Math.round(r*1000.0)/1000.0);
				row.setPortzentaia(p.doubleValue());
			}

			String queryAlokairuLuzeena = "SELECT DATE(hasiera_data) AS eguna, "
					+ "timediff(bukaera_data, hasiera_data) AS elapsed "
					+ "FROM ibilbidea "
					+ "WHERE fk_erab_nan=? AND bukatuta=1 "
					+ "ORDER BY timediff(bukaera_data, hasiera_data) DESC "
					+ "LIMIT 1";

			PreparedStatement ps1 = connector
					.prepareStatement(queryAlokairuLuzeena);
			ps1.setString(1, erabNan);
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next()) {
				alokairuLuzeenaEguna = rs1.getString("eguna");
				alokairuLuzeenaDenbora = rs1.getString("elapsed");
			}

			String queryEgunAktiboena = "SELECT DATE(hasiera_data) AS eguna, "
					+ "SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(bukaera_data, hasiera_data)))) AS elapsed "
					+ "FROM ibilbidea "
					+ "WHERE fk_erab_nan=? AND bukatuta=1 "
					+ "GROUP BY DATE(hasiera_data) "
					+ "ORDER BY SEC_TO_TIME(SUM(TIME_TO_SEC(timediff(bukaera_data, hasiera_data)))) DESC "
					+ "LIMIT 1";

			PreparedStatement ps2 = connector
					.prepareStatement(queryEgunAktiboena);
			ps2.setString(1, erabNan);
			ResultSet rs2 = ps2.executeQuery();
			if (rs2.next()) {
				egunAktiboenaEguna = rs2.getString("eguna");
				egunAktiboenaDenbora = rs2.getString("elapsed");
			}
			rs.close();
			ps.close();
			rs1.close();
			ps1.close();
			rs2.close();
			ps2.close();

			de = new DatuEstatistiko(alokatuKont, alokairuLuzeenaDenbora,
					alokairuLuzeenaEguna, kalkuluak.size(),
					egunAktiboenaDenbora, egunAktiboenaEguna, kalkuluak);

		} catch (ClassNotFoundException e) {
			throw new Salbuespena("CNF: " + e.getMessage(), e.getCause());
		} catch (SQLException e) {
			throw new Salbuespena("SQL: " + e.getMessage(), e.getCause());
		}
		return de;
	}
}
