<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form class="alinhamento" action="motorista" method="post">
		<div>
			<div>
				<div class="campo espacoMargin alinhamento">
					<input class="caixaDeTexto" type="number" value='<c:out value="${motorista.cod }" />' name="cod" min="0" step="1" placeholder="Código" required="required"> 
					<input class="btSimples" type="submit" name="botao" value="Buscar">
				</div>
				<input class="caixaDeTexto espacoMargin" value='<c:out value="${motorista.nome }" />' type="text" name="nome" placeholder="Nome"> 
				<input class="caixaDeTexto espacoMargin" value='<c:out value="${motorista.naturalidade }" />' type="text" name="naturalidade" placeholder="Naturalidade">
			</div>
			<div class="alinhamento">
				<input class="btSimples" type="submit" name="botao" value="Criar">
				<input class="btSimples" type="submit" name="botao" value="Alterar">
				<input class="btSimples" type="submit" name="botao" value="Remover">
			</div>
		</div>
	</form>
</body>
</html>