package br.fatec.zl.agisSpringData.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.zl.agisSpringData.model.Aluno;
import br.fatec.zl.agisSpringData.model.Materia;
import br.fatec.zl.agisSpringData.model.Nota;
import br.fatec.zl.agisSpringData.repository.NotasRepository;

@Service
public class NotasService {
	@Autowired
	private NotasRepository nrep;
	
	public void inserir(Nota n) {
		nrep.save(n);
	}
	
	public Nota buscar(Nota n) {
		Optional<Nota> optional = nrep.findById(n.getCod());
		return optional.get();
	}
	
	public Nota buscar(Long n) {
		Optional<Nota> optional = nrep.findById(n);
		return optional.get();
	}
	
	public void atualizar(Nota n) {
		Optional<Nota> optional = nrep.findById(n.getCod());
		Nota nNew = optional.get();
		
		nNew.setN1(n.getN1());
		nNew.setN2(n.getN2());
		nNew.setN3(n.getN3());
		
		nrep.save(nNew);
	}
	
	public List<Nota> buscarTudo() {
		return nrep.findAll();
	}
	
	public List<Nota> listarNotasMateria(Materia m) {
		return nrep.listarNotasMateria(m.getCod());
	}
	
	public List<Nota> listarNotasAluno(Aluno a) {
		return nrep.listarNotasAluno(a.getRa());
	}
	
	public Long contador() {
		return nrep.count();
	}
	
	public float calculaNotas(String tipo, float ... notas) {
		if (tipo.equals("Tipo1")) {
			return (float) (notas[0] * 0.3) + (float) (notas[1] * 0.5) + (float) (notas[2] * 0.2);
		} else if (tipo.equals("Tipo2")) {
			return (float) (notas[0] * 0.35) + (float) (notas[1] * 0.35) + (float) (notas[2] * 0.3);
		} else if (tipo.equals("Tipo3")) {
			return (float) (notas[0] * 0.33) + (float) (notas[1] * 0.33) + (float) (notas[2] * 0.33);
		} else {
			return (float) (notas[0] * 0.8) + (float) (notas[1] * 0.2);
		}
	}
	
}
