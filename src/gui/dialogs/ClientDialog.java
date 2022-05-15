package gui.dialogs;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import bll.BClient;
import bll.RALogging;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo de gestión de clientes
 * 
 * @author G1
 *
 */
public class ClientDialog extends GenericEntityDialog <BClient>
{
	private ValueChecks valueChecks;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo
	 * 
	 * @param mainFrame		Formulario principal de la aplicacíon
	 * @param list			Lista de clientes
	 * @param windowBounds	Rectángulo con la posición del formulario invocador
	 * @throws RAManagerException 
	 */
	public ClientDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getClientColumnNames(), rAManager.getAllBClients(""), language.get ("clientDialog"));
		
		setVisible (true);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
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
		
		bounds.height += 25;
		
		valueChecks = new ValueChecks (this, language);
		
		setTextFieldsDefaults ();
		
		setBounds (bounds);
	}


	// --------------------------------------------------------------------------------------------
	@Override
	protected void deleteEvent (ArrayList <Integer> ids)
	{
		try {
			rAManager.deleteClientsByIds (ids);
		} catch (bll.RAManagerException e) {
			ValueChecks.showExceptionMessage(this, e);
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
		
		if (valueChecks.isValidNIF(textFieldNIF))
		{
			client.setNIF(textFieldNIF.getText());
			
			if (valueChecks.isValidTextField (textFieldName))
			{
				client.setName(textFieldName.getText ());
			
				if (valueChecks.isValidTextField(textFieldSurnames))
				{
					client.setSurnames(textFieldSurnames.getText());
					
					if (valueChecks.isValidTextField(textFieldProvince))
					{
						client.setProvince(textFieldProvince.getText ());
						
						
						if (valueChecks.isValidTextField(textFieldDirection))
						{
							client.setDirection(textFieldDirection.getText());
							
							if (valueChecks.isValidEmail (textFieldEmail))
							{
								client.setEmail(textFieldEmail.getText ());
								
								if (valueChecks.isValidPhone (textFieldPhone))
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
				setChanged (false);
			} catch (bll.RAManagerException exception) {
				if (exception.getErrorCode() == bll.RAManagerException.ERROR_UNIQUE)
				{
					JOptionPane.showMessageDialog(this, language.get ("errorClientNIFUNIQUE"), language.get("errorText"), JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					ValueChecks.showExceptionMessage (this, exception);
				}
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
			ValueChecks.showExceptionMessage(this, e);
		}
	}
}
