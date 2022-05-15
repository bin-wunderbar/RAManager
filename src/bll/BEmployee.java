package bll;

import dal.Employee;

// ------------------------------------------------------------------------------------------------
/***
 * Entidad de empleado de la capa de negocio
 * 
 * @author G1
 *
 */
public class BEmployee extends Employee implements IRAObject
{
	public static final int COLUMN_NIF 			= 0;
	public static final int COLUMN_NAME			= 1;
	public static final int COLUMN_SURNAMES 	= 2;
	public static final int COLUMN_PASSWORD		= 3;
	public static final int COLUMN_PROVINCE		= 4;
	public static final int COLUMN_DIRECTION	= 5;
	public static final int COLUMN_EMAIL		= 6;
	public static final int COLUMN_PHONE		= 7;
	public static final int COLUMN_ROLE			= 8;
	
	private BRole role;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa un empleado vacío
	 */
	public BEmployee ()
	{
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializacióon de un emplado a partir de un ViewEmployee
	 * 
	 * @param employee		- Empleado del taller
	 */
	public BEmployee (Employee employee)
	{
		super (
				employee.getId(),
				employee.getNIF(), 
				employee.getName(), 
				employee.getSurnames(),
				employee.getPassword (), 
				employee.getProvince(), 
				employee.getDirection (), 
				employee.getEmail (),
				employee.getPhone(),
				employee.getIdRole()
				);
	}

	// --------------------------------------------------------------------------------------------
	/**
<<<<<<< HEAD
	 * Sobrescribe el método equals para comparar con otros empleados por ID
	 */
=======
	 * Calcula la cominsión aplicable a un empleado según el número de operaciones y clientes atendidos en los últimos 30 días
	 * 100 para 50 o más operaciones atendidas.
	 * 50 para 20 o más clientes atendidos.
	 * 
	 * @param monthOperationsString - Operaciones realizadas en los úlitmos 30 días
	 * @param monthClientsString - Clientes atendidos en los últimos 30 días
	 * @return Comisión aplicable
	 */
	public static double getCommission (String monthOperationsString, String monthClientsString)
	{
		double commission 		= 0.0;
		double monthOperations 	= 0.0;
		double monthClients 	= 0.0;
		
		try
		{
			monthOperations = Double.parseDouble(monthOperationsString);
			monthClients 	= Double.parseDouble(monthClientsString);

			if (monthOperations >= 50)
			{
				commission += 100;
			}
			
			if (monthClients >= 20)
			{
				commission += 50;
			}
		}
		catch (NumberFormatException exception)
		{
			System.err.println ("bll.BEmployee::getCommission () - Value conversion error");
		}
		
		return commission;
	}	
	
	// --------------------------------------------------------------------------------------------
>>>>>>> V3.1-alertas
	@Override
	public boolean equals (Object object)
	{
		return ((BEmployee)object).getNIF().toLowerCase().equals(getNIF ().toLowerCase());
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
	/***
	 * Sobrescribe el método toString para devolver el nombre y apellidos del empleado
	 */
=======
>>>>>>> V3.1-alertas
	@Override
	public String toString ()
	{
		return getName () + " " + getSurnames () + " ("+ role.getName () +")";
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Object getShowValue (int index)
	{
		if (index == COLUMN_PASSWORD)
		{
			return getAsterisks ();
		}
		else
		{
			return getValue (index);
		}
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
			case COLUMN_PASSWORD: 	return getPassword ();
			case COLUMN_PROVINCE:	return getProvince ();
			case COLUMN_DIRECTION:	return getDirection ();
			case COLUMN_EMAIL:		return getEmail ();
			case COLUMN_PHONE:		return getPhone ();
			case COLUMN_ROLE:		return getRole ();
		}
		
		return null;
	}	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Devuelve una cadena de asteriscos que reemplaza la contrasenna
	 * 
	 * @return cadena de asteriscos
	 */
>>>>>>> V3.1-alertas
	public String getAsterisks ()
	{
		String asterisks = "";
		
		int length = getPassword ().length();
		
		for (int i = 0; i < length; i++) asterisks += "*";
		
		return asterisks;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public void setValue (int column, Object object)
	{
		switch (column)
		{
			case COLUMN_NIF: 		setNiF ((String)object);		break;
			case COLUMN_NAME: 		setName ((String)object);		break;
			case COLUMN_SURNAMES: 	setSurnames ((String)object);	break;
			case COLUMN_PASSWORD:	setPassword ((String)object);	break;
			case COLUMN_PROVINCE:	setProvince ((String)object);	break;
			case COLUMN_DIRECTION:	setDirection ((String)object);	break;
			case COLUMN_EMAIL:		setEmail ((String)object);		break;
			case COLUMN_PHONE:		setPhone ((String)object);		break;
			case COLUMN_ROLE:		setRole ((BRole)object);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------
	/**
	 * @return the rol
	 */
	public BRole getRole() {
		return role;
	}

	/**
<<<<<<< HEAD
	 * @param rol the rol to set
=======
	 * @param role the role to set
>>>>>>> V3.1-alertas
	 */
	public void setRole (BRole role) {
		this.role = role;
	}

<<<<<<< HEAD
=======
	/**
	 * @return the isPermissionWrite
	 */
>>>>>>> V3.1-alertas
	public boolean isPermissionWrite() {
		return role.isPermissionWrite();
	}

<<<<<<< HEAD
	public boolean isPermissionRead() {
		return role.isPermissionRead();
	}

=======
	/**
	 * @return the isPermissionRead
	 */
	public boolean isPermissionRead() {
		return role.isPermissionRead();
	}
	
	/**
	 * @return the rol
	 */
>>>>>>> V3.1-alertas
	public boolean isPermissionRemove() {
		return role.isPermissionRemove();
	}
	
<<<<<<< HEAD
=======
	/**
	 * @return the isPermissionManagement
	 */
>>>>>>> V3.1-alertas
	public boolean isPermissionManagement () {
		return role.isPermissionManagement();
	}

<<<<<<< HEAD
	public boolean verifyPassword(String password) {
		return getPassword ().equals(password);
=======
	/**
	 * @return cierto si la contrasenna es correcta
	 */
	public boolean verifyPassword (String password) {
		return super.verifyPassword (password);
>>>>>>> V3.1-alertas
	}
}
