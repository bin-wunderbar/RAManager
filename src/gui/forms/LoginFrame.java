package gui.forms;

import java.awt.BorderLayout;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;

import bll.BEmployee;
import bll.RAConfig;
import bll.RAManager;
import bll.RAManagerException;
import fromScratchTest.RunTest;
import fromScratchTest.UnitTestDialog;
import gui.Language;
import gui.ValueChecks;
import gui.controls.RAComboBox;
import gui.dialogs.ExceptionDialog;
import gui.dialogs.CredentialsDBDialog;
import gui.forms.mainframe.MainFrame;
import gui.forms.orderbyloginform.OrderByLoginForm;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.ActionEvent;

//-------------------------------------------------------------------------------------------------

/***
 * Formulario de Inicio de Sesion
 * @author G1
 *
 */
@SuppressWarnings("serial")
public class LoginFrame extends JFrame 
{
	private JPanel contentPane;
	private RAComboBox <BEmployee> rAComboBoxLogin;
	private JPasswordField pwdPassword;
	private LoginFrame loginFrame;
	
	private Language language;
	private RAConfig config;
	private RAManager rAManager;
	private ValueChecks valueChecks;
	private static boolean debug;
	
	private static final int ERROR_BAD_COMMAND_LINE_PARAMETER 	= 1;
	private static final int ERROR_LOAD_DATABASE				= 2;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Muestra la ayuda de línea de órdenes
	 * 
	 * @param args	Argumentos de la línea de órdenes
	 */
	private static void printCommandLineHelp (String[] args)
	{
		System.err.println (
			"Bad command line parameter (" + args[0] + ")\n" +
			"Use:\n" +
			"RAManager.jar [/test|/testdialog]\n" +
			"	/test			Launch tests in command line interface.\n" +
			"	/testdialog		Launch tests in graphic user interface\n" + 
			"	/debug			Launch app without debug console info\n" +
			"	/savedatademo	Create a config data and database example"
			);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Lanza la aplicación en diferentes modos
	 *  
	 * @param args		- Argumentos de entrada <br>
	 * Un argumento <strong>vacío</strong> lanza el diálogo de entrada a la aplicación completa. <br>
	 * 	 <strong>/test</strong>			- Ejecuta las pruebas unitarias en modo línea de comando y finaliza la aplicación. <br>
	 *   <strong>/testdialog</strong>	- Ejecuta las pruebas unitarias en modo interfaz gráfico de usuario y finaliza la aplicación. <br>
	 *   <strong>/debug</strong>		- Lanza la aplicación en modo depuración, agregando información de operaciones en la consola.<br>
	 *   <strong>/savedatademo</strong>	- Crea datos de configuración y un ejemplo de base de datos. <br>
	 */
	public static void main(String[] args) {
		
		debug = false;
		
		if (args.length >= 1)
		{
			switch (args[0].toLowerCase())
			{
				case "/test":
					System.out.println ((new RunTest ()).getResults ());
					break;
					
				case "/testdialog":
					new UnitTestDialog ((new RunTest ()).getResults ());
					break;
					
				case "/debug":
					debug = true;
					launchApp ();
					break;
					
				case "/savedatademo":
					Scanner console = new Scanner (System.in);
					System.out.println ("Warning! All application data will be deleted.");
					System.out.println ("Are you sure? [N,s]");
					String decision = console.next ();
					
					if (decision.length () == 1 && decision.toLowerCase().charAt(0) == 's')
					{
						cmdLineSaveDataDemo ();
						System.out.println ("Demo created.");
					}
					else
					{
						System.out.println ("Demo canceled");
					}
					break;
					
				default:
					printCommandLineHelp (args);
					System.exit (ERROR_BAD_COMMAND_LINE_PARAMETER);
			}
		}
		else
		{
			launchApp ();
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Genera datos de demostracion y la guarda
	 * 
	 */
	private static void cmdLineSaveDataDemo ()
	{
		RAConfig config			= new RAConfig ();
		RAManager rAManager 	= new RAManager (config, false);
		
		try {
			config.saveToDisk();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			rAManager.saveDemo();
		} catch (RAManagerException e) {
			e.printStackTrace();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Lanza la aplicacion
	 */
	private static void launchApp ()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Create the login frame.
	 */
	public LoginFrame() 
	{
		initDialog ();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo
	 * 
	 */
	private final void initDialog ()
	{
		this.loginFrame	= this;
		
		config						= new RAConfig ();
		rAManager 					= new RAManager (config, debug);
		language 					= new Language (rAManager.getRALogging(), config.getLanguageName());
		valueChecks 				= new ValueChecks (this, language);

		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		if (!config.fileConfigurationExists ())
		{
			newConfiguration ();
		}
		
		if (config.getDataBaseEngine().equals (RAConfig.ENGINE_DERBY_STRING) && !config.derbyDataBaseExists ())
		{
			try {
				rAManager.saveDemo();
			} catch (RAManagerException e) {
				e.printStackTrace();
			}
		}
		
		if (tryConnection (rAManager, config, language, valueChecks))
		{
			initComponents ();
		}
		else
		{
			dispose ();
		}
		
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa los controles del formulario
	 */
	protected void initComponents ()
	{
		setTitle (language.get("login"));
		
		setBounds(100, 100, 550, 135);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelLogin = new JPanel();
		contentPane.add(panelLogin, BorderLayout.CENTER);
		panelLogin.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblLogin = new JLabel(language.get ("columnEmployee"));
		panelLogin.add(lblLogin);

		rAComboBoxLogin	= new RAComboBox <>();
		
		try 
		{
			rAComboBoxLogin.loadItems(rAManager.getAllBEmployees (""));
		} 
		catch (bll.RAManagerException exception) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, exception.getMessage());
			ValueChecks.showExceptionMessage(this, language, exception);
		}

		panelLogin.add(rAComboBoxLogin);
		
		JLabel lblPassword = new JLabel(language.get ("lblPassword"));
		panelLogin.add(lblPassword);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("Password1");
		panelLogin.add(pwdPassword);
		
		JPanel panelActions = new JPanel();
		contentPane.add(panelActions, BorderLayout.SOUTH);
		panelActions.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnCancel = new JButton(language.get ("cancelButton"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginFrame.dispose ();
			}
		});
		panelActions.add(btnCancel);
		
		JButton btnOk = new JButton(language.get ("okButton"));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkLogin ();
			}
		});
		panelActions.add(btnOk);
		
		setDefaultButton (btnOk);
		
		setVisible(true);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Intenta realizar una conexion con la base de datos
	 * 
	 * @param rAManager		- Gestor de datos de Rekord Autoak
	 * @param config		- Configuracion general de la aplicacion
	 * @param language		- Objeto de idioma
	 * @param valueChecks	- Objeto de verificaciones
	 * 
	 * @return Cierto si ha podido conectarse, falso en caso contrario
	 */
	protected boolean tryConnection (RAManager rAManager, RAConfig config, Language language, ValueChecks valueChecks)
	{
		boolean retryConnection 	= false;
		boolean newCredentials		= false;
		boolean connectionOk		= false;
		
		do
		{
			int errorCode = valueChecks.verifyConnection(this, rAManager, config.getSqlServer(), config.getSqlUser());
			
			switch (errorCode)
			{
				case RAManagerException.NO_ERROR:
					if (newCredentials)
					{
						try {
							config.saveToDisk();
						} catch (IOException iOException) {
							showErrorDialog(iOException.getMessage());
							iOException.printStackTrace();
						}
					}
					retryConnection 	= false;
					connectionOk 		= true;
					break;

				case RAManagerException.MYSQL_ERROR_CODE_DATABASE_DONT_EXISTS:
					createNewDatabase();
					retryConnection 	= false;
					connectionOk		= true;
					break;

				case RAManagerException.MYSQL_ERROR_CODE_SERVICE_NOT_AVAILABLE:
				case RAManagerException.MYSQL_ERROR_CODE_LOCKED_ROOT:
				case RAManagerException.MYSQL_ERROR_CODE_BAD_LOGIN:
					CredentialsDBDialog credentialsDBDialog = new CredentialsDBDialog (loginFrame, language, language.get ("inputDBCredentials"), config.getSqlServer(), config.getSqlUser(), config.getSqlPassword());
					if (credentialsDBDialog.isAccept ())
					{
						config.setSqlServer(credentialsDBDialog.getServer ());
						config.setSqlUser(credentialsDBDialog.getUser());
						config.setSqlPassword(credentialsDBDialog.getPassword());
						rAManager.reloadSqlCredentials (config.getSqlServer (), config.getSqlUser(), config.getSqlPassword());
						retryConnection = true;
						newCredentials = true;
					}
					else
					{
						retryConnection = false;
						rAManager.close ();
					}
					break;
					
					
				default:
					retryConnection = false;
					rAManager.close ();
			}
			
		} while (retryConnection);
		
		return connectionOk;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Crea una base de datos nueva
	 */
	private void newConfiguration ()
	{
		String[] values = {RAConfig.ENGINE_DERBY_STRING, RAConfig.ENGINE_MARIADB_STRING};

		Object selected = JOptionPane.showInputDialog (
				this, 
				language.get ("newConfiguration"), 
				language.get ("configurationText"), 
				JOptionPane.ERROR_MESSAGE, null, values, values[0]);
		
		if (selected != null)
		{
			try
			{
				if (selected == values[0])
				{
					config.setServerAsDefaultDirectory ();
					rAManager.setSelectedDriver(RAManager.DB_DRIVER_DERBY);
					rAManager.reloadSqlCredentials(config.getSqlServer(), config.getSqlUser(), config.getSqlPassword());
					rAManager.saveDemo();
				}
				else
				{
					rAManager.setSelectedDriver(RAManager.DB_DRIVER_MARIADB);
				}

				config.setDataBaseEngine((String)selected);
				
				try {
					config.saveToDisk();
				} catch (IOException ioException) {
					throw new RAManagerException (ioException.getMessage());
				}

			}
			catch (RAManagerException rAManagerException)
			{
				rAManagerException.printStackTrace();
				showExceptionDialog (this, rAManagerException, language);
			}
		}
		else
		{
			rAManager.close ();
		    System.exit (ERROR_LOAD_DATABASE);
		}
		
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Crea una base de datos nueva
	 */
	private void createNewDatabase ()
	{
		String[] values = {language.get ("minimalValueInit"), language.get ("saveDataDemo")};

		Object selected = JOptionPane.showInputDialog (
				this, 
				language.get ("loadErrorMariaDB"), 
				language.get ("loadErrorText"), 
				JOptionPane.ERROR_MESSAGE, null, values, values[0]);
		
		if (selected != null)
		{
			try
			{
				if (selected == values[0])
				{
					rAManager.saveDataMinimalDefaults();
				}
				else
				{
					rAManager.saveDemo();
				}
				
			}
			catch (RAManagerException rAManagerException)
			{
				rAManagerException.printStackTrace();
				showExceptionDialog (this, rAManagerException, language);
			}
		}
		else
		{
			rAManager.close ();
		    System.exit (ERROR_LOAD_DATABASE);
		}
		
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprueba los datos del login y si son correctos inicia el mainFrame, si NO lo son muestra mensaje de error 
	 */
	private void checkLogin ()
	{
		BEmployee login = (BEmployee)rAComboBoxLogin.getSelectedItem();
		if (login.verifyPassword (new String (pwdPassword.getPassword())))
		{
			rAManager.setActiveLogin (login);
			
			if (login.isPermissionManagement())
			{
				new MainFrame (rAManager, config, language);
			}
			else
			{
				new OrderByLoginForm (this, rAManager, login, config, language);
			}
			
			dispose ();
		}
		else
		{
			showErrorDialog (language.get ("loginFrameBadLogin"));
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Muestra el dialogo de excepciones de la aplicacion
	 * 
	 * @param frame			- Formulario padre
	 * @param exception		- Objeto de excepcion
	 * @param language		- Objeto de idioma
	 */
	public void showExceptionDialog (JFrame frame, RAManagerException exception, Language language)
	{
		new ExceptionDialog (frame, language, exception);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Muestra un dialogo de error generico
	 * 
	 * @param message		- Mensaje de error a mostrar
	 */
	public void showErrorDialog (String message)
	{
		JOptionPane.showMessageDialog(loginFrame, message, language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Definir boton por defecto para un boton en concreto
	 * @param defaultButton
	 */
	private void setDefaultButton (JButton defaultButton)
	{
		JRootPane rootPane = SwingUtilities.getRootPane(defaultButton); 
		rootPane.setDefaultButton(defaultButton);
	}
	

}
