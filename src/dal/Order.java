package dal;

<<<<<<< HEAD
=======
import java.sql.ResultSet;
import java.sql.SQLException;
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
	public static final String SQL_TABLE_NAME							= "WorkOrder";
	
	public static final String SQL_COLUMN_NAME_INPUT_DATE				= "inputDateTime";
	public static final String SQL_COLUMN_NAME_DESCRIPTION				= "description";
	public static final String SQL_COLUMN_NAME_ISSUED_DATE_TIME			= "issuedDateTime";
	public static final String SQL_COLUMN_NAME_ACCEPT					= "accept";
	public static final String SQL_COLUMN_NAME_ID_CLIENT				= "idClient";
	public static final String SQL_COLUMN_NAME_ID_VEHICLE				= "idVehicle";
	public static final String SQL_COLUMN_NAME_ID_EVALUATOR				= "idEmployeeEvaluator";
	public static final String SQL_COLUMN_NAME_ID_STATUS				= "idStatus";
	
	public static final String[] SQL_COLUMN_NAMES 	= {
			SQL_COLUMN_NAME_INPUT_DATE, 
			SQL_COLUMN_NAME_DESCRIPTION, 
			SQL_COLUMN_NAME_ISSUED_DATE_TIME, 
			SQL_COLUMN_NAME_ACCEPT,
			SQL_COLUMN_NAME_ID_CLIENT,
			SQL_COLUMN_NAME_ID_VEHICLE,
			SQL_COLUMN_NAME_ID_EVALUATOR,
			SQL_COLUMN_NAME_ID_STATUS
			}; 
	
>>>>>>> V3.1-alertas
	private LocalDateTime inputDate;
	private String description;
	private LocalDateTime issuedDateTime;
	private boolean accept;
	private int idClient;
	private int idVehicle;
	private int idEvaluator;
	private int idStatus;
	
<<<<<<< HEAD
	private static int autoId = 1;

	// -------------------------------------------------------------------------------------------
	/***
	 * Crea un objeto de tipo Order vacío
	 */
	public Order ()
	{
		id = autoId++;
=======
	// -------------------------------------------------------------------------------------------
	/***
	 * Crea un objeto de tipo Order vacio
	 */
	public Order ()
	{
>>>>>>> V3.1-alertas
	}
	
	// -------------------------------------------------------------------------------------------
	/***
	 * Inicializa valor a cada campo de la orden de trabajo
	 * 
<<<<<<< HEAD
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
=======
	 * @param id					- Identifica la orden de trabajo
	 * @param inputDate				- Fecha de entrada de la orden
	 * @param description			- Descripcion de la orden de trabajo
	 * @param issuedDateTime			- Fecha de facturacion de la orden
	 * @param accept				- Orden aceptada por el cliente
	 * @param idClient				- Id del cliente que solicita la orden de trabajo
	 * @param idVehicle				- Id del vehiculo que se esta reparando
	 * @param idEvaluator			- Id del mecanico que realiza la orden de trabajo
	 * @param idStatus				- Id de estado de la orden de trabajo
	 */
	public Order (
			int id, 
			LocalDateTime inputDate, 
			String description, 
			LocalDateTime issuedDateTime, 
			boolean accept, 
			int idClient, 
			int idVehicle,
			int idEvaluator, 
			int idStatus) {
		this.id 							= id;
		this.inputDate 						= inputDate;
		this.description 					= description;
		this.issuedDateTime					= issuedDateTime;
>>>>>>> V3.1-alertas
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
	
<<<<<<< HEAD
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
=======
	// --------------------------------------------------------------------------------------------
	@Override
	public void setResultSet (ResultSet resultSet) throws SQLException
	{
		id							= resultSet.getInt (SQL_COLUMN_NAME_ID);
		String inputDateString		= resultSet.getString (SQL_COLUMN_NAME_INPUT_DATE);
		inputDate 					= inputDateString == null ? null : LocalDateTime.parse (inputDateString);
		description 				= resultSet.getString (SQL_COLUMN_NAME_DESCRIPTION);
		String issuedDateTimeString	= resultSet.getString (SQL_COLUMN_NAME_ISSUED_DATE_TIME); 
		issuedDateTime				= issuedDateTimeString == null ? null : LocalDateTime.parse (issuedDateTimeString);
		accept						= resultSet.getBoolean (SQL_COLUMN_NAME_ACCEPT);
		idClient 					= resultSet.getInt (SQL_COLUMN_NAME_ID_CLIENT);
		idVehicle 					= resultSet.getInt (SQL_COLUMN_NAME_ID_VEHICLE);
		idEvaluator 				= resultSet.getInt (SQL_COLUMN_NAME_ID_EVALUATOR);
		idStatus 					= resultSet.getInt (SQL_COLUMN_NAME_ID_STATUS);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una consulta con todos los datos de la tabla
	 * 
	 * @return Consulta con todos los datos de la tabla
	 */
	public static String getQuery ()
	{
		return "select " + 
				SQL_COLUMN_NAME_ID + ", " + 
				"getLocalDateTimeFormat(" +SQL_COLUMN_NAME_INPUT_DATE + ") as " + SQL_COLUMN_NAME_INPUT_DATE + ", " +
				SQL_COLUMN_NAME_DESCRIPTION + ", " +
				"getLocalDateTimeFormat(" + SQL_COLUMN_NAME_ISSUED_DATE_TIME + ") as " + SQL_COLUMN_NAME_ISSUED_DATE_TIME +  ", " +
				SQL_COLUMN_NAME_ACCEPT + ", " +
				SQL_COLUMN_NAME_ID_CLIENT + ", " +
				SQL_COLUMN_NAME_ID_VEHICLE + ", " +
				SQL_COLUMN_NAME_ID_EVALUATOR + ", " +
				SQL_COLUMN_NAME_ID_STATUS + 
				" from " + SQL_TABLE_NAME;
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
		return new Object[] {DBManager.getIsoFormat (inputDate), description, DBManager.getIsoFormat (issuedDateTime), accept, idClient, idVehicle, idEvaluator, idStatus};
>>>>>>> V3.1-alertas
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
