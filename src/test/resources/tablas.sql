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
