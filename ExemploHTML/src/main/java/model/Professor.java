package model;

public class Professor {
	
	private int cod;
	private String nome;
	private String dataNasc;
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getData() {
		return dataNasc;
	}
	public void setData(String data) {
		this.dataNasc = data;
	}
	
	@Override
	public String toString() {
		return "Professor [cod=" + cod + ", nome=" + nome + ", dataNasc=" + dataNasc + "]";
	}
}
