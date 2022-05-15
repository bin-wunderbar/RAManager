package dal;

// -----------------------------------------------------------------------------------
/***
 * Representa la entidad de estado en la base de datos
 * 
 * @author G1
 *
 */

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
	}
	
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa la entidad de estado
	 * 
	 * @param id							- Identificador del rol
	 * @param name							- Nombre del rol
	 */
	public Status (int id, String name)
	{
		this.id					= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.name				= name;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
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
