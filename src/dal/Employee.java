package dal;

// ------------------------------------------------------------------------------------------------
/**
 * Representa un objeto de empleado procedente de la base de datos 
 * 
 * @author G1
 *
 */

public class Employee extends DBEntity
{
	private String nif;
	private String name;
	private String surnames;
	private String password;
	private String direction;
	private String province;
	private String email;
	private String phone;
	private int idRol;
	
	private static int autoId = 1;
	
	// --------------------------------------------------------------------------------------------
	public Employee ()
	{
		id = autoId++;
	}
	
	// --------------------------------------------------------------------------------------------
	
	/***
	 * Doy valor a todos los campos del empleado
	 * @param dni										- DNI del empleado
	 * @param name									- Nombre del empledo
	 * @param surnames							- Apellidos del empleado
	 * @param password							- Contraseña del empleado
	 * @param province							- Provincia del empleado
	 * @param direction							- Direccón del empleado
	 * @param email									- Correo electrónico del empleado
	 * @param phone									- Teléfono del empleado
	 * @param rol										- Tipo de trabajo del empleado
	 * @param queryPermission				- Permiso de consulta
	 * @param modifyPermission			- Permiso de modificar
	 * @param removePermission			- Permiso de eliminar
	 */
	public Employee (int id, String dni, String name, String surnames, String password, String province, String direction, String email, String phone, int idRol) 
	{
		this.id 				= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.nif				= dni;
		this.name				= name;
		this.surnames			= surnames;
		this.password			= password;
		this.province			= province;
		this.direction			= direction;
		this.email				= email;
		this.phone				= phone;
		this.idRol				= idRol;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return 
				name.toLowerCase ().contains(filter) || surnames.toLowerCase ().contains (filter) || nif.toLowerCase ().contains(filter) || 
				direction.toLowerCase ().contains (filter) || province.toLowerCase ().contains (filter) || 
				email.toLowerCase ().contains(filter) || phone.toLowerCase ().contains(filter);
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Employee.autoId = autoId;
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
	 * @return the dni
	 */
	public String getNIF() {
		return nif;
	}

	/**
	 * @param dni the dni to set
	 */
	public void setNiF (String nif) {
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the idRol
	 */
	public int getIdRole() {
		return idRol;
	}

	/**
	 * @param idRol the idRol to set
	 */
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	
}
