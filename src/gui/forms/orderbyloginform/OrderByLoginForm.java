package gui.forms.orderbyloginform;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bll.BEmployee;
import bll.BOperation;
import bll.BOrder;
import bll.BStatus;
import bll.RAConfig;
import bll.RAManager;
<<<<<<< HEAD
import bll.Util;
import gui.Language;
import gui.ValueChecks;
import gui.controls.ButtonAdd;
=======
import bll.RAManagerException;
import bll.Util;
import gui.Language;
import gui.ValueChecks;
import gui.controls.ButtonNew;
>>>>>>> V3.1-alertas
import gui.controls.ButtonDelete;
import gui.controls.ButtonEdit;
import gui.controls.ButtonPrint;
import gui.controls.RAComboBox;
import gui.controls.RATable;
import gui.dialogs.AboutDialog;
import gui.dialogs.ConfigurationDialog;
import gui.dialogs.PasswordChangeDialog;
import gui.dialogs.operationdialog.OperationDialog;
import gui.dialogs.printdialog.PrintDialog;
import gui.forms.PanelEntities;
import gui.forms.mainframe.MainFrame;

import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import java.util.ArrayList;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

<<<<<<< HEAD
=======
/**
 * Formulario de ordenes de trabajo por empleado que inicia la sesion
 * 
 * @author G4
 *
 */
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class OrderByLoginForm extends JFrame {
	private Language language;
	private RAManager rAManager;
	private BEmployee login;

	private JPanel contentPane;
	private JPanel panelHeader;
<<<<<<< HEAD
=======
	private JPanel panelLogo;
>>>>>>> V3.1-alertas
	private JPanel panelControls;
	private JPanel panelOrders;
	private JSplitPane splitPaneOrder;
	private PanelEntities panelEntities;
	private JPanel panelOperations;

	private JTextField textFieldFilter;
	private JLabel labelStatus;
	private JToolBar toolBarFilter;
	private RATable <BOrder> ordersTable;
	private RATable <BOperation> operationsTable;
	private RAComboBox<BStatus> rAComboBoxStatuses;

	private JCheckBox checkBoxDescription;
	private JCheckBox checkBoxNIF;

<<<<<<< HEAD
	private ButtonAdd buttonOperationAdd;
=======
	private ButtonNew buttonOperationAdd;
>>>>>>> V3.1-alertas
	private ButtonDelete buttonOperationDelete;
	private ButtonEdit buttonOperationEdit;

	private ButtonEdit buttonOrderEdit;
	private boolean editableOrder;

	private ButtonPrint buttonOrderPrint;

	private RAConfig config;
	private JMenu menuPreferences;
	private JMenuItem menuItemConfiguration;
	private OrderByLoginForm orderByLoginForm;
	private JMenuItem menuItemExit;
	private JMenu menuHelp;
	private JMenuItem menuItemAbout;
	private JMenu menuFile;
	private JMenuItem menuItemPrint;
	private JFrame owner;
	private JMenuItem menuItemPasswordChange;
	private JLabel labelFilter;

<<<<<<< HEAD
	/**
	 * Create the frame.
=======
	private RAComboBox <BEmployee> rAComboBoxEmployee;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo
	 * 
	 * @param owner				- Ventana propietaria
	 * @param rAManager			- Gestor de datos
	 * @param login				- Empleado que inicia sesion
	 * @param config			- Objeto de datos de configuracion
	 * @param language			- Objeto de idioma
>>>>>>> V3.1-alertas
	 */
	public OrderByLoginForm (JFrame owner, RAManager rAManager, BEmployee login, RAConfig config, Language language) 
	{
		this.owner			= owner;
		this.language 		= language;
		this.rAManager 		= rAManager;
		this.login			= login;
		this.config			= config;
		orderByLoginForm	= this;
		
		setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1240, 824);
		
		setTitle (config.getCompanyName());
		
		try {
			setIconImage(ImageIO.read (MainFrame.class.getResource("/resources/app.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuFile = new JMenu(language.get ("menuFile"));
		menuBar.add(menuFile);
		
		menuItemPrint = new JMenuItem (language.get("menuPrint"));
		menuItemPrint.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/printer.png")));
		menuFile.add(menuItemPrint);
		
		menuItemPrint.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showPrintDialog ();
			}
		});
		
		menuFile.addSeparator();
		menuItemExit = new JMenuItem(language.get("menuItemExit"));
		menuItemExit.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				windowClosing ();
			}
		});
		menuFile.add(menuItemExit);
		
		menuPreferences = new JMenu (language.get("menuPreferences"));
		menuBar.add(menuPreferences);
		
		
		menuItemPasswordChange = new JMenuItem (language.get ("menuItemPasswordChange"));
		menuItemPasswordChange.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e)
			{
				showPasswordChangeDialog ();
			}
		});
		menuPreferences.add (menuItemPasswordChange);
		
		menuItemConfiguration = new JMenuItem(language.get("menuItemConfiguration"));
		menuItemConfiguration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		menuItemConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showConfigurationDialog ();
			}
		});
		menuPreferences.add(menuItemConfiguration);
		
		menuHelp = new JMenu(language.get("menuHelp"));
		menuBar.add(menuHelp);
		
		menuItemAbout = new JMenuItem(language.get("menuItemAbout"));
		menuHelp.add(menuItemAbout);
		
		menuItemAbout.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showAboutDialog ();
			}
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panelHeader = new JPanel ();
		panelHeader.setLayout (new BorderLayout ());
		
<<<<<<< HEAD
=======
		panelLogo = new JPanel ();
		panelLogo.setLayout (new BorderLayout ());
		
>>>>>>> V3.1-alertas
		JToolBar toolBarStatus = new JToolBar();
		toolBarStatus.setLayout(new FlowLayout (FlowLayout.RIGHT));
		contentPane.add(toolBarStatus, BorderLayout.SOUTH);
		
		labelStatus = new JLabel("");
		toolBarStatus.add(labelStatus);

		panelOrders = new JPanel ();
		panelOrders.setLayout(new BorderLayout ());
		panelOrders.setBorder (new TitledBorder (language.get("orderTitle")));
		contentPane.add(panelOrders, BorderLayout.CENTER);

		splitPaneOrder = new JSplitPane ();
		panelOrders.add (splitPaneOrder, BorderLayout.CENTER);
		
		panelControls = new JPanel ();
		panelControls.setLayout (new BorderLayout ());
		
<<<<<<< HEAD
		panelHeader.add (createLogo (), BorderLayout.NORTH);
		panelHeader.add (panelControls, BorderLayout.CENTER);
=======
		panelLogo.add (createLogo (), BorderLayout.CENTER);
		panelHeader.add (panelLogo, BorderLayout.NORTH);
		panelHeader.add (panelControls, BorderLayout.SOUTH);
>>>>>>> V3.1-alertas
		contentPane.add (panelHeader, BorderLayout.NORTH);
		
		toolBarFilter = new JToolBar ();
		panelControls.add (toolBarFilter, BorderLayout.NORTH);
		panelEntities = new PanelEntities (language, config);
		panelControls.add (panelEntities, BorderLayout.CENTER);
		
		labelFilter = new JLabel(language.get("filter"));
		toolBarFilter.add(labelFilter);
		toolBarFilter.addSeparator();
		
		textFieldFilter = new JTextField();
		toolBarFilter.add(textFieldFilter);
		textFieldFilter.setColumns(10);
		textFieldFilter.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				applyOrderFilter ();
			}
		});
		
		checkBoxDescription = new JCheckBox(language.get ("columnDescription"));
		checkBoxDescription.setSelected(true);
		checkBoxDescription.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				applyOrderFilter ();
			}
		});
		toolBarFilter.add(checkBoxDescription);
		
		checkBoxNIF = new JCheckBox(language.get("columnNIF"));
		checkBoxNIF.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				applyOrderFilter ();
			}
		});
		
		toolBarFilter.add(checkBoxNIF);
		
		addWindowListener (new WindowAdapter () {
			public void windowClosing (WindowEvent windowEvent)
			{
				orderByLoginForm.windowClosing ();
			}
		});
		
		initComponents ();
		initLoad ();
		
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de cambio de password
	 */
>>>>>>> V3.1-alertas
	protected void showPasswordChangeDialog() 
	{
		new PasswordChangeDialog (this, rAManager, language, login);
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de configuracion
	 */
>>>>>>> V3.1-alertas
	public void showConfigurationDialog() 
	{
		new ConfigurationDialog (orderByLoginForm, rAManager, config, language);
		panelEntities.setCompanyFields();
		applyLanguage ();
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Gestiona el cerrado de ventana
	 */
>>>>>>> V3.1-alertas
	protected void windowClosing() 
	{
		// Si es ventana principal, cerrar RAManager, (puede ser hija del MainFrame en cuyo caso cerrará el MainFrame)
		if (owner.getClass () != gui.forms.mainframe.MainFrame.class)
		{
			rAManager.close ();
		}
		
		dispose ();		
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa los controles del formulario
	 * 
	 */
>>>>>>> V3.1-alertas
	private void initComponents ()
	{
		panelOperations = new JPanel ();
		panelOperations.setBorder (new TitledBorder (language.get("operationsTitle")));
		panelOperations.setLayout (new BorderLayout ());
		

		ordersTable = new RATable <> (language.getOrderColumnNames());
		operationsTable = new RATable <> (language.getOperationColumnNames ());
		
		ordersTable.addMouseListener (new MouseAdapter () {
			public void mouseClicked (MouseEvent mouseEvent)
			{
				switch (mouseEvent.getClickCount ())
				{
					case 1:
						int selectedRow = ordersTable.getSelectedRow();
						
						if (selectedRow >= 0)
						{
							selectOrder (ordersTable.get(selectedRow));
						}
						break;
						
					case 2:
						showEditOrderDialog ();

				}
			}
		});
		
		operationsTable.addMouseListener (new MouseAdapter () {
			public void mouseClicked (MouseEvent mouseEvent)
			{
				if (mouseEvent.getClickCount () == 2)
				{
					int selectedRow = operationsTable.getSelectedRow();
					
					if (selectedRow >= 0)
					{
						showModifyOperationDialog (selectedRow);
					}
				}
			}
		});

		splitPaneOrder.setLeftComponent (new JScrollPane (ordersTable));

		panelOperations.add (new JScrollPane (operationsTable), BorderLayout.CENTER);
		splitPaneOrder.setRightComponent (panelOperations);
		splitPaneOrder.setDividerLocation(getWidth () / 2);
		
<<<<<<< HEAD
		labelStatus.setText (RAConfig.getStatus(login));
=======
		labelStatus.setText (RAConfig.getStatus(login, rAManager.getInfoDB()));
		
		if (login.isPermissionManagement() && login.isPermissionWrite() && login.isPermissionRemove())
		{
			initAssumeIdentity ();
		}
>>>>>>> V3.1-alertas
		
		toolBarFilter.addSeparator();
		toolBarFilter.add (rAComboBoxStatuses = new RAComboBox <> ());
		rAComboBoxStatuses.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				applyOrderFilter ();
			}
		});
		
		initOperationsOrderBar ();
		initOperationsToolBar ();
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	protected void initAssumeIdentity ()
	{
		JPanel panelAssumeIdentity = new JPanel ();
		panelAssumeIdentity.setLayout (new BorderLayout ());
		rAComboBoxEmployee = new RAComboBox <> ();
		
		try {
			rAComboBoxEmployee.loadItems(rAManager.getAllBEmployees(""));

			rAComboBoxEmployee.selectById(login.getId());
			
			panelAssumeIdentity.add (new JLabel (language.get("assumeIdentity") + " "), BorderLayout.WEST);
			panelAssumeIdentity.add (rAComboBoxEmployee, BorderLayout.CENTER);
			panelHeader.add (panelAssumeIdentity, BorderLayout.CENTER);

			rAComboBoxEmployee.addActionListener(new ActionListener () {
				public void actionPerformed (ActionEvent actionEvent)
				{
					selectEmployee ();
				}
			});

		} catch (RAManagerException exception) {
			ValueChecks.showExceptionMessage (orderByLoginForm, language, exception);
		}
		
	}

	// --------------------------------------------------------------------------------------------
	protected void selectEmployee ()
	{
		login = (BEmployee) rAComboBoxEmployee.getSelectedItem();
		applyOrderFilter ();
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicaliza la barra de herramientas de las ordenes
	 */
>>>>>>> V3.1-alertas
	private void initOperationsOrderBar() 
	{
		JToolBar toolBarOrders = new JToolBar ();
		
		toolBarOrders.add (buttonOrderPrint = new ButtonPrint (language));
		toolBarOrders.add (buttonOrderEdit = new ButtonEdit (language));
		
		buttonOrderEdit.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showEditOrderDialog ();
			}
		});

		buttonOrderPrint.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showPrintDialog ();
			}
		});
		
		panelOrders.add (toolBarOrders, BorderLayout.NORTH);
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa la barra de herramientas de las operaciones
	 */
>>>>>>> V3.1-alertas
	private void initOperationsToolBar ()
	{
		JToolBar toolBarOperations = new JToolBar ();
	
<<<<<<< HEAD
		toolBarOperations.add (buttonOperationAdd 		= new ButtonAdd (language));
=======
		toolBarOperations.add (buttonOperationAdd 		= new ButtonNew (language));
>>>>>>> V3.1-alertas
		toolBarOperations.add (buttonOperationDelete 	= new ButtonDelete (language));
		toolBarOperations.add (buttonOperationEdit 		= new ButtonEdit (language));
		
		buttonOperationAdd.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showNewOperationDialog ();
			}
		});
		
		buttonOperationDelete.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				deleteOperations ();
			}
		});
		
		buttonOperationEdit.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				int selectedRow = operationsTable.getSelectedRow();
				
				if (selectedRow >= 0)
				{
					showModifyOperationDialog (selectedRow);
				}
			}
		});
		
		panelOperations.add (toolBarOperations, BorderLayout.NORTH);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Dibuja el logo del formulario
	 * @return El control con el dibujo
	 */
	private JPanel createLogo ()
	{
		return new JPanel () {
		    @Override
		    public Dimension getPreferredSize() {
		        return new Dimension (WIDTH, 35);
		    }
		    
			@Override
			public void paintComponent (Graphics graphics)
			{
				graphics.setColor (Color.BLACK);
				graphics.fillRect (0, 0, getWidth (), getHeight ());
				graphics.setColor (Color.RED);
				graphics.drawString (config.getCompanyName(), 35, 20);
				graphics.drawLine (10, 10, 25, 25);
				graphics.drawLine (25, 25, getWidth () - 10, 25);
			}
		};
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Carga inicial de datos a los controles
	 */
>>>>>>> V3.1-alertas
	private void initLoad ()
	{
		BStatus allStatuses;
		
		try 
		{
			ArrayList <BStatus> statuses = rAManager.getAllBStatus ("");
			statuses.add(0, allStatuses = new BStatus (language.get("statusAll")));
			allStatuses.setId(RAManager.NO_ID);
			rAComboBoxStatuses.loadItems(statuses);
			rAComboBoxStatuses.selectById (BStatus.PENDING);
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

		applyOrderFilter ();
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Aplicar el filtrado al listado de ordenes
	 */
>>>>>>> V3.1-alertas
	protected void applyOrderFilter() 
	{
		BStatus status = (BStatus) rAComboBoxStatuses.getSelectedItem();
		
<<<<<<< HEAD
=======
		operationsTable.clear ();
		
>>>>>>> V3.1-alertas
		try 
		{
			ordersTable.load(rAManager.getBOrdersByEmployee (login, status.getId(), textFieldFilter.getText(), checkBoxDescription.isSelected(), checkBoxNIF.isSelected()));
			
			if (ordersTable.getRowCount() > 0)
			{
				ordersTable.setRowSelectionInterval(0,  0);
				selectOrder (ordersTable.get(0));
				
				panelEntities.updateSelectedClient (ordersTable.get(0).getBClient());
			}
			else
			{
				operationsTable.getList().clear();
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
<<<<<<< HEAD
=======
	/**
	 * Selecionar una orden
	 * 
	 * @param order - Orden
	 */
>>>>>>> V3.1-alertas
	protected void selectOrder (BOrder order)
	{
		panelEntities.updateSelectedClient (order.getBClient());
		
		editableOrder = order.getIdStatus() != BStatus.FINALIZED && order.getIdStatus() != BStatus.CANCELED;
		operationsTable.load(order.getBOperations());
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de modificacion de operacio
	 * 
	 * @param selectedOperation - Indice de la operacion seleccionada
	 */
>>>>>>> V3.1-alertas
	protected void showModifyOperationDialog (int selectedOperation)
	{
		if (login.isPermissionWrite())
		{
			if (editableOrder)
			{
				int selectedOrder 		= ordersTable.getSelectedRow();
				
				if (selectedOrder >= 0)
				{
					BOrder order = ordersTable.get(selectedOrder);
					
					if (selectedOperation >= 0)
					{
						BOperation operation = operationsTable.get(selectedOperation);
						new OperationDialog (this, rAManager, language, login, order, operation);
						reloadTables ();
					}
				}
			}
			else
			{
				showOrderStatusDontChanged ();
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
	 * Muestra el dialogo de operacion nueva
	 */
>>>>>>> V3.1-alertas
	protected void showNewOperationDialog ()
	{
		
		if (login.isPermissionWrite())
		{
			if (editableOrder)
			{
				int selectedOrder 		= ordersTable.getSelectedRow();
				
				if (selectedOrder >= 0)
				{
					BOrder order = ordersTable.get(selectedOrder);
					new OperationDialog (this, rAManager, language, login, order, null);
					reloadTables ();
				}
			}
			else
			{
				showOrderStatusDontChanged ();
			}
		}
		else
		{
			showPermissionDeniedDialog ();
		}
		
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Recarga los componentes de tabla
	 */
>>>>>>> V3.1-alertas
	protected void reloadTables ()
	{
		int selectedOrder 		= ordersTable.getSelectedRow();
		int selectedOperation	= operationsTable.getSelectedRow();
		int idOrder 			= RAManager.NO_ID;
		int idOperation 		= RAManager.NO_ID;
				
		if (selectedOrder >= 0)
		{
			idOrder = ordersTable.get (selectedOrder).getId();
		}
		
		if (selectedOperation >= 0)
		{
			idOperation = operationsTable.get(selectedOperation).getId();
		}
		
		applyOrderFilter();
		
		ordersTable.selectById (idOrder);
		operationsTable.selectById (idOperation);
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Elimnina las operaciones
	 */
>>>>>>> V3.1-alertas
	protected void deleteOperations ()
	{
		if (login.isPermissionRemove())
		{
			if (editableOrder)
			{
				int[] selectedRows = operationsTable.getSelectedRows();
				
				if (selectedRows.length > 0)
				{
					ArrayList <Integer> ids = operationsTable.getSelectedIds (selectedRows);
					BOrder order = ordersTable.get(ordersTable.getSelectedRow());
					
					if (JOptionPane.showConfirmDialog (this, language.get ("deleteOperations"), language.get("delete"), JOptionPane.YES_NO_OPTION)
							== JOptionPane.YES_OPTION)
					{
						
						try {
							rAManager.deleteOperationsByIds (ids);
							applyOrderFilter ();
							ordersTable.selectById(order.getId());
						} catch (bll.RAManagerException e) {
<<<<<<< HEAD
							ValueChecks.showExceptionMessage(this, e);
=======
							ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
						}
					}
				}
			}
			else
			{
				showOrderStatusDontChanged ();
			}
		}
		else
		{
			showPermissionDeniedDialog ();
		}
		
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de edicion de la orden
	 */
>>>>>>> V3.1-alertas
	protected void showEditOrderDialog() 
	{
		if (login.isPermissionWrite())
		{
			int selectedRow = ordersTable.getSelectedRow(); 
			if (selectedRow >= 0)
			{
				BOrder order = ordersTable.get(selectedRow);
				
				if (editableOrder)
				{
					EditOrderByLoginDialog editOrderByLoginDialog = new EditOrderByLoginDialog (this, rAManager, language, order);
					
					if (editOrderByLoginDialog.isSavedChanges ())
					{
						reloadTables ();
					}
				}
				else
				{
					showOrderStatusDontChanged ();
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
=======
	/**
	 * Muestra el mensaje de error de que la orden no puede ser cambiada
	 */
>>>>>>> V3.1-alertas
	public void showOrderStatusDontChanged ()
	{
		JOptionPane.showMessageDialog(this, language.get("orderStatusDontChanged"), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);		
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de impresion
	 */
>>>>>>> V3.1-alertas
	protected void showPrintDialog() 
	{
		if (login.isPermissionRead ())
		{
			int selectedRow = ordersTable.getSelectedRow ();
			if (selectedRow >= 0)
			{
				BOrder order = ordersTable.get(selectedRow);
				new PrintDialog (this, rAManager, config, language, order, panelEntities.getText(), Util.getOrderText (language, order));
			}
			else
			{
				JOptionPane.showMessageDialog(this, language.get ("orderMustBeSelected"));
			}
		}
		else
		{
			showPermissionDeniedDialog ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el mensaje de permiso denegado
	 */
>>>>>>> V3.1-alertas
	public void showPermissionDeniedDialog ()
	{
		JOptionPane.showMessageDialog (this, language.get ("permissionDenied"), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Mostrar JDialog "Acerca de"
	 */
	public void showAboutDialog ()
	{
		new AboutDialog (this, language);
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Aplica el idioma
	 */
>>>>>>> V3.1-alertas
	public void applyLanguage() 
	{
		labelFilter.setText(language.get("filter"));
		checkBoxDescription.setText (language.get ("columnDescription"));
		checkBoxNIF.setText (language.get("columnNIF"));
		
		menuFile.setText (language.get ("menuFile"));
		menuItemExit.setText (language.get ("menuItemExit"));
		menuItemPrint.setText (language.get ("menuPrint"));
		menuPreferences.setText(language.get ("menuPreferences"));
		menuItemPasswordChange.setText  (language.get ("menuItemPasswordChange"));
		menuItemConfiguration.setText(language.get ("menuItemConfiguration"));
		menuHelp.setText(language.get ("menuHelp"));
		menuItemAbout.setText(language.get ("menuItemAbout"));
		
		buttonOperationAdd.applyLanguage ();
		buttonOperationDelete.applyLanguage ();
		buttonOperationEdit.applyLanguage ();
		buttonOrderEdit.applyLanguage ();
		buttonOrderPrint.applyLanguage ();
		
		ordersTable.setColumnNames (language.getOrderColumnNames());
		operationsTable.setColumnNames (language.getOperationColumnNames ());
		
		panelEntities.applyLanguage();
		
		panelOrders.setBorder (new TitledBorder (language.get("orderTitle")));
		panelOperations.setBorder (new TitledBorder (language.get("operationsTitle")));
	}
	
}
