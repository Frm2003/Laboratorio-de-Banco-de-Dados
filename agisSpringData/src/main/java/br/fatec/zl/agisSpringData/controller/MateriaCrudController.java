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

import br.fatec.zl.agisSpringData.model.Disciplina;
import br.fatec.zl.agisSpringData.model.Materia;
import br.fatec.zl.agisSpringData.model.Professor;
import br.fatec.zl.agisSpringData.services.DisciplinaService;
import br.fatec.zl.agisSpringData.services.MateriaService;
import br.fatec.zl.agisSpringData.services.ProfessorService;

@Controller
@RequestMapping("/materia")
public class MateriaCrudController {
	@Autowired
	private DisciplinaService dserv;
	
	@Autowired
	private MateriaService mserv;
	
	@Autowired
	private ProfessorService pserv;
	
	private List<Disciplina> disciplinas = new ArrayList<>();
	private List<Materia> materias = new ArrayList<>();
	private List<Professor> professores = new ArrayList<>();
	
	@GetMapping
	public String get(ModelMap model) {
		if (!disciplinas.isEmpty()) { disciplinas.removeAll(disciplinas); }
		if (!materias.isEmpty()) { materias.removeAll(materias); }
		if (!professores.isEmpty()) { professores.removeAll(professores); }

		disciplinas.addAll(dserv.buscarTudo());
		materias.addAll(mserv.buscarTudo());
		professores.addAll(pserv.buscarTudo());
		
		model.addAttribute("disciplinas", disciplinas);
		model.addAttribute("materias", materias);
		model.addAttribute("professores", professores);
		
		return "secretariaMateria";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		String cod = param.get("cod");
		String horario = param.get("horario");
		String diaDaSemana = param.get("diaDaSemana");
		String codDisci = param.get("codDisci");
		String codProf = param.get("codProf");
		String tipoAv = param.get("tipoAv");
		
		try {
			switch (param.get("botao")) {
			case "Inserir":
				criar(horario, diaDaSemana, Long.parseLong(codDisci), Long.parseLong(codProf), tipoAv);
				break;
			case "Atualizar":
				atualizar(Long.parseLong(cod), horario, diaDaSemana, Long.parseLong(codDisci), Long.parseLong(codProf), tipoAv);
				break;
			case "Buscar":
				model.addAttribute("materia", buscar(Long.parseLong(cod)));
				break;
			case "Deletar":
				deletar(Long.parseLong(cod));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("disciplinas", disciplinas);
		model.addAttribute("materias", materias);
		model.addAttribute("professores", professores);
		
		return "secretariaMateria";
	}
	private void criar(String horario, String diaDaSemana, Long codDisci, Long codProf, String tipoAv) {
		Materia m = new Materia();
		
		m.setCod(1 + mserv.contador());
		m.setHorario(horario);
		m.setDiaDaSemana(diaDaSemana);
		m.setTipoAvalicao(tipoAv);
		
		for (Disciplina d : disciplinas) {
			if (d.getCod() == codDisci) {
				m.setDisciplina(d);
			}
		}
		
		for (Professor p : professores) {
			if (p.getCod() == codProf) {
				m.setProfessor(p);
			}
		}
		
		mserv.inserir(m);
		materias.add(m);
	}

	private void atualizar(long cod, String horario, String diaDaSemana, Long codDisci, Long codProf, String tipoAv) {
		for (Materia m : materias) {
			if (m.getCod() == cod) {
				m.setHorario(horario);
				m.setDiaDaSemana(diaDaSemana);
				m.setTipoAvalicao(tipoAv);
				
				for (Disciplina d : disciplinas) {
					if (d.getCod() == codDisci) {
						m.setDisciplina(d);
					}
				}
				
				for (Professor p : professores) {
					if (p.getCod() == codProf) {
						m.setProfessor(p);
					}
				}
				
				mserv.atualizar(m);
				break;
			}
		}
	}

	private Materia buscar(long cod) {		for (Materia m : materias) {
			if (m.getCod() == cod) {
				return mserv.buscar(m);
			}
		}
		return null;
	}

	private void deletar(long cod) {		for (Materia m : materias) {
			if (m.getCod() == cod) {
				m.setDisciplina(null);
				m.setProfessor(null);
				mserv.atualizar(m);
				mserv.deletar(m);
				break;
			}
		}
	}
	
}
