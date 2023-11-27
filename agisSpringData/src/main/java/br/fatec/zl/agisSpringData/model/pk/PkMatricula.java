package br.fatec.zl.agisSpringData.model.pk;

import java.io.Serializable;

import br.fatec.zl.agisSpringData.model.Aluno;
import br.fatec.zl.agisSpringData.model.Materia;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PkMatricula implements Serializable {
	private static final long serialVersionUID = 1L;
	private Aluno aluno;
	private Materia Materia;
	private int ano;
	private int semestre;
}
