package dal;

<<<<<<< HEAD
=======
import java.sql.ResultSet;
import java.sql.SQLException;

>>>>>>> V3.1-alertas
// -----------------------------------------------------------------------------------
/***
 * Representa la entidad de Material en la base de datos
 * 
 * @author G1
 *
 */
<<<<<<< HEAD

public class Material extends DBEntity
{
=======
public class Material extends DBEntity
{
	public static final String SQL_TABLE_NAME		= "Material";
	
	public static final String SQL_COLUMN_NAME_NAME 		= "name";
	public static final String SQL_COLUMN_NAME_DESCRIPTION 	= "description";
	public static final String SQL_COLUMN_NAME_UNIT_PRICE 	= "unitPrice";
	public static final String SQL_COLUMN_NAME_ID_PROVIDER 	= "idProvider";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_NAME,
			SQL_COLUMN_NAME_DESCRIPTION,
			SQL_COLUMN_NAME_UNIT_PRICE,
			SQL_COLUMN_NAME_ID_PROVIDER
			}; 
	
>>>>>>> V3.1-alertas
	private String name;
	private String description;
	private double unitPrice;
	private int idProvider;
	
<<<<<<< HEAD
	private static int autoId = 1;
	
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto vacío
	 */
	public Material ()
	{
		id = autoId++;
=======
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto vacio
	 */
	public Material ()
	{
>>>>>>> V3.1-alertas
	}
	
	// -----------------------------------------------------------------------------------
	/***
<<<<<<< HEAD
	 * Da valor a cada campo del material
	 * @param id								- Identificación del material
	 * @param name							- Nombre del material
=======
	 * Inicializa un material
	 * 
	 * @param id						- Identificación del material
	 * @param name						- Nombre del material
>>>>>>> V3.1-alertas
	 * @param description				- Descripción del  mterial
	 * @param unitPrice					- Precio por unidad del material
	 * @param idProvider				- Identificación del proveedor del material
	 */
	public Material (int id, String name, String description, double unitPrice, int idProvider)
	{
<<<<<<< HEAD
		this.id					= id <= DBManager.ID_AUTO ? autoId++ : id;
=======
		this.id					= id;
>>>>>>> V3.1-alertas
		this.name				= name;
		this.description		= description;
		this.unitPrice			= unitPrice;
		this.idProvider			= idProvider;
	}

	// --------------------------------------------------------------------------------------------
	@Override
<<<<<<< HEAD
	public boolean containsFilter (String filter)
	{
		return name.toLowerCase ().contains(filter) || description.toLowerCase ().contains(filter);
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Material.autoId = autoId;
	}

	// -----------------------------------------------------------------------------------
	public static void reset ()
	{
		autoId = 1;
=======
	public void setResultSet (ResultSet resultSet) throws SQLException
	{
		id				= resultSet.getInt (SQL_COLUMN_NAME_ID);
		name			= resultSet.getString (SQL_COLUMN_NAME_NAME);
		description		= resultSet.getString (SQL_COLUMN_NAME_DESCRIPTION);
		unitPrice		= resultSet.getDouble (SQL_COLUMN_NAME_UNIT_PRICE);
		idProvider		= resultSet.getInt (SQL_COLUMN_NAME_ID_PROVIDER);
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
		return new Object[] {name, description, unitPrice, idProvider};
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return name.toLowerCase ().contains(filter) || description.toLowerCase ().contains(filter);
>>>>>>> V3.1-alertas
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the unitPrice
	 */
	public double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the idProvider
	 */
	public int getIdProvider() {
		return idProvider;
	}

	/**
	 * @param idProvider the idProvider to set
	 */
	public void setIdProvider(int idProvider) {
		this.idProvider = idProvider;
	}


	
}
