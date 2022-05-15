package dal;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// ------------------------------------------------------------------------------------------------
/**
 * Proporciona el driver de acceso a la base de datos MariaDB
 * 
 * @author G3
 *
 */
public class DBDriverDerby extends DBDriver 
{

	private Connection tmpConnection;
	private String dataBaseSqlFileName;
	private String insertDemoSqlFileName;
	private String insertMinimalSqlFileName;

	// --------------------------------------------------------------------------------------------
	/**
	 * Iniciliza el driver de acceso a MariaDB
	 * 
	 * @param server		- Servidor de acceso a la base de datos
	 * @param user			- Usuario de acceso a la base de datos
	 * @param password		- Passowrd de acceso a la base de datos
	 */
	public DBDriverDerby (String server, String user, String password, String dataBaseName, String dataBaseSqlFileName, String insertDemoSqlFileName, String insertMinimalSqlFileName) 
	{
		super(server, user, password, dataBaseName);
		
		this.dataBaseSqlFileName		= dataBaseSqlFileName;
		this.insertDemoSqlFileName 		= insertDemoSqlFileName;
		this.insertMinimalSqlFileName	= insertMinimalSqlFileName;
		
		try {
			DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean verifyConnection() throws DBManagerException
	{
		File file = new File (getStringDataBaseLocation ());
		if (file.exists())
		{
			try {
				DBManager.printlnDebug ("DBDriverDerby::verifyConnection () - Connection String (" + getStringConnection() + ")");;
				Connection connection = DriverManager.getConnection (getStringConnection ());
				connection.close ();
				
				return true;
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
			}
		}
		
		return false;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la cadena de conexion
	 * 
	 * @return Cadena de conexion
	 */
	private String getStringConnection ()
	{
		return "jdbc:derby:" + getStringDataBaseLocation () + ";create=true"; 
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la localizacion de la base de datos
	 * 
	 * @return Localizacion de la base de datos
	 */
	private String getStringDataBaseLocation ()
	{
		return server + File.separatorChar + dataBaseName;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Statement createStatement () throws DBManagerException
	{
		return createStatement (dataBaseName);
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Statement createStatement (String dataBaseName) throws DBManagerException
	{
		try {
			tmpConnection = DriverManager.getConnection (getStringConnection ());

			return tmpConnection.createStatement();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void closeStatement (Statement statement) throws DBManagerException 
	{
		try {
			statement.close ();
			tmpConnection.close ();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public String getInfo() throws DBManagerException 
	{
		String info = "Derby DataBase: ";
		
		try {
			Connection connection = DriverManager.getConnection (getStringConnection ());
			info += connection.getMetaData().getDatabaseProductVersion();
			connection.close ();
		} catch (SQLException sqlException) {
			throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
		}
		
		return info + ")";
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void createDataBase(String resourcesPath) throws DBManagerException {
		File file = new File (server);
		
		if (file.exists())
		{
			deleteTree (file);
		}
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executeResource (dataBaseName, resourcesPath, dataBaseSqlFileName);	
		
		DBManager.printlnDebug("DBDriverDerby::createDataBase () - Create database ok (" + server + ")");
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina el arbol de directorio especificado
	 * 
	 * @param file
	 */
	private void deleteTree (File file)
	{
		File[] contents = file.listFiles();
		
		if (contents != null) 
		{
		    for (File contentFile : contents) {
		        deleteTree (contentFile);
		    }
		}
		
		file.delete();
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void saveDataDemo(String resourcesPath) throws DBManagerException {
		createDataBase (resourcesPath);
		executeResource (dataBaseName, resourcesPath, insertDemoSqlFileName);		
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void saveMinimalDefaults(String resourcesPath) throws DBManagerException {
		createDataBase (resourcesPath);
		executeResource (dataBaseName, resourcesPath, insertMinimalSqlFileName);
	}
	
}
