package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Cliente {
	public String cpf;
	public String nome;
	public String email;
	private double limiteCred;
	private String dataNasc;
}
