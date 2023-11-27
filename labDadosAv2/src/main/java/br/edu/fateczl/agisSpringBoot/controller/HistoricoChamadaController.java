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
public class HistoricoChamadaController {
	@Autowired
	private AlunoDao adao;
	
	@Autowired
	private DisciplinaDao ddao;
	
	@Autowired
	private ChamadaDao cdao;
	
	private List<Aluno> alunos = new ArrayList<>();
	private List<Chamada> chamadas = new ArrayList<>();
	private List<Disciplina> disciplinas = new ArrayList<>();

	private String cod;
	
	@RequestMapping(name = "chamadaHistorico", value = "/chamadaHistorico", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model) {
		if (!alunos.isEmpty()) { alunos.removeAll(alunos); }
		if (!chamadas.isEmpty()) { chamadas.removeAll(chamadas); }
		if (!disciplinas.isEmpty()) { disciplinas.removeAll(disciplinas); }
		
		try {
			alunos.addAll(adao.listar());
			disciplinas.addAll(ddao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		
		model.addAttribute("disciplinas", disciplinas);
		
		return new ModelAndView("professorChamadaHistorico");
	}
	
	@RequestMapping(name = "chamadaHistorico", value = "/chamadaHistorico", method = RequestMethod.POST)
	public ModelAndView post(@RequestParam Map<String, String> param, ModelMap model) {
		String cod = param.get("codDisci");
		String data = param.get("data");
		
		try {
			switch (param.get("botao")) {
			case "Buscar":
				if (!chamadas.isEmpty()) { chamadas.removeAll(chamadas); }
				chamadas.addAll(buscarChamada(Integer.parseInt(cod), data));
				this.cod = cod;
				break;
			case "Atualizar":
				String ra = param.get("ra");
				data = param.get("dataChamada");
				
				atualizar(ra, Integer.parseInt(this.cod), data);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("chamadas", chamadas);
		model.addAttribute("disciplinas", disciplinas);
		
		return new ModelAndView("professorChamadaHistorico");
	}

	private void atualizar(String ra, int cod, String data) throws ClassNotFoundException, SQLException {
		Chamada ch = new Chamada();
	
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				ch.setAluno(a);
			}		
		}
		
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				ch.setDisciplina(d);
			}
		}
		
		ch.setData(data);
		
		cdao.iudCrud("u", ch);
		
	}

	private List<Chamada> buscarChamada(int cod, String data) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				return cdao.buscaChamada(d, data);
			}
		}
		return chamadas;
	}
}
