package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bll.BStatus;
import bll.RAManager;
import gui.Language;
import gui.ValueChecks;
import gui.controls.RAComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// ------------------------------------------------------------------------------------------------
/**
 * Dialogo de seleccion de estados
 * 
 * @author G4
 *
 */
@SuppressWarnings("serial")
public class StatusDialog extends JDialog 
{

	private final JPanel contentPanel = new JPanel();
	private RAComboBox <BStatus> rAComboBoxStatus;
	
	private int idStatusSelected;


	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param inputIdStatus	- identificador del estado a seleccionar
	 */
	public StatusDialog (JDialog owner, RAManager rAManager, Language language, int inputIdStatus) 
	{
		super (owner, true);
	
		initDialog (owner, rAManager, language, inputIdStatus);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA, MODAL CONFIGURABLE 
	/**
	 * Inicializa el dialogo
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param inputIdStatus	- identificador del estado a seleccionar
	 * @param modal			- Define si el dialogo es modal
	 */
	public StatusDialog (JDialog owner, RAManager rAManager, Language language, int inputIdStatus, boolean modal) 
	{
		super (owner, modal);
	
		initDialog (owner, rAManager, language, inputIdStatus);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param inputIdStatus	- identificador del estado a seleccionar
	 */
	private final void initDialog (JDialog owner, RAManager rAManager, Language language, int inputIdStatus) 
	{
		Rectangle bounds = owner.getBounds ();
		bounds.x += 50;
		bounds.y += 50;
		setBounds (bounds);
		setTitle (language.get ("statusDialogTitle"));
		
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setBounds(100, 100, 414, 122);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			rAComboBoxStatus = new RAComboBox <> ();
			contentPanel.add(rAComboBoxStatus);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancel ();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectStatus ();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		try 
		{
			rAComboBoxStatus.loadItems(rAManager.getAllBStatus (""));
			
			setSelectedById (inputIdStatus);
			idStatusSelected = -1;
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, language, e);
		}

		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Selecciona un objeto de estado segun su identificador
	 * 
	 * @param inputIdStatus - Identificador del estado
	 */
	private void setSelectedById (int inputIdStatus)
	{
		if (inputIdStatus > 0)
		{
			for (int i = 0; i < rAComboBoxStatus.getItemCount(); i++)
			{
				if (rAComboBoxStatus.getItemAt(i).getId() == inputIdStatus)
				{
					rAComboBoxStatus.setSelectedIndex(i);
					break;
				}
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Selecciona el estado
	 */
	protected void selectStatus() 
	{
		BStatus status = (BStatus)rAComboBoxStatus.getSelectedItem();
		
		if (status != null)
		{
			idStatusSelected = status.getId();
		}

		dispose ();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Cierra el dialogo
	 */
	protected void cancel() 
	{
		dispose ();
	}

	/**
	 * @return the idStatusSelected
	 */
	public int getIdStatusSelected() {
		return idStatusSelected;
	}

}
