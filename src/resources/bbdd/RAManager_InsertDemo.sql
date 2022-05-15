Use RAManager;

INSERT INTO Role (id, name, permissionRead, permissionWrite, permissionRemove,  permissionManagement)
VALUES  (1, "Taller", true, true, true, false),
		(2, "Consulta", true, false, false, true),
	    (3, "Direccion", true, true, true, true),
		(4, "Gestión", true, true, true, true),
		(5, "Supervisión", true, true, false, true);

INSERT INTO Employee (dni ,name, surnames , password, province, direction, email, phone, idRole )
VALUES  ("12395678B", "Charles", "Francis Xabier", MD5("Password1"), "Vizcaya", "Bilbao 1, 2", "charles.xabier@rekordautoak.com", "34111222333", 4),
		("22345678A", "Ororo", "Iqadi Munroe", MD5("Password1"), "Vizcaya", "Bilbao 2, 3", "storm@rekordautoak.com", "34222333444", 2),
	    ("32345678A", "Scott", "Summers", MD5("Password1"), "Vizcaya", "Bilbao 3, 4", "scott.summers@rekordautoak.com", "34333444555", 1),
		("42345678A", "Erik", "Lehnsherr", MD5("Password1"), "Vizcaya", "Bilbao 4, 5", "magneto@rekordautoak.com", "34666777888", 3),
		("52345678A", "Piotr", "Nikolaievitch Rasputin,", MD5("Password1"), "Vizcaya", "Bermeo 6, 7", "coloso@rekordautoak.com", "34999111222", 1),
		("11223344B", "Henry", "Philip McCoy", MD5("Password1"), "Vizcaya", "Basauri 1, 2", "beast@rekordautoak.com", "34112222333", 4);
        

INSERT INTO Status (id, name)
VALUES  (1, "Definición"),
		(2, "Aprobada"),
		(3, "Pendiente"),
		(4, "Solucionada"),
		(5, "Cancelada"),
		(6, "Finalizada");

INSERT INTO Client (dni, name, surnames, province, direction,  email, phone)
VALUES
        ("11223344B", "Endika", "Villafruela", "Vizcaya", "Villa fruela", "endika.villa@twitch.com", "34666123456"),
		("22334455B", "Ciprian", "Alupoli", "Vizcaya", "Bilbao" ,"cipri@gmail.com", "34777123456"),
		("33445566B", "Xabier", "Hierro Núñez", "Vizcaya", "Bilbao", "xabier.hierro@twitch.com", "34888123456"),
		("44556677B", "Miriam", "Miriam", "Vizcaya", "Bilbao", "miriam.miriam@twitch.com", "34999123456");

INSERT INTO Vehicle (registrationNumber, model, color, idClient)
VALUES  ("AE-1234", "Volkswagen t2", "Verde", 1),
		("CC-1234", "Delorean DMC12", "Azul claro", 2),
		("DB-1234", "XB GT Ford Falcon", "Negro metalizado", 3),
		("EA-1234", "Optimus Prime", "Rojo y blanco", 4),
		("FA-1234", "BatMobile Tumbler", "Negro metalizado", 4);


INSERT INTO WorkOrder (inputDateTime, description, issuedDateTime, accept, idClient, idVehicle, idEmployeeEvaluator, idStatus)
VALUES
        ("2020-03-22 10:00", "Revision eléctrica", "2020-03-22 10:00", true, 1, 1, 3, 1),
		("2020-04-12 10:00", "Problemas de batería", "2020-03-22 10:00", true, 1, 1, 5, 2),
		("2021-10-24 17:42", "Rotura de luna trasera", "2020-03-22 10:00", true, 4, 4, 3, 3),
		("2020-03-22 10:00", "Test1", "2020-03-22 10:00", true, 1, 1, 3, 1),
		("2020-03-22 10:00", "Test2", "2020-03-22 10:00", true, 1, 1, 3, 2),
		("2020-03-22 00:00", "Test3", null, true, 1, 1, 5, 3),
		("2020-03-22 10:00", "Test3.1", "2020-03-22 10:00", true, 2, 1, 3, 3),
		("2020-03-22 10:00", "Test4", "2020-03-22 10:00", true, 1, 1, 5, 4),
		("2020-03-22 10:00", "Test5", "2020-03-22 10:00", true, 1, 1, 3, 5),
		("2020-03-22 10:00", "Test6", "2020-03-22 10:00", true, 1, 1, 5, 6),
		("2022-01-23 10:30", "Fallo intermitente delantero izquierdo", null, false, 1, 1, 3, 1);

INSERT INTO Service (description, hourlyPrice)
VALUES  ("Inflado de neumáticos", 5),
		("Revisión general", 10),
		("Reparación de abolladura", 15),
		("Cambio de batería", 20),
		("Reparación de luna", 30);

INSERT INTO Operation (idOrder, idMechanic, idService, dedicatedTime, hourlyPriceApplied)
VALUES 
        (1, 3, 2, 45, 10),
        (1, 3, 1, 15, 5),
		(2, 5, 4, 30, 20),
		(3, 3, 3, 90, 15),
		(3, 3, 5, 90, 30),
		(6, 5, 2, 120, 40),
		(10, 5, 2, 120, 40); 

INSERT INTO Provider (name, direction, email, phone)
VALUES  ( "AUTODOC", "Josef-Orlopp-Straye 55 - 10365 Berlin", "info@autodoc.es", "34768512512"),
		( "RecambiosCoches.es", "online", "info@recambioscoches.es", "30208478250");

INSERT INTO Material (name, description, unitPrice, idProvider)
VALUES  ( "Recambio de freno", "Recambio de freno", 11.1, 1),
		( "Bomba de agua", "Bomba de agua", 22.2, 1),
		( "Embrague", "Embrague", 33.3, 1),
		( "Palanca de cambios", "Palanca de cambios", 44.4, 2),
		( "Elevalunas", "Elevalunas electrico", 55.5, 2),
		( "Neumático", "Neumático", 66.6, 2),
		( "Batería 12V 20W 3F14", "Batería de níquel metalhidruro", 77.7, 2),
		( "Luna de cristal", "Luna de cristal", 88.8, 2);
    

INSERT INTO UsedMaterial (units, idMaterial, idOperation, unitPriceApplied)
VALUES  ( 1, 7, 3, 77.7),
		( 1, 8, 5, 88.8),
		( 1, 8, 6, 88.8)
