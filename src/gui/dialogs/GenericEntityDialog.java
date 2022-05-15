package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import bll.IRAObject;
import bll.RAManager;
import gui.Language;
<<<<<<< HEAD
import gui.controls.ButtonAdd;
=======
import gui.controls.ButtonNew;
>>>>>>> V3.1-alertas
import gui.controls.ButtonDelete;
import gui.controls.ButtonEdit;
import gui.controls.ButtonSave;
import gui.controls.RATable;

import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// ------------------------------------------------------------------------------------------------
/***
<<<<<<< HEAD
 * Diálogo base para la creación de diálogos basados en la gestión de tablas de datos <br>
 * <br>
 * Diálogos que heredan de TableDialog son ClientDialog, EmployeeDialog, OrderDialog o VehiclesDialog
 * 
 * @author G1
 *
 * @param <T>	Tipo elementos genéricos con los que trabajará la tabla
 */
=======
 * Dialogo base para la creacion de dialogos basados en la gestion de tablas de datos <br>
 * <br>
 * Dialogos que heredan de TableDialog son ClientDialog, EmployeeDialog, OrderDialog o VehiclesDialog
 * 
 * @author G1
 *
 * @param <T>	Tipo elementos genericos con los que trabajara la tabla
 */
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public abstract class GenericEntityDialog <T> extends JDialog {
	private JPanel contentPanel;

	private JPanel panelControls;
<<<<<<< HEAD
	private JPanel panelFields;
	private JPanel panelFieldsButtons;
	private JToolBar toolBarFilter;
	private JPanel panelTable;
	
	private JPanel panelSelection;
=======
	protected JPanel panelFields;
	protected JPanel panelFieldsButtons;
	private JToolBar toolBarFilter;
	private JPanel panelTable;
	
	private JPanel panelBottom;
>>>>>>> V3.1-alertas
	private RATable <T> table;
	private JTextField[] textFields;
	private JButton[] buttons;
	
	private boolean changed;
	
	private boolean lockChanged;
	private ButtonEdit buttonEdit;
	private ButtonSave buttonSave;

	protected RAManager rAManager;
	protected Language language;
	protected int selectedObjectId;
	protected String[] columnNames;

	private JTextField textFieldFilter;

<<<<<<< HEAD

	// --------------------------------------------------------------------------------------------

	/***
	 * Inicializa el diálogo genérico
	 * 
	 * @param mainFrame		Formulario principal de la aplicación
	 * @param columnNames	Array con los nombres de las columnas a presentar
	 * @param list			Lista de elementos para cargar en la tabla
	 * @param title			Título del diálogo
	 * @param windowBounds	Geometría del formulario superior
=======
	protected JButton buttonSelect;


	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo generico
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param columnNames	- Nombres de las columnas
	 * @param list			- Lista de objetos a gestionar
	 * @param title			- Titulo del dialogo
>>>>>>> V3.1-alertas
	 */
	public GenericEntityDialog (java.awt.Window owner, RAManager rAManager, Language language, String[] columnNames, ArrayList <T> list, String title) 
	{
		super (owner);
		initComponents (owner, rAManager, language, columnNames, list, title);

	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA CONSTRUCTOR CON LA OPCIÓN MODAL
<<<<<<< HEAD
=======
	/**
	 * Inicializa el dialogo generico
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param columnNames	- Nombres de las columnas
	 * @param list			- Lista de objetos a gestionar
	 * @param title			- Titulo del dialogo
	 * @param modal			- Especifica si el dialogo es modal
	 */
>>>>>>> V3.1-alertas
	public GenericEntityDialog (java.awt.Window owner, RAManager rAManager, Language language, String[] columnNames, ArrayList <T> list, String title, boolean modal) 
	{
		super (owner);
		initComponents (owner, rAManager, language, columnNames, list, title);
		setModal (modal);
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa el dialogo generico
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param columnNames	- Nombres de las columnas
	 * @param list			- Lista de objetos a gestionar
	 * @param title			- Titulo del dialogo
	 */
>>>>>>> V3.1-alertas
	private final void initComponents (java.awt.Window owner, RAManager rAManager, Language language, String[] columnNames, ArrayList <T> list, String title)
	{
		setModal (true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				windowClosingEvent ();
			}
		});

		this.rAManager 		= rAManager;
		this.language		= language;
		this.columnNames 	= columnNames;

		setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
		
		setTitle (title);

		Rectangle windowBounds = owner.getBounds ();
		setBounds(windowBounds.x + 50, windowBounds.y + 50, 740, 700);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel 		= new JPanel ();
		panelControls		= new JPanel ();
		panelFieldsButtons	= new JPanel ();
		panelFields 		= new JPanel ();
<<<<<<< HEAD
		panelSelection		= new JPanel ();
=======
		panelBottom		= new JPanel ();
>>>>>>> V3.1-alertas
		toolBarFilter		= new JToolBar ();
		panelTable			= new JPanel ();

		panelTable.setLayout (new BorderLayout ());
		toolBarFilter.setLayout (new FlowLayout (FlowLayout.LEFT));
		panelControls.setLayout (new BorderLayout ());
		panelFieldsButtons.setLayout (new GridLayout (0, 1, 5, 5));
		panelControls.add (panelFieldsButtons, BorderLayout.EAST);
		
		FlowLayout fl_panelSelection = new FlowLayout ();
		fl_panelSelection.setAlignment(FlowLayout.RIGHT);
<<<<<<< HEAD
		panelSelection.setLayout (fl_panelSelection);
=======
		panelBottom.setLayout (fl_panelSelection);
>>>>>>> V3.1-alertas
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JToolBar toolBar = new JToolBar();
			contentPanel.add(toolBar, BorderLayout.CENTER);
			{
<<<<<<< HEAD
				ButtonAdd buttonAdd = new ButtonAdd (language);
=======
				ButtonNew buttonAdd = new ButtonNew (language);
>>>>>>> V3.1-alertas
				buttonAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (changed)
						{
							saveDialog ();
						}
						else
						{
							addEvent ();
						}
					}
				});
				toolBar.add(buttonAdd);
			}
			{
				ButtonDelete buttonDelete = new ButtonDelete (language);
				buttonDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (changed)
						{
							saveDialog ();
						}
						else
						{
							deleteEvent ();
						}
					}
				});
				toolBar.add(buttonDelete);
			}
			
			buttonEdit = new ButtonEdit (language);
			buttonEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (changed)
					{
						saveDialog ();
					}
					else
					{
						editEvent ();
					}
				}
			});
			toolBar.add(buttonEdit);
			
			buttonSave = new ButtonSave (language);
			buttonSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					saveEvent ();
				}
			});
			toolBar.add(buttonSave);
		}
		panelControls.add (panelFields, BorderLayout.CENTER);
		contentPanel.add (panelControls, BorderLayout.NORTH);
		contentPanel.add (panelTable, BorderLayout.SOUTH);
		panelTable.add (toolBarFilter, BorderLayout.NORTH);
		
		panelFields.setLayout(new GridLayout(0, 2, 5, 5));
		{
			table = new RATable <> (columnNames);
			table.addMouseListener (new MouseAdapter () {
				public void mouseClicked (MouseEvent mouseEvent)
				{
					mouseClickedEvent (mouseEvent);
				}
			});
			panelTable.add (new JScrollPane (table), BorderLayout.CENTER);
		}
		
<<<<<<< HEAD
		JButton buttonSelect = new JButton (language.get ("selectButton"));
=======
		JButton buttonClose = new JButton (language.get("closeButton"));
		buttonClose.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				windowClosingEvent ();
			}
		});
		
		panelBottom.add (buttonClose);

		buttonSelect = new JButton (language.get ("selectButton"));
>>>>>>> V3.1-alertas
		buttonSelect.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				if (changed)
				{
					saveDialog ();
				}
				else
				{
					selectedEvent ();
				}
			}
		});
		
<<<<<<< HEAD
		getContentPane().add (panelSelection, BorderLayout.SOUTH);
		panelSelection.add (buttonSelect);
=======
		getContentPane().add (panelBottom, BorderLayout.SOUTH);
		panelBottom.add (buttonSelect);
>>>>>>> V3.1-alertas
		
		loadItems (list);
		initControls ();
		
		lockChanged 		= false;
		changed				= false;
		selectedObjectId	= RAManager.NO_ID;

		initTableDialog ();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicaliza los controles
	 */
	protected void initControls ()
	{
		textFields 	= new JTextField [columnNames.length];
		buttons 	= new JButton [columnNames.length];
		
		for (int i = 0; i < columnNames.length; i++)
		{
			panelFields.add (new JLabel (columnNames[i]));
			panelFields.add (textFields[i] = new JTextField ());
			textFields[i].getDocument().addDocumentListener (new TextFieldChanged (textFields[i]));
			
			panelFieldsButtons.add (buttons[i] = new JButton ());
			buttons[i].setEnabled(false);
		}
		
		toolBarFilter.setLayout(new BorderLayout ());
		toolBarFilter.add (new JLabel (language.get("filter") + "  "), BorderLayout.WEST);
		textFieldFilter = new JTextField ();
		toolBarFilter.add (textFieldFilter, BorderLayout.CENTER);
		
		textFieldFilter.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				applyFilter ();
			}
		});
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Aplica el filtro
	 */
>>>>>>> V3.1-alertas
	public void applyFilter ()
	{
		refreshTable (textFieldFilter.getText().toLowerCase ());
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Carga los elementos en la tabla
	 * 
<<<<<<< HEAD
	 * @param rAObjects	Lista de objetos genérica
=======
	 * @param rAObjects	- Lista de objetos generica
>>>>>>> V3.1-alertas
	 */
	public void loadItems (ArrayList <T> rAObjects)
	{
		table.load (rAObjects);
	}

	// --------------------------------------------------------------------------------------------
	/***
<<<<<<< HEAD
	 * Ajusta los valores de lso campos a partir de un array de objetos
	 * 
	 * @param objectRow Array de objetos con los datos para rellenar
=======
	 * Ajusta los valores de los campos a partir de un array de objetos
	 * 
	 * @param iRAObject - Interfaz de objeto de rekord autoak
>>>>>>> V3.1-alertas
	 */
	public void setTextFieldsOnEditEvent (IRAObject iRAObject)
	{
		for (int i = 0; i < columnNames.length; i++)
		{
			Object value = iRAObject.getValue(i);
			textFields[i].setText("" + (value == null ? "" : value));
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve el array de campos de texto completo del diálogo
<<<<<<< HEAD
	 * @return
=======
	 * @return Array de campos de texto
>>>>>>> V3.1-alertas
	 */
	public String[] getFieldsText ()
	{
		String[] fieldsText = new String[columnNames.length];
		
		for (int i = 0; i < columnNames.length; i++)
		{
			fieldsText[i] = textFields[i].getText();
		}
		
		return fieldsText;
	}
	
	// --------------------------------------------------------------------------------------------
	// CLASES INTERNAS 
	// --------------------------------------------------------------------------------------------
	/***
	 * Clase para detectar los cambios en los campos de texto
	 * 
	 * @author G1
	 *
	 */
	class TextFieldChanged implements DocumentListener
	{
		private JTextField textField;
		
		public TextFieldChanged (JTextField textField)
		{
			this.textField = textField;
		}

		public void changedUpdate(DocumentEvent e) 
		{
			preTextFieldChangedEvent (textField);
		}

		public void removeUpdate(DocumentEvent e) 
		{
			preTextFieldChangedEvent (textField);
		}

		public void insertUpdate(DocumentEvent e) 
		{
			preTextFieldChangedEvent (textField);
		}
	}
	
	
	// --------------------------------------------------------------------------------------------
	/***
<<<<<<< HEAD
	 * Muestra el diálogo de confirmación para guardar los cambios
	 * @return
=======
	 * Muestra el dialogo de confirmación para guardar los cambios
	 * @return El identificador de la opcion selecionada
>>>>>>> V3.1-alertas
	 */
	protected int saveDialog ()
	{
		int selectedOption = JOptionPane.showConfirmDialog(this, 
				language.get ("saveChanges"), 
				language.get ("save"), 
				JOptionPane.YES_NO_CANCEL_OPTION);
		
		switch (selectedOption)
		{
			case JOptionPane.YES_OPTION: 
				saveFields ();
				break;
				
			case JOptionPane.NO_OPTION:
				changed = false;
				break;
		}
		
		return selectedOption;
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Evento de cerrado de ventana, verifica si hay cambios y solicita confirmación sobre el guardado
	 */
	protected void windowClosingEvent ()
	{
		if (changed)
		{
			if (saveDialog () != JOptionPane.CANCEL_OPTION)
			{
				dispose ();
			}
		}
		else
		{
			dispose ();
		}
	}
	
<<<<<<< HEAD
	// --------------------------------------------------------------------------------------------
	// MÉTODOS ABSTRACTOS, se implementan en las clases derivadas, cuando se pueda concretar las acciones 
	// para los siguientes eventos
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo de gestión de tablas de datos
	 */
	protected abstract void initTableDialog ();
	
	/***
	 * Método abstracto de guardado de campos
	 * @return
	 */
	protected abstract void saveFields ();
=======
	/***
	 * Evento de guardado de registro
	 */
	protected void saveEvent ()
	{
		if (rAManager.getActiveLogin().isPermissionWrite())
		{
			saveFields ();
		}
		else
		{
			showPermissionDeniedDialog();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Evento click del raton
	 * @param mouseEvent	- Objeto de evento de raton
	 */
	protected void mouseClickedEvent (MouseEvent mouseEvent)
	{
		if (mouseEvent.getClickCount () == 2)
		{
			selectedEvent();
		}
	}
>>>>>>> V3.1-alertas

	/***
	 * Evento de agregado de registro nuevo
	 */
	protected void addEvent()
	{
		if (rAManager.getActiveLogin ().isPermissionWrite())
		{
			if (!isChanged ()) 
			{
				selectedObjectId = RAManager.NO_ID;
				setTextFieldsDefaults ();
<<<<<<< HEAD
=======
				onNewEvent ();
>>>>>>> V3.1-alertas
			}
		}
		else
		{
			showPermissionDeniedDialog();
		}
	}
<<<<<<< HEAD
	
	protected abstract void setTextFieldsDefaults ();
	
	/***
	 * Evento de guardado de registro
	 */
	protected void saveEvent ()
	{
		if (rAManager.getActiveLogin().isPermissionWrite())
		{
			saveFields ();
		}
		else
		{
			showPermissionDeniedDialog();
		}
	}
	
	/***
	 * Evento click del ratón
	 * @param mouseEvent
	 */
	protected void mouseClickedEvent (MouseEvent mouseEvent)
	{
		if (mouseEvent.getClickCount () == 2)
		{
			selectedEvent();
		}
	}

=======

	/**
	 * Evento producido al crear un registro nuevo
	 */
	protected void onNewEvent () {}

	
	// --------------------------------------------------------------------------------------------
	// MÉTODOS ABSTRACTOS  
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	
	/***
	 * Inicializa el diálogo de gestión de tablas de datos
	 */
	protected abstract void initTableDialog ();
	
	/***
	 * Guarda los datos de los campos de texto
	 * 
	 */
	protected abstract void saveFields ();

	/**
	 * Ajusta los valores por defecto en los campos de texto 
	 */
	protected abstract void setTextFieldsDefaults ();
	
>>>>>>> V3.1-alertas
	// --------------------------------------------------------------------------------------------
	// GETTERS 
	// --------------------------------------------------------------------------------------------

	/***
	 * Devuelve la tabla 
	 * @return	tabla con los registros
	 */
	public RATable <T> getTable ()
	{
		return table;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve los campos de texto
<<<<<<< HEAD
	 * @return campos de texto
=======
	 * @return Los Campos de texto
>>>>>>> V3.1-alertas
	 */
	public JTextField[] getTextFields ()
	{
		return textFields;
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve los botones del panel derecho
<<<<<<< HEAD
	 * @return botones
=======
	 * @return Los botones
>>>>>>> V3.1-alertas
	 */
	public JButton[] getButtons ()
	{
		return buttons;
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta los campos de texto del formulario a partir de los datos obtenidos de la fila seleccionada en la tabla
	 */
	protected void setFieldsFromRow() 
	{
		int selectedRow = getTable ().getSelectedRow();
		if (selectedRow >= 0)
		{
			setTextFieldsOnEditEvent ((IRAObject)getTable ().get(selectedRow));
			selectedObjectId = ((IRAObject)getTable ().get (selectedRow)).getId ();
		}		
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Borra los datos de los campos de texto
	 */
	public void textFieldsClean ()
	{
		lockChanged = true;
		
		for (int i = 0; i < textFields.length; i++)
		{
			textFields[i].setText("");
		}
		
		lockChanged = false;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta los campos de texto en solo lectura
	 */
	public void textFieldsReadOnly ()
	{
		for (int i = 0; i < textFields.length; i++)
		{
			textFields[i].setEditable (false);
		}
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
	/***
=======
	/**
>>>>>>> V3.1-alertas
	 * Operaciones previas a la llamada del evento de cambio de datos al editar un registro
	 * @param textField
	 */
	protected void preTextFieldChangedEvent (JTextField textField)
	{
		if (!lockChanged)
		{
			setChanged (true);
		}
	}
	
<<<<<<< HEAD
	/***
=======
	/**
>>>>>>> V3.1-alertas
	 * Bloquea el acceso al panel de botones lateral
	 */
	protected void lockButtons ()
	{
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].setEnabled(false);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Selecciona un elemento de la tabla y finaliza el diálogo
	 */
	protected final void selectedEvent() 
	{
		int selectedRow = getTable().getSelectedRow();
		
		if (selectedRow >= 0)
		{
			selectedObjectId = ((IRAObject)getTable ().get (selectedRow)).getId();
			dispose ();
		}
		else
		{
			selectedObjectId = RAManager.NO_ID;
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Elimina los clientes seleccionados
	 */
	protected void deleteEvent() 
	{
		
		if (rAManager.getActiveLogin ().isPermissionRemove())
		{
			int[] selectedRows = getTable ().getSelectedRows();
			
			if (selectedRows.length > 0)
			{
				if (JOptionPane.showConfirmDialog(this, language.get ("deleteConfirm"))	== JOptionPane.OK_OPTION)
				{
					ArrayList <Integer> ids = new ArrayList <>(); 
					
					for (int i = 0; i < selectedRows.length; i++)
					{
						ids.add (((IRAObject)getTable ().get (selectedRows[i])).getId());
					}
					deleteEvent (ids);
					refreshTable ();
				}
			}
		}
		else
		{
			showPermissionDeniedDialog();
		}
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
	protected void refreshTable ()
	{
		refreshTable (textFieldFilter.getText().toLowerCase ());
	}
	
	// --------------------------------------------------------------------------------------------
	protected abstract void deleteEvent (ArrayList <Integer> ids);
	
	// --------------------------------------------------------------------------------------------
=======
	/**
	 * Refresca la tabla
	 */
	protected void refreshTable ()
	{
		refreshTable (textFieldFilter.getText().toLowerCase ());
		setTextFieldsDefaults();
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Evento para la eliminacion de objetos segun su lista de ids
	 * @param ids - Lista de ids
	 */
	protected abstract void deleteEvent (ArrayList <Integer> ids);
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Refresca la tabla segun el filtro
	 * 
	 * @param filter - Filtro
	 */
>>>>>>> V3.1-alertas
	protected abstract void refreshTable (String filter);
	
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
	protected final void editEvent() 
=======
	/**
	 * Evento para la edicion de un registro
	 */
	protected void editEvent() 
>>>>>>> V3.1-alertas
	{
		if (rAManager.getActiveLogin ().isPermissionWrite())
		{
			int selectedRow = getTable ().getSelectedRow();
			
			if (!isChanged ())
			{
				setFieldsFromRow ();
				setChanged (false);
			}
			else
			{
				saveDialog ();
			}
			
			if (selectedRow >= 0)
			{
				getTable().setRowSelectionInterval(selectedRow, selectedRow);
			}
			
		}
		else
		{
			showPermissionDeniedDialog();
		}
	}
	
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Habilita un boton de la lista
	 * 
	 * @param row 				- Fila en la que se encuentra el boton
	 * @param actionListener	- Manejador del evento de accion
	 */
>>>>>>> V3.1-alertas
	public void enableButton (int row, ActionListener actionListener)
	{
		textFields [row].setEditable (false);
		JButton button = buttons [row];
		button.setText("...");
		button.setEnabled (true);
		button.addActionListener (actionListener);
	}
	
	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------

	/**
	 * @return the lockChanged
	 */
	public boolean isLockChanged() {
		return lockChanged;
	}


	/**
	 * @param lockChanged the lockChanged to set
	 */
	public void setLockChanged (boolean lockChanged) {
		this.lockChanged = lockChanged;
	}	
	
	/**
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}


	/**
	 * @param changed the changed to set
	 */
	public void setChanged (boolean changed) {
		this.changed = changed;
	}

	/**
	 * @return the columnNames
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	public JPanel getPanelFields ()
	{
		return panelFields;
	}

	public int getSelectedObjectId ()
	{
		return selectedObjectId;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Mostrar mensaje de error "Accion no permitida"
	 */
	public void showPermissionDeniedDialog ()
	{
		JOptionPane.showMessageDialog (this, language.get ("permissionDenied"), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
	}
}
