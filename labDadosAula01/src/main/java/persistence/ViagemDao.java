package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class ViagemDao implements interfaceDao<Viagem> {
	private Connection c;
	
	public ViagemDao() throws ClassNotFoundException, SQLException {
		GenericDAO gdao = new GenericDAO();
		c = gdao.conexao();
	}
	
	public void desconectar() throws SQLException {
		c.close();
	}
	
	@Override
	public void criar(Viagem v) throws SQLException {
		String sql = "INSERT INTO Viagem VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, v.getCod());
		ps.setString(2, v.getOnibus().getPlaca());
		ps.setInt(3, v.getMotorista().getCod());
		ps.setInt(4, v.getHoraDeSaida());
		ps.setInt(5, v.getHoraDeChegada());
		ps.setString(6, v.getPartida());
		ps.setString(7, v.getChegada());
		
		ps.execute();
		ps.close();
	}

	@Override
	public Viagem buscar(Viagem v) throws SQLException {
		String sql = "select m.naturalidade as natMot, m.nome as nomeMot,"
				+ "		v.codigo as codV, v.onibus as onibusV, v.motorista as motV, v.horaDeSaida as horaSaida, v.horaDeChegada as horaChegada, v.partida as partida, v.destino as destino,"
				+ "		o.marca as marca, o.ano as ano, o.descricao as descr"
				+ "from Motorista m inner join Viagem v on m.codigo = v.motorista"
				+ "		inner join Onibus o on v.onibus = o.placa WHERE v.codigo = ? ";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, v.getCod());
		
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			v.setCod(rs.getInt("codV"));
			
			Onibus o = new Onibus();
			o.setPlaca(rs.getString("onibusV"));
			o.setMarca(rs.getString("marca"));
			o.setAno(rs.getInt("ano"));
			o.setDesc(rs.getString("descr"));
			
			v.setOnibus(o);
			
			Motorista m = new Motorista();
			m.setCod(rs.getInt("motV"));
			m.setNome(rs.getString("nomeMot"));
			m.setNaturalidade(rs.getString("natMot"));
			
			v.setMotorista(m);

			v.setHoraDeSaida(rs.getInt("horaSaida"));
			v.setHoraDeChegada(rs.getInt("horaChegada"));
			v.setPartida(rs.getString("partida"));
			v.setChegada(rs.getString("destino"));
		}
		
		return v;
	}

	@Override
	public void alterar(Viagem v) throws SQLException {
		String sql = "UPDATE Viagem SET onibus = ?, motorista = ?, horaDeSaida = ?, horaDeChegada = ?, partida = ?, chegada = ? WHERE codigo = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, v.getOnibus().getPlaca());
		ps.setInt(2, v.getMotorista().getCod());
		ps.setInt(3, v.getHoraDeSaida());
		ps.setInt(4, v.getHoraDeChegada());
		ps.setString(5, v.getPartida());
		ps.setString(6, v.getChegada());
		ps.setInt(7, v.getCod());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void remover(Viagem v) throws SQLException {
		String sql = "DELETE Viagem WHERE codigo = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, v.getCod());
		
		ps.execute();
		ps.close();
	}

	@Override
	public List<Viagem> buscaAll() throws SQLException {
		List<Viagem> viagens = new ArrayList<>();
		
		String sql = "select m.naturalidade as natMot, m.nome as nomeMot,"
				+ "		v.codigo as codV, v.onibus as onibusV, v.motorista as motV, v.horaDeSaida as horaSaida, v.horaDeChegada as horaChegada, v.partida as partida, v.destino as destino,"
				+ "		o.marca as marca, o.ano as ano, o.descricao as descr"
				+ "from Motorista m inner join Viagem v on m.codigo = v.motorista"
				+ "		inner join Onibus o on v.onibus = o.placa ";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Viagem v = new Viagem();
			v.setCod(rs.getInt("codV"));
			
			Onibus o = new Onibus();
			o.setPlaca(rs.getString("onibusV"));
			o.setMarca(rs.getString("marca"));
			o.setAno(rs.getInt("ano"));
			o.setDesc(rs.getString("descr"));
			
			v.setOnibus(o);
			
			Motorista m = new Motorista();
			m.setCod(rs.getInt("motV"));
			m.setNome(rs.getString("nomeMot"));
			m.setNaturalidade(rs.getString("natMot"));
			
			v.setMotorista(m);

			v.setHoraDeSaida(rs.getInt("horaSaida"));
			v.setHoraDeChegada(rs.getInt("horaChegada"));
			v.setPartida(rs.getString("partida"));
			v.setChegada(rs.getString("destino"));
			
			viagens.add(v);
		}
		
		return viagens;
	}

}
