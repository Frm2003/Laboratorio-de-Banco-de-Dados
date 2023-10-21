package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Aluno;
import model.Curso;
import persistence.AlunoDao;
import persistence.CursoDao;

public class AlunoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<Aluno> alunos = new ArrayList<>();
	private List<Curso> cursos = new ArrayList<>();
       
    public AlunoServlet() throws ClassNotFoundException, SQLException {
    	AlunoDao adao = new AlunoDao();
    	alunos.addAll(adao.listar());
    	CursoDao cdao = new CursoDao();
    	cursos.addAll(cdao.listar());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("alunos", alunos);
		request.setAttribute("cursos", cursos);
		RequestDispatcher rd = request.getRequestDispatcher("secretariaAluno.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ra = request.getParameter("ra");
		String nome = request.getParameter("nome");
		String nomeSocial = request.getParameter("nomeSocial");
		String dataNasc = request.getParameter("dataNasc");
		String emailPessoal = request.getParameter("emailPessoal");
		String dataConc2grau = request.getParameter("dataConc2grau");
		String cpf = request.getParameter("cpf");
		String isntConc2grau = request.getParameter("isntConc2grau");
		String ptVestibular = request.getParameter("ptVestibular");
		String posVestibular = request.getParameter("posVestibular");
		String curso = request.getParameter("curso");
		
		try {
			switch (request.getParameter("botao")) {
			case "Inserir":
				criar(nome, nomeSocial, emailPessoal, dataNasc, dataConc2grau, isntConc2grau, cpf, Integer.parseInt(ptVestibular), Integer.parseInt(posVestibular), Integer.parseInt(curso));
				break;
			case "Buscar":
				if (!alunos.isEmpty()) {
					request.setAttribute("aluno", buscar(ra));					
				}
				break;
			case "Atualizar":
				if (!alunos.isEmpty()) {
					atualizar(ra, nome, nomeSocial, emailPessoal, dataNasc, dataConc2grau, isntConc2grau, cpf, Integer.parseInt(ptVestibular), Integer.parseInt(posVestibular), Integer.parseInt(curso));					
				}
				break;
			case "Deletar":
				if (!alunos.isEmpty()) {					
					deletar(ra);
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.setAttribute("alunos", alunos);			
			request.setAttribute("cursos", cursos);
			RequestDispatcher rd = request.getRequestDispatcher("secretariaAluno.jsp");
			rd.forward(request, response);
		}
	}
	
	private void criar(String nome, String nomeSocial, String emailPessoal, String dataNasc, String dataConc2grau, String isntConc2grau, String cpf, int ptVestibular, int posVestibular, int curso) throws ClassNotFoundException, SQLException {
		Aluno a = new Aluno();
		AlunoDao adao = new AlunoDao();
		
		a.setRa(adao.geraRa());
		a.setCpf(cpf);
		a.setNome(nome);
		a.setNomeSocial(nomeSocial);
		a.setEmailPessoal(emailPessoal);
		a.setEmailCorporativo(geraEmailCorporativo(nome));
		a.setDataNasc(dataNasc);
		a.setDataConc2grau(dataConc2grau);
		a.setIsntConc2grau(isntConc2grau);
		a.setPtVestibular(ptVestibular);
		a.setPosVestibular(posVestibular);
		
		for (Curso c : cursos) {
			if (c.getCod() == curso) {
				a.setCurso(c);
			}
		}
		
		boolean valido = adao.iudCrud("i", a);
		
		if (valido) {
			alunos.add(a);			
		}
	}

	private Aluno buscar(String ra) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				AlunoDao adao = new AlunoDao();
				return adao.buscar(a);
			}		
		}
		return null;
	}

	private void atualizar(String ra, String nome, String nomeSocial, String emailPessoal, String dataNasc, String dataConc2grau, String isntConc2grau, String cpf, int ptVestibular, int posVestibular, int curso) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				AlunoDao adao = new AlunoDao();
				boolean valido = adao.iudCrud("u", a);
				if (valido) { 
					a.setRa(AlunoDao.geraRa());
					a.setCpf(cpf);
					a.setNome(nome);
					a.setNomeSocial(nomeSocial);
					a.setEmailPessoal(emailPessoal);
					a.setEmailCorporativo(geraEmailCorporativo(nome));
					a.setDataNasc(dataNasc);
					a.setDataConc2grau(dataConc2grau);
					a.setIsntConc2grau(isntConc2grau);
					a.setPtVestibular(ptVestibular);
					a.setPosVestibular(posVestibular);
					
					for (Curso c : cursos) {
						if (c.getCod() == curso) {
							a.setCurso(c);
						}
					}
				}
				break;
			}		
		}
		
	}

	private void deletar(String ra) throws ClassNotFoundException, SQLException {
		for (Aluno a : alunos) {
			if (a.getRa().equals(ra)) {
				AlunoDao adao = new AlunoDao();
				boolean valido = adao.iudCrud("u", a);
				if (valido) { 
					alunos.remove(a);				
				}
				break;
			}		
		}
	}
	
	private String geraEmailCorporativo(String nome) {
		String[] vetNomes = nome.split(" ");
		
		String emailCorp = vetNomes[0] + "." + vetNomes[vetNomes.length - 1] + "@email.com";
		
		return emailCorp;
	}

}
