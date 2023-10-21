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
import model.Disciplina;

public class DisciplinaDao implements InterfaceDao<Disciplina> {
	private Connection c;
	
	public DisciplinaDao() throws ClassNotFoundException, SQLException {
		GenericDAO gdao = new GenericDAO();
		c = gdao.conexao();
	}	
	
	public boolean iudCrud(String acao, Disciplina t) throws SQLException {
		String sql = "{CALL iudDisciplina (?, ?, ?, ?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setInt(2, t.getCod());
		cs.setString(3, t.getNome());
		cs.setInt(4, t.getQtdHorasSemanais());
		cs.setInt(5, t.getSemestre());
		cs.setString(6, t.getHorario());
		cs.setInt(7, t.getCurso().getCod());
		cs.registerOutParameter(8, Types.BIT);
		
		cs.execute();
		boolean valido = cs.getBoolean(8);
		cs.close();
		
		c.close();
		
		return valido;
	}

	@Override
	public Disciplina buscar(Disciplina t) throws ClassNotFoundException, SQLException {
		String sql = "select * from selectDisciplina where dcod = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, t.getCod());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Curso cur = new Curso();
			
			cur.setCod(rs.getInt("codc"));
			cur.setNome(rs.getString("nomec"));
			cur.setCargaHoraria(rs.getInt("cargaHoraria"));
			cur.setSigla(rs.getString("sigla"));
			cur.setEnade(rs.getFloat("enade"));
			cur.setTurno(rs.getString("turno"));
			
			t.setNome(rs.getString("nomee"));
			t.setQtdHorasSemanais(rs.getInt("qtdHoraSemanais"));
			t.setSemestre(rs.getInt("semestre"));
			t.setHorario(rs.getString("horario"));
			
			t.setCurso(cur);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return t;
	}

	@Override
	public List<Disciplina> listar() throws ClassNotFoundException, SQLException {
		List<Disciplina> lista = new ArrayList<>();
		String sql = "select * from selectDisciplina";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Curso cur = new Curso();
			
			cur.setCod(rs.getInt("codc"));
			cur.setNome(rs.getString("nomec"));
			cur.setCargaHoraria(rs.getInt("cargaHoraria"));
			cur.setSigla(rs.getString("sigla"));
			cur.setEnade(rs.getFloat("enade"));
			cur.setTurno(rs.getString("turno"));
			
			Disciplina d = new Disciplina();
			
			d.setCod(rs.getInt("cod"));
			d.setNome(rs.getString("nomee"));
			d.setQtdHorasSemanais(rs.getInt("qtdHoraSemanais"));
			d.setSemestre(rs.getInt("semestre"));
			d.setHorario(rs.getString("horario"));
			
			d.setCurso(cur);
			
			lista.add(d);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return lista;
	}
	
}
