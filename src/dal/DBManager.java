package dal;

import java.util.ArrayList;

import java.time.LocalDateTime;

// ------------------------------------------------------------------------------------------------
/***
 * Administrador de la base de datos
 * 
 * @author G1
 *
 */

public class DBManager 
{
	public static final int ID_AUTO	= 0;
	public static final int NO_ID	= 0;

	
	private DBEntityList <Role> roles;
	private DBEntityList <Employee> employees;
	private DBEntityList <Client> clients;
	private DBEntityList <Vehicle> vehicles;
	private DBEntityList <Order> orders;
	private DBEntityList <Operation> operations;
	private DBEntityList <UsedMaterial> usedMaterials;
	private DBEntityList <Service> services;
	private DBEntityList <Material> materials;
	private DBEntityList <Provider> providers;
	private DBEntityList <Status> statuses;

	private boolean simulation;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de la base de datos y carga desde la unidad de almacenamiento la información de las entidades, admite simulación para evitar escribir o eliminar datos 
	 * @param databaseConnection - Directorio de almacenamiento de la base de datos
	 * @param simulation - Permite operarar con los datos cargados pero evita la escritura y eliminación de datos
	 */
	public DBManager (String databaseConnection, boolean simulation)
	{
		this.simulation	= simulation;
		
		initDataLists (databaseConnection);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de la base de datos, <strong>no</strong> carga informacón desde la unidad de almacenamiento
	 * @param databaseConnection
	 */
	public DBManager (String databaseConnection)
	{
		simulation = false;
		
		initDataLists (databaseConnection);
	}

	// --------------------------------------------------------------------------------------------
	private final void initDataLists (String databaseConnection)
	{
		roles						= new DBEntityList <> (databaseConnection, "roles.dat", simulation);
		employees 					= new DBEntityList <> (databaseConnection, "employees.dat", simulation);
		clients						= new DBEntityList <> (databaseConnection, "clients.dat", simulation);
		vehicles					= new DBEntityList <> (databaseConnection, "vehicles.dat", simulation);
		orders						= new DBEntityList <> (databaseConnection, "orders.dat", simulation);
		operations					= new DBEntityList <> (databaseConnection, "operations.dat", simulation);
		services					= new DBEntityList <> (databaseConnection, "services.dat", simulation);
		usedMaterials				= new DBEntityList <> (databaseConnection, "usedmaterials.dat", simulation);
		materials					= new DBEntityList <> (databaseConnection, "materials.dat", simulation);
		providers					= new DBEntityList <> (databaseConnection, "providers.dat", simulation);
		statuses					= new DBEntityList <> (databaseConnection, "statuses.dat", simulation);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void connect () throws DBManagerException
	{
		loadFromDisk ();
	}
	
	// --------------------------------------------------------------------------------------------
	protected final void loadFromDisk () throws DBManagerException
	{
		try
		{
			roles.loadFromDisk();
			employees.loadFromDisk();
			clients.loadFromDisk();
			vehicles.loadFromDisk();
			orders.loadFromDisk();
			operations.loadFromDisk();
			services.loadFromDisk();
			usedMaterials.loadFromDisk();
			materials.loadFromDisk();
			providers.loadFromDisk();
			statuses.loadFromDisk();
		} 
		catch (DBManagerException exception)
		{
			throw new DBManagerException (exception.getMessage());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	protected void clearData ()
	{
		roles.clear ();
		employees.clear ();
		clients.clear ();
		vehicles.clear ();
		orders.clear ();
		operations.clear ();
		services.clear ();
		usedMaterials.clear ();
		materials.clear ();
		providers.clear ();
		statuses.clear ();
		
		Role.reset();
		Employee.reset();
		Client.reset();
		Vehicle.reset();
		Order.reset();
		Operation.reset();
		Service.reset();
		UsedMaterial.reset();
		Material.reset();
		Provider.reset();
		Status.reset();
	}
	
	// --------------------------------------------------------------------------------------------
	protected void saveAllData () throws DBManagerException
	{
		roles.saveToDisk();
		employees.saveToDisk();
		clients.saveToDisk();
		vehicles.saveToDisk();
		orders.saveToDisk();
		operations.saveToDisk();
		services.saveToDisk();
		usedMaterials.saveToDisk();
		materials.saveToDisk();
		providers.saveToDisk();
		statuses.saveToDisk();		
	}
	
	// --------------------------------------------------------------------------------------------
	protected void setMinimalDataDefaults ()
	{
		try {
			clearData ();
			roles.setSimulation (true);
			roles.save(new Role (ID_AUTO, "Taller", true, true, true, false));
			roles.save(new Role (ID_AUTO, "Consulta", true, false, false, true));
			roles.save(new Role (ID_AUTO, "Dirección", true, true, true, true));
			roles.save(new Role (ID_AUTO, "Gestión", true, true, true, true));
			roles.save(new Role (ID_AUTO, "Supervisión", true, true, false, true));
			
			statuses.setSimulation (true);
			statuses.save(new Status (ID_AUTO, "Definición"));
			statuses.save(new Status (ID_AUTO, "Aprobada"));
			statuses.save(new Status (ID_AUTO, "Pendiente"));
			statuses.save(new Status (ID_AUTO, "Solucionada"));
			statuses.save(new Status (ID_AUTO, "Cancelada"));
			statuses.save(new Status (ID_AUTO, "Finalizada"));

			employees.setSimulation (true);
			employees.save (new Employee (ID_AUTO, "12345678A", "Admin", "", "Password1", "----", "----", "admin@rekordautoak.com", "00000000000", 4));
			
			roles.setSimulation(simulation);
			statuses.setSimulation(simulation);
			employees.setSimulation(simulation);
			
			saveAllData ();
			
		} catch (DBManagerException exception) {
			exception.printStackTrace();
		}		
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Carga información de la base de datos
	 * @throws IOException 
	 */
	protected void saveDataDemo () throws DBManagerException
	{
		roles.save(new Role (ID_AUTO, "Taller", true, true, true, false));
		roles.save(new Role (ID_AUTO, "Consulta", true, false, false, true));
		roles.save(new Role (ID_AUTO, "Dirección", true, true, true, true));
		roles.save(new Role (ID_AUTO, "Gestión", true, true, true, true));
		roles.save(new Role (ID_AUTO, "Supervisión", true, true, false, true));
		
		statuses.save(new Status (ID_AUTO, "Definición"));
		statuses.save(new Status (ID_AUTO, "Aprobada"));
		statuses.save(new Status (ID_AUTO, "Pendiente"));
		statuses.save(new Status (ID_AUTO, "Solucionada"));
		statuses.save(new Status (ID_AUTO, "Cancelada"));
		statuses.save(new Status (ID_AUTO, "Finalizada"));
		
		employees.save (new Employee (ID_AUTO, "12345678B", "Charles", "Francis Xabier", "Password1", "Vizcaya", "Bilbao 1, 2", "charles.xabier@rekordautoak.com", "34111222333", 4));
		employees.save (new Employee (ID_AUTO, "22345678A", "Ororo", "Iqadi Munroe", "Password1", "Vizcaya", "Bilbao 2, 3", "storm@rekordautoak.com", "34222333444", 2));
		employees.save (new Employee (ID_AUTO, "32345678A", "Scott", "Summers", "Password1", "Vizcaya", "Bilbao 3, 4", "scott.summers@rekordautoak.com", "34333444555", 1));
		employees.save (new Employee (ID_AUTO, "42345678A", "Erik", "Lehnsherr", "Password1", "Vizcaya", "Bilbao 4, 5", "magneto@rekordautoak.com", "34666777888", 3));
		employees.save (new Employee (ID_AUTO, "52345678A", "Piotr", "Nikolaievitch Rasputin,", "Password1", "Vizcaya", "Bermeo 6, 7", "coloso@rekordautoak.com", "34999111222", 1));
		employees.save (new Employee (ID_AUTO, "12345678A", "Henry", "Philip McCoy", "Password1", "Vizcaya", "Basauri 1, 2", "beast@rekordautoak.com", "34111222333", 4));

		clients.save (new Client (ID_AUTO, "11223344B", "Endika", "Villafruela", "Vizcaya", "Villa fruela", "endika.villa@twitch.com", "34666123456"));
		clients.save (new Client (ID_AUTO, "22334455B", "Ciprian", "Alupoli", "Vizcaya", "Bilbao", "cipri@gmail.com", "34777123456"));
		clients.save (new Client (ID_AUTO, "33445566B", "Xabier", "Hierro Nuñez", "Vizcaya", "Bilbao", "xabier.hierro@twitch.com", "34888123456"));
		clients.save (new Client (ID_AUTO, "44556677B", "Miriam", "Miriam", "Vizcaya", "Bilbao", "miriam.miriam@twitch.com", "34999123456"));

		vehicles.save (new Vehicle (ID_AUTO, "AE-1234", "Wolkswagen t2", "Verde", 1));
		vehicles.save (new Vehicle (ID_AUTO, "CC-1234", "Delorean DMC12", "Azul claro", 2));
		vehicles.save (new Vehicle (ID_AUTO, "DB-1234", "XB GT Ford Falcon", "Negro metalizado", 3));
		vehicles.save (new Vehicle (ID_AUTO, "EA-1234", "Optimus Prime", "Rojo y blanco", 4));
		vehicles.save (new Vehicle (ID_AUTO, "FA-1234", "BatMobile Tumbler", "Negro metalizado", 4));

		orders.save (new Order (ID_AUTO, LocalDateTime.parse("2020-03-22T10:00"), "Revisión eléctrica", LocalDateTime.parse("2020-03-22T10:00"), true, 1, 1, 3, 1));
		orders.save (new Order (ID_AUTO, LocalDateTime.parse("2020-04-12T10:00"), "Problemas de batería", LocalDateTime.parse("2020-03-22T10:00"), true, 1, 1, 5, 2));
		orders.save (new Order (ID_AUTO, LocalDateTime.parse("2021-10-24T17:42"), "Rotura de luna trasera", LocalDateTime.parse("2020-03-22T10:00"), true, 4, 4, 3, 3));
		orders.save (new Order (ID_AUTO, LocalDateTime.parse("2020-03-22T10:00"), "Test1", LocalDateTime.parse("2020-03-22T10:00"), true, 1, 1, 3, 1));
		orders.save (new Order (ID_AUTO, LocalDateTime.parse("2020-03-22T10:00"), "Test2", LocalDateTime.parse("2020-03-22T10:00"), true, 1, 1, 3, 2));
		orders.save (new Order (ID_AUTO, LocalDateTime.parse("2020-03-22T10:00"), "Test3", null, true, 1, 1, 5, 3));
		orders.save (new Order (ID_AUTO, LocalDateTime.parse("2020-03-22T10:00"), "Test3.1", LocalDateTime.parse("2020-03-22T10:00"), true, 2, 1, 3, 3));
		orders.save (new Order (ID_AUTO, LocalDateTime.parse("2020-03-22T10:00"), "Test4", LocalDateTime.parse("2020-03-22T10:00"), true, 1, 1, 5, 4));
		orders.save (new Order (ID_AUTO, LocalDateTime.parse("2020-03-22T10:00"), "Test5", LocalDateTime.parse("2020-03-22T10:00"), true, 1, 1, 3, 5));
		orders.save (new Order (ID_AUTO, LocalDateTime.parse("2020-03-22T10:00"), "Test6", LocalDateTime.parse("2020-03-22T10:00"), true, 1, 1, 5, 6));

		operations.save (new Operation (ID_AUTO, 1, 1, 2, 45, 10));
		operations.save (new Operation (ID_AUTO, 1, 1, 1, 15, 5));
		operations.save (new Operation (ID_AUTO, 2, 3, 4, 30, 20));
		operations.save (new Operation (ID_AUTO, 3, 1, 3, 90, 15));
		operations.save (new Operation (ID_AUTO, 3, 5, 5, 90, 30));
		operations.save (new Operation (ID_AUTO, 6, 3, 2, 120, 40));
		operations.save (new Operation (ID_AUTO, 10, 4, 2, 120, 40));

		usedMaterials.save(new UsedMaterial (ID_AUTO, 1, 7, 3, 77.7));
		usedMaterials.save(new UsedMaterial (ID_AUTO, 1, 8, 5, 88.8));
		usedMaterials.save(new UsedMaterial (ID_AUTO, 1, 8, 6, 88.8));

		services.save (new Service (ID_AUTO, "Inflado de neumáticos", 5));
		services.save (new Service (ID_AUTO, "Revisión general", 10));
		services.save (new Service (ID_AUTO, "Reparación de abolladura", 15));
		services.save (new Service (ID_AUTO, "Cambio de batería", 20));
		services.save (new Service (ID_AUTO, "Reparación de luna", 30));

		materials.save (new Material (ID_AUTO, "Recambio de freno", "Recambio de freno", 11.1, 1));
		materials.save (new Material (ID_AUTO, "Bomba de agua", "Bomba de agua", 22.2, 1));
		materials.save (new Material (ID_AUTO, "Embrague", "Embrague", 33.3, 1));
		materials.save (new Material (ID_AUTO, "Palanca de cambios", "Palanca de cambios", 44.4, 2));
		materials.save (new Material (ID_AUTO, "Elevalunas", "Elevalunas eléctrico", 55.5, 2));
		materials.save (new Material (ID_AUTO, "Neumático", "Neumático", 66.6, 2));
		materials.save (new Material (ID_AUTO, "Batería 12V 20W 3F14", "Batería de niquel metalhidruro", 77.7, 2));
		materials.save (new Material (ID_AUTO, "Luna de cristal", "Luna de cristal", 88.8, 2));

		providers.save (new Provider (ID_AUTO, "AUTODOC", "Josef-Orlopp-Straße 55 - 10365 Berlin", "info@autodoc.es", "34768512512"));
		providers.save (new Provider (ID_AUTO, "RecambiosCoches.es", "online", "info@recambioscoches.es", "30208478250"));
	}

	// --------------------------------------------------------------------------------------------
	protected Status getStatus (int id) throws DBManagerException
	{
		return statuses.getByPrimaryKey (id);
	}
	
	// --------------------------------------------------------------------------------------------
	protected Material getMaterial (int id) throws DBManagerException
	{
		return materials.getByPrimaryKey(id);
	}
	
	// --------------------------------------------------------------------------------------------
	protected Operation getOperation (int id) throws DBManagerException
	{
		return operations.getByPrimaryKey(id);
	}
	
	// --------------------------------------------------------------------------------------------
	protected Role getRole (int id) throws DBManagerException
	{
		return roles.getByPrimaryKey(id);
	}
	
	// --------------------------------------------------------------------------------------------
	protected Employee getEmployee (int id) throws DBManagerException
	{
		return employees.getByPrimaryKey(id);
	}
	
	// --------------------------------------------------------------------------------------------
	protected Order getOrder (int id) throws DBManagerException 
	{
		return orders.getByPrimaryKey(id);
	}
	
	// --------------------------------------------------------------------------------------------
	protected ArrayList <Order> getOrdersByEmployee (int idEmployee) throws DBManagerException
	{
		ArrayList <Order> employeeOrders = new ArrayList <> ();
		
		for (Order order : orders)
		{
			if (order.getIdEvaluator() == idEmployee) employeeOrders.add (order);
		}
		
		return employeeOrders;
	}
	
	protected ArrayList <Order> getOrdersByClient (int idClient) throws DBManagerException
	{
		ArrayList <Order> clientOrders = new ArrayList <> ();
		
		for (Order order : orders)
		{
			if (order.getIdClient() == idClient) clientOrders.add (order);
		}
		
		return clientOrders;
	}
	
	// --------------------------------------------------------------------------------------------
	protected ArrayList <Employee> getEmployeesByIdRol (int idRol) throws DBManagerException
	{
		ArrayList <Employee> employeesByIdRol = new ArrayList <> ();
		
		for (Employee employee : employees)
		{
			if (employee.getIdRole() == idRol)
			{
				employeesByIdRol.add (employee);
			}
		}
		
		return employeesByIdRol;
	}
	
	// --------------------------------------------------------------------------------------------
	protected ArrayList <Vehicle> getClientVehicles (int idClient) throws DBManagerException
	{
		ArrayList <Vehicle> clientVehicles = new ArrayList <> ();
		
		for (Vehicle vehicle : vehicles)
		{
			if (vehicle.getIdClient() == idClient) clientVehicles.add (vehicle);
		}
		
		return clientVehicles;
	}
	
	// --------------------------------------------------------------------------------------------
	protected Vehicle getVehicleById (int id) throws DBManagerException
	{
		return vehicles.getByPrimaryKey (id);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void save (Vehicle vehicle) throws DBManagerException
	{
		for (Vehicle vehicleByRegistrationNumber : vehicles)
		{
			if (vehicleByRegistrationNumber.getId() != vehicle.getId () && vehicleByRegistrationNumber.getRegistrationNumber().equalsIgnoreCase(vehicle.getRegistrationNumber()))
			{
				throw new DBManagerException (DBManagerException.ERROR_UNIQUE, "Registration number vehicle duplicate error (UNIQUE)"); 
			}
		}
		
		vehicles.save (vehicle);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void save (Client client) throws DBManagerException
	{
		for (Client clientByNIF : clients)
		{
			if (clientByNIF.getId() != client.getId() && clientByNIF.getNIF().equalsIgnoreCase(client.getNIF()))
			{
				throw new DBManagerException (DBManagerException.ERROR_UNIQUE, "NIF client duplicate error (UNIQUE)");
			}
		}
		
		clients.save (client);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void save (Order order) throws DBManagerException
	{
		orders.save (order);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void save (Provider provider) throws DBManagerException
	{
		providers.save (provider);
	}

	// --------------------------------------------------------------------------------------------
	protected void save (Employee employee) throws DBManagerException
	{
		for (Employee employeeByNIF : employees)
		{
			if (employeeByNIF.getId() != employee.getId() && employeeByNIF.getNIF().equalsIgnoreCase(employee.getNIF()))
			{
				throw new DBManagerException (DBManagerException.ERROR_UNIQUE, "NIF employee duplicate error (UNIQUE)");
			}
		}
		
		employees.save (employee);
	}

	// --------------------------------------------------------------------------------------------
	protected void save (Operation operation) throws DBManagerException
	{
		operations.save (operation);
	}

	// --------------------------------------------------------------------------------------------
	protected void save (UsedMaterial usedMaterial) throws DBManagerException
	{
		usedMaterials.save (usedMaterial);
	}

	// --------------------------------------------------------------------------------------------
	protected void save (Role role) throws DBManagerException
	{
		roles.save (role);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void save (Material material) throws DBManagerException
	{
		materials.save (material);
	}

	// --------------------------------------------------------------------------------------------
	protected void save (Service service) throws DBManagerException
	{
		services.save (service);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void deleteEmployees (ArrayList<Integer> ids) throws DBManagerException
	{
		employees.deleteEntitiesByPrimaryKeys(ids);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void deleteClients (ArrayList<Integer> ids) throws DBManagerException 
	{
		clients.deleteEntitiesByPrimaryKeys(ids);
	}

	// --------------------------------------------------------------------------------------------
	protected void deleteOrders (ArrayList<Integer> ids) throws DBManagerException 
	{
		orders.deleteEntitiesByPrimaryKeys(ids);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void deleteProviders (ArrayList<Integer> ids) throws DBManagerException 
	{
		providers.deleteEntitiesByPrimaryKeys (ids);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void deleteVehicles (ArrayList<Integer> ids) throws DBManagerException 
	{
		vehicles.deleteEntitiesByPrimaryKeys(ids);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void deleteMaterials(ArrayList<Integer> ids) throws DBManagerException
	{
		materials.deleteEntitiesByPrimaryKeys(ids);
	}
	
	// --------------------------------------------------------------------------------------------
	public void deleteOperations (ArrayList <Integer> ids) throws DBManagerException 
	{
		operations.deleteEntitiesByPrimaryKeys(ids);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void deleteRole (int idRol) throws DBManagerException
	{
		roles.deleteByPrimaryKey (idRol);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void deleteServices (ArrayList<Integer> ids) throws DBManagerException 
	{
		services.deleteEntitiesByPrimaryKeys(ids);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void deleteUsedMaterialsByOperationId (int idOperation) throws DBManagerException
	{
		ArrayList <UsedMaterial> deleteUsedMaterials = new ArrayList <> ();
		
		// Localizar materiales a borrar
		for (UsedMaterial usedMaterial : usedMaterials)
		{
			if (usedMaterial.getIdOperation() == idOperation)
			{
				deleteUsedMaterials.add (usedMaterial);
			}
		}
		
		// Eliminarlos de la lista principal
		for (UsedMaterial usedMaterial : deleteUsedMaterials)
		{
			usedMaterials.remove(usedMaterial);
		}
	}
	
	/**
	 * @return the roles
	 */
	protected DBEntityList<Role> getRoles (String filter) throws DBManagerException 
	{
		return roles.getFilter (filter);
	}

	/**
	 * @return the employees
	 */
	protected DBEntityList<Employee> getEmployees (String filter) throws DBManagerException 
	{
		return employees.getFilter (filter);
	}

	/**
	 * @return the clients
	 */
	protected DBEntityList<Client> getClients (String filter) throws DBManagerException 
	{
		return clients.getFilter (filter);
	}

	/**
	 * @return the vehicles
	 */
	protected DBEntityList<Vehicle> getVehicles (String filter) throws DBManagerException 
	{
		return vehicles.getFilter (filter);
	}

	/**
	 * @return the orders
	 */
	protected DBEntityList<Order> getOrders (String filter) throws DBManagerException 
	{
		return orders.getFilter (filter);
	}

	/**
	 * @return the operations
	 */
	protected DBEntityList<Operation> getOperations (String filter) throws DBManagerException 
	{
		return operations.getFilter (filter);
	}

	/**
	 * @return the usedMaterials
	 */
	protected DBEntityList<UsedMaterial> getUsedMaterials (String filter) throws DBManagerException 
	{
		return usedMaterials.getFilter (filter);
	}

	/**
	 * @return the services
	 */
	protected DBEntityList<Service> getServices (String filter) throws DBManagerException 
	{
		return services.getFilter (filter);
	}

	/**
	 * @return the materials
	 */
	protected DBEntityList<Material> getMaterials (String filter) throws DBManagerException 
	{
		return materials.getFilter (filter);
	}

	/**
	 * @return the providers
	 */
	protected DBEntityList<Provider> getProviders (String filter) throws DBManagerException 
	{
		return providers.getFilter (filter);
	}

	/**
	 * @return the statuses
	 */
	protected DBEntityList<Status> getStatuses (String filter) throws DBManagerException 
	{
		return statuses.getFilter (filter);
	}

	
	
}
