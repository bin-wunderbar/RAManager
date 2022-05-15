package dal;

// -----------------------------------------------------------------------------------
/***
 * Representa la entidad de Material en la base de datos
 * 
 * @author G1
 *
 */

public class Material extends DBEntity
{
	private String name;
	private String description;
	private double unitPrice;
	private int idProvider;
	
	private static int autoId = 1;
	
	// -----------------------------------------------------------------------------------
	/***
	 * Inicializa un objeto vacío
	 */
	public Material ()
	{
		id = autoId++;
	}
	
	// -----------------------------------------------------------------------------------
	/***
	 * Da valor a cada campo del material
	 * @param id								- Identificación del material
	 * @param name							- Nombre del material
	 * @param description				- Descripción del  mterial
	 * @param unitPrice					- Precio por unidad del material
	 * @param idProvider				- Identificación del proveedor del material
	 */
	public Material (int id, String name, String description, double unitPrice, int idProvider)
	{
		this.id					= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.name				= name;
		this.description		= description;
		this.unitPrice			= unitPrice;
		this.idProvider			= idProvider;
	}

	// --------------------------------------------------------------------------------------------
	@Override
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
