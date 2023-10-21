package br.edu.fateczl.agisSpringBoot.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.edu.fateczl.agisSpringBoot.model.Professor;

@Controller
public class ProfessorDao {
	private Connection c;
	
	@Autowired
	private GenericDao gdao;
	
	public List<Professor> listar() throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<Professor> lista = new ArrayList<>();
		String sql = "select cod, nome from Professor";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Professor p = new Professor();
			
			p.setCod(rs.getInt("cod"));
			p.setNome(rs.getString("nome"));
			
			lista.add(p);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return lista;
	}
}
