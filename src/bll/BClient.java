package bll;

import java.util.ArrayList;

import dal.Client;

// ------------------------------------------------------------------------------------------------
/***
 * Entidad de cliente en la capa de negocio
 * 
 * @author G1
 *
 */
public class BClient extends Client implements IRAObject
{
	private ArrayList <BVehicle> vehicles;
	private ArrayList <BOrder> orders;
	
	public static final int COLUMN_NIF 			= 0;
	public static final int COLUMN_NAME			= 1;
	public static final int COLUMN_SURNAMES		= 2;
	public static final int COLUMN_PROVINCE		= 3;
	public static final int COLUMN_DIRECTION	= 4;
	public static final int COLUMN_EMAIL		= 5;
	public static final int COLUMN_PHONE		= 6;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa un cliente nuevo
	 */
	public BClient ()
	{
		initBClient ();
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Inicialización de un cliente a partir de un ViewClient
	 * 
	 * @param client		- Cliente del taller
	 */
	public BClient (Client client)
	{
		super (client.getId (), client.getNIF (), client.getName (), client.getSurnames (), client.getProvince(), client.getDirection(), client.getEmail(), client.getPhone());
		initBClient ();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Calcula el descuento aplicable a un cliente según el número de servicios solicitados en los últimos 30 días y el coste total
	 * -5% para coste mensual de reparaciones mayor o iual de 1000€
	 * -2% para 5 o más servicios solicitados en los últimos 30 días
	 * -5% para 10 o más servicios solicitados en los últimos 30 días
	 * 
	 * @param monthOrdersString - Ordenes de trabajo solicitadas en los últimos 30 días
	 * @param totalCostString - Coste total de facturación de los últimos 30 días
	 * @return Descuento aplicable
	 */
	public static double getDiscount (String monthOrdersString, String totalCostString)
	{
		double discount 	= 0.0;
		double totalCost 	= 0.0;
		double monthOrders 	= 0.0;
		
		try
		{
			totalCost 		= Double.parseDouble(totalCostString);
			monthOrders 	= Double.parseDouble(monthOrdersString);

			if (totalCost >= 1000)
			{
				discount += 5;
			}
			
			if (monthOrders >= 5 && monthOrders < 10)
			{
				discount += 2;
			}
			else if (monthOrders >= 10)
			{
				discount += 5;
			}
		}
		catch (NumberFormatException exception)
		{
			System.err.println ("bll.BClient::getDiscount () - Value conversion error");
		}
		
		return discount;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Creo los arrays 
	 */
	private void initBClient ()
	{
		vehicles 	= new ArrayList <> ();
		orders 		= new ArrayList <> ();
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	/***
	 * Devuelve el NIF y nombre del cliente
	 */
	public String toString ()
	{
		return getNIF () + " : " + getName ();
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve la primera orden de trabajo siemoore y cuando exista
	 * 
	 * @return	Devuelve la órden de trabajo si existe <strong>null</strong> en caso contrario
	 */
	public BOrder getFirstBOrder () 
	{
		if (orders.size () > 0)
		{
			return orders.get(0);
		}
		
		return null;
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
			case COLUMN_NIF: 		return getNIF ();
			case COLUMN_NAME: 		return getName ();
			case COLUMN_SURNAMES: 	return getSurnames ();
			case COLUMN_PROVINCE:	return getProvince ();
			case COLUMN_DIRECTION:	return getDirection ();
			case COLUMN_EMAIL:		return getEmail ();
			case COLUMN_PHONE:		return getPhone ();
		}
		
		return null;
	}	
	
	// --------------------------------------------------------------------------------------------
	@Override
	public void setValue (int index, Object object)
	{
		switch (index)
		{
			case COLUMN_NIF: 		setNIF ((String)object);		break;
			case COLUMN_NAME: 		setName ((String)object);		break;
			case COLUMN_SURNAMES: 	setSurnames ((String)object);	break;
			case COLUMN_PROVINCE:	setProvince ((String)object);	break;
			case COLUMN_DIRECTION:	setDirection ((String)object);	break;
			case COLUMN_EMAIL:		setEmail ((String)object);		break;
			case COLUMN_PHONE:		setPhone ((String)object);
		}
		
	}
	
	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------

	/**
	 * Devuelve los vehiculos segun el filtro
	 * @param filter - Texto de filtrado
	 * @return ArrayList con los vehiculos filtrados
	 */
	public ArrayList <BVehicle> getVehicles (String filter) {
		ArrayList <BVehicle> filteredVehicles = new ArrayList <> ();
		
		for (BVehicle vehicle : vehicles)
		{
			if (vehicle.containsFilter(filter)) 
			{
				filteredVehicles.add (vehicle);
			}
		}
		
		return filteredVehicles;
	}

	/**
	 * Ajusta los vehiculos del cliente
	 * 
	 * @param vehicles - ArrayList con los vehiculos a ajustar
	 */
	public void setVehicles(ArrayList <BVehicle> vehicles) {
		this.vehicles = vehicles;
	}

	/**
	 * Devuelve las ordenes asociadas al cliente filtradas
	 *  
	 * @param filter - Filtro de texto que deben cumplir las ordenes
	 * @return - ArrayList con las ordenes
	 */
	public ArrayList <BOrder> getOrders (String filter) {
		
		ArrayList <BOrder> filteredOrders = new ArrayList <> ();
		
		for (BOrder order : orders)
		{
			if (order.containsFilter(filter))
			{
				filteredOrders.add (order);
			}
		}
		
		return filteredOrders;
	}

	/**
	 * Ajusta las ordenes asociadas al cliente
	 * 
	 * @param orders - ArrayList con las ordenes a asociar 
	 */
	public void setOrders(ArrayList <BOrder> orders) {
		this.orders = orders;
	}

}
