package dal;

<<<<<<< HEAD
=======
import java.sql.ResultSet;
import java.sql.SQLException;

>>>>>>> V3.1-alertas
// ------------------------------------------------------------------------------------------------
/***
 * Representa la entidad de Proveedor en la base de datos
 * 
 * @author G1
 *
 */
<<<<<<< HEAD

public class Provider extends DBEntity
{
=======
public class Provider extends DBEntity
{
	public static final String SQL_TABLE_NAME				= "Provider";
	
	public static final String SQL_COLUMN_NAME_NAME 		= "name";
	public static final String SQL_COLUMN_NAME_DIRECTION 	= "direction";
	public static final String SQL_COLUMN_NAME_EMAIL 		= "email";
	public static final String SQL_COLUMN_NAME_PHONE 		= "phone";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_NAME,
			SQL_COLUMN_NAME_DIRECTION,
			SQL_COLUMN_NAME_EMAIL,
			SQL_COLUMN_NAME_PHONE
			}; 
	
>>>>>>> V3.1-alertas
	private String name;
	private String direction;
	private String email;
	private String phone;
	
<<<<<<< HEAD
	private static int autoId = 1;
	
	
	public Provider ()
	{
		id = autoId++;
=======

	/**
	 * Inicializa un objeto proveedor nuevo
	 */
	public Provider ()
	{
>>>>>>> V3.1-alertas
	}
	
	// --------------------------------------------------------------------------------------------
	/***
<<<<<<< HEAD
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
=======
	 * Inicializa un objeto proveedor con parametros
	 * 
	 * @param id			- Identifica  al proveedor
	 * @param name			- Nombre del proveedor
	 * @param direction		- Direccion del proveedor
	 * @param email			- Correo electronico de proveedor
	 * @param phone			- Telefono del proveedor
	 */
	public Provider(int id, String name, String direction, String email, String phone) {
		this.id 			= id;
>>>>>>> V3.1-alertas
		this.name 			= name;
		this.direction 		= direction;
		this.email 			= email;
		this.phone 			= phone;
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
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
=======
	@Override
	public void setResultSet (ResultSet resultSet) throws SQLException
	{
		id				= resultSet.getInt (SQL_COLUMN_NAME_ID);
		name			= resultSet.getString (SQL_COLUMN_NAME_NAME);
		direction		= resultSet.getString (SQL_COLUMN_NAME_DIRECTION);
		email			= resultSet.getString (SQL_COLUMN_NAME_EMAIL);
		phone			= resultSet.getString (SQL_COLUMN_NAME_PHONE);
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
	public String getSQLTableName ()
	{
		return SQL_TABLE_NAME;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public String[] getSQLColumnNames ()
	{
		return SQL_COLUMN_NAMES;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Object[] getEntityValues ()
	{
		return new Object[] {name, direction, email, phone};
	}
	
	// --------------------------------------------------------------------------------------------
	public boolean containsFilter (String filter)
	{
		return name.toLowerCase ().contains(filter) || direction.toLowerCase ().contains(filter) || email.toLowerCase ().contains(filter) || phone.toLowerCase ().contains(filter);
>>>>>>> V3.1-alertas
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
