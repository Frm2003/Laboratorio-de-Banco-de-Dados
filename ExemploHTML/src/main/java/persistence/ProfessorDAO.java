package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Professor;

public class ProfessorDAO implements interfaceDao<Professor>{
	private Connection c;

	public ProfessorDAO() throws ClassNotFoundException, SQLException {
		GenericDAO gdao = new GenericDAO();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserir(Professor p) throws SQLException {
		String sql = "INSERT INTO professor VALUES(?, ?, ?)";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, p.getCod());
		ps.setString(2, p.getNome());
		ps.setString(3, p.getData());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void atualizar(Professor p) throws SQLException {
		String sql = "UPDATE professor SET nome = ?, dataNasc = ? WHERE cod = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, p.getNome());
		ps.setString(2, p.getData());
		ps.setInt(3, p.getCod());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void excluir(Professor p) throws SQLException {
		String sql = "DELETE professor WHERE cod = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, p.getCod());
		
		ps.execute();
		ps.close();
		
	}

	@Override
	public Professor busca(Professor p) throws SQLException {
		String sql = "SELECT nome, dataNasc FROM professor WHERE cod = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);	
		ps.setInt(1, p.getCod());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			p.setNome(rs.getString("nome"));
			p.setData(rs.getString("dataNasc"));
		}
		
		rs.close();
		ps.close();
		
		return p;
	}

	@Override
	public List<Professor> buscaAll() throws SQLException {
		List<Professor> professores = new ArrayList<>();
		
		String sql = "SELECT cod, nome, dataNasc FROM professor";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Professor p = new Professor();
			p.setCod(rs.getInt("cod"));
			p.setNome(rs.getString("nome"));
			p.setData(rs.getString("dataNasc"));
			
			professores.add(p);
		}
		
		return professores;
	}
}
