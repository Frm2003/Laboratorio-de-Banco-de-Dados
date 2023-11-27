package br.edu.fateczl.agisSpringBoot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Matricula {
	private Aluno aluno;
	private Disciplina disciplina;
	private int ano;
	private int semestre;
	private String situacao;
	private int nota;
	private boolean aprovado;
}
