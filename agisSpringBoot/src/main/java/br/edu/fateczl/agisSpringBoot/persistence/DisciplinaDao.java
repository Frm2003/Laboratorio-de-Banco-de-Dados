package br.edu.fateczl.agisSpringBoot.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.agisSpringBoot.model.Aluno;
import br.edu.fateczl.agisSpringBoot.model.Curso;
import br.edu.fateczl.agisSpringBoot.model.Disciplina;
import br.edu.fateczl.agisSpringBoot.model.Professor;

@Repository
public class DisciplinaDao implements InterfaceDao<Disciplina> {
	private Connection c;
	
	@Autowired
	private GenericDao gdao;

	@Override
	public boolean iudCrud(String acao, Disciplina t) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		String sql = "{CALL iudDisciplina (?, ?, ?, ?, ?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setInt(2, t.getCod());
		cs.setString(3, t.getNome());
		cs.setInt(4, t.getQtdAulas());
		cs.setInt(5, t.getSemestre());
		cs.setString(6, t.getHorario());
		cs.setInt(7, t.getCurso().getCod());
		cs.setInt(8, t.getProfessor().getCod());
		cs.registerOutParameter(9, Types.BIT);
		
		cs.execute();
		boolean valido = cs.getBoolean(9);
		cs.close();
		
		c.close();
		
		return valido;
	}

	@Override
	public Disciplina buscar(Disciplina t) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		String sql = "select * from selectDisciplina where cod = ?";
		
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
			t.setQtdAulas(rs.getInt("qtdAulas"));
			t.setSemestre(rs.getInt("semestre"));
			t.setHorario(rs.getString("horario"));
			
			Professor p = new Professor();
			p.setCod(rs.getInt("codp"));
			p.setNome(rs.getString("nomep"));
			
			t.setCurso(cur);
			
			t.setProfessor(p);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return t;
	}

	@Override
	public List<Disciplina> listar() throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
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
			d.setQtdAulas(rs.getInt("qtdAulas"));
			d.setSemestre(rs.getInt("semestre"));
			d.setHorario(rs.getString("horario"));
			
			d.setCurso(cur);
			
			Professor p = new Professor();
			p.setCod(rs.getInt("codp"));
			p.setNome(rs.getString("nomep"));
			
			d.setProfessor(p);
			
			lista.add(d);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return lista;
	}
	
	public List<Disciplina> listarDisicplinasNaoMatriculadas(Aluno a) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();

		String sql = "select d.cod as dcod, d.nome as nomed, d.qtdAulas as qtdAulas, d.semestre as dsem, d.horario as horario, "
				+ "		c.cod as codc, c.nome as nomec, c.cargaHoraria as cargaHoraria, c.sigla as sigla, c.turno as turno, c.enade as enade,"
				+ "     p.cod as codp, p.nome as nomep"
				+ "	from Disciplina d inner join Professor p on p.cod = d.codProfessor"
				+ "     inner join CursoDisciplina cd on d.cod = cd.codDisci "
				+ "		inner join Curso c on c.cod = cd.codCurso "
				+ "		left join Matricula m on d.cod = m.codDisci and m.ra = ?" + 
				"	where m.ra is null";

		List<Disciplina> lista = new ArrayList<>();

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, a.getRa());

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

			d.setCod(rs.getInt("dcod"));
			d.setNome(rs.getString("nomed"));
			d.setQtdAulas(rs.getInt("qtdAulas"));
			d.setSemestre(rs.getInt("dsem"));
			d.setHorario(rs.getString("horario"));

			Professor p = new Professor();
			p.setCod(rs.getInt("codp"));
			p.setNome(rs.getString("nomep"));

			d.setProfessor(p);
			d.setCurso(cur);

			lista.add(d);
		}

		rs.close();
		ps.close();

		c.close();

		return lista;
	}
	
}
