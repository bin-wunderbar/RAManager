package bll;

import dal.Material;

// ------------------------------------------------------------------------------------------------
/***
 * Representa un objeto de tipo material procedente de la capa de datos
 * @author G1
 *
 */
public class BMaterial extends Material implements IRAObject
{
	public static final int COLUMN_NAME			= 0;
	public static final int COLUMN_DESCRIPTION	= 1;
	public static final int COLUMN_UNIT_PRICE	= 2;
	public static final int COLUMN_ID_PROVIDER	= 3;
	
	// -----------------------------------------------------------------------------------
	/**
	 * Crea un material vacío
	 */
	public BMaterial ()
	{
		
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicialización de un material de la capa de negocio a partir de material de la capa de datos
	 * 
	 * @param material		- Material del reparación
	 */
	public BMaterial(Material material) 
	{
		super(material.getId (), material.getName (), material.getDescription (), material.getUnitPrice (), material.getIdProvider ());
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
			case COLUMN_NAME:			return getName ();
			case COLUMN_DESCRIPTION:	return getDescription ();
			case COLUMN_UNIT_PRICE:		return getUnitPrice ();
			case COLUMN_ID_PROVIDER:	return getIdProvider ();
		}
		
		return null;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public void setValue (int column, Object object)
	{
		switch (column)
		{
			case COLUMN_NAME:			setName ((String)object); 			break;
			case COLUMN_DESCRIPTION:	setDescription ((String)object); 	break;
			case COLUMN_UNIT_PRICE:		setUnitPrice ((double)object); 		break;
			case COLUMN_ID_PROVIDER:	setIdProvider ((int)object);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public String toString ()
	{
		return getName();
	}
	
}
