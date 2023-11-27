package br.fatec.zl.agisSpringData.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ChamadaTurmaDto {
	@Id
	private Long codMateria;
	private String nomeDisciplina;
	private String professor;
	private String tipoAvalicao;
	private String notaFinal;
	private int totalFaltas;
	private String situacao;
}
