package br.fatec.zl.agisSpringData.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.zl.agisSpringData.model.Professor;
import br.fatec.zl.agisSpringData.repository.ProfessorRepository;

@Service
public class ProfessorService {
	@Autowired
	private ProfessorRepository prep;
	
	public List<Professor> buscarTudo() {
		return prep.findAll();
	}
}
