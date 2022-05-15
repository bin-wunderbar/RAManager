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
import gui.Language;
import gui.controls.RAComboBox;
import gui.forms.mainframe.MainFrame;
import gui.forms.orderbyloginform.OrderByLoginForm;
import test.RunTest;
import test.UnitTestDialog;

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
public class LoginFrame extends JFrame 
{
	private JPanel contentPane;
	private RAComboBox <BEmployee> rAComboBoxLogin;
	private JPasswordField pwdPassword;
	private LoginFrame loginFrame;
	
	private Language language;
	private RAConfig config;
	private RAManager rAManager;
	private static boolean simulation;
	
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
			"	/simulation		Launch app without write and delete operations\n" +
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
	 *   <strong>/simulation</strong>	- Lanza la aplicación sin operaciones de escritura y eliminación.<br>
	 *   <strong>/savedatademo</strong>	- Crea datos de configuración y un ejemplo de base de datos. <br>
	 */
	public static void main(String[] args) {
		
		simulation = false;
		
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
					
				case "/simulation":
					simulation = true;
					launchApp ();
					break;
					
				case "/savedatademo":
					Scanner console = new Scanner (System.in);
					System.out.println ("Warning! All application data will be deleted.");
					System.out.println ("Are you sure? [N,s]");
					String decision = console.next ();
					
					if (decision.length () == 1 && decision.toLowerCase().charAt(0) == 's')
					{
						saveDataDemo ();
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
	private static void saveDataDemo ()
	{
		RAConfig config			= new RAConfig ();
		RAManager rAManager 	= new RAManager (config.getDatabaseConnection ());
		
		try {
			config.saveToDisk();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		rAManager.saveDataDemo();
	}
	
	// --------------------------------------------------------------------------------------------
	private static void launchApp ()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
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
		initDialog (true);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA 
	public LoginFrame (boolean fillPasswordDefault)
	{
		initDialog (fillPasswordDefault);
	}
	
	// --------------------------------------------------------------------------------------------
	private final void initDialog (boolean fillPasswordDefault)
	{
		this.loginFrame	= this;
		
		config		= new RAConfig ();
		rAManager 	= new RAManager (config, simulation);
		language 	= new Language (rAManager.getRALogging(), config.getLanguageName());
		
		dataConnect ();
		
		setTitle ("Login");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 121);
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
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}

		panelLogin.add(rAComboBoxLogin);
		
		JLabel lblPassword = new JLabel(language.get ("lblPassword"));
		panelLogin.add(lblPassword);
		
		pwdPassword = new JPasswordField();
		if (fillPasswordDefault) pwdPassword.setText("Password1");
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
	}
	
	// --------------------------------------------------------------------------------------------
	private void dataConnect ()
	{
		try {
			rAManager.dataConnect ();
		} catch (RAManagerException exception) {
			
			String[] values = {language.get ("minimalValueInit"), language.get ("saveDataDemo")};

			Object selected = JOptionPane.showInputDialog (
					this, 
					"No se puede cargar la base de datos", 
					language.get ("loadErrorText"), 
					JOptionPane.ERROR_MESSAGE, null, values, values[0]);
			
			if (selected != null)
			{
				if (JOptionPane.showConfirmDialog (this, language.get ("warningDeleteDataBase"), language.get("overwrite"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				{
					if (selected == values[0]) 
					{
						rAManager.setMinimalDataDefaults();
					}
					else 
					{
						rAManager.saveDataDemo();
					}
				}
				else
				{
					rAManager.close ();
					System.exit (ERROR_LOAD_DATABASE);
				}
			}
			else
			{
				rAManager.close ();
			    System.exit (ERROR_LOAD_DATABASE);
			}
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
			JOptionPane.showMessageDialog(loginFrame, language.get ("loginFrameBadLogin"), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
		}
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
