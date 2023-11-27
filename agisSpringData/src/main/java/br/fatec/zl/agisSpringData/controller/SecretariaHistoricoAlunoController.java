package br.fatec.zl.agisSpringData.controller;

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
import br.fatec.zl.agisSpringData.model.dto.HistoricoDto;
import br.fatec.zl.agisSpringData.services.AlunoService;

@Controller
@RequestMapping("/historicoAluno")
public class SecretariaHistoricoAlunoController {
	@Autowired
	private AlunoService aserv;
	
	private List<Aluno> alunos = new ArrayList<>();
	private List<HistoricoDto> historico = new ArrayList<>();
	
	@GetMapping
	public String get() {
		if (!historico.isEmpty()) { historico.removeAll(historico); }
		
		alunos.addAll(aserv.buscarTudo());
		
		return "secretariaHistoricoAluno";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		String ra = param.get("ra");
		
		Aluno a = buscarAluno(ra);
		
		switch (param.get("botao")) {
		case "Buscar":
			historico = buscarHistorico(a);
			break;
		}
		
		model.addAttribute("aluno", a);
		model.addAttribute("historico", historico);
		
		return "secretariaHistoricoAluno";
	}
	
	private Aluno buscarAluno(String ra) {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				return aserv.buscar(a);
			}		
		}
		return null;
	}
	
	private List<HistoricoDto> buscarHistorico(Aluno aluno) {
		return aserv.listaHistorico(aluno);
	}
}
