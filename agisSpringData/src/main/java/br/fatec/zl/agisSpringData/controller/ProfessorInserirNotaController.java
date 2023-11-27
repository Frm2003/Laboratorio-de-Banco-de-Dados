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
import br.fatec.zl.agisSpringData.model.Nota;
import br.fatec.zl.agisSpringData.services.MateriaService;
import br.fatec.zl.agisSpringData.services.NotasService;


@Controller
@RequestMapping("/professorInseriNota")
public class ProfessorInserirNotaController {	
	@Autowired
	private MateriaService mserv;
	
	@Autowired
	private NotasService nserv;
	
	private List<Materia> materias = new ArrayList<>();
	private List<Nota> notas = new ArrayList<>();
	
	@GetMapping
	public String get(ModelMap model) {
		notas = nserv.buscarTudo();
		materias = mserv.buscarTudo();
		
		model.addAttribute("materias", materias);
		
		return "professorInseriNota";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		String codMateria = param.get("codMateria");
		
		switch (param.get("botao")) {
		case "Buscar matriculas":
			model.addAttribute("notas", notasDasMaterias(Long.parseLong(codMateria)));
			break;
		case "Aplicar Nota": 	
			String codNota = param.get("codNota");
			String tipoAv = param.get("tipoAvalicao");
			String situacao = "";
			
			float p1 = Float.parseFloat(param.get("p1"));
			float p2 = Float.parseFloat(param.get("p2"));
			float p3 = Float.parseFloat(param.get("t"));
			
			float val = nserv.calculaNotas(tipoAv, p1, p2, p3);
			
			if (val >= 6) {
				situacao = "aprovado";
			} else if (val >= 3) {
				situacao = "em exame";
			} else {
				situacao = "reprovado";
			}
			
			inserirNotas(Long.parseLong(codNota), situacao, p1, p2, p3);
			
			break;
		}
		model.addAttribute("materias", materias);
			
		return "professorInseriNota";
	}
	
	private void inserirNotas(Long codNota, String situacao, float ... notas) {
		Nota n = nserv.buscar(codNota);
		
		n.getMatricula().setSituacao(situacao);
		if (situacao.equals("aprovado")) {
			n.getMatricula().setAprovado(true);
		} 
		
		n.setN1(notas[0]);
		n.setN2(notas[1]);
		n.setN3(notas[2]);
		
		nserv.inserir(n);
	}
	
	private List<Nota> notasDasMaterias(Long codMateria) {
		for (Materia m : materias) {
			if (m.getCod() == codMateria) {
				return nserv.listarNotasMateria(m);
			}
		}
		return null;
	}

}