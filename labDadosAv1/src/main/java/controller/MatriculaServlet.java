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
import model.Aluno;
import model.Disciplina;
import model.Matricula;
import persistence.AlunoDao;
import persistence.MatriculaDao;


public class MatriculaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private List<Aluno> alunos = new ArrayList<>();
	private List<Disciplina> disciDispo = new ArrayList<>();
	private List<Matricula> matriculas = new ArrayList<>();
	
	private String ra;
		
    public MatriculaServlet() throws ClassNotFoundException, SQLException {
    	AlunoDao adao = new AlunoDao();
    	alunos.addAll(adao.listar());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("alunoMatricula.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ra = request.getParameter("ra");
		String cod = request.getParameter("cod");	
		
		try {
			switch (request.getParameter("botao")) {
			case "Buscar":
				disciDispo.removeAll(disciDispo);
				disciDispo.addAll(buscar(ra));
				this.ra = ra;
				request.setAttribute("disciplinas", disciDispo);
				break;
			case "Matricular":
				inserir(this.ra, Integer.parseInt(cod));
				disciDispo.removeAll(disciDispo);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.setAttribute("ra", ra);
			RequestDispatcher rd = request.getRequestDispatcher("alunoMatricula.jsp");
			rd.forward(request, response);
		}
	}
	
	private void inserir(String ra, int codDisci) throws ClassNotFoundException, SQLException {
		Matricula m = new Matricula();
		MatriculaDao mdao = new MatriculaDao();
		
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				m.setAluno(a);
			}
		}
		
		for (Disciplina d : disciDispo) {
			if (d.getCod() == codDisci) {
				m.setDisciplina(d);
			}
		}
		
		mdao.iudCrud("i", m);
		matriculas.add(m);
	}

	public List<Disciplina> buscar(String ra) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				MatriculaDao mdao = new MatriculaDao();
				return mdao.buscarDisciplinas(a);
			}
		}
		return null;
	}

}
