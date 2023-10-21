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
import br.edu.fateczl.agisSpringBoot.model.Chamada;
import br.edu.fateczl.agisSpringBoot.model.Disciplina;
import br.edu.fateczl.agisSpringBoot.persistence.AlunoDao;
import br.edu.fateczl.agisSpringBoot.persistence.ChamadaDao;
import br.edu.fateczl.agisSpringBoot.persistence.DisciplinaDao;

@Controller
public class RealizarChamadaController {
	@Autowired
	private AlunoDao adao;
	
	@Autowired
	private ChamadaDao cdao;
	
	@Autowired
	private DisciplinaDao ddao;
	
	private List<Aluno> alunos = new ArrayList<>();
	private List<Chamada> chamada = new ArrayList<>();
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@RequestMapping(name = "chamada", value = "/chamada", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model) {
		if (!alunos.isEmpty()) { alunos.removeAll(alunos); }
		if (!disciplinas.isEmpty()) { disciplinas.removeAll(disciplinas); }
		
		try {
			alunos.addAll(adao.listar());
			disciplinas.addAll(ddao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("disciplinas", disciplinas);
		
		return new ModelAndView("professorChamada");
	}
	
	@RequestMapping(name = "chamada", value = "/chamada", method = RequestMethod.POST)
	public ModelAndView post(@RequestParam Map<String, String> param, ModelMap model) {
		if (!chamada.isEmpty()) { chamada.removeAll(chamada); }
		
		String cod = param.get("codDisci");
		
		try {
			switch (param.get("botao")) {
			case "Gerar Chamada":
				chamada.addAll(geraChamada(Integer.parseInt(cod)));
				break;
			case "Realizar Chamada":
				String ra = param.get("ra");
				cod = param.get("cod");
				int falta = Integer.parseInt(param.get("qtdFalta"));
				
				inserir(ra, Integer.parseInt(cod), falta);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		model.addAttribute("disciplinas", disciplinas);
		model.addAttribute("chamada", chamada);
		
		return new ModelAndView("professorChamada");
	}

	private void inserir(String ra, int cod, int falta) throws ClassNotFoundException, SQLException {
		Chamada c = new Chamada();
		
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				c.setAluno(a);
			}
		}
		
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				c.setDisciplina(d);
			}
		}
		
		c.setFaltas(falta);
		cdao.iudCrud("i", c);
	}

	private List<Chamada> geraChamada(int cod) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				return cdao.geraChamada(d);
			}
		}
		return null;
	}
}
