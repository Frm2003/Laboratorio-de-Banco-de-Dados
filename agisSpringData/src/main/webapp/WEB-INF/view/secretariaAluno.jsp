<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href='<c:url value="./resources/estilo/nav.css"/>'>
	<link rel="stylesheet" href='<c:url value="./resources/estilo/slide.css"/>'>
	<link rel="stylesheet" href='<c:url value="./resources/estilo/style.css"/>'>
	<script src='<c:url value="./resources/scripts/script.js"/>'></script>
	<title>Insert title here</title>
</head>
<body onload="load()">
	<nav>
		<ul class="alin-se m15v">
			<li><a href="index">HOME</a></li>
			<li><a href="aluno">Matricular Aluno</a></li>
			<li><a href="curso">Inserir Curso</a></li>
			<li><a href="materia">Inserir Materia</a></li>
			<li><a href="disciplina">Inserir Disciplina</a></li>
			<li><a href="historicoAluno">Historico Aluno</a></li>
		</ul>
	</nav>
	<section class="sec1">
		<div class="navigation">
			<label for="r1" class="bar labelTitulo p05" checked>Formulário</label> 
			<label for="r2" class="bar labelTitulo p05">Lista Completa</label> 
		</div>
		<div class="slides">
			<input type="radio" name="r" id="r1"> 
			<input type="radio" name="r" id="r2"> 
			<div class="content s1">
				<form class="alin-se w100p" action="aluno" method="post">
					<div>
						<div>
							<div class="campo em15v alin-se">
								<input class="caixaDeTexto m15v" value='<c:out value="${aluno.ra }" />' name="ra" type="text" placeholder="Ra"> 
								<input class="btSimples" type="submit" name="botao" value="Buscar">
							</div>
							<p>Inserção de dados</p>
							<div class="tabelaLinha">
								<div>
									<input class="caixaDeTexto m15v" value='<c:out value="${aluno.nome }" />' name="nome" type="text" placeholder="Nome">
									<input class="caixaDeTexto m15v" value='<c:out value="${aluno.dataNasc }" />' name="dataNasc" type="date" placeholder="Data de Nascimento">  
									<input class="caixaDeTexto m15v" value='<c:out value="${aluno.nomeSocial }" />' name="nomeSocial" type="text" placeholder="Nome Social"> 
									<input class="caixaDeTexto m15v" value='<c:out value="${aluno.emailPessoal }" />' name="emailPessoal" type="text" placeholder="Email Pessoal">
									<input class="caixaDeTexto m15v" value='<c:out value="${aluno.dataConc2grau }" />' name="dataConc2grau" type="date" placeholder="Data de conclusão do 2° grau">
								</div>
								<div>
									<input class="caixaDeTexto m15v" value='<c:out value="${aluno.cpf }" />' name="cpf" type="text" placeholder="CPF"> 
									<input class="caixaDeTexto m15v" value='<c:out value="${aluno.instConc2grau }" />' name="instConc2grau" type="text" placeholder="Instituição de conclusão do 2° grau">
									<input class="caixaDeTexto m15v" value='<c:out value="${aluno.ptVestibular }" />' name="ptVestibular" type="number" placeholder="Pontuação do vestibular">
									<input class="caixaDeTexto m15v" value='<c:out value="${aluno.posVestibular }" />' name="posVestibular" type="number" placeholder="Posição do vestibular">
									<select name="curso" class="caixaDeTexto espacoMargin m15v">
										<option>Selecione um curso</option>
										<c:forEach var="c" items="${cursos }">
											<option value="${c.cod }">
												<c:out value="${c.nome } - ${c.turno }" />
											</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="alin-se">
								<input class="btSimples" type="submit" name="botao" value="Inserir">
								<input class="btSimples" type="submit" name="botao" value="Atualizar">
								<input class="btSimples" type="submit" name="botao" value="Deletar">
								<input class="btSimples" type="submit" name="botao" value="Listar">
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="content">
				<div class="alin-se w50p m15v">
					<div class="w100p">
						<div class="tabelaLinha m15v">
							<p class="colTitulo" id="col">Ra</p>
							<p class="colTitulo" id="col">Cpf</p>
							<p class="colTitulo" id="col">Nome</p>
							<p class="colTitulo" id="col">DataNasc</p>
							<p class="colTitulo" id="col">Instituição 2° grau</p>
							<p class="colTitulo" id="col">Data Matricula</p>
							<p class="colTitulo" id="col">Data Limite</p>
							<p class="colTitulo" id="col">Curso</p>
						</div>
						<c:forEach var="a" items="${alunos }">
							<div class="tabelaLinha p05">
								<p id="col"><c:out value="${a.ra }" /></p>
								<p id="col"><c:out value="${a.cpf }" /></p>
								<p id="col"><c:out value="${a.nome }" /></p>
								<p id="col"><c:out value="${a.dataNasc }" /></p>
								<p id="col"><c:out value="${a.instConc2grau }" /></p>
								<p id="col"><c:out value="${a.dataMatricula }" /></p>
								<p id="col"><c:out value="${a.dataLimiteMatricula }" /></p>
								<p id="col"><c:out value="${a.curso.sigla }" /></p>
							</div>
						</c:forEach>	
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>