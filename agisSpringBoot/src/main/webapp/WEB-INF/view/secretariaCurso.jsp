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
				<form class="alin-se" action="curso" method="post">
					<div>
						<div>
							<div class="campo em15v alin-se">
								<input class="caixaDeTexto m15v" value='<c:out value="${curso.cod }" />' name="cod" type="number" placeholder="cod"> 
								<input class="btSimples" type="submit" name="botao" value="Buscar">
							</div>
							<p>Inserção de dados</p>
							<input class="caixaDeTexto m15v" value='<c:out value="${curso.nome }" />' type="text" name="nome" placeholder="Nome"> 
							<input class="caixaDeTexto m15v" value='<c:out value="${curso.cargaHoraria }" />' type="number" name="cargaHoraria" placeholder="Carga Horaria">
							<input class="caixaDeTexto m15v" value='<c:out value="${curso.sigla }" />' type="text" name="sigla" placeholder="Sigla">
							<input class="caixaDeTexto m15v" value='<c:out value="${curso.enade }" />' type="number" name="enade" placeholder="Nota do Enade" min="0" max="5" step="0.1">
							<input class="caixaDeTexto m15v" value='<c:out value="${curso.turno }" />' type="text" name="turno" placeholder="Turno">
						</div>
						<div class="alin-se">
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
							<p class="colTitulo" id="col">cargaHoraria</p>
							<p class="colTitulo" id="col">sigla</p>
							<p class="colTitulo" id="col">enade</p>
							<p class="colTitulo" id="col">turno</p>
						</div>
						<c:forEach var="c" items="${cursos }">
							<div class="tabelaLinha p05">
								<p id="col"><c:out value="${c.cod }" /></p>
								<p id="col"><c:out value="${c.nome }" /></p>
								<p id="col"><c:out value="${c.cargaHoraria }" /></p>
								<p id="col"><c:out value="${c.sigla }" /></p>
								<p id="col"><c:out value="${c.enade }" /></p>
								<p id="col"><c:out value="${c.turno }" /></p>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>