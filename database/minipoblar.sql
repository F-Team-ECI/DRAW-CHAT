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

insert into Chat (id, usuario1, usuario2, tipo) values (1, 5555555555, 3333333333, 'false');
insert into Chat (id, usuario1, usuario2, tipo) values (2, 5555555555, 4444444444, 'false');
insert into Chat (id, usuario1, usuario2, tipo) values (3, 3333333333, 2222222222, 'false');
insert into Chat (id, usuario1, usuario2, tipo) values (4, 3333333333, 1111111111, 'false');
insert into Chat (id, usuario1, usuario2, tipo) values (5, 4444444444, 5555555555, 'false');