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
import persistence.DisciplinaDao;

public class DisciplinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	private CursoServlet cs = new CursoServlet();
       
    public DisciplinaServlet() throws ClassNotFoundException, SQLException {
    	DisciplinaDao ddao = new DisciplinaDao();
    	disciplinas.addAll(ddao.listar());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("cursos", cs.getCursos());
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
				inserir(nome, Integer.parseInt(qtdHorasSemanais), Integer.parseInt(semestre), horario);
				break;
			case "Buscar":
				if (!disciplinas.isEmpty()) {
					request.setAttribute("disciplina", buscar(Integer.parseInt(cod)));
				}
				break;
			case "Atualizar":
				if (!disciplinas.isEmpty()) {					
					atualizar(Integer.parseInt(cod), nome, Integer.parseInt(qtdHorasSemanais), Integer.parseInt(semestre), horario);
				}
				break;
			case "Deletar":
				if (!disciplinas.isEmpty()) {					
					deletar(Integer.parseInt(cod));
				} 
				break;
			case "Vincular":
				vincular(Integer.parseInt(cod), Integer.parseInt(curso));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.setAttribute("cursos", cs.getCursos());
			request.setAttribute("disciplinas", disciplinas);
			RequestDispatcher rd = request.getRequestDispatcher("secretariaDisciplina.jsp");
			rd.forward(request, response);
		}
	}

	private void inserir(String nome, int qtdHorasSemanais, int semestre, String horario) throws ClassNotFoundException, SQLException {
		Disciplina d = new Disciplina();
		DisciplinaDao ddao = new DisciplinaDao();
		
		d.setCod(1001 + disciplinas.size());
		d.setNome(nome);
		d.setQtdHorasSemanais(qtdHorasSemanais);
		d.setSemestre(semestre);
		d.setHorario(horario);
		
		ddao.iudCrud("i", d);
		disciplinas.add(d);
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

	private void atualizar(int cod, String nome, int qtdHorasSemanais, int semestre, String horario) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				DisciplinaDao ddao = new DisciplinaDao();
				d.setNome(nome);
				d.setQtdHorasSemanais(qtdHorasSemanais);
				d.setSemestre(semestre);
				d.setHorario(horario);
				ddao.iudCrud("u", d);
				break;
			}
		}
	}

	private void deletar(int cod) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				DisciplinaDao ddao = new DisciplinaDao();
				ddao.iudCrud("d", d);
				disciplinas.remove(d);
				break;
			}
		}
	}
	
	private void vincular(int cod, int curso) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				for (Curso c : cs.getCursos()) {
					if (c.getCod() == curso) {
						d.setCurso(cs.buscar(c.getCod()));
						DisciplinaDao ddao = new DisciplinaDao();
						ddao.vincular(d, c);
						break;
					}
				}
				break;
			} 
		}
	}

}
