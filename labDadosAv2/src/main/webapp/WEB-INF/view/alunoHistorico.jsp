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
			<li><a href="matricula">Matriculas Disponíveis</a></li>
			<li><a href="historico">Historico</a></li>
		</ul>
	</nav>
	<div class="content s1">
		<form class="alin-se w100p" action="historico" method="post">
			<div>
				<input class="caixaDeTexto m15v" name="ra" value="<c:out value="${ra }" />" type="text" placeholder="Ra"> 
				<div class="alin-se">
					<input class="btSimples" type="submit" name="botao" value="Ver matriculas">
				</div>
			</div>
		</form>
	</div>
	<div>
		<c:forEach var="m" items="${matriculas }">
			<form class="alin-se" action="historico" method="post">
				<div class="alin-se tabelaLinha">
					<input class="caixaDeTextoSemNada px600" name="" id="col" value="<c:out value="${m.disciplina.nome }" />" />
					<input class="caixaDeTextoSemNada px100" name="cod" id="col" value="<c:out value="${m.disciplina.cod }" />" />
					<input class="caixaDeTextoSemNada px100" name="" id="col" value="<c:out value="${m.nota }" />" />
					<input class="caixaDeTextoSemNada px300" name="" id="col" value="<c:out value="${m.situacao }" />" />
					<input class="caixaDeTextoSemNada px100" name="" id="col" value="<c:out value="${m.aprovado }" />" />
					<c:if test="${m.situacao != 'dispensado'}">
						<input class="btSimples" type="submit" name="botao" value="Dispensar">
					</c:if>
					<c:if test="${m.situacao == 'dispensado'}">
						<input class="btSimples" type="submit" name="botao" value="Abdicada">
					</c:if>
				</div>
			</form>
		</c:forEach>
	</div>
</body>
</html>