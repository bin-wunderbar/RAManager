package gui.dialogs;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JTextField;

import bll.BService;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo de gestión de servicios
 * 
 * @author G1
 *
 */
<<<<<<< HEAD
=======
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class ServiceDialog extends GenericEntityDialog <BService>
{
	private ValueChecks valueChecks;
		
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
	/***
	 * Inicializa el diálogo
	 * 
	 * @param mainFrame		Formulario principal de la aplicacíon
	 * @param list			Lista de servicios
	 * @throws RAManagerException 
=======
	/**
	 * Inicializa el dialog de servicios
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @throws RAManagerException
>>>>>>> V3.1-alertas
	 */
	public ServiceDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException
	{
		super (owner, rAManager, language, language.getServiceColumnNames(), rAManager.getAllBServices (""), language.get ("serviceTitle"));
		setVisible (true);
	}
	
	// SOBRECAGA POR REQUISITO DE RÚBRICA
<<<<<<< HEAD
=======
	/**
	 * Inicializa el dialog de servicios
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param services		- Lista de servicios
	 * @throws RAManagerException
	 */
>>>>>>> V3.1-alertas
	public ServiceDialog (java.awt.Window owner, RAManager rAManager, Language language, ArrayList <BService> services) throws RAManagerException
	{
		super (owner, rAManager, language, language.getServiceColumnNames(), services, language.get ("serviceTitle"));
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
		bounds.height -= 75;
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
			rAManager.deleteServicesByIds (ids);
		} catch (bll.RAManagerException e) {
<<<<<<< HEAD
			ValueChecks.showExceptionMessage(this, e);
=======
			ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
		}
	}


	// --------------------------------------------------------------------------------------------
	/***
	 * Crear un cliente nuevo a partir de los campos del formulario
	 * 
	 * @return Devuelve el cliente verificado o <strong>null</strong> en caso fallo en la verificación
	 */
	private BService buildService ()
	{
		setLockChanged (true);
		
		BService returnService = null;
		BService service = new BService ();
	
		JTextField textFieldDescription = getTextFields ()[BService.COLUMN_DESCRIPTION];
		JTextField textFieldHourPrice = getTextFields ()[BService.COLUMN_HOUR_PRICE];
		

		if (selectedObjectId > RAManager.NO_ID)
		{
			service.setId(selectedObjectId);
		}
		
<<<<<<< HEAD
		if (valueChecks.isValidTextField(textFieldDescription))
=======
		if (valueChecks.isValidTextField(textFieldDescription, columnNames[BService.COLUMN_DESCRIPTION]))
>>>>>>> V3.1-alertas
		{
			service.setDescription (textFieldDescription.getText());

			if (valueChecks.rangeOfHourPriceApplied(textFieldHourPrice))
			{
				service.setHourPrice (Double.parseDouble (textFieldHourPrice.getText ()));
				selectedObjectId = service.getId();
				returnService = service;
			}
		}
		
		setLockChanged (false);
		
		return returnService;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Guarda los campos como un proveedor nuevo
	 */
	protected void saveFields ()
	{
		BService service = buildService ();

		if (service != null)
		{
			try {
				rAManager.save (service);
				refreshTable ();
				setChanged (false);
			} catch (bll.RAManagerException e) {
<<<<<<< HEAD
				ValueChecks.showExceptionMessage(this, e);
=======
				ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
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
	/***
	 * Actualiza los datos de la tabla
	 */
	protected void refreshTable (String filter)
	{
		try 
		{
			loadItems(rAManager.getAllBServices (filter));
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
<<<<<<< HEAD
			gui.ValueChecks.showExceptionMessage(this, e);
=======
			ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
		}
	}

}
