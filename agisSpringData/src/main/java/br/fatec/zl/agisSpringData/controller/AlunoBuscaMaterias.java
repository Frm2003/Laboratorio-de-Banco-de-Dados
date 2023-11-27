package br.fatec.zl.agisSpringData.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import br.fatec.zl.agisSpringData.model.Materia;
import br.fatec.zl.agisSpringData.model.Matricula;
import br.fatec.zl.agisSpringData.model.Nota;
import br.fatec.zl.agisSpringData.services.AlunoService;
import br.fatec.zl.agisSpringData.services.MateriaService;
import br.fatec.zl.agisSpringData.services.MatriculaService;
import br.fatec.zl.agisSpringData.services.NotasService;

@Controller
@RequestMapping("/alunoBuscaMaterias")
public class AlunoBuscaMaterias {
	@Autowired
	private AlunoService aserv;
	
	@Autowired
	private MateriaService materiaServ;
	
	@Autowired
	private MatriculaService matriculaServ;	
	
	@Autowired
	private NotasService nserv;
	
	private List<Aluno> alunos = new ArrayList<>();
	private List<Materia> materias = new ArrayList<>();
	
	private List<Nota> notas = new ArrayList<>();
	
	@GetMapping
	public String get() {
		alunos = aserv.buscarTudo();
		
		return "alunoBuscaMaterias";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		if (!materias.isEmpty()) { materias.removeAll(materias); }
		
		String ra = param.get("ra");
		String codMateria = param.get("codMateria");
		
		switch (param.get("botao")) {
		case "Pesquisar":
			
			materias = matriculasDisponiveis(ra);
			notas = listarNotasAluno(ra);
			
			notas.forEach(n -> {
				n.setNotaFinal(nserv.calculaNotas(n.getMatricula().getMateria().getTipoAvalicao(), n.getN1(), n.getN2(), n.getN3()));
			});
			
			break;
		case "Matricular":
			matricular(ra, Long.parseLong(codMateria));
			break;
		case "Dispensar":
			dispensar(ra, Long.parseLong(codMateria));
			break;
		}
		
		model.addAttribute("materias", materias);
		model.addAttribute("notas", listarNotasAluno(ra));
		
		return "alunoBuscaMaterias";
	}

	private void matricular(String ra, Long codMateria) {
		Matricula m = new Matricula();
		
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				m.setAluno(a);
			}
		}
		for (Materia mat : materiaServ.buscarTudo()) {
			if (mat.getCod() == codMateria) {
				m.setMateria(mat);
			}
		}
		m.setAno(Calendar.YEAR);
		m.setSemestre(matriculaServ.getSemestre());
		m.setSituacao("cursando");
		m.setAprovado(false);
		
		Nota n = new Nota();
		
		n.setMatricula(m);
		n.setN1(0);
		n.setN2(0);
		n.setN3(0);
		
		matriculaServ.inserir(m);
		nserv.inserir(n);
	}
	
	private void dispensar(String ra, Long codMateria) {
		for (Matricula m : matriculaServ.buscarTudo()) {
			if (m.getAluno().getRa().equals(ra) && m.getMateria().getCod() == codMateria) {
				m.setSituacao("dispensado");
				matriculaServ.atualizar(m);
			}
		}
	}
	
	private List<Materia> matriculasDisponiveis(String ra) {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				return materiaServ.listaMatriculasDisponiveis(a);
			}
		}
		return null;
	}
	
	private List<Nota> listarNotasAluno(String ra) {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				return nserv.listarNotasAluno(a);
			}
		}
		return null;
	}
}