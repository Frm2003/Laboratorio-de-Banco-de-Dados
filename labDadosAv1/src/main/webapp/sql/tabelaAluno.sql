use labDadosAv1

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
	ra varchar(9) not null,
	foreign key (ra) references Aluno(ra),
	codDisci int not null,
	foreign key (codDisci) references Disciplina(cod),
	ano int not null,
	semestre int not null,
	situacao varchar(20) not null,
	aprovado bit not null,

	primary key (ra, codDisci, ano, semestre)
)

select a.ra as Ra, a.cpf as cpf, a.nome as nome, a.nomeSocial as nomeS, a.dataDeNasc as dataNasc, a.emailPessoal as emailP, a.emailCorporativo as emailC,
		a.dataConc2grau as dataConc2grau, a.instConc2grau as inst, a.ptVestibular as ptVest, a.posVestibular as posVest, a.anoIngresso as anoi, a.semetreDeIngresso as semi,
		a.anoLimite as al, a.semetreLimite as seml,
		--
		c.cod as codc, c.nome as nomec, c.cargaHoraria as cargaHoraria, c.sigla as sigla, c.turno as turno, c.enade as enade,
		--
		d.cod as cod, d.horario as horario, d.nome as nomee, d.qtdHoraSemanais as qtdHoraSemanais, d.semestre as semestre,
		--
		m.ano as ano, m.aprovado as aprovado, m.semestre as semestre, m.situacao as situacao
from Aluno a inner join Matricula m on a.ra = m.ra 
		inner join Disciplina d on m.codDisci = d.cod
		inner join CursoDisciplina cd on d.cod = cd.codDisci
		inner join Curso c on c.cod = cd.codCurso 
		where a.ra = '202329535'

select * from Aluno
select * from alunoCurso
select * from Matricula
select * from Disciplina

alter procedure iudMatricula(@acao as varchar(1), @ra as varchar(9), @codDisci as int, @saida as varchar(50) output)
as
	if (lower(@acao) = 'i') begin
		declare @anoAtual as int = cast(year(getdate()) as int) 

		declare @semAtual as varchar(1) = ''
		exec getSemestre @semAtual output

		insert into Matricula values(@ra, @codDisci, @anoAtual, cast(@semAtual as int), 'cursando', 0)
	end

alter procedure iudAluno(@acao as varchar(1), @ra as varchar(9), @cpf as varchar(11), @nome as varchar(100), @nomeSocial as varchar(100), @dataNasc as date, @emailPessoal as varchar(100), @emailCorporativo as varchar(100), @dataConc2grau as date, @instConc2grau as varchar(100), @ptVestibular as int, @posVestibular as int, @curso as int, @saida as varchar(50) output)
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
				print(@dataLimite)

				declare @anoLimite as int = year(@dataLimite)

				insert into Aluno values(@ra, @cpf, @nome, @nomeSocial, @dataNasc, @emailPessoal, @emailCorporativo, @dataConc2grau, @instConc2grau, @ptVestibular, @posVestibular, @anoAtual, cast(@semAtual as int), @anoLimite, cast(@semAtual as int))
				insert into alunoCurso values(@ra, @curso)
			end
		end

		if (lower(@acao) = 'u') begin
			update Aluno set ra = @ra, cpf = @cpf, nome = @nome, nomeSocial = @nomeSocial, dataDeNasc = @dataNasc, emailPessoal = @emailPessoal, emailCorporativo = @emailCorporativo, dataConc2grau = @dataConc2grau, instConc2grau = @instConc2grau, ptVestibular = @ptVestibular, posVestibular = @posVestibular where ra = @ra
		end

		if (lower(@acao) = 'd') begin
			delete Aluno where ra = @ra
		end
	end

alter procedure validaIdade(@dataNasc as date, @saida as bit output)
as
	declare @idade as int = datediff(year, @dataNasc, getdate())
	if (@idade >= 16) begin
		set @saida = 1
	end else begin
		set @saida = 0
	end


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

alter procedure getSemestre(@semestreAtual as varchar(1) output)
as
	declare @mes as int = month(getdate())
	if (@mes > 6) begin
		set @semestreAtual = '2'
	end else begin
		set @semestreAtual = '1'
	end


