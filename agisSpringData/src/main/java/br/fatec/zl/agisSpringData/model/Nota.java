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
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "avaliacoes")
@Inheritance(strategy = InheritanceType.JOINED)
public class Nota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod", nullable = false)
	private Long cod;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Matricula.class, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "codMateria", nullable = false),
		@JoinColumn(name = "ra", nullable = false),
		@JoinColumn(name = "ano", nullable = false),
		@JoinColumn(name = "semestre", nullable = false)
	})
	private Matricula matricula;
	
	@Column(name = "nota1")
	private float n1;
	
	@Column(name = "nota2")
	private float n2;
	
	@Column(name = "nota3")
	private float n3;
	
	@Transient
	private double notaFinal;
}
