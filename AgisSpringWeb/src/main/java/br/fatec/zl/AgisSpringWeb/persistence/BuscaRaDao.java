package br.fatec.zl.AgisSpringWeb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.fatec.zl.AgisSpringWeb.model.Aluno;
import br.fatec.zl.AgisSpringWeb.model.Curso;
import br.fatec.zl.AgisSpringWeb.model.Disciplina;
import br.fatec.zl.AgisSpringWeb.model.Matricula;

@Repository
public class BuscaRaDao {
	private Connection c;
	
	@Autowired
	private GenericDao gdao;
	
	public List<Disciplina> listarDisicplinasNaoMatriculadas(Aluno a) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		String sql = "select d.cod as dcod, d.nome as nomed, d.qtdAulas as qtdAulas, d.semestre as dsem, d.horario as horario, "
				+ "		c.cod as codc, c.nome as nomec, c.cargaHoraria as cargaHoraria, c.sigla as sigla, c.turno as turno, c.enade as enade "
				+ "	from Disciplina d inner join CursoDisciplina cd on d.cod = cd.codDisci "
				+ "		inner join Curso c on c.cod = cd.codCurso "
				+ "		left join Matricula m on d.cod = m.codDisci and m.ra = ?"
				+ "	where m.ra is null";
		
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
			
			d.setCurso(cur);
			
			lista.add(d);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return lista;
	}
}
