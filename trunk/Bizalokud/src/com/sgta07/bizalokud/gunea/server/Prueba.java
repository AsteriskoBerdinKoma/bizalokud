package com.sgta07.bizalokud.gunea.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import com.sgta07.bizalokud.gunea.client.DatuEstatistiko;
import com.sgta07.bizalokud.gunea.client.GuneInfo;
import com.sgta07.bizalokud.gunea.client.Salbuespena;
import com.sgta07.bizalokud.zerbitzaria.db.Connector;

public class Prueba {
	
	private Connector connector = new Connector();
	
	public DatuEstatistiko nireIbilbideakLortu(String erabNan)
			throws Salbuespena {
		DatuEstatistiko de = new DatuEstatistiko();
		Vector<Object[]> kalkuluak = new Vector<Object[]>();
		int alokatuKont = 0;
		HashMap<Integer, String> guneak = new HashMap<Integer, String>();
		boolean aurkitua = false;
		String alokairuLuzeenaEguna = "";
		String alokairuLuzeenaDenbora = "";
		String egunAktiboenaEguna = "";
		String egunAktiboenaDenbora = "";

		try {

			if (!connector.isConnectedToDatabase())
				connector.connect();

			String query = "SELECT i1.fk_gunehas_id, g1.izena as hasIzena, i1.fk_gunehel_id, g2.izena as helIzena "+
						   "FROM ibilbidea i1 INNER JOIN gunea g1 on i1.fk_gunehas_id=g1.id INNER JOIN gunea g2 on i1.fk_gunehel_id=g2.id "+
						   "WHERE fk_erab_nan = ? AND bukatuta = 1";
			PreparedStatement ps = connector.prepareStatement(query);
			ps.setString(1, erabNan);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				alokatuKont++;
				Object[] temp = new Object[6];
				temp[0] = new Integer(rs.getInt("fk_gunehas_id"));
				temp[1] = new Integer(rs.getInt("fk_gunehel_id"));
				temp[4] = new String(rs.getString("hasIzena"));
				temp[5] = new String(rs.getString("helIzena"));
				for (Object[] row : kalkuluak) {
					if (row[0].equals(temp[0]) && row[1].equals(temp[1])) {
						row[2] = (Integer) row[2] + 1;
						row[3] = new Integer(row[2].toString()).doubleValue()
								/ new Integer(alokatuKont).doubleValue();
						aurkitua = true;
						break;
					}
				}
				if (!aurkitua) {
					temp[2] = 1;
					temp[3] = 1.0 / new Integer(alokatuKont).doubleValue();
					kalkuluak.addElement(temp);
				}
				aurkitua = false;
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
	
	public static void main(String[] args){
		Prueba p = new Prueba();
		try {
			DatuEstatistiko de = p.nireIbilbideakLortu("09760589X");
			System.out.println("Alokairu Kopurua: "
					+ de.getAlokairuKop());
			System.out.println("Alokairu Luzeena: "
					+ de.getAlokairuLuzeenaEguna() + ": "
					+ de.getAlokairuLuzeenaDenbora());
			System.out.println("Egun aktiboena: "
					+ de.getEgunAktiboneaEguna() + ": "
					+ de.getEgunAktiboenaDenbora());
			System.out.println("Egindako Ibilaldiak: "
					+ de.getIbilaldiKop());
			System.out.println("Bidiaien portzentaiak");
			for (Object[] row : de
					.getIbilaldienPortzentaiak()) {
				System.out.println(row[4] + " - " + row[5]
						+ ": " + ((Double) row[3] * 100) + "%");
			}
		} catch (Salbuespena e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
