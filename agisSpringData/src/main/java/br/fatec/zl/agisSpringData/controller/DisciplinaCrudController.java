package br.fatec.zl.agisSpringData.controller;

import java.sql.SQLException;
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
import br.fatec.zl.agisSpringData.model.Disciplina;
import br.fatec.zl.agisSpringData.services.CursoService;
import br.fatec.zl.agisSpringData.services.DisciplinaService;

@Controller
@RequestMapping("/disciplina")
public class DisciplinaCrudController {
	@Autowired
	private CursoService cserv;
	
	@Autowired
	private DisciplinaService dserv;
	
	private List<Curso> cursos = new ArrayList<>();
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@GetMapping
	public String get(ModelMap model) {
		if (!cursos.isEmpty()) { cursos.removeAll(cursos); }
		if (!disciplinas.isEmpty()) { disciplinas.removeAll(disciplinas); }

		cursos.addAll(cserv.buscarTudo());
		disciplinas.addAll(dserv.buscarTudo());
		
		model.addAttribute("cursos", cursos);
		model.addAttribute("disciplinas", disciplinas);
		
		return "secretariaDisciplina";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		String cod = param.get("cod");
		String nome = param.get("nome");
		String qtdAulas = param.get("qtdAulas");
		String semestre = param.get("semestre");
		String curso = param.get("curso");
		
		try {
			switch (param.get("botao")) {
			case "Inserir":
				inserir(nome, Long.parseLong(qtdAulas), Long.parseLong(semestre), Long.parseLong(curso));
				break;
			case "Buscar":
				model.addAttribute("disciplina", buscar(Long.parseLong(cod)));
				break;
			case "Atualizar":
				atualizar(Long.parseLong(cod), nome, Long.parseLong(qtdAulas), Long.parseLong(semestre), Long.parseLong(curso));
				break;
			case "Deletar":
				deletar(Long.parseLong(cod));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("cursos", cursos);
		model.addAttribute("disciplinas", disciplinas);
		
		return "secretariaDisciplina";
	}
	
	private void inserir(String nome, Long qtdAulas, Long semestre, Long codCurso) throws ClassNotFoundException, SQLException {
		Disciplina d = new Disciplina();
		
		//d.setCod(1 + dserv.contador());
		d.setNome(nome);
		d.setQtdAulas(qtdAulas);
		d.setSemestre(semestre);
		
		for (Curso c : cursos) {
			if (c.getCod() == codCurso) {
				d.setCurso(cserv.buscar(c));
			}
		}
		
		dserv.inserir(d);
		disciplinas.add(d);
	}

	private Disciplina buscar(Long cod) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				return dserv.buscar(d);
			}
		}
		return null;
	}

	private void atualizar(Long cod, String nome, Long qtdAulas, Long semestre, Long codCurso) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				d.setNome(nome);
				d.setQtdAulas(qtdAulas);
				d.setSemestre(semestre);
				
				for (Curso c : cursos) {
					if (c.getCod() == codCurso) {
						d.setCurso(cserv.buscar(c));
					}
				}
				dserv.atualizar(d);
				break;
			}
		}
	}

	private void deletar(Long cod) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				d.setCurso(null);
				dserv.atualizar(d);
				dserv.deletar(d);
				disciplinas.remove(d);
				break;
			}
		}
	}
}
