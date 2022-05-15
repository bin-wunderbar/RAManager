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
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de la capa de negocio, carga información y admite simulación (no escritura, no eliminación)
	 * @param databaseConnection - Directorio de almacenamiento de la base de datos
	 * @param simulation - Permite operarar con los datos cargados pero evita la escritura y eliminación de datos
	 * @throws DBManagerException 
	 */
	public RAManager (RAConfig rAConfig, boolean simulation)
	{
		super (rAConfig.getDatabaseConnection (), simulation);
		rALogging 		= new RALogging (rAConfig.getLoggingFileName());
		
		logPrintln (RALogging.LEVEL_INFO, "RAManager init");
	}

	// --------------------------------------------------------------------------------------------
	public void dataConnect () throws RAManagerException
	{
		try {
			super.connect ();
		} catch (DBManagerException exception) {
			throw new RAManagerException (rALogging, exception.getMessage());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	public void setMinimalDataDefaults ()
	{
		super.setMinimalDataDefaults();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el gestor de la capa de negocio, <strong>no</string> carga información
	 * @param databaseConnection - Directorio de almacenamiento de la base de datos
	 */
	public RAManager (String databaseConnection)
	{
		super (databaseConnection);
	}
	
	// --------------------------------------------------------------------------------------------
	public void logPrintln (int logLevel, String message)
	{
		rALogging.println (logLevel, message);
	}
	
	// --------------------------------------------------------------------------------------------
	public void saveDataDemo ()
	{
		try {
			super.saveDataDemo ();
		} catch (DBManagerException exception) {
			logPrintln (RALogging.LEVEL_CRITICAL, exception.getMessage());
			exception.printStackTrace();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	public void close() 
	{
		logPrintln (RALogging.LEVEL_INFO, "RAManager close");
	}
	
	// --------------------------------------------------------------------------------------------
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve todos los  clientes con sus vehiculo y sus ordenes de trabajo
	 * 
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
			throw new RAManagerException (rALogging, exception.getMessage ());
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve el cliente con sus  ordenes y cada un de sus operaciones
	 * @param clientDni				- DNI del cliente guardados 
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
			throw new RAManagerException (rALogging, exception.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	public ArrayList <BOrder> getBOrdersByEmployee (BEmployee bEmployee, int idStatus, String searchText, boolean searchInDescription, boolean searchInNIF)  throws RAManagerException
	{
		return getBOrdersByEmployee (bEmployee.getId(), idStatus, searchText, searchInDescription, searchInNIF);
	}
	
	// --------------------------------------------------------------------------------------------
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
			throw new RAManagerException (rALogging, exception.getMessage());
		}
	}
	
	// --------------------------------------------------------------------------------------------
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
			throw new RAManagerException (rALogging, exception.getMessage());
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
			throw new RAManagerException (rALogging, exception.getMessage());
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
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
			throw new RAManagerException (rALogging, exception.getMessage ());
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
			throw new RAManagerException (rALogging, exception.getMessage ());
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener los datos de todos los empleados
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener todos los vehiculos
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener todos los vehiculos de los clientes
	 * @param clientDni					- DNI del cliente guardados 
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	public BVehicle getBVehicleById (int idVehicle)   throws RAManagerException
	{
		try
		{
			Vehicle vehicle = getVehicleById (idVehicle);

			return vehicle == null ? null : new BVehicle (vehicle);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
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
			throw new RAManagerException (rALogging, exception.getMessage ());
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
	 * @param idService					- Identificación del servicio ofrecido en la reparación
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
	 * @param bMaterials				- Materiales 
	 * @param id								- Identificación de los materiales
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
	public ArrayList <BEmployee> getBEmployeesByIdRol (int idRol) throws RAManagerException
	{
		try
		{
			ArrayList <BEmployee> bEmployees = new ArrayList <> ();
			
			for (Employee employee : getEmployeesByIdRol (idRol))
			{
					bEmployees.add (getNewBEmployee (employee));
			}
			
			return bEmployees;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Obtener todos los materiales utilizados
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Obtiene todos los proveedores
	 * @return
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Obtener todos los servicios
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Obtener todos los materiales
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve todos los estados de encargo disponibles
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	public BStatus getBStatusById (int id) throws RAManagerException
	{
		try
		{
			Status status = getStatus (id);

			return status == null ? null : new BStatus (status);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}
	}
	

	// --------------------------------------------------------------------------------------------
	public void save (BClient bClient) throws RAManagerException
	{
		try
		{
			super.save (bClient);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode (), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	public void save (BEmployee bEmployee) throws RAManagerException
	{
		try
		{
			super.save (bEmployee);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getErrorCode(), exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	public void save (BMaterial bMaterial) throws RAManagerException
	{
		try
		{
			super.save (bMaterial);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	public void save (BOrder bOrder) throws RAManagerException
	{
		try
		{
			super.save (bOrder);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	public void save (BProvider bProvider) throws RAManagerException
	{
		try
		{
			super.save (bProvider);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
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
	public void save(BService bService) throws RAManagerException 
	{
		try
		{
			super.save (bService);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Guarda una operacion
	 * @param bOperation					- Operacion guardada en el taller
	 */
	public void save (BOperation bOperation) throws RAManagerException
	{
		try
		{
			super.save (bOperation);
			
			deleteUsedMaterialsByOperationId (bOperation.getId());
			
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
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
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	public void deleteEmployeesByIds (ArrayList <Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteEmployees (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	public void deleteClientsByIds(ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteClients (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	public void deleteOrdersByIds (ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteOrders (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	public void deleteProvidersByIds (ArrayList<Integer> providerIds) throws RAManagerException 
	{
		try
		{
			super.deleteProviders (providerIds);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
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
			ArrayList <BEmployee> bEmployees = getBEmployeesByIdRol (idRol);
			
			// Restricción de eliminación por uso de un empleados
			if (bEmployees.isEmpty())
			{
				super.deleteRole (idRol);
			}
			
			return bEmployees;
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}

	// --------------------------------------------------------------------------------------------
	public void deleteVehiclesByIds (ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteVehicles (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	public void deleteMaterialsByIds (ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteMaterials (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	public void deleteOperationsByIds (ArrayList <Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteOperations (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
		}

	}
	
	// --------------------------------------------------------------------------------------------
	public void deleteServicesByIds (ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			super.deleteServices (ids);
		}
		catch (DBManagerException exception)
		{
			throw new RAManagerException (rALogging, exception.getMessage ());
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


}