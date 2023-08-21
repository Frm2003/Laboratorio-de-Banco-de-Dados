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

import model.*;
import persistence.*;

@WebServlet("/Viagem")
public class ViagemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<Motorista> motoristas = new ArrayList<>();
	private List<Onibus> onibus = new ArrayList<>();
	private List<Viagem> viagens = new ArrayList<>();
	
	private MotoristaDao mdao;
	private OnibusDao odao;
	private ViagemDao vdao;
	
    public ViagemServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			mdao = new MotoristaDao();
			odao = new OnibusDao();
			//vdao = new ViagemDao();
			if (motoristas.isEmpty()) { motoristas.addAll(mdao.buscaAll()); }
			if (onibus.isEmpty()) { onibus.addAll(odao.buscaAll()); }
			//if (viagens.isEmpty()) { viagens.addAll(vdao.buscaAll()); }
			mdao.desconectar();
			odao.desconectar();
			//vdao.desconectar();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("motoristas", motoristas);
		request.setAttribute("onibuses", onibus);
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cod = request.getParameter("cod");
		String placa = request.getParameter("onibus");
		String mot = request.getParameter("motorista");
		String horaSaida = request.getParameter("horaSaida");
		String horaChegada = request.getParameter("horaChegada");
		String partida = request.getParameter("partida");
		String chegada = request.getParameter("destino");
		
		String mens = "";
		
		try {
			vdao = new ViagemDao();
			switch (request.getParameter("botao")) {
			case "Criar":
				criar(cod, placa, mot, horaSaida, horaChegada, partida, chegada);
				break;
			case "Buscar":
				request.setAttribute("viagem", buscar(cod));
				break;
			case "Alterar":
				alterar(cod, placa, mot, horaSaida, horaChegada, partida, chegada);
				break;
			case "Remover":
				remover(cod);
				break;
			}
			
			vdao.desconectar();
		} catch (SQLException | ClassNotFoundException e) {
			mens = e.getMessage();
		} finally {
			request.setAttribute("mensagem", mens);			
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}
	}

	private void criar(String cod, String placa, String mot, String horaSaida, String horaChegada, String partida, String chegada) throws SQLException {
		Viagem v = new Viagem();
		
		v.setCod(Integer.parseInt(cod));
		for (Onibus o : onibus) {
			if (o.getPlaca().equals(placa)) {
				v.setOnibus(o);
			}
		}
		for (Motorista m : motoristas) {
			if (m.getCod() == Integer.parseInt(mot)) {
				v.setMotorista(m);
			}
		}
		v.setHoraDeSaida(Integer.parseInt(horaSaida));
		v.setHoraDeChegada(Integer.parseInt(horaChegada));
		v.setPartida(partida);
		v.setChegada(chegada);
		
		vdao.criar(v);
		viagens.add(v);
	}

	private Viagem buscar(String cod) throws SQLException {
		for (Viagem v : viagens) {
			if (v.getCod() == Integer.parseInt(cod)) {
				return vdao.buscar(v);
			}
		}
		return null;
	}

	private void alterar(String cod, String placa, String mot, String horaSaida, String horaChegada, String partida, String chegada) throws SQLException {
		for (Viagem v : viagens) {
			if (v.getCod() == Integer.parseInt(cod)) {
				for (Onibus o : onibus) {
					if (o.getPlaca().equals(placa)) {
						v.setOnibus(o);
					}
				}
				for (Motorista m : motoristas) {
					if (m.getCod() == Integer.parseInt(mot)) {
						v.setMotorista(m);
					}
				}
				v.setHoraDeSaida(Integer.parseInt(horaSaida));
				v.setHoraDeChegada(Integer.parseInt(horaChegada));
				v.setPartida(partida);
				v.setChegada(chegada);
				vdao.alterar(v);
				break;
			}
		}
	}

	private void remover(String cod) throws SQLException {
		for (Viagem v : viagens) {
			if (v.getCod() == Integer.parseInt(cod)) {
				vdao.remover(v);
				viagens.remove(v);
				break;
			}
		}
	}
}
