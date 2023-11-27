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
		<form class="alin-se w100p" action="matricula" method="post">
			<div>
				<input class="caixaDeTexto m15v" name="ra" value="<c:out value="${ra }" />" type="text" placeholder="Ra"> 
				<div class="alin-se">
					<input class="btSimples" type="submit" name="botao" value="Ver disciplinas">
				</div>
			</div>
		</form>
	</div>
	<c:if test="${not empty disciplinas }">
		<div>
		<div class="alin-se tabelaLinha m15v">
			<input class="caixaDeTextoSemNada colTitulo" id="col" value="<c:out value="Código Disci." />" />
			<input class="caixaDeTextoSemNada colTitulo" id="col" value="<c:out value="Nome Disci." />" disabled />  
			<input class="caixaDeTextoSemNada colTitulo" id="col" value="<c:out value="Semestre" />" disabled /> 
			<input class="caixaDeTextoSemNada colTitulo" id="col" value="<c:out value="Horário" />" disabled /> 
			<input class="caixaDeTextoSemNada colTitulo" id="col" value="<c:out value="Ação" />" disabled />
		</div>
		<c:forEach var="m" items="${disciplinas }">
			<form action="matricula" method="post">
				<div class="alin-se tabelaLinha p05">
					<input class="caixaDeTextoSemNada" name="cod" id="col" value="<c:out value="${m.cod }" />" />
					<input class="caixaDeTextoSemNada" id="col" value="<c:out value="${m.nome }" />" disabled />  
					<input class="caixaDeTextoSemNada" id="col" value="<c:out value="${m.semestre }" />" disabled /> 
					<input class="caixaDeTextoSemNada" id="col" value="<c:out value="${m.horario }" />" disabled /> 
					<input class="btSimples" id="col" type="submit" name="botao" value="Matricular">
				</div>
			</form>
		</c:forEach>
	</div>
	</c:if>
</body>
</html>