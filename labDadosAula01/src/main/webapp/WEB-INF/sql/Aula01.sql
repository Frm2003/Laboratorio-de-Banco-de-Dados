create database aula01
go
use aula01
 
create table Motorista(
	codigo int not null,
	nome varchar(100) not null,
	naturalidade varchar(50) not null,
 
	primary key (codigo)
)
create table Onibus(
	placa varchar(7) not null,
	marca varchar(10) not null,
	ano int not null,
	descricao varchar(100) not null,
 
	primary key (placa)
)
create table Viagem(
	codigo int not null,
	onibus varchar(7) not null,
	foreign key (onibus) references onibus(placa),
	motorista int not null,
	foreign key (motorista) references Motorista(codigo),
	horaDeSaida int not null check(horaDeSaida >= 0),
	horaDeChegada int not null check(horaDeChegada >= 0),
	partida varchar(50) not null,
	destino varchar(50) not null,

	primary key (codigo)
)
 
select * from Motorista
select * from Onibus
select * from Viagem

select m.naturalidade as natMot, m.nome as nomeMot,
		v.codigo as codV, v.onibus as onibusV, v.motorista as motV, v.horaDeSaida as horaSaida, v.horaDeChegada as horaChegada, v.partida as partida, v.destino as destino,
		o.marca as marca, o.ano as ano, o.descricao as descr
from Motorista m inner join Viagem v on m.codigo = v.motorista
		inner join Onibus o on v.onibus = o.placa 


--1) Criar um Union das tabelas Motorista e ônibus, com as colunas ID (Código e Placa) e Nome (Nome e Marca)
select cast(m.codigo as varchar(10)) as codigo, m.nome as nome, 'Motorista' as tipo
from Motorista m
union
select o.placa as codigo, o.marca as nome, 'Onibus' as tipo
from Onibus o
 
--2) Criar uma View (Chamada v_motorista_onibus) do Union acima
create view v_motorista_onibus
as 
select cast(m.codigo as varchar(10)) as codigo, m.nome as nome, 'Motorista' as tipo
from Motorista m
union
select o.placa as codigo, o.marca as nome, 'Onibus' as tipo
from Onibus o
 
select * from v_motorista_onibus
 
--3) Criar uma View (Chamada v_descricao_onibus) que mostre o Código da Viagem, o Nome do motorista, a placa do ônibus (Formato XXX-0000), a Marca do ônibus, o Ano do ônibus e a descrição do onibus
create view v_descricao_onibus
as 
select v.codigo, m.nome, SUBSTRING(o.placa, 1, 3) + '-' + SUBSTRING(o.placa, 4, 7) as placa, o.ano, o.descricao
from Onibus o inner join viagem v on o.placa = v.onibus
		inner join Motorista m on v.motorista = m.codigo
 
select * from v_descricao_onibus
 
--4) Criar uma View (Chamada v_descricao_viagem) que mostre o Código da viagem, a placa do ônibus(Formato XXX-0000), a Hora da Saída da viagem (Formato HH:00), 
--a Hora da Chegada da viagem (Formato HH:00), partida e destino
create view v_descricao_viagem
as 
select v.codigo, 
	SUBSTRING(o.placa, 1, 3) + '-' + SUBSTRING(o.placa, 4, 7) as placa,
	case when (horaDeSaida < 10) then
		'0' + cast(v.horaDeSaida as varchar(2)) + ':00'
	else 
		cast(v.horaDeSaida as varchar(2)) + ':00'
	end as horaDeSaida,
	case when (v.horaDeChegada < 10) then
		'0' + cast(v.horaDeChegada as varchar(2)) + ':00'
	else 
		cast(v.horaDeChegada as varchar(2)) + ':00'
	end as horaDeChegada,
	v.partida,
	v.destino
from viagem v inner join onibus o on v.onibus = o.placa
 
select * from v_descricao_viagem