package dal;

<<<<<<< HEAD
=======
import java.sql.ResultSet;
import java.sql.SQLException;

>>>>>>> V3.1-alertas
// ------------------------------------------------------------------------------------------------

/***
 * Representa la entidad de Materiales usados en la base de datos
 * 
 * @author G1
 *
 */
public class UsedMaterial extends DBEntity
{
<<<<<<< HEAD
=======
	public static final String SQL_TABLE_NAME		= "UsedMaterial";
	
	public static final String SQL_COLUMN_NAME_UNITS 			= "units";
	public static final String SQL_COLUMN_NAME_ID_MATERIAL 		= "idMaterial";
	public static final String SQL_COLUMN_NAME_ID_OPERATION 		= "idOperation";
	public static final String SQL_COLUMN_NAME_UNIT_PRICE_APPLIED 	= "unitPriceApplied";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_UNITS,
			SQL_COLUMN_NAME_ID_MATERIAL,
			SQL_COLUMN_NAME_ID_OPERATION,
			SQL_COLUMN_NAME_UNIT_PRICE_APPLIED
			}; 
	
>>>>>>> V3.1-alertas
	private double units;
	private int idMaterial;
	private int idOperation;
	private double unitPriceApplied;
	
<<<<<<< HEAD
	private static int autoId = 1;
	
	public UsedMaterial ()
	{
		id = autoId++;
=======
	// -----------------------------------------------------------------------------------
	/**
	 * Inicializa un objeto de material utilizado por la operacion vacio
	 */
	public UsedMaterial ()
	{
>>>>>>> V3.1-alertas
	}
	
	// ------------------------------------------------------------------------------------------------
	/***
<<<<<<< HEAD
	 * Doy valor a todos los campos del material usado
	 * @param id										- Identificación del histórico del material
	 * @param units									- Unidades del material utilizado
	 * @param idMaterial						- Identificación del material
	 * @param idOperation						- Identificación de la operación
	 * @param unitPriceApplied			- Precio por unidad del material
	 */
	
	public UsedMaterial (int id, double units, int idMaterial, int idOperation, double unitPriceApplied)
	{
		this.id 				= id <= DBManager.ID_AUTO ? autoId++ : id;
=======
	 * Inicializa un objeto de material utilizado por la operacion por sus parametros
	 * 
	 * @param id					- Identifica el objeto de historico de material utilizado
	 * @param units					- Unidades del material utilizado
	 * @param idMaterial			- Identifica el material
	 * @param idOperation			- Identifica la operación
	 * @param unitPriceApplied		- Precio por unidad del material aplicado
	 */
	public UsedMaterial (int id, double units, int idMaterial, int idOperation, double unitPriceApplied)
	{
		this.id 				= id;
>>>>>>> V3.1-alertas
		this.units				= units;
		this.idMaterial			= idMaterial;
		this.idOperation		= idOperation;
		this.unitPriceApplied	= unitPriceApplied;
	}
<<<<<<< HEAD
	
	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return true;
	}

	
	// ------------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// ------------------------------------------------------------------------------------------------

	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		UsedMaterial.autoId = autoId;
	}

	// -----------------------------------------------------------------------------------
	public static void reset ()
	{
		autoId = 1;
	}
=======

	// --------------------------------------------------------------------------------------------
	@Override
	public void setResultSet (ResultSet resultSet) throws SQLException
	{
		id						= resultSet.getInt (SQL_COLUMN_NAME_ID);
		units					= resultSet.getDouble (SQL_COLUMN_NAME_UNITS);
		idMaterial				= resultSet.getInt (SQL_COLUMN_NAME_ID_MATERIAL);
		idOperation				= resultSet.getInt (SQL_COLUMN_NAME_ID_OPERATION);
		unitPriceApplied		= resultSet.getDouble (SQL_COLUMN_NAME_UNIT_PRICE_APPLIED);
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
		return new Object[] {units, idMaterial, idOperation, unitPriceApplied};
	}

	
	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return true;
	}

	// ------------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// ------------------------------------------------------------------------------------------------
>>>>>>> V3.1-alertas
	
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
	 * @return the units
	 */
	public double getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(double units) {
		this.units = units;
	}

	/**
	 * @return the idMaterial
	 */
	public int getIdMaterial() {
		return idMaterial;
	}

	/**
	 * @param idMaterial the idMaterial to set
	 */
	public void setIdMaterial(int idMaterial) {
		this.idMaterial = idMaterial;
	}

	/**
	 * @return the idOperation
	 */
	public int getIdOperation() {
		return idOperation;
	}

	/**
	 * @param idOperation the idOperation to set
	 */
	public void setIdOperation(int idOperation) {
		this.idOperation = idOperation;
	}

	/**
	 * @return the unitPriceApplied
	 */
	public double getUnitPriceApplied() {
		return unitPriceApplied;
	}

	/**
	 * @param unitPriceApplied the unitPriceApplied to set
	 */
	public void setUnitPriceApplied(double unitPriceApplied) {
		this.unitPriceApplied = unitPriceApplied;
	}


}
