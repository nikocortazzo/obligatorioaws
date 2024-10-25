DROP DATABASE IF EXISTS ObligatorioAWS;

CREATE DATABASE ObligatorioAWS;

USE ObligatorioAWS;

CREATE TABLE Usuario (
nombreusuario varchar(50) primary key,
clave varchar(50),
correo varchar(50)
);

CREATE TABLE Cliente (
foreign key (nombreusuario) references Usuario (nombreusuario),
cedula bigint,
domicilio varchar(50),
telefono varchar(50),
clave varchar(50),
correo varchar(50)
);

CREATE TABLE Sucursal (
numero bigint primary key,
nombresucursal varchar(50)
);

CREATE TABLE Empleado (
foreign key (nombreusuario) references Usuario (nombreusuario),
foreign key (numero) references Sucursal (numero),
clave varchar(50),
correo varchar(50)
);

CREATE TABLE Categoria (
idcat int primary key AUTO_INCREMENT,
nombre varchar(50)
);

CREATE TABLE EstadoRastreo (
idestado int primary key AUTO_INCREMENT,
descripcion varchar(100)
);

CREATE TABLE Paquete (
idpaquete int primary key AUTO_INCREMENT,
fechahregistro datetime, 
nombredes varchar(50),
direcciondes varchar(50),
cobroades bit,
teldestinatario varchar(50),
foreign key (idcat) references Categoria (idcat),
foreign key (idestado) references EstadoRastreo (idestado)
);
