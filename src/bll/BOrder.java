package bll;

import java.time.LocalDateTime;
import java.util.ArrayList;

import dal.Order;

// ------------------------------------------------------------------------------------------------

/***
 * Representa una orden de trabajo en la capa de negocio
 *  
 * @author G1
 *
 */
public class BOrder extends Order implements IRAObject
{
	private BEmployee bEvaluator;
	private BClient bClient;
	private BVehicle bVehicle;
	private BStatus bStatus;
	private ArrayList <BOperation> bOperations;
	
	public static final int COLUMN_INPUTDATE					= 0;
	public static final int COLUMN_DESCRIPTION					= 1;
	public static final int COLUMN_ISSUED_DATETIME				= 2;
	public static final int COLUMN_ACCEPT						= 3;
	public static final int COLUMN_ID_CLIENT					= 4;
	public static final int COLUMN_ID_VEHICLE					= 5;
	public static final int COLUMN_ID_EVALUATOR					= 6;
	public static final int COLUMN_ID_STATUS					= 7;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Iniciallizacion de la  orden de trabajo
	 */
	public BOrder ()
	{
		initBOrder ();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicialización de la orden de trabajo
	 * @param order										- Orden de trabajo de la reparación
	 * @param bEmployeeEvaluator			- Empleado que evalua la orden de  trabajo
	 */
	
	public BOrder(Order order, BEmployee bEmployeeEvaluator) 
	{
		super (
			order.getId (), 
			order.getInputDate(), 
			order.getDescription(), 
			order.getIssuedDateTime(),
			order.isAccept(),
			order.getIdClient(), 
			order.getIdVehicle(), 
			order.getIdEvaluator(),
			order.getIdStatus()
		);
		
		this.bEvaluator = bEmployeeEvaluator;
		initBOrder ();
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa la orden
	 */
	
	private void initBOrder ()
	{
		bOperations = new ArrayList <BOperation> ();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Añado la operacion
	 * @param operation 		- Operación de la reparación
	 */
	public void addOperation(BOperation operation) 
	{
		if (bOperations != null)
		{
			operation.setIdOrder(getId());
			bOperations.add(operation);
		}
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Sobrescribe el método toString para devolver la descripción de la orden
	 */
	@Override
	public String toString ()
	{
		return getDescription ();
	}

	// --------------------------------------------------------------------------------------------
	public ArrayList <Double> getTotalArray ()
	{
		ArrayList <Double> doubleArrayList = new ArrayList <> ();
		
		for (BOperation operation : bOperations)
		{
			doubleArrayList.add (operation.getTotal ());
			
			for (BUsedMaterial usedMaterial : operation.getBUsedMaterials())
			{
				doubleArrayList.add (usedMaterial.getTotal ());
			}
		}

		return doubleArrayList;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Object getShowValue (int index)
	{
		switch (index)
		{
			case COLUMN_INPUTDATE:				return getInputDate(); 
			case COLUMN_DESCRIPTION: 			return getDescription(); 
			case COLUMN_ISSUED_DATETIME:		return getIssuedDateTime ();
			case COLUMN_ACCEPT:					return isAccept ();
			case COLUMN_ID_CLIENT:				return bClient == null ? getIdClient() : bClient; 
			case COLUMN_ID_VEHICLE:				return bVehicle == null ? getIdVehicle() : bVehicle; 
			case COLUMN_ID_EVALUATOR:			return bEvaluator == null ? getIdEvaluator() : bEvaluator;
			case COLUMN_ID_STATUS:				return bStatus == null ? getIdStatus () : bStatus;		
		}
		
		return null;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Object getValue (int index)
	{
		switch (index)
		{
			case COLUMN_INPUTDATE:				return getInputDate(); 
			case COLUMN_DESCRIPTION: 			return getDescription(); 
			case COLUMN_ISSUED_DATETIME:		return getIssuedDateTime ();
			case COLUMN_ACCEPT:					return isAccept ();
			case COLUMN_ID_CLIENT:				return getIdClient(); 
			case COLUMN_ID_VEHICLE:				return getIdVehicle(); 
			case COLUMN_ID_EVALUATOR:			return getIdEvaluator();
			case COLUMN_ID_STATUS:				return getIdStatus ();		
		}
		
		return null;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public void setValue (int index, Object object)
	{
		switch (index)
		{
			case COLUMN_INPUTDATE:				setInputDate((LocalDateTime)object); 		break; 
			case COLUMN_DESCRIPTION: 			setDescription((String)object); 			break;
			case COLUMN_ISSUED_DATETIME:		setIssuedDateTime ((LocalDateTime)object); 	break;
			case COLUMN_ACCEPT:					setAccept ((boolean)object);				break;
			case COLUMN_ID_CLIENT:				setIdClient((int)object); 					break;
			case COLUMN_ID_VEHICLE:				setIdVehicle((int)object); 					break;
			case COLUMN_ID_EVALUATOR:			setIdEvaluator ((int)object); 				break;
			case COLUMN_ID_STATUS:				setIdStatus ((int)object);		
		}
	}

	
	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------

	/**
	 * @return the bEvaluator
	 */
	public BEmployee getBEvaluator() {
		return bEvaluator;
	}

	/**
	 * @param bEEvaluator the bEvaluator to set
	 */
	public void setBEvaluator(BEmployee bEvaluator) {
		this.bEvaluator = bEvaluator;
	}

	/**
	 * @return the bOperations
	 */
	public ArrayList <BOperation> getBOperations() {
		return bOperations;
	}

	/**
	 * @param bOperations the bOperations to set
	 */
	public void setBOperations(ArrayList <BOperation> bOperations) {
		this.bOperations = bOperations;
	}

	/**
	 * @return the bClient
	 */
	public BClient getBClient() {
		return bClient;
	}

	/**
	 * @param bClient the bClient to set
	 */
	public void setBClient(BClient bClient) {
		this.bClient = bClient;
	}

	/**
	 * @return the bVehicle
	 */
	public BVehicle getBVehicle() {
		return bVehicle;
	}

	/**
	 * @param bVehicle the bVehicle to set
	 */
	public void setBVehicle(BVehicle bVehicle) {
		this.bVehicle = bVehicle;
	}

	/**
	 * @return the bStatus
	 */
	public BStatus getBStatus() {
		return bStatus;
	}

	/**
	 * @param bStatus the bStatus to set
	 */
	public void setBStatus(BStatus bStatus) {
		this.bStatus = bStatus;
	}


}
