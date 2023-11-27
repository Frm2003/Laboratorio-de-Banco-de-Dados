package br.fatec.zl.agisSpringData.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.Materia;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long>{
	
	@Query(value = "select mat.* "
			+ "from materias mat "
			+ "	inner join disciplinas d on mat.codDisci = d.cod "
			+ "	inner join cursos c on d.codCurso = c.cod "
			+ "	left join matriculas m on m.codMateria = mat.cod and m.alunoRa = ? "
			+ "where m.alunoRa is null", nativeQuery = true)
	List<Materia> listaMatriculasDisponiveis(@Param("ra") String ra);
}
