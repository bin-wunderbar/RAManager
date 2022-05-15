package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
<<<<<<< HEAD
=======
import javax.swing.JComboBox;
>>>>>>> V3.1-alertas
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
<<<<<<< HEAD
=======
import javax.swing.JPasswordField;
>>>>>>> V3.1-alertas
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import bll.BRole;
import bll.RAConfig;
import bll.RALogging;
import bll.RAManager;
import gui.Language;
import gui.ValueChecks;
import gui.controls.RAComboBox;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo de configuración de opciones de la aplicación
 * 
 * @author G1
 *
 */
<<<<<<< HEAD
public class ConfigurationDialog extends JDialog {

=======
@SuppressWarnings("serial")
public class ConfigurationDialog extends JDialog {


>>>>>>> V3.1-alertas
	private JTabbedPane tabbedPanel;
	
	private RAComboBox<String> rAComboBoxLanguage;
	private RAManager rAManager;
	private RAConfig config;
	private Language language;

	private JButton buttonOk;
	private JButton buttonCancel;
	private JButton buttonDefaults;
	private JTextField textFieldIva;
	private JTextField textFieldCurrency;
	private JTextField textFieldPrintFontSize;
	private JTextField textFieldCompanyName;
	private JTextField textFieldCompanyDirection;
	private JTextField textFieldCompanyProvince;
	private JTextField textFieldCompanyMail;
	private JTextField textFieldCompanyPhone;
	private JTextField textFieldCompanyNIF;
<<<<<<< HEAD
	private JTextField textFieldDatabaseConnection;
=======
	private JTextField textFieldSqlServer;
>>>>>>> V3.1-alertas
	private JTextField textFieldLoggingFileName;

	private JPanel contentPanel;

	private ValueChecks valueChecks;

	private JTextField[] textFields;
	private boolean textFieldsChanged;
<<<<<<< HEAD




	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo de configuración de opciones
	 * 
	 * @param mainFrame		Formulario principal de la aplicación
	 * @param language		Objeto con el lenguaje a aplicar
=======
	
	private JComboBox <String> comboBoxDataBaseEngine;
	private JTextField textFieldSqlUser;
	private JPasswordField passwordFieldSqlPassword;


	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo de configuracion de opciones
	 * 
	 * @param owner 		- Ventana propietaria
	 * @param rAManager		- Gestor de datos de la capa de negocio
	 * @param config		- Objeto de datos de configuracion
	 * @param language		- Objeto de idioma
>>>>>>> V3.1-alertas
	 */
	public ConfigurationDialog (JFrame owner, RAManager rAManager, RAConfig config, Language language) 
	{
		super (owner, true);
		initDialog (owner, rAManager, config, language);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA, CONFIGURACIÓN DEL ESTADO MODAL
<<<<<<< HEAD
=======
	/**
	 * Inicializa el dialogo de configuracion de opciones
	 * 
	 * @param owner 		- Ventana propietaria
	 * @param rAManager		- Gestor de datos de la capa de negocio
	 * @param config		- Objeto de datos de configuracion
	 * @param language		- Objeto de idioma
	 * @param modal			- Indica si el dialogo es modal o no
	 */
>>>>>>> V3.1-alertas
	public ConfigurationDialog (JFrame owner, RAManager rAManager, RAConfig config, Language language, boolean modal) 
	{
		super (owner, modal);
		initDialog (owner, rAManager, config, language);
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa el dialogo de configuracion de opciones
	 * 
	 * @param owner 		- Ventana propietaria
	 * @param rAManager		- Gestor de datos de la capa de negocio
	 * @param config		- Objeto de datos de configuracion
	 * @param language		- Objeto de idioma
	 */
>>>>>>> V3.1-alertas
	private final void initDialog (JFrame owner, RAManager rAManager, RAConfig config, Language language)
	{
		this.language		= language;
		this.config 		= config;
		this.rAManager  	= rAManager;
		
		valueChecks = new ValueChecks (owner, language);
		
		setTitle (language.get("configurationText"));
		Rectangle bounds = owner.getBounds ();
		setBounds(bounds.x + 50, bounds.y + 50, 650, 250);
		
		setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
		
		addWindowListener (new WindowAdapter () {
			@Override
			public void windowClosing (WindowEvent windowEvent)
			{
				verifyWindowClosing ();
			}
		}); 
		
		getContentPane ().setLayout (new BorderLayout());
		contentPanel = new JPanel ();
		contentPanel.setLayout (new BorderLayout ());
		contentPanel.setBorder (new EmptyBorder (5, 5, 5, 5));
		getContentPane ().add (contentPanel, BorderLayout.CENTER); 
		
		tabbedPanel = new JTabbedPane ();

		contentPanel.add(tabbedPanel, BorderLayout.CENTER);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			{
				buttonDefaults = new JButton (language.get ("defaultsButton"));
				buttonDefaults.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent actionEvent)
					{
						restoreDefaults ();
					}
				});
				buttonPane.add (buttonDefaults);
			}
			
			{
				buttonCancel = new JButton(language.get ("cancelButton"));
				buttonCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						verifyWindowClosing ();
					}
				});
				buttonCancel.setActionCommand("Cancel");
				buttonPane.add(buttonCancel);
			}
			{
				buttonOk = new JButton(language.get ("okButton"));
				buttonOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveDataConfig ();
					}
				});
				buttonOk.setActionCommand("OK");
				buttonPane.add(buttonOk);
				getRootPane().setDefaultButton(buttonOk);
			}

		}
		
		initComponents ();
		setPermissionsByRole ();
		load ();

		textFieldsChanged = false;

		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Verifica si se debe guardar al cerrar el dialogo
	 * 
	 */
>>>>>>> V3.1-alertas
	private void verifyWindowClosing ()
	{
		if (textFieldsChanged)
		{
			switch (JOptionPane.showConfirmDialog(this, language.get ("saveChanges"), language.get ("save"), JOptionPane.YES_NO_CANCEL_OPTION))
			{
				case JOptionPane.YES_OPTION: 
					saveDataConfig();
					break;
					
				case JOptionPane.NO_OPTION:
					dispose ();
					break;
			}
		}
		else
		{
			dispose ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
<<<<<<< HEAD
	 * Inicializa los componentes del diálogo
=======
	 * Inicializa los componentes del dialogo
>>>>>>> V3.1-alertas
	 */
	protected void initComponents ()
	{
		JPanel panelCompany 		= new JPanel ();
		JPanel panelMiscellany		= new JPanel ();
<<<<<<< HEAD
		
		panelCompany.setLayout (new GridLayout (0, 2));
		panelMiscellany.setLayout (new GridLayout (0, 2));
		
		tabbedPanel.addTab (language.get ("miscellanyText"), panelMiscellany);
		tabbedPanel.addTab (language.get ("companyLblTitle"), panelCompany);
=======
		JPanel panelConnection		= new JPanel ();
		
		panelCompany.setLayout (new GridLayout (0, 2));
		panelMiscellany.setLayout (new GridLayout (0, 2));
		panelConnection.setLayout (new GridLayout (0, 2));
		
		tabbedPanel.addTab (language.get ("connectionText"), panelConnection);
		tabbedPanel.addTab (language.get ("companyLblTitle"), panelCompany);
		tabbedPanel.addTab (language.get ("miscellanyText"), panelMiscellany);
>>>>>>> V3.1-alertas
		
		rAComboBoxLanguage = new RAComboBox <> ();
		
		textFieldIva 				= new JTextField ();	textFieldIva.setHorizontalAlignment(JTextField.RIGHT);
		textFieldCurrency			= new JTextField ();	textFieldCurrency.setHorizontalAlignment(JTextField.RIGHT);
		textFieldPrintFontSize 		= new JTextField ();	textFieldPrintFontSize.setHorizontalAlignment(JTextField.RIGHT);
<<<<<<< HEAD
		textFieldDatabaseConnection = new JTextField ();
=======

>>>>>>> V3.1-alertas
		textFieldLoggingFileName 	= new JTextField ();
		textFieldCompanyName 		= new JTextField ();
		textFieldCompanyDirection 	= new JTextField ();
		textFieldCompanyProvince 	= new JTextField ();
		textFieldCompanyMail 		= new JTextField ();
		textFieldCompanyPhone 		= new JTextField ();
		textFieldCompanyNIF 		= new JTextField ();

<<<<<<< HEAD
=======
		textFieldSqlServer 			= new JTextField ();
		textFieldSqlUser			= new JTextField ();
		passwordFieldSqlPassword	= new JPasswordField ();
		comboBoxDataBaseEngine 		= new JComboBox <> ();

>>>>>>> V3.1-alertas
		textFields = new JTextField[] {
				textFieldIva, 
				textFieldCurrency, 
				textFieldPrintFontSize, 
<<<<<<< HEAD
				textFieldDatabaseConnection,
=======
>>>>>>> V3.1-alertas
				textFieldLoggingFileName,
				textFieldCompanyName,
				textFieldCompanyDirection,
				textFieldCompanyProvince,
				textFieldCompanyMail,
				textFieldCompanyPhone,
				textFieldCompanyNIF,
<<<<<<< HEAD
=======
				textFieldSqlServer,
				textFieldSqlUser
>>>>>>> V3.1-alertas
		}; 
		
		panelMiscellany.add (new JLabel (language.get ("languageText")));
		panelMiscellany.add (rAComboBoxLanguage);
		panelMiscellany.add (new JLabel (language.get("ivaText")));
		panelMiscellany.add (textFieldIva);
		panelMiscellany.add (new JLabel (language.get("currencyText")));
		panelMiscellany.add (textFieldCurrency);
		panelMiscellany.add (new JLabel (language.get("printFontSize")));
		panelMiscellany.add (textFieldPrintFontSize);
<<<<<<< HEAD
		panelMiscellany.add (new JLabel (language.get("databaseConnection")));
		panelMiscellany.add (textFieldDatabaseConnection);
		panelMiscellany.add (new JLabel (language.get("loggingFileName")));
		panelMiscellany.add (textFieldLoggingFileName);
=======
		panelMiscellany.add (new JLabel (language.get("loggingFileName")));
		panelMiscellany.add (textFieldLoggingFileName);
		addPanelCleanRow (panelMiscellany);
>>>>>>> V3.1-alertas
		
		panelCompany.add (new JLabel (language.get("companyLblName")));
		panelCompany.add (textFieldCompanyName);
		panelCompany.add (new JLabel (language.get("columnDirection")));
		panelCompany.add (textFieldCompanyDirection);
		panelCompany.add (new JLabel (language.get("companyLblProvince")));
		panelCompany.add (textFieldCompanyProvince);
		panelCompany.add (new JLabel (language.get("companyLblMail")));
		panelCompany.add (textFieldCompanyMail);
		panelCompany.add (new JLabel (language.get("companyLblPhone")));
		panelCompany.add (textFieldCompanyPhone);
		panelCompany.add (new JLabel (language.get("companyLblNIF")));
		panelCompany.add (textFieldCompanyNIF);
<<<<<<< HEAD

		
=======
		addPanelCleanRow (panelCompany);

		panelConnection.add (new JLabel (language.get ("preferredEngine")));
		panelConnection.add (comboBoxDataBaseEngine);
		panelConnection.add (new JLabel (language.get("sqlServer")));
		panelConnection.add (textFieldSqlServer);
		panelConnection.add (new JLabel (language.get("sqlUser")));
		panelConnection.add (textFieldSqlUser);
		panelConnection.add (new JLabel (language.get("sqlPassword")));
		panelConnection.add (passwordFieldSqlPassword);
		addPanelCleanRow (panelConnection);
		addPanelCleanRow (panelConnection);

>>>>>>> V3.1-alertas
		for (JTextField textField : textFields)
		{
			textField.getDocument().addDocumentListener (new TextFieldChanged ());
		}
		
<<<<<<< HEAD
	}
	
	// --------------------------------------------------------------------------------------------
=======
		passwordFieldSqlPassword.getDocument().addDocumentListener(new TextFieldChanged ());
		
		
	}
	
	// --------------------------------------------------------------------------------------------
	private void addPanelCleanRow (JPanel panel)
	{
		panel.add (new JLabel ()); panel.add (new JLabel ());
	}
	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta los permisos segun el rol
	 */
>>>>>>> V3.1-alertas
	private void setPermissionsByRole ()
	{
		int idLogin = rAManager.getActiveLogin().getRole().getId(); 
		if (!(idLogin == BRole.MANAGEMENT || idLogin == BRole.DIRECTION))
		{
			for (JTextField textField : textFields)
			{
				textField.setEnabled (false);
			}
			
			buttonDefaults.setEnabled (false);
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Clase para detectar los cambios en los campos de texto
	 * 
	 * @author G4
	 *
	 */
	class TextFieldChanged implements DocumentListener
	{
		public void changedUpdate(DocumentEvent e) 
		{
			textFieldsChanged = true;
		}

		public void removeUpdate(DocumentEvent e) 
		{
			textFieldsChanged = true;
		}

		public void insertUpdate(DocumentEvent e) 
		{
			textFieldsChanged = true;
		}
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Carga los datos del dialogo
	 * 
	 */
>>>>>>> V3.1-alertas
	private void load ()
	{
		rAComboBoxLanguage.loadItems (new ArrayList <String> (Arrays.asList (language.getDomainNames())));
		rAComboBoxLanguage.selectBy (config.getLanguageName());
		rAComboBoxLanguage.setEditable(false);
<<<<<<< HEAD
=======

		textFieldSqlServer.setText(config.getSqlServer());
		textFieldSqlUser.setText (config.getSqlUser());
		passwordFieldSqlPassword.setText(config.getSqlPassword());
>>>>>>> V3.1-alertas
		
		textFieldIva.setText("" + config.getIva());
		textFieldCurrency.setText(config.getCurrency());
		textFieldPrintFontSize.setText ("" + config.getPrintFontSize());
<<<<<<< HEAD
		textFieldDatabaseConnection.setText(config.getDatabaseConnection());
=======
>>>>>>> V3.1-alertas
		textFieldLoggingFileName.setText(config.getLoggingFileName());
		
		textFieldCompanyName.setText(config.getCompanyName());
		textFieldCompanyDirection.setText(config.getCompanyDirection());
		textFieldCompanyProvince.setText(config.getCompanyProvince());
		textFieldCompanyMail.setText(config.getCompanyMail ());
		textFieldCompanyPhone.setText(config.getCompanyPhone());
		textFieldCompanyNIF.setText(config.getCompanyNIF());
<<<<<<< HEAD
	}

	// --------------------------------------------------------------------------------------------
=======
		
		comboBoxDataBaseEngine.addItem(RAConfig.ENGINE_MARIADB_STRING);
		comboBoxDataBaseEngine.addItem(RAConfig.ENGINE_DERBY_STRING);
		

		comboBoxDataBaseEngineSelectedItem(config.getDataBaseEngine());
		comboBoxDataBaseEngine.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				setDataBaseEngineDefaultValues ();
			}
		});
	}
	
	
	
	// --------------------------------------------------------------------------------------------
	protected void setDataBaseEngineDefaultValues() 
	{
		switch ((String)comboBoxDataBaseEngine.getSelectedItem())
		{
			default:
			case RAConfig.ENGINE_MARIADB_STRING:
				textFieldSqlServer.setText (config.getServerAsDefaultHost ());
				break;
			case RAConfig.ENGINE_DERBY_STRING:
				textFieldSqlServer.setText (config.getServerAsDefaultDirectory ());
				break;
		}
		
		
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Selecciona un elemento del combobox segun el identificador del motor de base de datos
	 * @param dataBaseEngine		- Identificador del motor de base de datos
	 */
	private void comboBoxDataBaseEngineSelectedItem (String dataBaseEngine)
	{
		for (int i = 0, count =  comboBoxDataBaseEngine.getItemCount(); i < count; i++)
		{
			if (((String)comboBoxDataBaseEngine.getItemAt(i)).equalsIgnoreCase(dataBaseEngine))
			{
				comboBoxDataBaseEngine.setSelectedIndex(i);
				return;
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda los datos del dialogo
	 * 
	 */
>>>>>>> V3.1-alertas
	private void saveDataConfig ()
	{
		if (verifyFields ())
		{
			String selectedLanguage = (String)rAComboBoxLanguage.getSelectedItem(); 
			language.loadTranslation (selectedLanguage);

<<<<<<< HEAD

=======
			config.setDataBaseEngine((String)comboBoxDataBaseEngine.getSelectedItem());
			config.setSqlServer (textFieldSqlServer.getText());
			config.setSqlUser (textFieldSqlUser.getText());
			config.setSqlPassword (new String (passwordFieldSqlPassword.getPassword()));
>>>>>>> V3.1-alertas
			config.setLanguageName (selectedLanguage);
			config.setIva(Double.parseDouble(textFieldIva.getText()));
			config.setCurrency(textFieldCurrency.getText());
			config.setPrintFontSize (Integer.parseInt (textFieldPrintFontSize.getText()));
<<<<<<< HEAD
			config.setDatabaseConnection(textFieldDatabaseConnection.getText());
=======
>>>>>>> V3.1-alertas
			config.setLoggingFileName(textFieldLoggingFileName.getText());
			
			config.setCompanyName(textFieldCompanyName.getText ());
			config.setCompanyDirection(textFieldCompanyDirection.getText ());
			config.setCompanyProvince (textFieldCompanyProvince.getText());
			config.setCompanyMail (textFieldCompanyMail.getText());
			config.setCompanyPhone (textFieldCompanyPhone.getText());
			config.setCompanyNIF (textFieldCompanyNIF.getText());
			
			try 
			{
				config.saveToDisk();
			} 
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog (this, language.get("saveDataError"), language.get("errorText"), JOptionPane.ERROR_MESSAGE);
<<<<<<< HEAD
				rAManager.logPrintln (RALogging.LEVEL_ERROR, "ConfigurationDialog::saveDataConfig () - Save data config error [" + RAConfig.configFile + "]");
=======
				rAManager.logPrintln (RALogging.LEVEL_ERROR, "ConfigurationDialog::saveDataConfig () - Save data config error [" + RAConfig.CONFIG_FILE + "]");
>>>>>>> V3.1-alertas
				e.printStackTrace();
			}

			dispose ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
	private boolean verifyFields ()
	{
		JTextField[] normalTextFields = new JTextField[] {
				textFieldDatabaseConnection,
=======
	/**
	 * Verifica que los campos contengan datos correctos
	 * 
	 * @return Cierto si todas las comprobaciones son correctas 
	 */
	private boolean verifyFields ()
	{
		JTextField[] normalTextFields = new JTextField[] {
				textFieldSqlServer,
				textFieldSqlUser,
>>>>>>> V3.1-alertas
				textFieldLoggingFileName,
				textFieldCompanyName,
				textFieldCompanyDirection,
				textFieldCompanyProvince,
				textFieldCompanyMail,
				textFieldCompanyPhone,
				textFieldCompanyNIF
			};
<<<<<<< HEAD
		if (valueChecks.isValidIVA (textFieldIva))
		{
			if (valueChecks.isCharacterValue(textFieldCurrency))
=======
		
		String[] normalLabels = new String[] {
				language.get("sqlServer"),
				language.get("sqlUser"),
				language.get("loggingFileName"),
				language.get("companyLblName"),
				language.get("columnDirection"),
				language.get("companyLblProvince"),
				language.get("companyLblMail"),
				language.get("companyLblPhone"),
				language.get("companyLblNIF")
		}; 
		
		if (valueChecks.isValidIVA (textFieldIva, language.get("ivaText")))
		{
			if (valueChecks.isCharacterValue(textFieldCurrency, language.get("currencyText")))
>>>>>>> V3.1-alertas
			{
				if (valueChecks.rangeOfPrintFontSize (textFieldPrintFontSize))
				{
					boolean normalTextFieldsOk = true;
<<<<<<< HEAD
					for (JTextField normalTextField : normalTextFields)
					{
						if (!valueChecks.isValidTextField(normalTextField))
=======
					
					for (int i = 0; i < normalTextFields.length; i++)
					{
						if (!valueChecks.isValidTextField(normalTextFields[i], normalLabels[i]))
>>>>>>> V3.1-alertas
						{
							normalTextFieldsOk = false;
							break;
						}
					}
					
					if (normalTextFieldsOk)
					{
<<<<<<< HEAD
						return true;
=======
						if (valueChecks.isValidPassword(passwordFieldSqlPassword, language.get("sqlPassword")))
						{
							return true;
						}
>>>>>>> V3.1-alertas
					}
				}
			}
		}
		
		return false;
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Restablece los datos mostrados por el dialogo segun los parametros de fabrica
	 * 
	 */
>>>>>>> V3.1-alertas
	private void restoreDefaults ()
	{
		if (JOptionPane.showConfirmDialog(this, language.get("loadDefaultsConfirm"), language.get("load"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			config.loadDefaults();
			load ();
		}
	}

}
