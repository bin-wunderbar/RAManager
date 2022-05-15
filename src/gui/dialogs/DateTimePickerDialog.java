package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DateTimePicker;

import gui.Language;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.awt.event.ActionEvent;


// ------------------------------------------------------------------------------------------------
/**
 * Dialogo de seleccion de fecha y hora
 * 
 * @author G4
 *
 */
@SuppressWarnings("serial")
public class DateTimePickerDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String localDateTimeFormat;
	private DateTimePicker dateTimePicker;

	// --------------------------------------------------------------------------------------------	
	/***
	 * Dialogo de selección de fecha y hora modal
	 * 
	 * @param owner		Diálogo superior
	 * @param language	Objeto de idioma
	 */
	public DateTimePickerDialog (JDialog owner, Language language) 
	{
		super (owner, true);
		initComponents (owner, language);
	}
	
	// --------------------------------------------------------------------------------------------
	// SOBRECARGA POR REQUISITO DE RÚBRICA
	/***
	 * Diálogo de selección de fecha y hora modal selecionable
	 * 
	 * @param owner		Diálogo superior
	 * @param language	Objeto de idioma
	 * @param modal	- Define el comportamiento modal del diálogo
	 */
	public DateTimePickerDialog (JDialog owner, Language language, boolean modal) 
	{
		super (owner, modal);
		initComponents (owner, language);
	}
	
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Iniciliza los componentes del dialogo
	 * 
	 * @param owner 	- Ventana propietaria
	 * @param language 	- Objeto de idioma
	 */
	private final void initComponents (JDialog owner, Language language)
	{
		Rectangle bounds = owner.getBounds();
		setBounds(bounds.x + 50, bounds.y + 50, 448, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton (language.get("cancelButton"));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose ();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			JButton okButton = new JButton (language.get("okButton"));
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedDatePicker ();
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);

			initDateTimePicker ();
			setVisible (true);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * 	Inicializa el componente de selección de fecha y hora
	 */
	private void initDateTimePicker ()
	{
		dateTimePicker = new DateTimePicker ();
		dateTimePicker.datePicker.getComponentDateTextField().setEditable (false);
		dateTimePicker.timePicker.getComponentTimeTextField().setEditable (false);
		
		dateTimePicker.datePicker.setDateToToday();
		dateTimePicker.timePicker.setTimeToNow();
		
		contentPanel.add (dateTimePicker);
		
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Ajustar la fecha y hora seleccionadas y finaliza el diálogo
	 */
	private void selectedDatePicker ()
	{
		LocalDate date = dateTimePicker.getDatePicker().getDate();
		LocalTime time = dateTimePicker.getTimePicker().getTime();
		
		if (date != null && time != null)
		{
			localDateTimeFormat = date.toString() + "T" + time.toString();
		}
		else
		{
			localDateTimeFormat = null;
		}
		
		dispose ();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la fecha y hora en formato LocalDateTime
	 * 
	 * @return the localeDateTimeFormat
	 */
	public String getLocaleDateTimeFormat() 
	{
		return localDateTimeFormat;
	}

}
