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
<body>
	<section class="sec1">
		<div class="navigation">
			<label for="r1" class="bar labelTitulo p05" checked>Matricular-se</label> 
			<label for="r2" class="bar labelTitulo p05">Histórico</label> 
		</div>
		<div class="slides">
			<input type="radio" name="r" id="r1"> 
			<input type="radio" name="r" id="r2"> 
			<div class="content s1">		
				<c:forEach var="m" items="${disciplinas }">
					<form class="alin-se" action="matricula" method="post">
						<div class="alin-se tabelaLinha p05">
							<input class="caixaDeTexto" name="ra" id="col" value="<c:out value="${ra }" />" />
							<input class="caixaDeTexto" name="cod" id="col" value="<c:out value="${m.cod }" />" />
							<input class="caixaDeTexto" id="col" value="<c:out value="${m.nome }" />" disabled />  
							<input class="caixaDeTexto" id="col" value="<c:out value="${m.semestre }" />" disabled /> 
							<input class="caixaDeTexto" id="col" value="<c:out value="${m.horario }" />" disabled /> 
							<input class="btSimples" id="col" type="submit" name="botao" value="Matricular">
						</div>
					</form>
				</c:forEach>
			</div>
			<div class="content">
				<div class="alin-se w50p m15v">
					<div class="w100p">
						<div class="tabelaLinha m15v">
							<p class="colTitulo" id="col">Disciplina</p>
							<p class="colTitulo" id="col">Situação</p>
							<p class="colTitulo" id="col">Aprovado</p>
						</div>
						<c:forEach var="m" items="${matriculas }">
							<div class="tabelaLinha p05">
								<p id="col"><c:out value="${m.disciplina.nome }" /></p>
								<p id="col"><c:out value="${a.situacao }" /></p>
								<p id="col"><c:out value="${a.aprovado }" /></p>
							</div>
						</c:forEach>	
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>