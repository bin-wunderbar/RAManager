package dal;

import java.time.LocalDateTime;

// -----------------------------------------------------------------------------------------------
/***
 * Representa la entidad de orden de trabajo de la base de datos
 * 
 * @author G1
 *
 */
public class Order extends DBEntity
{
	private LocalDateTime inputDate;
	private String description;
	private LocalDateTime issuedDateTime;
	private boolean accept;
	private int idClient;
	private int idVehicle;
	private int idEvaluator;
	private int idStatus;
	
	private static int autoId = 1;

	// -------------------------------------------------------------------------------------------
	/***
	 * Crea un objeto de tipo Order vacío
	 */
	public Order ()
	{
		id = autoId++;
	}
	
	// -------------------------------------------------------------------------------------------
	/***
	 * Inicializa valor a cada campo de la orden de trabajo
	 * 
	 * @param id					- Identificación de la orden de trabajo
	 * @param inputDate				- Fecha de la orden de trabajo
	 * @param description			- Descripción de la orden de trabajo
	 * @param idClient				- Id del cliente que solicita la orden de trabajo
	 * @param idVehicle				- Id del vehículo que se está reparando
	 * @param idEvaluator			- Id del mecánico que realiza la orden de trabajo
	 * @param idStatus				- Id de estado de la orden de trabajo
	 */
	public Order (int id, LocalDateTime inputDate, String description, LocalDateTime issueDateTime, boolean accept, int idClient, int idVehicle,
			int idEvaluator, int idStatus) {
		this.id 							= id <= DBManager.ID_AUTO ? autoId++ : id;
		this.inputDate 						= inputDate;
		this.description 					= description;
		this.issuedDateTime					= issueDateTime;
		this.accept							= accept;
		this.idClient 						= idClient;
		this.idVehicle 						= idVehicle;
		this.idEvaluator 					= idEvaluator;
		this.idStatus 						= idStatus;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter (String filter)
	{
		return 	description.toLowerCase ().contains(filter) || 
				(inputDate == null ? false : inputDate.toString().toLowerCase().contains(filter)) || 
				(issuedDateTime == null ? false : issuedDateTime.toString().toLowerCase().contains(filter));
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void setAutoId (int autoId) 
	{
		Order.autoId = autoId;
	}

	// -----------------------------------------------------------------------------------
	public static void reset ()
	{
		autoId = 1;
	}
	
	// -------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// -------------------------------------------------------------------------------------------


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
	 * @return the inputDate
	 */
	public LocalDateTime getInputDate() {
		return inputDate;
	}

	/**
	 * @param inputDate the inputDate to set
	 */
	public void setInputDate(LocalDateTime inputDate) {
		this.inputDate = inputDate;
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

	/**
	 * @return the idVehicle
	 */
	public int getIdVehicle() {
		return idVehicle;
	}

	/**
	 * @param idVehicle the idVehicle to set
	 */
	public void setIdVehicle(int idVehicle) {
		this.idVehicle = idVehicle;
	}

	/**
	 * @return the idEvaluatorMechanic
	 */
	public int getIdEvaluator() {
		return idEvaluator;
	}

	/**
	 * @param idEvaluatorMechanic the idEvaluatorMechanic to set
	 */
	public void setIdEvaluator(int idEvaluatorMechanic) {
		this.idEvaluator = idEvaluatorMechanic;
	}

	/**
	 * @return the idStatus
	 */
	public int getIdStatus() {
		return idStatus;
	}

	/**
	 * @param idStatus the idStatus to set
	 */
	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}

	/**
	 * @return the issuedDateTime
	 */
	public LocalDateTime getIssuedDateTime() {
		return issuedDateTime;
	}

	/**
	 * @param issuedDateTime the issuedDateTime to set
	 */
	public void setIssuedDateTime(LocalDateTime issuedDateTime) {
		this.issuedDateTime = issuedDateTime;
	}

	/**
	 * @return the accept
	 */
	public boolean isAccept() {
		return accept;
	}

	/**
	 * @param accept the accept to set
	 */
	public void setAccept(boolean accept) {
		this.accept = accept;
	}


}
