package bll;

import dal.Status;

// ------------------------------------------------------------------------------------------------

/***
 * Representa un estado en la capa de negocio
 * 
 * @author G1
 *
 */
public class BStatus extends Status implements IRAObject
{

	private static final int COLUMN_NAME 	= 0;
	
	public static final int DEFINITION 		= 1;
	public static final int APPROVED 		= 2;
	public static final int PENDING			= 3;
	public static final int SOLVED			= 4;
	public static final int CANCELED		= 5;
	public static final int FINALIZED		= 6;
 
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicialización de un estado
	 * @param status			- Estado en la capa de datos  
	 */
	public BStatus(Status status)
	{
		super(status.getId (), status.getName ());
	}
	
	// --------------------------------------------------------------------------------------------
	public BStatus (String name)
	{
		setName (name);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Sobrescribe el método equals para comparar otro objeto de estado por id
	 */
	@Override
	public boolean equals (Object object)
	{
		return ((BStatus)object).getId() == getId ();
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	/***
	 * Sobrescribe el método toString para devolver el nombre asociado al estado
	 */
	public String toString ()
	{
		return getName ();
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public Object getShowValue (int index) 
	{
		return getValue (index);
	}
	// --------------------------------------------------------------------------------------------
	@Override
	public Object getValue(int index) 
	{
			switch (COLUMN_NAME)
			{
				case COLUMN_NAME: return getName ();
			}
			
		return null;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void setValue(int index, Object object) 
	{
		switch (COLUMN_NAME)
		{
			case COLUMN_NAME: setName ((String)object);
		}		
	}
}
