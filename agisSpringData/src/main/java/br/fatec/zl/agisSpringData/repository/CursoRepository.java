package br.fatec.zl.agisSpringData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
