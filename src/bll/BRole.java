package bll;

import dal.Role;

// ------------------------------------------------------------------------------------------------
/***
 * Representa un objeto de tipo Rol procedente de la capa de datos
 * @author G1
 *
 */
public class BRole extends Role implements IRAObject
{
	public static final int COLUMN_NAME				= 0;
	public static final int PERMISSION_READ			= 1;
	public static final int PERMISSION_WRITE		= 2;
	public static final int PERMISSION_REMOVE		= 3;
	public static final int PERMISSION_MANAGEMENT	= 4;
	
	public static final int WORKSHOP	= 1;
	public static final int CONSULTANT	= 2;
	public static final int DIRECTION	= 3;
	public static final int MANAGEMENT	= 4;
	public static final int SUPERVISION = 5;
	
	// -----------------------------------------------------------------------------------
	/**
	 * Crea un rol vacío
	 */
	public BRole ()
	{
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicialización de un rol de la capa de negocio a partir de rol de la capa de datos
	 * 
	 * @param role		- Rol de la capa de datos
	 */
	public BRole(Role role) 
	{
		super(
				role.getId(), 
				role.getName(),
				role.isPermissionRead(),
				role.isPermissionWrite(),
				role.isPermissionRemove(),
				role.isPermissionManagement()
				);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Sobrescribe el método toString para devolver el nombre del rol
	 */
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
	public Object getValue (int column)
	{
		switch (column)
		{
			case COLUMN_NAME:			return getName ();		
			case PERMISSION_READ:		return isPermissionRead ();
			case PERMISSION_WRITE:		return isPermissionWrite ();
			case PERMISSION_REMOVE:		return isPermissionRemove ();
			case PERMISSION_MANAGEMENT:	return isPermissionManagement ();
		}
		
		return null;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public void setValue (int column, Object object)
	{
		switch (column)
		{
			case COLUMN_NAME:			setName ((String)object);					break;
			case PERMISSION_READ:		setPermissionRead ((boolean)object);		break;
			case PERMISSION_WRITE:		setPermissionWrite ((boolean)object);		break;
			case PERMISSION_REMOVE:		setPermissionRemove ((boolean)object);		break;
			case PERMISSION_MANAGEMENT:	setPermissionManagement ((boolean)object);
		}
	}


}
