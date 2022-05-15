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
import bll.BEmployee;
import bll.BOrder;
import bll.BStatus;
import bll.BVehicle;
import bll.IRAObject;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;
import gui.controls.DBTextField;

// ------------------------------------------------------------------------------------------------
/***
 * Dialogo para la gestion ordenes de trabajo
 * 
 * @author G1
 *
 */
@SuppressWarnings("serial")
public class OrderDialog extends GenericEntityDialog <BOrder>
{
	private ValueChecks valueChecks;
	
	private BClient currentClient;
	private JCheckBox checkBoxAccept;
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
	 * Inicializa el dialogo de ordenes de trabajo (todas)
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 */	public OrderDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getOrderColumnNames(), rAManager.getAllBOrders (""), language.get ("orderTitle"));
	
		panelFieldsRebuild ();
		setTextFieldsDefaults (null);
		
		setVisible (true);
	}

	// --------------------------------------------------------------------------------------------
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
		bounds.height += 150;
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
				showOperatorDialog ();
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
	 * Muestra el dialogo de estado de la orden de trabajo
	 */
	// --------------------------------------------------------------------------------------------
	protected void showStatusDialog() 
	{
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
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Muestra el dialogo para selección de fecha y hora de inicio de la orden
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
	 * Muestra el dialogo para selección de fecha y hora facturación de la orden
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
	/**
	 * Muestra el dialogo de seleccion de cliente
	 */
	private void showClientDialog ()
	{
		try 
		{
			ClientDialog clientDialog = new ClientDialog (this, rAManager, language);
			
			int idClient = clientDialog.getSelectedObjectId();
			
			if (idClient > RAManager.NO_ID)
			{
				dbTextFieldClient.setEntity(rAManager.getBClientById(idClient));
				setChanged (true);
			}
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, language, e);
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
				dbTextFieldVehicle.setEntity(rAManager.getBVehicleById(idVehicle));
				setChanged (true);
			}
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, language, e);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Muestra el diálogo de gestión y selección de empleados
	 */
	private void showOperatorDialog ()
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
				dbTextFieldOperator.setEntity(rAManager.getBEmployeeById(idEmployee));
				setChanged (true);
			}
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, language, e);
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

		if (selectedObjectId > RAManager.NO_ID)
		{
			order.setId(selectedObjectId);
		}
			if (valueChecks.isValidTextField(textFieldInputDateTime, columnNames[BOrder.COLUMN_INPUTDATE]))
			{
				order.setInputDate (LocalDateTime.parse(textFieldInputDateTime.getText()));

				if (valueChecks.isValidTextField (textFieldDescription, columnNames[BOrder.COLUMN_INPUTDATE]))
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
				ValueChecks.showExceptionMessage(this, language, e);
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
				ValueChecks.showExceptionMessage(this, language, e);
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
				ValueChecks.showExceptionMessage(this, language, e);
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
			ValueChecks.showExceptionMessage(this, language, e);
		}
	}

	// --------------------------------------------------------------------------------------------
	protected void setTextFieldsDefaults ()
	{
		// anulado para retrasar la llamada a la del constructor actual
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	protected void onNewEvent ()
	{
		setTextFieldsDefaults (currentClient);
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
					case BOrder.COLUMN_ACCEPT:
						checkBoxAccept.setSelected (false);
						break;
						
					case BOrder.COLUMN_ID_CLIENT:
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
					
				default:
					Object value = order.getValue(i);
					getTextFields()[i].setText("" + (value == null ? "" : value));
			}
		}
	}

}
