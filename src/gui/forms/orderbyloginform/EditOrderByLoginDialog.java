package gui.forms.orderbyloginform;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DateTimePicker;

import bll.BOrder;
import bll.BStatus;
import bll.RAManager;
import gui.Language;
import gui.ValueChecks;
import gui.controls.RAComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.awt.GridLayout;
import javax.swing.JCheckBox;

// ------------------------------------------------------------------------------------------------
public class EditOrderByLoginDialog extends JDialog 
{

	private final JPanel contentPanel = new JPanel();
	private RAComboBox <BStatus> rAComboBoxStatus;
	private RAManager rAManager;
	private Language language;
	private BOrder order;
	private boolean changes;
	private boolean savedChanges;
	private DateTimePicker dateTimePicker;
	private JCheckBox checkBoxAccept;


	// ------------------------------------------------------------------------------------------------
	/**
	 * Create the dialog.
	 */
	public EditOrderByLoginDialog (JFrame owner, RAManager rAManager, Language language, BOrder order) 
	{
		super (owner, true);
		
		this.rAManager 	= rAManager;
		this.language	= language;
		this.order		= order;
		savedChanges	= false;
		
		setTitle (language.get("editOrder"));
		setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
		
		Rectangle bounds = owner.getBounds ();
		setBounds(bounds.x + 50, bounds.y + 50, 641, 139);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton buttonCancel = new JButton (language.get("cancel"));
				buttonCancel.addActionListener (new ActionListener () {
					public void actionPerformed (ActionEvent actionEvent)
					{
						verifyWindowClosing ();
					}
				});
				buttonPane.add(buttonCancel);
			}
			{
				JButton buttonSave = new JButton (language.get("save"));
				buttonSave.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent actionEvent)
					{
						saveOrder ();
					}
				});
				buttonPane.add(buttonSave);
				getRootPane().setDefaultButton (buttonSave);
			}
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				verifyWindowClosing ();
			}
		});
		
		initComponents ();
		
		setStatus (order.getIdStatus());
		changes = false;
		setVisible (true);
	}

	// ------------------------------------------------------------------------------------------------
	protected void initComponents ()
	{
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		contentPanel.add (new JLabel (language.get ("columnStatus")));
		
		rAComboBoxStatus = new RAComboBox <> ();
		
		rAComboBoxStatus.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				changes = true;
			}
		});
		
		contentPanel.add (rAComboBoxStatus);
		{
			JLabel labelAccept = new JLabel(language.get ("columnAccept"));
			contentPanel.add(labelAccept);
		}
		{
			checkBoxAccept = new JCheckBox();
			contentPanel.add(checkBoxAccept);
		}
		
		contentPanel.add (new JLabel (language.get("columnIssuedDateTime")));
		initDateTimePicker ();
		
		load ();
	}
	
	// ------------------------------------------------------------------------------------------------
	private void initDateTimePicker ()
	{
		dateTimePicker = new DateTimePicker ();
		dateTimePicker.datePicker.getComponentDateTextField().setEditable (false);
		dateTimePicker.timePicker.getComponentTimeTextField().setEditable (false);
		
		contentPanel.add (dateTimePicker);
	}

	// ------------------------------------------------------------------------------------------------
	protected void load ()
	{
		
		try 
		{
			rAComboBoxStatus.loadItems(rAManager.getAllBStatus (""));
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println (bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, e);
		}
		
		checkBoxAccept.setSelected (order.isAccept());
		
		LocalDateTime issuedDateTime = order.getIssuedDateTime();
		if (issuedDateTime != null)
		{
			dateTimePicker.datePicker.setDate(issuedDateTime.toLocalDate());
			dateTimePicker.timePicker.setTime(issuedDateTime.toLocalTime());
		}
	}
	
	// ------------------------------------------------------------------------------------------------
	protected void setStatus (int idStatus)
	{
		rAComboBoxStatus.selectById (idStatus);
	}
	
	// ------------------------------------------------------------------------------------------------
	protected void saveOrder() 
	{
		saveChanges ();
		dispose ();
	}

	// ------------------------------------------------------------------------------------------------
	protected void saveChanges ()
	{
		BStatus status = (BStatus)rAComboBoxStatus.getSelectedItem();
		order.setIdStatus (status.getId());
		
		LocalDate date = dateTimePicker.getDatePicker().getDate();
		LocalTime time = dateTimePicker.getTimePicker().getTime();
		
		if (date != null && time != null)
		{
			order.setIssuedDateTime(dateTimePicker.getDateTimeStrict());
		}
		else
		{
			order.setIssuedDateTime(null);
		}
		
		order.setAccept(checkBoxAccept.isSelected());
		
		try {
			rAManager.save (order);
			savedChanges = true;
		} catch (bll.RAManagerException e) {
			ValueChecks.showExceptionMessage (this, e);
		}
	}
	
	// ------------------------------------------------------------------------------------------------
	protected void verifyWindowClosing() 
	{
		if (changes)
		{
			switch (JOptionPane.showConfirmDialog (this, language.get ("saveChanges"), language.get ("save"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE))
			{
				case JOptionPane.YES_OPTION:
					saveChanges ();
					break;

				case JOptionPane.NO_OPTION:
					dispose ();
					break;
					
				default:
					break;
			}
		}
		else
		{
			dispose ();
		}
	}

	// ------------------------------------------------------------------------------------------------
	public boolean isSavedChanges ()
	{
		return savedChanges;
	}
	
}
