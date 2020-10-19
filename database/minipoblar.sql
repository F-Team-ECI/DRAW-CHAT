insert into Usuario (telefono, nombre, apellido, contraseña, fechaRegistro, fechaConexion, estado) values (1111111111, 'andres', 'parra', '7aVQXGVEDC', '2020-05-24 21:04:03', '2020-08-28 04:59:09', 'Disconnected');
insert into Usuario (telefono, nombre, apellido, contraseña, fechaRegistro, fechaConexion, estado) values (2222222222, 'aquiles', 'baeza', 'R4SwFnPzMI', '2020-03-08 17:52:21', '2020-03-04 01:33:26', 'Online');
insert into Usuario (telefono, nombre, apellido, contraseña, fechaRegistro, fechaConexion, estado) values (3333333333, 'alejandro', 'vasquez', 'egQTq9', '2020-02-24 05:27:50', '2020-03-20 17:48:18', 'Disconnected');
insert into Usuario (telefono, nombre, apellido, contraseña, fechaRegistro, fechaConexion, estado) values (4444444444, 'german', 'ospina', '7SegkTQNS8o', '2019-10-24 13:00:51', '2020-07-16 05:35:48', 'Disconnected');
insert into Usuario (telefono, nombre, apellido, contraseña, fechaRegistro, fechaConexion, estado) values (5555555555, 'michael', 'ballesteros', 'aca123', '2020-03-22 02:39:54', '2020-01-11 16:41:19', 'Online');

insert into Contacto (propietario, dirigido) values (5555555555, 3333333333);
insert into Contacto (propietario, dirigido) values (5555555555, 4444444444);
insert into Contacto (propietario, dirigido) values (3333333333, 2222222222);
insert into Contacto (propietario, dirigido) values (3333333333, 1111111111);
insert into Contacto (propietario, dirigido) values (4444444444, 5555555555);

insert into Chat (id, usuario1, usuario2, tipo) values (1, 5555555555, 3333333333, 'normal');
insert into Chat (id, usuario1, usuario2, tipo) values (2, 5555555555, 4444444444, 'normal');
insert into Chat (id, usuario1, usuario2, tipo) values (3, 3333333333, 2222222222, 'normal');
insert into Chat (id, usuario1, usuario2, tipo) values (4, 3333333333, 1111111111, 'normal');
insert into Chat (id, usuario1, usuario2, tipo) values (5, 4444444444, 5555555555, 'normal');

insert into Grupo (id, nombre, lema, fechacreacion) values (1, 'papulinces', '404 not found', '2020-05-09 19:05:04');
insert into Grupo (id, nombre, lema, fechacreacion) values (2, 'colombia', '404 not found', '2020-04-02 11:14:52');
insert into Grupo (id, nombre, lema, fechacreacion) values (3, 'eeuu', '404 not found', '2019-12-09 08:46:34');
insert into Grupo (id, nombre, lema, fechacreacion) values (4, 'empresa', '404 not found', '2020-04-01 09:04:11');
insert into Grupo (id, nombre, lema, fechacreacion) values (5, 'japon', '404 not found', '2020-01-03 02:16:25');

insert into Mensaje (id, chat, emisor, contenido, fecha) values (1, 1, 5555555555, 'hola', '2019-11-07 22:32:18');
insert into Mensaje (id, chat, emisor, contenido, fecha) values (2, 1, 3333333333, 'hola como vas', '2020-07-23 03:45:53');
insert into Mensaje (id, chat, emisor, contenido, fecha) values (3, 1, 5555555555, 'bien estoy en una reunion', '2020-06-22 09:04:14');
insert into Mensaje (id, chat, emisor, contenido, fecha) values (4, 2, 4444444444, 'confirmo asistencia', '2019-12-07 20:40:24');
insert into Mensaje (id, chat, emisor, contenido, fecha) values (5, 2, 5555555555, 'entendido', '2020-01-31 16:26:58');

insert into Mensaje (id, grupo, emisor, contenido, fecha) values (6, 1, 5555555555, 'hey', '2019-11-07 22:32:18');
insert into Mensaje (id, grupo, emisor, contenido, fecha) values (7, 1, 3333333333, 'hola a todos', '2020-07-23 03:45:53');
insert into Mensaje (id, grupo, emisor, contenido, fecha) values (8, 1, 5555555555, 'hoy es un buen dia', '2020-06-22 09:04:14');
insert into Mensaje (id, grupo, emisor, contenido, fecha) values (9, 2, 4444444444, 'no llego el paquete', '2019-12-07 20:40:24');
insert into Mensaje (id, grupo, emisor, contenido, fecha) values (10, 2, 5555555555, 'cual es el siguiente trabajo?', '2020-01-31 16:26:58');
