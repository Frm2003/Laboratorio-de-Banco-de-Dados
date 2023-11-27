package br.fatec.zl.agisSpringData.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.dto.ChamadaDto;

@Repository
public interface GeraChamadaRepository extends JpaRepository<ChamadaDto, String> {
	
	@Query(value = "select distinct  * from geraChamada(?) ", nativeQuery = true)
	List<ChamadaDto> geraChamada(@Param("codMateria") Long codMateria);
	
}
