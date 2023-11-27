package br.fatec.zl.agisSpringData.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.zl.agisSpringData.model.Curso;
import br.fatec.zl.agisSpringData.repository.CursoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CursoService {
	@Autowired
	private CursoRepository crep;
	
	public void inserir(Curso c) {		
		crep.save(c);
	}
	
	public void atualizar(Curso cur) {
		Optional<Curso> c = crep.findById(cur.getCod());
		Curso cNew = c.get();
		
		cNew.setNome(cur.getNome());
		cNew.setCargaHoraria(cur.getCargaHoraria());
		cNew.setSigla(cur.getSigla());
		cNew.setEnade(cur.getEnade());
		cNew.setTurno(cur.getTurno());
		
		crep.save(cNew);
	}
	
	public Curso buscar(Curso c) {
		Optional<Curso> optional = crep.findById(c.getCod());
		c = optional.get();
		return c;
	}
	
	public void deletar(Curso c) {
		crep.delete(c);
	}
	
	public List<Curso> buscarTudo() {
		return crep.findAll();
	}
}
