<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="css/slide.css">
	<script src="js/script.js"></script>
	<title>Insert title here</title>
</head>
<body onload="load()">
	<!-- 
	<div>
		<jsp:include page="menu.jsp" />
	</div>
	 -->
	<section class="sec1">
		<div class="navigation">
			<label for="r1" class="bar labelTitulo">Formul�rio</label> 
			<label for="r2" class="bar labelTitulo">Lista Completa</label> 
		</div>
		<div class="slides">
			<input type="radio" name="r" id="r1"> 
			<input type="radio" name="r" id="r2"> 
			<div class="content s1">
				<form class="alinhamento" action="professor" method="post">
					<div>
						<div>
							<div class="campo espacoMargin alinhamento">
								<input class="caixaDeTexto" type="number" value='<c:out value="${professor.cod }" />' name="cod" min="0" step="1" placeholder="C�digo" required="required"> 
								<input class="btSimples" type="submit" name="botao" value="Buscar">
							</div>
							<input class="caixaDeTexto espacoMargin" value='<c:out value="${professor.nome }" />' type="text" name="nome" placeholder="Nome"> 
							<input class="caixaDeTexto espacoMargin" value='<c:out value="${professor.data }" />' type="date" name="dataNasc">
						</div>
						<div class="alinhamento">
							<input class="btSimples" type="submit" name="botao" value="Criar">
							<input class="btSimples" type="submit" name="botao" value="Alterar">
							<input class="btSimples" type="submit" name="botao" value="Remover">
							<input class="btSimples" type="submit" name="botao" value="Listar">
						</div>
					</div>
				</form>
				<c:if test="${not empty erro }">
					<h2><c:out value="${erro }" /></h2>
				</c:if>
				<c:if test="${not empty saida }">
					<h2><c:out value="${saida }" /></h2>
				</c:if>
			</div>
			<div class="content">
				<c:if test="${not empty professores }">
						<table border="1">
							<thead>
								<tr>
									<th><b>ID</b></th>
									<th><b>Nome</b></th>
									<th><b>Data Nasc.</b></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="p" items="${professores }">
								<tr>
									<td><c:out value="${p.cod }" /></td>
									<td><c:out value="${p.nome }" /></td>
									<td><c:out value="${p.data }" /></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
				</c:if>
			</div>
			<div class="content"></div>
		</div>
	</section>
	
	<br>
	<br>

	<div align="center">
		<!-- 
		<table border="1">
			<thead>
				<tr>
					<th><b>ID</b></th>
					<th><b>Nome</b></th>
					<th><b>Data Nasc.</b></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>a</td>
					<td>a</td>
					<td>aa/aa/aaaa</td>
				</tr>
				<tr>
					<td>b</td>
					<td>b</td>
					<td>bb/bb/bbbb</td>
				</tr>
			</tbody>
		</table>
		 -->
	</div>
	
</body>
</html>