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
	<form class="alinhamento" action="onibus" method="post">
		<div>
			<div>
				<div class="campo espacoMargin alinhamento">
					<input class="caixaDeTexto" value='<c:out value="${onibus.placa }" />' type="text" name="placa" placeholder="Placa" required="required"> 
					<input class="btSimples" type="submit" name="botao2" value="Buscar">
				</div>
				<input class="caixaDeTexto espacoMargin" value='<c:out value="${onibus.marca }" />' type="text" name="marca" placeholder="Nome"> 
				<input class="caixaDeTexto espacoMargin" value='<c:out value="${onibus.ano }" />' type="number" name="ano" min="0" step="1" placeholder="Ano">
				<input class="caixaDeTexto espacoMargin" value='<c:out value="${onibus.desc }" />' type="text" name="desc" placeholder="Descrição">
			</div>
			<div class="alinhamento">
				<input class="btSimples" type="submit" name="botao2" value="Criar">
				<input class="btSimples" type="submit" name="botao2" value="Alterar">
				<input class="btSimples" type="submit" name="botao2" value="Remover">
			</div>
		</div>
	</form>	
	<c:if test="${not empty mensagem }">
		<c:out value="${mensagem }"/>
	</c:if>
</body>
</html>