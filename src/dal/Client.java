package dal;

// -----------------------------------------------------------------------------------
/***
 * Representa una vista del cliente procedente de la base de datos
 *  
 * @author G1
 *
 */

public class Client extends DBEntity
{
	private String nif;
	private String name;
	private String surnames;
	private String province;
	private String direction;
	private String email;
	private String phone;
	
	private static int autoId = 1;
	
	// -----------------------------------------------------------------------------------
	/**
	 * Inicializa el cliente vacío
	 */
	public Client ()
	{
		id = autoId++;
	}
	
	// -----------------------------------------------------------------------------------
	/***
	 * Doy valor a todos los campos del cliente
	 * @param id				- Id de la entidad
	 * @param dni				- DNI del cliente
	 * @param name				- Nombre del cliente
	 * @param surnames			- Apellidos del cliente
	 * @param province			- Provincia del cliente
	 * @param direction			- Direccción del cliente
	 * @param email				- Correo electrónico del cliente
	 * @param phone				- Teléfono del cliente
	 */
	
	public Client (int id, String dni, String name, String surnames, String province, String direction, String email, String phone)
	{
		this.id 		= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.nif 		= dni;
		this.name		= name;
		this.surnames	= surnames;
		this.province	= province;
		this.direction	= direction;
		this.email		= email;
		this.phone		= phone;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return 	name.toLowerCase ().contains(filter) || surnames.toLowerCase ().contains (filter) || nif.toLowerCase ().contains(filter) || 
				direction.toLowerCase ().contains (filter) || province.toLowerCase ().contains (filter) || 
				email.toLowerCase ().contains(filter) || phone.toLowerCase ().contains(filter);
	}


	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Client.autoId = autoId;
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
	 * @return the dni
	 */
	public String getNIF() {
		return nif;
	}

	/**
	 * @param dni the dni to set
	 */
	public void setNIF(String nif) {
		this.nif = nif;
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
	 * @return the surnames
	 */
	public String getSurnames() {
		return surnames;
	}

	/**
	 * @param surnames the surnames to set
	 */
	public void setSurnames(String surnames) {
		this.surnames = surnames;
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

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}


}