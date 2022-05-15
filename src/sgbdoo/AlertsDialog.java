package sgbdoo;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bll.IRAObject;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;
import gui.dialogs.DateTimePickerDialog;
import gui.dialogs.GenericEntityDialog;

// ------------------------------------------------------------------------------------------------
/***
 * Dialogo de gestion de alertas
 * 
 * @author G3
 *
 */
@SuppressWarnings("serial")
public class AlertsDialog extends GenericEntityDialog <Alert>
{
	protected ValueChecks valueChecks;
	protected AlertsManager alertsManager;
	protected JCheckBox checkBoxDone;
	protected boolean showExpiredAlerts;
		
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo
	 * 
	 * @param owner					- Ventana propietaria
	 * @param rAManager				- Gestor de datos
	 * @param alertsManager			- Gestor de alertas
	 * @param showExpiredAlerts 	- Define si se deben mostrar las alertas que han expirado o todas
	 * @param language				- Objeto de idioma
	 * @throws RAManagerException
	 */
	public AlertsDialog (java.awt.Window owner, RAManager rAManager, AlertsManager alertsManager, ArrayList <Alert> alerts, boolean showExpiredAlerts, Language language) throws RAManagerException
	{
		super (owner, rAManager, language, language.getAlertColumnNames(), alerts, language.get ("userAlerts"));
	
		this.alertsManager 		= alertsManager;
		this.showExpiredAlerts 	= showExpiredAlerts;
		
		panelFieldsRebuild ();
		setAlertTextFieldsDefaults();
		
		setVisible (true);
	}

	 /**
	  * Reconstruye el panel de campos de texto
	  */
	public void panelFieldsRebuild ()
	{
		checkBoxDone = new JCheckBox ();
		
		getPanelFields ().removeAll ();
		
		JTextField[] textFields = getTextFields ();
		JPanel panelFields = getPanelFields ();
		
		for (int i = 0; i < textFields.length; i++)
		{
			panelFields.add (new JLabel (columnNames[i]));
			if (i == Alert.COLUMN_DONE)
			{
				panelFields.add (checkBoxDone);
			}
			else
			{
				panelFields.add (textFields[i]);
			}
		}

		enableButton (Alert.COLUMN_EVENT_TIME, new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showEventTimeDialog ();
			}
		});

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
		buttonSelect.setVisible(false);
		
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Muestra el dialogo para selección de fecha y hora de inicio de la orden
	 */
	private void showEventTimeDialog ()
	{
		DateTimePickerDialog datePickerDialog = new DateTimePickerDialog (this, language);
		String localDateTimeFormat = datePickerDialog.getLocaleDateTimeFormat();
		
		if (localDateTimeFormat == null)
		{
			getTextFields ()[Alert.COLUMN_EVENT_TIME].setText ("");

		}
		else
		{
			getTextFields ()[Alert.COLUMN_EVENT_TIME].setText (localDateTimeFormat);
		}	
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Elimina las alertas seleccionadas
	 */
	@Override
	protected void deleteEvent (ArrayList <Integer> ids) 
	{
		try {
			alertsManager.deleteAlerts (ids);
		} catch (bll.RAManagerException e) {
			ValueChecks.showExceptionMessage(this, language, e);
		}
	}


	// --------------------------------------------------------------------------------------------
	/***
	 * Crea una alerta nuevo a partir de los campos del formulario
	 * 
	 * @return Devuelve la alerta verificada o <strong>null</strong> en caso contrario
	 */
	private Alert buildAlert ()
	{
		setLockChanged (true);
		
		Alert returnAlert = null;
		Alert alert = new Alert ();
	
		JTextField textFieldEventTime = getTextFields ()[Alert.COLUMN_EVENT_TIME];
		JTextField textFieldNote = getTextFields ()[Alert.COLUMN_NOTE];
		

		if (selectedObjectId > RAManager.NO_ID)
		{
			alert.setId(selectedObjectId);
		}
		
		if (valueChecks.isValidTextField(textFieldEventTime, columnNames[Alert.COLUMN_EVENT_TIME]))
		{
			alert.setEventTime(textFieldEventTime.getText());

			if (valueChecks.isValidTextField(textFieldNote, columnNames[Alert.COLUMN_NOTE]))
			{
				alert.setNote(textFieldNote.getText());
				alert.setDone (checkBoxDone.isSelected());
				
				selectedObjectId = alert.getId();
				returnAlert = alert;
			}
		}
		
		setLockChanged (false);
		
		return returnAlert;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Guarda los campos como un proveedor nuevo
	 */
	protected void saveFields ()
	{
		Alert alert = buildAlert ();

		if (alert != null)
		{
			try {
				alertsManager.save (alert);
				refreshTable ();
				setChanged (false);
			} catch (bll.RAManagerException e) {
				ValueChecks.showExceptionMessage(this, language, e);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	protected void setTextFieldsDefaults () {
		// Anulado para retrasar la llamada del constructor actual
	}

	// --------------------------------------------------------------------------------------------
	@Override
	protected void onNewEvent ()
	{
		setAlertTextFieldsDefaults ();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta los campos de texto con valores por defecto
	 */
	protected void setAlertTextFieldsDefaults ()
	{
		setLockChanged (true);
		
		textFieldsClean ();
		checkBoxDone.setSelected (false);

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
			loadItems (showExpiredAlerts ? alertsManager.getExpiredAlerts (filter) : alertsManager.getAlerts (filter));
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, language, e);
		}

	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void setTextFieldsOnEditEvent (IRAObject iRAObject)
	{
		Alert alert = (Alert)iRAObject;
		
		for (int i = 0; i < columnNames.length; i++)
		{
			if (i == Alert.COLUMN_DONE)
			{
				checkBoxDone.setSelected(alert.isDone ());
			}
			else
			{
				Object value = alert.getValue(i);
				getTextFields()[i].setText("" + (value == null ? "" : value));
			}
		}
	}
}
