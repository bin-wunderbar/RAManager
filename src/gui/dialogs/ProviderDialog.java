package gui.dialogs;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JTextField;

import bll.BProvider;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;

// ------------------------------------------------------------------------------------------------
/***
 * Dialogo de gestion de proveedores
 * 
 * @author G1
 *
 */
@SuppressWarnings("serial")
public class ProviderDialog extends GenericEntityDialog <BProvider>
{
	private ValueChecks valueChecks;
		
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @throws RAManagerException
	 */
	public ProviderDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException
	{
		super (owner, rAManager, language, language.getProviderColumnNames(), rAManager.getAllBProviders (""), language.get ("providerTitle"));
		setVisible (true);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	/**
	 * Inicializa el dialogo
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param providers		- Lista de proveedores
	 * @throws RAManagerException
	 */	public ProviderDialog (java.awt.Window owner, RAManager rAManager, Language language, ArrayList <BProvider> providers) throws RAManagerException
	{
		super (owner, rAManager, language, language.getProviderColumnNames(), providers, language.get ("providerTitle"));
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
		bounds.height -= 25;
		setBounds (bounds);
		
		valueChecks = new ValueChecks (this, language);
		
		setTextFieldsDefaults ();
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Elimina los clientes seleccionados
	 */
	@Override
	protected void deleteEvent (ArrayList <Integer> ids) 
	{
		try {
			rAManager.deleteProvidersByIds (ids);
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
	private BProvider buildProvider ()
	{
		setLockChanged (true);
		
		BProvider returnProvider = null;
		BProvider provider = new BProvider ();
	
		JTextField textFieldName = getTextFields ()[BProvider.COLUMN_NAME];
		JTextField textFieldDirection = getTextFields ()[BProvider.COLUMN_DIRECTION];
		JTextField textFieldEmail = getTextFields ()[BProvider.COLUMN_EMAIL];
		JTextField textFieldPhone = getTextFields ()[BProvider.COLUMN_PHONE];
		

		if (selectedObjectId > RAManager.NO_ID)
		{
			provider.setId(selectedObjectId);
		}
		
		if (valueChecks.isValidTextField(textFieldName, columnNames[BProvider.COLUMN_NAME]))
		{
			provider.setName(textFieldName.getText());

			if (valueChecks.isValidTextField(textFieldDirection, columnNames[BProvider.COLUMN_DIRECTION]))
			{
				provider.setDirection(textFieldDirection.getText());
				
				if (valueChecks.isValidEmail(textFieldEmail, columnNames[BProvider.COLUMN_EMAIL]))
				{
					provider.setEmail(textFieldEmail.getText());
					
					if (valueChecks.isValidPhone(textFieldPhone, columnNames[BProvider.COLUMN_PHONE]))
					{
						provider.setPhone(textFieldPhone.getText());
						
						selectedObjectId = provider.getId();
						returnProvider = provider;
					}
				}
			}
		}
		
		setLockChanged (false);
		
		return returnProvider;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Guarda los campos como un proveedor nuevo
	 */
	protected void saveFields ()
	{
		BProvider provider = buildProvider ();

		if (provider != null)
		{
			try {
				rAManager.save (provider);
				refreshTable ();
				setChanged (false);
			} catch (bll.RAManagerException e) {
				ValueChecks.showExceptionMessage(this, language, e);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta los campos de texto con valores por defecto
	 */
	protected void setTextFieldsDefaults ()
	{
		setLockChanged (true);
		
		textFieldsClean ();

		if (!rAManager.getActiveLogin().isPermissionWrite())
		{
			textFieldsReadOnly ();
			lockButtons ();
		}
		
		setLockChanged (false);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Actualiza los datos de la tabla
	 */
	protected void refreshTable (String filter)
	{
		try 
		{
			loadItems (rAManager.getAllBProviders (filter));
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, language, e);
		}

	}

}
