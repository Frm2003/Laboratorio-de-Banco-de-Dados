package br.fatec.zl.agisSpringData.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.zl.agisSpringData.model.Aluno;
import br.fatec.zl.agisSpringData.model.Materia;
import br.fatec.zl.agisSpringData.model.dto.ChamadaTurmaDto;
import br.fatec.zl.agisSpringData.model.dto.HistoricoTurmaDto;
import br.fatec.zl.agisSpringData.repository.ChamadaTurmaDtoRepository;
import br.fatec.zl.agisSpringData.repository.HistoricoTurmaDtoRepository;
import br.fatec.zl.agisSpringData.repository.MateriaRepository;

@Service
public class MateriaService {
	@Autowired
	private MateriaRepository mrep;
	
	@Autowired
	private HistoricoTurmaDtoRepository htrep;
	
	@Autowired
	private ChamadaTurmaDtoRepository ctrep;
	
	public void inserir(Materia m) {
		mrep.save(m);
	}
	
	public void atualizar(Materia mat) {
		Optional<Materia> m = mrep.findById(mat.getCod());
		Materia mNew = m.get();
		
		mNew.setHorario(mat.getHorario());
		mNew.setDiaDaSemana(mat.getDiaDaSemana());
		mNew.setDisciplina(mat.getDisciplina());
		mNew.setProfessor(mat.getProfessor());
		
		mrep.save(mNew);
	}
	
	public Materia buscar(Materia m) {
		Optional<Materia> optional = mrep.findById(m.getCod());
		m = optional.get();
		return m;
	}
	
	public void deletar(Materia m) {
		mrep.delete(m);
	}
	
	public List<Materia> buscarTudo() {
		return mrep.findAll();
	}
	
	public Long contador() {
		return mrep.count();
	}
	
	public List<Materia> listaMatriculasDisponiveis(Aluno a) {
		return mrep.listaMatriculasDisponiveis(a.getRa());
	}
	
	// HISTORICO
	
	public List<ChamadaTurmaDto> listaChamadaTurma(Materia m) {
		return ctrep.listaChamadaTurma(m.getCod());
	}
	
	public List<HistoricoTurmaDto> listaHistoricoNotasMateria(Materia m) {
		return htrep.listaHistoricoNotasMateria(m.getCod());
	}
	
}
