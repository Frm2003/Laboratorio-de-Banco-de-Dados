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
import br.edu.fateczl.agisSpringBoot.model.Matricula;
import br.edu.fateczl.agisSpringBoot.persistence.AlunoDao;
import br.edu.fateczl.agisSpringBoot.persistence.MatriculaDao;

@Controller
public class AlunoHistoricoController {
	private String ra;
	
	@Autowired
	private AlunoDao adao;

	@Autowired
	private MatriculaDao mdao;

	private List<Aluno> alunos = new ArrayList<>();
	private List<Matricula> matriculas = new ArrayList<>();

	@RequestMapping(name = "historico", value = "/historico", method = RequestMethod.GET)
	public ModelAndView historicoRaGet(ModelMap model) {
		if (!alunos.isEmpty()) { alunos.removeAll(alunos); }
		if (!matriculas.isEmpty()) { matriculas.removeAll(matriculas); }

		try {
			alunos.addAll(adao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return new ModelAndView("alunoHistorico");
	}

	@RequestMapping(name = "historico", value = "/historico", method = RequestMethod.POST)
	public ModelAndView historicoPost(@RequestParam Map<String, String> param, ModelMap model) {
		String ra = param.get("ra");
		
		try {
			switch (param.get("botao")) {
			case "Ver matriculas":
				if (!matriculas.isEmpty()) { matriculas.removeAll(matriculas); }
				matriculas.addAll(historicoAlunoRa(ra));
				this.ra = ra;
				break;
			case "Dispensar":
				String codDisci = param.get("cod");
				dispensar(this.ra, Integer.parseInt(codDisci));
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("matriculas", matriculas);
		model.addAttribute("ra", ra);

		return new ModelAndView("alunoHistorico");
	}

	private List<Matricula> historicoAlunoRa(String ra) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				return mdao.listarMatriculas(a);
			}
		}
		return null;
	}
	
	private void dispensar(String ra, int cod) throws ClassNotFoundException, SQLException {
		for (Matricula m : matriculas) {
			if (m.getAluno().getRa().equals(ra) && m.getDisciplina().getCod() == cod) {
				mdao.iudCrud("u", m);
			}
		}
	}
}