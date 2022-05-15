package dal;

// ------------------------------------------------------------------------------------------------
/***
 * Representa la entidad de Operacion en la base de datos
 * 
 * @author G1
 *
 */

public class Operation extends DBEntity
{
	// --------------------------------------------------------------------------------------------
	private int idOrder;
	private int idMechanic;
	private int idService;
	private int dedicatedTime;
	private double hourlyPriceApplied;
	
	private static int autoId = 1;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto de Operacion vacío
	 */
	public Operation ()
	{
		id = autoId++;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Da  valor a cada campo de las operaciones
	 * @param id													- Identificación de la  operación
	 * @param idOrder											- Identificación de la orden de trabajo de la operación
	 * @param idMechanic									- Identificación del mecánico
	 * @param idService										- Identificación del servicio de la reparación
	 * @param dedicatedTime								- Tiempo dedicado a la operación
	 * @param hourlyPriceApplied					- Precio por hora aplicado a la operación
	 */
	
	public Operation(int id, int idOrder, int idMechanic, int idService, int dedicatedTime, double hourlyPriceApplied) 
	{
		this.id 					= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.idOrder 				= idOrder;
		this.idMechanic				= idMechanic;
		this.idService 				= idService;
		this.dedicatedTime 			= dedicatedTime;
		this.hourlyPriceApplied 	= hourlyPriceApplied;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return true;
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Operation.autoId = autoId;
	}

	// -----------------------------------------------------------------------------------
	public static void reset ()
	{
		autoId = 1;
	}

	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------
	
	/**
	 * @return the idMechanic
	 */
	public int getIdMechanic() {
		return idMechanic;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the idOrder
	 */
	public int getIdOrder() {
		return idOrder;
	}

	/**
	 * @param idOrder the idOrder to set
	 */
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	/**
	 * @return the idService
	 */
	public int getIdService() {
		return idService;
	}

	/**
	 * @param idService the idService to set
	 */
	public void setIdService(int idService) {
		this.idService = idService;
	}

	/**
	 * @return the dedicatedTime
	 */
	public int getDedicatedTime() {
		return dedicatedTime;
	}

	/**
	 * @param dedicatedTime the dedicatedTime to set
	 */
	public void setDedicatedTime(int dedicatedTime) {
		this.dedicatedTime = dedicatedTime;
	}

	/**
	 * @param idMechanic the idMechanic to set
	 */
	public void setIdMechanic(int idMechanic) {
		this.idMechanic = idMechanic;
	}

	/**
	 * @return the hourlyPriceApplied
	 */
	public double getHourlyPriceApplied() {
		return hourlyPriceApplied;
	}

	/**
	 * @param hourlyPriceApplied the hourlyPriceApplied to set
	 */
	public void setHourlyPriceApplied(double hourlyPriceApplied) {
		this.hourlyPriceApplied = hourlyPriceApplied;
	}

}
