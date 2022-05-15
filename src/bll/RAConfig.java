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

import racrypt.RACrypt;

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
	
	public static final String ENGINE_MARIADB_STRING 	= "mariadb";
	public static final String ENGINE_DERBY_STRING		= "derby";
	public static final String DIRECTORY_RAMANAGER 		= ".ramanager";
	public static final String CONFIG_FILE 				= "raconfig.properties";

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
	private String loggingFileName;

	private String dataBaseEngine;
	private String sqlServer;
	private String sqlUser;
	private String sqlPassword;
	
	
	//public static final Logger LOGGER 				= Logger.getLogger (RAConfig.class.getName());
	
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa la configuracion de la aplicacion
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
		// Miscelanea fields
		languageName			= "es";
		iva 					= 0.21;
		currency				= "€";
		printFontSize			= 6;
		
		loggingFileName			= getHome () + File.separatorChar + DIRECTORY_RAMANAGER + File.separatorChar + "ramanager.log";
		
		// Company fields
		companyName 			= "Rekord Autoak S.L.";
		companyDirection 		= "Garat Txomin Hiribidea, 6, 48004 Bilbo, Bizkaia";
		companyProvince			= "Vizcaya"; 
		companyMail				= "contacto@rekordautoak.com"; 
		companyPhone			= "+34 666 777 888";
		companyNIF				= "89526899S";

		// SQL fields
		dataBaseEngine			= ENGINE_MARIADB_STRING;
		sqlServer				= "localhost";
		sqlUser					= "mysqladmin";
		sqlPassword				= "Password1";
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda en la unidad de almacenamiento los datos de configuracion
	 * @throws IOException
	 */
	public void saveToDisk () throws IOException
	{
		File fileDirectoryManager = new File (this.getRAManagerPath());
		
		if (!fileDirectoryManager.exists())
		{
			fileDirectoryManager.mkdirs();
		}

		FileOutputStream fileOutputStream = new FileOutputStream (getConfigPath());
		
		fileOutputStream.write(("languageName=" + languageName + "\n").getBytes());
		fileOutputStream.write(("iva=" + iva + "\n").getBytes());
		fileOutputStream.write(("currency=" + currency + "\n").getBytes());
		fileOutputStream.write(("printFontSize=" + printFontSize + "\n").getBytes());
		fileOutputStream.write(("loggingFileName=" + loggingFileName + "\n").getBytes());
		fileOutputStream.write(("companyName=" + companyName + "\n").getBytes());
		fileOutputStream.write(("companyDirection=" + companyDirection + "\n").getBytes());
		fileOutputStream.write(("companyProvince=" + companyProvince + "\n").getBytes());
		fileOutputStream.write(("companyMail=" + companyMail + "\n").getBytes());
		fileOutputStream.write(("companyPhone=" + companyPhone + "\n").getBytes());
		fileOutputStream.write(("companyNIF=" + companyNIF + "\n").getBytes());
		fileOutputStream.write(("dataBaseEngine=" + dataBaseEngine + "\n").getBytes());
		fileOutputStream.write(("sqlServer=" + sqlServer + "\n").getBytes());
		fileOutputStream.write(("sqlUser=" + sqlUser + "\n").getBytes());
		String obfuscatedPassword = RACrypt.toHexString(RACrypt.parseToObfuscatedData(sqlPassword));
		fileOutputStream.write(("sqlPassword=" + obfuscatedPassword + "\n").getBytes());
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Recupera de la unidad de almacenamiento los datos de configuracion
	 */
	private void loadFromDisk ()
	{
		try {
			HashMap <String, String> rAConfigDictionary = getDictionaryFromStream (new FileInputStream (getConfigPath ()));
			
			if (rAConfigDictionary.containsKey("languageName"))	languageName = rAConfigDictionary.get ("languageName");
			if (rAConfigDictionary.containsKey("iva")) iva = Double.parseDouble (rAConfigDictionary.get ("iva"));
			if (rAConfigDictionary.containsKey("currency")) currency = rAConfigDictionary.get ("currency");
			if (rAConfigDictionary.containsKey("printFontSize")) printFontSize = Integer.parseInt (rAConfigDictionary.get ("printFontSize"));
			
			if (rAConfigDictionary.containsKey("loggingFileName")) loggingFileName = rAConfigDictionary.get ("loggingFileName");
			
			if (rAConfigDictionary.containsKey("companyName")) companyName = rAConfigDictionary.get ("companyName");
			if (rAConfigDictionary.containsKey("companyDirection")) companyDirection = rAConfigDictionary.get ("companyDirection");
			if (rAConfigDictionary.containsKey("companyProvince")) companyProvince = rAConfigDictionary.get ("companyProvince");
			if (rAConfigDictionary.containsKey("companyMail")) companyMail = rAConfigDictionary.get ("companyMail");
			if (rAConfigDictionary.containsKey("companyPhone")) companyPhone = rAConfigDictionary.get ("companyPhone");
			if (rAConfigDictionary.containsKey("companyNIF")) companyNIF = rAConfigDictionary.get ("companyNIF");
			if (rAConfigDictionary.containsKey("dataBaseEngine")) dataBaseEngine = rAConfigDictionary.get ("dataBaseEngine");
			if (rAConfigDictionary.containsKey("sqlServer")) sqlServer = rAConfigDictionary.get ("sqlServer");
			if (rAConfigDictionary.containsKey("sqlUser")) sqlUser = rAConfigDictionary.get ("sqlUser");
			if (rAConfigDictionary.containsKey("sqlPassword"))
			{
				String obfuscatedPassword = rAConfigDictionary.get ("sqlPassword"); 
				sqlPassword = RACrypt.parseFromObfuscatedData(RACrypt.fromHexString(obfuscatedPassword));
			}
			
		} catch (IOException e) {
			loadDefaults ();
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un diccionario que representa todos los pares de clave y valor del flujo especificado
	 * @param inputStream - Flujo de datos de texto con los pares clave y valor
	 * @return - Devuelve el diccionario con la configuracion
	 * @throws IOException
	 */
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
	/**
	 * Devuelve la ruta del sistema de archivos de la aplicacion
	 * @return - Ruta de la aplicacion
	 */
	public String getRAManagerPath ()
	{
		return getHome () + File.separatorChar + DIRECTORY_RAMANAGER;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la ruta en el sistema de archivos del archivo de configuracion
	 * @return - Ruta del archivo de configuracion 
	 */
	public String getConfigPath ()
	{
		return getHome () + File.separatorChar + DIRECTORY_RAMANAGER + File.separatorChar + CONFIG_FILE;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la ruta del perfil del usuario en el sistema de archivos
	 * @return - Ruta del perfil del usuario
	 */
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
	 * Devuelve el campo de servidor como ruta de acceso a la base de datos local
	 */
	public String getServerAsDefaultDirectory() 
	{
		return getRAManagerPath() + File.separatorChar + ENGINE_DERBY_STRING;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el campo de servidor como host por defecto
	 */
	public String getServerAsDefaultHost() 
	{
		return "localhost";
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Configura el campo de servidor como ruta de acceso a la base de datos local
	 */
	public void setServerAsDefaultDirectory() 
	{
		sqlServer = getServerAsDefaultDirectory ();
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Configura el campo de servidor como host por defecto
	 */
	public void setServerAsDefaultHost() 
	{
		sqlServer = getServerAsDefaultHost ();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve informacion de estado sobre el usuario que ha iniciado sesion
	 * @return - Informacion de estado del usuario
	 */
	public static String getStatus (BEmployee login, String infoDB)
	{
		return login.getName () + " " + login.getSurnames() + " : " + login.getRole() + " - (" + infoDB + ")";
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
	 * 
	 * @return currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * 
	 * @return printFontSize
	 */
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

	public String getSqlServer() {
		return sqlServer;
	}

	public void setSqlServer(String sqlServer) {
		this.sqlServer = sqlServer;
	}

	public String getSqlUser() {
		return sqlUser;
	}

	public void setSqlUser(String sqlUser) {
		this.sqlUser = sqlUser;
	}

	public String getSqlPassword() {
		return sqlPassword;
	}

	public void setSqlPassword(String sqlPassword) {
		this.sqlPassword = sqlPassword;
	}

	/**
	 * @return the dataBaseEngine
	 */
	public String getDataBaseEngine() {
		return dataBaseEngine;
	}

	/**
	 * @param dataBaseEngine the dataBaseEngine to set
	 */
	public void setDataBaseEngine(String dataBaseEngine) {
		this.dataBaseEngine = dataBaseEngine;
	}

	/**
	 * Verifica si el archivo de configuracion de la aplicacion existe
	 * 
	 * @return Cierto si existe el archivo de configuracion
	 */
	public boolean fileConfigurationExists() 
	{
		File file = new File (getConfigPath());
				
		return file.exists();
	}

	/**
	 * Verifica si el directorio de base de datos de derby existe
	 * @return
	 */
	public boolean derbyDataBaseExists() 
	{
		File directory = new File (getServerAsDefaultDirectory());
		
		return directory.exists();
	}


}
