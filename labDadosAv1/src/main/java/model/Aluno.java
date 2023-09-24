package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Aluno extends Usuario {
	private String ra;
	private String nomeSocial;
	private String emailPessoal;
	private String dataConc2grau;
	private String isntConc2grau;
	private int ptVestibular;
	private int posVestibular;
	private int anoDeIngresso;
	private int semestreDeIngresso;
	private int semestreLimite;
	private int anoLimite;
	private Curso curso;
}
