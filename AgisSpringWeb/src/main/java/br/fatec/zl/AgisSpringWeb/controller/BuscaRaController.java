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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.fatec.zl.AgisSpringWeb.model.Aluno;
import br.fatec.zl.AgisSpringWeb.model.Disciplina;
import br.fatec.zl.AgisSpringWeb.model.Matricula;
import br.fatec.zl.AgisSpringWeb.persistence.AlunoDao;
import br.fatec.zl.AgisSpringWeb.persistence.BuscaRaDao;
import br.fatec.zl.AgisSpringWeb.persistence.MatriculaDao;

@Controller
public class BuscaRaController {
	
	@Autowired
	private AlunoDao adao;
	
	@Autowired
	private BuscaRaDao brdao;
	
	@Autowired
	private MatriculaDao mdao;

	private List<Aluno> alunos = new ArrayList<>();

	@RequestMapping(name = "login", value = "/login", method = RequestMethod.GET)
	public ModelAndView buscaRaGet(ModelMap model) {
		if (!alunos.isEmpty()) { alunos.removeAll(alunos); }

		try {
			alunos.addAll(adao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("alunoBuscaRa");
	}

	@RequestMapping(name = "login", value = "/login", method = RequestMethod.POST)
	public RedirectView buscaRaPost(@RequestParam Map<String, String> param, RedirectAttributes model) {
		String ra = param.get("ra");
		try {
			switch (param.get("botao")) {
			case "Ver matriculas": 
				model.addFlashAttribute("ra", ra);
				model.addFlashAttribute("disciplinas", listarDisicplinasNaoMatriculadas(ra));
				model.addFlashAttribute("matriculas", listarMatriculada(ra));
				return new RedirectView("matricula");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
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
