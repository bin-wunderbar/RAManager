package dal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Statement;

// ------------------------------------------------------------------------------------------------
/**
 * Generaliza el acceso a la base de datos en las operaciones comunes de forma independiente del tipo de base de datos
 * 
 * @author G3
 *
 */
abstract public class DBDriver 
{
	protected String server;
	protected String user;
	protected String password;
	protected String dataBaseName;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicilaiza el driver de acceso a la base de datos
	 * 
	 * @param server			- Servidor de acceso a la base de datos
	 * @param user				- Usuario de acceso a la base de datos
	 * @param password			- Password de acceso a la base de datos
	 */
	public DBDriver (String server, String user, String password, String dataBaseName)
	{
		this.server			= server;
		this.user			= user;
		this.password		= password;
		this.dataBaseName	= dataBaseName;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Verifica la conexion con la base de datos
	 * @return Cierto si la conexion se ha establecido correctamente
	 */
	public abstract boolean verifyConnection () throws DBManagerException;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Realiza una conexion nueva especificando la base de datos y devuelve una sentencia temporal
	 * 
	 * @return Sentencia nueva
	 * @throws DBManagerException
	 */
	public abstract Statement createStatement (String dataBaseName) throws DBManagerException;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve informacion de version de la base de datos
	 * 
	 * @return Texto informativo sobre la version de la base de datos
	 * @throws DBManagerException
	 */
	public abstract String getInfo () throws DBManagerException;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Realiza una conexion nueva y devuelve una sentencia temporal
	 * 
	 * @return Sentencia nueva
	 * @throws DBManagerException
	 */
	public abstract Statement createStatement () throws DBManagerException;

	
	// --------------------------------------------------------------------------------------------
	/**
	 * Cierra una sentencia temporal 
	 * 
	 */
	public abstract void closeStatement (Statement statement) throws DBManagerException;

	
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Ejecuta como sentencias de sql un archivo de recursos de la solucion
	 * 
	 * @param resourceName		- Nombre del archivo recurso
	 * @throws DBManagerException
	 */
	public void executeResource (String dataBaseName, String resources, String resourceName) throws DBManagerException
	{
		Statement statement;
		try {
			statement = createStatement(dataBaseName);
			
			String sqlStatements = loadStringFromResources (resources, resourceName);
			
			for (String sqlStatement : sqlStatements.split(";"))
			{
				statement.execute (sqlStatement);
			}
			
			closeStatement (statement);
		} catch (SQLException | IOException exception) {
			throw new DBManagerException (exception.getMessage());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve como texto el archivo de recursos especificado
	 * 
	 * @param fileName		- Nombre del archivo de recursos
	 * @return Texto correspondiente al archivo de recursos
	 * @throws IOException
	 */
	public String loadStringFromResources (String resources, String fileName) throws IOException
	{
		
		InputStream inputStream = getClass().getResourceAsStream ("/" + resources + "/" + fileName);

		byte[] content = new byte[inputStream.available()];
		inputStream.read(content);
		
		inputStream.close();
		
		String resource = new String (content, StandardCharsets.UTF_8);
		
		return resource;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Crea la base de datos vacia
	 * @param resourcesPath
	 */
	public abstract void createDataBase (String resourcesPath) throws DBManagerException;
	
	/**
	 * Genera datos para demostracion
	 * @param resourcesPath
	 */
	public abstract void saveDataDemo (String resourcesPath) throws DBManagerException;
	
	/**
	 * Genera datos minimos para empezar desde cero
	 * @param resourcesPath
	 */
	public abstract void saveMinimalDefaults (String resourcesPath) throws DBManagerException;
	
	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------
	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the dataBaseName
	 */
	public String getDataBaseName() {
		return dataBaseName;
	}

	/**
	 * @param dataBaseName the dataBaseName to set
	 */
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

}
