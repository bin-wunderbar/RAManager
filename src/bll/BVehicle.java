package bll;

import dal.Vehicle;

// ------------------------------------------------------------------------------------------------
/***
 * Representa una entidad vehículo en la capa de negocio
 *  
 * @author G1
 *
 */
public class BVehicle extends Vehicle implements IRAObject
{
	public static final int COLUMN_REGISTRATION_NUMBER	= 0;
	public static final int COLUMN_MODEL				= 1;
	public static final int COLUMN_COLOR				= 2;
	public static final int COLUMN_ID_CLIENT			= 3;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa un vehículo vacío
	 */
	public BVehicle ()
	{
		
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializar un vehiculo
	 * @param vehicle 			- Vehiuclo de la reparación
	 */
	public BVehicle (Vehicle vehicle)
	{
		super (vehicle.getId (), vehicle.getRegistrationNumber(), vehicle.getModel(), vehicle.getColor(), vehicle.getIdClient());
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public Object getShowValue (int index)
	{
		return getValue (index);
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Object getValue (int index)
	{
		switch (index)
		{
			case COLUMN_REGISTRATION_NUMBER:	return getRegistrationNumber ();
			case COLUMN_MODEL:					return getModel ();
			case COLUMN_COLOR:					return getColor ();
			case COLUMN_ID_CLIENT:				return getIdClient ();		
		}
		
		return null;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public void setValue (int index, Object object)
	{
		switch (index)
		{
			case COLUMN_REGISTRATION_NUMBER:	setRegistrationNumber ((String)object); break;
			case COLUMN_MODEL:					setModel ((String)object); 				break;
			case COLUMN_COLOR:					setColor ((String)object); 				break;
			case COLUMN_ID_CLIENT:				setIdClient ((int)object);		
		}
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public String toString ()
	{
		return getRegistrationNumber ();
	}

}
