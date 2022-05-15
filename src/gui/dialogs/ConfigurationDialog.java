package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
public class ConfigurationDialog extends JDialog {

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
	private JTextField textFieldDatabaseConnection;
	private JTextField textFieldLoggingFileName;

	private JPanel contentPanel;

	private ValueChecks valueChecks;

	private JTextField[] textFields;
	private boolean textFieldsChanged;




	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo de configuración de opciones
	 * 
	 * @param mainFrame		Formulario principal de la aplicación
	 * @param language		Objeto con el lenguaje a aplicar
	 */
	public ConfigurationDialog (JFrame owner, RAManager rAManager, RAConfig config, Language language) 
	{
		super (owner, true);
		initDialog (owner, rAManager, config, language);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA, CONFIGURACIÓN DEL ESTADO MODAL
	public ConfigurationDialog (JFrame owner, RAManager rAManager, RAConfig config, Language language, boolean modal) 
	{
		super (owner, modal);
		initDialog (owner, rAManager, config, language);
	}
	
	// --------------------------------------------------------------------------------------------
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
	 * Inicializa los componentes del diálogo
	 */
	protected void initComponents ()
	{
		JPanel panelCompany 		= new JPanel ();
		JPanel panelMiscellany		= new JPanel ();
		
		panelCompany.setLayout (new GridLayout (0, 2));
		panelMiscellany.setLayout (new GridLayout (0, 2));
		
		tabbedPanel.addTab (language.get ("miscellanyText"), panelMiscellany);
		tabbedPanel.addTab (language.get ("companyLblTitle"), panelCompany);
		
		rAComboBoxLanguage = new RAComboBox <> ();
		
		textFieldIva 				= new JTextField ();	textFieldIva.setHorizontalAlignment(JTextField.RIGHT);
		textFieldCurrency			= new JTextField ();	textFieldCurrency.setHorizontalAlignment(JTextField.RIGHT);
		textFieldPrintFontSize 		= new JTextField ();	textFieldPrintFontSize.setHorizontalAlignment(JTextField.RIGHT);
		textFieldDatabaseConnection = new JTextField ();
		textFieldLoggingFileName 	= new JTextField ();
		textFieldCompanyName 		= new JTextField ();
		textFieldCompanyDirection 	= new JTextField ();
		textFieldCompanyProvince 	= new JTextField ();
		textFieldCompanyMail 		= new JTextField ();
		textFieldCompanyPhone 		= new JTextField ();
		textFieldCompanyNIF 		= new JTextField ();

		textFields = new JTextField[] {
				textFieldIva, 
				textFieldCurrency, 
				textFieldPrintFontSize, 
				textFieldDatabaseConnection,
				textFieldLoggingFileName,
				textFieldCompanyName,
				textFieldCompanyDirection,
				textFieldCompanyProvince,
				textFieldCompanyMail,
				textFieldCompanyPhone,
				textFieldCompanyNIF,
		}; 
		
		panelMiscellany.add (new JLabel (language.get ("languageText")));
		panelMiscellany.add (rAComboBoxLanguage);
		panelMiscellany.add (new JLabel (language.get("ivaText")));
		panelMiscellany.add (textFieldIva);
		panelMiscellany.add (new JLabel (language.get("currencyText")));
		panelMiscellany.add (textFieldCurrency);
		panelMiscellany.add (new JLabel (language.get("printFontSize")));
		panelMiscellany.add (textFieldPrintFontSize);
		panelMiscellany.add (new JLabel (language.get("databaseConnection")));
		panelMiscellany.add (textFieldDatabaseConnection);
		panelMiscellany.add (new JLabel (language.get("loggingFileName")));
		panelMiscellany.add (textFieldLoggingFileName);
		
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

		
		for (JTextField textField : textFields)
		{
			textField.getDocument().addDocumentListener (new TextFieldChanged ());
		}
		
	}
	
	// --------------------------------------------------------------------------------------------
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
	private void load ()
	{
		rAComboBoxLanguage.loadItems (new ArrayList <String> (Arrays.asList (language.getDomainNames())));
		rAComboBoxLanguage.selectBy (config.getLanguageName());
		rAComboBoxLanguage.setEditable(false);
		
		textFieldIva.setText("" + config.getIva());
		textFieldCurrency.setText(config.getCurrency());
		textFieldPrintFontSize.setText ("" + config.getPrintFontSize());
		textFieldDatabaseConnection.setText(config.getDatabaseConnection());
		textFieldLoggingFileName.setText(config.getLoggingFileName());
		
		textFieldCompanyName.setText(config.getCompanyName());
		textFieldCompanyDirection.setText(config.getCompanyDirection());
		textFieldCompanyProvince.setText(config.getCompanyProvince());
		textFieldCompanyMail.setText(config.getCompanyMail ());
		textFieldCompanyPhone.setText(config.getCompanyPhone());
		textFieldCompanyNIF.setText(config.getCompanyNIF());
	}

	// --------------------------------------------------------------------------------------------
	private void saveDataConfig ()
	{
		if (verifyFields ())
		{
			String selectedLanguage = (String)rAComboBoxLanguage.getSelectedItem(); 
			language.loadTranslation (selectedLanguage);


			config.setLanguageName (selectedLanguage);
			config.setIva(Double.parseDouble(textFieldIva.getText()));
			config.setCurrency(textFieldCurrency.getText());
			config.setPrintFontSize (Integer.parseInt (textFieldPrintFontSize.getText()));
			config.setDatabaseConnection(textFieldDatabaseConnection.getText());
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
				rAManager.logPrintln (RALogging.LEVEL_ERROR, "ConfigurationDialog::saveDataConfig () - Save data config error [" + RAConfig.configFile + "]");
				e.printStackTrace();
			}

			dispose ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	private boolean verifyFields ()
	{
		JTextField[] normalTextFields = new JTextField[] {
				textFieldDatabaseConnection,
				textFieldLoggingFileName,
				textFieldCompanyName,
				textFieldCompanyDirection,
				textFieldCompanyProvince,
				textFieldCompanyMail,
				textFieldCompanyPhone,
				textFieldCompanyNIF
			};
		if (valueChecks.isValidIVA (textFieldIva))
		{
			if (valueChecks.isCharacterValue(textFieldCurrency))
			{
				if (valueChecks.rangeOfPrintFontSize (textFieldPrintFontSize))
				{
					boolean normalTextFieldsOk = true;
					for (JTextField normalTextField : normalTextFields)
					{
						if (!valueChecks.isValidTextField(normalTextField))
						{
							normalTextFieldsOk = false;
							break;
						}
					}
					
					if (normalTextFieldsOk)
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}

	// --------------------------------------------------------------------------------------------
	private void restoreDefaults ()
	{
		if (JOptionPane.showConfirmDialog(this, language.get("loadDefaultsConfirm"), language.get("load"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			config.loadDefaults();
			load ();
		}
	}

}
