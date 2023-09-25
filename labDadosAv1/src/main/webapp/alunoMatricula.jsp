<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="estilo/nav.css">
	<link rel="stylesheet" href="estilo/slide.css">
	<link rel="stylesheet" href="estilo/style.css">
	<script src="scripts/script.js"></script>
	<title>Insert title here</title>
</head>
<body>
	<div class="content s1">
		<form class="alin-se w100p" action="matricula" method="post">
			<div>
				<div>
					<input class="caixaDeTexto m15v" name="ra" type="text" placeholder="Ra"> 
					<div class="alin-se">
						<input class="btSimples" type="submit" name="botao" value="Buscar">
						<input class="btSimples" type="submit" name="botao" value="Listar">
					</div>
				</div>
			</div>
		</form>
		<div>
			<div class="alin-se w100p">
				<div class="w100p">
					<c:forEach var="m" items="${disciplinas }">
						<div class="tabelaLinha p05">
							<form class="alin-se" action="matricula" method="post">
								<input class="caixaDeTexto m15v"  name="cod" id="col" value="<c:out value="${m.cod }" />" />
								<input class="caixaDeTexto m15v" id="col" value="<c:out value="${m.nome }" />" disabled/>
								<input class="caixaDeTexto m15v" id="col" value="<c:out value="${m.qtdHorasSemanais }" />" disabled/>
								<input class="caixaDeTexto m15v" id="col" value="<c:out value="${m.semestre }" />" disabled/>
								<input class="caixaDeTexto m15v" id="col" value="<c:out value="${m.horario }" />" disabled/>
								<input class="btSimples" id="col" type="submit" name="botao"value="Matricular">
							</form>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div>
			<c:if test="${not empty matriculas }">
				<div class="alin-se w50p m15v">
					<div class="w50p">
						<div class="tabelaLinha m15v">
							<p class="colTitulo" id="col">Ra</p>
							<p class="colTitulo" id="col">Disciplina</p>
							<p class="colTitulo" id="col">Situação</p>
							<p class="colTitulo" id="col">Ano</p>
							<p class="colTitulo" id="col">semestre</p>
							<p class="colTitulo" id="col">Aprovado</p>
						</div>
						<c:forEach var="a" items="${matriculas }">
							<div class="tabelaLinha p05">
								<p id="col"><c:out value="${a.aluno.ra }" /></p>
								<p id="col"><c:out value="${a.disciplina.nome }" /></p>
								<p id="col"><c:out value="${a.situacao }" /></p>
								<p id="col"><c:out value="${a.ano }" /></p>
								<p id="col"><c:out value="${a.semestre }" /></p>
								<p id="col"><c:out value="${a.aprovado }" /></p>
							</div>
						</c:forEach>	
					</div>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>