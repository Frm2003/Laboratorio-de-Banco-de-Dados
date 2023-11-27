package br.fatec.zl.agisSpringData.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "alunos")
@Inheritance(strategy = InheritanceType.JOINED)
public class Aluno {
	@Id
	@Column(name = "ra", nullable = false, length = 9)
	private String ra;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "dataNasc", columnDefinition = "DATE")
	private LocalDate dataNasc;
	
	@Column(name = "nomeSocial", nullable = false, length = 100)
	private String nomeSocial;
	
	@Column(name = "emailPessoal", nullable = false, length = 100)
	private String emailPessoal;
	
	@Column(name = "dataConcMedio", columnDefinition = "DATE")
	private LocalDate dataConc2grau;
	
	@Column(name = "cpf", nullable = false, length = 11)
	private String cpf;
	
	@Column(name = "instituicaoEnsinoMedio", nullable = false, length = 100)
	private String instConc2grau;
	
	@Column(name = "ptVestibular", nullable = false)
	private int ptVestibular;
	
	@Column(name = "posVestibular", nullable = false)
	private int posVestibular;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Curso.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "codCurso", nullable = false)
	private Curso curso;
	
	@Column(name = "emailCorp", nullable = false, length = 100)
	private String emailCorporativo;
	
	@Column(name = "dataMatricula", columnDefinition = "DATE")
	private LocalDate dataMatricula;

	@Column(name = "dataLimiteMatricula", columnDefinition = "DATE")
	private LocalDate dataLimiteMatricula;
}
