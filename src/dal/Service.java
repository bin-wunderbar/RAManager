package dal;

<<<<<<< HEAD
=======
import java.sql.ResultSet;
import java.sql.SQLException;

>>>>>>> V3.1-alertas
// ------------------------------------------------------------------------------------------------
/***
 * Representa la entidad de servicio en la base de datos
 *  
 * @author G1
 *
 */
<<<<<<< HEAD

public class Service extends DBEntity 
{
	private String description;
	private double hourPrice;
	
	private static int autoId = 1;
	
	
	public Service ()
	{
		id = autoId++;
=======
public class Service extends DBEntity 
{
	public static final String SQL_TABLE_NAME					= "Service";
	
	public static final String SQL_COLUMN_NAME_DESCRIPTION 		= "description";
	public static final String SQL_COLUMN_NAME_HOURLY_PRICE 	= "hourlyPrice";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_DESCRIPTION,
			SQL_COLUMN_NAME_HOURLY_PRICE,
			}; 
	
	private String description;
	private double hourPrice;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa un servicio nuevo
	 */
	public Service ()
	{
>>>>>>> V3.1-alertas
	}
	
	// --------------------------------------------------------------------------------------------
	/***
<<<<<<< HEAD
	 * Doy valor a todos los campos de los servicios
	 * @param id								- Indentificación de los  servicios
	 * @param description				- Descripción del servicio
=======
	 * Inicializa un servicio por sus parametros
	 * 
	 * @param id						- Indentifica el servicio
	 * @param description				- Descripcion del servicio
>>>>>>> V3.1-alertas
	 * @param hourPrice					- Precio por hora del servicio
	 */
	
	public Service (int id, String description, double hourPrice)
	{
<<<<<<< HEAD
		this.id				= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.description	= description;
		this.hourPrice		= hourPrice;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return getDescription ().toLowerCase ().contains(filter);
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Service.autoId = autoId;
	}

	// -----------------------------------------------------------------------------------
	public static void reset ()
	{
		autoId = 1;
	}

	// --------------------------------------------------------------------------------------------
=======
		this.id				= id;
		this.description	= description;
		this.hourPrice		= hourPrice;
	}
	// --------------------------------------------------------------------------------------------
	@Override
	public void setResultSet (ResultSet resultSet) throws SQLException
	{
		id				= resultSet.getInt (SQL_COLUMN_NAME_ID);
		description		= resultSet.getString (SQL_COLUMN_NAME_DESCRIPTION);
		hourPrice		= resultSet.getDouble (SQL_COLUMN_NAME_HOURLY_PRICE);
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
		return new Object[] {description, hourPrice};
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return getDescription ().toLowerCase ().contains(filter);
	}
	
	// --------------------------------------------------------------------------------------------
>>>>>>> V3.1-alertas
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the hourPrice
	 */
	public double getHourPrice() {
		return hourPrice;
	}

	/**
	 * @param hourPrice the hourPrice to set
	 */
	public void setHourPrice(double hourPrice) {
		this.hourPrice = hourPrice;
	}

}
