INSERT INTO Role (name, permissionRead, permissionWrite, permissionRemove,  permissionManagement)
VALUES  ('Taller', true, true, true, false),
		('Consulta', true, false, false, true),
	    ('Direccion', true, true, true, true),
		('Gestión', true, true, true, true),
		('Supervisión', true, true, false, true);

INSERT INTO Status (name)
VALUES  ('Definición'),
		('Aprobada'),
		('Pendiente'),
		('Solucionada'),
		('Cancelada'),
		('Finalizada');

INSERT INTO Employee (dni ,name, surnames , password, province, direction, email, phone, idRole )
VALUES  ('87654321Z', 'Admin', '', '2ac9cb7dc02b3c0083eb70898e549b63', '----', '----', 'admin@rekordautoak.com', '00000000000', 4)
