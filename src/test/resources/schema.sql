CREATE TABLE public.usuario (
	nombre varchar(250) NULL,
	telefono int8 NOT NULL,
	apellido varchar(250) NULL,
	contrasena varchar(250) NULL,
	fecharegistro timestamp NULL,
	fechaconexion timestamp NULL,
	estado varchar(250) NULL,
	CONSTRAINT usuario_pkey PRIMARY KEY (telefono)
);

CREATE TABLE public.chat (
	id int4 NOT NULL,
	usuario1 int8 NOT NULL,
	usuario2 int8 NOT NULL,
	tipo varchar(250) NULL,
	CONSTRAINT chat_pkey PRIMARY KEY (id,usuario1,usuario2),
	CONSTRAINT chat_usuario1_fkey FOREIGN KEY (usuario1) REFERENCES usuario(telefono),
	CONSTRAINT chat_usuario2_fkey FOREIGN KEY (usuario2) REFERENCES usuario(telefono)
);

CREATE TABLE public.contacto (
	propietario int8 NOT NULL,
	dirigido int8 NOT NULL,
	CONSTRAINT contacto_pkey PRIMARY KEY (propietario, dirigido),
	CONSTRAINT contacto_dirigido_fkey FOREIGN KEY (dirigido) REFERENCES usuario(telefono),
	CONSTRAINT contacto_propietario_fkey FOREIGN KEY (propietario) REFERENCES usuario(telefono)
);

CREATE TABLE public.grupo (
	id int4 NOT NULL,
	nombre varchar(250) NULL,
	lema varchar(250) NULL,
	fechacreacion timestamp NULL,
	chat int4 NULL,
	CONSTRAINT grupo_pkey PRIMARY KEY (id),
	CONSTRAINT grupo_chat_fkey FOREIGN KEY (chat) REFERENCES chat(id)
);

CREATE TABLE public.gruposusuario (
	usuario int8 NOT NULL,
	grupo int4 NOT NULL,
	rol varchar(250) NULL,
	CONSTRAINT gruposusuario_pkey PRIMARY KEY (usuario, grupo),
	CONSTRAINT gruposusuario_grupo_fkey FOREIGN KEY (grupo) REFERENCES grupo(id),
	CONSTRAINT gruposusuario_usuario_fkey FOREIGN KEY (usuario) REFERENCES usuario(telefono)
);

CREATE TABLE public.mensaje (
	id int4 NOT NULL,
	chat int4 NULL,
	emisor int8 NULL,
	contenido varchar(250) NULL,
	fecha timestamp NULL,
	CONSTRAINT mensaje_pkey PRIMARY KEY (id),
	CONSTRAINT mensaje_chat_fkey FOREIGN KEY (chat) REFERENCES chat(id),
	CONSTRAINT mensaje_emisor_fkey FOREIGN KEY (emisor) REFERENCES usuario(telefono)
);

CREATE TABLE public.permiso (
	nombre varchar(250) NOT NULL,
	usuario int8 NOT NULL,
	grupo int4 NOT NULL,
	estado bool NULL,
	descripcion varchar(250) NULL,
	CONSTRAINT permiso_pkey PRIMARY KEY (nombre, usuario, grupo),
	CONSTRAINT permiso_usuario_grupo_fkey FOREIGN KEY (usuario, grupo) REFERENCES gruposusuario(usuario, grupo)
);

CREATE TABLE public.sesiontablero (
	id int4 NOT NULL,
	grupo int4 NULL,
	fecha date NULL,
	CONSTRAINT sesiontablero_pkey PRIMARY KEY (id),
	CONSTRAINT sesiontablero_grupo_fkey FOREIGN KEY (grupo) REFERENCES grupo(id)
);