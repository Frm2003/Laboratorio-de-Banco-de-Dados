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
import lombok.Data;

@Data

@Entity
@Table(name = "materias")
@Inheritance(strategy = InheritanceType.JOINED)
public class Materia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cod;
	
	@JoinColumn(name = "codDisci", nullable = false)
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Disciplina.class, fetch = FetchType.LAZY)
	private Disciplina disciplina;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Professor.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "codProf", nullable = false)
	private Professor professor;
	
	@Column(name = "diaDaSemana", nullable = false, length = 15)
	private String diaDaSemana;
	
	@Column(name = "horario", nullable = false, length = 100)
	private String horario;
	
	@Column(name = "tipoAvaliacao", nullable = false, length = 100)
	private String tipoAvalicao;
}
