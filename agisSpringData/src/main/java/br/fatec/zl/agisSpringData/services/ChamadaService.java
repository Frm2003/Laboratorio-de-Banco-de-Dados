package br.fatec.zl.agisSpringData.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.zl.agisSpringData.model.Chamada;
import br.fatec.zl.agisSpringData.model.Materia;
import br.fatec.zl.agisSpringData.model.dto.ChamadaDto;
import br.fatec.zl.agisSpringData.model.pk.PkChamada;
import br.fatec.zl.agisSpringData.repository.ChamadaRepository;
import br.fatec.zl.agisSpringData.repository.GeraChamadaRepository;

@Service
public class ChamadaService {
	@Autowired
	private ChamadaRepository crep;
	
	public void inserir(Chamada c) {
		crep.save(c);
	}
	
	public void atualizar(Chamada c) {
		Optional<Chamada> optional = crep.findById(new PkChamada(c.getAluno(), c.getMateria()));
		Chamada cNew = optional.get();
		
		cNew.setFaltas(c.getFaltas());
		
		crep.save(cNew);
	}
	
	public List<Chamada> buscaTudo() {
		return crep.findAll();
	}
	
	public List<Chamada> consultaChamada(Chamada c) {
		return crep.consultaChamada(c.getMateria().getCod(), c.getData());
	}

	@Autowired
	private GeraChamadaRepository gcrep;
	
	public List<ChamadaDto> geraChamada(Materia m) {
		return gcrep.geraChamada(m.getCod());
	}
	
}
