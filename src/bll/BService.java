package bll;

import dal.Service;

// ------------------------------------------------------------------------------------------------

/***
 * Representa un servicio en la capa de negocio
 * 
 * @author G1
 *
 */
public class BService extends Service implements IRAObject
{
	public static final int COLUMN_DESCRIPTION 	= 0;
	public static final int COLUMN_HOUR_PRICE 	= 1;

	// --------------------------------------------------------------------------------------------
	/***
<<<<<<< HEAD
	 * Inicialización de un servicio
=======
	 * Inicializa un servicio de la capa de negocio a partir de uno de la capa de datos 
>>>>>>> V3.1-alertas
	 * @param service			- Servicio efectuado  
	 */
	public BService(Service service)
	{
		super(service.getId (), service.getDescription(), service.getHourPrice());
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa un servicio de la capa de negocio
	 */
>>>>>>> V3.1-alertas
	public BService ()
	{
		
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Object getValue(int index) 
	{
		switch (index)
		{
			case COLUMN_DESCRIPTION: 	return getDescription ();
			case COLUMN_HOUR_PRICE:		return getHourPrice ();
		}
		
		return null;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public Object getShowValue(int index) 
	{
		return getValue (index);
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void setValue(int index, Object object) 
	{
		switch (index)
		{
			case COLUMN_DESCRIPTION: 	setDescription ((String)object); 	break;
			case COLUMN_HOUR_PRICE:		setHourPrice ((double)object);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Sobrescribe el método equals para comprar otro servicio por id
	 */
	@Override
	public boolean equals (Object object)
	{
		return ((BService)object).getId() == getId ();
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	/***
	 * Sobrescribe el método toString para devolver la descripción del servicio
	 */
	public String toString ()
	{
		return getDescription();
	}

}
