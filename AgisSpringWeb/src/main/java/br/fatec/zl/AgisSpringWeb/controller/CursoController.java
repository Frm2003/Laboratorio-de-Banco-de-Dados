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
import br.fatec.zl.AgisSpringWeb.persistence.CursoDao;

@Controller
public class CursoController {
	
	@Autowired
	private CursoDao cdao;
	
	private List<Curso> cursos = new ArrayList<>();
	
	@RequestMapping(name = "curso", value = "/curso", method = RequestMethod.GET)
	public ModelAndView cursoGet(ModelMap model) {
		if (!cursos.isEmpty()) { cursos.removeAll(cursos); }
		
		try {
			cursos.addAll(cdao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("cursos", cursos);
		
		return new ModelAndView("secretariaCurso");
	}
	
	@RequestMapping(name = "curso", value = "/curso", method = RequestMethod.POST)
	public ModelAndView cursoPost(@RequestParam Map<String, String> param, ModelMap model) {
		String cod = param.get("cod");
		String nome = param.get("nome");
		String cargaHoraria = param.get("cargaHoraria");
		String sigla = param.get("sigla");
		String enade = param.get("enade");
		String turno = param.get("turno");
		
		try {
			switch (param.get("botao")) {
			case "Inserir":
				inserir(nome, Integer.parseInt(cargaHoraria), sigla, Float.parseFloat(enade), turno);
				break;
			case "Buscar":
				if (!cursos.isEmpty()) {
					model.addAttribute("curso", buscar(Integer.parseInt(cod)));
				}
				break;
			case "Atualizar":
				if (!cursos.isEmpty()) {					
					atualizar(Integer.parseInt(cod), nome, Integer.parseInt(cargaHoraria), sigla, Float.parseFloat(enade), turno);
				}
				break;
			case "Deletar":
				if (!cursos.isEmpty()) {					
					deletar(Integer.parseInt(cod));
				} 
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			model.addAttribute("cursos", cursos);
		}
		return new ModelAndView("secretariaCurso");
	}
	
	private void inserir(String nome, int cargaHoraria, String sigla, float enade, String turno) throws ClassNotFoundException, SQLException {
		Curso c = new Curso();
		
		c.setCod(1 + cursos.size());
		c.setNome(nome);
		c.setCargaHoraria(cargaHoraria);
		c.setSigla(sigla);
		c.setEnade(enade);
		c.setTurno(turno);
		
		boolean valido = cdao.iudCrud("i", c);
		
		if (valido) {
			cursos.add(c);	
		}		
	}
	
	private Curso buscar(int cod) throws ClassNotFoundException, SQLException {
		for (Curso c : cursos) {
			if (c.getCod() == cod) {
				return cdao.buscar(c);
			} 
		}
		return null;
	}
	
	private void atualizar(int cod, String nome, int cargaHoraria, String sigla, float enade, String turno) throws ClassNotFoundException, SQLException {
		for (Curso c : cursos) {
			if (c.getCod() == cod) {
				c.setNome(nome);
				c.setCargaHoraria(cargaHoraria);
				c.setSigla(sigla);
				c.setEnade(enade);
				c.setTurno(turno);
				cdao.iudCrud("u", c);
				break;
			} 
		}
	}
	
	private void deletar(int cod) throws ClassNotFoundException, SQLException {
		for (Curso c : cursos) {
			if (c.getCod() == cod) {
				boolean valido = cdao.iudCrud("d", c);
				if (valido) {
					cursos.remove(c);					
				}
				break;
			} 
		}
	}
}