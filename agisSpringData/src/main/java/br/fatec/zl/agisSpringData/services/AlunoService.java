package br.fatec.zl.agisSpringData.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.zl.agisSpringData.model.Aluno;
import br.fatec.zl.agisSpringData.model.dto.HistoricoDto;
import br.fatec.zl.agisSpringData.repository.AlunoRepository;
import br.fatec.zl.agisSpringData.repository.HistoricoRepository;

@Service
public class AlunoService {
	@Autowired
	private  AlunoRepository arep;
	
	//CRUD
	public void inserir(Aluno a) {		
		if (arep.validarCPF(a.getCpf()) == true) {
			arep.save(a);
		}
	}
	
	public void atualizar(Aluno a) {
		Optional<Aluno> alu = arep.findById(a.getRa());
		Aluno aNew = alu.get();
		
		aNew.setNome(a.getNome());
		aNew.setDataNasc(a.getDataNasc());
		aNew.setNomeSocial(a.getNomeSocial());
		aNew.setEmailPessoal(a.getEmailPessoal());
		aNew.setDataConc2grau(a.getDataConc2grau());
		aNew.setCpf(a.getCpf());
		aNew.setInstConc2grau(a.getInstConc2grau());
		aNew.setPtVestibular(a.getPtVestibular());
		aNew.setPosVestibular(a.getPosVestibular());
		aNew.setCurso(a.getCurso());
		
		arep.save(aNew);
	}
	
	public Aluno buscar(Aluno a) {
		Optional<Aluno> optional = arep.findById(a.getRa());
		a = optional.get();
		return a;
	}
	
	public void deletar(Aluno a) {
		arep.delete(a);
	}
	
	public List<Aluno> buscarTudo() {
		return arep.findAll();
	}
	
	// DADOS
	public String geraRa() {
		return arep.geraRa();
	}
	
	public LocalDate calculaDataLimite() {
		return arep.calculaDataLimite();
	}
	
	public String geraEmail(String nome) {
		String[] vet = nome.split(" ");
		return vet[0] + "." + vet[vet.length - 1] + "@email.com";
	}
	
	@Autowired
	private HistoricoRepository hrep;
	
	public List<HistoricoDto> listaHistorico(Aluno a) {
		return hrep.historicoAluno(a.getRa());
	}
}
