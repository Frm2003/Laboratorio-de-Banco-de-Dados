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
import br.edu.fateczl.agisSpringBoot.model.Matricula;
import br.edu.fateczl.agisSpringBoot.model.Professor;

@Repository
public class MatriculaDao {
	private Connection c;

	@Autowired
	private GenericDao gdao;

	public void iudCrud(String acao, Matricula m) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		String sql = "{CALL iudMatricula (?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);

		cs.setString(1, acao);
		cs.setString(2, m.getAluno().getRa());
		cs.setInt(3, m.getDisciplina().getCod());
		cs.registerOutParameter(4, Types.VARCHAR);

		cs.execute();

		cs.close();
		c.close();
	}

	public List<Matricula> listarMatriculas(Aluno a) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		String sql = "select * from selectMatricula where Ra = ?";

		List<Matricula> lista = new ArrayList<>();

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, a.getRa());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Matricula m = new Matricula();

			a.setRa(rs.getString("Ra"));
			a.setCpf(rs.getString("cpf"));
			a.setNome(rs.getString("nome"));
			a.setNomeSocial(rs.getString("nomeS"));
			a.setDataNasc(rs.getString("dataNasc"));
			a.setEmailPessoal(rs.getString("emailP"));
			a.setEmailCorporativo(rs.getString("emailC"));
			a.setDataConc2grau(rs.getString("dataConc2grau"));
			a.setIsntConc2grau(rs.getString("inst"));
			a.setPtVestibular(rs.getInt("ptVest"));
			a.setPosVestibular(rs.getInt("posVest"));
			a.setAnoDeIngresso(rs.getInt("anoi"));
			a.setSemestreDeIngresso(rs.getInt("semi"));
			a.setAnoLimite(rs.getInt("al"));
			a.setSemestreLimite(rs.getInt("seml"));
			a.setDataMatricula(rs.getString("dataMatricula"));

			m.setAluno(a);

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

			m.setDisciplina(d);
			m.setSemestre(rs.getInt("semestrem"));
			m.setAno(rs.getInt("ano"));
			m.setSituacao(rs.getString("situacao"));
			m.setNota(rs.getInt("nota"));
			m.setAprovado(rs.getBoolean("aprovado"));

			lista.add(m);
		}

		rs.close();
		ps.close();

		c.close();

		return lista;
	}
}