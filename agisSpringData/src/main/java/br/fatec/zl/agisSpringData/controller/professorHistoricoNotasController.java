package br.fatec.zl.agisSpringData.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.fatec.zl.agisSpringData.model.Materia;
import br.fatec.zl.agisSpringData.model.dto.HistoricoTurmaDto;
import br.fatec.zl.agisSpringData.persistence.GenericDao;
import br.fatec.zl.agisSpringData.services.MateriaService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
@RequestMapping("professorHistoricoNotas")
public class professorHistoricoNotasController {	
	@Autowired
	private MateriaService mserv;
	
	private List<Materia> materias = new ArrayList<>();
	
	@GetMapping
	public String get(ModelMap model) {
		if (!materias.isEmpty()) { materias.removeAll(materias); }
		
		materias.addAll(mserv.buscarTudo());
		model.addAttribute("materias", materias);
		
		return "professorHistoricoNotas";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		String cod = param.get("codMateria");
		
		switch (param.get("botao")) {
		case "Buscar":
			model.addAttribute("historico", listaHistoricoNotasMateria(Long.parseLong(cod)));
			break;
		}
		
		model.addAttribute("materias", materias);
		
		return "professorHistoricoNotas";
	}
	
	private List<HistoricoTurmaDto> listaHistoricoNotasMateria (Long cod) {
		for (Materia m : materias) {
			if (m.getCod() == cod) {
				return mserv.listaHistoricoNotasMateria(m);
			}
		}
		return null;
	}
	
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	private ResponseEntity gerarRelatorio(@RequestParam Map<String, String> param) {
		GenericDao gdao = new GenericDao();
		String erro = "";
		
		System.out.println(param.get("codMateria"));
		Map<String, Object> paramRelatorio = new HashMap<>();
        paramRelatorio.put("codMateria", param.get("codMateria"));
		
		byte[] bytes = null;
		
		InputStreamResource resource = null;
		HttpStatus status = null;
		HttpHeaders headers = new HttpHeaders();
		
		try {
			Connection c = gdao.conexao();
			File arq = ResourceUtils.getFile("classpath:reports/notasTurma.jasper");
			System.out.println(arq.getPath());
			JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(arq.getPath());
			bytes = JasperRunManager.runReportToPdf(report, paramRelatorio, c);
		} catch (ClassNotFoundException | SQLException | FileNotFoundException | JRException e) {
			e.printStackTrace();
			erro = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} finally {
			if (erro.equals("")) {
				ByteArrayInputStream is = new ByteArrayInputStream(bytes);
				resource = new InputStreamResource(is);
				headers.setContentLength(bytes.length);
				headers.setContentType(MediaType.APPLICATION_PDF);
				status = HttpStatus.OK;
			}
		}
		
		return new ResponseEntity(resource, headers, status);
	}
}
