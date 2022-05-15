package gui.dialogs.operationdialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import bll.BEmployee;
import bll.BMaterial;
import bll.BOperation;
import bll.BOrder;
import bll.BService;
import bll.RAManager;
<<<<<<< HEAD
import gui.Language;
import gui.ValueChecks;
import gui.controls.ButtonAdd;
=======
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;
import gui.controls.ButtonNew;
>>>>>>> V3.1-alertas
import gui.controls.ButtonDelete;
import gui.controls.RAComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> V3.1-alertas

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo para la administración de operaciones de la orden de trabajo
 *  
 * @author G1
 *
 */
<<<<<<< HEAD
=======
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class OperationDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private RAManager rAManager;

	private JButton okButton;
	private JLabel lblService;
	private JLabel lblEmployee;
	private JLabel lblDedicatedTime;
	private final JPanel panelOperation = new JPanel();
 
	private JTextField textFieldHourPrice;
	private JTextField textFieldDedicatedTime;

	private RAComboBox <BMaterial> comboBoxMaterial;
	private RAComboBox <BService> comboBoxService;
	private RAComboBox <BEmployee> comboBoxEmployee;
	private JTableUsedMaterials tableUsedMaterials;
	
	private ValueChecks valueChecks;
	
	private boolean changes;
	private JLabel lblMaterial;
	private JLabel lblHourPrice;
	private Language language;
	private BOrder order;
	private BOperation existingOperation;
	private BEmployee login;
	
	// ---------------------------------------------------------------------------------------------
<<<<<<< HEAD
	/***
	 * Inicializa el diálogo de operaciones
	 * 
	 * @param owner		Formulario de la clase principal
	 * @param language		Idioma para aplicar en los controles de usuario
	 * @param operation		Operación de orden de trabajo. <br>
	 * Un valor <strong>null</strong> se procesará como la creación de una operación nueva.<br>
	 * Un valor diferente de <strong>null</strong> se procesará como la edición de una operación existente. 
=======
	/**
	 * Inicializa el dialogo de operaciones
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param login			- Empleado que inicia la sesion
	 * @param order			- Orden de trabajo (nula para nuevo)
	 * @param operation		- Operacion a seleccionar
>>>>>>> V3.1-alertas
	 */
	public OperationDialog (JFrame owner, RAManager rAManager, Language language, BEmployee login, BOrder order, BOperation operation) 
	{
		super (owner, true);
		
		initDialog (owner, rAManager, language, login, order, operation);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA, MODAL CONFIGURABLE
<<<<<<< HEAD
=======
	/**
	 * Inicializa el dialogo de operaciones
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param login			- Empleado que inicia la sesion
	 * @param order			- Orden de trabajo (nula para nuevo)
	 * @param operation		- Operacion a seleccionar
	 * @param modal			- Define si el dialogo es modal
	 */
>>>>>>> V3.1-alertas
	public OperationDialog (JFrame owner, RAManager rAManager, Language language, BEmployee login, BOrder order, BOperation operation, boolean modal) 
	{
		super (owner, modal);
		
		initDialog (owner, rAManager, language, login, order, operation);
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa el dialogo de operaciones
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param login			- Empleado que inicia la sesion
	 * @param order			- Orden de trabajo (nula para nuevo)
	 * @param operation		- Operacion a seleccionar
	 */
>>>>>>> V3.1-alertas
	private final void initDialog (JFrame owner, RAManager rAManager, Language language, BEmployee login, BOrder order, BOperation operation)
	{
		this.rAManager		= rAManager;
		this.language		= language;
		this.login			= login;
		this.order			= order;
		existingOperation	= operation;
		
		valueChecks		= new ValueChecks (this, language);
		
		setTitle (language.get ("operationDialogTitle"));
		
		setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 695, 343);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelOperation, BorderLayout.NORTH);
		panelOperation.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblEmployee = new JLabel (language.get ("columnEmployee"));
		panelOperation.add (lblEmployee);
		comboBoxEmployee = new RAComboBox <> ();
		comboBoxEmployee.setEnabled (login.isPermissionManagement());
		panelOperation.add (comboBoxEmployee);
		
		lblDedicatedTime		= new JLabel (language.get ("columnDedicatedMinutes"));
		panelOperation.add (lblDedicatedTime);
		textFieldDedicatedTime	= new JTextField ();
		textFieldDedicatedTime.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textFieldDedicatedTime.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) { changes = true; }
			  public void removeUpdate(DocumentEvent e) { changes = true;  }
			  public void insertUpdate(DocumentEvent e) { changes = true;  }
			});
		
		panelOperation.add (textFieldDedicatedTime);
		
		lblService = new JLabel(language.get ("columnService"));
		
		panelOperation.add(lblService);
			comboBoxService = new RAComboBox <>();
			comboBoxService.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					serviceSelectedEvent ((BService)comboBoxService.getSelectedItem());
				}
			});
		panelOperation.add(comboBoxService);

		lblHourPrice = new JLabel(language.get ("columnHourlyPrice"));
		panelOperation.add(lblHourPrice);
		textFieldHourPrice = new JTextField();
		textFieldHourPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textFieldHourPrice.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) { changes = true; }
			  public void removeUpdate(DocumentEvent e) { changes = true;  }
			  public void insertUpdate(DocumentEvent e) { changes = true;  }
			});

		
		panelOperation.add(textFieldHourPrice);
		textFieldHourPrice.setColumns(10);
		
		
		JPanel panelMaterials = new JPanel();
		contentPanel.add(panelMaterials, BorderLayout.CENTER);
		panelMaterials.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelMaterialButtons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelMaterialButtons.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelMaterials.add(panelMaterialButtons, BorderLayout.NORTH);
			{
				lblMaterial = new JLabel(language.get ("columnMaterialName"));
				panelMaterialButtons.add(lblMaterial);
			}
			{
				comboBoxMaterial = new RAComboBox <> ();
				panelMaterialButtons.add(comboBoxMaterial);
			}
			{
<<<<<<< HEAD
				ButtonAdd buttonMaterialAdd = new ButtonAdd (language);
=======
				ButtonNew buttonMaterialAdd = new ButtonNew (language);
>>>>>>> V3.1-alertas
				buttonMaterialAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addToUsedMaterials ((BMaterial)comboBoxMaterial.getSelectedItem ());
					}
				});
				panelMaterialButtons.add(buttonMaterialAdd);
			}
			{
				ButtonDelete buttonMaterialDelete = new ButtonDelete (language);
				buttonMaterialDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						deleteSelectedMaterials (tableUsedMaterials.getSelectedRows());
					}
				});
				panelMaterialButtons.add(buttonMaterialDelete);
			}
		}
		{
			JPanel panelMaterialsTable = new JPanel();
			panelMaterials.add(panelMaterialsTable, BorderLayout.CENTER);
			panelMaterialsTable.setLayout(new BorderLayout(0, 0));
			{
				tableUsedMaterials = new JTableUsedMaterials (language, rAManager, valueChecks);
				panelMaterialsTable.add(new JScrollPane (tableUsedMaterials));
			}
		}
		{
			JPanel PanelButtons = new JPanel();
			PanelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(PanelButtons, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton(language.get ("cancelButton"));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						verifyWindowClosing ();
					}
				});
				cancelButton.setActionCommand(language.get ("cancelButton"));
				PanelButtons.add(cancelButton);
			}
			{
				okButton = new JButton(language.get ("okButton"));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveChanges ();
					}
				});
				okButton.setActionCommand("OK");
				PanelButtons.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				verifyWindowClosing ();
			}
		});

		
		loadFields (existingOperation);
		
		setDefaultButton (okButton);
		
		changes	= false;
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	// EVENTOS
	// --------------------------------------------------------------------------------------------
	/**
	 * Verificar que se cierra la ventana
	 */
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

	// --------------------------------------------------------------------------------------------
	/**
	 * Guardar los cambios que hemos realizado
	 */
	private void saveChanges ()
	{
		BOperation operation = buildOperation ();
		if (operation != null)
		{
			try {
				rAManager.save (operation);
				dispose ();
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
	/**
	 * Agrega un material al histórico de materiales utilizados
	 * 
	 * @param material	Material para agregar al histórico
	 */
	private void addToUsedMaterials(BMaterial material) 
	{
		tableUsedMaterials.addMaterial (material);
		changes = true;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina los materiales seleccionados
	 * 
	 * @param selectedMaterials	Array con la lista de índices de los materiales en el histórico 
	 */
	private void deleteSelectedMaterials (int[] selectedMaterials) 
	{
		if (selectedMaterials.length > 0)
		{
			if (JOptionPane.showConfirmDialog(this,  
					language.get ("deleteConfirm") + "\n" + tableUsedMaterials.getItemsText (selectedMaterials)) 
					== JOptionPane.OK_OPTION)
			{
<<<<<<< HEAD
				tableUsedMaterials.removeMaterials (selectedMaterials);
				changes = true;
=======
				ArrayList <Integer> ids = tableUsedMaterials.getSelectedRegisterIds(selectedMaterials);
				try {
					rAManager.deleteUsedMaterialsByIds (ids);
					tableUsedMaterials.removeMaterials (selectedMaterials);
					changes = true;
				} catch (RAManagerException e) {
					ValueChecks.showExceptionMessage(this, language, e);
				}
>>>>>>> V3.1-alertas
			}
		} 
	}
	
	// --------------------------------------------------------------------------------------------
	// CARGAS
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Carga los campos del diálogo
	 * 
	 * @param operation	Operación de la órden de trabajo
	 */
	private void loadFields (BOperation operation)
	{
		try 
		{
			comboBoxMaterial.loadItems(rAManager.getAllBMaterials (""));
			comboBoxEmployee.loadItems(rAManager.getAllBEmployees (""));;
			comboBoxService.loadItems(rAManager.getAllBServices (""));
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
<<<<<<< HEAD
			gui.ValueChecks.showExceptionMessage(this, e);
=======
			gui.ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
		}
		
		if (operation != null)
		{
			loadFieldsFromExistsOperation (operation);
		}
		else
		{
			comboBoxEmployee.selectBy (login);
		}
		
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Carga campos desde una operación existente
	 * 
	 * @param operation con información para completar los campos del diálogo
	 */
	private void loadFieldsFromExistsOperation (BOperation operation)
	{
		comboBoxEmployee.selectBy (operation.getBMechanic());
		comboBoxService.selectBy (operation.getService());
		textFieldDedicatedTime.setText("" + operation.getDedicatedTime());
		textFieldHourPrice.setText("" + operation.getHourlyPriceApplied());
	
		tableUsedMaterials.loadUsedMaterials(operation.getBUsedMaterials());

	}
	
	// --------------------------------------------------------------------------------------------
	// COMPLEMENTOS
	// --------------------------------------------------------------------------------------------

	// --------------------------------------------------------------------------------------------
	/***
	 * Construye una operación de órden de trabajo
	 * 
	 * @return Operación nueva si las verificaciones han resultado exitosas <strong>null</strong> en caso contrario
	 */
	private BOperation buildOperation ()
	{
		BOperation operation, returnValue = null;
		if (existingOperation != null)
		{
			operation = existingOperation;
		}
		else
		{
			operation = new BOperation ();
			operation.setIdOrder (order.getId ());
		}

		if (valueChecks.rangeOfHourPriceApplied(textFieldHourPrice))
		{
			operation.setHourlyPriceApplied(Double.parseDouble (textFieldHourPrice.getText()));
			
			if (valueChecks.rangeOfDedicatedTime (textFieldDedicatedTime))
			{
				operation.setDedicatedTime(Integer.parseInt(textFieldDedicatedTime.getText ()));
				
				operation.setBMechanic ((BEmployee)comboBoxEmployee.getSelectedItem());

				operation.setBUsedMaterials(tableUsedMaterials.getBUsedMaterials());
				operation.setIdService(((BService)comboBoxService.getSelectedItem ()).getId());
				
				returnValue = operation;
			}
		}
		
		return returnValue;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Realiza los ajustes oportunos una vez que el usuario selecciona un servicio
	 * 
	 * @param service	Servicio seleccionado
	 */
	private void serviceSelectedEvent (BService service)
	{
		textFieldHourPrice.setText("" + service.getHourPrice());
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta el botón por defecto del diálogo
	 * 
	 * @param defaultButton
	 */
	private void setDefaultButton (JButton defaultButton)
	{
		JRootPane rootPane = SwingUtilities.getRootPane(defaultButton); 
		rootPane.setDefaultButton(defaultButton);
	}

}
