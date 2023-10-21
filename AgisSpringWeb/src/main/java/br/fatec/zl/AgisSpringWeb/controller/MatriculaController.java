package br.fatec.zl.AgisSpringWeb.controller;

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

import br.fatec.zl.AgisSpringWeb.model.Aluno;
import br.fatec.zl.AgisSpringWeb.model.Disciplina;
import br.fatec.zl.AgisSpringWeb.model.Matricula;
import br.fatec.zl.AgisSpringWeb.persistence.AlunoDao;
import br.fatec.zl.AgisSpringWeb.persistence.BuscaRaDao;
import br.fatec.zl.AgisSpringWeb.persistence.DisciplinaDao;
import br.fatec.zl.AgisSpringWeb.persistence.MatriculaDao;

@Controller
public class MatriculaController {
	
	@Autowired
	private AlunoDao adao;
	
	@Autowired
	private BuscaRaDao brdao;
	
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
			disciplinas.addAll(ddao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		
		return new ModelAndView("matricula");
	}
	
	@RequestMapping(name = "matricula", value = "/matricula", method = RequestMethod.POST)
	public ModelAndView matriculaPost(@RequestParam Map<String, String> param, ModelMap model) {
		String ra = param.get("ra");
		String codDisci = param.get("cod");
		
		try {
			switch (param.get("botao")) {
			case "Matricular":
				inserir(ra, Integer.parseInt(codDisci));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				model.addAttribute("ra", ra);
				model.addAttribute("disciplinas", listarDisicplinasNaoMatriculadas(ra));
				model.addAttribute("matriculas", listarMatriculada(ra));
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		return new ModelAndView("matricula");
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
	
		mdao.iudCrud("i", m);
	}
	
	private List<Disciplina> listarDisicplinasNaoMatriculadas(String ra) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				return brdao.listarDisicplinasNaoMatriculadas(a);
			}
		}
		return null;
	}
	
	private List<Matricula> listarMatriculada(String ra) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				return mdao.listarMatriculas(a);
			}
		}
		return null;
	}

}
