package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.Getter;

import model.Curso;
import persistence.CursoDao;

public class CursoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Curso> cursos = new ArrayList<>();
	
    public CursoServlet() throws ClassNotFoundException, SQLException {
    	CursoDao cdao = new CursoDao();
    	cursos.addAll(cdao.listar());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("cursos", cursos);
		RequestDispatcher rd = request.getRequestDispatcher("secretariaCurso.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cod = request.getParameter("cod");
		String nome = request.getParameter("nome");
		String cargaHoraria = request.getParameter("cargaHoraria");
		String sigla = request.getParameter("sigla");
		String enade = request.getParameter("enade");
		String turno = request.getParameter("turno");
		
		try {
			switch (request.getParameter("botao")) {
			case "Inserir":
				inserir(nome, Integer.parseInt(cargaHoraria), sigla, Float.parseFloat(enade), turno);
				break;
			case "Buscar":
				if (!cursos.isEmpty()) {
					request.setAttribute("curso", buscar(Integer.parseInt(cod)));
				}
				break;
			case "Atualizar":
				if (!cursos.isEmpty()) {					
					atualizar(Integer.parseInt(cod), nome, Integer.parseInt(cargaHoraria), sigla, Float.parseFloat(enade), turno);
				}
				break;
			case "Deletar":
				if (!cursos.isEmpty()) {					
					deletar(Integer.parseInt(cod));
				} 
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.setAttribute("cursos", cursos);
			RequestDispatcher rd = request.getRequestDispatcher("secretariaCurso.jsp");
			rd.forward(request, response);
		}
		
	}
	
	public void inserir(String nome, int cargaHoraria, String sigla, float enade, String turno) throws ClassNotFoundException, SQLException {
		Curso c = new Curso();
		CursoDao cdao = new CursoDao();
		
		c.setCod(1 + cursos.size());
		c.setNome(nome);
		c.setCargaHoraria(cargaHoraria);
		c.setSigla(sigla);
		c.setEnade(enade);
		c.setTurno(turno);
		
		boolean valido = cdao.iudCrud("i", c);
		
		if (valido) {
			cursos.add(c);	
		}		
	}
	
	public Curso buscar(int cod) throws ClassNotFoundException, SQLException {
		for (Curso c : cursos) {
			if (c.getCod() == cod) {
				CursoDao cdao = new CursoDao();
				return cdao.buscar(c);
			} 
		}
		return null;
	}
	
	public void atualizar(int cod, String nome, int cargaHoraria, String sigla, float enade, String turno) throws ClassNotFoundException, SQLException {
		for (Curso c : cursos) {
			if (c.getCod() == cod) {
				CursoDao cdao = new CursoDao();
				boolean valido = cdao.iudCrud("u", c);
				if (valido) {									
					c.setNome(nome);
					c.setCargaHoraria(cargaHoraria);
					c.setSigla(sigla);
					c.setEnade(enade);
					c.setTurno(turno);
				}
				break;
			} 
		}
	}
	
	public void deletar(int cod) throws ClassNotFoundException, SQLException {
		for (Curso c : cursos) {
			if (c.getCod() == cod) {
				CursoDao cdao = new CursoDao();
				boolean valido = cdao.iudCrud("d", c);
				if (valido) {
					cursos.remove(c);					
				}
				break;
			} 
		}
	}
}
