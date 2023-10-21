package br.fatec.zl.AgisSpringWeb.model;

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
	private boolean aprovado;
}
