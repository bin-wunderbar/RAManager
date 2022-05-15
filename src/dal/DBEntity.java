package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// ------------------------------------------------------------------------------------------------
/**
 * Clase base para la conversion del modelo relacional a objetos
 * 
 * @author G4
 *
 */
public abstract class DBEntity
{
	public static final String SQL_COLUMN_NAME_ID	= "id";
	
	protected int id;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa un objeto de entidad vacio
	 */
	public DBEntity ()
	{
		id = DBManager.ID_NEW;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa un objeto de entidad con un valor de id
	 * 
	 * @param id - Valor de identificacion
	 */
	public DBEntity (int id)
	{
		this.id = id;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve cierto si un objeto del mismo tipo tiene la misma clave primaria
	 * 
	 * @param object Objeto con la clave primaria
	 * @return Cierto en caso de que coincida la clave primaria
	 */
	boolean isSamePrimaryKey (Object object)
	{
		return id == ((DBEntity)object).id;
	}

	/**
	 * Devuelve cierto si el objeto es la misma clave primaria
	 * @param value Valor de clave primaria
	 * @return Cierto en caso de que coincida la clave primaria
	 */
	boolean isPrimaryKeyValue (Object value)
	{
		return id == (int)value;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el objeto a partir de un resultSet de SQL
	 * @param resultSet
	 */
	public abstract void setResultSet(ResultSet resultSet) throws SQLException;

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el nombre de la tabla que representa esta entidad en SQL
	 * 
	 * @return Nombre de la tabla
	 */
	public abstract String getSQLTableName ();
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve los nombres de columnas en SQL
	 * 
	 * @return Matriz de cadenas con los nombres de las columnas en SQL
	 */
	public abstract String[] getSQLColumnNames ();
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve los valores de la entidad
	 * 
	 * @return Matriz de objetos con los valores de la entidad
	 */
	public abstract Object[] getEntityValues ();
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda la entidad en la base de datos
	 * 
	 * @param statement - Sentencia desde la que generar la operacion 
	 */
	public void save (Statement statement) throws SQLException
	{
		String sql = getId () == DBManager.ID_NEW ? getSQLInsert () : getSQLUpdate ();
		
		DBManager.printlnDebug ("DBEntity::save () - " + sql);
		
		statement.executeUpdate(sql);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Deuvelve una sentencia insert a partir de los datos de nombre de la tabla, columnas y valores
	 * 
	 * @return Texto de la sentencia
	 */
	public String getSQLInsert ()
	{
		String sql = 
				"insert into " + getSQLTableName () + 
				" (" + getSQLParseColumnNames (getSQLColumnNames ()) + ")" + 
				" values " + 
				"(" + getSQLParseValues () + ")";
		
		return sql;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Deuvelve una sentencia replace a partir de los datos de nombre de la tabla, columnas y valores
	 * 
	 * @return Texto de la sentencia
	 */
	public String getSQLUpdate ()
	{
		String sql = "update " + getSQLTableName () + " set ";
		
		String[] columnNames = getSQLColumnNames ();
		Object[] values = getEntityValues ();
		
		for (int i = 0; i < columnNames.length; i++)
		{
			sql += columnNames[i] + "=" + (values[i].getClass() == String.class ? "\'" + values[i] + "\'" : values[i]);
			if (i < columnNames.length - 1) sql += ", ";
		}
		
		sql += " where id = " + id;
		
		return sql;
	}
	
	// --------------------------------------------------------------------------------------------
	public String getSQLdelete ()
	{
		return  "delete from " + getSQLTableName () + " where id = " + id;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la lista de columnas de SQL separadas por comas
	 * 
	 * @return Lista de columnas separadas por comas
	 */
	protected static String getSQLParseColumnNames (String[] sqlColumnNames)
	{
		String parseColumnNames = "";
		
		for (int i = 0; i < sqlColumnNames.length; i++)
		{
			parseColumnNames += sqlColumnNames[i];
			if (i < sqlColumnNames.length - 1) parseColumnNames += ", ";
		}
		
		return parseColumnNames;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Deuvelve la lista de valores separados por comas y entrecomillados si son cadenas de texto
	 * 
	 * @return Lista de valores separados por comas
	 */
	protected String getSQLParseValues ()
	{
		String parseValues = "";
		
		for (int i = 0; i < getEntityValues ().length; i++)
		{
			Object object =getEntityValues ()[i];
			parseValues += object.getClass() == String.class ? "\'" + object + "\'" : object;
			if (i < getEntityValues ().length - 1) parseValues += ", ";
		}
		
		return parseValues;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el id
	 * 
	 * @return El id
	 */
	public int getId ()
	{
		return id;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta el id
	 * 
	 * @param id - Identificador especificado
	 */
	public void setId (int id)
	{
		this.id = id;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve cierto cuando alguno de los campos útiles contengan el texto de filtrado
	 * @param filter - Texto de filtrado
	 * @return - Cierto cuando contenga el texto de filtrado, falso en caso contrario
	 */
	public abstract boolean containsFilter (String filter);


	
}
