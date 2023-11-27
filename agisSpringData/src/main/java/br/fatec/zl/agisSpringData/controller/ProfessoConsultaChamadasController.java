package br.fatec.zl.agisSpringData.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import br.fatec.zl.agisSpringData.model.Chamada;
import br.fatec.zl.agisSpringData.model.Materia;
import br.fatec.zl.agisSpringData.services.ChamadaService;
import br.fatec.zl.agisSpringData.services.MateriaService;

@Controller
@RequestMapping("/consultaChamada")
public class ProfessoConsultaChamadasController {
	@Autowired
	private ChamadaService cserv;
	
	@Autowired
	private MateriaService mserv;
	
	private List<Materia> materias = new ArrayList<>();
	private List<Chamada> chamadas = new ArrayList<>();
	
	@GetMapping
	public String get(ModelMap model) {
		if (!materias.isEmpty()) { materias.removeAll(materias); }
		
		chamadas.addAll(cserv.buscaTudo());
		materias.addAll(mserv.buscarTudo());
		
		model.addAttribute("turmas", materias);
		
		return "professorConsultaChamada";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		String ra = param.get("ra");
		
		String codTurma = param.get("codTurma");
		String codMateria = param.get("codMateria");
		
		String data = param.get("data");
		String faltas = param.get("qtdFalta");
		
		try {
			switch (param.get("botao")) {
			case "Buscar":
				model.addAttribute("chamadas", consultaChamada(Long.parseLong(codTurma), data));
				break;
			case "Atualizar":
				atualizarChamada(ra, Long.parseLong(codMateria), data, Integer.parseInt(faltas));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		model.addAttribute("turmas", materias);
		
		return "professorConsultaChamada";
	}

	private List<Chamada> consultaChamada(Long codTurma, String dataChamada) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		for (Chamada c : chamadas) {
			if (c.getMateria().getCod() == codTurma && c.getData().isEqual(LocalDate.parse(dataChamada, dtf))) {
				return cserv.consultaChamada(c);
			} 
		}
		return null;
	}
	
	private void atualizarChamada(String ra, Long codTurma, String dataChamada, int faltas) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		for (Chamada c : chamadas) {
			if (c.getAluno().getRa().equals(ra) && c.getMateria().getCod() == codTurma && c.getData().isEqual(LocalDate.parse(dataChamada, dtf))) {
				c.setFaltas(faltas);
				cserv.atualizar(c);
			} 
		}
	}
}
