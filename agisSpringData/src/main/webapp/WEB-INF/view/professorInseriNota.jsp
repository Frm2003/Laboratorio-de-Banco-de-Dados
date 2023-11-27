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
			<li><a href="consultaChamada">Historico Chamada</a></li>
			<li><a href="professorInseriNota">Inserir Nota</a></li>
		</ul>
	</nav>
	<div class="content s1">
		<form class="alin-se w100p" action="professorInseriNota" method="post">
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
					<input class="btSimples" type="submit" name="botao" value="Buscar matriculas">
				</div>
			</div>
		</form>
	</div>
	<div>
		<div class="alin-se w100p m15v">
			<div class="w50p">
				<div class="tabelaLinha m15v">
					<p style="font-size: 21px" class="colTitulo" id="col"><b>Aluno Ra</b></p>
					<p style="font-size: 21px" class="colTitulo" id="col"><b>Tipo Av.</b></p>
					<p style="font-size: 21px" class="colTitulo" id="col"><b>n1</b></p>
					<p style="font-size: 21px" class="colTitulo" id="col"><b>n2</b></p>
					<c:if test="${tipoAvalicao != 'Tipo4'}">
						<p style="font-size: 21px" class="colTitulo" id="col"><b>n3</b></p>
					</c:if>
					<p class="colTitulo" id="col"></p>
				</div>
				<c:forEach var="n" items="${notas }">
					<div class="tabelaLinha p05">
						<form action="professorInseriNota" method="post">
							<input type="hidden" name="codNota" value="${n.cod }">
							<input type="hidden" name="tipoAvalicao" value="${n.matricula.materia.tipoAvalicao }">
							
							<p id="col"><c:out value="${n.matricula.aluno.ra }"></c:out></p>
							<p id="col"><c:out value="${n.matricula.materia.tipoAvalicao }"></c:out></p>

							<div>
								<c:if test="${n.matricula.materia.tipoAvalicao != 'Tipo4'}">
									<input id="col" type="number" name="p1" min="0" max="10" value="<c:out value="${n.n1 }" />" placeholder="p1">
									<input id="col" type="number" name="p2" min="0" max="10" value="<c:out value="${n.n2 }" />" placeholder="p2">
									<input id="col" type="number" name="t" min="0" max="10" value="<c:out value="${n.n3 }" />"  placeholder="t">
								</c:if>
								
								
								<c:if test="${n.matricula.materia.tipoAvalicao == 'Tipo4'}">
									<input id="col" type="number" name="p1" min="0" max="10" value="<c:out value="${n.n1 }" />"  placeholder="Artigo">
									<input id="col" type="number" name="p2" min="0" max="10" value="<c:out value="${n.n2 }" />"  placeholder="Monografia">
									<input id="col" type="hidden" name="t" value ="0">
								</c:if>

								<input id="col" class="btSimples" type="submit" name="botao" value="Aplicar Nota">
							</div>
						
						</form>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>