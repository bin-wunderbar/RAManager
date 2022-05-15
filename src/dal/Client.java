package dal;

<<<<<<< HEAD
=======
import java.sql.ResultSet;
import java.sql.SQLException;

>>>>>>> V3.1-alertas
// -----------------------------------------------------------------------------------
/***
 * Representa una vista del cliente procedente de la base de datos
 *  
 * @author G1
 *
 */

public class Client extends DBEntity
{
<<<<<<< HEAD
=======
	public static final String SQL_TABLE_NAME				= "Client";
	
	public static final String SQL_COLUMN_NAME_DNI			= "dni";
	public static final String SQL_COLUMN_NAME_NAME			= "name";
	public static final String SQL_COLUMN_NAME_SURNAMES		= "surnames";
	public static final String SQL_COLUMN_NAME_PROVINCE		= "province";
	public static final String SQL_COLUMN_NAME_DIRECTION	= "direction";
	public static final String SQL_COLUMN_NAME_EMAIL		= "email";
	public static final String SQL_COLUMN_NAME_PHONE		= "phone";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_DNI, 
			SQL_COLUMN_NAME_NAME, 
			SQL_COLUMN_NAME_SURNAMES, 
			SQL_COLUMN_NAME_PROVINCE, 
			SQL_COLUMN_NAME_DIRECTION, 
			SQL_COLUMN_NAME_EMAIL, 
			SQL_COLUMN_NAME_PHONE 
			}; 

>>>>>>> V3.1-alertas
	private String nif;
	private String name;
	private String surnames;
	private String province;
	private String direction;
	private String email;
	private String phone;
	
<<<<<<< HEAD
	private static int autoId = 1;
	
	// -----------------------------------------------------------------------------------
	/**
	 * Inicializa el cliente vacío
	 */
	public Client ()
	{
		id = autoId++;
=======
	// -----------------------------------------------------------------------------------
	/**
	 * Inicializa el cliente vacio
	 */
	public Client ()
	{
>>>>>>> V3.1-alertas
	}
	
	// -----------------------------------------------------------------------------------
	/***
<<<<<<< HEAD
	 * Doy valor a todos los campos del cliente
	 * @param id				- Id de la entidad
	 * @param dni				- DNI del cliente
=======
	 * Inicializa un objeto cliente a partir de sus atributos
	 * 
	 * @param id				- Id de la entidad
	 * @param nif				- DNI del cliente
>>>>>>> V3.1-alertas
	 * @param name				- Nombre del cliente
	 * @param surnames			- Apellidos del cliente
	 * @param province			- Provincia del cliente
	 * @param direction			- Direccción del cliente
	 * @param email				- Correo electrónico del cliente
	 * @param phone				- Teléfono del cliente
	 */
<<<<<<< HEAD
	
	public Client (int id, String dni, String name, String surnames, String province, String direction, String email, String phone)
	{
		this.id 		= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.nif 		= dni;
=======
	public Client (int id, String nif, String name, String surnames, String province, String direction, String email, String phone)
	{
		this.id 		= id;
		this.nif 		= nif;
>>>>>>> V3.1-alertas
		this.name		= name;
		this.surnames	= surnames;
		this.province	= province;
		this.direction	= direction;
		this.email		= email;
		this.phone		= phone;
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
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
	
=======
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
		this.id 		= resultSet.getInt (SQL_COLUMN_NAME_ID);
		this.nif 		= resultSet.getString (SQL_COLUMN_NAME_DNI);
		this.name		= resultSet.getString (SQL_COLUMN_NAME_NAME);
		this.surnames	= resultSet.getString (SQL_COLUMN_NAME_SURNAMES);
		this.province	= resultSet.getString (SQL_COLUMN_NAME_PROVINCE);
		this.direction	= resultSet.getString (SQL_COLUMN_NAME_DIRECTION);
		this.email		= resultSet.getString (SQL_COLUMN_NAME_EMAIL);
		this.phone		= resultSet.getString (SQL_COLUMN_NAME_PHONE);
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
		return new Object[] {nif, name, surnames, province, direction, email, phone};
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
		return 	name.toLowerCase ().contains(filter) || surnames.toLowerCase ().contains (filter) || nif.toLowerCase ().contains(filter) || 
				direction.toLowerCase ().contains (filter) || province.toLowerCase ().contains (filter) || 
				email.toLowerCase ().contains(filter) || phone.toLowerCase ().contains(filter);
	}

>>>>>>> V3.1-alertas
	// -----------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// -----------------------------------------------------------------------------------
	/**
<<<<<<< HEAD
	 * @return the dni
=======
	 * @return the nif
>>>>>>> V3.1-alertas
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
