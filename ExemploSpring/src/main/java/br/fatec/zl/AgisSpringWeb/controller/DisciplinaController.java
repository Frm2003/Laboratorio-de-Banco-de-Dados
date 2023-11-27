package br.fatec.zl.AgisSpringWeb.controller;

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

import br.fatec.zl.AgisSpringWeb.model.Curso;
import br.fatec.zl.AgisSpringWeb.model.Disciplina;
import br.fatec.zl.AgisSpringWeb.persistence.CursoDao;
import br.fatec.zl.AgisSpringWeb.persistence.DisciplinaDao;

@Controller
public class DisciplinaController {
	@Autowired
	private CursoDao cdao;
	
	@Autowired
	private DisciplinaDao ddao;
	
	private List<Curso> cursos = new ArrayList<>();
	private List<Disciplina> disciplinas = new ArrayList<>();

	@RequestMapping(name = "disciplina", value = "/disciplina", method = RequestMethod.GET)
	public ModelAndView disciplinaGet(ModelMap model) {
		if (!cursos.isEmpty()) { cursos.removeAll(cursos); }
		if (!disciplinas.isEmpty()) { disciplinas.removeAll(disciplinas); }
		
		try {
			cursos.addAll(cdao.listar());
			disciplinas.addAll(ddao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		
		model.addAttribute("cursos", cursos);
		model.addAttribute("disciplinas", disciplinas);
		
		return new ModelAndView("secretariaDisciplina") ;
	}
	
	@RequestMapping(name = "disciplina", value = "/disciplina", method = RequestMethod.POST)
	public ModelAndView disciplinaPost(@RequestParam Map<String, String> param, ModelMap model) {
		String cod = param.get("cod");
		String nome = param.get("nome");
		String qtdAulas = param.get("qtdAulas");
		String semestre = param.get("semestre");
		String horario = param.get("horario");
		String curso = param.get("curso");
		
		try {
			switch (param.get("botao")) {
			case "Inserir":
				inserir(nome, Integer.parseInt(qtdAulas), Integer.parseInt(semestre), horario, Integer.parseInt(curso));
				break;
			case "Buscar":
				if (!disciplinas.isEmpty()) {
					model.addAttribute("disciplina", buscar(Integer.parseInt(cod)));
				}
				break;
			case "Atualizar":
				if (!disciplinas.isEmpty()) {					
					atualizar(Integer.parseInt(cod), nome, Integer.parseInt(qtdAulas), Integer.parseInt(semestre), horario, Integer.parseInt(curso));
				}
				break;
			case "Deletar":
				if (!disciplinas.isEmpty()) {					
					deletar(Integer.parseInt(cod));
				} 
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			model.addAttribute("disciplinas", disciplinas);
			model.addAttribute("cursos", cursos);
		}
		return new ModelAndView("secretariaDisciplina") ;
	}
	
	private void inserir(String nome, int qtdAulas, int semestre, String horario, int curso) throws ClassNotFoundException, SQLException {
		Disciplina d = new Disciplina();
		
		d.setCod(1001 + disciplinas.size());
		d.setNome(nome);
		d.setQtdAulas(qtdAulas);
		d.setSemestre(semestre);
		d.setHorario(horario);
		
		for (Curso c : cursos) {
			if (c.getCod() == curso) {
				d.setCurso(c);
			}
		}
		
		boolean valido = ddao.iudCrud("i", d);
		
		if (valido) {			
			disciplinas.add(d);
		}
	}

	private Disciplina buscar(int cod) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				return ddao.buscar(d);
			}
		}
		return null;
	}

	private void atualizar(int cod, String nome, int qtdAulas, int semestre, String horario, int curso) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				d.setNome(nome);
				d.setQtdAulas(qtdAulas);
				d.setSemestre(semestre);
				d.setHorario(horario);
			
				for (Curso c : cursos) {
					if (c.getCod() == curso) {
						d.setCurso(c);
					}
				}
				ddao.iudCrud("u", d);
				break;
			}
		}
	}

	private void deletar(int cod) throws ClassNotFoundException, SQLException {
		for (Disciplina d : disciplinas) {
			if (d.getCod() == cod) {
				boolean valido = ddao.iudCrud("d", d);
				if (valido) {
					disciplinas.remove(d);
				}
				break;
			}
		}
	}
}
