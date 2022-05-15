package dal;

// ------------------------------------------------------------------------------------------------
/***
 * Representa la entidad de servicio en la base de datos
 *  
 * @author G1
 *
 */

public class Service extends DBEntity 
{
	private String description;
	private double hourPrice;
	
	private static int autoId = 1;
	
	
	public Service ()
	{
		id = autoId++;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Doy valor a todos los campos de los servicios
	 * @param id								- Indentificación de los  servicios
	 * @param description				- Descripción del servicio
	 * @param hourPrice					- Precio por hora del servicio
	 */
	
	public Service (int id, String description, double hourPrice)
	{
		this.id				= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.description	= description;
		this.hourPrice		= hourPrice;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return getDescription ().toLowerCase ().contains(filter);
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Service.autoId = autoId;
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
	 * @return the hourPrice
	 */
	public double getHourPrice() {
		return hourPrice;
	}

	/**
	 * @param hourPrice the hourPrice to set
	 */
	public void setHourPrice(double hourPrice) {
		this.hourPrice = hourPrice;
	}

}
