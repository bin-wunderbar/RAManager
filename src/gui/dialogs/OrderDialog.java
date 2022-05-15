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
<<<<<<< HEAD
import bll.BOrder;
=======
import bll.BEmployee;
import bll.BOrder;
import bll.BStatus;
import bll.BVehicle;
>>>>>>> V3.1-alertas
import bll.IRAObject;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;
<<<<<<< HEAD

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo para la gestión órdenes de trabajo
=======
import gui.controls.DBTextField;

// ------------------------------------------------------------------------------------------------
/***
 * Dialogo para la gestion ordenes de trabajo
>>>>>>> V3.1-alertas
 * 
 * @author G1
 *
 */
<<<<<<< HEAD
=======
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class OrderDialog extends GenericEntityDialog <BOrder>
{
	private ValueChecks valueChecks;
	
	private BClient currentClient;
	private JCheckBox checkBoxAccept;
<<<<<<< HEAD
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el diálogo de gestión de órdenes de trabajo
	 * @param owner	- Ventana propietaria
	 * @param rAManager - Gestor de la aplicación
	 * @param language - Objeto de idioma
	 * @param currentClient - Cliente seleccionado en la aplicación
=======
	private DBTextField <BClient> dbTextFieldClient;
	private DBTextField <BVehicle> dbTextFieldVehicle;
	private DBTextField <BEmployee> dbTextFieldOperator;
	private DBTextField <BStatus> dbTextFieldStatus;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo de ordenes de trabajo (por cliente)
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param currentClient - Cliente para filtrar las ordenes
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
	 * Inicializa el diálogo de gestión de órdenes de trabajo
	 * @param owner	- Ventana propietaria
	 * @param rAManager - Gestor de la aplicación
	 * @param language - Objeto de idioma
	 * @throws RAManagerException 
	 */
	public OrderDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
=======
	 * Inicializa el dialogo de ordenes de trabajo (todas)
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 */	public OrderDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
>>>>>>> V3.1-alertas
	{
		super (owner, rAManager, language, language.getOrderColumnNames(), rAManager.getAllBOrders (""), language.get ("orderTitle"));
	
		panelFieldsRebuild ();
		setTextFieldsDefaults (null);
		
		setVisible (true);
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
	public void panelFieldsRebuild ()
	{
		checkBoxAccept = new JCheckBox ();
=======
	 /**
	  * Reconstruye el panel de campos de texto
	  */
	public void panelFieldsRebuild ()
	{
		checkBoxAccept 		= new JCheckBox ();
		dbTextFieldClient 	= new DBTextField <> ();
		dbTextFieldVehicle	= new DBTextField <> ();
		dbTextFieldOperator = new DBTextField <> ();
		dbTextFieldStatus	= new DBTextField <> ();
		
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
				
				case BOrder.COLUMN_ID_CLIENT:
					dbTextFieldClient.setEditable(false);
					panelFields.add (dbTextFieldClient);
					break;
					
				case BOrder.COLUMN_ID_VEHICLE:
					dbTextFieldVehicle.setEditable(false);
					panelFields.add (dbTextFieldVehicle);
					break;
				
				case BOrder.COLUMN_ID_EVALUATOR:
					dbTextFieldOperator.setEditable (false);
					panelFields.add (dbTextFieldOperator);
					break;
					
				case BOrder.COLUMN_ID_STATUS:
					dbTextFieldStatus.setEditable (false);
					panelFields.add (dbTextFieldStatus);
					break;
>>>>>>> V3.1-alertas
					
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
<<<<<<< HEAD
		bounds.height += 100;
=======
		bounds.height += 150;
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
				showMechanicDialog ();
=======
				showOperatorDialog ();
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
	 * Muestra el diálogo de estado de la orden de trabajo
=======
	 * Muestra el dialogo de estado de la orden de trabajo
>>>>>>> V3.1-alertas
	 */
	// --------------------------------------------------------------------------------------------
	protected void showStatusDialog() 
	{
<<<<<<< HEAD
		
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
=======
		StatusDialog statusDialog;
		
		BStatus status = dbTextFieldStatus.getEntity();
		statusDialog = new StatusDialog (this, rAManager, language,
				status != null ? status.getId() : 1);
		
		
		int idStatus = statusDialog.getIdStatusSelected();
		if (idStatus > 0)
		{
			try 
			{
				dbTextFieldStatus.setEntity(rAManager.getBStatusById(idStatus));
				setChanged (true);

			} catch (bll.RAManagerException e) 
			{
				rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
				ValueChecks.showExceptionMessage(this, language, e);
			}
>>>>>>> V3.1-alertas
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
<<<<<<< HEAD
	 * Muestra el diálogo para selección de fecha y hora de inicio de la orden
=======
	 * Muestra el dialogo para selección de fecha y hora de inicio de la orden
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
	 * Muestra el diálogo para selección de fecha y hora facturación de la orden
=======
	 * Muestra el dialogo para selección de fecha y hora facturación de la orden
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de seleccion de cliente
	 */
>>>>>>> V3.1-alertas
	private void showClientDialog ()
	{
		try 
		{
			ClientDialog clientDialog = new ClientDialog (this, rAManager, language);
			
			int idClient = clientDialog.getSelectedObjectId();
			
			if (idClient > RAManager.NO_ID)
			{
<<<<<<< HEAD
				getTextFields ()[BOrder.COLUMN_ID_CLIENT].setText("" + idClient);
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
<<<<<<< HEAD
				getTextFields ()[BOrder.COLUMN_ID_VEHICLE].setText("" + idVehicle);
=======
				dbTextFieldVehicle.setEntity(rAManager.getBVehicleById(idVehicle));
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
	 * Muestra el diálogo de gestión y selección de empleados
	 */
<<<<<<< HEAD
	private void showMechanicDialog ()
=======
	private void showOperatorDialog ()
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
				getTextFields()[BOrder.COLUMN_ID_EVALUATOR].setText("" + idEmployee);
=======
				dbTextFieldOperator.setEntity(rAManager.getBEmployeeById(idEmployee));
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
<<<<<<< HEAD
		JTextField textFieldIdClient			= getTextFields ()[BOrder.COLUMN_ID_CLIENT];
		JTextField textFieldIdVehicle 			= getTextFields ()[BOrder.COLUMN_ID_VEHICLE];
		JTextField textFieldIdMechanic			= getTextFields ()[BOrder.COLUMN_ID_EVALUATOR];
		JTextField textFieldIdStatus			= getTextFields ()[BOrder.COLUMN_ID_STATUS];
=======
>>>>>>> V3.1-alertas

		if (selectedObjectId > RAManager.NO_ID)
		{
			order.setId(selectedObjectId);
		}
<<<<<<< HEAD

			if (valueChecks.isValidTextField(textFieldInputDateTime))
			{
				order.setInputDate (LocalDateTime.parse(textFieldInputDateTime.getText()));

				if (valueChecks.isValidTextField (textFieldDescription))
=======
			if (valueChecks.isValidTextField(textFieldInputDateTime, columnNames[BOrder.COLUMN_INPUTDATE]))
			{
				order.setInputDate (LocalDateTime.parse(textFieldInputDateTime.getText()));

				if (valueChecks.isValidTextField (textFieldDescription, columnNames[BOrder.COLUMN_INPUTDATE]))
>>>>>>> V3.1-alertas
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
					
<<<<<<< HEAD
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
=======
					if (valueChecks.isSelectedObject (dbTextFieldClient.getEntity(), columnNames[BOrder.COLUMN_ID_CLIENT]))
					{
						order.setIdClient(dbTextFieldClient.getEntity().getId());
						
						if (valueChecks.isSelectedObject (dbTextFieldVehicle.getEntity(), columnNames[BOrder.COLUMN_ID_VEHICLE]))
						{
							order.setIdVehicle(dbTextFieldVehicle.getEntity().getId());

							if (valueChecks.isSelectedObject (dbTextFieldOperator.getEntity(), columnNames[BOrder.COLUMN_ID_EVALUATOR]))
							{
								order.setIdEvaluator(dbTextFieldOperator.getEntity().getId());

								if (valueChecks.isSelectedObject (dbTextFieldStatus, columnNames[BOrder.COLUMN_ID_STATUS]))
								{
									order.setIdStatus(dbTextFieldStatus.getEntity().getId());
>>>>>>> V3.1-alertas

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
<<<<<<< HEAD
			ValueChecks.showExceptionMessage(this, e);
=======
			ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
		}
	}

	// --------------------------------------------------------------------------------------------
	protected void setTextFieldsDefaults ()
	{
		// anulado para retrasar la llamada a la del constructor actual
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	@Override
	protected void onNewEvent ()
	{
		setTextFieldsDefaults (currentClient);
	}
	
	// --------------------------------------------------------------------------------------------
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
					case BOrder.COLUMN_INPUTDATE:
					case BOrder.COLUMN_ISSUED_DATETIME:
						textFields[i].setText("");
						break;
						
=======
>>>>>>> V3.1-alertas
					case BOrder.COLUMN_ACCEPT:
						checkBoxAccept.setSelected (false);
						break;
						
					case BOrder.COLUMN_ID_CLIENT:
<<<<<<< HEAD
						textFields[i].setText ("" + (currentClient == null ? "" : currentClient.getId()));
						break;

					case BOrder.COLUMN_ID_STATUS:
						textFields[i].setText("1");
						break;
=======
						dbTextFieldClient.setEntity (currentClient);
						break;
					
					case BOrder.COLUMN_ID_VEHICLE:
						dbTextFieldVehicle.setEntity (null);
						break;
						
					case BOrder.COLUMN_ID_EVALUATOR:
						dbTextFieldOperator.setEntity(null);
						break;

					case BOrder.COLUMN_ID_STATUS:
						try 
						{
							dbTextFieldStatus.setEntity(rAManager.getBStatusById(i));
						} 
						catch (bll.RAManagerException e) 
						{
							rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
							ValueChecks.showExceptionMessage(this, language, e);
						}
						break;

					default:
						textFields[i].setText("");
>>>>>>> V3.1-alertas
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
					
<<<<<<< HEAD
=======
				case BOrder.COLUMN_ID_CLIENT:
					dbTextFieldClient.setEntity(order.getBClient());
					break;
					
				case BOrder.COLUMN_ID_VEHICLE:
					dbTextFieldVehicle.setEntity(order.getBVehicle());
					break;
					
				case BOrder.COLUMN_ID_EVALUATOR:
					dbTextFieldOperator.setEntity(order.getBEvaluator());
					break;
					
				case BOrder.COLUMN_ID_STATUS:
					dbTextFieldStatus.setEntity(order.getBStatus());
					break;
					
>>>>>>> V3.1-alertas
				default:
					Object value = order.getValue(i);
					getTextFields()[i].setText("" + (value == null ? "" : value));
			}
		}
	}

}
