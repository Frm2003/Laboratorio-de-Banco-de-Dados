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
import model.Curso;
import model.Disciplina;
import persistence.CursoDao;
import persistence.DisciplinaDao;

public class DisciplinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<Disciplina> disciplinas = new ArrayList<>();
	private List<Curso> cursos = new ArrayList<>();
       
    public DisciplinaServlet() throws ClassNotFoundException, SQLException {
    	DisciplinaDao ddao = new DisciplinaDao();
    	disciplinas.addAll(ddao.listar());
    	CursoDao cdao = new CursoDao();
    	cursos.addAll(cdao.listar());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("cursos", cursos);
		request.setAttribute("disciplinas", disciplinas);
		RequestDispatcher rd = request.getRequestDispatcher("secretariaDisciplina.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cod = request.getParameter("cod");
		String nome = request.getParameter("nome");
		String qtdHorasSemanais = request.getParameter("qtdHorasSemanais");
		String semestre = request.getParameter("semestre");
		String horario = request.getParameter("horario");
		String curso = request.getParameter("curso");
		
		try {
			switch (request.getParameter("botao")) {
			case "Inserir":
				inserir(nome, Integer.parseInt(qtdHorasSemanais), Integer.parseInt(semestre), horario, Integer.parseInt(curso));
				break;
			case "Buscar":
				if (!disciplinas.isEmpty()) {
					request.setAttribute("disciplina", buscar(Integer.parseInt(cod)));
				}
				break;
			case "Atualizar":
				if (!disciplinas.isEmpty()) {					
					atualizar(Integer.parseInt(cod), nome, Integer.parseInt(qtdHorasSemanais), Integer.parseInt(semestre), horario, Integer.parseInt(curso));
				}
				break;
			case "Deletar":
				if (!disciplinas.isEmpty()) {					
					deletar(Integer.parseInt(cod));
				} 
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.setAttribute("cursos", cursos);
			request.setAttribute("disciplinas", disciplinas);
			RequestDispatcher rd = request.getRequestDispatcher("secretariaDisciplina.jsp");
			rd.forward(request, response);
		}
	}

	private void inserir(String nome, int qtdHorasSemanais, int semestre, String horario, int curso) throws ClassNotFoundException, SQLException {
		Disciplina d = new Disciplina();
		DisciplinaDao ddao = new DisciplinaDao();
		
		d.setCod(1001 + disciplinas.size());
		d.setNome(nome);
		d.setQtdHorasSemanais(qtdHorasSemanais);
		d.setSemestre(semestre);
		d.setHorario(horario);
		
		for (Curso c : cursos) {
			if (c.getCod() == curso) {
				d.setCurso(c);
			}
		}
		
		boolean valido = ddao.iudCrud("i", d);
		
		if (valido) {			
			disciplinas.add(d);
		}
	}

	private Disciplina buscar(int cod) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				DisciplinaDao ddao = new DisciplinaDao();
				return ddao.buscar(d);
			}
		}
		return null;
	}

	private void atualizar(int cod, String nome, int qtdHorasSemanais, int semestre, String horario, int curso) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				DisciplinaDao ddao = new DisciplinaDao();
				d.setNome(nome);
				d.setQtdHorasSemanais(qtdHorasSemanais);
				d.setSemestre(semestre);
				d.setHorario(horario);
			
				for (Curso c : cursos) {
					if (c.getCod() == curso) {
						d.setCurso(c);
					}
				}
				ddao.iudCrud("u", d);
				break;
			}
		}
	}

	private void deletar(int cod) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				DisciplinaDao ddao = new DisciplinaDao();
				boolean valido = ddao.iudCrud("d", d);
				if (valido) {
					disciplinas.remove(d);
				}
				break;
			}
		}
	}

}
