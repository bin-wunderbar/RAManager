package dal;

import java.sql.ResultSet;
import java.sql.SQLException;

// -----------------------------------------------------------------------------------
/***
 * Representa la entidad de estado en la base de datos
 * 
 * @author G1
 *
 */
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
	}
	
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa la entidad de estado
	 * 
	 * @param id		- Identificador del rol
	 * @param name		- Nombre del rol
	 */
	public Status (int id, String name)
	{
		this.id					= id;
		this.name				= name;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
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
