CREATE TABLE public.usuario (
	nombre varchar(250) NULL,
	telefono int8 NOT NULL,
	apellido varchar(250) NULL,
	contraseña varchar(250) NULL,
	fecharegistro timestamp NULL,
	fechaconexion timestamp NULL,
	estado varchar(250) NULL,
	CONSTRAINT usuario_pkey PRIMARY KEY (telefono)
);

CREATE TABLE public.contacto (
	propietario int8 NOT NULL,
	dirigido int8 NOT NULL,
	CONSTRAINT contacto_pkey PRIMARY KEY (propietario, dirigido)
);

CREATE TABLE public.chat (
	id int4 NOT NULL,
	usuario1 int8 NULL,
	usuario2 int8 NULL,
	tipo varchar(250) NULL,
	CONSTRAINT chat_pkey PRIMARY KEY (id)
);