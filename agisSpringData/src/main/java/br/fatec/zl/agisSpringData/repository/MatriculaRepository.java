package br.fatec.zl.agisSpringData.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.Matricula;
import br.fatec.zl.agisSpringData.model.pk.PkMatricula;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, PkMatricula> {
	
	@Procedure(name = "Matricula.getSemestre")
	int getSemestre();
	
	@Query(value = "select * from matriculas where alunoRa = ?", nativeQuery = true)
	List<Matricula> listaMatriculasAluno(@Param("ra") String ra);
	
	@Query(value = "select matri.* "
			+ "from matriculas matri "
			+ "	inner join materias mate on matri.codMateria = mate.cod "
			+ "where mate.cod = ?", nativeQuery = true)
	List<Matricula> listaMatriculasMateria(@Param("cod") Long cod);
}
