package dal;

// ------------------------------------------------------------------------------------------------

/***
 * Representa la entidad de Materiales usados en la base de datos
 * 
 * @author G1
 *
 */
public class UsedMaterial extends DBEntity
{
	private double units;
	private int idMaterial;
	private int idOperation;
	private double unitPriceApplied;
	
	private static int autoId = 1;
	
	public UsedMaterial ()
	{
		id = autoId++;
	}
	
	// ------------------------------------------------------------------------------------------------
	/***
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
		this.units				= units;
		this.idMaterial			= idMaterial;
		this.idOperation		= idOperation;
		this.unitPriceApplied	= unitPriceApplied;
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
