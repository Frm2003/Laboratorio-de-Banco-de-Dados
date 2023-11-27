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

import br.fatec.zl.agisSpringData.model.Materia;
import br.fatec.zl.agisSpringData.model.dto.ChamadaTurmaDto;
import br.fatec.zl.agisSpringData.services.MateriaService;

@Controller
@RequestMapping("professorHistoricoChamadas")
public class ProfessorHistoricoChamadasController {
	@Autowired
	private MateriaService mserv;
	
	private List<Materia> materias = new ArrayList<>();
	
	@GetMapping
	public String get(ModelMap model) {
		if (!materias.isEmpty()) { materias.removeAll(materias); }
		
		materias.addAll(mserv.buscarTudo());
		model.addAttribute("materias", materias);
		
		return "professorHistoricoChamadas";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		String cod = param.get("codMateria");
		
		switch (param.get("botao")) {
		case "Buscar":
			model.addAttribute("historico", listaChamadaTurma(Long.parseLong(cod)));
			break;
		}
		
		model.addAttribute("materias", materias);
		
		return "professorHistoricoChamadas";
	}
	
	private List<ChamadaTurmaDto> listaChamadaTurma (Long cod) {
		for (Materia m : materias) {
			if (m.getCod() == cod) {
				return mserv.listaChamadaTurma(m);
			}
		}
		return null;
	}
}
