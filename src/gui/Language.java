package gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


import bll.RAConfig;
import bll.RALogging;

//------------------------------------------------------------------------------------------------
/***
 * Clase de idioma para el proyecto Rekord Autoak (RA)
 * @author G1
 */
public class Language 
{
	public static final String LANGUAGE_DIRECTORY = "resources/lang";
	
	private HashMap <String, String> dictionary;
	private String domainName;
	private RALogging rALogging;
	
	private String[] roleColumnNames;
	private String[] materialColumnNames;
	private String[] clientColumnNames;
	private String[] orderColumnNames;
	private String[] vehicleColumnNames;
	private String[] employeColumnNames;
	private String[] providerColumnNames;
	private String[] operationColumnNames;
	private String[] serviceColumnNames;
	
	

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el objeto con el lenguaje indicado
	 * 
	 * @param domainName 	- Abreviatura del idioma (es, eus, en, etc..) 
	 */
	public Language (RALogging rALogging, String domainName)
	{
		this.rALogging 	= rALogging;
		dictionary	 	= new HashMap <> ();
		
		loadTranslation (domainName);
	}
	
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Carga el idioma según la abreviatura.
	 * @param domainName 	- Abreviatura del idioma (es, eus, en, etc..)
	 */
	public void loadTranslation (String domainName)
	{
		this.domainName	= domainName;
		loadFromPropertiesFile (domainName);
		
		clientColumnNames = new String[] {
			get ("columnNIF"),
			get ("columnName"),
			get ("columnSurnames"),
			get ("columnProvince"),
			get ("columnDirection"),
			get ("columnMail"),
			get ("columnPhone")
			};

		orderColumnNames = new String[] {
			get ("columnDateTime"),
			get ("columnDescription"),
			get ("columnIssuedDateTime"),
			get ("columnAccept"),
			get ("columnClient"),
			get ("columnVehicle"),
			get ("columnOperator"),
			get ("columnStatus")
		};
		
		vehicleColumnNames = new String[] {
				get ("columnRegisterNumber"),
				get ("columnModel"),
				get ("columnColor"),
				get ("idClient")
		};
		
		employeColumnNames = new String[] {
				get ("columnNIF"),
				get ("columnName"),
				get ("columnSurnames"),
				get ("columnPassword"),
				get ("columnProvince"),
				get ("columnDirection"),
				get ("columnMail"),
				get ("columnPhone"),
				get ("columnRole")
		};
		
		providerColumnNames = new String[] {
				get ("columnName"),
				get ("columnDirection"),
				get ("columnEmail"),
				get ("columnPhone")
		};
		
		roleColumnNames = new String [] {
				get ("columnRolName"),
				get ("columnPermissionRead"),
				get ("columnPermissionWrite"),
				get ("columnPermissionRemove"),
				get ("columnPermissionManagement")
		};
		
		materialColumnNames = new String [] {
				get ("columnMaterialName"),
				get ("columnDescription"),
				get ("columnUnitPrice"),
				get ("columnIdProvider")
		};
		
		operationColumnNames = new String[] {
				get ("columnMechanic"),
				get ("columnService"),
				get ("columnDedicatedMinutes"),
				get ("columnHourlyPriceApplied")
		};
		
		serviceColumnNames = new String[] {
			get ("columnDescription"),
			get ("columnHourlyPrice")
		};
		
	}
	
	// --------------------------------------------------------------------------------------------
	private void loadFromPropertiesFile (String languageName)
	{
		try {
			InputStream inputStream = getClass().getResourceAsStream ("/" + LANGUAGE_DIRECTORY + "/" + languageName + ".txt");

			dictionary = RAConfig.getDictionaryFromStream (inputStream);
			
		} catch (Exception e) {
			rALogging.println(RALogging.LEVEL_ERROR, "Language::loadFromPropertiesFile (...) - Can't load language");
			e.printStackTrace();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	// GETTERS
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	public String get (String key)
	{
		String value = dictionary.get(key); 
		return value == null ? key : value;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * - La función devuelve los nombres de dominio {"es", "en", "eus"}, lista los archivos disponibles en el directorio de recursos
	 *  extrayendo los nombres que coincidirán con el dominio tipo es.txt, en.txt, etc...
	 * 
	 * @return the domainNames
	 */
	public String[] getDomainNames() {
		
		File  languageDirectoryFile = new File (getClass ().getResource ("/" + LANGUAGE_DIRECTORY).getFile());
		String[] domainNames = {"es"};
		
		String[] fileNames = languageDirectoryFile.list();
		
		if (fileNames != null)		// Files from system directory
		{
			domainNames = getDomainNamesFromFileNames (fileNames);
		}
		else 						// Files from jar file
		{
			try {
				
				String locationPath = Language.class.getProtectionDomain().getCodeSource().getLocation().toURI ().getPath();

				final File jarFile = new File(locationPath);
	        	String fileNamesString = "";

				if (jarFile.isFile()) 
				{
				    JarFile jar = new JarFile(jarFile);
				    Enumeration<JarEntry> entries = jar.entries();
				    
				    while (entries.hasMoreElements()) 
				    {
				        String entryName = entries.nextElement().getName();
				        if (entryName.contains(LANGUAGE_DIRECTORY))
				        {
				            String fileName = entryName.substring (entryName.lastIndexOf (File.separatorChar) + 1);
				            
				            if (!fileName.isEmpty())
				            {
					            System.out.println ("Language::getDomainNames () - fileName: [" + fileName + "]");
				            	fileNamesString += fileName + "\n";
				            }
				            
				        }
				    }
				    
				    domainNames = this.getDomainNamesFromFileNames(fileNamesString.split ("\n"));
				    
				    jar.close();
				}
				
			} catch (IOException | URISyntaxException e) {
				rALogging.println(RALogging.LEVEL_ERROR, "Language::getDomainNames (...) - Unable to list language files");
				e.printStackTrace();
			}
		}
		
		return domainNames;
	}
	
	// --------------------------------------------------------------------------------------------
	private String[] getDomainNamesFromFileNames (String[] fileNames)
	{
		String[] domainNames = new String[fileNames.length];
		
		for (int i = 0; i < fileNames.length; i++)
		{
			domainNames[i] = fileNames[i].substring(0, fileNames[i].lastIndexOf("."));
		}

		return domainNames;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * 
	 * @return the clientColumnNames
	 */
	public String[] getClientColumnNames ()
	{
		return clientColumnNames;
	}


	/**
	 * @return the orderColumnNames
	 */
	public String[] getOrderColumnNames() {
		return orderColumnNames;
	}

	public String[] getProviderColumnNames() {
		return providerColumnNames;
	}
	
	/**
	 * @return the vehicleColumnNames
	 */
	public String[] getVehicleColumnNames() {
		return vehicleColumnNames;
	}

	/**
	 * @return the employeColumnNames
	 */
	public String[] getEmployeeColumnNames() {
		return employeColumnNames;
	}


	public String[] getRoleColumnNames() {
		return roleColumnNames;
	}

	public String[] getMaterialColumnNames() {
		return materialColumnNames;
	}


	public String[] getOperationColumnNames() {
		return operationColumnNames;
	}


	public String[] getServiceColumnNames() {
		return serviceColumnNames;
	}

	public String getSelectedLanguage() {
		return domainName;
	}
	
}
