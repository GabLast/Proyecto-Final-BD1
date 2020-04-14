use VentaVehiculos
go

create table TipoUser
(
idTipoUser smallint identity(1,1) primary key,
descripcion varchar(20) not null
)

begin tran
insert into TipoUser values ('Administrador')
insert into TipoUser values ('Vendedor')
insert into TipoUser values ('Cliente')
commit

select * from TipoUser


create table Provincias
(
idProvincia int primary key,
nombre varchar(45) not null
)

begin tran
INSERT INTO Provincias VALUES
    (1,'Azua')
    ,(2,'Bahoruco')
    ,(3,'Barahona')
    ,(4,'Dajabón')
    ,(5,'Distrito Nacional')
    ,(6,'Duarte')
    ,(7,'Elías Piña')
    ,(8,'El Seibo')
    ,(9,'Espaillat')
    ,(10,'Hato Mayor')
    ,(11,'Hermanas Mirabal')
    ,(12,'Independencia')
    ,(13,'La Altagracia')
    ,(14,'La Romana')
    ,(15,'La Vega')
    ,(16,'María Trinidad Sánchez')
    ,(17,'Monseñor Nouel')
    ,(18,'Monte Cristi')
    ,(19,'Monte Plata')
    ,(20,'Pedernales')
    ,(21,'Peravia')
    ,(22,'Puerto Plata')
    ,(23,'Samaná')
    ,(24,'Sánchez Ramírez')
    ,(25,'San Cristóbal')
    ,(26,'San José de Ocoa')
    ,(27,'San Juan')
    ,(28,'San Pedro de Macorís')
    ,(29,'Santiago')
    ,(30,'Santiago Rodríguez')
    ,(31,'Santo Domingo')
    ,(32,'Valverde')
commit

create table Users
(
usuario varchar(32) primary key,
idTipoUser smallint foreign key references TipoUser(idTipoUser),
clave varchar(40) not null
)

create table Cliente
(
idCliente bigint identity(1,1) primary key
)

create table TipoVendedor
(
idTipoVendedor smallint identity(1,1) primary key,
descripcion varchar(20) not null
)

begin tran
insert into TipoVendedor values('Dealer')
insert into TipoVendedor values('Particular')
commit

create table Vendedor
(
idVendedor bigint identity(1,1) primary key,
idTipoVendedor smallint foreign key references TipoVendedor(idTipoVendedor)
)

create table Persona
(
idPersona bigint identity(1,1) primary key,
cedula varchar(11),
nombre varchar(25),
apellido varchar(30),
idProvincia int foreign key references Provincias(idProvincia) not null,
numero varchar(10) not null,
usuario varchar(32) foreign key references Users(usuario) not null,
idVendedor bigint foreign key references Vendedor(idVendedor) null,
idCliente bigint foreign key references Cliente(idCliente) null,
autorizado smallint,
email varchar(62)
)

alter table Persona
add unique (cedula)

alter table Users
add idPersona bigint

alter table Users with check
add constraint fkIdPers
foreign key (idPersona) references Persona(idPersona)

alter table Cliente
add idPersona bigint

alter table Cliente with check
add constraint fkClienteCedula
foreign key (idPersona) references Persona(idPersona)

alter table Vendedor
add idPersona bigint

alter table Vendedor with check
add constraint fkVendedorCedula
foreign key (idPersona) references Persona(idPersona)

create table Anuncio
(
idAnuncio bigint identity(1,1) primary key,
decripcion varchar(70),
costo money not null,
fechaInicio datetime not null,
fechaFin datetime not null,
autorizado smallint not null,
idVendedor bigint foreign key references Vendedor(idVendedor) not null,
idVehiculo bigint
)

create table Modelo
(
idModelo bigint identity(1,1) primary key,
nombre varchar(32) not null
)

create table TipoVehiculo
(
idTipoVehiculo smallint identity(1,1) primary key,
descripcion varchar(32) not null
)

begin tran
insert into TipoVehiculo values ('Sedán')
insert into TipoVehiculo values ('Motor')
insert into TipoVehiculo values ('Compacto')
insert into TipoVehiculo values ('Camión')
insert into TipoVehiculo values ('Jeepeta')
insert into TipoVehiculo values ('Barco')
insert into TipoVehiculo values ('Camioneta')
insert into TipoVehiculo values ('Autobus')
insert into TipoVehiculo values ('Coupé')
insert into TipoVehiculo values ('Vehículo Pesado')
insert into TipoVehiculo values ('Otro')
commit

create table Marca
(
idMarca bigint identity(1,1) primary key,
idModelo bigint foreign key references Modelo(idModelo) null
)

create table Vehiculo
(
idVehiculo bigint identity(1,1) primary key,
estado varchar(20),
anio int not null,
idMarca bigint foreign key references Marca(idMarca),
idModelo bigint foreign key references Modelo(idModelo),
idTipoVehiculo smallint foreign key references TipoVehiculo(idTipoVehiculo),
idAnuncio bigint foreign key references Anuncio(idAnuncio) null
)

Create table ClienteCompraVehiculo
(
idCliente bigint not null,
idVehiculo bigint not null,
fechaCompra datetime not null

constraint primarykeys primary key clustered
(
idCliente,
idVehiculo
)
)

alter table ClienteCompraVehiculo with check
add constraint addingFks1
foreign key (idCliente)
references Cliente(idCliente)

alter table ClienteCompraVehiculo with check
add constraint addingFks2
foreign key (idVehiculo)
references Vehiculo(idVehiculo)

alter table marca
add nombre varchar(50)

alter table marca
add constraint unName
unique (nombre)

--DBCC CHECKIDENT ('Marca', RESEED, 25)  

begin tran
insert into Marca values(null, 'Acura')
insert into Marca values(null, 'Alfa Romeo')
insert into Marca values(null, 'Audi')
insert into Marca values(null, 'BMW')
insert into Marca values(null, 'Cadillac')
insert into Marca values(null, 'Chevrolet')
insert into Marca values(null, 'Daihatsu')
insert into Marca values(null, 'Ferrari')
insert into Marca values(null, 'Ford')
insert into Marca values(null, 'Freightliner')
insert into Marca values(null, 'GMC')
insert into Marca values(null, 'Honda')
insert into Marca values(null, 'HUMMER')
insert into Marca values(null, 'Hyundai')
insert into Marca values(null, 'Inifnity')
insert into Marca values(null, 'Isuzu')
insert into Marca values(null, 'Jaguar')
insert into Marca values(null, 'Jeep')
insert into Marca values(null, 'Kia')
insert into Marca values(null, 'Lincoln')
insert into Marca values(null, 'Mitsubishi')
insert into Marca values(null, 'Mercedes-Benz')
insert into Marca values(null, 'Nissan')
insert into Marca values(null, 'Porsche')
commit

alter table modelo
add idMarca bigint

alter table modelo
add constraint fkMarca
foreign key (idMarca) references Marca(idMarca)

alter table vehiculo
add constraint fkModel
foreign key (idModelo) references Modelo(idModelo)


select * from Modelo
select idmarca from marca where nombre = 'ford'

begin tran
insert into Modelo values('Explorer', 10)
insert into Modelo values('Escape', 10)
commit


begin tran
insert into Modelo values('Giulia', 2)
insert into Modelo values('Stelvio', 2)
commit

begin tran
insert into Modelo values('A3', 3)
insert into Modelo values('S3', 3)
commit

begin tran
insert into Modelo values('BMW325', 5)
insert into Modelo values('Z8', 5)
insert into Modelo values('740IL', 5)
insert into Modelo values('CADOTH', 6)
insert into Modelo values('Traverse', 7)
insert into Modelo values('Tahoe', 7)
insert into Modelo values('Daeoth', 8)
insert into Modelo values('Rocky', 8)
commit



alter table vehiculo
add autorizado varchar(62)

alter table modelo
add constraint nombreUnique
unique (nombre)

select * from Vehiculo
select * from Anuncio
select nombre from Marca

select descripcion from TipoVehiculo

select mo.idModelo, mo.nombre, ma.nombre from Modelo mo join Marca ma on mo.idMarca = ma.idMarca where ma.idMarca = 1

select mo.idModelo, mo.nombre, ma.nombre from Modelo mo join Marca ma on mo.idMarca = ma.idMarca where mo.nombre = 'escape'

alter table vehiculo
add idVendedor bigint

alter table vehiculo
add constraint idVendedorFK
foreign key (idVendedor) references Vendedor(idVendedor)

alter table Anuncio
add precioVehiculo money

use VentaVehiculos
select * from TipoUser
select * from Users
select * from Persona

alter table persona
alter column cedula varchar(15)

alter table anuncio
add comprado smallint

alter table persona
add unique (cedula)


alter table Persona
alter column cedula varchar(15) not null

select * from Persona
begin tran
update Persona
set cedula = '01249z'
where idPersona = 34
commit

alter table Vehiculo
alter column vendido smallint not null