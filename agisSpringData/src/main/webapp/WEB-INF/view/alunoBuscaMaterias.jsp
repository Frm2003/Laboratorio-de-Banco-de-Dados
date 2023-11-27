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
		</ul>
	</nav>
	<div class="content s1">
		<form class="alin-se w100p" action="alunoBuscaMaterias" method="post">
			<div>
				<div>
					<input class="caixaDeTexto m15v" name="ra" type="text" placeholder="Ra"> 
					<div class="alin-se">
						<input type="submit" name="botao" value="Pesquisar">
					</div>
				</div>
			</div>
		</form>
	</div>
	<section class="sec1">
		<div class="navigation">
			<label for="r1" class="bar labelTitulo p05" checked>Matricular-se</label> 
			<label for="r2" class="bar labelTitulo p05">Histórico</label> 
		</div>
		<div class="slides">
			<input type="radio" name="r" id="r1"> 
			<input type="radio" name="r" id="r2"> 
			<div class="content s1">
				<c:if test="${not empty materias }">
					<div class="alin-se w100p m15v">
						<div class="w50p">
							<div class="tabelaLinha m15v">
								<p style="font-size: 21px" class="colTitulo3" id="col3"><b>Nome Disci.</b></p>
								<p style="font-size: 21px" class="colTitulo3" id="col3"><b>Nome Prof.</b></p>
								<p style="font-size: 21px" class="colTitulo3" id="col3"><b>Dia da Semana</b></p>
								<p style="font-size: 21px" class="colTitulo3" id="col3"><b>Horario</b></p>
								<p style="font-size: 21px" class="colTitulo3" id="col3"></p>
							</div>
							<c:forEach var="m" items="${materias }">
								<form action="alunoBuscaMaterias" method="post">
									<div class="alin-se tabelaLinha">
										<input type="hidden" name="ra" value="${ra }">
										<input type="hidden" name="codMateria" value="${m.cod }">
										<p id="col3"><c:out value="${m.disciplina.nome }" /></p>
										<p id="col3"><c:out value="${m.professor.nome }" /></p>
										<p id="col3"><c:out value="${m.diaDaSemana }" /></p>
										<p id="col3"><c:out value="${m.horario }" /></p>
										<input class="btSimples" id="col3" type="submit" name="botao" value="Matricular">
									</div>
								</form>
							</c:forEach>
						</div>
					</div>
				</c:if>		
			</div>
			<div class="content">
				<c:if test="${not empty notas }">
					<div class="alin-se w50p m15v">
						<div class="w100p">
							<div class="tabelaLinha m15v">
								<p style="font-size: 21px" class="colTitulo" id="col"><b>Nome materia</b></p>
								<p style="font-size: 21px" class="colTitulo" id="col"><b>Situação</b></p>
								<p style="font-size: 21px" class="colTitulo" id="col"><b>Nota 1</b></p>
								<p style="font-size: 21px" class="colTitulo" id="col"><b>Nota 2</b></p>
								<p style="font-size: 21px" class="colTitulo" id="col"><b>Nota 3</b></p>
								<p style="font-size: 21px" class="colTitulo" id="col"><b>Nota Final</b></p>
								<p style="font-size: 21px" class="colTitulo" id="col"></p>
							</div>
							<c:forEach var="n" items="${notas }">
								<form action="alunoBuscaMaterias" method="post">
									<div class="tabelaLinha p05">
										<input type="hidden" name="ra" value="${n.matricula.aluno.ra }">
										<input type="hidden" name="codMateria" value="${n.matricula.materia.cod }">
										
										<p id="col"><c:out value="${n.matricula.materia.disciplina.nome}" /></p>
										<p id="col"><c:out value="${n.matricula.situacao }" /></p>
										<p id="col"><c:out value="${n.n1}" /></p>
										<p id="col"><c:out value="${n.n2}" /></p>
										<p id="col"><c:out value="${n.n3}" /></p>
										<c:if test="${n.matricula.situacao == 'dispensado' }">
											<p id="col">D</p>
											<input class="btSimples" id="col" type="submit" name="botao" value="Dispensar" disabled>
										</c:if>
										<c:if test="${n.matricula.situacao != 'dispensado' }">
											<p id="col"><c:out value="${n.notaFinal }"/></p>
											<input class="btSimples" id="col" type="submit" name="botao" value="Dispensar">
										</c:if>
									</div>
								</form>
							</c:forEach>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</section>
</body>
</html>