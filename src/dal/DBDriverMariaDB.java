package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// ------------------------------------------------------------------------------------------------
/**
 * Proporciona el driver de acceso a la base de datos MariaDB
 * 
 * @author G3
 *
 */
public class DBDriverMariaDB extends DBDriver 
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
	public DBDriverMariaDB (String server, String user, String password, String dataBaseName, String dataBaseSqlFileName, String insertDemoSqlFileName, String insertMinimalSqlFileName) 
	{
		super(server, user, password, dataBaseName);
		
		this.dataBaseSqlFileName		= dataBaseSqlFileName;
		this.insertDemoSqlFileName 		= insertDemoSqlFileName;
		this.insertMinimalSqlFileName	= insertMinimalSqlFileName;
		
		try {
			DriverManager.registerDriver (new org.mariadb.jdbc.Driver ());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean verifyConnection() throws DBManagerException
	{
		try {
			Connection connection = DriverManager.getConnection ("jdbc:mysql://" + server + "/" + dataBaseName + "?allowMultiQueries=true", user, password);
			connection.close ();
			
			return true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
		}
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
			tmpConnection = DriverManager.getConnection ("jdbc:mysql://" + server + "/" + dataBaseName + "?allowMultiQueries=true", user, password);

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
		String info = "";

		try {
			Statement statement = createStatement ();
			
			statement.execute ("show variables like 'version'");
			ResultSet resultSet = statement.getResultSet();
			
			if (resultSet.next())
			{
				info = resultSet.getString("Value");
			}
			
			closeStatement(statement);
		} catch (SQLException sqlException) {
			throw new DBManagerException (sqlException.getErrorCode(), sqlException.getMessage());
		}
		
		return info;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void createDataBase(String resourcesPath) throws DBManagerException
	{
		executeResource ("", resourcesPath, dataBaseSqlFileName);
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
