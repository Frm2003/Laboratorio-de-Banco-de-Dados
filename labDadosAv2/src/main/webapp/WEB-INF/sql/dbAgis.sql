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

drop table Disciplina
create table Disciplina(
	cod int identity(1001, 1) not null,
	nome varchar(100) not null,
	qtdAulas int not null,
	semestre int not null,
	horario varchar(15) not null,
	codProfessor int not null,
	foreign key (codProfessor) references Professor(cod),

	primary key (cod)
)

create table Professor(
	cod int identity(1, 1) not null,
	nome varchar(100) not null,

	primary key(cod)
)

drop table Aluno
create table Aluno(
	ra varchar(9) not null,
	cpf varchar(11) not null,
	nome varchar(100) not null,
	nomeSocial varchar(100),
	dataDeNasc date not null,
	emailPessoal varchar(100) not null,
	emailCorporativo varchar(100) not null,
	dataConc2grau date not null,
	instConc2grau varchar(100) not null,
	ptVestibular int not null,
	posVestibular int not null,
	anoIngresso int not null,
	semetreDeIngresso int not null,
	anoLimite int not null,
	semetreLimite int not null,
	dataMatricula date not null,

	primary key(ra)
)

drop table alunoCurso
create table alunoCurso(
	ra varchar(9) not null,
	foreign key (ra) references Aluno(ra),
	codCurso int not null,
	foreign key (codCurso) references Curso(cod),

	primary key (ra, codCurso)
)

drop table Matricula
create table Matricula(
	cod int identity(1, 1) not null,
	ra varchar(9) not null,
	foreign key (ra) references Aluno(ra),
	codDisci int not null,
	foreign key (codDisci) references Disciplina(cod),
	ano int not null,
	semestre int not null,
	situacao varchar(20) not null,
	nota int not null,
	aprovado bit not null,

	primary key (cod)
)

drop table CursoDisciplina
create table CursoDisciplina(
	codCurso int not null,
	foreign key (codCurso) references Curso(cod),
	codDisci int not null,
	foreign key (codDisci) references Disciplina(cod),

	primary key (codCurso, codDisci)
)

create table Chamada(
	ra varchar(9) not null,
	foreign key (ra) references Aluno(ra),
	codDisci int not null,
	foreign key (codDisci) references Disciplina(cod),
	dataChamada date not null,
	falta int not null,

	primary key(ra, codDisci, dataChamada)
)
-- TRIGGERS
enable trigger matriculaSemestreUm on alunoCurso
enable trigger noDeleteMatricula on matricula
enable trigger noDeleteChamada on chamada

create trigger matriculaSemestreUm
on alunoCurso
after insert
as
begin
    insert into Matricula(ra, codDisci, ano, semestre, situacao, nota, aprovado)
	select
		i.ra as ra,
		d.cod as codDisci,
        cast(year(getdate()) as int) as ano,
        case when MONTH(getdate()) > 6 then '2' else '1' end as semestre,
		'cursando' as situacao,
		0 as nota,
		0 as aprovado
    from inserted i 
		inner join CursoDisciplina cd on i.codCurso = cd.codCurso
		inner join Disciplina d on cd.codDisci = d.cod
	where d.semestre = 1
end

create trigger noDeleteMatricula
on Matricula
instead of delete
as begin
	rollback transaction
    RAISERROR('Exclusão não permitida na tabela matricula.', 16, 1)
END

create trigger noDeleteChamada
on Chamada
instead of delete
as begin
	rollback transaction
    RAISERROR('Exclusão não permitida na tabela chamada.', 16, 1)
END

--PROCEDURES IUD'S
alter procedure iudAluno(@acao as varchar(1), @ra as varchar(9), @cpf as varchar(11), @nome as varchar(100), @nomeSocial as varchar(100), @dataNasc as date, @emailPessoal as varchar(100), @emailCorporativo as varchar(100), @dataConc2grau as date, @instConc2grau as varchar(100), @ptVestibular as int, @posVestibular as int, @curso as int, @saida as bit output)
as
	if (lower(@acao) = 'i') begin
		declare @cpfEhValido as bit
		exec validarCPF @cpf, @cpfEhValido output

		if (@cpfEhValido = 1) begin
			declare @idadeEhValido as bit
			exec validaIdade @dataNasc, @idadeEhValido output

			if (@idadeEhValido = 1) begin
				declare @anoAtual as int = cast(year(getdate()) as int) 
				print(@anoAtual)

				declare @semAtual as varchar(1) = ''
				exec getSemestre @semAtual output

				declare @dataLimite as date
				exec calculaDataLimite @dataLimite output

				declare @anoLimite as int = year(@dataLimite)

				insert into Aluno values(@ra, @cpf, @nome, @nomeSocial, @dataNasc, @emailPessoal, @emailCorporativo, @dataConc2grau, @instConc2grau, @ptVestibular, @posVestibular, @anoAtual, cast(@semAtual as int), @anoLimite, cast(@semAtual as int), getdate())
				insert into alunoCurso values(@ra, @curso)

				set @saida = 1
			end
		end

		if (lower(@acao) = 'u') begin
			update Aluno set cpf = @cpf, nome = @nome, nomeSocial = @nomeSocial, dataDeNasc = @dataNasc, emailPessoal = @emailPessoal, emailCorporativo = @emailCorporativo, dataConc2grau = @dataConc2grau, instConc2grau = @instConc2grau, ptVestibular = @ptVestibular, posVestibular = @posVestibular where ra = @ra
		end

		if (lower(@acao) = 'd') begin
			delete Aluno where ra = @ra
		end
	end

alter procedure iudDisciplina(@acao char(1), @cod as int, @nome as varchar(100), @qtdAulas as int, @semestre as int, @horario as varchar(15), @codCurso as int, @codProfessor as int, @saida as bit output)
as
	if (lower(@acao) = 'i') begin
		insert into Disciplina values (@nome, @qtdAulas, @semestre, @horario, @codProfessor)
		insert into CursoDisciplina values (@codCurso, @cod)
		set @saida = 1
	end

	if (lower(@acao) = 'u') begin
		update Disciplina set nome = @nome, qtdAulas = @qtdAulas, semestre = @semestre, codProfessor = @codProfessor where cod = @cod
		set @saida = 1
	end

	if (lower(@acao) = 'd') begin
		delete Disciplina where cod = @cod
		set @saida = 1
	end

alter procedure iudCurso(@acao char(1), @cod as int, @nome as varchar(100), @cargaHoraria as int, @sigla as varchar(10), @enade as decimal(7, 2), @turno as varchar(10), @saida as bit output)
as
	if (lower(@acao) = 'i') begin
		insert into Curso values (@nome, @cargaHoraria, @sigla, @enade, @turno)
		set @saida = 1
	end

	if (lower(@acao) = 'u') begin
		update Curso set nome = @nome, cargaHoraria = @cargaHoraria, sigla = @sigla, enade = @enade, turno = @turno where cod = @cod
		set @saida = 1
	end

	if (lower(@acao) = 'd') begin
		delete Curso where cod = @cod
		set @saida = 1
	end

alter procedure iudMatricula(@acao as varchar(1), @ra as varchar(9), @codDisci as int, @saida as varchar(50) output)
as
	if (lower(@acao) = 'i') begin
		declare @anoAtual as int = cast(year(getdate()) as int) 

		declare @semAtual as varchar(1) = ''
		exec getSemestre @semAtual output

		insert into Matricula values(@ra, @codDisci, @anoAtual, cast(@semAtual as int), 'cursando', 0, 0)
	end

	if (lower(@acao) = 'u') begin
		update matricula set situacao = 'dispensado', aprovado = 1 where ra = @ra and codDisci = @codDisci
	end

alter procedure iudChamada(@acao as varchar(1), @ra as varchar(9), @codDisci as int, @falta as int, @dataChamada as date, @saida as varchar(50) output)
as
	if (lower(@acao) = 'i') begin
		insert into Chamada values(@ra, @codDisci, getdate(), @falta)
	end

	if (lower(@acao) = 'u') begin
		update Chamada set falta = @falta where ra = @ra and codDisci = @codDisci and dataChamada = @dataChamada
	end

-- PROCEDURES DE VALIDAÇÃO
alter procedure validaIdade(@dataNasc as date, @saida as bit output)
as
	declare @idade as int = datediff(year, @dataNasc, getdate())
	if (@idade >= 16) begin
		set @saida = 1
	end else begin
		set @saida = 0
	end

alter procedure validarCPF(@cpf varchar(11), @valido bit output)
as	
	
	declare @Nr_Documento_Aux VARCHAR(11)
    set @Nr_Documento_Aux = LTRIM(RTRIM(@cpf))

	if (@Nr_Documento_Aux not in ('00000000000', '11111111111', '22222222222', '33333333333', '44444444444', '55555555555', '66666666666', '77777777777', '88888888888', '99999999999', '12345678909')) begin
		declare @saida varchar(100)
		declare @restDiv int = 0

		exec calculoCpf @cpf, 10, @restDiv output
		exec validaDigitoCpf @cpf, @restDiv, 10, @saida output

		if (@saida = 'valida') begin
			set @restDiv = 0
	
			exec calculoCpf @cpf, 11, @restDiv output
			exec validaDigitoCpf @cpf, @restDiv, 11, @saida output

			if (@saida = 'valida') begin
				set @valido = 1 
			end else begin
				set @valido = 0
			end
		end else begin
			set @valido = 0
		end
	end else begin 
		set @valido = 0
	end

alter procedure calculoCpf(@cpf varchar(11), @cont as int, @result int output)
as
	declare @soma int = 0
	declare @val varchar(1)
	declare @pos int = 0

	while (@cont >= 2) begin
		set @pos = @pos + 1
		set @val = substring(@cpf, @pos, 1)
		set @soma = @soma + (cast(@val as int) * @cont)
		set @cont = @cont - 1
	end

	set @result = @soma % 11 

alter procedure validaDigitoCpf(@cpf as varchar(11), @restDiv as int, @pos as int, @saida varchar(100) output)
as
	if (@restDiv >= 2) begin
		if (SUBSTRING(@cpf, @pos, 1) like cast(11 - @restDiv as varchar(1))) begin
			set @saida = 'valida'
		end else begin
			set @saida = 'invalida'
		end
	end else begin
		if (SUBSTRING(@cpf, @pos, 1) = '0') begin
			set @saida = 'valida'
		end else begin
			set @saida = 'invalida'
		end
	end 

-- PROCEDURES QUE GERAM DADOS
alter procedure calculaDataLimite(@saida as date output)
as
	set @saida = dateadd(year, 5, getDate())

alter procedure geraRa(@ra as varchar(9) output)
as
	declare @anoAtual as int = year(getdate())
	declare @semestreAtual as varchar(1)

	set @ra = '' + @anoAtual

	exec getSemestre @semestreAtual output
	set @ra = @ra + @semestreAtual

	declare @cont as int = 0

	while (@cont < 4) begin
		set @ra = @ra + cast(cast(rand() * 10 as int) as varchar(1))
		set @cont = @cont + 1
	end

alter procedure getSemestre(@semestreAtual as int output)
as
	if (MONTH(getdate()) > 6) begin
		set @semestreAtual = '2'
	end else begin
		set @semestreAtual = '1'
	end

-- VIEWS DOS SELECTS
alter view selectAluno as
	select a.ra as Ra, a.cpf as cpf, a.nome as nome, a.nomeSocial as nomeS, a.dataDeNasc as dataNasc, a.emailPessoal as emailP, a.emailCorporativo as emailC,
		a.dataConc2grau as dataConc2grau, a.instConc2grau as inst, a.ptVestibular as ptVest, a.posVestibular as posVest, a.anoIngresso as anoi, a.semetreDeIngresso as semi,
		a.anoLimite as al, a.semetreLimite as seml,a.dataMatricula as dataMatricula,
		c.cod as codc, c.nome as nomec, c.cargaHoraria as cargaHoraria, c.sigla as sigla, c.turno as turno, c.enade as enade
	from Aluno a inner join alunoCurso ac on a.ra = ac.ra
		inner join Curso c on c.cod = ac.codCurso

alter view selectDisciplina as
	select d.cod as cod, d.horario as horario, d.nome as nomee, d.qtdAulas as qtdAulas, d.semestre as semestre, 
		c.cod as codc, c.nome as nomec, c.cargaHoraria as cargaHoraria, c.sigla as sigla, c.turno as turno, c.enade as enade,
		p.cod as codp, p.nome as nomep, 
	from Curso c inner join CursoDisciplina cd on c.cod = cd.codCurso
		inner join Disciplina d on cd.codDisci = d.cod
		inner join Professor p on p.cod = d.codProfessor

alter view selectMatricula as
	select a.ra as Ra, a.cpf as cpf, a.nome as nome, a.nomeSocial as nomeS, a.dataDeNasc as dataNasc, a.emailPessoal as emailP, a.emailCorporativo as emailC,
		a.dataConc2grau as dataConc2grau, a.instConc2grau as inst, a.ptVestibular as ptVest, a.posVestibular as posVest, a.anoIngresso as anoi, a.semetreDeIngresso as semi,
		a.anoLimite as al, a.semetreLimite as seml, a.dataMatricula as dataMatricula,
		d.cod as cod, d.horario as horario, d.nome as nomee, d.qtdAulas as qtdAulas, d.semestre as semestre, 
		c.cod as codc, c.nome as nomec, c.cargaHoraria as cargaHoraria, c.sigla as sigla, c.turno as turno, c.enade as enade,
		m.ano as ano, m.aprovado as aprovado, m.semestre as semestrem, m.situacao as situacao, m.nota as nota,
		p.cod as codp, p.nome as nomep
	from Aluno a inner join Matricula m on a.ra = m.ra 
		inner join Disciplina d on m.codDisci = d.cod 
		inner join CursoDisciplina cd on d.cod = cd.codDisci 
		inner join Curso c on c.cod = cd.codCurso
		inner join Professor p on p.cod = d.codProfessor

create view selectChamada as 
	select a.ra as Ra, a.cpf as cpf, a.nome as nome, a.nomeSocial as nomeS, a.dataDeNasc as dataNasc, a.emailPessoal as emailP, a.emailCorporativo as emailC,
		a.dataConc2grau as dataConc2grau, a.instConc2grau as inst, a.ptVestibular as ptVest, a.posVestibular as posVest, a.anoIngresso as anoi, a.semetreDeIngresso as semi,
		a.anoLimite as al, a.semetreLimite as seml, a.dataMatricula as dataMatricula,
		d.cod as cod, d.horario as horario, d.nome as nomee, d.qtdAulas as qtdAulas, d.semestre as semestre, 
		c.cod as codc, c.nome as nomec, c.cargaHoraria as cargaHoraria, c.sigla as sigla, c.turno as turno, c.enade as enade,
		ch.falta as falta, ch.dataChamada as dataChamada,
		p.cod as codp, p.nome as nomep
	from Aluno a
		inner join Chamada ch on ch.ra = a.ra
		inner join Disciplina d on ch.codDisci = d.cod 
		inner join CursoDisciplina cd on d.cod = cd.codDisci 
		inner join Curso c on c.cod = cd.codCurso
		inner join Professor p on p.cod = d.codProfessor

select * from selectChamada where cod = 1001 and dataChamada = '20/10/2023'

-- FUNCTION
create function historico(@ra as varchar(10))
returns @table table (
	codDisci int not null,
	nomeDisci varchar(50) not null,
	nomeProf varchar(50) not null,
	notaFinal varchar(3) not null,
	qtdFaltas int not null
) as begin
	insert into @table (codDisci, nomeDisci, nomeProf, notaFinal, qtdFaltas)
	select 
		d.cod, 
		d.nome, 
		p.nome, 
		case when m.situacao = 'dispensado' then 
			'D' 
		else  
			cast(m.nota as varchar(3))
		end as notaFinal, 
		sum(ch.falta) as qtdFaltas
	from Disciplina d 
		inner join Professor p on p.cod = d.codProfessor
		inner join Matricula m on m.codDisci = d.cod
		inner join Chamada ch on ch.ra = m.ra
	where m.ra = '202321939' and d.cod = ch.codDisci
	group by d.cod, d.nome, p.nome, m.situacao, m.nota

	return
end

alter function geraChamada(@codDisci as int)
returns @table table (
	ra varchar(9) not null,
	nomeAluno varchar(100) not null,
	codDisci int not null,
	nomeDisci varchar(100) not null,
	nomeProfessor varchar(100) not null
) as begin
	declare @ano as int = year(getdate())
	declare @semestre as int 
	
	if (month(getdate()) > 6) begin 
		set @semestre = 2
	end else begin
		set @semestre = 1
	end

	insert into @table (ra, nomeAluno, codDisci, nomeDisci, nomeProfessor)
	select 
		a.ra,
		a.nome,
		d.cod,
		d.nome,
		p.nome
	from Disciplina d 
		inner join Professor p on p.cod = d.codProfessor
		inner join Matricula m on m.codDisci = d.cod
		inner join Aluno a on a.ra = m.ra
	where d.cod = @codDisci and m.semestre = @semestre and m.ano = @ano
	return
end

select * from geraChamada(1001)
select * from Chamada

select * from Aluno
select * from alunoCurso
select * from Matricula
select * from Professor
select * from Curso
select * from CursoDisciplina
select * from Disciplina
select * from Chamada

DBCC CHECKIDENT ('Curso', RESEED, 0)

delete Aluno
delete alunoCurso
delete Matricula
delete Curso
delete Disciplina
delete CursoDisciplina

select d.cod as dcod, d.nome as nomed, d.qtdAulas as qtdAulas, d.semestre as dsem, d.horario as horario,
	c.cod as codc, c.nome as nomec, c.cargaHoraria as cargaHoraria, c.sigla as sigla, c.turno as turno, c.enade as enade,
	p.cod as codp, p.nome as nomep
from Disciplina d inner join Professor p on p.cod = d.codProfessor
	inner join CursoDisciplina cd on d.cod = cd.codDisci 
	inner join Curso c on c.cod = cd.codCurso
	left join Matricula m on d.cod = m.codDisci and m.ra = '202323295'
where m.ra is null