package br.fatec.zl.agisSpringData.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.dto.HistoricoDto;

@Repository
public interface HistoricoRepository extends JpaRepository<HistoricoDto, Long> {
	@Query(value = "select * from historico(?)", nativeQuery = true)
	List<HistoricoDto> historicoAluno(@Param("ra") String ra);
}
