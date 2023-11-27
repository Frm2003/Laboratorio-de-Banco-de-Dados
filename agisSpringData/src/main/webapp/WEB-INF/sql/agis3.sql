use master
go
drop database agis3

create database agis3
go
use agis3

insert into professores values
('Satoshi'),
('Colevati'),
('Vendramel')
go
INSERT INTO Cursos VALUES 
(2800, 4.00, 'Analise e desenvolvimento de sistemas', 'ADS', 'Tarde'),
(2800, 4.00, 'Analise e desenvolvimento de sistemas', 'ADS', 'Noite')
GO
INSERT INTO Disciplinas VALUES 
('Arquitetura e Organizacao de Computadores', 4, 1, 1),
('Arquitetura e Organizacao de Computadores', 4, 1, 2),
('Laboratorio de Hardware', 4, 1, 1),
('Banco de Dados', 4, 1, 1),
('Sistemas Operacionais I', 4, 1, 1),
('Sistemas Operacionais I', 4, 1, 2),
('Estruturas de Dados', 4, 1, 2),
('Laboratorio de Banco de Dados', 4, 1, 1),
('Metodologia de Pesquisa Cientifico Tecnologica', 2, 1, 1)
GO
INSERT INTO materias VALUES 
('Segunda', '13:00 a 16:30', 'Tipo1', 1, 2),
('Segunda', '19:20 a 22:50', 'Tipo1', 2, 2),
('Terca', '13:00 a 16:30', 'Tipo1', 3, 2),
('Quarta', '13:00 a 16:30', 'Tipo1', 4, 2),
('Quinta', '13:00 a 16:30', 'Tipo2', 5, 2),
('Quinta', '19:20 a 22:50', 'Tipo2', 6, 2),
('Sabado', '08:00 a 11:30', 'Tipo2', 7, 2),
('Sexta', '13:00 a 16:30', 'Tipo3', 8, 2),
('Sexta', '16:30 a 18:20', 'Tipo4', 9, 3)

DBCC CHECKIDENT ('disciplinas', RESEED, 0);

delete alunos
delete matriculas
delete avaliacoes

--SELECTS
select * from alunos
select * from cursos
select * from chamadas
select * from disciplinas
select * from materias
select * from matriculas
select * from avaliacoes

select matri.*, c.turno
from matriculas matri
	inner join materias mat on mat.cod = matri.codMateria
	inner join disciplinas d on d.cod = mat.codDisci
	inner join cursos c on d.codCurso = c.cod
where c.turno = 'Noite'

select matri.* 
from matriculas matri 
	inner join materias mate on matri.codMateria = mate.cod
where mate.cod = 1

select mat.*
from materias mat
	inner join disciplinas d on mat.codDisci = d.cod
	inner join cursos c on d.codCurso = c.cod 
	left join matriculas m on m.codMateria = mat.cod and m.alunoRa = '202321687'
where m.alunoRa is null

--PROCEDURES DE VALIDAÇÃO
create procedure validaIdade(@dataNasc as date, @saida as bit output)
as
	declare @idade as int = datediff(year, @dataNasc, getdate())
	if (@idade >= 16) begin
		set @saida = 1
	end else begin
		set @saida = 0
	end
go
create procedure validarCPF(@cpf varchar(11), @valido bit output)
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
go
create procedure calculoCpf(@cpf varchar(11), @cont as int, @result int output)
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
go
create procedure validaDigitoCpf(@cpf as varchar(11), @restDiv as int, @pos as int, @saida varchar(100) output)
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
go
--PROCEDURES QUE GERAM DADOS
create procedure calculaDataLimite(@saida as date output)
as
	set @saida = dateadd(year, 5, getDate())
go
create procedure getSemestre(@saida as int output)
as
	if (MONTH(getdate()) > 6) begin
		set @saida = 2
	end else begin
		set @saida = 1
	end
go
create procedure geraRa(@ra as varchar(9) output)
as
	declare @anoAtual as int = year(getdate())
	declare @semestreAtual as varchar(1)

	set @ra = '' + @anoAtual

	if (MONTH(getdate()) > 6) begin
		set @semestreAtual = '2'
	end else begin
		set @semestreAtual = '1'
	end

	set @ra = @ra + @semestreAtual

	declare @cont as int = 0

	while (@cont < 4) begin
		set @ra = @ra + cast(cast(rand() * 10 as int) as varchar(1))
		set @cont = @cont + 1
	end

-- TRIGGERS
create trigger matriculaSemestreUm
on alunos
after insert
as
begin
    insert into matriculas(ano, semestre, aprovado, situacao, codMateria, alunoRa)
	select 
		cast(year(getdate()) as int) as ano,
        case when MONTH(getdate()) > 6 then '2' else '1' end as semestre,
		0 as aprovado,
		'cursando' as situacao,
		m.cod as codMateria,
		i.ra as alunoRa
	from inserted i
		inner join cursos c on i.codCurso = c.cod
		inner join disciplinas d on d.codCurso = c.cod
		inner join materias m on d.cod = m.codDisci
	where d.semestre = 1
end

enable trigger matriculaSemestreUm on alunos
disable trigger matriculaSemestreUm on alunos

create trigger inseriNotaMatricula
on alunos
after insert
as
begin
	insert into avaliacoes(nota1, nota2, nota3, codMateria, ra, ano, semestre)
	select
		0,
		0,
		0,
		m.cod as codMateria,
		i.ra as alunoRa,
		cast(year(getdate()) as int) as ano,
        case when MONTH(getdate()) > 6 then '2' else '1' end as semestre
	from inserted i
		inner join cursos c on i.codCurso = c.cod
		inner join disciplinas d on d.codCurso = c.cod
		inner join materias m on d.cod = m.codDisci
	where d.semestre = 1
end

enable trigger inseriNotaMatricula on alunos
disable trigger inseriNotaMatricula on alunos

create trigger noDeleteMatricula
on matriculas
instead of delete
as begin
	rollback transaction
    RAISERROR('Exclusão não permitida na tabela matricula.', 16, 1)
end

enable trigger noDeleteMatricula on matriculas
disable trigger noDeleteMatricula on matriculas

create trigger noDeleteChamada
on chamadas
instead of delete
as begin
	rollback transaction
    RAISERROR('Exclusão não permitida na tabela chamada.', 16, 1)
end

enable trigger noDeleteChamada on chamadas

--FUNCTION
create function geraChamada(@codMateria as int)
returns @table table (
	ra varchar(9) not null,
	codMateria bigint not null,
	dataChamada date not null,
	qtdFaltas int not null
) as begin
	insert into @table (ra, codMateria, dataChamada, qtdFaltas)
	select 
		a.ra as ra,
		mat.cod as codMateria,
		getDate() as dataChamada,
		0 as qtdFaltas
	from alunos a
		inner join matriculas matri on a.ra = matri.alunoRa
		inner join materias mat on matri.codMateria = mat.cod
	where mat.cod = @codMateria
	return
end

select distinct  * from geraChamada(3)

create function historico(@ra as varchar(10))
returns @table table (
	codDisci int not null,
	nomeDisci varchar(50) not null,
	nomeProf varchar(50) not null,
	notaFinal varchar(3) not null,
	qtdFalta int not null
) as begin
	insert into @table (codDisci, nomeDisci, nomeProf, notaFinal, qtdFalta)
	select 
		m.codDisci,
		d.nome,
		p.nome,
		CASE
		WHEN mm.situacao = 'dispensado' then 'D'
		WHEN m.tipoAvaliacao = 'tipo1' THEN cast(((av.nota1 * 0.3) + (av.nota2 * 0.5) + (av.nota3 * 0.2)) as varchar(3))
		WHEN m.tipoAvaliacao = 'tipo2' THEN cast(((av.nota1 * 0.35) + (av.nota2 * 0.35) + (av.nota3 * 0.3)) as varchar(3))
		WHEN m.tipoAvaliacao = 'tipo3' THEN cast(((av.nota1 * 0.33) + (av.nota2 * 0.33) + (av.nota3 * 0.3)) as varchar(3))
		ELSE cast(((av.nota1 * 0.8) + (av.nota2 * 0.2)) as varchar(3))
		END,
		sum(c.qtdFalta)
	from materias m
		inner join disciplinas d on m.codDisci = d.cod
		inner join professores p on m.codProf = p.cod
		inner join avaliacoes av on m.cod = av.codMateria
		inner join chamadas c on m.cod = c.codMateria
		inner join matriculas mm on m.cod = mm.codMateria
	where c.alunoRa = '202326135' and mm.situacao != 'cursando'
	group by m.codDisci, d.nome, p.nome, m.tipoAvaliacao, av.nota1, av.nota2, av.nota3, mm.situacao
	return
end

create function historico(@ra as varchar(10))
returns @table table (
	codDisci int not null,
	nomeDisci varchar(50) not null,
	nomeProf varchar(50) not null,
	notaFinal varchar(3) not null,
	qtdFalta int not null
) as begin
	insert into @table (codDisci, nomeDisci, nomeProf, notaFinal, qtdFalta)
	select 
		m.codDisci,
		d.nome,
		p.nome,
		CASE
		WHEN mm.situacao = 'dispensado' then 'D'
		WHEN m.tipoAvaliacao = 'tipo1' THEN cast(((av.nota1 * 0.3) + (av.nota2 * 0.5) + (av.nota3 * 0.2)) as varchar(3))
		WHEN m.tipoAvaliacao = 'tipo2' THEN cast(((av.nota1 * 0.35) + (av.nota2 * 0.35) + (av.nota3 * 0.3)) as varchar(3))
		WHEN m.tipoAvaliacao = 'tipo3' THEN cast(((av.nota1 * 0.33) + (av.nota2 * 0.33) + (av.nota3 * 0.3)) as varchar(3))
		ELSE cast(((av.nota1 * 0.8) + (av.nota2 * 0.2)) as varchar(3))
		END,
		sum(c.qtdFalta)
	from materias m
		inner join disciplinas d on m.codDisci = d.cod
		inner join professores p on m.codProf = p.cod
		inner join avaliacoes av on m.cod = av.codMateria
		inner join chamadas c on m.cod = c.codMateria
		inner join matriculas mm on m.cod = mm.codMateria
	where c.alunoRa = @ra and mm.situacao != 'cursando'
	group by m.codDisci, d.nome, p.nome, m.tipoAvaliacao, av.nota1, av.nota2, av.nota3, mm.situacao
	return
end

select * from historico('202326135')

create function historicoTurma(@codMateria as bigint)
returns @table table (
    codMateria bigint,
    turno varchar(20),
    nomeDisciplina varchar(100),
    tipoAvaliacao varchar(10),
    nota1 float,
    nota2 float,
    nota3 float,
    notaFinal varchar(3)
) as 
begin 
    declare @codMateriaCursor bigint,
            @turnoCursor varchar(20),
            @nomeDisciplinaCursor varchar(100),
            @tipoAvaliacaoCursor varchar(10),
            @nota1Cursor float,
            @nota2Cursor float,
            @nota3Cursor float,
            @notaFinalCursor varchar(3);

    -- Declaração do cursor
    declare cursorHistorico cursor for 
        select 
            m.cod,
            c.turno,
            d.nome,
            m.tipoAvaliacao,
            av.nota1,
            av.nota2,
            av.nota3,
            CASE
                WHEN mm.situacao = 'dispensado' then 'D'
                WHEN m.tipoAvaliacao = 'tipo1' THEN cast(((av.nota1 * 0.3) + (av.nota2 * 0.5) + (av.nota3 * 0.2)) as varchar(3))
                WHEN m.tipoAvaliacao = 'tipo2' THEN cast(((av.nota1 * 0.35) + (av.nota2 * 0.35) + (av.nota3 * 0.3)) as varchar(3))
                WHEN m.tipoAvaliacao = 'tipo3' THEN cast(((av.nota1 * 0.33) + (av.nota2 * 0.33) + (av.nota3 * 0.3)) as varchar(3))
                ELSE cast(((av.nota1 * 0.8) + (av.nota2 * 0.2)) as varchar(3))
            END
        from materias m
            inner join avaliacoes av on av.codMateria = m.cod
            inner join disciplinas d on d.cod = m.codDisci
            inner join cursos c on d.codCurso = c.cod
            inner join matriculas mm on mm.codMateria = m.cod
        where m.cod = @codMateria;

    -- Abertura do cursor
    open cursorHistorico;

    -- Leitura do primeiro registro
    fetch next from cursorHistorico into @codMateriaCursor, @turnoCursor, @nomeDisciplinaCursor, @tipoAvaliacaoCursor, @nota1Cursor, @nota2Cursor, @nota3Cursor, @notaFinalCursor;

    -- Loop para processar os registros
    while @@FETCH_STATUS = 0
    begin
        -- Inserção na tabela de retorno
        insert into @table(codMateria, turno, nomeDisciplina, tipoAvaliacao, nota1, nota2, nota3, notaFinal)
        values (@codMateriaCursor, @turnoCursor, @nomeDisciplinaCursor, @tipoAvaliacaoCursor, @nota1Cursor, @nota2Cursor, @nota3Cursor, @notaFinalCursor);

        -- Leitura do próximo registro
        fetch next from cursorHistorico into @codMateriaCursor, @turnoCursor, @nomeDisciplinaCursor, @tipoAvaliacaoCursor, @nota1Cursor, @nota2Cursor, @nota3Cursor, @notaFinalCursor;
    end

    -- Fechamento do cursor
    close cursorHistorico;
    deallocate cursorHistorico;

    return
end

select * from historicoTurma(1)

create alter function historicoTurmaChamada(@codMateria as bigint)
returns @table table (
    codMateria INT,
    nomeDisciplina VARCHAR(100),
    professor VARCHAR(100),
    tipoAvalicao VARCHAR(30),
    notaFinal VARCHAR(3),
    totalFaltas INT,
	porcentagem float,
    situacao VARCHAR(50)
) as 
begin
    declare @codMateriaCursor INT,
            @nomeDisciplinaCursor VARCHAR(100),
            @professorCursor VARCHAR(100),
            @tipoAvaliacaoCursor VARCHAR(30),
            @notaFinalCursor VARCHAR(3),
            @totalFaltasCursor INT,
			@porcentagem float,
            @situacaoCursor VARCHAR(50);

    -- Declaração do cursor
    declare cursorHistorico cursor for 
        select
            m.cod,
            d.nome,
            p.nome,
            m.tipoAvaliacao,
            CASE
                WHEN mm.situacao = 'dispensado' then 'D'
                WHEN m.tipoAvaliacao = 'tipo1' THEN cast(((av.nota1 * 0.3) + (av.nota2 * 0.5) + (av.nota3 * 0.2)) as varchar(3))
                WHEN m.tipoAvaliacao = 'tipo2' THEN cast(((av.nota1 * 0.35) + (av.nota2 * 0.35) + (av.nota3 * 0.3)) as varchar(3))
                WHEN m.tipoAvaliacao = 'tipo3' THEN cast(((av.nota1 * 0.33) + (av.nota2 * 0.33) + (av.nota3 * 0.3)) as varchar(3))
                ELSE cast(((av.nota1 * 0.8) + (av.nota2 * 0.2)) as varchar(3)) 
            END as mediaFinal,
            sum(c.qtdFalta) as totalFalta,
			cast(sum(c.qtdFalta) as float) / cast(COUNT(c.qtdFalta) * d.qtdAulas as float) * 100 as Porcentagem,
            CASE WHEN ((COUNT(c.qtdFalta) * d.qtdAulas) * 0.75) + SUM(c.qtdFalta) > (COUNT(c.qtdFalta) * d.qtdAulas) THEN 
                'Reprovado por faltas'
            ELSE 
                'Aprovado'
            END as situacao
        from materias m
            inner join disciplinas d on m.codDisci = d.cod
            inner join professores p on m.codProf = p.cod
            inner join avaliacoes av on m.cod = av.codMateria
            inner join matriculas mm on m.cod = mm.codMateria
            inner join chamadas c on m.cod = c.codMateria
        where m.cod = 1
        group by m.cod, d.nome, p.nome, m.tipoAvaliacao, mm.situacao, av.nota1, av.nota2, av.nota3, d.qtdAulas

		
    -- Abertura do cursor
    open cursorHistorico;

    -- Leitura do primeiro registro
    fetch next from cursorHistorico into @codMateriaCursor, @nomeDisciplinaCursor, @professorCursor, @tipoAvaliacaoCursor, @notaFinalCursor, @totalFaltasCursor, @porcentagem, @situacaoCursor;

    -- Loop para processar os registros
    while @@FETCH_STATUS = 0
    begin
        -- Inserção na tabela de retorno
        insert into @table(codMateria, nomeDisciplina, professor, tipoAvalicao, notaFinal, totalFaltas, porcentagem, situacao)
        values (@codMateriaCursor, @nomeDisciplinaCursor, @professorCursor, @tipoAvaliacaoCursor, @notaFinalCursor, @totalFaltasCursor, @porcentagem, @situacaoCursor);

        -- Leitura do próximo registro
        fetch next from cursorHistorico into @codMateriaCursor, @nomeDisciplinaCursor, @professorCursor, @tipoAvaliacaoCursor, @notaFinalCursor, @totalFaltasCursor, @porcentagem, @situacaoCursor;
    end

    -- Fechamento do cursor
    close cursorHistorico;
    deallocate cursorHistorico;

    return
end

select * from historicoTurmaChamada(1)