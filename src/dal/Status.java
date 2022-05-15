package dal;

<<<<<<< HEAD
=======
import java.sql.ResultSet;
import java.sql.SQLException;

>>>>>>> V3.1-alertas
// -----------------------------------------------------------------------------------
/***
 * Representa la entidad de estado en la base de datos
 * 
 * @author G1
 *
 */
<<<<<<< HEAD

public class Status extends DBEntity
{
	private String name;
	
	private static int autoId = 1;
	
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto vacío
	 */
	public Status ()
	{
		id = autoId++;
=======
public class Status extends DBEntity
{
	public static final String SQL_TABLE_NAME			= "Status";
	
	public static final String SQL_COLUMN_NAME_NAME 	= "name";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_NAME
			}; 
	
	private String name;
	
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto vacio
	 */
	public Status ()
	{
>>>>>>> V3.1-alertas
	}
	
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa la entidad de estado
	 * 
<<<<<<< HEAD
	 * @param id							- Identificador del rol
	 * @param name							- Nombre del rol
	 */
	public Status (int id, String name)
	{
		this.id					= id <= DBManager.ID_AUTO ? autoId++ : id;
=======
	 * @param id		- Identificador del rol
	 * @param name		- Nombre del rol
	 */
	public Status (int id, String name)
	{
		this.id					= id;
>>>>>>> V3.1-alertas
		this.name				= name;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
<<<<<<< HEAD
	public boolean containsFilter (String filter)
	{
		return name.toLowerCase ().contains(filter);
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Status.autoId = autoId;
	}

	// -----------------------------------------------------------------------------------
	public static void reset ()
	{
		autoId = 1;
	}

=======
	public void setResultSet (ResultSet resultSet) throws SQLException
	{
		id				= resultSet.getInt (SQL_COLUMN_NAME_ID);
		name			= resultSet.getString (SQL_COLUMN_NAME_NAME);
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
		return new Object[] {name};
	}

	
	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return name.toLowerCase ().contains(filter);
	}
	
>>>>>>> V3.1-alertas
	// -----------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// -----------------------------------------------------------------------------------
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
