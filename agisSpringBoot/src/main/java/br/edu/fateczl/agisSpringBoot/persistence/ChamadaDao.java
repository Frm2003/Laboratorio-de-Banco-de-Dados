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
import br.edu.fateczl.agisSpringBoot.model.Chamada;
import br.edu.fateczl.agisSpringBoot.model.Curso;
import br.edu.fateczl.agisSpringBoot.model.Disciplina;
import br.edu.fateczl.agisSpringBoot.model.Professor;

@Repository
public class ChamadaDao {
	private Connection c;
	
	@Autowired
	private GenericDao gdao;
	
	public boolean iudCrud(String acao, Chamada ch) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		String sql = "{CALL iudChamada (?, ?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setString(2, ch.getAluno().getRa());
		cs.setInt(3, ch.getDisciplina().getCod());
		cs.setInt(4, ch.getFaltas());
		cs.setString(5, ch.getData());
		cs.registerOutParameter(6, Types.BIT);
	
		cs.execute();
		boolean valido = cs.getBoolean(6);
		cs.close();
		
		c.close();
		
		return valido;
	}
	
	public List<Chamada> geraChamada(Disciplina d) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		
		String sql = "select * from geraChamada(?)";
		
		List<Chamada> lista = new ArrayList<>();

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, d.getCod());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Chamada c = new Chamada();
			
			Aluno a = new Aluno();
			
			a.setRa(rs.getString("ra"));
			a.setNome(rs.getString("nomeAluno"));
			
			c.setAluno(a);
			c.setDisciplina(d);
			
			lista.add(c);
		}

		rs.close();
		ps.close();

		c.close();
		
		return lista;
	}
	
	public List<Chamada> buscaChamada(Disciplina d, String data) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		
		String sql = "select * from selectChamada where cod = ? and dataChamada = ?";
		
		List<Chamada> lista = new ArrayList<>();

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, d.getCod());
		ps.setString(2, data);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Chamada ch = new Chamada();
			
			Aluno a = new Aluno();
			
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

			Curso cur = new Curso();

			cur.setCod(rs.getInt("codc"));
			cur.setNome(rs.getString("nomec"));
			cur.setCargaHoraria(rs.getInt("cargaHoraria"));
			cur.setSigla(rs.getString("sigla"));
			cur.setEnade(rs.getFloat("enade"));
			cur.setTurno(rs.getString("turno"));

			Disciplina dd = new Disciplina();

			dd.setCod(rs.getInt("cod"));
			dd.setNome(rs.getString("nomee"));
			dd.setQtdAulas(rs.getInt("qtdAulas"));
			dd.setSemestre(rs.getInt("semestre"));
			dd.setHorario(rs.getString("horario"));

			dd.setCurso(cur);

			Professor p = new Professor();

			p.setCod(rs.getInt("codp"));
			p.setNome(rs.getString("nomep"));

			dd.setProfessor(p);
					
			a.setCurso(cur);
			
			ch.setAluno(a);
			ch.setDisciplina(dd);
			ch.setFaltas(rs.getInt("falta"));
			ch.setData(rs.getString("dataChamada"));
			
			lista.add(ch);
			
			System.out.println(ch.toString());
		}

		rs.close();
		ps.close();

		c.close();
		
		return lista;
	}
}
