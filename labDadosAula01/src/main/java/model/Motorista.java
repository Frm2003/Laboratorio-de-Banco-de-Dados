package model;

public class Motorista {
	private int cod;
	private String nome;
	private String naturalidade;
	
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
	public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	
	@Override
	public String toString() {
		return "Motorista [cod=" + cod + ", nome=" + nome + ", naturalidade=" + naturalidade + "]";
	}
}