package bll;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

// ------------------------------------------------------------------------------------------------
/**
 * Gestiona los eventos de la aplicacion y el registro de los mismos en el sistema de archivos
 * 
 * @author G4
 *
 */
public class RALogging 
{
	public static final int LEVEL_NOTSET	= 0;
	public static final int LEVEL_DEBUG		= 10;
	public static final int LEVEL_INFO		= 20;
	public static final int LEVEL_WARN		= 30;
	public static final int LEVEL_ERROR		= 40;
	public static final int LEVEL_CRITICAL	= 50;
	
	private String logFileName;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de eventos
	 * 
	 * @param logFileName - Nombre del archivo de log
	 */
	public RALogging (String logFileName)
	{
		this.logFileName = logFileName;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el registro de eventos en el directorio actual (SOBRECARGA POR REQUISITO DE RÚBRICA)
	 */
	public RALogging ()
	{
		this.logFileName = "ramanager.log";
	}

	
	// --------------------------------------------------------------------------------------------
	/**
	 * Imprime en el archivo de registro un evento
	 * 
	 * @param logLevel - Nivel del evento
	 * @param message - Mensaje
	 */
	public void println (int logLevel, String message)
	{
		String errorLevelText 		= "NOTSET";
		
		switch (logLevel)
		{
			case LEVEL_DEBUG: 		errorLevelText = "NOTSET"; break;
			case LEVEL_INFO:		errorLevelText = "INFO"; break;
			case LEVEL_WARN:		errorLevelText = "WARN"; break;
			case LEVEL_ERROR:		errorLevelText = "ERROR"; break;
			case LEVEL_CRITICAL:	errorLevelText = "CRITICAL";
		}
		
		
		String messageLog = 
				errorLevelText + ";" + Util.getIsoFormat(LocalDateTime.now()) + ";" + 
				message + System.lineSeparator();
		
		try 
		{
			FileWriter fileWriter 		= new FileWriter (logFileName, true);
			PrintWriter printWriter 	= new PrintWriter (fileWriter);
			
			printWriter.write (messageLog);

			fileWriter.close ();
		} catch (IOException e) 
		{
			System.out.println (messageLog);
		}

	}
	
}
