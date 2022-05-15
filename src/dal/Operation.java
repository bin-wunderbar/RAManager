package dal;

import java.sql.ResultSet;
import java.sql.SQLException;

// ------------------------------------------------------------------------------------------------
/***
 * Representa la entidad de Operacion en la base de datos
 * 
 * @author G1
 *
 */
public class Operation extends DBEntity
{
	public static final String SQL_TABLE_NAME							= "Operation";
	
	public static final String SQL_COLUMN_NAME_ID_ORDER					= "idOrder";
	public static final String SQL_COLUMN_NAME_ID_MECHANIC				= "idMechanic";
	public static final String SQL_COLUMN_NAME_ID_SERVICE				= "idService";
	public static final String SQL_COLUMN_NAME_DEDICATED_TIME			= "dedicatedTime";
	public static final String SQL_COLUMN_NAME_HOURLY_PRICE_APPLIED		= "hourlyPriceApplied";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_ID_ORDER, 
			SQL_COLUMN_NAME_ID_MECHANIC, 
			SQL_COLUMN_NAME_ID_SERVICE, 
			SQL_COLUMN_NAME_DEDICATED_TIME, 
			SQL_COLUMN_NAME_HOURLY_PRICE_APPLIED 
			}; 
	
	private int idOrder;
	private int idMechanic;
	private int idService;
	private int dedicatedTime;
	private double hourlyPriceApplied;
	
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto de Operacion vacio
	 */
	public Operation ()
	{
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa una operacion
	 * @param id					- Identifica la operacion
	 * @param idOrder				- Identifica la orden de trabajo de la operacion
	 * @param idMechanic			- Identifica al mecanico
	 * @param idService				- Identifica al servicio
	 * @param dedicatedTime			- Tiempo dedicado a la operacion
	 * @param hourlyPriceApplied	- Precio por hora aplicado
	 */
	public Operation(int id, int idOrder, int idMechanic, int idService, int dedicatedTime, double hourlyPriceApplied) 
	{
		this.id 					= id;
		this.idOrder 				= idOrder;
		this.idMechanic				= idMechanic;
		this.idService 				= idService;
		this.dedicatedTime 			= dedicatedTime;
		this.hourlyPriceApplied 	= hourlyPriceApplied;
	}
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una consulta con todos los datos de la tabla
	 * 
	 * @return Consulta con todos los datos de la tabla
	 */
	public static String getQuery ()
	{
		return "select " + SQL_COLUMN_NAME_ID + ", " + getSQLParseColumnNames(SQL_COLUMN_NAMES) + " from " + SQL_TABLE_NAME;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void setResultSet (ResultSet resultSet) throws SQLException
	{
		this.id 				= resultSet.getInt (SQL_COLUMN_NAME_ID);
		this.idOrder 			= resultSet.getInt (SQL_COLUMN_NAME_ID_ORDER);
		this.idMechanic 		= resultSet.getInt (SQL_COLUMN_NAME_ID_MECHANIC);
		this.idService 			= resultSet.getInt (SQL_COLUMN_NAME_ID_SERVICE);
		this.dedicatedTime		= resultSet.getInt (SQL_COLUMN_NAME_DEDICATED_TIME);
		this.hourlyPriceApplied	= resultSet.getDouble (SQL_COLUMN_NAME_HOURLY_PRICE_APPLIED);
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public String getSQLTableName ()
	{
		return SQL_TABLE_NAME;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Object[] getEntityValues ()
	{
		return new Object[] {idOrder, idMechanic, idService, dedicatedTime, hourlyPriceApplied};
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public String[] getSQLColumnNames ()
	{
		return SQL_COLUMN_NAMES;
	}


	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return true;
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
