package br.edu.fateczl.agisSpringBoot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlunoHistorico {
	private int codDisci;
	private String nomeDisci;
	private String nomeProf;
	private String notaFinal;
	private int qtdFalta;
}
