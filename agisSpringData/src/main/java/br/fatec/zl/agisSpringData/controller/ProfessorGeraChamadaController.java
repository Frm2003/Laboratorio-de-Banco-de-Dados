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

import br.fatec.zl.agisSpringData.model.Aluno;
import br.fatec.zl.agisSpringData.model.Chamada;
import br.fatec.zl.agisSpringData.model.Materia;
import br.fatec.zl.agisSpringData.model.dto.ChamadaDto;
import br.fatec.zl.agisSpringData.services.AlunoService;
import br.fatec.zl.agisSpringData.services.ChamadaService;
import br.fatec.zl.agisSpringData.services.MateriaService;

@Controller
@RequestMapping("/geraChamada")
public class ProfessorGeraChamadaController {
	@Autowired
	private AlunoService aserv;
	
	@Autowired
	private ChamadaService cserv;
	
	@Autowired
	private MateriaService mserv;
	
	private List<Materia> materias = new ArrayList<>();
	private List<ChamadaDto> chamadas = new ArrayList<>();
	
	@GetMapping
	public String get(ModelMap model) {
		if (!materias.isEmpty()) { materias.removeAll(materias); }
		
		materias.addAll(mserv.buscarTudo());
		model.addAttribute("materias", materias);
		
		return "professorGeraChamada";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		if (!chamadas.isEmpty()) { chamadas.removeAll(chamadas); }
		
		String cod = param.get("codMateria");
		System.out.println(cod);
		
		switch (param.get("botao")) {
		case "Gerar Chamada":
			chamadas.addAll(geraChamada(Long.parseLong(cod)));
			model.addAttribute("chamadas", chamadas);
			break;
		case "Realizar Chamada":
			String ra = param.get("ra");
			String dataChamada = param.get("dataChamada");
			String qtdFalta = param.get("qtdFalta");
			
			realizarChamada(ra, Long.parseLong(cod), dataChamada, Integer.parseInt(qtdFalta));
			break;
		}
		
		model.addAttribute("materias", materias);
		
		return "professorGeraChamada";
	}
	
	private void realizarChamada(String ra, Long codMat, String dataChamada, int falta) {
		Chamada c = new Chamada();
		
		for (Aluno a : aserv.buscarTudo()) {
			if (a.getRa().equals(ra)) {
				c.setAluno(a);
			}
		}
		
		for (Materia m : materias) {
			if (m.getCod() == codMat) {
				c.setMateria(m);
			}
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		c.setData(LocalDate.parse(dataChamada, dtf));
		
		c.setFaltas(falta);
		
		cserv.inserir(c);
	}
	
	private List<ChamadaDto> geraChamada(Long cod) {
		for (Materia m : materias) {
			if (m.getCod() == cod) {
				return cserv.geraChamada(m);
			}
		}
		return null;
	}
	
}
