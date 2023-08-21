package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Motorista;

public class MotoristaDao implements interfaceDao<Motorista> {
	private Connection c;
	
	public MotoristaDao() throws ClassNotFoundException, SQLException {
		GenericDAO gdao = new GenericDAO();
		c = gdao.conexao();
	}
	
	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void criar(Motorista m) throws SQLException {
		String sql = "INSERT INTO Motorista VALUES(?, ?, ?)";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, m.getCod());
		ps.setString(2, m.getNome());
		ps.setString(3, m.getNaturalidade());
		
		ps.execute();
		ps.close();
	}

	@Override
	public Motorista buscar(Motorista m) throws SQLException {
		String sql = "SELECT nome, naturalidade FROM Motorista WHERE codigo = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, m.getCod());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			m.setNome(rs.getString("nome"));
			m.setNaturalidade(rs.getString("naturalidade"));
		}
		
		rs.close();
		ps.close();
		
		return m;
	}

	@Override
	public void alterar(Motorista m) throws SQLException {
		String sql = "UPDATE Motorista SET nome = ?, naturalidade = ? WHERE codigo = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, m.getNome());
		ps.setString(2, m.getNaturalidade());
		ps.setInt(3, m.getCod());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void remover(Motorista m) throws SQLException {
		String sql = "DELETE Motorista WHERE codigo = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, m.getCod());
		
		ps.execute();
		ps.close();
	}

	@Override
	public List<Motorista> buscaAll() throws SQLException {
		List<Motorista> motoristas = new ArrayList<>();
		
		String sql = "SELECT codigo, nome, naturalidade FROM Motorista";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Motorista m = new Motorista();
			m.setCod(rs.getInt("codigo"));
			m.setNome(rs.getString("nome"));
			m.setNaturalidade(rs.getString("naturalidade"));
			
			motoristas.add(m);
		}
		
		return motoristas;
	}	
}
