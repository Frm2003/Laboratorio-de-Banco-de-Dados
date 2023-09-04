package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class ClienteDao {
	private Connection c;

	public ClienteDao() throws ClassNotFoundException, SQLException {
		GenericDAO gdao = new GenericDAO();
		c = gdao.conexao();
	}

	public String crudCliente(String acao, Cliente cli) throws SQLException {
		String sql = "{CALL crudCliente (?, ?, ?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, acao);
		cs.setString(2, cli.getCpf());
		cs.setString(3, cli.getNome());
		cs.setString(4, cli.getEmail());
		cs.setDouble(5, cli.getLimiteCred());
		cs.setString(6, cli.getDataNasc());
		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(7);
		cs.close();
		c.close();

		return saida;
	}

	public Cliente buscar(Cliente cli) throws SQLException {
		String sql = "SELECT nome, email, limiteCred, dataNasc FROM Cliente WHERE cpf = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cli.getCpf());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			cli.setNome(rs.getString("nome"));
			cli.setEmail(rs.getString("email"));
			cli.setLimiteCred(rs.getDouble("limiteCred"));
			cli.setDataNasc(rs.getString("dataNasc"));
		}

		rs.close();
		ps.close();

		return cli;
	}
	
	public List<Cliente> listar() throws SQLException {
		String sql = "SELECT cpf, nome, email, limiteCred, dataNasc FROM Cliente";
		
		List<Cliente> clientes = new ArrayList<>();
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			Cliente c = new Cliente();
			
			c.setCpf(rs.getString("cpf"));
			c.setNome(rs.getString("nome"));
			c.setEmail(rs.getString("email"));
			c.setLimiteCred(rs.getDouble("limiteCred"));
			c.setDataNasc(rs.getString("dataNasc"));
			
			clientes.add(c);
		}

		rs.close();
		ps.close();

		return clientes;
	}

}
