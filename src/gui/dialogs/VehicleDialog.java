package gui.dialogs;

import java.awt.Rectangle;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
<<<<<<< HEAD
import javax.swing.JOptionPane;
=======
import javax.swing.JLabel;
import javax.swing.JPanel;
>>>>>>> V3.1-alertas
import javax.swing.JTextField;

import bll.BClient;
import bll.BVehicle;
<<<<<<< HEAD
=======
import bll.IRAObject;
>>>>>>> V3.1-alertas
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;
<<<<<<< HEAD

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo de gestión de vehículos
=======
import gui.controls.DBTextField;

// ------------------------------------------------------------------------------------------------
/***
 * Dialogo de gestión de vehiculos
>>>>>>> V3.1-alertas
 * 
 * @author G1
 *
 */
<<<<<<< HEAD
=======
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class VehicleDialog extends GenericEntityDialog <BVehicle>
{

	private JButton buttonIdClient;
	private ValueChecks valueChecks;
	private BClient currentClient;
<<<<<<< HEAD
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el diálogo de gestión de los vehículos del cliente
	 * 
	 * @param owner - Ventana propietaria
	 * @param rAManager - Gestor principal de la aplicación
	 * @param language - Objeto de idioma
=======
	private DBTextField <BClient> dbTextFieldClient;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo de gestión de los vehiculos del cliente
	 * 
	 * @param owner 		- Ventana propietaria
	 * @param rAManager 	- Gestor principal de la aplicacin
	 * @param language 		- Objeto de idioma
>>>>>>> V3.1-alertas
	 * @param currentClient - Cliente desde el que obtener la lista de vehículos
	 */
	public VehicleDialog (java.awt.Window owner, RAManager rAManager, Language language, BClient currentClient) 
	{
		super (owner, rAManager, language, language.getVehicleColumnNames(), currentClient.getVehicles (""), language.get ("vehicleDialogTitle"));

		this.currentClient = currentClient;
		buttonIdClient = getButtons ()[BVehicle.COLUMN_ID_CLIENT];
		buttonIdClient.setEnabled (false);
<<<<<<< HEAD
		getTextFields ()[BVehicle.COLUMN_ID_CLIENT].setText ("" + currentClient.getId());
=======
		
		panelFieldsRebuild ();
		dbTextFieldClient.setEntity (currentClient);
		
		setChanged(false);
>>>>>>> V3.1-alertas
		
		setVisible (true);
	}

	// --------------------------------------------------------------------------------------------
	/**
<<<<<<< HEAD
	 * Inicializa el diálogo de gestión de todos los vehículos
	 * 
	 * @param owner - Ventana propietaria
	 * @param rAManager - Gestor principal de la aplicación
	 * @param language - Objeto de idioma
=======
	 * Inicializa el dialogo de gestion de todos los vehiculos
	 * 
	 * @param owner 		- Ventana propietaria
	 * @param rAManager 	- Gestor principal de la aplicacion
	 * @param language 		- Objeto de idioma
>>>>>>> V3.1-alertas
	 * @throws RAManagerException 
	 */
	public VehicleDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getVehicleColumnNames(), rAManager.getAllBVehicles (""), language.get ("vehicleDialogTitle"));
<<<<<<< HEAD
=======
		panelFieldsRebuild ();
>>>>>>> V3.1-alertas
		
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
<<<<<<< HEAD
		bounds.height -= 25;
=======
		bounds.height += 50;
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
	 /**
	  * Reconstruye el panel de campos de texto
	  */
	public void panelFieldsRebuild ()
	{
		dbTextFieldClient 	= new DBTextField <> ();
		
		getPanelFields ().removeAll ();
		
		JTextField[] textFields = getTextFields ();
		JPanel panelFields = getPanelFields ();
		
		for (int i = 0; i < textFields.length; i++)
		{
			panelFields.add (new JLabel (columnNames[i]));
			switch (i)
			{
				case BVehicle.COLUMN_ID_CLIENT:
					dbTextFieldClient.setEditable(false);
					panelFields.add (dbTextFieldClient);
					break;
					
				default:
					panelFields.add (textFields[i]);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
				getTextFields ()[BVehicle.COLUMN_ID_CLIENT].setText("" + idClient);
=======
				dbTextFieldClient.setEntity(rAManager.getBClientById(idClient));
				setChanged (true);
>>>>>>> V3.1-alertas
			}
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
<<<<<<< HEAD
				if (exception.getErrorCode() == bll.RAManagerException.ERROR_UNIQUE)
				{
					JOptionPane.showMessageDialog(this, language.get ("errorRegistrationNumberUNIQUE"), language.get("errorText"), JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					ValueChecks.showExceptionMessage (this, exception);
				}
=======
				ValueChecks.showExceptionMessage (this, language, exception);
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
		JTextField textFieldIdClient = getTextFields ()[BVehicle.COLUMN_ID_CLIENT];
=======
>>>>>>> V3.1-alertas
		
		if (selectedObjectId > RAManager.NO_ID)
		{
			vehicle.setId(selectedObjectId);
		}
		
<<<<<<< HEAD
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
=======
		if (valueChecks.isValidTextField (textFieldRegistrationNumber, columnNames[BVehicle.COLUMN_REGISTRATION_NUMBER]))
		{
			vehicle.setRegistrationNumber(textFieldRegistrationNumber.getText());
			
			if (valueChecks.isValidTextField(textFieldModel, columnNames[BVehicle.COLUMN_MODEL]))
			{
				vehicle.setModel(textFieldModel.getText());
				
				if (valueChecks.isValidTextField(textFieldColor, columnNames[BVehicle.COLUMN_COLOR]))
				{
					vehicle.setColor(textFieldColor.getText());

					if (valueChecks.isSelectedObject (dbTextFieldClient.getEntity(), columnNames[BVehicle.COLUMN_ID_CLIENT]))
					{
						vehicle.setIdClient (dbTextFieldClient.getEntity ().getId());
>>>>>>> V3.1-alertas
						
						selectedObjectId = vehicle.getId();
						returnVehicle = vehicle;
					}
<<<<<<< HEAD
=======
					else
					{
						
					}
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
				gui.ValueChecks.showExceptionMessage(this, e);
=======
				ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
				gui.ValueChecks.showExceptionMessage(this, e);
=======
				ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
			}
		}
	}
	
<<<<<<< HEAD
=======
	// --------------------------------------------------------------------------------------------
	@Override
	public void setTextFieldsOnEditEvent (IRAObject iRAObject)
	{
		BVehicle vehicle = (BVehicle)iRAObject;
		
		for (int i = 0; i < columnNames.length; i++)
		{
			switch (i)
			{
				case BVehicle.COLUMN_ID_CLIENT:
					try {
						dbTextFieldClient.setEntity(rAManager.getBClientById(vehicle.getIdClient()));
					} catch (RAManagerException e) {
						rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
						ValueChecks.showExceptionMessage(this, language, e);
					}
					break;
					
				default:
					Object value = vehicle.getValue(i);
					getTextFields()[i].setText ("" + (value == null ? "" : value));
			}
		}
	}
		
>>>>>>> V3.1-alertas
	
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
<<<<<<< HEAD
			ValueChecks.showExceptionMessage(this, e);
=======
			ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
		}
	}
	
}
