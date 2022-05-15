Use RAManager;

INSERT INTO Role (id, name, permissionRead, permissionWrite, permissionRemove,  permissionManagement)
VALUES  (1, "Taller", true, true, true, false),
		(2, "Consulta", true, false, false, true),
	    (3, "Direccion", true, true, true, true),
		(4, "Gestión", true, true, true, true),
		(5, "Supervisión", true, true, false, true);

INSERT INTO Status (id, name)
VALUES  (1, "Definición"),
		(2, "Aprobada"),
		(3, "Pendiente"),
		(4, "Solucionada"),
		(5, "Cancelada"),
		(6, "Finalizada");

INSERT INTO Employee (dni ,name, surnames , password, province, direction, email, phone, idRole )
VALUES  ("87654321Z", "Admin", "", MD5("Password1"), "----", "----", "admin@rekordautoak.com", "00000000000", 4)
