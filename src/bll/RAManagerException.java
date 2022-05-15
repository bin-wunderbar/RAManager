package bll;

// ------------------------------------------------------------------------------------------------
public class RAManagerException extends Exception 
{
	public static final int GENERIC_EXCEPTION		= 0;
	public static final int ERROR_UNIQUE			= 1;
	private int errorCode;
	
	// --------------------------------------------------------------------------------------------
	public RAManagerException (String message)
	{
		super (message);
	}
	
	// --------------------------------------------------------------------------------------------
	public RAManagerException (RALogging rALogging, String message)
	{
		super (message);
		rALogging.println(RALogging.LEVEL_ERROR, message);
	}
	
	// --------------------------------------------------------------------------------------------
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
