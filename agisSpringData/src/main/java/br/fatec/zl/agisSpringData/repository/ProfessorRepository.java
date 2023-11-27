package br.fatec.zl.agisSpringData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.zl.agisSpringData.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}
