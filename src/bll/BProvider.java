package bll;

import dal.Provider;

// ------------------------------------------------------------------------------------------------

/***
 * Representa un proveedor en la capa de negocio
 * 
 * @author G1
 *
 */
public class BProvider extends Provider implements IRAObject
{

	public static final int COLUMN_NAME			= 0;
	public static final int COLUMN_DIRECTION	= 1;
	public static final int COLUMN_EMAIL		= 2;
	public static final int COLUMN_PHONE		= 3;
	
	// --------------------------------------------------------------------------------------------
	public BProvider ()
	{
		
	}
	
	// --------------------------------------------------------------------------------------------
	public BProvider(Provider provider)
	{
		super(provider.getId (), provider.getName(), provider.getDirection(), provider.getEmail(), provider.getPhone());
	}
		
	// --------------------------------------------------------------------------------------------
	@Override
	public boolean equals (Object object)
	{
		return ((BProvider)object).getId() == getId ();
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public String toString ()
	{
		return getName();
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
			case COLUMN_NAME:		return getName ();
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
			case COLUMN_NAME:		setName ((String)object);		break;
			case COLUMN_DIRECTION:	setDirection ((String)object); 	break;
			case COLUMN_EMAIL:		setEmail ((String)object); 		break;
			case COLUMN_PHONE:		setPhone ((String)object);
		}
	}

}
