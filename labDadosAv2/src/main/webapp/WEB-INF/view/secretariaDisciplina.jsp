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
			<li><a href="curso">Matricular Curso</a></li>
			<li><a href="disciplina">Matricular Disciplina</a></li>
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
				<form class="alin-se" action="disciplina" method="post">
					<div>
						<div>
							<div class="campo em15v alin-se">
								<input class="caixaDeTexto m15v" value='<c:out value="${disciplina.cod }" />' name="cod" type="number" placeholder="cod"> 
								<input class="btSimples" type="submit" name="botao" value="Buscar">
							</div>
							<p>Inserção de dados</p>
							<input class="caixaDeTexto m15v" value='<c:out value="${disciplina.nome }" />' type="text" name="nome" placeholder="Nome"> 
							<input class="caixaDeTexto m15v" value='<c:out value="${disciplina.qtdAulas }" />' type="number" name="qtdAulas" placeholder="Carga Horaria">
							<input class="caixaDeTexto m15v" value='<c:out value="${disciplina.semestre }" />' type="number" name="semestre" placeholder="Semestre">
							<select name="horario" class="caixaDeTexto m15v">
								<c:if test="${empty disciplina }">
									<option>Selecione o horario</option>
								</c:if>
								<c:if test="${not empty disciplina }">
									<option>
										<c:out value="${disciplina.horario }"></c:out>
									</option>
								</c:if>
								<option value="13:00 a 16:30">13:00 a 16:30</option>
								<option value="13:00 a 14:40">13:00 a 14:40</option>
								<option value="14:50 a 18:20">14:50 a 18:20</option>
								<option value="14:50 a 16:30">14:50 a 16:30</option>
								<option value="16:30 a 18:20">16:30 a 18:20</option>
							</select>
							<div class="alin-se">
								<select name="curso" class="caixaDeTexto">
									<c:if test="${not empty disciplina }">
										<option value="${disciplina.curso.cod }">
											<c:out value="${disciplina.curso.nome }"></c:out>
										</option>
									</c:if>
									<c:if test="${empty disciplina.curso.nome }">
										<option>Selecione o curso</option>
									</c:if>
									<c:forEach var="c" items="${cursos }">
										<option value="${c.cod }">
											<c:out value="${c.nome }" />
										</option>
									</c:forEach>
								</select> 
							</div>
							<div class="alin-se m15v">
								<select name="professor" class="caixaDeTexto">
									
									<c:if test="${not empty disciplina.professor.nome }">
										<option value="${disciplina.professor.cod }">
											<c:out value="${disciplina.professor.nome }"></c:out>
										</option>
									</c:if>
									<c:if test="${empty disciplina.professor.nome }">
										<option>Selecione o professor</option>
									</c:if>
									<c:forEach var="c" items="${professores }">
										<option value="${c.cod }">
											<c:out value="${c.nome }" />
										</option>
									</c:forEach>
								</select> 
							</div>
						</div>
						<div class="alin-se m15v">
							<input class="btSimples" type="submit" name="botao" value="Inserir">
							<input class="btSimples" type="submit" name="botao" value="Atualizar">
							<input class="btSimples" type="submit" name="botao" value="Deletar">
						</div>
					</div>
				</form>
			</div>
			<div class="content">
				<div class="alin-se w50p m15v">
					<div class="w50p">
						<div class="tabelaLinha m15v">
							<p class="colTitulo" id="col">Cod</p>
							<p class="colTitulo" id="col">Nome</p>
							<p class="colTitulo" id="col">Qtd. aula</p>
							<p class="colTitulo" id="col">Horário</p>
							<p class="colTitulo" id="col">Curso</p>
							<p class="colTitulo" id="col">Professor</p>
							
						</div>
						<c:forEach var="d" items="${disciplinas }">
							<div class="tabelaLinha p05">
								<p id="col"><c:out value="${d.cod }" /></p>
								<p id="col"><c:out value="${d.nome }" /></p>
								<p id="col"><c:out value="${d.qtdAulas }" /></p>
								<p id="col"><c:out value="${d.horario }" /></p>
								<p id="col"><c:out value="${d.curso.sigla }" /></p>
								<p id="col"><c:out value="${d.professor.nome }" /></p>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>