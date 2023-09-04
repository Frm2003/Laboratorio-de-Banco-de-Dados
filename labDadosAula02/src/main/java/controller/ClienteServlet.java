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

import model.Cliente;
import persistence.ClienteDao;

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<Cliente> clientes = new ArrayList<>();
	
	public ClienteServlet() {
		try {
			ClienteDao cdao = new ClienteDao();
			clientes.addAll(cdao.listar());
			for (Cliente cliente : clientes) {
				System.out.println(cliente.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher rd = request.getRequestDispatcher("cliente.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpf = request.getParameter("cpf");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String limiteCred = request.getParameter("limiteCred");
		String dataNasc = request.getParameter("dataNasc");
		
		try {
			switch (request.getParameter("botao")) {
			case "Criar":
				request.setAttribute("msg", inserir(cpf, nome, email, Double.parseDouble(limiteCred), dataNasc));
				break;
			case "Buscar":
				request.setAttribute("cliente", buscar(cpf));
				break;
			case "Alterar":
				atualizar(cpf, nome, email, Double.parseDouble(limiteCred), dataNasc);
				break;
			case "Remover":
				remover(cpf);
				break;
			}
			
		} catch (Exception e) {
			
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher("cliente.jsp");
			rd.forward(request, response);
		}
	}
	
	private String inserir(String cpf, String nome, String email, double limiteCred, String dataNasc) throws ClassNotFoundException, SQLException {
		Cliente c = new Cliente();
		ClienteDao cdao = new ClienteDao();
		
		c.setCpf(cpf);
		c.setNome(nome);
		c.setEmail(email);
		c.setLimiteCred(limiteCred);
		c.setDataNasc(dataNasc);
		
		String saida = cdao.crudCliente("i", c);
		clientes.add(c);
		
		return saida;
	}
	
	public Cliente buscar(String cpf) throws ClassNotFoundException, SQLException {
		for (Cliente c : clientes) {
			if (c.getCpf().equals(cpf)) {
				ClienteDao cdao = new ClienteDao();
				return c;
			}
		}
		return null;
	}
	
	public void atualizar(String cpf, String nome, String email, double limiteCred, String dataNasc) throws ClassNotFoundException, SQLException {
		for (Cliente c : clientes) {
			if (c.getCpf().equals(cpf)) {
				ClienteDao cdao = new ClienteDao();
				
				c.setNome(nome);
				c.setEmail(email);
				c.setLimiteCred(limiteCred);
				c.setDataNasc(dataNasc);
				
				String saida = cdao.crudCliente("u", c);
				break;
			}
		}
	}

	public void remover(String cpf) throws ClassNotFoundException, SQLException {
		for (Cliente c : clientes) {
			if (c.getCpf().equals(cpf)) {
				ClienteDao cdao = new ClienteDao();
				String saida = cdao.crudCliente("r", c);
				clientes.remove(c);
				break;
			}
		}
	}

}
