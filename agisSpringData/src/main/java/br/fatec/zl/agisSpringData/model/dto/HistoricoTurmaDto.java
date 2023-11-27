package br.fatec.zl.agisSpringData.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class HistoricoTurmaDto {
	@Id
	private Long codMateria;
	private String turno;
	private String nomeDisciplina;
	private String tipoAvaliacao;
	private float nota1;
	private float nota2;
	private float nota3;
	private String notaFinal;
}

