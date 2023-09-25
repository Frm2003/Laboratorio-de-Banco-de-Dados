create database labDadosAv1
go 
use labDadosAv1

drop table Curso
create table Curso(
	cod int identity(1, 1) not null,
	nome varchar(100) not null,
	cargaHoraria int not null,
	sigla varchar(10) not null,
	enade decimal(7, 2) not null,
	turno varchar(10) not null,

	primary key (cod)
)

select * from Curso

drop table Disciplina
create table Disciplina(
	cod int identity(1001, 1) not null,
	nome varchar(100) not null,
	qtdHoraSemanais int not null,
	semestre int not null,
	horario varchar(15) not null,

	primary key (cod)
)

select * from Disciplina

drop table CursoDisciplina
create table CursoDisciplina(
	codCurso int not null,
	foreign key (codCurso) references Curso(cod),
	codDisci int not null,
	foreign key (codDisci) references Disciplina(cod),

	primary key (codCurso, codDisci)
)
select * from CursoDisciplina

alter procedure iudDisciplina(@acao char(1), @cod as int, @nome as varchar(100), @qtdHoraSemanais as int, @semestre as int, @horario as varchar(15), @saida as varchar(100) output)
as
	if (lower(@acao) = 'i') begin
		insert into Disciplina values (@nome, @qtdHoraSemanais, @semestre, @horario)
	end

	if (lower(@acao) = 'u') begin
		update Disciplina set nome = @nome, qtdHoraSemanais = @qtdHoraSemanais, semestre = @semestre where cod = @cod
	end

	if (lower(@acao) = 'd') begin
		delete Disciplina where cod = @cod
	end

alter procedure iudCurso(@acao char(1), @cod as int, @nome as varchar(100), @cargaHoraria as int, @sigla as varchar(10), @enade as decimal(7, 2), @turno as varchar(10), @saida as varchar(100) output)
as
	if (lower(@acao) = 'i') begin
		insert into Curso values (@nome, @cargaHoraria, @sigla, @enade, @turno)
	end

	if (lower(@acao) = 'u') begin
		update Curso set nome = @nome, cargaHoraria = @cargaHoraria, sigla = @sigla, enade = @enade, turno = @turno where cod = @cod
	end

	if (lower(@acao) = 'd') begin
		delete Curso where cod = @cod
	end