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
import br.edu.fateczl.agisSpringBoot.model.Disciplina;
import br.edu.fateczl.agisSpringBoot.model.Matricula;
import br.edu.fateczl.agisSpringBoot.persistence.AlunoDao;
import br.edu.fateczl.agisSpringBoot.persistence.DisciplinaDao;
import br.edu.fateczl.agisSpringBoot.persistence.MatriculaDao;

@Controller
public class AlunoMatriculaDispoController {
	private String ra;
	
	@Autowired
	private AlunoDao adao;
	
	@Autowired
	private DisciplinaDao ddao;
	
	@Autowired
	private MatriculaDao mdao;
	
	private List<Aluno> alunos = new ArrayList<>();
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@RequestMapping(name = "matricula", value = "/matricula", method = RequestMethod.GET)
	public ModelAndView matriculaGet(@RequestParam Map<String, String> param, ModelMap model) {
		if (!alunos.isEmpty()) { alunos.removeAll(alunos); }
		if (!disciplinas.isEmpty()) { disciplinas.removeAll(disciplinas); }
		
		try {
			alunos.addAll(adao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		
		return new ModelAndView("alunoMatriculaDispo");
	}
	
	@RequestMapping(name = "matricula", value = "/matricula", method = RequestMethod.POST)
	public ModelAndView matriculaPost(@RequestParam Map<String, String> param, ModelMap model) {
		String ra = param.get("ra");
		
		try {
			switch (param.get("botao")) {
			case "Ver disciplinas":
				if (!disciplinas.isEmpty()) { disciplinas.removeAll(disciplinas); }
				disciplinas.addAll(listarDisicplinasNaoMatriculadas(ra));
				this.ra = ra;
				break;
			case "Matricular":
				String codDisci = param.get("cod");
				inserir(this.ra, Integer.parseInt(codDisci));
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("ra", ra);
		model.addAttribute("disciplinas", disciplinas);
		
		return new ModelAndView("alunoMatriculaDispo");
	}
	
	private void inserir(String ra, int cod) throws ClassNotFoundException, SQLException {
		Matricula m = new Matricula();
		
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				m.setAluno(a);
			}
		}
		
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				m.setDisciplina(d);
			}
		}
		
		m.setSituacao("cursando");
		m.setNota(0);
		m.setAprovado(false);
	
		mdao.iudCrud("i", m);
	}
	
	public List<Disciplina> listarDisicplinasNaoMatriculadas(String ra) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				return ddao.listarDisicplinasNaoMatriculadas(a);
			}
		}
		return null;
	}

}
