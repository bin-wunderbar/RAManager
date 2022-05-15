package dal;

import java.util.ArrayList;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

// ------------------------------------------------------------------------------------------------
/***
 * Gestor de la base de datos
 * 
 * @author G1
 *
 */
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
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de la base de datos y carga desde la unidad de almacenamiento la información de las entidades, admite simulación para evitar escribir o eliminar datos 
	 * @param sqlServer 	- Direccion del servidor de base de datos
	 * @param sqlUser 		- Usuario de acceso a la base de datos
	 * @param sqlPassword	- Contrasenna de acceso a la base de datos
	 * @param debug - Especifica si usar el modo de depuracion, agregando informacion de operaciones a la consola
	 */
	public DBManager (String sqlServer, String sqlUser, String sqlPassword, int selectedDriver, boolean debug)
	{
		initDBManager (sqlServer, sqlUser, sqlPassword, selectedDriver, debug);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de la base de datos, <strong>no</strong> carga informacón desde la unidad de almacenamiento
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
		}
	}
	
	// --------------------------------------------------------------------------------------------
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
	protected ArrayList <Order> getOrdersByEmployee (int idEmployee) throws DBManagerException
	{
		ArrayList <Order> employeeOrders = new ArrayList <> ();
		
		for (Order order : getOrders (""))
		{
			if (order.getIdEvaluator() == idEmployee) employeeOrders.add (order);
		}
		
		return employeeOrders;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de ordenes por cliente
	 * 
	 * @param idClient - Identificador del cliente
	 * @return La lista de clientes
	 * @throws DBManagerException
	 */
	protected ArrayList <Order> getOrdersByClient (int idClient) throws DBManagerException
	{
		ArrayList <Order> clientOrders = new ArrayList <> ();
		
		for (Order order : getOrders (""))
		{
			if (order.getIdClient() == idClient) clientOrders.add (order);
		}
		
		return clientOrders;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de empleados segun su rol
	 * 
	 * @param idRol - Identificador del rol
	 * @return La lista de empleados
	 * @throws DBManagerException
	 */
	protected ArrayList <Employee> getEmployeesByIdRol (int idRol) throws DBManagerException
	{
		ArrayList <Employee> employeesByIdRol = new ArrayList <> ();
		
		for (Employee employee : getEmployees (""))
		{
			if (employee.getIdRole() == idRol)
			{
				employeesByIdRol.add (employee);
			}
		}
		
		return employeesByIdRol;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de vehiculos por cliente
	 * 
	 * @param idClient - Identificador del cliente
	 * @return La lista de vehiculos
	 * @throws DBManagerException
	 */
	protected ArrayList <Vehicle> getClientVehicles (int idClient) throws DBManagerException
	{
		ArrayList <Vehicle> clientVehicles = new ArrayList <> ();
		
		for (Vehicle vehicle : getVehicles (""))
		{
			if (vehicle.getIdClient() == idClient) clientVehicles.add (vehicle);
		}
		
		return clientVehicles;
	}
	
	// --------------------------------------------------------------------------------------------
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


}
