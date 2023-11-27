package br.fatec.zl.agisSpringData.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "disciplinas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Disciplina {
	
	public Disciplina(String nome, Long qtdAulas, Long semestre, Curso curso) {
		super();
		this.nome = nome;
		this.qtdAulas = qtdAulas;
		this.semestre = semestre;
		this.curso = curso;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cod;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "qtdAulas", nullable = false)
	private Long qtdAulas;
	
	@Column(name = "semestre", nullable = false)
	private Long semestre;

	@ManyToOne(cascade = CascadeType.REMOVE, targetEntity = Curso.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "codCurso")
	private Curso curso;
}
