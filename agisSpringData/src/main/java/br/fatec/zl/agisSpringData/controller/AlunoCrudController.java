package br.fatec.zl.agisSpringData.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.fatec.zl.agisSpringData.model.Aluno;
import br.fatec.zl.agisSpringData.model.Curso;
import br.fatec.zl.agisSpringData.services.AlunoService;
import br.fatec.zl.agisSpringData.services.CursoService;

@Controller
@RequestMapping("/aluno")
public class AlunoCrudController {
	@Autowired
	private AlunoService aserv;
	
	@Autowired
	private CursoService cserv;
	
	private List<Aluno> alunos = new ArrayList<>();
	private List<Curso> cursos = new ArrayList<>();
	
	@GetMapping
	public String get(ModelMap model) {
		if (!alunos.isEmpty()) { alunos.removeAll(alunos); }
		if (!cursos.isEmpty()) { cursos.removeAll(cursos); }

		alunos.addAll(aserv.buscarTudo());
		cursos.addAll(cserv.buscarTudo());
		
		model.addAttribute("alunos", alunos);
		model.addAttribute("cursos", cursos);
		
		return "secretariaAluno";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		String ra = param.get("ra");
		String nome = param.get("nome");
		String dataNasc = param.get("dataNasc");
		String nomeSocial = param.get("nomeSocial");
		String emailPessoal = param.get("emailPessoal");
		String dataConc2grau = param.get("dataConc2grau");
		String cpf = param.get("cpf");
		String instConc2grau = param.get("instConc2grau");
		String ptVestibular = param.get("ptVestibular");
		String posVestibular = param.get("posVestibular");
		String curso = param.get("curso");
		
		try {
			switch (param.get("botao")) {
			case "Inserir":
				criar(nome, LocalDate.parse(dataNasc), nomeSocial, emailPessoal, LocalDate.parse(dataConc2grau), cpf, instConc2grau, Integer.parseInt(ptVestibular), Integer.parseInt(posVestibular), Integer.parseInt(curso));
				break;
			case "Buscar":
				model.addAttribute("aluno", buscar(ra));
				break;
			case "Atualizar":
				atualizar(ra, nome, LocalDate.parse(dataNasc), nomeSocial, emailPessoal, LocalDate.parse(dataConc2grau), cpf, instConc2grau, Integer.parseInt(ptVestibular), Integer.parseInt(posVestibular), Integer.parseInt(curso));
				break;
			case "Deletar":
				deletar(ra);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("alunos", alunos);
		model.addAttribute("cursos", cursos);
		
		return "secretariaAluno";
	}

	private void criar(String nome, LocalDate dataNasc, String nomeSocial, String emailPessoal, LocalDate dataConc2grau, String cpf, String instConc2grau, int ptVestibular, int posVestibular, int curso) {
		Aluno a = new Aluno();
		
		a.setRa(aserv.geraRa());
		a.setNome(nomeSocial);
		a.setDataNasc(dataNasc);
		a.setNomeSocial(nomeSocial);
		a.setEmailPessoal(emailPessoal);
		a.setDataConc2grau(dataConc2grau);
		a.setCpf(cpf);
		a.setInstConc2grau(instConc2grau);
		a.setPtVestibular(ptVestibular);
		a.setPosVestibular(posVestibular);
		
		for (Curso c : cursos) {
			if (c.getCod() == curso) {
				a.setCurso(c);
			}
		}
		
		a.setEmailCorporativo(aserv.geraEmail(nome));
		a.setDataMatricula(LocalDate.now());
		a.setDataLimiteMatricula(aserv.calculaDataLimite());
		
		aserv.inserir(a);
		alunos.add(a);
	}
	
	private Aluno buscar(String ra) {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				return aserv.buscar(a);
			}
		}
		return null;
	}
	
	private void atualizar(String ra, String nome, LocalDate dataNasc, String nomeSocial, String emailPessoal, LocalDate dataConc2grau, String cpf, String instConc2grau, int ptVestibular, int posVestibular, int curso) {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				a.setRa(aserv.geraRa());
				a.setNome(nomeSocial);
				a.setDataNasc(dataNasc);
				a.setNomeSocial(nomeSocial);
				a.setEmailPessoal(emailPessoal);
				a.setDataConc2grau(dataConc2grau);
				a.setCpf(cpf);
				a.setInstConc2grau(instConc2grau);
				a.setPtVestibular(ptVestibular);
				a.setPosVestibular(posVestibular);
				
				for (Curso c : cursos) {
					if (c.getCod() == curso) {
						a.setCurso(c);
					}
				}
				
				aserv.atualizar(a);
				break;
			}
		}
	}
	
	private void deletar(String ra) {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				a.setCurso(null);
				aserv.atualizar(a);
				aserv.deletar(a);
				alunos.remove(a);
				break;
			}
		}
	}
}
