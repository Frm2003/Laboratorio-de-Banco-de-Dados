package br.fatec.zl.agisSpringData.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.dto.HistoricoTurmaDto;

@Repository
public interface HistoricoTurmaDtoRepository extends JpaRepository<HistoricoTurmaDto, Long>{
	@Query(value = "select * from historicoTurma(?)", nativeQuery = true)	
	List<HistoricoTurmaDto> listaHistoricoNotasMateria(@Param("cod") Long codTurma);
}
