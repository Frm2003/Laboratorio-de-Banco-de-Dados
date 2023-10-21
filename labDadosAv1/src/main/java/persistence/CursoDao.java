package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import model.Curso;

public class CursoDao implements InterfaceDao<Curso> {
	private Connection c;
	
	public CursoDao() throws ClassNotFoundException, SQLException {
		GenericDAO gdao = new GenericDAO();
		c = gdao.conexao();
	}
	
	@Override
	public boolean iudCrud(String acao, Curso t) throws ClassNotFoundException, SQLException {
		String sql = "{CALL iudCurso (?, ?, ?, ?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setInt(2, t.getCod());
		cs.setString(3, t.getNome());
		cs.setInt(4, t.getCargaHoraria());
		cs.setString(5, t.getSigla());
		cs.setFloat(6, t.getEnade());
		cs.setString(7, t.getTurno());
		cs.registerOutParameter(8, Types.BIT);
		
		cs.execute();
		boolean valido = cs.getBoolean(8);
		cs.close();

		c.close();
		
		return valido;
	}
	
	@Override
	public Curso buscar(Curso cur) throws ClassNotFoundException, SQLException {
		String sql = "select nome, cargaHoraria, sigla, enade, turno from Curso where cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cur.getCod());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			cur.setNome(rs.getString("nome"));
			cur.setCargaHoraria(rs.getInt("cargaHoraria"));
			cur.setSigla(rs.getString("sigla"));
			cur.setEnade(rs.getFloat("enade"));
			cur.setTurno(rs.getString("turno"));
		}

		rs.close();
		ps.close();
		
		c.close();
		
		return cur;
	}

	@Override
	public List<Curso> listar() throws ClassNotFoundException, SQLException {
		List<Curso> cursos = new ArrayList<>();
		
		String sql = "select cod, nome, cargaHoraria, sigla, enade, turno from Curso";

		PreparedStatement ps = c.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Curso cur = new Curso();
			
			cur.setCod(rs.getInt("cod"));
			cur.setNome(rs.getString("nome"));
			cur.setCargaHoraria(rs.getInt("cargaHoraria"));
			cur.setSigla(rs.getString("sigla"));
			cur.setEnade(rs.getFloat("enade"));
			cur.setTurno(rs.getString("turno"));
			
			cursos.add(cur);
		}

		rs.close();
		ps.close();
		
		c.close();
		
		return cursos;
	}
	
}
