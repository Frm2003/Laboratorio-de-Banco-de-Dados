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
	<section class="sec1">
		<div class="navigation">
			<label for="r1" class="bar labelTitulo">Formulário</label> 
			<label for="r2" class="bar labelTitulo">Lista Completa</label> 
		</div>
		<div class="slides">
			<input type="radio" name="r" id="r1"> 
			<input type="radio" name="r" id="r2"> 
			<div class="content s1">
				<form class="alinhamento" action="cliente" method="post">
					<div>
						<div>
							<div class="campo espacoMargin alinhamento">
								<input class="caixaDeTexto" type="number" value='<c:out value="${cliente.cpf }" />' name="cpf" placeholder="cpf" required="required"> 
								<input class="btSimples" type="submit" name="botao" value="Buscar">
							</div>
							<input class="caixaDeTexto espacoMargin" value='<c:out value="${cliente.nome }" />' type="text" name="nome" placeholder="Nome"> 
							<input class="caixaDeTexto espacoMargin" value='<c:out value="${cliente.email }" />' type="text" name="email" placeholder="Email">
							<input class="caixaDeTexto espacoMargin" value='<c:out value="${cliente.limiteCred }" />' type="number" name="limiteCred" placeholder="limite Créd.">
							<input class="caixaDeTexto espacoMargin" value='<c:out value="${cliente.dataNasc }" />' type="date" name="dataNasc">
						</div>
						<div class="alinhamento">
							<input class="btSimples" type="submit" name="botao" value="Criar">
							<input class="btSimples" type="submit" name="botao" value="Alterar">
							<input class="btSimples" type="submit" name="botao" value="Remover">
							<input class="btSimples" type="submit" name="botao" value="Listar">
						</div>
					</div>
				</form>
			</div>
			<div class="content">
				
			</div>
		</div>
	</section>	
</body>
</html>