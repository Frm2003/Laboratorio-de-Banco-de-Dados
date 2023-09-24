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
	
	public void iudCrud(String acao, Disciplina t) throws SQLException {
		String sql = "{CALL iudDisciplina (?, ?, ?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setInt(2, t.getCod());
		cs.setString(3, t.getNome());
		cs.setInt(4, t.getQtdHorasSemanais());
		cs.setInt(5, t.getSemestre());
		cs.setString(6, t.getHorario());
		cs.registerOutParameter(7, Types.VARCHAR);
		
		cs.execute();

		cs.close();
		c.close();
	}

	@Override
	public Disciplina buscar(Disciplina t) throws ClassNotFoundException, SQLException {
		String sql = "select nome, qtdHoraSemanais, horario, semestre from Disciplina where cod = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, t.getCod());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			t.setNome(rs.getString("nome"));
			t.setQtdHorasSemanais(rs.getInt("qtdHoraSemanais"));
			t.setSemestre(rs.getInt("semestre"));
			t.setHorario(rs.getString("horario"));
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return t;
	}

	@Override
	public List<Disciplina> listar() throws ClassNotFoundException, SQLException {
		List<Disciplina> lista = new ArrayList<>();
		String sql = "select cod, nome, qtdHoraSemanais, semestre, horario from Disciplina";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Disciplina d = new Disciplina();
			
			d.setCod(rs.getInt("cod"));
			d.setNome(rs.getString("nome"));
			d.setQtdHorasSemanais(rs.getInt("qtdHoraSemanais"));
			d.setSemestre(rs.getInt("semestre"));
			d.setHorario(rs.getString("horario"));
			
			lista.add(d);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return lista;
	}
	
	public void vincular(Disciplina d, Curso cur) throws ClassNotFoundException, SQLException {
		String sql = "insert into CursoDisciplina values (?, ?)";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cur.getCod());
		ps.setInt(2, d.getCod());
		
		ps.execute();
		ps.close();
		
		c.close();
	}
	
}
