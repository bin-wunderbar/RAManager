package gui.dialogs;

import java.awt.Rectangle;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import bll.BClient;
import bll.RALogging;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo de gestión de clientes
 * 
 * @author G1
 *
 */
@SuppressWarnings("serial")
public class ClientDialog extends GenericEntityDialog <BClient>
{
	private ValueChecks valueChecks;
	private JButton buttonVehicles;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo
	 * 
	 * @param owner				- Ventana propietaria
	 * @param rAManager			- Gestor de datos
	 * @param language			- Objeto de idioma
	 * @throws RAManagerException
	 */
	public ClientDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getClientColumnNames(), rAManager.getAllBClients(""), language.get ("clientDialog"));
		
		setVisible (true);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	/**
	 * Inicializa el dialogo
	 * 
	 * @param owner				- Ventana propietaria
	 * @param rAManager			- Gestor de datos
	 * @param language			- Objeto de idioma
	 * @param clients			- Lista de clientes
	 * @throws RAManagerException
	 */
	public ClientDialog (java.awt.Window owner, RAManager rAManager, Language language, ArrayList <BClient> clients) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getClientColumnNames(), clients, language.get ("clientDialog"));
		
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo de tabla
	 */
	@Override
	protected void initTableDialog() 
	{
		Rectangle bounds = getBounds ();
		
		bounds.height += 125;
		
		valueChecks = new ValueChecks (this, language);
		
		setTextFieldsDefaults ();
		
		panelFields.add (new JLabel (language.get("vehicleDialogTitle")));
		buttonVehicles = new JButton ("...");
		panelFieldsButtons.add (buttonVehicles);
		buttonVehicles.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showVehiclesDialog ();
			}
		});
		buttonVehicles.setEnabled (false);
		
		setBounds (bounds);
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void editEvent ()
	{
		super.editEvent();
		
		buttonVehicles.setEnabled (true);
	}
	
	// --------------------------------------------------------------------------------------------
	public void showVehiclesDialog ()
	{
		if (selectedObjectId == RAManager.NO_ID)
		{
			JOptionPane.showMessageDialog (this, language.get ("clientMustExists"));
		}
		else
		{
			try {
				new VehicleDialog (this, rAManager, language, rAManager.getBClientById(selectedObjectId));
			} catch (bll.RAManagerException e) {
				ValueChecks.showExceptionMessage(this, language, e);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	protected void deleteEvent (ArrayList <Integer> ids)
	{
		try {
			rAManager.deleteClientsByIds (ids);
		} catch (bll.RAManagerException e) {
			ValueChecks.showExceptionMessage(this, language, e);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Crear un cliente nuevo a partir de los campos del formulario
	 * 
	 * @return Devuelve el cliente verificado o <strong>null</strong> en caso fallo en la verificación
	 */
	private BClient buildClient ()
	
	{
		setLockChanged (true);
		
		BClient returnClient = null;
		BClient client = new BClient ();
	
		JTextField textFieldNIF = getTextFields()[BClient.COLUMN_NIF];
		JTextField textFieldName = getTextFields ()[BClient.COLUMN_NAME];
		JTextField textFieldSurnames = getTextFields ()[BClient.COLUMN_SURNAMES];
		JTextField textFieldProvince = getTextFields ()[BClient.COLUMN_PROVINCE];
		JTextField textFieldDirection = getTextFields ()[BClient.COLUMN_DIRECTION];
		JTextField textFieldEmail = getTextFields ()[BClient.COLUMN_EMAIL];
		JTextField textFieldPhone = getTextFields ()[BClient.COLUMN_PHONE];
		
		if (selectedObjectId > RAManager.NO_ID)
		{
			client.setId(selectedObjectId);
		}
		
		if (valueChecks.isValidNIF(textFieldNIF, columnNames[BClient.COLUMN_NIF]))
		{
			client.setNIF(textFieldNIF.getText());
			
			if (valueChecks.isValidTextField (textFieldName, columnNames[BClient.COLUMN_NAME]))
			{
				client.setName(textFieldName.getText ());
			
				if (valueChecks.isValidTextField(textFieldSurnames, columnNames[BClient.COLUMN_SURNAMES]))
				{
					client.setSurnames(textFieldSurnames.getText());
					
					if (valueChecks.isValidTextField(textFieldProvince, columnNames[BClient.COLUMN_PROVINCE]))
					{
						client.setProvince(textFieldProvince.getText ());
						
						
						if (valueChecks.isValidTextField(textFieldDirection, columnNames[BClient.COLUMN_DIRECTION]))
						{
							client.setDirection(textFieldDirection.getText());
							
							if (valueChecks.isValidEmail (textFieldEmail, columnNames[BClient.COLUMN_EMAIL]))
							{
								client.setEmail(textFieldEmail.getText ());
								
								if (valueChecks.isValidPhone (textFieldPhone, columnNames[BClient.COLUMN_PHONE]))
								{
									client.setPhone(textFieldPhone.getText ());

									selectedObjectId = client.getId();
									returnClient = client;
								} 
							} 
						}
					} 
				} 
			} 
		} 
		
		setLockChanged (false);
		
		return returnClient;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Guarda los campos como un cliente nuevo
	 */
	protected void saveFields ()
	{
		BClient client = buildClient ();

		if (client != null)
		{
			try {
				rAManager.save (client);
				refreshTable ();
				buttonVehicles.setEnabled (false);
				setChanged (false);
			} catch (bll.RAManagerException exception) {
				ValueChecks.showExceptionMessage (this, language, exception);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta los campos de texto con valores por defecto
	 */
	public void setTextFieldsDefaults ()
	{
		setLockChanged (true);
		
		if (rAManager.getActiveLogin().isPermissionWrite())
		{
			JTextField[] textFields = getTextFields ();
			
			for (int i = 0; i < textFields.length; i++)
			{
				textFields[i].setText("");
			}
		}
		else
		{
			textFieldsClean ();
			textFieldsReadOnly ();
			lockButtons ();
		}
		
		setLockChanged (false);
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	/***
	 * Actualiza los datos de la tabla
	 */
	protected void refreshTable (String filter)
	{
		try 
		{
			loadItems(rAManager.getAllBClients (filter));
		} 
		catch (RAManagerException e) 
		{
			rAManager.getRALogging ().println(RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, language, e);
		}
	}
}
