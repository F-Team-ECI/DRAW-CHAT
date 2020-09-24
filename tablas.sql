create table Usuario(
	telefono varchar(250) primary key,
	nombre varchar(250),
	apellido varchar(250),
	contraseña varchar(250),
	fechaRegistro date,
	fechaConexion date,
	estado varchar(250)
);

create table Contacto(
	propietario varchar(250),
	dirigido varchar(250),
	primary key(propietario, dirigido),
	foreign key(propietario) references usuario (telefono),
	foreign key(dirigido) references usuario (telefono)
);


create table Chat(
	usuario1 varchar(205),
	usuario2 varchar(250),
	tipo varchar(250),
	primary key(usuario1, usuario2),
	foreign key(usuario1) references Usuario (telefono),
	foreign key(usuario2) references Usuario (telefono)
);

create table Grupo(
	nombre varchar(250) primary key,
	lema varchar(250),
	fechaCreacion date
);

create table GruposUsuario(
	usuario varchar(250),
	grupo varchar(250),
	rol varchar(250),
	primary key (usuario, grupo),
	foreign key(usuario) references Usuario (telefono),
	foreign key(grupo) references Grupo (nombre)
);

create table Permiso(
	nombre varchar(250),
	usuario varchar(250),
	grupo varchar(250),
	estado boolean,
	descripcion varchar(250),
	primary key(nombre, usuario, grupo),
	foreign key(usuario, grupo) references gruposusuario (usuario, grupo)
	/* foreign key(grupo) references gruposusuario (grupo) */
);

create table SesionTablero(
	id int primary key,
	grupo varchar(250),
	fecha date,
	foreign key(grupo) references Grupo (nombre)
	
);

create table mensaje(
	id int,
	chat int,
	emisor varchar(250),
	grupo varchar(250),
	usuario1 varchar(250),
	usuario2 varchar(250),
	contenido varchar(250),
	fecha date,
	primary key(id),
	foreign key(usuario1, usuario2) references chat (usuario1, usuario2),
	foreign key(grupo) references grupo (nombre),
	foreign key(emisor) references usuario (telefono)
);