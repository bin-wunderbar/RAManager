Use RAManager;

INSERT INTO Role (name, permissionRead, permissionWrite, permissionRemove,  permissionManagement)
VALUES  ( "Taller", true, true, true, false),
		( "Consulta", true, false, false, true),
	    ( "DirecciÃ³n", true, true, true, true),
		( "GestiÃ³n", true, true, true, true),
		( "SupervisiÃ³n", true, true, false, true);

INSERT INTO Employee (dni ,name, surnames , password, direction, email, phone, idRole )
VALUES  ("12345678B", "Charles", "Francis Xabier", "Password1", "Vizcaya Bilbao 1, 2", "charles.xabier@rekordautoak.com", "34111222333", 4),
		  ( "22345678A", "Ororo", "Iqadi Munroe", "Password1", "Vizcaya Bilbao 2, 3", "storm@rekordautoak.com", "34222333444", 2),
	     ( "32345678A", "Scott", "Summers", "Password1", "Vizcaya Bilbao 3, 4", "scott.summers@rekordautoak.com", "34333444555", 1),
		( "42345678A", "Erik", "Lehnsherr", "Password1", "Vizcaya Bilbao 4, 5", "magneto@rekordautoak.com", "34666777888", 3),
		( "52345678A", "Piotr", "Nikolaievitch Rasputin,", "Password1", "Vizcaya Bermeo 6, 7", "coloso@rekordautoak.com", "34999111222", 1),
		("12345678A", "Henry", "Philip McCoy", "Password1", "Vizcaya Basauri 1, 2", "beast@rekordautoak.com", "34112222333", 4);
        

INSERT INTO Status (name)
VALUES ( "DefiniciÃ³n"),
		 ( "Aprobada"),
		 ( "Pendiente"),
		("Solucionada"),
		("Cancelada"),
		( "Finalizada");



INSERT INTO Client (dni, name, surnames, direction,  email, phone)
VALUES
        ( "11223344B", "Endika", "Villafruela", "Vizcaya Villa fruela", "endika.villa@twitch.com", "34666123456"),
		("22334455B", "Ciprian", "Alupoli", "Vizcaya Bilbao" ,"cipri@gmail.com", "34777123456"),
		("33445566B", "Xabier", "Hierro NuÃ±ez", "Vizcaya Bilbao", "xabier.hierro@twitch.com", "34888123456"),
		("44556677B", "Miriam", "Miriam", "Vizcaya Bilbao", "miriam.miriam@twitch.com", "34999123456");

INSERT INTO Vehicle (registrationNumber, model, color, idClient)
VALUES ( "AE-1234", "Wolkswagen t2", "Verde", 1),
		( "CC-1234", "Delorean DMC12", "Azul claro", 2),
		( "DB-1234", "XB GT Ford Falcon", "Negro metalizado", 3),
		( "EA-1234", "Optimus Prime", "Rojo y blanco", 4),
		( "FA-1234", "BatMobile Tumbler", "Negro metalizado", 4);


INSERT INTO WorkOrder (inputDateTime, description, issuedDateTime, accept, idClient, idVehicle, idEmployeeEvaluator, idStatus)
VALUES
        ( "2020-03-22 10:00", "RevisiÃ³n elÃ©ctrica", "2020-03-22 10:00", true, 1, 1, 3, 1),
		( "2020-04-12 10:00", "Problemas de baterÃ­a", "2020-03-22 10:00", true, 1, 1, 5, 2),
		( "2021-10-24 17:42", "Rotura de luna trasera", "2020-03-22 10:00", true, 4, 4, 3, 3),
		( "2020-03-22 10:00", "Test1", "2020-03-22 10:00", true, 1, 1, 3, 1),
		( "2020-03-22 10:00", "Test2", "2020-03-22 10:00", true, 1, 1, 3, 2),
		( "2020-03-22 00:00", "Test3", null, true, 1, 1, 5, 3),
		( "2020-03-22 10:00", "Test3.1", "2020-03-22 10:00", true, 2, 1, 3, 3),
		( "2020-03-22 10:00", "Test4", "2020-03-22 10:00", true, 1, 1, 5, 4),
		( "2020-03-22 10:00", "Test5", "2020-03-22 10:00", true, 1, 1, 3, 5),
		( "2020-03-22 10:00", "Test6", "2020-03-22 10:00", true, 1, 1, 5, 6);

INSERT INTO Service (description, hourlyPrice)
VALUES ("Inflado de neumÃ¡ticos", 5),
		("RevisiÃ³n general", 10),
		("ReparaciÃ³n de abolladura", 15),
		("Cambio de baterÃ­a", 20),
		("ReparaciÃ³n de luna", 30);

  INSERT INTO Operation (idOrder , idMechanic, idService, dedicatedTime, hourlyPriceApplied)
  VALUES 
        (1, 1, 2, 45, 10),
        (1, 1, 1, 15, 5),
		(2, 3, 4, 30, 20),
		(3, 1, 3, 90, 15),
		(3, 5, 5, 90, 30),
		(6, 3, 2, 120, 40),
		(10, 4, 2, 120, 40); 

INSERT INTO Provider (name, direction, email, phone)
VALUES  ( "AUTODOC", "Josef-Orlopp-StraÃŸe 55 - 10365 Berlin", "info@autodoc.es", "34768512512"),
		 ( "RecambiosCoches.es", "online", "info@recambioscoches.es", "30208478250");

INSERT INTO Material (name, description, unitPrice, idProvider)
VALUES ( "Recambio de freno", "Recambio de freno", 11.1, 1),
		( "Bomba de agua", "Bomba de agua", 22.2, 1),
		( "Embrague", "Embrague", 33.3, 1),
		 ( "Palanca de cambios", "Palanca de cambios", 44.4, 2),
		 ( "Elevalunas", "Elevalunas elÃ©ctrico", 55.5, 2),
		 ( "NeumÃ¡tico", "NeumÃ¡tico", 66.6, 2),
		 ( "BaterÃ­a 12V 20W 3F14", "BaterÃ­a de niquel metalhidruro", 77.7, 2),
		 ( "Luna de cristal", "Luna de cristal", 88.8, 2);
    

INSERT INTO UsedMaterial (units, idMaterial, idOperation, unitPriceApplied)
VALUES ( 1, 7, 3, 77.7),
		( 1, 8, 5, 88.8),
		( 1, 8, 6, 88.8);
