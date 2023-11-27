
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
import br.edu.fateczl.agisSpringBoot.model.AlunoHistorico;
import br.edu.fateczl.agisSpringBoot.persistence.AlunoDao;

@Controller
public class SecretariaHitoricoAlunoController {
	@Autowired
	private AlunoDao adao;
	
	private List<Aluno> alunos = new ArrayList<>();
	private List<AlunoHistorico> historico = new ArrayList<>();
	
	@RequestMapping(name = "historicoAluno", value = "/historicoAluno", method = RequestMethod.GET)
	public ModelAndView get() {
		if (!historico.isEmpty()) { historico.removeAll(historico); }
		
		try {
			alunos.addAll(adao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("secretariaHistoricoAluno");
	}
	
	@RequestMapping(name = "historicoAluno", value = "/historicoAluno", method = RequestMethod.POST)
	public ModelAndView post(@RequestParam Map<String, String> param, ModelMap model) {
		String ra = param.get("ra");
		Aluno aluno = null;
		
		try {
			switch (param.get("botao")) {
			case "Buscar":
				if (!historico.isEmpty()) { historico.removeAll(historico); }
				aluno = buscarAluno(ra);
				historico.addAll(buscarHistorico(aluno));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("historico", historico);
		model.addAttribute("aluno", aluno);
		
		return new ModelAndView("secretariaHistoricoAluno");
	}

	private Aluno buscarAluno(String ra) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				return adao.buscar(a);
			}		
		}
		return null;
	}
	
	private List<AlunoHistorico> buscarHistorico(Aluno aluno) throws ClassNotFoundException, SQLException {
		return adao.historicoAluno(aluno);
	}

}
