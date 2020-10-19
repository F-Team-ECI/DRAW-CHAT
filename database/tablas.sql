create table Usuario(
	nombre varchar(250),
	telefono BIGINT primary key,
	apellido varchar(250),
	contraseña varchar(250),
	fechaRegistro TIMESTAMP,
	fechaConexion TIMESTAMP,
	estado varchar(250)
);

create table Contacto(
	propietario BIGINT,
	dirigido BIGINT,
	primary key(propietario, dirigido),
	foreign key(propietario) references usuario (telefono),
	foreign key(dirigido) references usuario (telefono)
);

create table Chat(
	id int,
	usuario1 BIGINT,
	usuario2 BIGINT,
	tipo varchar(250),
	primary key(id),
	foreign key(usuario1) references Usuario (telefono),
	foreign key(usuario2) references Usuario (telefono)
);

create table Grupo(
	id int primary key,
	nombre varchar(250),
	lema varchar(250),
	fechaCreacion TIMESTAMP
);

create table GruposUsuario(
	usuario BIGINT,
	grupo int,
	rol varchar(250),
	primary key (usuario, grupo),
	foreign key(usuario) references Usuario (telefono),
	foreign key(grupo) references Grupo (id)
);

create table Permiso(
	nombre varchar(250),
	usuario BIGINT,
	grupo int,
	estado boolean,
	descripcion varchar(250),
	primary key(nombre, usuario, grupo),
	foreign key(usuario, grupo) references gruposusuario (usuario, grupo)
);

create table SesionTablero(
	id int primary key,
	grupo int,
	fecha date,
	foreign key(grupo) references Grupo (id)
);

create table mensaje(
	id int primary key,
	chat int,
	grupo int,
	emisor BIGINT,
	contenido varchar(250),
	fecha TIMESTAMP,
	foreign key(chat) references chat (id),
	foreign key(grupo) references grupo (id),
	foreign key(emisor) references usuario (telefono)
);