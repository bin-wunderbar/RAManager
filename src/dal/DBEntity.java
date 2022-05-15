package dal;

import java.io.Serializable;

// ------------------------------------------------------------------------------------------------
public abstract class DBEntity implements Serializable
{
	protected int id;
	
	// --------------------------------------------------------------------------------------------
	public DBEntity ()
	{
	}

	// --------------------------------------------------------------------------------------------
	public DBEntity (int id)
	{
		this.id = id;
	}

	// --------------------------------------------------------------------------------------------
	boolean isSamePrimaryKey (Object object)
	{
		return id == ((DBEntity)object).id;
	}

	boolean isPrimaryKeyValue (Object value)
	{
		return id == (int)value;
	}
	
	// --------------------------------------------------------------------------------------------
	public int getId ()
	{
		return id;
	}
	
	// --------------------------------------------------------------------------------------------
	public void setId (int id)
	{
		this.id = id;
	}
	
	// --------------------------------------------------------------------------------------------
	public abstract void setAutoId (int autoId);
	
	/**
	 * Devuelve cierto cuando alguno de los campos útiles contengan el texto de filtrado
	 * @param filter - Texto de filtrado
	 * @return - Cierto cuando contenga el texto de filtrado, falso en caso contrario
	 */
	public abstract boolean containsFilter (String filter);

}
