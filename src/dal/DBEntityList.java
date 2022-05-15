package dal;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

// ------------------------------------------------------------------------------------------------
public class DBEntityList <T> extends ArrayList <T> 
{
	private String databaseDirectory; 
	private String databaseFileName;
	private boolean simulation;
	
	// --------------------------------------------------------------------------------------------
	public DBEntityList (String databaseDirectory, String databaseFileName, boolean simulation)
	{
		this.databaseDirectory	= databaseDirectory;
		this.databaseFileName	= databaseFileName;
		this.simulation			= simulation;
	}

	// --------------------------------------------------------------------------------------------
	public DBEntityList (String databaseDirectory, String databaseFileName)
	{
		this.databaseDirectory	= databaseDirectory;
		this.databaseFileName	= databaseFileName;
		simulation 				= false;
	}
	
	// --------------------------------------------------------------------------------------------
	public void setSimulation (boolean simulation) 
	{
		this.simulation = simulation;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Guarda la entidad especificada
	 * @param entity					- Entidad a guardar
	 * @throws IOException 
	 */
	public void save (T entity) throws DBManagerException 
	{
		int index = entityExists (entity);
		if (index >= 0)
		{
			set (index, entity);
		}
		else
		{
			add (entity);
		}
		
		if (!simulation) 
		{ 
			saveToDisk (); 
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Comprueba la existencia de una entidad por su clave primaria
	 * @param entitySearch	Entidad a comprobar
	 * @return índice de la lista donde ha sido localizada la entidad <strong>-1</strong> en caso contrario
	 */
	public int entityExists (T entitySearch) throws DBManagerException
	{
		int index = 0;
		for (T entity : this)
		{
			if (((DBEntity)entitySearch).isSamePrimaryKey(entity)) return index;
			index++;
		}
		
		return -1;
	}

	// --------------------------------------------------------------------------------------------
	public T getByPrimaryKey (Object primaryKey) throws DBManagerException
	{
		for (T entity : this)
		{
			if (((DBEntity)entity).isPrimaryKeyValue (primaryKey)) return entity;
		}
		
		return null;
	}

	// --------------------------------------------------------------------------------------------
	public <PK> void deleteByPrimaryKey (PK primaryKey) throws DBManagerException
	{
		deleteByPrimaryKeyFromList (primaryKey);
		
		if (!simulation)
		{
			saveToDisk ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina una entidad por su clave primaria
	 * @param primaryKey Clave primaria
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
	 * @param primaryKeys Lista de claves primarias
	 * @throws IOException 
	 */
	public <PK> void deleteEntitiesByPrimaryKeys (ArrayList<PK> primaryKeys) throws DBManagerException 
	{
		for (PK primaryKey : primaryKeys)
		{
			deleteByPrimaryKeyFromList (primaryKey);
		}
		
		if (!simulation)
		{
			saveToDisk ();
		}
	}

	// --------------------------------------------------------------------------------------------
	public DBEntityList <T> getFilter (String filter)
	{
		DBEntityList <T> filterList = new DBEntityList <> (databaseDirectory, databaseFileName);
		
		for (T entity : this)
		{
			if (((DBEntity)entity).containsFilter(filter))
			{
				filterList.add(entity);
			}
		}
		
		return filterList;
	}
	
	// --------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	/**
	 * Carga de la unidad de almacenamiento los objetos de la lista
	 * @throws DBManagerException
	 */
	public void loadFromDisk () throws DBManagerException
	{
		ArrayList <Integer> ids = new ArrayList <> ();
		
		clear ();
		
		try
		{
		FileInputStream fis = new FileInputStream (databaseDirectory + File.separatorChar + databaseFileName);
		ObjectInputStream ois = new ObjectInputStream (fis);
		
		DBEntity dBEntity = null;
		while (fis.available() > 0)
		{
			T entity = (T)ois.readObject();						// Recoger como entidad genérica
			dBEntity = (DBEntity)entity; 						// Recoger como entidad de la base de datos
			ids.add (dBEntity.getId());							// Obtener todos los ID de las entidades
			add (entity);
		}
		
		if (!ids.isEmpty())
		{
			Collections.sort(ids, Collections.reverseOrder());	// Obtener el ID de mayor valor para calcular los nuevos IDs
			dBEntity.setAutoId(ids.get(0) + 1);					// Ajustar el mayor ID + 1 como autoId estático de la clase
		}
		
		fis.close();
		}
		catch (java.io.IOException | ClassNotFoundException exception)
		{
			throw new DBManagerException ("DBEntityList::loadFromDisk () - Error to get data from disk [" + databaseDirectory + File.separatorChar + databaseFileName + "].");
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda en la unidad de almacenamiento los objetos de la lista
	 * 
	 * @throws DBManagerException
	 */
	public void saveToDisk () throws DBManagerException
	{
		try
		{
			File directory = new File (databaseDirectory);
			
			if (!directory.exists())
			{
				directory.mkdirs ();
			}
			
			FileOutputStream fos = new FileOutputStream (databaseDirectory + File.separatorChar + databaseFileName);
			ObjectOutputStream oos = new ObjectOutputStream (fos);
			
			for (T entity : this)
			{
				oos.writeObject (entity);
			}
			
			fos.close ();
		}
		catch (java.io.IOException exception)
		{
			throw new DBManagerException ("DBEntityList::saveToDisk - Error saving data to disk [" + databaseDirectory + File.separatorChar + databaseFileName + "]");
		}
	}

}
