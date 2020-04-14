------------------------------------------------------------------------------------------

create procedure registrarCliente
@usuario varchar(50),
@tipoUser varchar(3),
@clave varchar(30),
@cedula varchar(2), 
@nombre varchar(50), 
@apellido varchar(50),
@provincia varchar(3), 
@email varchar(62),
@numero varchar(22)
as
begin
begin tran
insert into Users
(
	usuario,
	idTipoUser,
	clave
)
values
(
	@usuario, 
	convert(smallint, @tipoUser), 
	@clave
)

insert into Persona
(
	cedula,
	nombre,
	apellido,
	idProvincia,
	numero,
	usuario,
	autorizado,
	email
)
values 
(
	@cedula,
	@nombre,
	@apellido,
	convert(int, @provincia),
	@numero, 
	@usuario,
	1, 
	@email
)
update Users set idPersona = SCOPE_IDENTITY() where Users.usuario = @usuario
declare @tempIdPersona bigint;
set @tempIdPersona = SCOPE_IDENTITY();
insert into Cliente values (SCOPE_IDENTITY())
update Persona set idCliente = SCOPE_IDENTITY() where Persona.idPersona = @tempIdPersona
commit
end


------------------------------------------------------------------------------------------

create procedure registrarVendedor
@usuario varchar(50),
@tipoUser varchar(3),
@clave varchar(30),
@cedula varchar(22), 
@nombre varchar(50), 
@apellido varchar(50),
@provincia varchar(3), 
@email varchar(62),
@numero varchar(22),
@tipoVendedor varchar(2)
as
begin tran
insert into Users
(
	usuario,
	idTipoUser,
	clave
)
values
(
	@usuario, 
	convert(smallint, @tipoUser), 
	@clave
)

insert into Persona
(
	cedula,
	nombre,
	apellido,
	idProvincia,
	numero,
	usuario,
	autorizado,
	email
)
values 
(
	@cedula,
	@nombre,
	@apellido,
	convert(int, @provincia),
	@numero, 
	@usuario,
	1, 
	@email
)
update Users set idPersona = SCOPE_IDENTITY() where Users.usuario = @usuario
declare @tempIdPersona bigint;
set @tempIdPersona = SCOPE_IDENTITY();

insert into Vendedor
(
	idTipoVendedor,
	idPersona
)
values 
(
	convert(smallint, @tipoVendedor), 
	SCOPE_IDENTITY()
)


update Persona set idVendedor = SCOPE_IDENTITY() where Persona.idPersona = @tempIdPersona
commit

------------------------------------------------------------------------------------------
--Esta no fue necesaria para la implementacion con el programa
create function buscarClienteByCedula(@cedula varchar(11))
returns bigint
begin
	declare @ret bigint
	select @ret = c.idCliente
	from Cliente c
	where c.cedula = @cedula

	IF (@ret IS NULL)   
        SET @ret = 0;  
    RETURN @ret;  
end


--------------------------------------------------------------------------------------------------------------------------------------------

create procedure registrarVehiculo
@estado varchar(50),
@anio int,
@idMarca bigint,
@idModelo bigint,
@idTipoVehiculo smallint,
@descripcion varchar(120),
@idVendedor bigint
as
begin tran
insert into Vehiculo
(
	estado,
	anio,
	idMarca,
	idModelo,
	idTipoVehiculo,
	descripcion,
	idVendedor,
	vendido
)
values
(
	@estado,
	@anio,
	@idMarca,
	@idModelo,
	@idTipoVehiculo,
	@descripcion,
	@idVendedor,
	0
)
commit

--------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------
create function ListarVehiculosVendedor(@idVendedor bigint)
returns table
as
return
(
	select v.idVehiculo as 'Código del Vehiculo'
	, m.nombre as 'Marca', mo.nombre as 'Modelo', v.anio as 'Año',
	t.descripcion as 'Tipo de Vehículo', v.descripcion as 'Descripción'
	from Vehiculo as v join Marca as m
	on m.idMarca = v.idMarca
	join Modelo as mo
	on mo.idModelo = v.idModelo
	join  TipoVehiculo as t
	on t.idTipoVehiculo = v.idTipoVehiculo
	where v.idVendedor = @idVendedor and v.vendido = 0
)

-----------------------------------------------------------------------------------------------------------------

create procedure crearAnuncio
@descripcion varchar(120),
@fechaInicio varchar(40),
@fechaFin varchar(40),
@idVendedor bigint,
@idVehiculo bigint,
@precioVehiculo varchar(40)
as
begin tran
declare @days int
set @days = DATEDIFF(DAY, convert(datetime, @fechaInicio), convert(datetime, @fechaFin))
insert into Anuncio
(
	descripcion,
	costo,
	fechaInicio,
	fechaFin,
	idVendedor,
	idVehiculo,
	precioVehiculo,
	autorizado,
	comprado
)
values
(
	@descripcion,
	convert(money, 1200 * @days),
	convert(datetime,@fechaInicio),
	convert(datetime, @fechaFin),
	@idVendedor,
	@idVehiculo,
	convert(money, @precioVehiculo),
	0,
	0
)
commit

-----------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------
create procedure autorizarAnuncio
@idAnuncio bigint
as
begin tran
	update Anuncio set autorizado = 1 where idAnuncio = @idAnuncio
	declare @idvehiculo bigint
	select @idvehiculo = idvehiculo from anuncio where idAnuncio = @idAnuncio
	update Vehiculo set idAnuncio = @idAnuncio where idVehiculo = @idVehiculo
commit
--------------------------------------------------------------------------------------------------------------------------------------------
create procedure autorizarPersona
@idPersona bigint
as
begin tran
	update Persona set autorizado = 1 where idPersona = @idPersona
commit
--------------------------------------------------------------------------------------------------------------------------------------------
create procedure ListarVehiculosEnVenta
as
	select a.idAnuncio as 'Código del Anuncio', v.idVehiculo as 'Código del Vehiculo', 
	m.nombre as 'Marca', mo.nombre as 'Modelo', v.anio as 'Año',
	t.descripcion as 'Tipo de Vehículo', v.descripcion as 'Descripción',
	pers.nombre + ' ' + pers.apellido as 'Vendedor', a.precioVehiculo as 'Precio del Vehículo'
	from Vehiculo as v join Marca as m
	on m.idMarca = v.idMarca
	join Modelo as mo
	on mo.idModelo = v.idModelo
	join  TipoVehiculo as t
	on t.idTipoVehiculo = v.idTipoVehiculo
	join Anuncio as a
	on a.idVehiculo = v.idVehiculo
	join Persona as pers
	on pers.idVendedor = a.idVendedor
	where a.autorizado = 1 and a.comprado = 0
	order by a.fechaInicio
	
--------------------------------------------------------------------------------------------------------------------------------------------
create function consultaVendedores()
returns table
as
return
(
	select p.idPersona as 'ID de Registro', p.cedula as 'Cédula', p.nombre + ' ' + p.apellido as 'Vendedor', t.descripcion as 'Tipo', count(a.idAnuncio) as 'Cantidad de Anuncios'
	from Persona as p join Vendedor as v
	on p.idPersona = v.idPersona
	join TipoVendedor as t
	on t.idTipoVendedor = v.idTipoVendedor
	join Anuncio as a
	on a.idVendedor = v.idVendedor
	where a.autorizado = 1 and a.comprado = 0
	group by p.idPersona, p.cedula, p.nombre, p.apellido, t.descripcion
)
--------------------------------------------------------------------------------------------------------------------------------------------
create function listarClientes()
returns table
as
return
(
	select p.idPersona as 'ID de Registro', p.cedula as 'Cédula', p.idCliente as 'Código como cliente', 
	p.nombre + ' ' + p.apellido as 'Cliente', p.numero as 'Número', p.email as 'E-mail'
	from Persona as p join Cliente as c
	on p.idPersona = c.idPersona
)
--------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------
create function listarPublicaciones()
returns table
as
return
(
	select idAnuncio as 'Código', costo as 'Costo', datediff(day,fechaInicio,fechaFin) as 'Días Activos Restantes', autorizado as 'Válido'
	from Anuncio
	where comprado = 0
)
--------------------------------------------------------------------------------------------------------------------------------------------
create function vendedoresMayorVentas()
returns table
as
return
(
	select top 10 ven.idVendedor as 'ID Vendedor', pVen.nombre +' ' + pVen.apellido as 'Vendedor', count(all c.idVehiculo) as 'Número de Ventas'
	from ClienteCompraVehiculo c join Cliente cli
	on cli.idCliente = c.idCliente
	join Vehiculo v
	on v.idVehiculo = c.idVehiculo
	join Vendedor ven
	on ven.idVendedor = v.idVendedor
	join Persona pVen
	on pVen.idPersona = ven.idPersona
	group by ven.idVendedor, pVen.nombre, pVen.apellido
	order by count(c.idvehiculo) desc
)

-----------------------------------------------------------------------------------------------------------------------------------------
create procedure comprarVehiculo
@idCliente bigint,
@idVehiculo bigint,
@idAnuncio bigint
as
begin tran
declare @precioVehi money
select @precioVehi = precioVehiculo from Anuncio where idAnuncio = @idAnuncio
insert into ClienteCompraVehiculo
(
	idCliente,
	idVehiculo,
	fechaCompra,
	costoCompra
)
values
(
	@idCliente,
	@idVehiculo,
	GETDATE(),
	@precioVehi
)
update Anuncio set comprado = 1 where idAnuncio = @idAnuncio
update Vehiculo set vendido = 1 where idVehiculo = @idVehiculo
commit

--------------------------------------------------------------------------------------------------------------------------------------------

create function comprasCliente(@idCliente bigint)
returns table
as
return
(
	select m.nombre as 'Marca', mo.nombre as 'Modelo', v.anio as 'Año', com.costoCompra as 'Costo', com.fechaCompra as 'Fecha de Compra'
	from Cliente c join Persona p
	on  c.idPersona = p.idPersona
	join ClienteCompraVehiculo com
	on com.idCliente = c.idCliente
	join Vehiculo as v
	on v.idVehiculo = com.idVehiculo
	join Marca m
	on m.idMarca = v.idMarca
	join Modelo as mo
	on mo.idModelo = v.idModelo
	where com.idCliente = @idCliente
	group by m.nombre, mo.nombre, v.anio, com.costoCompra, com.fechaCompra
)

-----------------------------------------------------------------------------------------------
create procedure eliminarAnuncio
@idAnuncio bigint
as
begin tran
update Vehiculo
set idAnuncio = null
where idAnuncio = @idAnuncio
delete from Anuncio
where idAnuncio = @idAnuncio
commit
-------------------------------------------------------------------------------------------------------------
create function [dbo].[calcularCosto](@fechaInicio datetime, @fechaFin datetime)
returns money
as
begin
	declare @days int
	set @days = DATEDIFF(DAY, convert(datetime, @fechaInicio), convert(datetime, @fechaFin))
	return convert(money,@days*1200)
end
----------------------------------------------------------------------------------------------------------------
create procedure autorizarAnuncio
@idAnuncio bigint
as
begin tran
	update Anuncio set autorizado = 1 where idAnuncio = @idAnuncio
	declare @idvehiculo bigint
	select @idvehiculo = idvehiculo from anuncio where idAnuncio = @idAnuncio
	update Vehiculo set idAnuncio = @idAnuncio where idVehiculo = @idVehiculo
commit

-----------------------------------------------------------------------------------------------------

create function mostrarVehiculosPorMarca(@idMarca bigint)
returns table
as
return
(
	select v.idVehiculo as 'Código Vehículo', ma.nombre as 'Marca', mo.nombre as 'Modelo', v.anio as 'Año',
	v.idVendedor as 'ID del Dueño'
	from Vehiculo as v join Marca as ma
	on v.idMarca = ma.idMarca
	join Modelo as mo
	on v.idModelo = mo.idModelo
	join Anuncio as anu
	on anu.idVehiculo = v.idVehiculo
	where ma.idMarca = @idMarca and comprado = 0
)

-----------------------------------------------------------------------------------------------------

create function consultaVentaPorAnioMesMarcaCiudad()
returns table
as
return
(
	select c.idCliente as 'ID Cliente', ven.idVendedor as 'ID Vendedor', c.idVehiculo as 'ID Vehículo', 
	DATEPART(year, c.fechaCompra) as 'Año', DATEPART(MONTH, c.fechaCompra) as 'Mes', 
	ma.nombre as 'Marca', provi.nombre as 'Provincia'
	from ClienteCompraVehiculo as c join Cliente as cli
	on c.idCliente = cli.idCliente
	join Vehiculo as v
	on v.idVehiculo = c.idVehiculo
	join Marca as ma
	on ma.idMarca = v.idMarca
	join Vendedor as ven
	on ven.idVendedor = v.idVendedor
	join Persona as p
	on p.idPersona = ven.idPersona
	join Provincias as provi
	on provi.idProvincia = p.idProvincia
	group by c.idCliente, c.idVehiculo, c.fechaCompra, ma.nombre, provi.nombre, ven.idVendedor
)


------------------------------------------------------------------------------------------------------

create function ventasVendedor(@idVendedor bigint)
returns table
as
return
(
	select c.idVehiculo as 'ID Vehículo', 
	DATEPART(year, c.fechaCompra) as 'Año', DATEPART(MONTH, c.fechaCompra) as 'Mes', 
	ma.nombre as 'Marca', pro.nombre as 'Provincia'
	from ClienteCompraVehiculo as c join Vehiculo as v
	on c.idVehiculo = v.idVehiculo
	join Vendedor as seller
	on seller.idVendedor = v.idVendedor
	join Marca as ma
	on ma.idMarca = v.idMarca
	join Modelo as mo
	on mo.idModelo = v.idModelo
	join Persona as p
	on p.idPersona = seller.idPersona
	join Cliente as cli
	on cli.idCliente = c.idCliente
	join Provincias as pro
	on pro.idProvincia = p.idProvincia
	where seller.idVendedor = @idVendedor
	group by seller.idVendedor, c.idVehiculo, c.fechaCompra, ma.nombre, pro.nombre
)
------------------------------------------------------------------------------------------------------
