package br.fatec.zl.agisSpringData.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.zl.agisSpringData.model.Disciplina;
import br.fatec.zl.agisSpringData.repository.DisciplinaRepository;

@Service
public class DisciplinaService {
	@Autowired
	private DisciplinaRepository drep;
	
	public void inserir(Disciplina d) {		
		drep.save(d);
	}
	
	public void atualizar(Disciplina d) {
		Optional<Disciplina> dOp = drep.findById(d.getCod());
		Disciplina dNew = dOp.get();
		
		dNew.setNome(d.getNome());
		dNew.setQtdAulas(d.getQtdAulas());
		dNew.setSemestre(d.getSemestre());
		dNew.setCurso(d.getCurso());
		
		drep.save(dNew);
	}
	
	public Disciplina buscar(Disciplina d) {
		Optional<Disciplina> optional = drep.findById(d.getCod());
		d = optional.get();
		return d;
	}
	
	public void deletar(Disciplina d) {
		drep.deleteById(d.getCod());
	}
	
	public List<Disciplina> buscarTudo() {
		return drep.findAll();
	}
	
	public Long contador() {
		return drep.count();
	}
}
