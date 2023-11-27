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
			<li><a href="geraChamada">Realizar Chamada</a></li>
			<li><a href="professorInseriNota">Inserir Nota</a></li>
			<li><a href="consultaChamada">Atualizar Chamada</a></li>
			<li><a href="professorHistoricoNotas">Historico Notas</a></li>
			<li><a href="professorHistoricoChamadas">Historico Chamadas</a></li>
		</ul>
	</nav>
	<div class="content s1">
		<form class="alin-se w100p" action="consultaChamada" method="post">
			<div>
				<select name="codTurma" class="caixaDeTexto espacoMargin m15v">
					<option>Selecione uma turma</option>
					<c:forEach var="c" items="${turmas }">
						<option value="${c.cod }">
							<c:out value="${c.disciplina.nome }" />
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
		<div class="alin-se w100p m15v">
			<c:if test="${not empty chamadas }">
				<div class="w50p">
					<div class="tabelaLinha m15v">
						<p style="font-size: 21px" class="colTitulo" id="col"><b>Aluno Ra</b></p>
						<p style="font-size: 21px" class="colTitulo" id="col"><b>Nome Aluno</b></p>
						<p style="font-size: 21px" class="colTitulo" id="col"><b>Materia</b></p>
						<p style="font-size: 21px" class="colTitulo" id="col"><b>Qtd. Faltas</b></p>
						<p style="font-size: 21px" class="colTitulo" id="col"><b>D. Chamada</b></p>
						<p class="colTitulo" id="col"></p>
					</div>
					<c:forEach var="c" items="${chamadas }">
						<div class="tabelaLinha p05">
							<form action="consultaChamada" method="post">
								<input type="hidden" name="ra" value="<c:out value="${c.aluno.ra }" />" />
								<input type="hidden" name="codMateria" value="<c:out value="${c.materia.cod }" />" />
								<input type="hidden" name="data" value="<c:out value="${c.data }" />" />
								<div class="alin-se tabelaLinha">
									<p id="col"><c:out value="${c.aluno.ra }"/></p>
									<p id="col"><c:out value="${c.aluno.nome }"/></p>
									<p id="col"><c:out value="${c.materia.disciplina.nome }"/></p>
									<select id="col" name="qtdFalta" class="caixaDeTexto px100">
										<option><c:out value="${c.faltas }" /></option>
										<option value="0">0</option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
									</select>
									<p id="col"><c:out value="${c.data }" /></p>
									<input class="btSimples" id="col" type="submit" name="botao" value="Atualizar">
								</div>
							</form>
						</div>
					</c:forEach>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>