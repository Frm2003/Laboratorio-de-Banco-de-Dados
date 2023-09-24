package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Disciplina {
	private int cod;
	private String nome;
	private int qtdHorasSemanais;
	private int semestre;
	private String horario;
	private Curso curso;
}
