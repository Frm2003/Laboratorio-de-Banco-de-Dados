package br.fatec.zl.agisSpringData.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.Chamada;
import br.fatec.zl.agisSpringData.model.pk.PkChamada;

@Repository
public interface ChamadaRepository extends JpaRepository<Chamada, PkChamada> {
	
	@Query(value = "select * from chamadas where codMateria = ? and dataChamada = ?", nativeQuery = true)
	List<Chamada> consultaChamada(@Param("codMateria") Long materia, @Param("dataChamada") LocalDate data);
}
