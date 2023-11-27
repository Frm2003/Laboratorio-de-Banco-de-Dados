package br.fatec.zl.agisSpringData.controller;

import java.math.BigDecimal;
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

import br.fatec.zl.agisSpringData.model.Curso;
import br.fatec.zl.agisSpringData.services.CursoService;

@Controller
@RequestMapping("/curso")
public class CursoCrudController {
	@Autowired
	private CursoService cserv;

	private List<Curso> cursos = new ArrayList<>();

	@GetMapping
	public String get(ModelMap model) {
		if (!cursos.isEmpty()) { cursos.removeAll(cursos); }

		cursos.addAll(cserv.buscarTudo());
		model.addAttribute("cursos", cursos);
		
		return "secretariaCurso";
	}

	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		String cod = param.get("cod");
		String nome = param.get("nome");
		String cargaHoraria = param.get("cargaHoraria");
		String sigla = param.get("sigla");
		String enade = param.get("enade");
		String turno = param.get("turno");
		
		switch (param.get("botao")) {
		case "Inserir":
			criar(nome, Integer.parseInt(cargaHoraria), sigla, Double.parseDouble(enade), turno);
			break;
		case "Atualizar":
			atualizar(Long.parseLong(cod), nome, Integer.parseInt(cargaHoraria), sigla, Double.parseDouble(enade), turno);
			break;
		case "Buscar":
			model.addAttribute("curso", buscar(Long.parseLong(cod)));
			break;
		case "Deletar":
			deletar(Long.parseLong(cod));
			break;
		}
		
		model.addAttribute("cursos", cursos);
		
		return "secretariaCurso";
	}
	
	private void criar(String nome, int cargaHoraria, String sigla, double enade, String turno) {
		Curso c = new Curso(nome, cargaHoraria, sigla, BigDecimal.valueOf(enade), turno);
		cserv.inserir(c);
		cursos.add(c);
	}
	
	private void atualizar(Long cod, String nome, int cargaHoraria, String sigla, double enade, String turno) {
		for (Curso c: cursos) {
			if (c.getCod() == cod) {
				c = new Curso(cod, nome, cargaHoraria, sigla, BigDecimal.valueOf(enade), turno);
				cserv.atualizar(c);
				break;
			}
		}
	}
	
	private Curso buscar(Long cod) {
		for (Curso c : cursos) {
			if (c.getCod() == cod) {
				return cserv.buscar(c);
			}
		}
		return null;
	}
	
	private void deletar(Long cod) {
		for (Curso c : cursos) {
			if (c.getCod() == cod) {
				cserv.deletar(c);
				cursos.remove(c);
				break;
			}
		}
	}
}