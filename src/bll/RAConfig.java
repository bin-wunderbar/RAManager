package bll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

// ------------------------------------------------------------------------------------------------
/***
 * 
 * Configuración de la aplicación
 * 
 * @author G1
 *
 */
public class RAConfig 
{
	private String languageName;
	private double iva;
	private String currency;
	private int printFontSize;
	private String companyName;
	private String companyDirection;
	private String companyProvince;
	private String companyMail;
	private String companyPhone;
	private String companyNIF;
	private String databaseConnection;
	private String loggingFileName;
	
	public static final String directoryRAManager 	= ".ramanager";
	public static final String configFile 			= "raconfig.properties";
	
	//public static final Logger LOGGER 				= Logger.getLogger (RAConfig.class.getName());
	
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa la configuración de la aplicación
	 */
	public RAConfig ()
	{
		loadDefaults ();
		loadFromDisk ();
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa la configuración de la aplicación (SOBRECARGA POR REQUISITO DE RÚBRICA)
	 */
	public RAConfig (String languageName)
	{
		this.languageName =languageName;
		loadDefaults ();
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Carga los datos de configuración
	 */
	public final void loadDefaults ()
	{
		languageName			= "es";
		iva 					= 0.21;
		currency				= "€";
		printFontSize			= 6;
		databaseConnection		= getHome () + File.separatorChar + directoryRAManager + File.separatorChar + "database";
		
		loggingFileName			= getHome () + File.separatorChar + directoryRAManager + File.separatorChar + "ramanager.log";
		
		// Company fields
		companyName 			= "Rekord Autoak S.L.";
		companyDirection 		= "Garat Txomin Hiribidea, 6, 48004 Bilbo, Bizkaia";
		companyProvince			= "Vizcaya"; 
		companyMail				= "contacto@rekordautoak.com"; 
		companyPhone			= "+34 666 777 888";
		companyNIF				= "89526899S";
	}
	
	// --------------------------------------------------------------------------------------------
	public void saveToDisk () throws IOException
	{
		File fileDirectoryManager = new File (this.getDirectoryRAManagerPath());
		
		if (!fileDirectoryManager.exists())
		{
			fileDirectoryManager.mkdirs();
		}

		FileOutputStream fileOutputStream = new FileOutputStream (getConfigPath());
		
		fileOutputStream.write(("languageName=" + languageName + "\n").getBytes());
		fileOutputStream.write(("iva=" + iva + "\n").getBytes());
		fileOutputStream.write(("currency=" + currency + "\n").getBytes());
		fileOutputStream.write(("printFontSize=" + printFontSize + "\n").getBytes());
		fileOutputStream.write(("databaseConnection=" + databaseConnection + "\n").getBytes());
		fileOutputStream.write(("loggingFileName=" + loggingFileName + "\n").getBytes());
		fileOutputStream.write(("companyName=" + companyName + "\n").getBytes());
		fileOutputStream.write(("companyDirection=" + companyDirection + "\n").getBytes());
		fileOutputStream.write(("companyProvince=" + companyProvince + "\n").getBytes());
		fileOutputStream.write(("companyMail=" + companyMail + "\n").getBytes());
		fileOutputStream.write(("companyPhone=" + companyPhone + "\n").getBytes());
		fileOutputStream.write(("companyNIF=" + companyNIF + "\n").getBytes());
	}
	
	// --------------------------------------------------------------------------------------------
	private void loadFromDisk ()
	{
		try {
			HashMap <String, String> rAConfigDictionary = getDictionaryFromStream (new FileInputStream (getConfigPath ()));
			
			if (rAConfigDictionary.containsKey("languageName"))	languageName = rAConfigDictionary.get ("languageName");
			if (rAConfigDictionary.containsKey("iva")) iva = Double.parseDouble (rAConfigDictionary.get ("iva"));
			if (rAConfigDictionary.containsKey("currency")) currency = rAConfigDictionary.get ("currency");
			if (rAConfigDictionary.containsKey("printFontSize")) printFontSize = Integer.parseInt (rAConfigDictionary.get ("printFontSize"));
			if (rAConfigDictionary.containsKey("databaseConnection")) databaseConnection = rAConfigDictionary.get ("databaseConnection");
			
			if (rAConfigDictionary.containsKey("loggingFileName")) loggingFileName = rAConfigDictionary.get ("loggingFileName");
			
			if (rAConfigDictionary.containsKey("companyName")) companyName = rAConfigDictionary.get ("companyName");
			if (rAConfigDictionary.containsKey("companyDirection")) companyDirection = rAConfigDictionary.get ("companyDirection");
			if (rAConfigDictionary.containsKey("companyProvince")) companyProvince = rAConfigDictionary.get ("companyProvince");
			if (rAConfigDictionary.containsKey("companyMail")) companyMail = rAConfigDictionary.get ("companyMail");
			if (rAConfigDictionary.containsKey("companyPhone")) companyPhone = rAConfigDictionary.get ("companyPhone");
			if (rAConfigDictionary.containsKey("companyNIF")) companyNIF = rAConfigDictionary.get ("companyNIF");
			
		} catch (IOException e) {
			loadDefaults ();
		}
	}

	// --------------------------------------------------------------------------------------------
	public static HashMap <String, String> getDictionaryFromStream (InputStream inputStream) throws IOException
	{
		HashMap <String, String> dictionary = new HashMap <> ();
		
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		
		Scanner scanner = new Scanner (inputStreamReader);

		
		String[] lineData;
		final int ID_KEY 	= 0;
		final int ID_VALUE 	= 1;
		
		dictionary.clear();
		while (scanner.hasNextLine())
		{
			lineData = scanner.nextLine ().split("=");

			if (lineData != null)
			{
				dictionary.put (
						lineData[ID_KEY].trim (), 
						lineData[ID_VALUE].trim ().replace("\\n", "\n")
						);
			}
		}

		scanner.close();
		inputStream.close();
		
		return dictionary;
	}
	
	// --------------------------------------------------------------------------------------------
	public String getDirectoryRAManagerPath ()
	{
		return getHome () + File.separatorChar + directoryRAManager;
	}
	
	// --------------------------------------------------------------------------------------------
	public String getConfigPath ()
	{
		return getHome () + File.separatorChar + directoryRAManager + File.separatorChar + configFile;
	}
	
	// --------------------------------------------------------------------------------------------
	public String getHome ()
	{
		// Get home from Linux
		String home = System.getenv ("HOME");
		
		if (home == null)
		{
			// Get home from Windows
			home = System.getenv("USERPROFILE");
		}
		
		if (home == null)
		{
			// Default home at system fails
			home = ".";
		}
		
		return home;
	}
	
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve información de estado sobre el usuario que ha iniciado sesión
	 * @return
	 */
	public static String getStatus (BEmployee login)
	{
		return login.getName () + " " + login.getSurnames() + " : " + login.getRole();
	}
	
	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------

	/**
	 * @return the iva
	 */
	public double getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(double iva) {
		this.iva = iva;
	}

	/**
	 * @return the languageName
	 */
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * @param languageName the languageName to set
	 */
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	/**
	 * @param currency Tipo de moneda utilizada
	 */
	public String getCurrency() {
		return currency;
	}

	public int getPrintFontSize() {
		return printFontSize;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the companyDirection
	 */
	public String getCompanyDirection() {
		return companyDirection;
	}

	/**
	 * @param companyDirection the companyDirection to set
	 */
	public void setCompanyDirection(String companyDirection) {
		this.companyDirection = companyDirection;
	}

	/**
	 * @return the companyProvince
	 */
	public String getCompanyProvince() {
		return companyProvince;
	}

	/**
	 * @param companyProvince the companyProvince to set
	 */
	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	/**
	 * @return the companyMail
	 */
	public String getCompanyMail() {
		return companyMail;
	}

	/**
	 * @param companyMail the companyMail to set
	 */
	public void setCompanyMail(String companyMail) {
		this.companyMail = companyMail;
	}

	/**
	 * @return the companyPhone
	 */
	public String getCompanyPhone() {
		return companyPhone;
	}

	/**
	 * @param companyPhone the companyPhone to set
	 */
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	/**
	 * @return the companyNIF
	 */
	public String getCompanyNIF() {
		return companyNIF;
	}

	/**
	 * @param companyNIF the companyNIF to set
	 */
	public void setCompanyNIF(String companyNIF) {
		this.companyNIF = companyNIF;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @param printFontSize the printFontSize to set
	 */
	public void setPrintFontSize(int printFontSize) {
		this.printFontSize = printFontSize;
	}

	public String getDatabaseConnection() {
		return databaseConnection;
	}

	/**
	 * @return the loggingFileName
	 */
	public String getLoggingFileName() {
		return loggingFileName;
	}

	/**
	 * @param loggingFileName the loggingFileName to set
	 */
	public void setLoggingFileName(String loggingFileName) {
		this.loggingFileName = loggingFileName;
	}

	/**
	 * @param databaseConnection the databaseConnection to set
	 */
	public void setDatabaseConnection(String databaseConnection) {
		this.databaseConnection = databaseConnection;
	}
	
}
