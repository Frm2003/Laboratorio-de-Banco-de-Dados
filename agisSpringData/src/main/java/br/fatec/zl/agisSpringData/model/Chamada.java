package br.fatec.zl.agisSpringData.model;

import java.time.LocalDate;

import br.fatec.zl.agisSpringData.model.pk.PkChamada;
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
@Table(name = "chamadas")
@Inheritance(strategy = InheritanceType.JOINED)
@IdClass(PkChamada.class)
public class Chamada {
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Aluno.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "alunoRa", nullable = false)
	private Aluno aluno;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Materia.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "codMateria", nullable = false)
	private Materia materia;
	
	@Column(name = "dataChamada", columnDefinition = "DATE")
	private LocalDate data;
	
	@Column(name = "qtdFalta", nullable = false)
	private int faltas;
}
