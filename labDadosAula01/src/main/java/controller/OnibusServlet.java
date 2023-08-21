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
import model.Onibus;
import persistence.OnibusDao;

@WebServlet("/Onibus")
public class OnibusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<Onibus> onibus = new ArrayList<>();
	private OnibusDao odao;

    public OnibusServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {		
			odao = new OnibusDao();
			if (onibus.isEmpty()) { onibus.addAll(odao.buscaAll()); }
			odao.desconectar();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String placa = request.getParameter("placa");
		String marca = request.getParameter("marca");
		String ano = request.getParameter("ano");
		String desc = request.getParameter("desc");
		
		String mens = "";
		
		try {
			odao = new OnibusDao();
			switch (request.getParameter("botao2")) {
			case "Criar":
				if (!onibus.isEmpty()) {
					criar(placa, marca, ano, desc);
				} else {
					mens = "lista vazia";
				}
				break;
			case "Buscar":
				if (!onibus.isEmpty()) {
					request.setAttribute("onibus", buscar(placa));
				} else {
					mens = "lista vazia";
				}
				break;
			case "Alterar":
				if (!onibus.isEmpty()) {
					alterar(placa, marca, ano, desc);
				} else {
					mens = "lista vazia";
				}
				break;
			case "Remover":
				if (!onibus.isEmpty()) {
					remover(placa);
				} else {
					mens = "lista vazia";
				}
				break;
			}
			
			odao.desconectar();
		} catch (SQLException | ClassNotFoundException e) {
			mens = e.getMessage();
		} finally {
			request.setAttribute("mensagem", mens);
			
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}
	}

	private void criar(String placa, String marca, String ano, String desc) throws SQLException {
		Onibus o = new Onibus();
		
		o.setPlaca(placa);
		o.setMarca(marca);
		o.setAno(Integer.parseInt(ano));
		o.setDesc(desc);
		
		odao.criar(o);
		onibus.add(o);
	}

	private Onibus buscar(String placa) throws SQLException {
		for (Onibus o : onibus) {
			if (o.getPlaca().equals(placa)) {
				return odao.buscar(o);
			}
		}
		return null;
	}

	private void alterar(String placa, String marca, String ano, String desc) throws SQLException {
		for (Onibus o : onibus) {
			if (o.getPlaca().equals(placa)) {
				o.setMarca(marca);
				o.setAno(Integer.parseInt(ano));
				o.setDesc(desc);
				odao.alterar(o);
				break;
			}
		}
	}

	private void remover(String placa) throws SQLException {
		for (Onibus o : onibus) {
			if (o.getPlaca().equals(placa)) {
				odao.remover(o);
				onibus.remove(o);
				break;
			}
		}
	}
}