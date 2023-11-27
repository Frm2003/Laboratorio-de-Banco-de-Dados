package br.fatec.zl.agisSpringData.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.Nota;

@Repository
public interface NotasRepository extends JpaRepository<Nota, Long>{	
	//Optional<Notas> findById(String ra);
	
	@Query(value = "select * from avaliacoes where codMateria = ?", nativeQuery = true) 
	List<Nota> listarNotasMateria(@Param("cod") Long cod);
	
	@Query(value = "select * from avaliacoes where ra = ?", nativeQuery = true) 
	List<Nota> listarNotasAluno(@Param("ra") String ra);
}
