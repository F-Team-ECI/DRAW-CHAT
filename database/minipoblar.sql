insert into Usuario (telefono, nombre, apellido, contraseña, fechaRegistro, fechaConexion, estado) values ('0001', 'andres', 'parra', '7aVQXGVEDC', '2020-05-24 21:04:03', '2020-08-28 04:59:09', 'Disconnected');
insert into Usuario (telefono, nombre, apellido, contraseña, fechaRegistro, fechaConexion, estado) values ('0002', 'aquiles', 'baeza', 'R4SwFnPzMI', '2020-03-08 17:52:21', '2020-03-04 01:33:26', 'Online');
insert into Usuario (telefono, nombre, apellido, contraseña, fechaRegistro, fechaConexion, estado) values ('0003', 'alejandro', 'vasquez', 'egQTq9', '2020-02-24 05:27:50', '2020-03-20 17:48:18', 'Disconnected');
insert into Usuario (telefono, nombre, apellido, contraseña, fechaRegistro, fechaConexion, estado) values ('0004', 'german', 'ospina', '7SegkTQNS8o', '2019-10-24 13:00:51', '2020-07-16 05:35:48', 'Disconnected');
insert into Usuario (telefono, nombre, apellido, contraseña, fechaRegistro, fechaConexion, estado) values ('0005', 'michael', 'ballesteros', 'aca123', '2020-03-22 02:39:54', '2020-01-11 16:41:19', 'Online');

insert into Contacto (propietario, dirigido) values ('0005', '0003');
insert into Contacto (propietario, dirigido) values ('0005', '0004');
insert into Contacto (propietario, dirigido) values ('0003', '0002');
insert into Contacto (propietario, dirigido) values ('0003', '0001');
insert into Contacto (propietario, dirigido) values ('0004', '0005');