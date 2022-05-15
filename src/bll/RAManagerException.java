package bll;

// ------------------------------------------------------------------------------------------------
/**
 * Excepciones de RAManager
 * 
 * @author G4
 *
 */
@SuppressWarnings("serial")
public class RAManagerException extends Exception 
{
	private int errorCode;

	public static final int NO_ERROR									= 0;
	public static final int UNKNOW_ERROR								= -9999; 
	
	public static final int MYSQL_ERROR_CODE_SERVICE_NOT_AVAILABLE		= -1;
	public static final int MYSQL_ERROR_CODE_BAD_LOGIN					= 1045;
	public static final int MYSQL_ERROR_CODE_DATABASE_DONT_EXISTS		= 1049;
	public static final int MYSQL_ERROR_CODE_LOCKED_ROOT				= 1698;
	
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa una excepcion
	 * 
	 * @param message - Mensaje
	 */
	public RAManagerException (String message)
	{
		super (message);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa una excepcion y registra el mensaje como evento de aplicacion
	 * 
	 * @param rALogging - Gestor de logging
	 * @param message - Mensaje
	 */
	public RAManagerException (RALogging rALogging, String message)
	{
		super (message);
		rALogging.println(RALogging.LEVEL_ERROR, message);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa una excepcion y registra el mensaje como evento de aplicacion
	 * 
	 * @param rALogging - Gestor de logging
	 * @param errorCode - Codigo de error
	 * @param message - Mensaje
	 */
	public RAManagerException (RALogging rALogging, int errorCode, String message)
	{
		super (message);
		this.errorCode = errorCode;
		rALogging.println(RALogging.LEVEL_ERROR, message);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
