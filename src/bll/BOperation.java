package bll;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import dal.Operation;

//----------------------------------------------------------------------------------------
/***
 * Representa la entidad operacion en la capa de negocio
 * @author G1
 *
 */
public class BOperation extends Operation implements IRAObject
{
	private ArrayList <BUsedMaterial> bUsedMaterials;
	private BEmployee mechanic;
	private BService service;
	
	public static final int COLUMN_ID_MECHANIC				= 0;
	public static final int COLUMN_ID_SERVICE				= 1;
	public static final int COLUMN_DEDICATED_TIME			= 2;
	public static final int COLUMN_HOURLY_PRICE_APPLIED		= 3;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Crea una nueva operación vacía
	 */
	public BOperation ()
	{
		
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicialización de una operacion 
	 * 
	 * @param operation		- Operación de reparación
	 * @param mechanic		- Mecánico del taller
	 * @param service			- Servcio de reparacióon
	 */
	public BOperation (Operation operation, BEmployee mechanic, BService service) 
	{
		super(
				operation.getId (),
				operation.getIdOrder (),
				operation.getIdMechanic(),
				operation.getIdService(),
				operation.getDedicatedTime(),
				operation.getHourlyPriceApplied());

		this.mechanic 	= mechanic;
		this.service 	= service;
		initBOperation ();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Creo array
	 */
	private void initBOperation ()
	{
		bUsedMaterials	= new ArrayList <> ();
	}

	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------
	
	/**
	 * @return the materials
	 */
	public ArrayList<BUsedMaterial> getBUsedMaterials() {
		return bUsedMaterials;
	}

	/**
	 * @param bUsedMaterials the materials to set
	 */
	public void setBUsedMaterials(ArrayList<BUsedMaterial> bUsedMaterials) {
		this.bUsedMaterials = bUsedMaterials;
	}

	/**
	 * @return the bEmployee
	 */
	public BEmployee getBMechanic() {
		return mechanic;
	}

	/**
	 * @param mechanic the bEmployee to set
	 */
	public void setBMechanic(BEmployee mechanic) {
		this.mechanic = mechanic;
		setIdMechanic(mechanic.getId());
	}

	/**
	 * @return the service
	 */
	public BService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(BService service) {
		this.service = service;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public Object getShowValue(int index) 
	{
		switch (index)
		{
			case COLUMN_ID_MECHANIC: 			return mechanic == null ? getIdMechanic () : mechanic;
			case COLUMN_ID_SERVICE:				return service == null ? getIdService() : service;
			case COLUMN_DEDICATED_TIME:			return getDedicatedTime();
			case COLUMN_HOURLY_PRICE_APPLIED:	return getHourlyPriceApplied();
		}
		
		return null;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public Object getValue(int index) 
	{
		switch (index)
		{
			case COLUMN_ID_MECHANIC: 			return getIdMechanic();
			case COLUMN_ID_SERVICE:				return getIdService();
			case COLUMN_DEDICATED_TIME:			return getDedicatedTime();
			case COLUMN_HOURLY_PRICE_APPLIED:	return getHourlyPriceApplied();
		}
		
		return null;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void setValue(int index, Object object) 
	{
		switch (index)
		{
			case COLUMN_ID_MECHANIC: 			setIdMechanic((int)object); 			break;
			case COLUMN_ID_SERVICE:				setIdService((int)object); 				break;
			case COLUMN_DEDICATED_TIME:			setDedicatedTime ((int)object); 		break;
			case COLUMN_HOURLY_PRICE_APPLIED:	setHourlyPriceApplied((double)object);
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el total de multiplicar el precio por hora aplicado a los minutos dedicados
	 * @return El total
	 */
	public double getTotal() 
	{
		BigDecimal bigDecimalHourlyPriceApplied = new BigDecimal (getHourlyPriceApplied());
		BigDecimal bigDecimalDedicatedTime = new BigDecimal (getDedicatedTime());
		
		// Convert minutes to hour units
		BigDecimal bigDecimalHourUnits = bigDecimalDedicatedTime.divide(new BigDecimal (60), 2, RoundingMode.HALF_UP);

		return bigDecimalHourUnits.multiply(bigDecimalHourlyPriceApplied).doubleValue(); 
	}
	


}
