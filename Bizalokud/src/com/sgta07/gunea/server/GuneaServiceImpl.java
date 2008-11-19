package com.sgta07.gunea.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sgta07.gunea.client.GuneaService;

public class GuneaServiceImpl extends RemoteServiceServlet implements
		GuneaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Connection connection = null;
	private Statement statement = null;

	public boolean alokaDaiteke(int guneId, String userNan)
			throws Exception {

		int bizikletakLibre = 0;
		konexioaEzarri();

		String queryLibre = "SELECT COUNT(*) AS bizikletakLibre "
				+ "FROM bizileta "
				+ "WHERE alta = 'true' AND alokatuta = 'false' AND fk_uneko_gune_id = ?";
		String queryBidean = "SELECT COUNT(*) AS bizikletakBidean "
				+ "FROM ibilbidea "
				+ "WHERE bukatuta = 'false' AND fk_gune_hel_id = ?";

		PreparedStatement ps1 = this.connection.prepareStatement(queryLibre);
		ps1.setInt(1, guneId);
		ResultSet rs1 = ps1.executeQuery();
		PreparedStatement ps2 = this.connection.prepareStatement(queryBidean);
		ps2.setInt(1, guneId);
		ResultSet rs2 = ps2.executeQuery();
		if (rs1.next()) {
			bizikletakLibre += rs1.getInt("bizikletakLibre");
		}
		if (rs2.next()) {
			bizikletakLibre += rs2.getInt("bizikletakBidean");
		}

		konexioaDeuseztu();

		return bizikletakLibre > 1;

	}

	public boolean helburuaAukeraDaiteke(int unekoGuneId, int helburuGuneId)
			throws Exception {

		konexioaEzarri();

		int tokiLibreak = 0;

		String queryBizikletaLibre = "SELECT COUNT(*) AS bizikletakLibre "
				+ "FROM bizileta "
				+ "WHERE alta = 'true' AND alokatuta = 'false' AND fk_uneko_gune_id = ?";
		PreparedStatement ps1 = this.connection
				.prepareStatement(queryBizikletaLibre);
		ps1.setInt(1, helburuGuneId);
		ResultSet rs1 = ps1.executeQuery();

		String queryTokiLibre = "SELECT COUNT(*) AS bideanDirenak "
				+ "FROM ibilbidea WHERE fk_gunehel_id = ? AND bukatuta = 0";
		PreparedStatement ps2 = this.connection
				.prepareStatement(queryTokiLibre);
		ps2.setInt(1, helburuGuneId);
		ResultSet rs2 = ps2.executeQuery();

		String queryGunekoEspazio = "SELECT toki_kop FROM gunea WHERE id=?";
		PreparedStatement ps3 = this.connection
				.prepareStatement(queryGunekoEspazio);
		ps3.setInt(1, helburuGuneId);
		ResultSet rs3 = ps3.executeQuery();

		if (rs1.next() && rs2.next() && rs3.next()) {
			tokiLibreak = rs3.getInt("toki_kop")
					- rs1.getInt("bizikletakLibre")
					+ rs2.getInt("bideanDirenak");
		}
		konexioaDeuseztu();
		return tokiLibreak > 0;

	}

	public HashMap<Integer, String> guneenZerrenda(int guneId)
			throws Exception {

		HashMap<Integer, String> guneak = new HashMap<Integer, String>();
		konexioaEzarri();

		String query = "SELECT id,helb FROM gunea WHERE id <> ?";

		PreparedStatement ps = this.connection.prepareStatement(query);
		ps.setInt(1, guneId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			guneak.put(rs.getInt("id"), rs.getString("helb"));
		}

		konexioaDeuseztu();

		return guneak;
	}

	public int alokatu(int unekoGuneId, int helburuGuneId, String erabNan)
			throws Exception {

		int esleitutakoBizikleta = -1;
		int result = -1;
		konexioaEzarri();
		String queryLibre = "SELECT id "
				+ "FROM bizileta "
				+ "WHERE alta = 'true' AND alokatuta = 'false' AND fk_uneko_gune_id = ?";
		PreparedStatement ps1 = this.connection.prepareStatement(queryLibre);
		ps1.setInt(1, unekoGuneId);
		ResultSet rs1 = ps1.executeQuery();
		if (rs1.next()) {
			esleitutakoBizikleta = rs1.getInt("id");
		}

		if (esleitutakoBizikleta != -1) {
			String queryIbilbide = "INSERT INTO ibilbidea SET hasiera_data=now(), fk_gunehas_id = ?, "
					+ "fk_gunehel_id = ?, fk_erab_nan = ?, fk_bizi_id = ?, bukatuta=0";

			PreparedStatement ps2 = connection.prepareStatement(queryIbilbide);
			ps2.setInt(1, unekoGuneId);
			ps2.setInt(2, helburuGuneId);
			ps2.setString(3, erabNan);
			ps2.setInt(6, esleitutakoBizikleta);

			result = ps2.executeUpdate();
		}

		konexioaDeuseztu();

		if (result > 0)
			return esleitutakoBizikleta;
		else
			return -1;
	}

	private void konexioaEzarri() throws SQLException, ClassNotFoundException {
		Connector.connect();
		this.connection = Connector.getConnection();
		this.statement = Connector.getStatement();
	}

	private void konexioaDeuseztu() throws SQLException {
		Connector.close();
	}
}
