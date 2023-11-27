package br.fatec.zl.agisSpringData.model.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ChamadaDto {
	@Id
    private String ra;
    private Long codMateria;
    private LocalDate dataChamada;
    private Long qtdFaltas;
}
	