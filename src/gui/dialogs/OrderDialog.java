package gui.dialogs;

import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.time.LocalDateTime;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import bll.BClient;
import bll.BOrder;
import bll.IRAObject;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo para la gestión órdenes de trabajo
 * 
 * @author G1
 *
 */
public class OrderDialog extends GenericEntityDialog <BOrder>
{
	private ValueChecks valueChecks;
	
	private BClient currentClient;
	private JCheckBox checkBoxAccept;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el diálogo de gestión de órdenes de trabajo
	 * @param owner	- Ventana propietaria
	 * @param rAManager - Gestor de la aplicación
	 * @param language - Objeto de idioma
	 * @param currentClient - Cliente seleccionado en la aplicación
	 */
	public OrderDialog (java.awt.Window owner, RAManager rAManager, Language language, BClient currentClient) 
	{
		super (owner, rAManager, language, language.getOrderColumnNames(), currentClient.getOrders (""), language.get ("orderTitle"));
		this.currentClient = currentClient;
		
		panelFieldsRebuild ();
		setTextFieldsDefaults (currentClient);
		
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el diálogo de gestión de órdenes de trabajo
	 * @param owner	- Ventana propietaria
	 * @param rAManager - Gestor de la aplicación
	 * @param language - Objeto de idioma
	 * @throws RAManagerException 
	 */
	public OrderDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getOrderColumnNames(), rAManager.getAllBOrders (""), language.get ("orderTitle"));
	
		panelFieldsRebuild ();
		setTextFieldsDefaults (null);
		
		setVisible (true);
	}

	// --------------------------------------------------------------------------------------------
	public void panelFieldsRebuild ()
	{
		checkBoxAccept = new JCheckBox ();
		getPanelFields ().removeAll ();
		
		JTextField[] textFields = getTextFields ();
		JPanel panelFields = getPanelFields ();
		
		for (int i = 0; i < textFields.length; i++)
		{
			panelFields.add (new JLabel (columnNames[i]));
			switch (i)
			{
				case BOrder.COLUMN_ACCEPT:
					panelFields.add (checkBoxAccept);
					break;
					
				default:
					panelFields.add (textFields[i]);
			}
			
		}
		
		if (currentClient == null)
		{
			enableButton (BOrder.COLUMN_ID_CLIENT, new  ActionListener () {
				public void actionPerformed (ActionEvent actionEvent)
				{
					showClientDialog ();
				}
			});
		}
		else
		{
			textFields[BOrder.COLUMN_ID_CLIENT].setEnabled (false);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo de tabla
	 */
	@Override
	public void initTableDialog ()
	{
		valueChecks = new ValueChecks (this, language);
		
		Rectangle bounds = getBounds ();
		bounds.height += 100;
		setBounds (bounds);
		
		enableButton (BOrder.COLUMN_INPUTDATE, new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showInputDateTimeDialog ();
			}
		});

		enableButton (BOrder.COLUMN_ISSUED_DATETIME, new  ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showIssuedDateTimeDialog ();
			}
		});
		
		enableButton (BOrder.COLUMN_ID_VEHICLE, new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showVehiclesDialog ();
			}
		});
		
		enableButton (BOrder.COLUMN_ID_EVALUATOR, new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showMechanicDialog ();
			}
		});
		
		enableButton (BOrder.COLUMN_ID_STATUS, new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showStatusDialog ();
			}
		});
		

		setTextFieldsDefaults();
	}


	/**
	 * Muestra el diálogo de estado de la orden de trabajo
	 */
	// --------------------------------------------------------------------------------------------
	protected void showStatusDialog() 
	{
		
		int idStatus;
		StatusDialog statusDialog;
		
		try
		{
			idStatus = Integer.parseInt (getTextFields ()[BOrder.COLUMN_ID_STATUS].getText ());
			statusDialog = new StatusDialog (this, rAManager, language, idStatus);
		}
		catch (NumberFormatException exception)
		{
			statusDialog = new StatusDialog (this, rAManager, language, 1);
		}
		
		
		idStatus = statusDialog.getIdStatusSelected();
		
		if (idStatus > 0)
		{
			getTextFields()[BOrder.COLUMN_ID_STATUS].setText("" + idStatus);
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Muestra el diálogo para selección de fecha y hora de inicio de la orden
	 */
	private void showInputDateTimeDialog ()
	{
		DateTimePickerDialog datePickerDialog = new DateTimePickerDialog (this, language);
		String localDateTimeFormat = datePickerDialog.getLocaleDateTimeFormat();
		
		if (localDateTimeFormat == null)
		{
			getTextFields ()[BOrder.COLUMN_INPUTDATE].setText ("");

		}
		else
		{
			getTextFields ()[BOrder.COLUMN_INPUTDATE].setText (localDateTimeFormat);
		}	
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Muestra el diálogo para selección de fecha y hora facturación de la orden
	 */
	private void showIssuedDateTimeDialog ()
	{
		DateTimePickerDialog datePickerDialog = new DateTimePickerDialog (this, language);
		String localDateTimeFormat = datePickerDialog.getLocaleDateTimeFormat();
		
		if (localDateTimeFormat == null)
		{
			getTextFields ()[BOrder.COLUMN_ISSUED_DATETIME].setText ("");
		}
		else
		{
			getTextFields ()[BOrder.COLUMN_ISSUED_DATETIME].setText (localDateTimeFormat);
		}
	}

	// --------------------------------------------------------------------------------------------
	private void showClientDialog ()
	{
		try 
		{
			ClientDialog clientDialog = new ClientDialog (this, rAManager, language);
			
			int idClient = clientDialog.getSelectedObjectId();
			
			if (idClient > RAManager.NO_ID)
			{
				getTextFields ()[BOrder.COLUMN_ID_CLIENT].setText("" + idClient);
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
	 * Muestra el diálogo de gestión y selección de vehículos
	 */
	private void showVehiclesDialog ()
	{
		try 
		{
			VehicleDialog vehicleDialog = 
					currentClient == null ? 
							new VehicleDialog (this, rAManager, language):
							new VehicleDialog (this, rAManager, language, currentClient);
			
			int idVehicle = vehicleDialog.getSelectedObjectId ();
			
			if (idVehicle > RAManager.NO_ID)
			{
				getTextFields ()[BOrder.COLUMN_ID_VEHICLE].setText("" + idVehicle);
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
	 * Muestra el diálogo de gestión y selección de empleados
	 */
	private void showMechanicDialog ()
	{
		try 
		{
			EmployeeDialog employeeDialog = new EmployeeDialog (this, rAManager, language);
			
			Rectangle rectangle = getBounds ();
			rectangle.x += 50;
			rectangle.y += 50;
			employeeDialog.setBounds (rectangle);

			int idEmployee = employeeDialog.getSelectedObjectId ();
			
			if (idEmployee > RAManager.NO_ID)
			{
				getTextFields()[BOrder.COLUMN_ID_EVALUATOR].setText("" + idEmployee);
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
	 * Construye una órden de trabajo a partir de los campos del diálogo
	 * 
	 * @return Orden de trabajo verificada o <strong>null</strong> en caso de error de verificación de los campos del diálogo
	 */
	private BOrder buildOrder ()
	{
		setLockChanged (true);

		BOrder returnOrder = null;
		BOrder order = new BOrder ();

		JTextField textFieldInputDateTime		= getTextFields ()[BOrder.COLUMN_INPUTDATE];
		JTextField textFieldDescription 		= getTextFields ()[BOrder.COLUMN_DESCRIPTION];
		JTextField textFieldIssuedDateTime		= getTextFields ()[BOrder.COLUMN_ISSUED_DATETIME];
		JTextField textFieldIdClient			= getTextFields ()[BOrder.COLUMN_ID_CLIENT];
		JTextField textFieldIdVehicle 			= getTextFields ()[BOrder.COLUMN_ID_VEHICLE];
		JTextField textFieldIdMechanic			= getTextFields ()[BOrder.COLUMN_ID_EVALUATOR];
		JTextField textFieldIdStatus			= getTextFields ()[BOrder.COLUMN_ID_STATUS];

		if (selectedObjectId > RAManager.NO_ID)
		{
			order.setId(selectedObjectId);
		}

			if (valueChecks.isValidTextField(textFieldInputDateTime))
			{
				order.setInputDate (LocalDateTime.parse(textFieldInputDateTime.getText()));

				if (valueChecks.isValidTextField (textFieldDescription))
				{
					order.setDescription(textFieldDescription.getText());
				
					if (textFieldIssuedDateTime.getText().isEmpty())
					{
						order.setIssuedDateTime (null);
					}
					else
					{
						order.setIssuedDateTime (LocalDateTime.parse(textFieldIssuedDateTime.getText()));
					}
					
					order.setAccept (checkBoxAccept.isSelected ());
					
					if (valueChecks.isInteger(textFieldIdClient))
					{
						order.setIdClient(Integer.parseInt (textFieldIdClient.getText()));
						
						if (valueChecks.isInteger(textFieldIdVehicle))
						{
							order.setIdVehicle(Integer.parseInt (textFieldIdVehicle.getText()));

							if (valueChecks.isInteger(textFieldIdMechanic))
							{
								order.setIdEvaluator(Integer.parseInt (textFieldIdMechanic.getText()));

								if (valueChecks.isInteger(textFieldIdStatus))
								{
									order.setIdStatus(Integer.parseInt (textFieldIdStatus.getText ()));

									selectedObjectId = order.getId();
									returnOrder = order;
								}
							}
						}
					}
				}		
			}
			

		setLockChanged (false);
		
		return returnOrder;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Guarda los campos del formulario
	 */
	protected void saveFields ()
	{
		BOrder order = buildOrder ();

		if (order != null)
		{
			try {
				rAManager.save (order);
				refreshTable ();
				setChanged (false);
			} catch (bll.RAManagerException e) {
				ValueChecks.showExceptionMessage(this, e);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Actualiza la tabla de órdenes de trabajo
	 */
	protected void refreshTable (String filter)
	{
		if (currentClient == null)
		{
			try 
			{
				loadItems (rAManager.getAllBOrders (filter));
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
			} 
			catch (bll.RAManagerException e) 
			{
				rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
				gui.ValueChecks.showExceptionMessage(this, e);
			}

			loadItems (currentClient.getOrders (filter));
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Elimina los elementos seleccionados
	 */
	@Override
	protected void deleteEvent(ArrayList <Integer> ids) 
	{
		try {
			rAManager.deleteOrdersByIds (ids);
		} catch (bll.RAManagerException e) {
			ValueChecks.showExceptionMessage(this, e);
		}
	}

	// --------------------------------------------------------------------------------------------
	protected void setTextFieldsDefaults ()
	{
		// anulado para retrasar la llamada a la del constructor actual
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta los campos de texto con valores por defecto
	 */
	protected void setTextFieldsDefaults (BClient currentClient)
	{
		setLockChanged (true);
		
		if (rAManager.getActiveLogin().isPermissionWrite())
		{
			JTextField[] textFields = getTextFields ();
			
			for (int i = 0; i < textFields.length; i++)
			{
				switch (i)
				{
					case BOrder.COLUMN_INPUTDATE:
					case BOrder.COLUMN_ISSUED_DATETIME:
						textFields[i].setText("");
						break;
						
					case BOrder.COLUMN_ACCEPT:
						checkBoxAccept.setSelected (false);
						break;
						
					case BOrder.COLUMN_ID_CLIENT:
						textFields[i].setText ("" + (currentClient == null ? "" : currentClient.getId()));
						break;

					case BOrder.COLUMN_ID_STATUS:
						textFields[i].setText("1");
						break;
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
	@Override
	public void setTextFieldsOnEditEvent (IRAObject iRAObject)
	{
		BOrder order = (BOrder)iRAObject;
		
		for (int i = 0; i < columnNames.length; i++)
		{
			switch (i)
			{
				case BOrder.COLUMN_ACCEPT:
					checkBoxAccept.setSelected(order.isAccept());
					break;
					
				default:
					Object value = order.getValue(i);
					getTextFields()[i].setText("" + (value == null ? "" : value));
			}
		}
	}

}
