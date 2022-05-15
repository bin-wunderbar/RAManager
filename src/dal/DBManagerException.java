package dal;

// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
/**
 * Excepciones de la clase DBManager
 * @author soporte
 *
 */
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class DBManagerException extends Exception
{
	public static final int GENERIC_EXCEPTION		= 0;
	public static final int ERROR_UNIQUE			= 1;
	private int errorCode;
	
	// ----------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa una excepcion con un mensaje
	 * 
	 * @param message - Mensaje
	 */
>>>>>>> V3.1-alertas
	DBManagerException (String message)
	{
		super (message);
		errorCode	= GENERIC_EXCEPTION;
	}

	// ----------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa una excepcion por codigo de error y mensaje
	 * 
	 * @param errorCode - Codigo de error
	 * @param message - Mensaje
	 */
>>>>>>> V3.1-alertas
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

