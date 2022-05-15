package dal;

// --------------------------------------------------------------------------------------------
/**
 * Excepciones de la clase DBManager
 * @author soporte
 *
 */
@SuppressWarnings("serial")
public class DBManagerException extends Exception
{
	public static final int GENERIC_EXCEPTION		= 0;
	public static final int ERROR_UNIQUE			= 1;
	private int errorCode;
	
	// ----------------------------------------------------------------------------------------
	/**
	 * Inicializa una excepcion con un mensaje
	 * 
	 * @param message - Mensaje
	 */
	DBManagerException (String message)
	{
		super (message);
		errorCode	= GENERIC_EXCEPTION;
	}

	// ----------------------------------------------------------------------------------------
	/**
	 * Inicializa una excepcion por codigo de error y mensaje
	 * 
	 * @param errorCode - Codigo de error
	 * @param message - Mensaje
	 */
	DBManagerException (int errorCode, String message)
	{
		super (message);
		this.errorCode	= errorCode;
	}

	// ----------------------------------------------------------------------------------------
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

