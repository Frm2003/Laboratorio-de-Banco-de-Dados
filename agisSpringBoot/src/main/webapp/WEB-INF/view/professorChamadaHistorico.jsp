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
</head>
<body onload="load()">
	<nav>
		<ul class="alin-se m15v">
			<li><a href="index">HOME</a></li>
			<li><a href="chamada">Realizar Chamada</a></li>
			<li><a href="chamadaHistorico">Historico Chamada</a></li>
		</ul>
	</nav>
	<div class="content s1">
		<form class="alin-se w100p" action="chamadaHistorico" method="post">
			<div>
				<select name="codDisci" class="caixaDeTexto espacoMargin m15v">
					<option>Selecione um curso</option>
					<c:forEach var="c" items="${disciplinas }">
						<option value="${c.cod }">
							<c:out value="${c.nome }" />
						</option>
					</c:forEach>
				</select>
				<input class="caixaDeTexto espacoMargin m15v" name="data" type="date">
				<div class="alin-se">
					<input class="btSimples" type="submit" name="botao" value="Buscar">
				</div>
			</div>
		</form>
	</div>
	<div>
		<h2>Atualizar chamada</h2>
		<c:forEach var="c" items="${chamadas }">
			<div class="tabelaLinha p05">
				<form class="alin-se" action="chamada" method="post">
					<div class="alin-se tabelaLinha">
						<input class="caixaDeTextoSemNada" name="ra" id="col" value="<c:out value="${c.aluno.ra }"  />" />
						<input class="caixaDeTextoSemNada" name="cod" id="col" value="<c:out value="${c.disciplina.cod }" />" />
						<select name="qtdFalta" class="caixaDeTexto px100">
							<option value="c.faltas "><c:out value="${c.faltas }" /></option>
							<option value="0">0</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
						</select>
						<input class="caixaDeTextoSemNada" name="dataChamada" id="col" value="<c:out value="${c.data }" />" />
						<input class="btSimples" type="submit" name="botao" value="Atualizar Chamada">
					</div>
				</form>
			</div>
		</c:forEach>
	</div>
</body>
</html>