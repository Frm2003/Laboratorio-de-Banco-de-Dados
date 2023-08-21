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

import model.Motorista;
import persistence.MotoristaDao;

@WebServlet("/Motorista")
public class MotoristaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<Motorista> motoristas = new ArrayList<>();
	private MotoristaDao mdao;

    public MotoristaServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			mdao = new MotoristaDao();
			if (motoristas.isEmpty()) { motoristas.addAll(mdao.buscaAll()); }
			mdao.desconectar();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cod = Integer.parseInt(request.getParameter("cod"));
		String nome = request.getParameter("nome");
		String nat = request.getParameter("naturalidade");
		
		String mensagem = "";
		
		try {
			mdao = new MotoristaDao();
			switch (request.getParameter("botao")) {
			case "Criar":
				criar(cod, nome, nat);
				break;
			case "Buscar":
				if (!motoristas.isEmpty()) {					
					request.setAttribute("motorista", buscar(cod));
				} else {
					mensagem = "lista vazia";
				}
				break;
			case "Alterar":
				if (!motoristas.isEmpty()) {
					alterar(cod, nome, nat);
				} else {
					mensagem = "lista vazia";
				}
				break;
			case "Remover":
				if (!motoristas.isEmpty()) {
					remover(cod);
				} else {
					mensagem = "lista vazia";
				}
				break;
			}
			
			mdao.desconectar();
		} catch (SQLException | ClassNotFoundException e) {
			mensagem = e.getMessage();
		} finally {
			request.setAttribute("mensagem", mensagem);
			
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}
	}

	private void criar(int cod, String nome, String nat) throws SQLException {
		Motorista m = new Motorista();
		
		m.setCod(cod);
		m.setNome(nome);
		m.setNaturalidade(nat);
		
		mdao.criar(m);
		motoristas.add(m);
	}

	private Motorista buscar(int cod) throws SQLException {
		for (Motorista m : motoristas) {
			if (m.getCod() == cod) {
				return mdao.buscar(m);
			}
		}
		return null;	
	}
	
	private void alterar(int cod, String nome, String nat) throws SQLException {
		for (Motorista m : motoristas) {
			if (m.getCod() == cod) {
				m.setNome(nome);
				m.setNaturalidade(nat);
				mdao.alterar(m);
				break;
			}
		}
	}
	
	private void remover(int cod) throws SQLException {
		for (Motorista m : motoristas) {
			if (m.getCod() == cod) {
				mdao.remover(m);
				motoristas.remove(m);
				break;
			}
		}
	}
}
