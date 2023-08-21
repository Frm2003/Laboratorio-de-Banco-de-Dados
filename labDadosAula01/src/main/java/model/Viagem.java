package model;

import java.sql.Date;

public class Viagem {
	private int cod;
	private Onibus onibus;
	private Motorista motorista;
	private int horaDeSaida;
	private int horaDeChegada;
	private String partida;
	private String chegada;
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public Onibus getOnibus() {
		return onibus;
	}
	public void setOnibus(Onibus onibus) {
		this.onibus = onibus;
	}
	public Motorista getMotorista() {
		return motorista;
	}
	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
	public int getHoraDeSaida() {
		return horaDeSaida;
	}
	public void setHoraDeSaida(int horaDeSaida) {
		this.horaDeSaida = horaDeSaida;
	}
	public int getHoraDeChegada() {
		return horaDeChegada;
	}
	public void setHoraDeChegada(int horaDeChegada) {
		this.horaDeChegada = horaDeChegada;
	}
	public String getPartida() {
		return partida;
	}
	public void setPartida(String partida) {
		this.partida = partida;
	}
	public String getChegada() {
		return chegada;
	}
	public void setChegada(String chegada) {
		this.chegada = chegada;
	}
}
