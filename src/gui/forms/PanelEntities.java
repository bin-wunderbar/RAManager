package gui.forms;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import bll.BClient;
import bll.RAConfig;
import bll.Util;
import gui.Language;

// ------------------------------------------------------------------------------------------------
/***
 * Panel con las entidades de empresa y cliente
 * 
 * @author G1
 *
 */
<<<<<<< HEAD
=======
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class PanelEntities extends JPanel 
{
	private Language language;
	private RAConfig config;

	private JPanel panelCompany;
	private JPanel panelClient;
	private String companyName;
	private String companyDirection;
	private String companyProvince; 
	private String companyMail; 
	private String companyPhone;
	private String companyNIF;
	private JTextField textFieldCompanyName;
	private JTextField textFieldCompanyDirection;
	private JTextField textFieldCompanyProvince;
	private JTextField textFieldCompanyMail;
	private JTextField textFieldCompanyPhone;
	private JTextField textFieldCompanyNIF;
	private JTextField textFieldClientPhone;
	private JTextField textFieldClientMail;
	private JTextField textFieldClientProvince;
	private JTextField textFieldClientDirection;
	private JTextField textFieldClientName;
	private JTextField textFieldClientNIF;
	
	private JLabel lblCompanyName;
	private JLabel lblCompanyDirection;
	private JLabel lblCompanyProvince;
	private JLabel lblCompanyMail;
	private JLabel lblCompanyPhone;
	private JLabel lblCompanyNIF;
	private JLabel lblClientName;
	private JLabel lblClientDirection;
	private JLabel lblClientProvince;
	private JLabel lblClientMail;
	private JLabel lblClientPhone;
	private JLabel lblNif;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Constructor de panel de entidades
	 * @param language	Objeto de idioma
	 */
	public PanelEntities (Language language, RAConfig config)
	{
		this.language 	= language;
		this.config		= config;
		
		setLayout(new GridLayout(0, 2, 0, 0));
		
		panelCompany = new JPanel();
		add(panelCompany);
		panelCompany.setBorder(new TitledBorder (language.get ("companyLblTitle")));
		panelCompany.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblCompanyName = new JLabel(language.get ("companyLblName"));
		panelCompany.add(lblCompanyName);
		
		textFieldCompanyName = new JTextField();
		panelCompany.add(textFieldCompanyName);
		textFieldCompanyName.setColumns(10);
		
		lblCompanyDirection = new JLabel(language.get ("columnDirection"));
		panelCompany.add(lblCompanyDirection);
		
		textFieldCompanyDirection = new JTextField();
		textFieldCompanyDirection.setColumns(10);
		panelCompany.add(textFieldCompanyDirection);
		
		lblCompanyProvince = new JLabel(language.get ("companyLblProvince"));
		panelCompany.add(lblCompanyProvince);
		
		textFieldCompanyProvince = new JTextField();
		textFieldCompanyProvince.setColumns(10);
		panelCompany.add(textFieldCompanyProvince);
		
		lblCompanyMail = new JLabel(language.get ("companyLblMail"));
		panelCompany.add(lblCompanyMail);
		
		textFieldCompanyMail = new JTextField();
		textFieldCompanyMail.setColumns(10);
		panelCompany.add(textFieldCompanyMail);
		
		lblCompanyPhone = new JLabel(language.get ("companyLblPhone"));
		panelCompany.add(lblCompanyPhone);
		
		textFieldCompanyPhone = new JTextField();
		textFieldCompanyPhone.setColumns(10);
		panelCompany.add(textFieldCompanyPhone);
		
		lblCompanyNIF	= new JLabel (language.get ("columnNIF"));
		panelCompany.add (lblCompanyNIF);
		
		textFieldCompanyNIF	= new JTextField ();
		textFieldCompanyNIF.setColumns(10);
		panelCompany.add (textFieldCompanyNIF);
		
		panelClient = new JPanel();
		add(panelClient);
		panelClient.setBorder(new TitledBorder (language.get ("clientTitle")));
		panelClient.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblClientName = new JLabel(language.get ("columnName"));
		panelClient.add(lblClientName);
		
		textFieldClientName = new JTextField();
		textFieldClientName.setColumns(10);
		panelClient.add(textFieldClientName);
		
		lblClientDirection = new JLabel(language.get ("columnDirection"));
		panelClient.add(lblClientDirection);
		
		textFieldClientDirection = new JTextField();
		textFieldClientDirection.setColumns(10);
		panelClient.add(textFieldClientDirection);
		
		lblClientProvince = new JLabel(language.get ("columnProvince"));
		panelClient.add(lblClientProvince);
		
		textFieldClientProvince = new JTextField();
		textFieldClientProvince.setColumns(10);
		panelClient.add(textFieldClientProvince);
		
		lblClientMail = new JLabel(language.get ("columnMail"));
		panelClient.add(lblClientMail);
		
		textFieldClientMail = new JTextField();
		textFieldClientMail.setColumns(10);
		panelClient.add(textFieldClientMail);
		
		lblClientPhone = new JLabel(language.get ("columnPhone"));
		panelClient.add(lblClientPhone);
		
		textFieldClientPhone = new JTextField();
		textFieldClientPhone.setColumns(10);
		panelClient.add(textFieldClientPhone);
		
		lblNif = new JLabel(language.get ("columnNIF"));
		panelClient.add(lblNif);
		
		textFieldClientNIF = new JTextField();
		textFieldClientNIF.setColumns(10);
		panelClient.add(textFieldClientNIF);
		
		setCompanyFields ();
		readOnlyFields ();
	}

	// --------------------------------------------------------------------------------------------
	// CARGAS
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajustar valores por defecto en los campos de texto de los valores de la empresa
	 */
	public void setCompanyFields ()
	{
		loadCompanyValues ();
		textFieldCompanyName.setText(companyName);
		textFieldCompanyDirection.setText (companyDirection);
		textFieldCompanyProvince.setText(companyProvince);
		textFieldCompanyMail.setText(companyMail);
		textFieldCompanyPhone.setText(companyPhone);
		textFieldCompanyNIF.setText (companyNIF);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Prohibir la edicion en los campos de texto
	 */
	private void readOnlyFields ()
	{
		textFieldCompanyName.setEditable(false);
		textFieldCompanyDirection.setEditable(false);
		textFieldCompanyProvince.setEditable(false);
		textFieldCompanyMail.setEditable(false);
		textFieldCompanyPhone.setEditable(false);
		textFieldCompanyNIF.setEditable (false);

		textFieldClientName.setEditable (false);
		textFieldClientDirection.setEditable (false);
		textFieldClientProvince.setEditable (false);
		textFieldClientMail.setEditable (false);
		textFieldClientPhone.setEditable (false);
		textFieldClientNIF.setEditable (false);		
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Cargar valores de la empresa
	 */
	private void loadCompanyValues ()
	{
		companyName 			= config.getCompanyName();
		companyDirection 		= config.getCompanyDirection();
		companyProvince			= config.getCompanyProvince(); 
		companyMail				= config.getCompanyMail(); 
		companyPhone			= config.getCompanyPhone();
		companyNIF				= config.getCompanyNIF();
	}
	
	// --------------------------------------------------------------------------------------------
	// COMPLEMENTOS
	// --------------------------------------------------------------------------------------------

	// --------------------------------------------------------------------------------------------
	/***
	 * Actualizar datos del cliente seleccionado
	 * @param client
	 */
	public void updateSelectedClient (BClient client)
	{
		if (client != null)
		{
			textFieldClientName.setText(client.getName());
			textFieldClientDirection.setText (client.getDirection());
			textFieldClientProvince.setText(client.getProvince());
			textFieldClientMail.setText(client.getEmail());
			textFieldClientPhone.setText(client.getPhone());
			textFieldClientNIF.setText(client.getNIF());
		}
		else
		{
			textFieldClientName.setText("");
			textFieldClientDirection.setText ("");
			textFieldClientProvince.setText("");
			textFieldClientMail.setText("");
			textFieldClientPhone.setText("");
			textFieldClientNIF.setText("");
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve los campos formateados en cuatro columnas de texto
	 * 
	 * @param label1	Etiqueta izquierda
	 * @param text1		Valor izquierda
	 * @param label2	Etiqueta derecha
	 * @param text2		Valor derecha
	 * @return
	 */
	private String padFields (String label1, String text1, String label2, String text2)
	{
		return Util.getFormatedData(label1,  20,  text1,  40,  label2,  20,  text2,  40);
	}

	// --------------------------------------------------------------------------------------------
	// GETTERS
	// --------------------------------------------------------------------------------------------

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve las entidades de empresa y cliente en formato de texto encolumnado
	 * 
	 * @return Texto multilínea formateado con las entidades
	 */
	public String getText() 
	{
		String text = "\n";
		
		text += String.format("%-20s %-40s    %-40s %-20s\n", language.get ("companyLblTitle"), "", language.get ("menuItemClient"), "");
		text += "\n";
		text += padFields (language.get ("companyLblName"), companyName, "    " + language.get ("columnName"), textFieldClientName.getText());
		text += padFields (language.get ("columnDirection"), companyDirection, "    " + language.get ("columnDirection"), textFieldClientDirection.getText());
		text += padFields (language.get ("companyLblProvince"), companyProvince, "    " + language.get ("columnProvince"), textFieldClientProvince.getText());
		text += padFields (language.get ("companyLblMail"), companyMail, "    " + language.get ("columnMail"), textFieldClientMail.getText());
		text += padFields (language.get ("companyLblPhone"), companyPhone, "    " + language.get ("columnPhone"), textFieldClientPhone.getText());
		text += padFields (language.get ("companyNIF"), companyNIF, "    " + language.get ("columnNIF"), textFieldClientNIF.getText());
		return text;
	}

	
	// --------------------------------------------------------------------------------------------
	// IDIOMAS
	// --------------------------------------------------------------------------------------------

	// --------------------------------------------------------------------------------------------
	/***
	 * Aplicar idioma al panel de entidades
	 */
	public void applyLanguage() 
	{
		panelCompany.setBorder(new TitledBorder (language.get ("companyLblTitle")));
		lblCompanyName.setText(language.get ("companyLblName"));
		lblCompanyDirection.setText(language.get ("columnDirection"));
		lblCompanyProvince.setText (language.get ("companyLblProvince"));
		lblCompanyMail.setText(language.get ("companyLblMail"));
		lblCompanyPhone.setText(language.get ("companyLblPhone"));
		lblCompanyNIF.setText (language.get ("columnNIF"));
		panelClient.setBorder(new TitledBorder (language.get ("clientTitle")));
		lblClientName.setText(language.get ("columnName"));
		lblClientDirection.setText(language.get ("columnDirection"));
		lblClientProvince.setText(language.get ("columnProvince"));
		lblClientMail.setText(language.get ("columnMail"));
		lblClientPhone.setText(language.get ("columnPhone"));
		lblNif.setText(language.get ("columnNIF"));
	}
}
