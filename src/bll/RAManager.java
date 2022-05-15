package bll;

import java.util.ArrayList;

import dal.Employee;
import dal.Material;
import dal.Operation;
import dal.Order;
import dal.Provider;
import dal.Role;
import dal.Service;
import dal.Status;
import dal.UsedMaterial;
import dal.Vehicle;
import dal.Client;
import dal.DBEntityList;
import dal.DBManagerException;

// ------------------------------------------------------------------------------------------------
/***
 * Gestor de la aplicación, proporciona acceso a datos en la capa de negocio y operaciones comunes
 *  
 * @author G1
 *
 */
public class RAManager extends dal.DBManager 
{
	private BEmployee activeLogin;
	private RALogging rALogging;
	private String infoDB;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de la capa de negocio, carga información y admite simulación (no escritura, no eliminación)
	 * 
	 * @param rAConfig		- Objeto de datos de configuracion
	 * @param debug			- Especifica si usar el modo de depuración, agregando infomración de operaciones a la consola
	 */
	public RAManager (RAConfig rAConfig, boolean debug)
	{
		super (rAConfig.getSqlServer (), rAConfig.getSqlUser(), rAConfig.getSqlPassword(), getSelectedDriver (rAConfig), debug);
		
		rALogging 		= new RALogging (rAConfig.getLoggingFileName());
		
		logPrintln (RALogging.LEVEL_INFO, "RAManager init");
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Traduce los identificadores de texto de motor de base de datos a codigos numericos
	 * 
	 * @param rAconfig	- Objeto de configuracion de la aplicacion
	 * @return Identificador numerico correspondiente con la base de datos indicada
	 */
	private static int getSelectedDriver (RAConfig rAconfig)
	{
		switch (rAconfig.getDataBaseEngine())
		{
			default:
			case RAConfig.ENGINE_MARIADB_STRING:
				return RAManager.DB_DRIVER_MARIADB;
			
			case RAConfig.ENGINE_DERBY_STRING:
				return RAManager.DB_DRIVER_DERBY;
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Conecta con la base de datos
	 * 
	 * @throws RAManagerException
	 */
	public void dataConnect () throws RAManagerException
	{
		try {
			super.connect ();
			infoDB = getInfo();
		} catch (DBManagerException exception) {
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Recarga las credenciales del usuario de conexion al servicio de base de datos
	 * 
	 * @param sqlUser		- Usuario de SQL
	 * @param sqlPassword	- Password de SQL
	 */
	public void reloadSqlCredentials (String sqlServer, String sqlUser, String sqlPassword) 
	{
		super.reloadSqlCredentials (sqlServer, sqlUser, sqlPassword);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda datos de demostracion basicos para presentar y/o probar la aplicacion
	 * @throws RAManagerException 
	 */
	public void saveDemo () throws RAManagerException
	{
		try
		{
			super.saveDataDemo ();
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta los valores de roles y empleado minimos para que la aplicacion comience a funcionar en limpio
	 * @throws RAManagerException 
	 */
	public void saveDataMinimalDefaults () throws RAManagerException
	{
		try
		{
			super.saveMinimalDefaults ();
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(),exception.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta el driver de acceso a la base de datos
	 */
	public void setSelectedDriver (int selectedDriver)
	{
		super.setSelectedDriver(selectedDriver);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Registra eventos de logging
	 * 
	 * @param logLevel - Nivel del evento
	 * @param message - Mensaje
	 */
	public void logPrintln (int logLevel, String message)
	{
		rALogging.println (logLevel, message);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Cierra la aplicacion
	 */
	public void close() 
	{
		logPrintln (RALogging.LEVEL_INFO, "RAManager close");
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve todos los roles filtrados
	 * 
	 * @param filter - Filtro de rol
	 * @return - Lista de roles
	 * @throws RAManagerException
	 */
	public ArrayList <BRole> getAllBRoles (String filter) throws RAManagerException
	{
		try
		{
			ArrayList <BRole> bRoles = new ArrayList <> ();
			
			for (Role role : getRoles (filter))
			{
				BRole bRole = new BRole (role);
				bRoles.add(bRole);
			}
			
			return bRoles;			
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve todos los  clientes con sus vehiculo y sus ordenes de trabajo
	 * 
	 * @param filter - Filtro
	 * @return La lista de clientes
	 */
	public ArrayList <BClient> getAllBClients (String filter) throws RAManagerException
	{
		try
		{
			
			ArrayList <BClient> bClients = new ArrayList <> ();
			
			for (Client client : getClients (filter))
			{
				BClient bClient = new BClient (client);
				bClient.setVehicles(getBClientBVehicles(client.getId()));
				bClient.setOrders(getBOrdersByClient (client.getId()));
				bClients.add(bClient);
			}
			
			return bClients;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Busca el cliente a traves del DNI
	 * @param clientDNI		- DNI del cliente guardados 
	 * @return				- El cliente si es localizado <strong>null</strong> en caso contrario
	 */
	public BClient getBClientByDNI(String clientDNI) throws RAManagerException
	{
		ArrayList <BClient> bClients = getAllBClients ("");
		BClient bClientDNI = null;
		
		for (BClient client : bClients)
		{
			if (client.getNIF().toLowerCase().equals(clientDNI.toLowerCase()))
			{
				bClientDNI = client;
				break;
			}
		}
		
		return bClientDNI;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un cliente por su id
	 * 
	 * @param idClient - Id del cliente
	 * @return - El cliente
	 * @throws RAManagerException
	 */
	public BClient getBClientById (int idClient) throws RAManagerException
	{
		ArrayList <BClient> bClients = getAllBClients ("");
		BClient bClient = null;
		
		for (BClient client : bClients)
		{
			if (client.getId() == idClient)
			{
				bClient = client;
				break;
			}
		}
		
		return bClient;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve todas las ordenes filtradas
	 * 
	 * @param filter - Filtro de la orden
	 * @return - Lista de ordenes
	 * @throws RAManagerException
	 */
	public ArrayList<BOrder> getAllBOrders (String filter) throws RAManagerException
	{
		try
		{
			ArrayList <BOrder> allBOrders = new ArrayList <> ();
			
			for (Order order : getOrders (filter))
			{
				allBOrders.add (newBOrder (order));
			}
			
			return allBOrders;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve el cliente con sus  ordenes y cada un de sus operaciones
	 * @param idClient				- Identificador del cliente 
	 * @return	La lista de órdenes
	 */
	public ArrayList <BOrder> getBOrdersByClient (int idClient) throws RAManagerException
	{
		try
		{
			ArrayList <BOrder> bClientBOrders = new ArrayList <> ();
			
			for (Order order : getOrdersByClient (idClient))
			{
				bClientBOrders.add(newBOrder (order));
			}
			
			return bClientBOrders;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve las ordenes por empleado asignado
	 * 
	 * @param bEmployee - Empleado asignado a la orden
	 * @param idStatus - Id de estado
	 * @param searchText - Texto a buscar
	 * @param searchInDescription - Buscar en la descricion
	 * @param searchInNIF - Buscar en el NIF
	 * @return - Ordenes del empleado
	 * @throws RAManagerException
	 */
	public ArrayList <BOrder> getBOrdersByEmployee (BEmployee bEmployee, int idStatus, String searchText, boolean searchInDescription, boolean searchInNIF)  throws RAManagerException
	{
		return getBOrdersByEmployee (bEmployee.getId(), idStatus, searchText, searchInDescription, searchInNIF);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve las ordenes por empleado asignado
	 * 
	 * @param idEmployee - Id de empleado asignado a la orden
	 * @param idStatus - Id de estado
	 * @param searchText - Texto a buscar
	 * @param searchInDescription - Buscar en la descricion
	 * @param searchInNIF - Buscar en el NIF
	 * @return - Ordenes del empleado
	 * @throws RAManagerException
	 */
	public ArrayList <BOrder> getBOrdersByEmployee (int idEmployee, int idStatus, String searchText, boolean searchInDescription, boolean searchInNIF)  throws RAManagerException
	{
		try
		{
			ArrayList <BOrder> bEmployeeOrders = new ArrayList <> ();
			
			for (Order order : getOrdersByEmployee (idEmployee))
			{
				if (idStatus <= NO_ID || idStatus == order.getIdStatus())
				{
					if (searchText != null && !searchText.isEmpty())
					{
						boolean match = false;
						
						if (searchInDescription)
						{
							if (order.getDescription().toLowerCase().contains(searchText.toLowerCase()))
							{
								match = true;
							}
						}
						
						if (searchInNIF)
						{
							BClient bClient = this.getBClientById(order.getIdClient());
							if (bClient != null && bClient.getNIF ().toLowerCase().contains(searchText.toLowerCase()))
							{
								match = true;
							}
						}
						
						if (match)
						{
							bEmployeeOrders.add (newBOrder (order));
						}
						
					}
					else
					{
						bEmployeeOrders.add (newBOrder (order));
					}
				}

			}
			
			return bEmployeeOrders;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Crea una nueva orden en la capa de negocio con los listados asociados
	 * 
	 * @param order - Orden de la capa de datos
	 * @return - Orden de la capa de negocio
	 * @throws RAManagerException
	 */
	private BOrder newBOrder (Order order)  throws RAManagerException
	{
		try
		{
			BOrder bOrder = new BOrder (order, getBEmployeeById (order.getIdEvaluator()));
			bOrder.setBOperations(getBOperationsByIdOrder (order.getId()));
			
			/*
			 * Solo para mostrar información de objetos, sin el anidamiento correspondiente (puede ocasionar stack overflow) 
			 */
			
			Client client 	= getClients ("").getByPrimaryKey(order.getIdClient());
			Vehicle vehicle = getVehicles ("").getByPrimaryKey(order.getIdVehicle());
			Status status 	= getStatuses ("").getByPrimaryKey(order.getIdStatus()); 
			
			bOrder.setBClient (client == null ? null : new BClient (client));
			bOrder.setBVehicle (vehicle == null ? null : new BVehicle (vehicle));
			bOrder.setBStatus (status == null ? null : new BStatus (status));
			
			return bOrder;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener la orden de trabajo a traves del DNI
	 * @param orderId		- Identificación de la orden de trabajo
	 * @return				- La orden de trabajo cuando es localizada <strong>null</strong> en caso contrario
	 */
	public BOrder getBOrderById (int orderId) throws RAManagerException
	{
		try
		{
			Order order = getOrder (orderId);
			
			return order == null ? null : newBOrder (order);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Obtiene las operaciones de una orden de trabajo por el ID de la orden
	 *  
	 * @param idOrder		- Identificador de la orden de trabajo
	 * @return				- Lista con las operaciones
	 */
	private ArrayList<BOperation> getBOperationsByIdOrder (int idOrder) throws RAManagerException
	{
		ArrayList <BOperation> bOperations = new ArrayList <> ();
		
		for (BOperation bOperation : getAllBOperations (""))
		{
			if (bOperation.getIdOrder() == idOrder) bOperations.add(bOperation);
		}
		
		return bOperations;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Deuvelve un empleado por su id
	 * @param id - Id del empleado
	 * @return - Empleado
	 * @throws RAManagerException
	 */
	public BEmployee getBEmployeeById (int id) throws RAManagerException
	{
		try
		{
			Employee employee = getEmployee (id);

			if (employee != null)
			{
				return getNewBEmployee (employee); 
			}
			
			return null;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Deuvelve un empleado nuevo en la capa de negocio con sus atributos relacionados
	 * 
	 * @param employee - Empleado de la capa de datos
	 * @return - Empleado de la capa de negocio
	 * @throws RAManagerException
	 */
	private BEmployee getNewBEmployee (Employee employee) throws RAManagerException
	{
		try
		{
			BEmployee bEmployee = new BEmployee (employee);
			bEmployee.setRole(new BRole (getRole (employee.getIdRole())));
			
			return bEmployee; 
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener la operacion deseada a traves del numero de iidentificacion
	 * @param operationId				- Identificación de  la operación
	 * @return	La operación cuando es localizada <strong>null</strong> en caso contrario
	 */
	public BOperation getBOperationById (int operationId) throws RAManagerException
	{
		try
		{
			Operation operation = getOperation (operationId);
			return operation == null ? null : buildBOperation (operation);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Obtener el material a traves de su identificacion
	 * @param id				- Identificación del material de la reparación
	 * @return	El material cuando es localizado <strong>null</strong> en caso contrario
	 */
	public BMaterial getBMaterialById (int id) throws RAManagerException
	{
		try
		{
			Material material = getMaterial (id);
			return material == null ? null : new BMaterial (material);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener los datos de todos los empleados
	 * 
	 * @param filter - Filtro
	 * @return	La lista con todos los empleados 
	 */
	public ArrayList <BEmployee> getAllBEmployees (String filter) throws RAManagerException
	{
		try
		{
			ArrayList <BEmployee> bEmployees = new ArrayList <> ();
			
			for (Employee employee : getEmployees (filter))
			{
				bEmployees.add (getNewBEmployee (employee));
			}
			
			return bEmployees;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener todos los vehiculos
	 * 
	 * @param filter - Filtro
	 * @return La lista de vehículos
	 */
	public ArrayList<BVehicle> getAllBVehicles (String filter) throws RAManagerException
	{
		try
		{
			ArrayList <BVehicle> bVehicles = new ArrayList <> ();
			
			for (Vehicle vehicle : getVehicles (filter))
			{
				bVehicles.add (new BVehicle (vehicle));
			}
			
			return bVehicles;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener todos los vehiculos de los clientes
	 * @param idClient					- Identificador del cliente 
	 * @return	La lista de vehículos
	 */
	public ArrayList <BVehicle> getBClientBVehicles (int idClient)  throws RAManagerException
	{
		try
		{
			ArrayList <BVehicle> bClientBVehicles = new ArrayList <> ();
			
			for (Vehicle vehicle : getClientVehicles (idClient))
			{
				bClientBVehicles.add(new BVehicle (vehicle));
			}
			
			return bClientBVehicles;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un vehiculo por su id
	 * 
	 * @param idVehicle - Id del vehiculo
	 * @return - El vehiculo
	 * @throws RAManagerException
	 */
	public BVehicle getBVehicleById (int idVehicle)   throws RAManagerException
	{
		try
		{
			Vehicle vehicle = getVehicle (idVehicle);

			return vehicle == null ? null : new BVehicle (vehicle);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuevle un proveedor por su id
	 * 
	 * @param idProvider - Id del proveedor
	 * @return - El proveedor
	 * @throws RAManagerException
	 */
	public BProvider getBProviderById(int idProvider) throws RAManagerException 
	{
		try
		{
			Provider provider = getProvider (idProvider);

			return provider == null ? null : new BProvider (provider);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Crear la opperacion deseada para el cliente elegido
	 * @param operation				- Operación de la reparación
	 * @return La operación cuando es localizada <strong>null</strong> en caso contrario
	 */
	private BOperation buildBOperation (Operation operation)  throws RAManagerException
	{
		BOperation bOperation = new BOperation (operation, 
				getBEmployeeById (operation.getIdMechanic()),
				getBServiceById (operation.getIdService())
				);
		bOperation.setBUsedMaterials (getBUsedMaterialsByOperationId (operation.getId()));
		
		return bOperation;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener todas las operaciones
	 * @return La lista de operaciones
	 */
	public ArrayList <BOperation> getAllBOperations (String filter)  throws RAManagerException
	{
		try
		{
			ArrayList <BOperation> bOperations = new ArrayList <> ();
			
			for (Operation operation : getOperations (filter))
			{
				BOperation bOperation = buildBOperation (operation);
				bOperations.add(bOperation);
			}
			
			return bOperations;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener los materiales utilizados a traves del numero de identifcacion de la operacion
	 * @param idOperation					- Identificación de la operacion de la reparación
	 * @return La lista de los materiales utilizados	
	 */
	private ArrayList<BUsedMaterial> getBUsedMaterialsByOperationId (int idOperation)  throws RAManagerException
	{
		ArrayList <BUsedMaterial> bUsedMaterials = new ArrayList <> ();
		
		for (BUsedMaterial bUsedMaterial : getAllBUsedMaterials (""))
		{
			if (bUsedMaterial.getIdOperation() == idOperation)
			{
				bUsedMaterials.add(bUsedMaterial);
			}
		}
		
		return bUsedMaterials;
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener el servicio  traves de su identificacion
	 * @param idService	- Identificación del servicio ofrecido en la reparación
	 * @return El servicio cuando es localizado <strong>null</strong> en caso contrario
	 */
	public BService getBServiceById(int idService) throws RAManagerException
	{
		for (BService bService : getAllBServices (""))
		{
			if (bService.getId() == idService) return bService;
		}
		
		return null;
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener el material a traves de su identifcacion
	 * @param bMaterials - Materiales 
	 * @param id - Identificación de los materiales
	 * @return El material cuando es localizado <strong>null</strong> en caso contrario
	 */
	
	public BMaterial getBMaterialById(ArrayList<BMaterial> bMaterials, int id) throws RAManagerException
	{
		for (BMaterial bMaterial : bMaterials)
		{
			if (bMaterial.getId() == id) return bMaterial;
		}
			
		return null;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Deuvelve una lista de empleados por su id de rol
	 * @param idRole
	 * @return Lista de empleados
	 * @throws RAManagerException
	 */
	public ArrayList <BEmployee> getBEmployeesByIdRole (int idRole) throws RAManagerException
	{
		try
		{
			ArrayList <BEmployee> bEmployees = new ArrayList <> ();
			
			for (Employee employee : getEmployeesByIdRol (idRole))
			{
					bEmployees.add (getNewBEmployee (employee));
			}
			
			return bEmployees;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Obtener todos los materiales utilizados
	 * 
	 * @param filter - Filtro
	 * @return La lista de los materiales utilizados
	 */
	public ArrayList <BUsedMaterial> getAllBUsedMaterials (String filter)  throws RAManagerException
	{
		try
		{
			ArrayList <BUsedMaterial> bUsedMaterials = new ArrayList <> ();
			
			for (UsedMaterial usedMaterial : getUsedMaterials (filter))
			{
				bUsedMaterials.add(new BUsedMaterial (usedMaterial, getBMaterialById (usedMaterial.getIdMaterial())));
			}
			
			return bUsedMaterials;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Obtiene todos los proveedores
	 * 
	 * @param filter - Filtro
	 * @return Lista de proveedores
	 */
	public ArrayList<BProvider> getAllBProviders (String filter)  throws RAManagerException
	{
		try
		{
			ArrayList <BProvider> bProviders = new ArrayList <> ();
			
			for (Provider provider : getProviders (filter))
			{
				bProviders.add (new BProvider (provider));
			}
			
			return bProviders;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener todos los servicios
	 * 
	 * @param filter - Filtro
	 * @return La lista de servicios
	 */
	public ArrayList <BService> getAllBServices (String filter)  throws RAManagerException
	{
		try
		{
			ArrayList <BService> bServices = new ArrayList <> ();
			
			for (Service service : getServices (filter))
			{
				bServices.add(new BService (service));
			}
			
			return bServices;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Obtener todos los materiales
	 * 
	 * @param filter - Filtro
	 * @return La lista de los materiales
	 */
	public ArrayList <BMaterial> getAllBMaterials (String filter)  throws RAManagerException
	{
		try
		{
			ArrayList <BMaterial> bMaterials = new ArrayList <> ();
			
			for (Material material : getMaterials (filter))
			{
				bMaterials.add(new BMaterial (material));
			}
			
			return bMaterials;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve todos los estados de encargo disponibles
	 * 
	 * @param filter - Filtro
	 * @return Estados disponibles
	 */
	public ArrayList<BStatus> getAllBStatus (String filter)  throws RAManagerException
	{
		try
		{
			ArrayList <BStatus> bStatuses = new ArrayList <> ();
			
			for (Status status : getStatuses (filter))
			{
				bStatuses.add (new BStatus (status));
			}
			
			return bStatuses;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Deuvelve un estado segun su id
	 * 
	 * @param id - Id del estado
	 * @return Estado de la capa de negocio
	 * @throws RAManagerException
	 */
	public BStatus getBStatusById (int id) throws RAManagerException
	{
		try
		{
			Status status = getStatus (id);

			return status == null ? null : new BStatus (status);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	

	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda un cliente
	 * 
	 * @param bClient - Cliente
	 * @throws RAManagerException
	 */
	public void save (BClient bClient) throws RAManagerException
	{
		try
		{
			super.save (bClient);
			rALogging.println(RALogging.LEVEL_INFO, "Client " + (bClient.getId() == NO_ID ? "add" : "update") + " (" + bClient.getName () + ":" + bClient.getNIF() + ")");
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode (), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda un empleado
	 * 
	 * @param bEmployee - Empleado
	 * @throws RAManagerException
	 */
	public void save (BEmployee bEmployee) throws RAManagerException
	{
		try
		{
			super.save (bEmployee);
			rALogging.println(RALogging.LEVEL_INFO, "Employee " + (bEmployee.getId() == NO_ID ? "add" : "update") + " (" + bEmployee.getName() + ":" + bEmployee.getNIF() + ")");
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda un material
	 * 
	 * @param bMaterial - Material
	 * @throws RAManagerException
	 */
	public void save (BMaterial bMaterial) throws RAManagerException
	{
		try
		{
			super.save (bMaterial);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda una orden
	 * 
	 * @param bOrder - Orden
	 * @throws RAManagerException
	 */
	public void save (BOrder bOrder) throws RAManagerException
	{
		try
		{
			super.save (bOrder);
			rALogging.println(RALogging.LEVEL_INFO, "Order save (" + (bOrder.getId() == RAManager.ID_NEW ? "new" : bOrder.getId()) + ":" + bOrder.getDescription() + ")");
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda un proveedor
	 * 
	 * @param bProvider - Proveedor
	 * @throws RAManagerException
	 */
	public void save (BProvider bProvider) throws RAManagerException
	{
		try
		{
			super.save (bProvider);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda un vehiculo
	 * 
	 * @param bVehicle - Vehiculo
	 * @throws RAManagerException
	 */
	public void save (BVehicle bVehicle) throws RAManagerException
	{
		try
		{
			super.save (bVehicle);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode (), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda un servicio
	 * 
	 * @param bService - Servicio
	 * @throws RAManagerException
	 */
	public void save(BService bService) throws RAManagerException 
	{
		try
		{
			super.save (bService);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Guarda una operacion
	 * @param bOperation - Operacion guardada en el taller
	 */
	public void save (BOperation bOperation) throws RAManagerException
	{
		try
		{
			super.save (bOperation);
			
			for (BUsedMaterial bUsedMaterial : bOperation.getBUsedMaterials())
			{
				if (bUsedMaterial.getIdOperation() <= NO_ID)
				{
					bUsedMaterial.setIdOperation(bOperation.getId());
				}
				
				save (bUsedMaterial);
			}
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda una lista de roles
	 * 
	 * @param roleList - Lista de roles
	 * @throws RAManagerException
	 */
	public void save (ArrayList<BRole> roleList) throws RAManagerException
	{
		try
		{
			for (BRole bRole : roleList)
			{
				save(bRole);
			}
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de empleados segun sus ids
	 * 
	 * @param ids - Lista de ids
	 * @throws RAManagerException
	 */
	public void deleteEmployeesByIds (ArrayList <Integer> ids) throws RAManagerException 
	{
		try
		{
			DBEntityList <Employee> employees = getEmployeesByIds (ids);
			super.deleteEmployees (ids);
			
			for (Employee employee : employees)
			{
				rALogging.println(RALogging.LEVEL_INFO, "Employee remove (" + employee.getNIF() + ":" + employee.getName() + ")");
			}
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de clientes segun sus ids
	 * 
	 * @param ids - Lista de ids
	 * @throws RAManagerException
	 */
	public void deleteClientsByIds(ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			DBEntityList <Client> clients = getClientsByIds (ids);
			super.deleteClients (ids);
			
			for (Client client : clients)
			{
				rALogging.println(RALogging.LEVEL_INFO, "Client remove (" + client.getNIF() + ":" + client.getName() + ")");
			}
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de ordenes segun sus ids
	 * 
	 * @param ids - Lista de ids
	 * @throws RAManagerException
	 */
	public void deleteOrdersByIds (ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			DBEntityList <Order> orders = getOrdersByIds (ids);
			super.deleteOrders (ids);
			
			for (Order order : orders)
			{
				rALogging.println(RALogging.LEVEL_INFO, "Order remove (" + order.getId() + ":" + order.getDescription() + ")");
			}
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de proveedores segun sus ids
	 * 
	 * @param providerIds - Lista de ids
	 * @throws RAManagerException
	 */
	public void deleteProvidersByIds (ArrayList<Integer> providerIds) throws RAManagerException 
	{
		try
		{
			super.deleteProviders (providerIds);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina roles, restringiendo la eliminación cuando el rol está siendo utilizado por algún empleado.
	 * @param idRol - Id del rol a eliminar
	 * @return - Lista de empleados que usan el rol, 0 si el rol ha sido eliminado, mayor que 0 si no ha podido ser eliminado
	 * @throws RAManagerException
	 */
	public ArrayList <BEmployee> deleteRoleRestrict (int idRol) throws RAManagerException 
	{
		try
		{
			ArrayList <BEmployee> bEmployees = getBEmployeesByIdRole (idRol);
			
			// Restricción de eliminación por uso de un empleados
			if (bEmployees.isEmpty())
			{
				super.deleteRole (idRol);
			}
			
			return bEmployees;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de vehiculos segun sus ids
	 * @param ids - Lista de ids
	 * @throws RAManagerException
	 */
	public void deleteVehiclesByIds (ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteVehicles (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de materiales segun su lista de ids
	 * 
	 * @param ids - Lista de ids
	 * @throws RAManagerException
	 */
	public void deleteMaterialsByIds (ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteMaterials (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de materiales utilizados segun su lista de ids
	 *  
	 * @param ids - Lista de ids
	 * @throws RAManagerException
	 */
	public void deleteUsedMaterialsByIds(ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteUsedMaterials (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de operaciones segun sus ids
	 * 
	 * @param ids - Lista de ids
	 * @throws RAManagerException
	 */
	public void deleteOperationsByIds (ArrayList <Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteOperations (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una lista de servicios segun sus ids
	 * 
	 * @param ids - Lista de ids
	 * @throws RAManagerException
	 */
	public void deleteServicesByIds (ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteServices (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------
	/**
	 * @return the activeLogin
	 */
	public BEmployee getActiveLogin() {
		return activeLogin;
	}

	/**
	 * Ajusta el usuario que ha iniciado sesión
	 * @param login Usuario que ha inicado sesión
	 */
	public void setActiveLogin(BEmployee login) {
		this.activeLogin = login;
		logPrintln (RALogging.LEVEL_INFO , "Logged in: " + login);
	}

	/**
	 * @return the rALogging
	 */
	public RALogging getRALogging() {
		return rALogging;
	}

	/**
	 * @param rALogging the rALogging to set
	 */
	public void setrALogging(RALogging rALogging) {
		this.rALogging = rALogging;
	}

	/**
	 * @return the infoDB
	 */
	public String getInfoDB() {
		return infoDB;
	}

	/**
	 * Utiliza una base de datos Derby
	 */
	public void useDerbyDemo() {
		setSelectedDriver(DB_DRIVER_MARIADB);
	}


}
