package dal;

import java.sql.ResultSet;
import java.sql.SQLException;

// -----------------------------------------------------------------------------------
/***
 * Representa la entidad de Rol en la base de datos
 * 
 * @author G1
 *
 */
public class Role extends DBEntity
{
	public static final String SQL_TABLE_NAME							= "Role";
	
	public static final String SQL_COLUMN_NAME_NAME 					= "name";
	public static final String SQL_COLUMN_NAME_PERMISSION_READ 			= "permissionRead";
	public static final String SQL_COLUMN_NAME_PERMISSION_WRITE 		= "permissionWrite";
	public static final String SQL_COLUMN_NAME_PERMISSION_REMOVE 		= "permissionRemove";
	public static final String SQL_COLUMN_NAME_PERMISSION_MANAGEMENT 	= "permissionManagement";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_NAME,
			SQL_COLUMN_NAME_PERMISSION_READ,
			SQL_COLUMN_NAME_PERMISSION_WRITE,
			SQL_COLUMN_NAME_PERMISSION_REMOVE,
			SQL_COLUMN_NAME_PERMISSION_MANAGEMENT
			}; 
	
	private String name;
	
	private boolean permissionRead;
	private boolean permissionWrite;
	private boolean permissionRemove;
	private boolean permissionManagement;
	
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto vacio
	 */
	public Role ()
	{
	}
	
	// -----------------------------------------------------------------------------------
	/**
	 * Inicializa un rol por sus parametros
	 * 
	 * @param id - Identifica al rol
	 * @param name - Nombre
	 * @param permissionRead - Permiso de lectura
	 * @param permissionWrite - Permiso de escritura
	 * @param permissionRemove - Permiso de eliminacion
	 * @param permissionManagement - Permiso de gestion
	 */
	public Role (int id, String name, boolean permissionRead, boolean permissionWrite, boolean permissionRemove, boolean permissionManagement)
	{
		this.id						= id;
		this.name					= name;
		this.permissionRead			= permissionRead;
		this.permissionWrite		= permissionWrite;
		this.permissionRemove		= permissionRemove;
		this.permissionManagement	= permissionManagement;
	}
	// --------------------------------------------------------------------------------------------
	@Override
	public void setResultSet (ResultSet resultSet) throws SQLException
	{
		id						= resultSet.getInt (SQL_COLUMN_NAME_ID);
		name					= resultSet.getString (SQL_COLUMN_NAME_NAME);
		permissionRead			= resultSet.getBoolean (SQL_COLUMN_NAME_PERMISSION_READ);
		permissionWrite			= resultSet.getBoolean (SQL_COLUMN_NAME_PERMISSION_WRITE);
		permissionRemove		= resultSet.getBoolean (SQL_COLUMN_NAME_PERMISSION_REMOVE);
		permissionManagement	= resultSet.getBoolean (SQL_COLUMN_NAME_PERMISSION_MANAGEMENT);
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
		return new Object[] {name, permissionRead, permissionWrite, permissionRemove, permissionManagement};
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

	/**
	 * @return the permissionRead
	 */
	public boolean isPermissionRead() {
		return permissionRead;
	}

	/**
	 * @param permissionRead the permissionRead to set
	 */
	public void setPermissionRead(boolean permissionRead) {
		this.permissionRead = permissionRead;
	}

	/**
	 * @return the permissionWrite
	 */
	public boolean isPermissionWrite() {
		return permissionWrite;
	}

	/**
	 * @param permissionWrite the permissionWrite to set
	 */
	public void setPermissionWrite(boolean permissionWrite) {
		this.permissionWrite = permissionWrite;
	}

	/**
	 * @return the permissionRemove
	 */
	public boolean isPermissionRemove() {
		return permissionRemove;
	}

	/**
	 * @param permissionRemove the permissionRemove to set
	 */
	public void setPermissionRemove(boolean permissionRemove) {
		this.permissionRemove = permissionRemove;
	}

	/**
	 * @return the permissionManagement
	 */
	public boolean isPermissionManagement() {
		return permissionManagement;
	}

	/**
	 * @param permissionManagement the permissionManagement to set
	 */
	public void setPermissionManagement(boolean permissionManagement) {
		this.permissionManagement = permissionManagement;
	}
	
}
