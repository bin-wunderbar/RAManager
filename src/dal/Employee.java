package dal;

<<<<<<< HEAD
=======
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import racrypt.RACrypt;

>>>>>>> V3.1-alertas
// ------------------------------------------------------------------------------------------------
/**
 * Representa un objeto de empleado procedente de la base de datos 
 * 
 * @author G1
 *
 */
<<<<<<< HEAD

public class Employee extends DBEntity
{
=======
public class Employee extends DBEntity
{
	public static final String SQL_TABLE_NAME				= "Employee";
	
	public static final String SQL_COLUMN_NAME_DNI			= "dni";
	public static final String SQL_COLUMN_NAME_NAME			= "name";
	public static final String SQL_COLUMN_NAME_SURNAMES		= "surnames";
	public static final String SQL_COLUMN_NAME_PASSWORD		= "password";
	public static final String SQL_COLUMN_NAME_PROVINCE		= "province";
	public static final String SQL_COLUMN_NAME_DIRECTION	= "direction";
	public static final String SQL_COLUMN_NAME_EMAIL		= "email";
	public static final String SQL_COLUMN_NAME_PHONE		= "phone";
	public static final String SQL_COLUMN_NAME_ID_ROLE		= "idRole";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_DNI, 
			SQL_COLUMN_NAME_NAME, 
			SQL_COLUMN_NAME_SURNAMES,
			SQL_COLUMN_NAME_PASSWORD,
			SQL_COLUMN_NAME_PROVINCE, 
			SQL_COLUMN_NAME_DIRECTION, 
			SQL_COLUMN_NAME_EMAIL, 
			SQL_COLUMN_NAME_PHONE,
			SQL_COLUMN_NAME_ID_ROLE
			}; 
	
	
>>>>>>> V3.1-alertas
	private String nif;
	private String name;
	private String surnames;
	private String password;
	private String direction;
	private String province;
	private String email;
	private String phone;
<<<<<<< HEAD
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
=======
	private int idRole;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa un objeto de empleado vacio
	 */
	public Employee ()
	{
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto empleado
	 * @param id				- Identificador
	 * @param nif				- NIF del empleado
	 * @param name				- Nombre del empledo
	 * @param surnames			- Apellidos del empleado
	 * @param password			- Contrasenna del empleado
	 * @param province			- Provincia del empleado
	 * @param direction			- Direccion del empleado
	 * @param email				- Correo electronico del empleado
	 * @param phone				- Teléfono del empleado
	 * @param idRol				- Identificador del rol que cumple
	 */
	public Employee (int id, String nif, String name, String surnames, String password, String province, String direction, String email, String phone, int idRol) 
	{
		this.id 				= id;
		this.nif				= nif;
>>>>>>> V3.1-alertas
		this.name				= name;
		this.surnames			= surnames;
		this.password			= password;
		this.province			= province;
		this.direction			= direction;
		this.email				= email;
		this.phone				= phone;
<<<<<<< HEAD
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
=======
		this.idRole				= idRol;
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
	public void setResultSet (ResultSet resultSet) throws SQLException
	{
		id 			= resultSet.getInt (SQL_COLUMN_NAME_ID);
		nif 		= resultSet.getString (SQL_COLUMN_NAME_DNI);
		name		= resultSet.getString (SQL_COLUMN_NAME_NAME);
		surnames	= resultSet.getString (SQL_COLUMN_NAME_SURNAMES);
		password   	= resultSet.getString (SQL_COLUMN_NAME_PASSWORD);
		province	= resultSet.getString (SQL_COLUMN_NAME_PROVINCE);
		direction	= resultSet.getString (SQL_COLUMN_NAME_DIRECTION);
		email		= resultSet.getString (SQL_COLUMN_NAME_EMAIL);
		phone		= resultSet.getString (SQL_COLUMN_NAME_PHONE);
		idRole		= resultSet.getInt(SQL_COLUMN_NAME_ID_ROLE);
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public String getSQLTableName ()
	{
		return SQL_TABLE_NAME;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Object[] getEntityValues ()
	{
		return new Object[] {nif, name, surnames, password, province, direction, email, phone, idRole};
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public String[] getSQLColumnNames ()
	{
		return SQL_COLUMN_NAMES;
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
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Verifica el password del empleado atendiendo al codigo checksum representado en el atributo password
	 * 
	 * @param password - Password real
	 * 
	 * @return Cierto si el password es correcto
	 */
	public boolean verifyPassword (String password) 
	{
		try {
			return RACrypt.parseToMD5 (password).equals (this.password);
		} catch (NoSuchAlgorithmException exception) {
			DBManager.printlnDebug (exception.getMessage());
			exception.printStackTrace();
		}
		return false;
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
	 * @param dni the dni to set
=======
	 * @param nif the nif to set
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
		this.password = password;
=======
		try {
			this.password = RACrypt.parseToMD5(password);
		} catch (NoSuchAlgorithmException exception) {
			DBManager.printlnDebug (exception.getMessage());
			exception.printStackTrace();
		}
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
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

=======
	 * @return the idRole
	 */
	public int getIdRole() {
		return idRole;
	}

	/**
	 * @param idRole the idRol to set
	 */
	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}


>>>>>>> V3.1-alertas
	
}
