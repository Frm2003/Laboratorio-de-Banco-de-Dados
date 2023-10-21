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
	<div class="content s1">
		<form class="alin-se w100p" action="login" method="post">
			<div>
				<div>
					<input class="caixaDeTexto m15v" name="ra" type="text" placeholder="Ra"> 
					<div class="alin-se">
						<input class="btSimples" type="submit" name="botao" value="Ver matriculas">
					</div>
				</div>
			</div>
		</form>
		
	</div>
</body>
</html>