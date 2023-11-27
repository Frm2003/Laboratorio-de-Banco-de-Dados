package br.edu.fateczl.agisSpringBoot.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.fateczl.agisSpringBoot.model.Aluno;
import br.edu.fateczl.agisSpringBoot.model.Curso;
import br.edu.fateczl.agisSpringBoot.persistence.AlunoDao;
import br.edu.fateczl.agisSpringBoot.persistence.CursoDao;

@Controller
public class AlunoController {
	@Autowired
	private AlunoDao adao;
	
	@Autowired
	private CursoDao cdao;
	
	private List<Aluno> alunos = new ArrayList<>();
	private List<Curso> cursos = new ArrayList<>();
	
	@RequestMapping(name = "aluno", value = "/aluno", method = RequestMethod.GET)
	public ModelAndView alunoGet(ModelMap model) {
		if (!alunos.isEmpty()) { alunos.removeAll(alunos); }
		if (!cursos.isEmpty()) { cursos.removeAll(cursos); }
		
		try {
			alunos.addAll(adao.listar());
			cursos.addAll(cdao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("alunos", alunos);
		model.addAttribute("cursos", cursos);
		
		return new ModelAndView("secretariaAluno");
	}
	
	@RequestMapping(name = "aluno", value = "/aluno", method = RequestMethod.POST)
	public ModelAndView alunoPost(@RequestParam Map<String, String> param, ModelMap model) {
		String ra = param.get("ra");
		String nome = param.get("nome");
		String nomeSocial = param.get("nomeSocial");
		String dataNasc = param.get("dataNasc");
		String emailPessoal = param.get("emailPessoal");
		String dataConc2grau = param.get("dataConc2grau");
		String cpf = param.get("cpf");
		String isntConc2grau = param.get("isntConc2grau");
		String ptVestibular = param.get("ptVestibular");
		String posVestibular = param.get("posVestibular");
		String curso = param.get("curso");
		
		try {
			switch (param.get("botao")) {
			case "Inserir":
				criar(nome, nomeSocial, emailPessoal, dataNasc, dataConc2grau, isntConc2grau, cpf, Integer.parseInt(ptVestibular), Integer.parseInt(posVestibular), Integer.parseInt(curso));
				break;
			case "Buscar":
				if (!alunos.isEmpty()) {
					model.addAttribute("aluno", buscar(ra));					
				}
				break;
			case "Atualizar":
				if (!alunos.isEmpty()) {
					atualizar(ra, nome, nomeSocial, emailPessoal, dataNasc, dataConc2grau, isntConc2grau, cpf, Integer.parseInt(ptVestibular), Integer.parseInt(posVestibular), Integer.parseInt(curso));					
				}
				break;
			case "Deletar":
				if (!alunos.isEmpty()) {					
					deletar(ra);
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			model.addAttribute("alunos", alunos);
			model.addAttribute("cursos", cursos);
		}
		return new ModelAndView("secretariaAluno");
	}
	
	private void criar(String nome, String nomeSocial, String emailPessoal, String dataNasc, String dataConc2grau, String isntConc2grau, String cpf, int ptVestibular, int posVestibular, int curso) throws ClassNotFoundException, SQLException {
		Aluno a = new Aluno();
		
		a.setRa(adao.geraRa());
		a.setCpf(cpf);
		a.setNome(nome);
		a.setNomeSocial(nomeSocial);
		a.setEmailPessoal(emailPessoal);
		a.setEmailCorporativo(geraEmailCorporativo(nome));
		a.setDataNasc(dataNasc);
		a.setDataConc2grau(dataConc2grau);
		a.setIsntConc2grau(isntConc2grau);
		a.setPtVestibular(ptVestibular);
		a.setPosVestibular(posVestibular);
		
		for (Curso c : cursos) {
			if (c.getCod() == curso) {
				a.setCurso(c);
			}
		}
		
		boolean valido = adao.iudCrud("i", a);
		
		if (valido) {
			alunos.add(a);			
		}
	}

	private Aluno buscar(String ra) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				return adao.buscar(a);
			}		
		}
		return null;
	}

	private void atualizar(String ra, String nome, String nomeSocial, String emailPessoal, String dataNasc, String dataConc2grau, String isntConc2grau, String cpf, int ptVestibular, int posVestibular, int curso) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				a.setCpf(cpf);
				a.setNome(nome);
				a.setNomeSocial(nomeSocial);
				a.setEmailPessoal(emailPessoal);
				a.setEmailCorporativo(geraEmailCorporativo(nome));
				a.setDataNasc(dataNasc);
				a.setDataConc2grau(dataConc2grau);
				a.setIsntConc2grau(isntConc2grau);
				a.setPtVestibular(ptVestibular);
				a.setPosVestibular(posVestibular);
				
				for (Curso c : cursos) {
					if (c.getCod() == curso) {
						a.setCurso(c);
					}
				}
				adao.iudCrud("u", a);
				break;
			}		
		}
		
	}

	private void deletar(String ra) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				boolean valido = adao.iudCrud("u", a);
				if (valido) { 
					alunos.remove(a);				
				}
				break;
			}		
		}
	}
	
	private String geraEmailCorporativo(String nome) {
		String[] vetNomes = nome.split(" ");
		
		String emailCorp = vetNomes[0] + "." + vetNomes[vetNomes.length - 1] + "@email.com";
		
		return emailCorp;
	}
}
