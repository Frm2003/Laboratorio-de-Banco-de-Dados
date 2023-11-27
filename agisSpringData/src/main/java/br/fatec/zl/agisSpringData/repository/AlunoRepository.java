package br.fatec.zl.agisSpringData.repository;

import java.util.Optional;
import java.time.LocalDate;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, String>  {
	Optional<Aluno> findById(String ra);
	
	// GERAM DADOS
	@Procedure(name = "Aluno.geraRa")
	String geraRa();
	
	@Procedure(name = "Aluno.calculaDataLimite")
	LocalDate calculaDataLimite();
	
	//VALIDAM
	@Procedure(name = "Aluno.validarCPF")
	boolean validarCPF(@Param("cpf") String cpf);
	
	@Procedure(name = "Aluno.validaIdade")
	boolean validaIdade(@Param("dataNasc") LocalDate dataNasc);
	
}
