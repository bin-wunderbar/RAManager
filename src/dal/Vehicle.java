package dal;

// ------------------------------------------------------------------------------------------------
/***
 * Representa la entidad de vehículo en la base de datos
 * 
 * @author G1
 *
 */

public class Vehicle extends DBEntity
{
	private String registrationNumber;
	private String model;
	private String color;
	private int idClient;
	
	private static int autoId = 1;

	// ---------------------------------------------------------------------------------------------
	/**
	 * Inicializa el objeto vacío
	 */
	public Vehicle ()
	{
		id = autoId++;
	}
	
	// ---------------------------------------------------------------------------------------------
	public Vehicle (int id, String registrationNumber, String model, String color, int idClient) 
	{
		this.id 					= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.registrationNumber 	= registrationNumber;
		this.model 					= model;
		this.color 					= color;
		this.idClient 				= idClient;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return registrationNumber.toLowerCase ().contains(filter) || model.toLowerCase ().contains(filter) || color.toLowerCase ().contains(filter);
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Vehicle.autoId = autoId;
	}
	
	// -----------------------------------------------------------------------------------
	public static void reset ()
	{
		autoId = 1;
	}
	
	// ---------------------------------------------------------------------------------------------
	// GETTERS && SETTERS
	// ---------------------------------------------------------------------------------------------

	/**
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * @param registrationNumber the registrationNumber to set
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the idClient
	 */
	public int getIdClient() {
		return idClient;
	}

	/**
	 * @param idClient the idClient to set
	 */
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

}
