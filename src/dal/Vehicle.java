package dal;

<<<<<<< HEAD
=======
import java.sql.ResultSet;
import java.sql.SQLException;

>>>>>>> V3.1-alertas
// ------------------------------------------------------------------------------------------------
/***
 * Representa la entidad de vehículo en la base de datos
 * 
 * @author G1
 *
 */
<<<<<<< HEAD

public class Vehicle extends DBEntity
{
=======
public class Vehicle extends DBEntity
{
	public static final String SQL_TABLE_NAME							= "Vehicle";
	
	public static final String SQL_COLUMN_NAME_REGISTRATION_NUMBER 		= "registrationNumber";
	public static final String SQL_COLUMN_NAME_MODEL				 	= "model";
	public static final String SQL_COLUMN_NAME_COLOR				 	= "color";
	public static final String SQL_COLUMN_NAME_ID_CLIENT			 	= "idClient";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_REGISTRATION_NUMBER,
			SQL_COLUMN_NAME_MODEL,
			SQL_COLUMN_NAME_COLOR,
			SQL_COLUMN_NAME_ID_CLIENT
			}; 
	
>>>>>>> V3.1-alertas
	private String registrationNumber;
	private String model;
	private String color;
	private int idClient;
	
<<<<<<< HEAD
	private static int autoId = 1;

	// ---------------------------------------------------------------------------------------------
	/**
	 * Inicializa el objeto vacío
	 */
	public Vehicle ()
	{
		id = autoId++;
	}
	
	// ---------------------------------------------------------------------------------------------
	public Vehicle (int id, String registrationNumber, String model, String color, int idClient) 
	{
		this.id 					= id <= DBManager.ID_AUTO ? autoId++ : id;
=======
	// ---------------------------------------------------------------------------------------------
	/**
	 * Inicializa el objeto vacio
	 */
	public Vehicle ()
	{
	}
	
	// ---------------------------------------------------------------------------------------------
	/**
	 * Inicializa un vehiculo segun sus valores
	 * 
	 * @param id 					- Identifica al vehiculo
	 * @param registrationNumber 	- Matricula 
	 * @param model					- Modelo
	 * @param color					- Color
	 * @param idClient				- Identifica al cliente propietario
	 */
	public Vehicle (int id, String registrationNumber, String model, String color, int idClient) 
	{
		this.id 					= id;
>>>>>>> V3.1-alertas
		this.registrationNumber 	= registrationNumber;
		this.model 					= model;
		this.color 					= color;
		this.idClient 				= idClient;
	}
<<<<<<< HEAD

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return registrationNumber.toLowerCase ().contains(filter) || model.toLowerCase ().contains(filter) || color.toLowerCase ().contains(filter);
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Vehicle.autoId = autoId;
	}
	
	// -----------------------------------------------------------------------------------
	public static void reset ()
	{
		autoId = 1;
=======
	
	// --------------------------------------------------------------------------------------------
	@Override
	public void setResultSet (ResultSet resultSet) throws SQLException
	{
		id						= resultSet.getInt (SQL_COLUMN_NAME_ID);
		registrationNumber		= resultSet.getString (SQL_COLUMN_NAME_REGISTRATION_NUMBER);
		model					= resultSet.getString (SQL_COLUMN_NAME_MODEL);
		color					= resultSet.getString (SQL_COLUMN_NAME_COLOR);
		idClient				= resultSet.getInt (SQL_COLUMN_NAME_ID_CLIENT);
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
	public String getSQLTableName ()
	{
		return SQL_TABLE_NAME;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public String[] getSQLColumnNames ()
	{
		return SQL_COLUMN_NAMES;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Object[] getEntityValues ()
	{
		return new Object[] {registrationNumber, model, color, idClient};
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return registrationNumber.toLowerCase ().contains(filter) || model.toLowerCase ().contains(filter) || color.toLowerCase ().contains(filter);
>>>>>>> V3.1-alertas
	}
	
	// ---------------------------------------------------------------------------------------------
	// GETTERS && SETTERS
	// ---------------------------------------------------------------------------------------------

	/**
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * @param registrationNumber the registrationNumber to set
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the idClient
	 */
	public int getIdClient() {
		return idClient;
	}

	/**
	 * @param idClient the idClient to set
	 */
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

}
