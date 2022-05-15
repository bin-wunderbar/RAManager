package gui.dialogs;

import java.awt.Rectangle;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import bll.BClient;
import bll.BVehicle;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo de gestión de vehículos
 * 
 * @author G1
 *
 */
public class VehicleDialog extends GenericEntityDialog <BVehicle>
{

	private JButton buttonIdClient;
	private ValueChecks valueChecks;
	private BClient currentClient;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el diálogo de gestión de los vehículos del cliente
	 * 
	 * @param owner - Ventana propietaria
	 * @param rAManager - Gestor principal de la aplicación
	 * @param language - Objeto de idioma
	 * @param currentClient - Cliente desde el que obtener la lista de vehículos
	 */
	public VehicleDialog (java.awt.Window owner, RAManager rAManager, Language language, BClient currentClient) 
	{
		super (owner, rAManager, language, language.getVehicleColumnNames(), currentClient.getVehicles (""), language.get ("vehicleDialogTitle"));

		this.currentClient = currentClient;
		buttonIdClient = getButtons ()[BVehicle.COLUMN_ID_CLIENT];
		buttonIdClient.setEnabled (false);
		getTextFields ()[BVehicle.COLUMN_ID_CLIENT].setText ("" + currentClient.getId());
		
		setVisible (true);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el diálogo de gestión de todos los vehículos
	 * 
	 * @param owner - Ventana propietaria
	 * @param rAManager - Gestor principal de la aplicación
	 * @param language - Objeto de idioma
	 * @throws RAManagerException 
	 */
	public VehicleDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getVehicleColumnNames(), rAManager.getAllBVehicles (""), language.get ("vehicleDialogTitle"));
		
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo de vehículos
	 */
	@Override
	protected void initTableDialog() 
	{
		Rectangle bounds = getBounds ();
		bounds.height -= 25;
		setBounds (bounds);
		
		valueChecks = new ValueChecks (this, language);
		
		enableButton (BVehicle.COLUMN_ID_CLIENT, new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showClientDialog ();
			}
		});
		
		setTextFieldsDefaults ();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Muestra el diálogo de gestión y selección de clientes
	 */
	private void showClientDialog() 
	{
		try 
		{
			ClientDialog clientDialog = new ClientDialog (this, rAManager, language);
			
			int idClient = clientDialog.getSelectedObjectId();
			
			if (idClient > RAManager.NO_ID)
			{
				getTextFields ()[BVehicle.COLUMN_ID_CLIENT].setText("" + idClient);
			}
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}

	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Guarda los datos de los campos como un reistro de vehículo nuevo
	 */
	protected void saveFields ()
	{
		BVehicle vehicle = buildVehicle ();

		if (vehicle != null)
		{
			try {
				rAManager.save (vehicle);
				refreshTable ();
				setChanged (false);
			} catch (bll.RAManagerException exception) 
			{
				if (exception.getErrorCode() == bll.RAManagerException.ERROR_UNIQUE)
				{
					JOptionPane.showMessageDialog(this, language.get ("errorRegistrationNumberUNIQUE"), language.get("errorText"), JOptionPane.ERROR_MESSAGE);
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
	 * Construye un objeto de tipo vehículo a partir de los campos de texto del formulario
	 * 
	 * @return El vehículo creado o <strong>null</strong> en caso de que las verificaciones fallen
	 */
	private BVehicle buildVehicle ()
	{
		setLockChanged (true);
		
		BVehicle returnVehicle = null;
		BVehicle vehicle = new BVehicle ();
		
		JTextField textFieldRegistrationNumber = getTextFields()[BVehicle.COLUMN_REGISTRATION_NUMBER];
		JTextField textFieldModel = getTextFields ()[BVehicle.COLUMN_MODEL];
		JTextField textFieldColor = getTextFields ()[BVehicle.COLUMN_COLOR];
		JTextField textFieldIdClient = getTextFields ()[BVehicle.COLUMN_ID_CLIENT];
		
		if (selectedObjectId > RAManager.NO_ID)
		{
			vehicle.setId(selectedObjectId);
		}
		
		if (valueChecks.isValidTextField (textFieldRegistrationNumber))
		{
			vehicle.setRegistrationNumber(textFieldRegistrationNumber.getText());
			
			if (valueChecks.isValidTextField(textFieldModel))
			{
				vehicle.setModel(textFieldModel.getText());
				
				if (valueChecks.isValidTextField(textFieldColor))
				{
					vehicle.setColor(textFieldColor.getText());

					if (valueChecks.isInteger (textFieldIdClient))
					{
						vehicle.setIdClient (Integer.parseInt (textFieldIdClient.getText()));
						
						selectedObjectId = vehicle.getId();
						returnVehicle = vehicle;
					}
				}
			}
		}
		
		setLockChanged (false);
		
		return returnVehicle;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Actualiza la tabla a partir de los vehículos procedentes de la base de datos
	 */
	protected void refreshTable (String filter)
	{
		if (currentClient == null)
		{
			try 
			{
				loadItems (rAManager.getAllBVehicles (filter));
			} 
			catch (bll.RAManagerException e) 
			{
				rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
				gui.ValueChecks.showExceptionMessage(this, e);
			}
		}
		else
		{
			try 
			{
				currentClient = rAManager.getBClientById(currentClient.getId());
				loadItems (currentClient.getVehicles (filter));
			} 
			catch (bll.RAManagerException e) 
			{
				rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
				gui.ValueChecks.showExceptionMessage(this, e);
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
				if (i != BVehicle.COLUMN_ID_CLIENT)
				{
					textFields[i].setText (getColumnNames ()[i]);
				}
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
	 * Elimina los registros seleccionados
	 */
	@Override
	protected void deleteEvent (ArrayList <Integer> ids) 
	{
		try {
			rAManager.deleteVehiclesByIds (ids);
		} catch (bll.RAManagerException e) {
			ValueChecks.showExceptionMessage(this, e);
		}
	}
	
}
