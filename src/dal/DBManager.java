package dal;

import java.util.ArrayList;

<<<<<<< HEAD
=======
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
>>>>>>> V3.1-alertas
import java.time.LocalDateTime;

// ------------------------------------------------------------------------------------------------
/***
<<<<<<< HEAD
 * Administrador de la base de datos
=======
 * Gestor de la base de datos
>>>>>>> V3.1-alertas
 * 
 * @author G1
 *
 */
<<<<<<< HEAD

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
=======
public class DBManager 
{
	public static final int ID_NEW	= 0;
	public static final int NO_ID	= 0;
	
	public static final String BBDD_NAME						= "RAManager";
	
	public static final String BBDD_RESOURCES 					= "resources/bbdd";
	
	public static final String MARIADB_RAMANAGER 					= "RAManager.sql";
	public static final String MARIADB_RAMANAGER_DEMO 				= "RAManager_InsertDemo.sql";
	public static final String MARIADB_RAMANAGER_MINIMAL_DEFAULTS	= "RAManager_InsertMinimal.sql";

	public static final String DERBY_RAMANAGER 						= "RAManager_derby.sql";
	public static final String DERBY_RAMANAGER_DEMO 				= "RAManager_derby_InsertDemo.sql";
	public static final String DERBY_RAMANAGER_MINIMAL_DEFAULTS		= "RAManager_derby_InsertMinimal.sql";

	private static boolean debug;
	
	public static final int DB_DRIVER_MARIADB 					= 0;
	public static final int DB_DRIVER_DERBY						= 1;
	
	private DBDriver dbDriver;
	private int selectedDBDriver;
	private String server;
	private String user;
	private String password;
>>>>>>> V3.1-alertas
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de la base de datos y carga desde la unidad de almacenamiento la información de las entidades, admite simulación para evitar escribir o eliminar datos 
<<<<<<< HEAD
	 * @param databaseConnection - Directorio de almacenamiento de la base de datos
	 * @param simulation - Permite operarar con los datos cargados pero evita la escritura y eliminación de datos
	 */
	public DBManager (String databaseConnection, boolean simulation)
	{
		this.simulation	= simulation;
		
		initDataLists (databaseConnection);
=======
	 * @param sqlServer 	- Direccion del servidor de base de datos
	 * @param sqlUser 		- Usuario de acceso a la base de datos
	 * @param sqlPassword	- Contrasenna de acceso a la base de datos
	 * @param debug - Especifica si usar el modo de depuracion, agregando informacion de operaciones a la consola
	 */
	public DBManager (String sqlServer, String sqlUser, String sqlPassword, int selectedDriver, boolean debug)
	{
		initDBManager (sqlServer, sqlUser, sqlPassword, selectedDriver, debug);
>>>>>>> V3.1-alertas
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de la base de datos, <strong>no</strong> carga informacón desde la unidad de almacenamiento
<<<<<<< HEAD
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
=======
	 * @param sqlServer 	- Direccion del servidor de base de datos
	 * @param sqlUser 		- Usuario de acceso a la base de datos
	 * @param sqlPassword	- Contrasenna de acceso a la base de datos
	 */
	public DBManager (String sqlServer, String sqlUser, String sqlPassword, int selectedDriver)
	{
		initDBManager (sqlServer, sqlUser, sqlPassword, selectedDriver, false);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de la base de datos y carga desde la unidad de almacenamiento la información de las entidades, admite simulación para evitar escribir o eliminar datos 
	 * @param server 	- Direccion del servidor de base de datos
	 * @param user 		- Usuario de acceso a la base de datos
	 * @param password	- Contrasenna de acceso a la base de datos
	 * @param debug - Especifica si usar el modo de depuracion, agregando informacion de operaciones a la consola
	 */
	public final void initDBManager (String server, String user, String password, int selectedDriver, boolean debug)
	{
		DBManager.debug 	= debug;
		
		this.server 		= server;
		this.user			= user;
		this.password		= password;
		
		setSelectedDriver (selectedDriver);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Permite seleccionar el driver a utilizar de acceso a la base de datos
	 */
	protected void setSelectedDriver (int selectedDBDriver) 
	{
		this.selectedDBDriver = selectedDBDriver;
		setDriver ();
	}

	// --------------------------------------------------------------------------------------------
	protected void setDriver ()
	{
		switch (selectedDBDriver)
		{
			default:
			case DB_DRIVER_MARIADB:
				dbDriver = new DBDriverMariaDB (server, user, password, BBDD_NAME, MARIADB_RAMANAGER, MARIADB_RAMANAGER_DEMO, MARIADB_RAMANAGER_MINIMAL_DEFAULTS);
				break;
				
			case DB_DRIVER_DERBY:
				dbDriver = new DBDriverDerby (server, user, password, BBDD_NAME, DERBY_RAMANAGER, DERBY_RAMANAGER_DEMO, DERBY_RAMANAGER_MINIMAL_DEFAULTS);
				break;
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Conecta con la base de datos serializada
	 * 
	 * @throws DBManagerException
	 */
	protected void connect () throws DBManagerException
	{
		if (dbDriver.verifyConnection())
		{
			printlnDebug("DBManager::connect () - Ready connection");
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Recarga las credenciales del usuario de conexion al servicio de base de datos
	 * 
	 * @param sqlUser		- Usuario de SQL
	 * @param sqlPassword	- Password de SQL
	 */
	protected void reloadSqlCredentials (String sqlServer, String sqlUser, String sqlPassword) 
	{
		dbDriver.setServer (sqlServer);
		dbDriver.setUser (sqlUser);
		dbDriver.setPassword (sqlPassword);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Comprueba si la conexion es correcta sin afectar a la conexion en curso
	 * 
	 * @param sqlServer 		- Nombre o direccion del servidor de base de datos 
	 * @param sqlUser			- Nombre del usuario con privilegios para acceder a la base de datos
	 * @param sqlPassword		- Contrasenna de acceso a la base de datos
	 * @throws DBManagerException
	 */
	public void verifyConnection (String sqlServer, String sqlUser, String sqlPassword) throws DBManagerException
	{
		try {
			dbDriver.verifyConnection ();
		} catch (DBManagerException dbManagerException) {
			dbManagerException.printStackTrace();
			throw dbManagerException;
>>>>>>> V3.1-alertas
		}
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
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
=======
	/**
	 * Devuelve informacion del motor de base de datos
	 * 
	 * @return Informacion del motor de base de datos
	 * @throws DBManagerException
	 */
	protected String getInfo () throws DBManagerException
	{
		return dbDriver.getInfo();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Crea una base de datos nueva con registros suficientes para la demostracion
	 * 
	 * @throws DBManagerException
	 */
	protected void saveDataDemo () throws DBManagerException
	{
		dbDriver.saveDataDemo(BBDD_RESOURCES);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Crea una base de datos nueva con los registros minimos para comenzar a usar la aplicacion
	 * 
	 * @throws DBManagerException
	 */
	protected void saveMinimalDefaults () throws DBManagerException
	{
		dbDriver.saveMinimalDefaults(BBDD_RESOURCES);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Carga una lista de entidades desde la base de datos a partir de un tipo generico
	 * 
	 * @param <T>			- Tipo generico
	 * @param entityClass	- Clase del tipo generico
	 * @param query			- Cadena de texto con la consulta
	 * @param filter		- Filtrado
	 * @return				- Lista de entidades filtrada
	 * @throws DBManagerException
	 */
	@SuppressWarnings("unchecked")
	protected <T> DBEntityList <T> loadEntityList (Class <T> entityClass, String query, String filter) throws DBManagerException
	{
		DBEntityList <T> entityList = new DBEntityList <> ();
		try {
			
			Statement statement = dbDriver.createStatement();
	
			printlnDebug ("DBManager::loadEntityList () - " + query);
			ResultSet resultSet = statement.executeQuery(query);
			
			
			while (resultSet.next())
			{
				try {

					DBEntity dbEntity = (DBEntity) entityClass.getDeclaredConstructor ().newInstance();
					dbEntity.setResultSet (resultSet);
					entityList.add ((T)dbEntity);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException exception) {
					exception.printStackTrace();
					throw new DBManagerException (exception.getMessage());
				}
			}
			
			resultSet.close ();
			dbDriver.closeStatement (statement);
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
		}
	
		return entityList.getFilter (filter);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la lista de clientes segun la lista de ids indicada
	 * 
	 * @param ids		- Lista de ids de cliente
	 * @return Lista de clientes
	 * @throws DBManagerException
	 */
	public DBEntityList <Client> getClientsByIds (ArrayList <Integer> ids) throws DBManagerException
	{
		return loadEntityList (Client.class, Client.getQuery () + " where id in (" + getFormatedIds (ids) + ")", "");
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la lista de empleados segun la lista de ids indicada
	 * 
	 * @param ids		- Lista de ids de empleado
	 * @return Lista de empleados
	 * @throws DBManagerException
	 */
	public DBEntityList <Employee> getEmployeesByIds (ArrayList <Integer> ids) throws DBManagerException
	{
		return loadEntityList (Employee.class, Employee.getQuery () + " where id in (" + getFormatedIds (ids) + ")", "");
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la lista de ordenes segun la lista de ids indicada
	 * 
	 * @param ids		- Lista de ids de ordenes
	 * @return Lista de ordenes
	 * @throws DBManagerException
	 */
	public DBEntityList <Order> getOrdersByIds (ArrayList <Integer> ids) throws DBManagerException
	{
		
		return loadEntityList (Order.class, Order.getQuery () + " where id in (" + getFormatedIds (ids) + ")", "");
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de roles
	 * 
	 * @param filter - Filtrado
	 * @return the roles
	 */
	protected DBEntityList<Role> getRoles (String filter) throws DBManagerException 
	{
		return loadEntityList (Role.class, Role.getQuery(), filter);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de empleados
	 * 
	 * @param filter - Filtrado
	 * @return the employees
	 */
	protected DBEntityList <Employee> getEmployees (String filter) throws DBManagerException 
	{
		return loadEntityList (Employee.class, Employee.getQuery (), filter);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de clientes
	 * 
	 * @param filter - Filtrado
	 * @return the clients
	 */
	protected DBEntityList <Client> getClients (String filter) throws DBManagerException 
	{
		return loadEntityList (Client.class, Client.getQuery (), filter);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de materiales
	 * 
	 * @param filter - Filtrado
	 * @return the materials
	 */
	protected DBEntityList <Material> getMaterials (String filter) throws DBManagerException 
	{
	
		return loadEntityList (Material.class, Material.getQuery(), filter);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de vehiculos
	 * 
	 * @param filter - Filtrado
	 * @return the vehicles
	 */
	protected DBEntityList<Vehicle> getVehicles (String filter) throws DBManagerException 
	{
		return loadEntityList (Vehicle.class, Vehicle.getQuery(), filter);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de ordenes de trabajo
	 * 
	 * @param filter - Filtrado
	 * @return the orders
	 */
	protected DBEntityList<Order> getOrders (String filter) throws DBManagerException 
	{
		return loadEntityList (Order.class, Order.getQuery(), filter);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de operaciones
	 *  
	 * @param filter - Filtrado
	 * @return the operations
	 */
	protected DBEntityList<Operation> getOperations (String filter) throws DBManagerException 
	{
		return loadEntityList (Operation.class, Operation.getQuery(), filter);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de materiales
	 * 
	 * @param filter - Filtrado
	 * @return the usedMaterials
	 */
	protected DBEntityList<UsedMaterial> getUsedMaterials (String filter) throws DBManagerException 
	{
		return loadEntityList (UsedMaterial.class, UsedMaterial.getQuery(), filter);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de servicios
	 * 
	 * @param filter - Filtrado
	 * @return the services
	 */
	protected DBEntityList<Service> getServices (String filter) throws DBManagerException 
	{
		return loadEntityList (Service.class, Service.getQuery(), filter);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de proveedores
	 * 
	 * @param filter - Filtrado
	 * @return the providers
	 */
	protected DBEntityList<Provider> getProviders (String filter) throws DBManagerException 
	{
		return loadEntityList (Provider.class, Provider.getQuery(), filter);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de estados
	 * 
	 * @param filter - Filtrado
	 * @return the statuses
	 */
	protected DBEntityList<Status> getStatuses (String filter) throws DBManagerException 
	{
		return loadEntityList (Status.class, Status.getQuery(), filter);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una entidad de la base de datos a partir de un tipo generico
	 * 
	 * @param <T>			- Tipo generico 
	 * @param entityClass	- Clase del tipo generico 
	 * @param query			- Cadena de texto con la consulta
	 * @param id			- Identificador de la entidad
	 * @return				- Entidad generica
	 * @throws DBManagerException
	 */
	protected <T> DBEntity getEntity (Class <T> entityClass, String query, int id) throws DBManagerException
	{
		DBEntity dbEntity = null;
		String queryEntity = query + " where id = " + id;
	
		try {
			
			Statement statement = dbDriver.createStatement();
	
			printlnDebug ("DBManager::getEntity () - " + queryEntity);
			ResultSet resultSet = statement.executeQuery (queryEntity);
			
			if (resultSet.next())
			{
				try {

					dbEntity = (DBEntity) entityClass.getDeclaredConstructor ().newInstance();
					dbEntity.setResultSet (resultSet);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException exception) {
					exception.printStackTrace();
					throw new DBManagerException (exception.getMessage());
				}
			}
			
			resultSet.close ();
			dbDriver.closeStatement (statement);
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
		}
		
		return dbEntity;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un objeto de estado de la orden
	 * 
	 * @param id - Identificador del estado
	 * @return El estado
	 * @throws DBManagerException
	 */
	protected Status getStatus (int id) throws DBManagerException
	{
		return (Status)getEntity (Status.class, Status.getQuery(), id);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un objeto de material
	 * 
	 * @param id - Identificador del material
	 * @return El material
	 * @throws DBManagerException
	 */
	protected Material getMaterial (int id) throws DBManagerException
	{
		return (Material)getEntity (Material.class, Material.getQuery(), id);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una operacion
	 * 
	 * @param id - Identificador de la operacion
	 * @return La operacion
	 * @throws DBManagerException
	 */
	protected Operation getOperation (int id) throws DBManagerException
	{
		return (Operation)getEntity (Operation.class, Operation.getQuery(), id);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un objeto de rol
	 *  
	 * @param id - Identificador del rol
	 * @return El rol
	 * @throws DBManagerException
	 */
	protected Role getRole (int id) throws DBManagerException
	{
		return (Role)getEntity (Role.class, Role.getQuery(), id);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un objeto de empleado
	 * 
	 * @param id - Identificador del empleado
	 * @return El empleado
	 * @throws DBManagerException
	 */
	protected Employee getEmployee (int id) throws DBManagerException
	{
		return (Employee)getEntity (Employee.class, Employee.getQuery(), id);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un objeto de orden de trabajo
	 * @param id - Identificador de la orden
	 * @return La orden
	 * @throws DBManagerException
	 */
	protected Order getOrder (int id) throws DBManagerException 
	{
		return (Order)getEntity (Order.class, Order.getQuery(), id);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de ordenes por empleado
	 * 
	 * @param idEmployee - Identificador del empleado
	 * @return La lista de ordenes
	 * @throws DBManagerException
	 */
>>>>>>> V3.1-alertas
	protected ArrayList <Order> getOrdersByEmployee (int idEmployee) throws DBManagerException
	{
		ArrayList <Order> employeeOrders = new ArrayList <> ();
		
<<<<<<< HEAD
		for (Order order : orders)
=======
		for (Order order : getOrders (""))
>>>>>>> V3.1-alertas
		{
			if (order.getIdEvaluator() == idEmployee) employeeOrders.add (order);
		}
		
		return employeeOrders;
	}
	
<<<<<<< HEAD
=======
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de ordenes por cliente
	 * 
	 * @param idClient - Identificador del cliente
	 * @return La lista de clientes
	 * @throws DBManagerException
	 */
>>>>>>> V3.1-alertas
	protected ArrayList <Order> getOrdersByClient (int idClient) throws DBManagerException
	{
		ArrayList <Order> clientOrders = new ArrayList <> ();
		
<<<<<<< HEAD
		for (Order order : orders)
=======
		for (Order order : getOrders (""))
>>>>>>> V3.1-alertas
		{
			if (order.getIdClient() == idClient) clientOrders.add (order);
		}
		
		return clientOrders;
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Devuelve una lista de empleados segun su rol
	 * 
	 * @param idRol - Identificador del rol
	 * @return La lista de empleados
	 * @throws DBManagerException
	 */
>>>>>>> V3.1-alertas
	protected ArrayList <Employee> getEmployeesByIdRol (int idRol) throws DBManagerException
	{
		ArrayList <Employee> employeesByIdRol = new ArrayList <> ();
		
<<<<<<< HEAD
		for (Employee employee : employees)
=======
		for (Employee employee : getEmployees (""))
>>>>>>> V3.1-alertas
		{
			if (employee.getIdRole() == idRol)
			{
				employeesByIdRol.add (employee);
			}
		}
		
		return employeesByIdRol;
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Devuelve una lista de vehiculos por cliente
	 * 
	 * @param idClient - Identificador del cliente
	 * @return La lista de vehiculos
	 * @throws DBManagerException
	 */
>>>>>>> V3.1-alertas
	protected ArrayList <Vehicle> getClientVehicles (int idClient) throws DBManagerException
	{
		ArrayList <Vehicle> clientVehicles = new ArrayList <> ();
		
<<<<<<< HEAD
		for (Vehicle vehicle : vehicles)
=======
		for (Vehicle vehicle : getVehicles (""))
>>>>>>> V3.1-alertas
		{
			if (vehicle.getIdClient() == idClient) clientVehicles.add (vehicle);
		}
		
		return clientVehicles;
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
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

	
	
=======
	/**
	 * Devuelve un vehiculo por su id
	 * @param id - Identificador del vehiculo
	 * @return El vehiculo
	 * @throws DBManagerException
	 */
	protected Vehicle getVehicle (int id) throws DBManagerException
	{
		return (Vehicle)getEntity (Vehicle.class, Vehicle.getQuery(), id);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un proveedr por su id
	 * @param id - Identificador del vehiculo
	 * @return El proveedor
	 * @throws DBManagerException
	 */
	protected Provider getProvider (int id) throws DBManagerException
	{
		return (Provider)getEntity (Provider.class, Provider.getQuery(), id);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda una entidad de la base de datos
	 * 
	 * @param dbEntity			- Entidad de la base de datos
	 * @throws DBManagerException
	 */
	protected void save (DBEntity dbEntity) throws DBManagerException
	{
		try {
			Statement statement = dbDriver.createStatement();
			
			dbEntity.save (statement);

			dbDriver.closeStatement (statement);
			
		} catch (SQLException sqlException) {
			throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de elementos por sus identificadores de la tabla indicada
	 * 
	 * @param tableName			- Tabla en la que eliminar los elementos
	 * @param ids				- Lista de identificadores
	 * 
	 * @throws DBManagerException
	 */
	protected void deleteEntities (String tableName, ArrayList <Integer> ids) throws DBManagerException
	{
		if (!ids.isEmpty())
		{
			String deleteSQL = "delete from " + tableName + " where id in (" + getFormatedIds (ids) + ")";
			
			try {
				Statement statement = dbDriver.createStatement();
				statement.execute (deleteSQL);
				
				dbDriver.closeStatement (statement);
			} catch (SQLException sqlException) {
				throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
			}
			
			
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de empleados por sus ids
	 * @param ids - Lista de identificadores
	 * @throws DBManagerException
	 */
	protected void deleteEmployees (ArrayList<Integer> ids) throws DBManagerException
	{
		deleteEntities (Employee.SQL_TABLE_NAME, ids);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de clientes por sus ids
	 * 
	 * @param ids - Lista de identificadores
	 * @throws DBManagerException
	 */
	protected void deleteClients (ArrayList<Integer> ids) throws DBManagerException 
	{
		deleteEntities (Client.SQL_TABLE_NAME, ids);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de ordenes por sus ids
	 * 
	 * @param ids - Lista de identificadores
	 * @throws DBManagerException
	 */
	protected void deleteOrders (ArrayList<Integer> ids) throws DBManagerException 
	{
		deleteEntities (Order.SQL_TABLE_NAME, ids);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de proveedores por sus ids
	 * @param ids - Lista de identificadores
	 * @throws DBManagerException
	 */
	protected void deleteProviders (ArrayList<Integer> ids) throws DBManagerException 
	{
		deleteEntities (Provider.SQL_TABLE_NAME, ids);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de vehiculos por sus ids
	 * 
	 * @param ids - Lista de identificadores
	 * @throws DBManagerException
	 */
	protected void deleteVehicles (ArrayList<Integer> ids) throws DBManagerException 
	{
		deleteEntities (Vehicle.SQL_TABLE_NAME, ids);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de materiales por sus ids
	 * @param ids - Lista de identificadores
	 * @throws DBManagerException
	 */
	protected void deleteMaterials(ArrayList<Integer> ids) throws DBManagerException
	{
		deleteEntities (Material.SQL_TABLE_NAME, ids);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de materiales utilizados por la operacion por sus ids
	 * @param ids - Lista de identificadores
	 * @throws DBManagerException
	 */
	protected void deleteUsedMaterials(ArrayList<Integer> ids) throws DBManagerException
	{
		deleteEntities (UsedMaterial.SQL_TABLE_NAME, ids);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de operaciones por sus ids
	 * 
	 * @param ids - Lista de identificadores
	 * @throws DBManagerException
	 */
	protected void deleteOperations (ArrayList <Integer> ids) throws DBManagerException 
	{
		deleteEntities (Operation.SQL_TABLE_NAME, ids);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una entidad de la base de datos por su id
	 * 
	 * @param id - Identificador de la entidad
	 * @throws DBManagerException
	 */
	protected void deleteEntity (String tableName, int id) throws DBManagerException
	{
		try {
			Statement statement = dbDriver.createStatement ();

			String deleteSQL = "delete from " + tableName + " where id = " + id;
			statement.execute (deleteSQL);
			
			dbDriver.closeStatement (statement);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina un rol
	 * 
	 * @param idRole - Identificador del rol
	 * @throws DBManagerException
	 */
	protected void deleteRole (int idRole) throws DBManagerException
	{
		deleteEntity (Role.SQL_TABLE_NAME, idRole);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de servicios por sus ids 
	 * 
	 * @param ids - Lista de identificadores
	 * @throws DBManagerException
	 */
	protected void deleteServices (ArrayList<Integer> ids) throws DBManagerException 
	{
		deleteEntities (Service.SQL_TABLE_NAME, ids);
	}
	
    // --------------------------------------------------------------------------------------------
	/**
	 * Muestra informacion de depuracion en la consola cuando la depuracion esta habilitada
	 *  
	 * @param message		- Mensaje a mostrar por consola
	 */
	public static void printlnDebug(String message) 
	{
		if (debug)
		{
			System.out.println (message);
		}
	}

    // --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la representacion de fecha y hora segun el formato LocalDateTime necesario para la conversion desde 
	 * bases de datos Derby
	 * 
	 * @param timeStamp		- Valor con la fecha y hora de la base de datos
	 * @return Representacion de texto en formato LocalDateTime
	 */
	public static String getLocalDateTimeFormat (java.sql.Timestamp timeStamp)
	{
		if (timeStamp != null)
		{
			LocalDateTime localDateTime = timeStamp.toLocalDateTime(); 
	    	return String.format("%04d-%02d-%02dt%02d:%02d:%02d", 
	    			localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(),
	    			localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()
	    			);
		}
		
		return null;
	}
	
    // --------------------------------------------------------------------------------------------
    /**
     * Devuelve la rempresentacion de fecha y hora segun el formato iso 8601
     * @param localDateTime - Objeto de fecha y hora
     * @return Cadena de texto con la representacion en formato iso
     */
    public static String getIsoFormat (LocalDateTime localDateTime)
    {
    	return String.format("%04d-%02d-%02d %02d:%02d:%02d", 
    			localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(),
    			localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()
    			);
    }

	// --------------------------------------------------------------------------------------------
    /**
     * Devuelve una lista de identificadores en formato de texto separados por comas
     * 
     * @param ids		- Lista de identificadores
     * @return Cadena de texto con los identificadores separados por comas
     */
	private String getFormatedIds (ArrayList <Integer> ids)
	{
		String formatedIds = "";
		int i = 0, size = ids.size();
		for (int id : ids)
		{
			formatedIds += id + "";
			if (i++ < size - 1) formatedIds += ", ";
			i++;
		}

		return formatedIds;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * @return the debug
	 */
	public static boolean isDebug() {
		return debug;
	}


>>>>>>> V3.1-alertas
}
