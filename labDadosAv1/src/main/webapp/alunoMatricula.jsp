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
					<div class="campo em15v alin-se">
						<input class="caixaDeTexto m15v" name="ra" type="text" placeholder="Ra"> 
						<input class="btSimples" type="submit" name="botao" value="Buscar">
					</div>
				</div>
			</div>
		</form>
		<div>
			<div class="alin-se w100p m15v">
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
	</div>
</body>
</html>