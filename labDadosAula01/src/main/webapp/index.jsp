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
			<label for="r1" class="bar labelTitulo" checked>Form. Motorista</label>
			<label for="r2" class="bar labelTitulo">Form. Onibus</label>
			<label for="r3" class="bar labelTitulo">Form. Viagem</label>
			<label for="r4" class="bar labelTitulo">Listas</label>        
		</div>
		<div class="slides">
			<input type="radio" name="r" id="r1"> 
			<input type="radio" name="r" id="r2">
			<input type="radio" name="r" id="r3">
			<input type="radio" name="r" id="r4">
			
			<div class="content s1">
				<!-- 
				 -->
				<jsp:include page="motorista.jsp" />
			</div>
			
			<div class="content">
				<!-- 
				 -->
				<jsp:include page="onibus.jsp" />	
			</div>
			
			<div class="content">
				<form class="alinhamento" action="viagem" method="post">
					<div>
						<div>
							<div class="campo espacoMargin alinhamento">
								<input class="caixaDeTexto" type="number" value='<c:out value="${viagem.cod }" />' name="cod" min="0" step="1" placeholder="Código" required="required"> 
								<input class="btSimples" type="submit" name="botao" value="Buscar">
							</div>
							<select name="onibus" class="caixaDeTexto espacoMargin">
								<option value="0">Selecione um onibus</option>
								<c:forEach var="o" items="${onibuses }">
									<option value="${o.placa }">
										<c:out value="${o.placa }" />
									</option>
								</c:forEach>
							</select>
							<select name="motorista" class="caixaDeTexto espacoMargin">
								<option value="0">Selecione motorista</option>
								<c:forEach var="o" items="${motoristas }">
									<option value="${o.cod }">
										<c:out value="${o.nome}" />
									</option>
								</c:forEach>
							</select> 
							<input class="caixaDeTexto espacoMargin" value='<c:out value="${viagem.horaSaida }" />' type="number" name="horaSaida " placeholder="Hora de saída ">
							<input class="caixaDeTexto espacoMargin" value='<c:out value="${viagem.horaChegada }" />' type="number" name="horaChegada " placeholder="Hora de chegada">
							<input class="caixaDeTexto espacoMargin" value='<c:out value="${viagem.partida }" />' type="text" name="partida" placeholder="Partida">
							<input class="caixaDeTexto espacoMargin" value='<c:out value="${viagem.destino }" />' type="text" name="destino" placeholder="Destino">  
						</div>
						<div class="alinhamento">
							<input class="btSimples" type="submit" name="botao" value="Criar">
							<input class="btSimples" type="submit" name="botao" value="Alterar">
							<input class="btSimples" type="submit" name="botao" value="Remover">
						</div>
					</div>
				</form>
			</div>
			
			<div class="content">
			d
			</div>
		</div>
	</section>
</body>
</html>