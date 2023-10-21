package br.edu.fateczl.agisSpringBoot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Chamada {
	private Aluno aluno;
	private Disciplina disciplina;
	private int faltas;
	private String data;
}
