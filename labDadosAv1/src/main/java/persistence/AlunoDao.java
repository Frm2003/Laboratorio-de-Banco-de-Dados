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

public class AlunoDao implements InterfaceDao<Aluno>{
	private static Connection c;
	
	public AlunoDao() throws ClassNotFoundException, SQLException {
		GenericDAO gdao = new GenericDAO();
		c = gdao.conexao();
	}
	
	@Override
	public boolean iudCrud(String acao, Aluno t) throws ClassNotFoundException, SQLException {
		String sql = "{CALL iudAluno (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setString(2, t.getRa());
		cs.setString(3, t.getCpf());
		cs.setString(4, t.getNome());
		cs.setString(5, t.getNomeSocial());
		cs.setString(6, t.getDataNasc());
		cs.setString(7, t.getEmailPessoal());
		cs.setString(8, t.getEmailCorporativo());
		cs.setString(9, t.getDataConc2grau());
		cs.setString(10, t.getIsntConc2grau());
		cs.setInt(11, t.getPtVestibular());
		cs.setInt(12, t.getPosVestibular());
		cs.setInt(13, t.getCurso().getCod());
		cs.registerOutParameter(14, Types.BIT);
	
		cs.execute();
		boolean valido = cs.getBoolean(14);
		cs.close();
		
		c.close();
		
		return valido;
	}

	@Override
	public Aluno buscar(Aluno t) throws ClassNotFoundException, SQLException {
		String sql = "select * from selectAluno where Ra = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, t.getRa());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			t.setRa(rs.getString("Ra"));
			t.setCpf(rs.getString("cpf"));
			t.setNome(rs.getString("nome"));
			t.setNomeSocial(rs.getString("nomeS"));
			t.setDataNasc(rs.getString("dataNasc"));
			t.setEmailPessoal(rs.getString("emailP"));
			t.setEmailCorporativo(rs.getString("emailC"));
			t.setDataConc2grau(rs.getString("dataConc2grau"));
			t.setIsntConc2grau(rs.getString("inst"));
			t.setPtVestibular(rs.getInt("ptVest"));
			t.setPosVestibular(rs.getInt("posVest"));
			t.setAnoDeIngresso(rs.getInt("anoi"));
			t.setSemestreDeIngresso(rs.getInt("semi"));
			t.setAnoLimite(rs.getInt("al"));
			t.setSemestreLimite(rs.getInt("seml"));
			
			Curso cur = new Curso();
			
			cur.setCod(rs.getInt("codc"));
			cur.setNome(rs.getString("nomec"));
			cur.setCargaHoraria(rs.getInt("cargaHoraria"));
			cur.setSigla(rs.getString("sigla"));
			cur.setEnade(rs.getFloat("enade"));
			cur.setTurno(rs.getString("turno"));
			
			t.setCurso(cur);
		}

		rs.close();
		ps.close();
		
		c.close();
		
		return t;
	}

	@Override
	public List<Aluno> listar() throws ClassNotFoundException, SQLException {
		List<Aluno> alunos = new ArrayList<>();
		
		String sql = "select * from selectAluno";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Aluno t = new Aluno();
			t.setRa(rs.getString("Ra"));
			t.setCpf(rs.getString("cpf"));
			t.setNome(rs.getString("nome"));
			t.setNomeSocial(rs.getString("nomeS"));
			t.setDataNasc(rs.getString("dataNasc"));
			t.setEmailPessoal(rs.getString("emailP"));
			t.setEmailCorporativo(rs.getString("emailC"));
			t.setDataConc2grau(rs.getString("dataConc2grau"));
			t.setIsntConc2grau(rs.getString("inst"));
			t.setPtVestibular(rs.getInt("ptVest"));
			t.setPosVestibular(rs.getInt("posVest"));
			t.setAnoDeIngresso(rs.getInt("anoi"));
			t.setSemestreDeIngresso(rs.getInt("semi"));
			t.setAnoLimite(rs.getInt("al"));
			t.setSemestreLimite(rs.getInt("seml"));
			
			Curso cur = new Curso();
			
			cur.setCod(rs.getInt("codc"));
			cur.setNome(rs.getString("nomec"));
			cur.setCargaHoraria(rs.getInt("cargaHoraria"));
			cur.setSigla(rs.getString("sigla"));
			cur.setEnade(rs.getFloat("enade"));
			cur.setTurno(rs.getString("turno"));
			
			t.setCurso(cur);
			
			alunos.add(t);
		}
		
		return alunos;
	}
	
	public static String geraRa() throws ClassNotFoundException, SQLException {
		String sql = "{CALL geraRa (?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.registerOutParameter(1, Types.VARCHAR);
		cs.execute();
		String ra = cs.getString(1);
		
		cs.close();
		
		return ra;
	}

}