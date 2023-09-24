package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Usuario {
	private String cpf;
	private String nome;
	private String dataNasc;
	private String emailCorporativo;
}	
