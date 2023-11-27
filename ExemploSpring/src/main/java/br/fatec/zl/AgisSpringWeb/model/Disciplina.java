package br.fatec.zl.AgisSpringWeb.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Disciplina {
	private int cod;
	private String nome;
	private int qtdAulas;
	private int semestre;
	private String horario;
	private Curso curso;
}
