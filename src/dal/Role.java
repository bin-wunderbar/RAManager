package dal;

// -----------------------------------------------------------------------------------
/***
 * Representa la entidad de Rol en la base de datos
 * 
 * @author G1
 *
 */

public class Role extends DBEntity
{
	private String name;
	
	private boolean permissionRead;
	private boolean permissionWrite;
	private boolean permissionRemove;
	private boolean permissionManagement;
	
	private static int autoId = 1;
	
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto vacío
	 */
	public Role ()
	{
		id = autoId++;
	}
	
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa el rol
	 * 
	 * @param id							- Identificador del rol
	 * @param name							- Nombre del rol
	 */
	public Role (int id, String name, boolean permissionRead, boolean permissionWrite, boolean permissionRemove, boolean permissionManagement)
	{
		this.id						= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.name					= name;
		this.permissionRead			= permissionRead;
		this.permissionWrite		= permissionWrite;
		this.permissionRemove		= permissionRemove;
		this.permissionManagement	= permissionManagement;
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
		Role.autoId = autoId;
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
