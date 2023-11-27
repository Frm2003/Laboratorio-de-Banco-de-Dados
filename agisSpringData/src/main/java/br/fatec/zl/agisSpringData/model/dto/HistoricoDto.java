package br.fatec.zl.agisSpringData.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class HistoricoDto {
	@Id
	private Long codDisci;
	
	private String nomeDisci;
	private String nomeProf;
	private String notaFinal;
	private int qtdFalta;
}
