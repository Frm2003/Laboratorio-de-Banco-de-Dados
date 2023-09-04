use aula02

create table Cliente(
	cpf varchar(11) not null,
	nome varchar(100) not null,
	email varchar(200) not null,
	limiteCred decimal(7, 2) not null,
	dataNasc date not null,

	primary key(cpf)
)

select * from Cliente
delete Cliente

alter procedure crudCliente(@acao as char(1), @cpf as varchar(11), @nome as varchar(100), @email as varchar(200), @limiteCred as decimal(7, 2), @dataNasc as varchar(10), @saida varchar(100) output)
as
	declare @valido bit
	exec validarCPF @cpf, @valido output
	
	if (@valido = 1) begin
		if (lower(@acao) = 'i') begin
			insert into Cliente values(@cpf, @nome, @email, @limiteCred, @dataNasc)
			set @saida = 'cliente criado'
		end
		if (lower(@acao) = 'u') begin
			update Cliente set nome = @nome, email = @email, limiteCred = @limiteCred, dataNasc = @dataNasc where cpf= @cpf
			set @saida = 'cliente atualizado'
		end
		if (lower(@acao) = 'r') begin
			delete Cliente where cpf = @cpf
			set @saida = 'cliente deletado'
		end
	end else begin
		raiserror('CPF inválido', 16, 1)
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
			set @saida = 'válida'
		end else begin
			set @saida = 'inválida'
		end
	end else begin
		if (SUBSTRING(@cpf, @pos, 1) = '0') begin
			set @saida = 'válida'
		end else begin
			set @saida = 'inválida'
		end
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

		if (@saida = 'válida') begin
			set @restDiv = 0
	
			exec calculoCpf @cpf, 11, @restDiv output
			exec validaDigitoCpf @cpf, @restDiv, 11, @saida output

			if (@saida = 'válida') begin
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

	