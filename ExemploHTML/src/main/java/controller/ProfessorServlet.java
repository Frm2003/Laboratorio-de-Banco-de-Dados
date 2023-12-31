package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Professor;
import persistence.ProfessorDAO;

@WebServlet("/professor")
public class ProfessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Professor> listaProfs = new ArrayList<>();
	ProfessorDAO pdao = null;

	public ProfessorServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cod = Integer.parseInt(request.getParameter("cod"));
		String nome = request.getParameter("nome");
		String dataNasc = request.getParameter("dataNasc");
		String bt = request.getParameter("botao");
		
		String erro = "";
		
		try {
			pdao = new ProfessorDAO();
			
			switch (bt) {
			case "Criar":
				criar(cod, nome, dataNasc);
				break;
			case "Alterar":
				alterar(cod, nome, dataNasc);
				break;
			case "Excluir":
				excluir(cod);
				break;
			case "Buscar":
				request.setAttribute("professor", buscar(cod));
				break;
			case "Listar":
				listar();
				break;
			}
		} catch (Exception e) {
			erro = e.getMessage();
		} finally {
			try {
				pdao.desconectar();
			} catch (SQLException e) {
				erro = e.getMessage();
			}
			
			request.setAttribute("professores", listaProfs);
			request.setAttribute("erro", erro);
			
			RequestDispatcher rd = request.getRequestDispatcher("professor.jsp");
			rd.forward(request, response);
		}
	}
	


	public void criar(int cod, String nome, String dataNasc) throws SQLException {
		Professor p = new Professor();
		
		p.setCod(cod);
		p.setNome(nome);
		p.setData(dataNasc);
		
		pdao.inserir(p);
		listaProfs.add(p);
	}
	
	private void alterar(int cod, String nome, String dataNasc) throws SQLException {
		for (Professor p : listaProfs) {
			if (p.getCod() == cod) {
				p.setNome(nome);
				p.setData(dataNasc);
				pdao.atualizar(p);
				break;
			}
		}
	}
	
	private void excluir(int cod) throws SQLException {
		for (Professor p : listaProfs) {
			if (p.getCod() == cod) {
				pdao.excluir(p);
				listaProfs.remove(p);
				break;
			}
		}		
	}
	
	private Professor buscar(int cod) throws SQLException {
		for (Professor p : listaProfs) {
			if (p.getCod() == cod) {
				return pdao.busca(p);
			}
		}
		return null;
	}
	
	private void listar() throws SQLException {
		listaProfs.addAll(pdao.buscaAll());
	}
}
