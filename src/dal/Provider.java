package dal;

// ------------------------------------------------------------------------------------------------
/***
 * Representa la entidad de Proveedor en la base de datos
 * 
 * @author G1
 *
 */

public class Provider extends DBEntity
{
	private String name;
	private String direction;
	private String email;
	private String phone;
	
	private static int autoId = 1;
	
	
	public Provider ()
	{
		id = autoId++;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto proveedor de materiales
	 * 
	 * @param id								- Identiicación del proveedor
	 * @param name							- Nombre del proveedor
	 * @param direction					- Dirección del proveedor
	 * @param email							- Correo electrónico de proveedor
	 * @param phone							- Tléefono del proveedor
	 */
	public Provider(int id, String name, String direction, String email, String phone) {
		this.id 			= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.name 			= name;
		this.direction 		= direction;
		this.email 			= email;
		this.phone 			= phone;
	}
	
	// --------------------------------------------------------------------------------------------
	public boolean containsFilter (String filter)
	{
		return name.toLowerCase ().contains(filter) || direction.toLowerCase ().contains(filter) || email.toLowerCase ().contains(filter) || phone.toLowerCase ().contains(filter);
	}

	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Provider.autoId = autoId;
	}

	// -----------------------------------------------------------------------------------
	public static void reset ()
	{
		autoId = 1;
	}

	// --------------------------------------------------------------------------------------------
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
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
