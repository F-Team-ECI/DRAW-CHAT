create table Usuario(
	id serial primary key,
	nombre varchar(250),
	telefono BIGINT,
	apellido varchar(250),
	contraseña varchar(250),
	fechaRegistro TIMESTAMP,
	fechaConexion TIMESTAMP,
	estado varchar(250)
);

create table Contacto(
	propietario int,
	dirigido int,
	primary key(propietario, dirigido),
	foreign key(propietario) references usuario (telefono),
	foreign key(dirigido) references usuario (telefono)
);

create table Chat(
	id int primary key,
	usuario1 int,
	usuario2 int,
	tipo varchar(250),
	foreign key(usuario1) references Usuario (telefono),
	foreign key(usuario2) references Usuario (telefono)
);

create table Grupo(
	id int primary key,
	nombre varchar(250),
	lema varchar(250),
	fechaCreacion TIMESTAMP,
	chat int,
	foreign key(chat) references chat (id)
);

create table GruposUsuario(
	usuario int,
	grupo int,
	rol varchar(250),
	primary key (usuario, grupo),
	foreign key(usuario) references Usuario (telefono),
	foreign key(grupo) references Grupo (id)
);

create table Permiso(
	nombre varchar(250),
	usuario int,
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
	id int,
	chat int,
	emisor int,
	contenido varchar(250),
	fecha TIMESTAMP,
	primary key(id),
	foreign key(chat) references chat (id),
	foreign key(emisor) references usuario (telefono)
);

