package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import model.Curso;
import model.Disciplina;
import model.Matricula;

public class MatriculaDao {
	private Connection c;
	
	public MatriculaDao() throws ClassNotFoundException, SQLException {
		GenericDAO gdao = new GenericDAO();
		c = gdao.conexao();
	}
	
	public void iudCrud(String acao, Matricula m) throws SQLException{
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
	
	public List<Disciplina> buscarDisciplinas(Aluno t) throws SQLException {
		String sql = "select d.cod as dcod, d.nome as nomed, d.qtdHoraSemanais as qtdAulas, d.semestre as dsem, d.horario as horario, "
				+ "		c.cod as codc, c.nome as nomec, c.cargaHoraria as cargaHoraria, c.sigla as sigla, c.turno as turno, c.enade as enade "
				+ "from Disciplina d inner join CursoDisciplina cd on d.cod = cd.codDisci inner join Curso c on c.cod = cd.codCurso "
				+ "	left join Matricula m on d.cod = m.codDisci and m.ra = ?"
				+ "where m.ra is null";
		
		List<Disciplina> matriculas = new ArrayList<>();
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, t.getRa());

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
			d.setQtdHorasSemanais(rs.getInt("qtdAulas"));
			d.setSemestre(rs.getInt("dsem"));
			d.setHorario(rs.getString("horario"));
			d.setCurso(cur);
			
			matriculas.add(d);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return matriculas;
	}
}

/*
Matricula m = new Matricula();
			
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
			
			m.setAluno(a);
			
			Curso cur = new Curso();
			cur.setNome(rs.getString("nomec"));
			cur.setCargaHoraria(rs.getInt("cargaHoraria"));
			cur.setSigla(rs.getString("sigla"));
			cur.setEnade(rs.getFloat("enade"));
			cur.setTurno(rs.getString("turno"));
			
			a.setCurso(cur);
			
			Disciplina d = new Disciplina();
			
			d.setCod(rs.getInt("dcod"));
			d.setNome(rs.getString("nomed"));
			d.setQtdHorasSemanais(rs.getInt("qtdAulas"));
			d.setSemestre(rs.getInt("dsem"));
			d.setHorario(rs.getString("horario"));
			
			d.setCurso(cur);
			
			m.setDisciplina(d);
*/

