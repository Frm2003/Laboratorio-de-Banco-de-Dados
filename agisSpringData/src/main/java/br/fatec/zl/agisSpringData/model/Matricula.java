package br.fatec.zl.agisSpringData.model;

import br.fatec.zl.agisSpringData.model.pk.PkMatricula;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "matriculas")
@Inheritance(strategy = InheritanceType.JOINED)
@IdClass(PkMatricula.class)
public class Matricula {
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Aluno.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "alunoRa", nullable = false)
	private Aluno aluno;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Materia.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "codMateria", nullable = false)
	private Materia Materia;
	
	@Id
	@Column(name = "ano", nullable = false)
	private int ano;
	
	@Id
	@Column(name = "semestre", nullable = false)
	private int semestre;
	
	@Column(name = "situacao", nullable = false, length = 100)
	private String situacao;
	
	@Column(name = "aprovado", nullable = false)
	private boolean aprovado;
}
