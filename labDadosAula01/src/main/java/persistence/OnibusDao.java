package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Onibus;

public class OnibusDao implements interfaceDao<Onibus> {
	private Connection c;
	
	public OnibusDao() throws ClassNotFoundException, SQLException {
		GenericDAO gdao = new GenericDAO();
		c = gdao.conexao();
	}
	
	public void desconectar() throws SQLException {
		c.close();
	}
	
	@Override
	public void criar(Onibus o) throws SQLException {
		String sql = "INSERT INTO Onibus VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, o.getPlaca());
		ps.setString(2, o.getMarca());
		ps.setInt(3, o.getAno());
		ps.setString(4, o.getDesc());
		
		ps.execute();
		ps.close();
	}

	@Override
	public Onibus buscar(Onibus o) throws SQLException {
		String sql = "SELECT marca, ano, descricao FROM Onibus WHERE placa = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, o.getPlaca());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			o.setMarca(rs.getString("marca"));
			o.setAno(rs.getInt("ano"));
			o.setDesc(rs.getString("descricao"));
		}
		
		rs.close();
		ps.close();
		
		return o;
	}

	@Override
	public void alterar(Onibus o) throws SQLException {
		String sql = "UPDATE Onibus SET marca = ?, ano = ?, descricao = ? WHERE placa = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, o.getMarca());
		ps.setInt(2, o.getAno());
		ps.setString(3, o.getDesc());
		ps.setString(4, o.getPlaca());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void remover(Onibus o) throws SQLException {
		String sql = "DELETE Onibus WHERE placa = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, o.getPlaca());
		
		ps.execute();
		ps.close();
	}

	@Override
	public List<Onibus> buscaAll() throws SQLException {
		List<Onibus> onibus = new ArrayList<>();
		
		String sql = "SELECT placa, marca, ano, descricao FROM Onibus";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Onibus o = new Onibus();
			o.setPlaca(rs.getString("placa"));
			o.setMarca(rs.getString("marca"));
			o.setAno(rs.getInt("ano"));
			o.setDesc(rs.getString("descricao"));
			
			onibus.add(o);
		}
		
		return onibus;
	}

}
