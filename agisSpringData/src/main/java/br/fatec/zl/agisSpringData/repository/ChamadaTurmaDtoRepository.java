package br.fatec.zl.agisSpringData.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.dto.ChamadaTurmaDto;

@Repository
public interface ChamadaTurmaDtoRepository extends JpaRepository<ChamadaTurmaDto, Long> {
	
	@Query(value = "select * from historicoTurmaChamada(?)", nativeQuery = true)
	List<ChamadaTurmaDto> listaChamadaTurma(@Param("cod") Long cod);
}
