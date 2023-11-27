package br.fatec.zl.agisSpringData.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.zl.agisSpringData.model.Aluno;
import br.fatec.zl.agisSpringData.model.Materia;
import br.fatec.zl.agisSpringData.model.Matricula;
import br.fatec.zl.agisSpringData.model.pk.PkMatricula;
import br.fatec.zl.agisSpringData.repository.MatriculaRepository;

@Service
public class MatriculaService {
	@Autowired
	private MatriculaRepository mrep;
	
	public void inserir(Matricula m) {
		mrep.save(m);
	}
	
	public void atualizar(Matricula m) {
		System.out.println(m);
		
		Optional<Matricula> optional = mrep.findById(new PkMatricula(m.getAluno(), m.getMateria(), m.getAno(), m.getSemestre()));		
		Matricula mNew = optional.get();
		
		mNew.setSituacao(m.getSituacao());
		mNew.setSituacao(m.getSituacao());
		
		mrep.save(m);
	}
	
	public List<Matricula> buscarTudo() {
		return mrep.findAll();
	}
	
	public List<Matricula> listaMatriculasAluno(Aluno a) {
		return mrep.listaMatriculasAluno(a.getRa());
	}
	
	public List<Matricula> listaMatriculasMateria(Materia m) {
		return mrep.listaMatriculasMateria(m.getCod());
	}
	
	public Long contador() {
		return mrep.count();
	}
	
	public int getSemestre() {
		return mrep.getSemestre();
	}
	
	
}
