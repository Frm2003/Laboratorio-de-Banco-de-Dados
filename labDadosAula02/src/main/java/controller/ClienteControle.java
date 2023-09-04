package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import persistence.ClienteDao;

public class ClienteControle {
	private List<Cliente> lista = new ArrayList<>();
	
	public ClienteControle() throws ClassNotFoundException, SQLException {
		ClienteDao cdao = new ClienteDao();
		lista.addAll(cdao.listar());
	}

	public String inserir(String cpf, String nome, String email, double limiteCred, String dataNasc) throws ClassNotFoundException, SQLException {
		Cliente c = new Cliente();
		ClienteDao cdao = new ClienteDao();
		
		c.setCpf(cpf);
		c.setNome(nome);
		c.setEmail(email);
		c.setLimiteCred(limiteCred);
		c.setDataNasc(dataNasc);
		
		String saida = cdao.crudCliente("i", c);
		lista.add(c);
		
		return saida;
	}

	public Cliente buscar(String cpf) throws ClassNotFoundException, SQLException {
		for (Cliente c : lista) {
			if (c.getCpf().equals(cpf)) {
				ClienteDao cdao = new ClienteDao();
				return cdao.buscar(c);
			}
		}
		return null;
	}
	
	public void atualizar(String cpf, String nome, String email, double limiteCred, String dataNasc) throws ClassNotFoundException, SQLException {
		for (Cliente c : lista) {
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
		for (Cliente c : lista) {
			if (c.getCpf().equals(cpf)) {
				ClienteDao cdao = new ClienteDao();
				String saida = cdao.crudCliente("r", c);
				lista.remove(c);
				break;
			}
		}
	}
}
