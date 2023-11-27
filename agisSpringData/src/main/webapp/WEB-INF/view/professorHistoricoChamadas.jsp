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
			<li><a href="geraChamada">Realizar Chamada</a></li>
			<li><a href="professorInseriNota">Inserir Nota</a></li>
			<li><a href="consultaChamada">Atualizar Chamada</a></li>
			<li><a href="professorHistoricoNotas">Historico Notas</a></li>
			<li><a href="professorHistoricoChamadas">Historico Chamadas</a></li>
		</ul>
	</nav>
	<div class="content s1">
		<form class="alin-se w100p" action="professorHistoricoChamadas" method="post">
			<div>
				<select name="codMateria" class="caixaDeTexto espacoMargin m15v">
					<option>Selecione uma mateira</option>
					<c:forEach var="m" items="${materias }">
						<option value="${m.cod }">
							<c:out value="${m.disciplina.nome } - ${m.disciplina.curso.turno }" />
						</option>
					</c:forEach>
				</select>
				<div class="alin-se">
					<input class="btSimples" type="submit" name="botao" value="Buscar">
				</div>
			</div>
		</form>
	</div>
	<h2 class="alin-se w100p">Historico</h2>
	<div class="alin-se w100p m15v">
		<div class="w50p">
			<div class="tabelaLinha m15v">
				<p class="colTitulo" id="col">Cod. Turma.</p>
				<p class="colTitulo" id="col">Nome Disic.</p>
				<p class="colTitulo" id="col">Nome Prof.</p>
				<p class="colTitulo" id="col">Tipo Av.</p>
				<p class="colTitulo" id="col">Nota Final</p>
				<p class="colTitulo" id="col">Total faltas</p>
				<p class="colTitulo" id="col">Porcentagem</p>
				<p class="colTitulo" id="col">Situacão</p>
			</div>
			<c:forEach var="c" items="${historico }">
				<div class="tabelaLinha p05">
					<p id="col"><c:out value="${c.codMateria }" /></p>
					<p id="col"><c:out value="${c.nomeDisciplina }" /></p>
					<p id="col"><c:out value="${c.professor }" /></p>
					<p id="col"><c:out value="${c.tipoAvalicao }" /></p>
					<p id="col"><c:out value="${c.notaFinal }" /></p>
					<p id="col"><c:out value="${c.totalFaltas }" /></p>
					<p id="col"><c:out value="${c.porcentagem }%" /></p>
					<p id="col"><c:out value="${c.situacao }" /></p>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>