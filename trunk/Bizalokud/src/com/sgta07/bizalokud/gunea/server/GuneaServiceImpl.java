package com.sgta07.bizalokud.gunea.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import com.sgta07.bizalokud.gunea.client.GuneInfo;
import com.sgta07.bizalokud.gunea.client.GuneaService;
import com.sgta07.bizalokud.zerbitzaria.db.Connector;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GuneaServiceImpl extends RemoteServiceServlet implements GuneaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Connector connector = new Connector();
	
//	private Connection connection = null;
//	private Statement statement = null;

	public boolean alokaDaiteke(int guneId, String userNan)
			throws Exception {

		int bizikletakLibre = 0;
		
		if (!connector.isConnectedToDatabase())
			connector.connect();

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
		connector.close();

		return bizikletakLibre > 1;

	}

	public boolean helburuaAukeraDaiteke(int unekoGuneId, int helburuGuneId)
			throws Exception {

		if (!connector.isConnectedToDatabase())
			connector.connect();

		int tokiLibreak = 0;

		String queryBizikletaLibre = "SELECT COUNT(*) AS bizikletakLibre "
				+ "FROM bizileta "
				+ "WHERE alta = 'true' AND alokatuta = 'false' AND fk_uneko_gune_id = ?";
		PreparedStatement ps1 = connector.prepareStatement(queryBizikletaLibre);
		ps1.setInt(1, helburuGuneId);
		ResultSet rs1 = ps1.executeQuery();

		String queryTokiLibre = "SELECT COUNT(*) AS bideanDirenak "
				+ "FROM ibilbidea WHERE fk_gunehel_id = ? AND bukatuta = 0";
		PreparedStatement ps2 = connector.prepareStatement(queryTokiLibre);
		ps2.setInt(1, helburuGuneId);
		ResultSet rs2 = ps2.executeQuery();

		String queryGunekoEspazio = "SELECT toki_kop FROM gunea WHERE id=?";
		PreparedStatement ps3 = connector.prepareStatement(queryGunekoEspazio);
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
		connector.close();
		
		return tokiLibreak > 0;

	}

	public HashMap<Integer, GuneInfo> guneenZerrenda() throws Exception {

		HashMap<Integer, GuneInfo> guneak = new HashMap<Integer, GuneInfo>();
		
		if (!connector.isConnectedToDatabase())
			connector.connect();

		String query = "SELECT id, izena, helb FROM gunea";

		PreparedStatement ps = connector.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()){
			int id = rs.getInt("id");
			guneak.put(id, new GuneInfo(id, rs.getString("izena"), rs.getString("helb")));
		}

		rs.close();
		ps.close();
		connector.close();

		return guneak;
	}

	public int alokatu(int unekoGuneId, int helburuGuneId, String erabNan)
			throws Exception {

		int esleitutakoBizikleta = -1;
		int result = -1;
		
		if (!connector.isConnectedToDatabase())
			connector.connect();
		
		String queryLibre = "SELECT id "
				+ "FROM bizileta "
				+ "WHERE alta = 'true' AND alokatuta = 'false' AND fk_uneko_gune_id = ?";
		PreparedStatement ps1 = connector.prepareStatement(queryLibre);
		ps1.setInt(1, unekoGuneId);
		ResultSet rs1 = ps1.executeQuery();
		if (rs1.next()) {
			esleitutakoBizikleta = rs1.getInt("id");
		}

		if (esleitutakoBizikleta != -1) {
			String queryIbilbide = "INSERT INTO ibilbidea SET hasiera_data=now(), fk_gunehas_id = ?, "
					+ "fk_gunehel_id = ?, fk_erab_nan = ?, fk_bizi_id = ?, bukatuta=0";

			PreparedStatement ps2 = connector.prepareStatement(queryIbilbide);
			ps2.setInt(1, unekoGuneId);
			ps2.setInt(2, helburuGuneId);
			ps2.setString(3, erabNan);
			ps2.setInt(6, esleitutakoBizikleta);

			result = ps2.executeUpdate();
		}

		rs1.close();
		ps1.close();
		connector.close();

		if (result > 0)
			return esleitutakoBizikleta;
		else
			return -1;
	}
	
	public GuneInfo getMyInfo() throws Exception{
		if(!connector.isConnectedToDatabase())
			connector.connect();
		
		String query = "SELECT * FROM gunea WHERE ip=?";
		PreparedStatement ps = connector.prepareStatement(query);
		ps.setString(1, getThreadLocalRequest().getLocalAddr());
		
		ResultSet rs = ps.executeQuery();
		
		GuneInfo emaitza = null;
		
		if(rs.next())
			emaitza = new GuneInfo(rs.getInt("id"), rs.getString("izena"), rs.getString("helb"));
		
		return emaitza;
	}

//	private void konexioaEzarri() throws SQLException, ClassNotFoundException {
//		Connector.connect();
//		this.connection = Connector.getConnection();
//		this.statement = Connector.getStatement();
//	}
//
//	private void konexioaDeuseztu() throws SQLException {
//		Connector.close();
//	}
}
