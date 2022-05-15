package dal;

import java.util.ArrayList;

// ------------------------------------------------------------------------------------------------
/**
 * Lista generica para la gestion de entidades convertidas del modelo relacional a objetos
 * 
 * @author G4
 *
 * @param <T> - Entidad generica
 */
@SuppressWarnings("serial")
public class DBEntityList <T> extends ArrayList <T> 
{
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un objeto de la lista segun su clave primaria
	 * 
	 * @param primaryKey - Clave primaria
	 * @return - Objeto de la lista o null en caso de no localizarlo
	 * @throws DBManagerException
	 */
	public T getByPrimaryKey (Object primaryKey) throws DBManagerException
	{
		for (T entity : this)
		{
			if (((DBEntity)entity).isPrimaryKeyValue (primaryKey)) return entity;
		}
		
		return null;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina objetos de la lista segun su clave primaria
	 * 
	 * @param <PK> - Clase generica de clave primaria
	 * @param primaryKey - Objeto generico de clave primaria
	 * @throws DBManagerException
	 */
	public <PK> void deleteByPrimaryKey (PK primaryKey) throws DBManagerException
	{
		deleteByPrimaryKeyFromList (primaryKey);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina un objeto de la lista por su clave primaria
	 * 
	 * @param <PK> - Clase generica de clave primaria
	 * @param primaryKey - Objeto generico de clave primaria
	 */
	private <PK> void deleteByPrimaryKeyFromList (PK primaryKey)
	{
		int index = 0;
		for (T iteratorEntity : this)
		{
			if (((DBEntity)iteratorEntity).isPrimaryKeyValue(primaryKey))
			{
				remove (index);
				break;
			}
			index++;
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina entidades por su clave primaria
	 * 
	 * @param primaryKeys - Lista de claves primarias
	 * @throws DBManagerException 
	 */
	public <PK> void deleteEntitiesByPrimaryKeys (ArrayList<PK> primaryKeys) throws DBManagerException 
	{
		for (PK primaryKey : primaryKeys)
		{
			deleteByPrimaryKeyFromList (primaryKey);
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista del mismo tipo con los objetos que cumplan el filtrado
	 * 
	 * @param filter - Cadena de texto para el filtrado de los objetos
	 * @return - Lista con los objetos filtrados
	 */
	public DBEntityList <T> getFilter (String filter)
	{
		DBEntityList <T> filterList = new DBEntityList <> ();
		
		for (T entity : this)
		{
			if (((DBEntity)entity).containsFilter(filter))
			{
				filterList.add(entity);
			}
		}
		
		return filterList;
	}

}
