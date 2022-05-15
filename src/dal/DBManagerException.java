package dal;

// --------------------------------------------------------------------------------------------
public class DBManagerException extends Exception
{
	public static final int GENERIC_EXCEPTION		= 0;
	public static final int ERROR_UNIQUE			= 1;
	private int errorCode;
	
	// ----------------------------------------------------------------------------------------
	DBManagerException (String message)
	{
		super (message);
		errorCode	= GENERIC_EXCEPTION;
	}

	// ----------------------------------------------------------------------------------------
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

