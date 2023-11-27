package br.fatec.zl.agisSpringData.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "cursos")
public class Curso {
	
	public Curso(String nome, int cargaHoraria, String sigla, BigDecimal enade, String turno) {
		this.nome = nome;
		this.cargaHoraria = cargaHoraria;
		this.sigla = sigla;
		this.enade = enade;
		this.turno = turno;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod", nullable = false)
	private Long cod;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "cargaHoraria", nullable = false)
	private int cargaHoraria;
	
	@Column(name = "sigla", nullable = false, length = 100)
	private String sigla;
	
	@Column(name = "enade", precision = 10, scale = 2)
	private BigDecimal enade;
	
	@Column(name = "turno", nullable = false, length = 20)
	private String turno;
}
