<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<div class="content s1">
		<form class="alin-se w100p" action="historicoAluno" method="post">
			<div>
				<input class="caixaDeTexto m15v" name="ra" value="<c:out value="${ra }" />" type="text" placeholder="Ra"> 
				<div class="alin-se">
					<input class="btSimples" type="submit" name="botao" value="Buscar">
				</div>
			</div>
		</form>
	</div>
	<h2 class="alin-se w100p">Cabeçalho</h2>
	<div class="alin-se w100p m15v">
		<div class="w50p">
			<div class="tabelaLinha m15v">
				<p class="colTitulo" id="col">Ra</p>
				<p class="colTitulo" id="col">Nome</p>
				<p class="colTitulo" id="col">Curso</p>
				<p class="colTitulo" id="col">ptVestibular</p>
				<p class="colTitulo" id="col">posVestibular</p>
				<p class="colTitulo" id="col">Data Matricula</p>
			</div>
			<div class="tabelaLinha p05">
				<p id="col"><c:out value="${aluno.ra }" /></p>
				<p id="col"><c:out value="${aluno.nome }" /></p>
				<p id="col"><c:out value="${aluno.curso.nome }" /></p>
				<p id="col"><c:out value="${aluno.ptVestibular }" /></p>
				<p id="col"><c:out value="${aluno.posVestibular }" /></p>
				<p id="col"><c:out value="${aluno.dataMatricula }" /></p>
			</div>	
		</div>
	</div>
	<h2 class="alin-se w100p">Historico</h2>
	<div class="alin-se w100p m15v">
		<div class="w50p">
			<div class="tabelaLinha m15v">
				<p class="colTitulo3" id="col3">Cod. Turma.</p>
				<p class="colTitulo3" id="col3">Nome Disic.</p>
				<p class="colTitulo3" id="col3">Nome Prof.</p>
				<p class="colTitulo3" id="col3">Nota Final</p>
				<p class="colTitulo3" id="col3">Qtd. Faltas</p>
			</div>
			<c:forEach var="c" items="${historico }">
				<div class="tabelaLinha p05">
					<p id="col3"><c:out value="${c.codDisci }" /></p>
					<p id="col3"><c:out value="${c.nomeDisci }" /></p>
					<p id="col3"><c:out value="${c.nomeProf }" /></p>
					<p id="col3"><c:out value="${c.notaFinal }" /></p>
					<p id="col3"><c:out value="${c.qtdFalta }" /></p>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>