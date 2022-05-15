package gui.forms.mainframe;

import java.awt.BorderLayout;


import java.awt.FlowLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import bll.RAManager;
import bll.Util;
import gui.Language;
import gui.dialogs.AboutDialog;
import gui.dialogs.ClientDialog;
import gui.dialogs.ConfigurationDialog;
import gui.dialogs.EmployeeDialog;
import gui.dialogs.MaterialDialog;
import gui.dialogs.OrderDialog;
import gui.dialogs.PasswordChangeDialog;
import gui.dialogs.ProviderDialog;
import gui.dialogs.RoleDialog;
import gui.dialogs.ServiceDialog;
import gui.dialogs.VehicleDialog;
import gui.dialogs.operationdialog.OperationDialog;
import gui.dialogs.printdialog.PrintDialog;
import gui.forms.PanelEntities;
import gui.forms.orderbyloginform.OrderByLoginForm;
import bll.BClient;
import bll.BEmployee;
import bll.BOperation;
import bll.BOrder;
import bll.BRole;
import bll.RAConfig;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/***
 * Formulario principal donde se reuniran todos los datos  necesarios de nuestro cliente para crear la factura
 * @author G1
 *
 */
public class MainFrame extends JFrame {

	private Language language;
	private RAManager rAManager;

	private MainFrame mainFrame;
	private JPanel contentPane;

	private MainFrameMenuBar mainFrameMenuBar;
	private PanelEntities panelEntities;
	private TabbedOrder tabbedOrder;
	private ToolBarOrder toolBarOrder;
	private JToolBar jToolBarStatus;
	private JLabel lblStatus;
	private BOrder currentOrder;
	private BClient currentClient;
	private BEmployee login;
	
	private RAConfig config;
	
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el formulario principal
	 * 
	 * @param rAManager		Gestor operativo de la aplicación
	 * @param config		Configuración de la aplicación
	 * @param language		Objeto de idioma de la aplicación
	 */
	public MainFrame (RAManager rAManager, RAConfig config, Language language) 
	{
		mainFrame		= this;
		this.rAManager	= rAManager;
		this.language	= language;
		this.config		= config; 
		login 			= rAManager.getActiveLogin();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1088, 784);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		addWindowListener (new WindowAdapter () {
			public void windowClosing (WindowEvent windowEvent)
			{
				mainFrame.windowClosing ();
			}
		});
		
		initComponents ();

		setVisible (true);
	}
	

	// --------------------------------------------------------------------------------------------
	// INICIALIZACIONES
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializar los componentes del Formulario
	 */
	private final void initComponents ()
	{
		setTitle (config.getCompanyName());

		mainFrameMenuBar 	= new MainFrameMenuBar (mainFrame, language);
		setJMenuBar(mainFrameMenuBar);

		toolBarOrder		= new ToolBarOrder (mainFrame);
		contentPane.add(toolBarOrder, BorderLayout.NORTH);

		panelEntities 		= new PanelEntities (language, config);

		contentPane.add(panelEntities, BorderLayout.CENTER);

		JPanel panelSouth = new JPanel ();
		panelSouth.setLayout (new BorderLayout ());
		contentPane.add (panelSouth, BorderLayout.SOUTH);
		
		tabbedOrder 			= new TabbedOrder (mainFrame);
		panelSouth.add (tabbedOrder, BorderLayout.CENTER);
		
		jToolBarStatus 		= new JToolBar ();
		jToolBarStatus.setLayout(new FlowLayout (FlowLayout.RIGHT));
		panelSouth.add (jToolBarStatus, BorderLayout.SOUTH);
		
		lblStatus			= new JLabel (RAConfig.getStatus (login));
		jToolBarStatus.add(lblStatus);

		try {
			setIconImage(ImageIO.read (MainFrame.class.getResource("/resources/app.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	// --------------------------------------------------------------------------------------------
	// DIALOGOS
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	public void showConfigurationDialog() 
	{
		new ConfigurationDialog (mainFrame, mainFrame.getRAManager(), mainFrame.getConfig (), language);
		panelEntities.setCompanyFields();
		applyLanguage ();
	}
	
	// --------------------------------------------------------------------------------------------
	public void showRoleDialog ()
	{
		if (login.isPermissionManagement() && login.getIdRole() == BRole.MANAGEMENT)
		{
			new RoleDialog (mainFrame, rAManager, language);
		}
		else
		{
			showPermissionDeniedDialog();
		}
	}

	// --------------------------------------------------------------------------------------------	
	public void showProviderDialog() 
	{
		try 
		{
			new ProviderDialog (mainFrame, rAManager, language);
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}

	}

	// --------------------------------------------------------------------------------------------
	public void showMaterialDialog() 
	{
		try 
		{
			new MaterialDialog (mainFrame, rAManager, language);
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}

	}
	
	// --------------------------------------------------------------------------------------------
	public void showServiceDialog() 
	{
		try 
		{
			new ServiceDialog (mainFrame, rAManager, language);
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Mostrar formulario de Clientes
	 */
	public void showClientDialog()
	{
		try 
		{
			ClientDialog clientDialog = new ClientDialog (this, rAManager, language);
			updateCurrentClient ();
			setSelectedClientById(clientDialog.getSelectedObjectId());
		
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}
	}


	// --------------------------------------------------------------------------------------------
	/***
	 * Mostrar formulario de vehiculos
	 */
	public void showVehicleDialog() 
	{
		try 
		{
			new VehicleDialog (mainFrame, rAManager, language);
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	public void showEmployeeDialog() 
	{
		try 
		{
			new EmployeeDialog (this, rAManager, language);
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Mostrar JDialog "Acerca de"
	 */
	public void showAboutDialog ()
	{
		new AboutDialog (mainFrame, language);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Mostrar el diálogo de operaciones en modo modificación
	 * @param operationId	Identificador de operación a modificar
	 */
	public void showModifyOperationDialog (int operationId)
	{
		if (login.isPermissionWrite())
		{
			try 
			{
				BOperation operation = rAManager.getBOperationById (operationId);
				new OperationDialog (mainFrame, rAManager, language, login, currentOrder, operation);
				mainFrame.updateCurrentClient();
			} 
			catch (bll.RAManagerException e) 
			{
				rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
				gui.ValueChecks.showExceptionMessage(this, e);
			}
		}
		else
		{
			showPermissionDeniedDialog ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Añadir nueva operacion al JDialog de Operaciones
	 */
	public void showNewOperationDialog ()
	{
		if (login.isPermissionWrite())
		{
			if (currentOrder != null)
			{
				new OperationDialog (mainFrame, rAManager, language, login, currentOrder, null);
				mainFrame.updateCurrentClient ();
			}
			else
			{
				JOptionPane.showMessageDialog(mainFrame, language.get ("orderMustBeSelected"));
			}
		}
		else
		{
			showPermissionDeniedDialog ();
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Eliminar operaciones
	 */
	public void deleteOperations() 
	{
		if (login.isPermissionRemove())
		{
			tabbedOrder.deleteOperations();
		}
		else
		{
			showPermissionDeniedDialog ();
		}
	}

	// --------------------------------------------------------------------------------------------
	public void showOrderEmployeeDialog() 
	{
		new OrderByLoginForm (this, rAManager, login, config, language);
	}

	// --------------------------------------------------------------------------------------------
	public void showOrderAllDialog() 
	{
		try 
		{
			new OrderDialog (mainFrame, rAManager, language);
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Mostrar Formulario de Encargos
	 */
	public void showOrderClientDialog() 
	{
		if (currentClient != null)
		{
			OrderDialog orderDialog = new OrderDialog (mainFrame, rAManager, language, currentClient);
			
			int selectedOrderId = orderDialog.getSelectedObjectId();
			
			updateCurrentClient();
			
			if (selectedOrderId > 0)
			{
				updateCurrentdOrder (selectedOrderId);
			}
			else
			{
				updateSelectedOrder (currentClient.getFirstBOrder ());
			}
		}
		else
		{
			JOptionPane.showMessageDialog(mainFrame, language.get ("clientMustBeSelected"));
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Mostrar formulario de Impresion de la Factura
	 */
	public void showPrintDialog ()
	{
		if (login.isPermissionRead ())
		{
			if (currentOrder != null)
			{
				new PrintDialog (mainFrame, rAManager, config, language, currentOrder, panelEntities.getText(), Util.getOrderText (language, currentOrder));
			}
			else
			{
				JOptionPane.showMessageDialog(mainFrame, language.get ("orderMustBeSelected"));
			}
		}
		else
		{
			showPermissionDeniedDialog ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	public void showPasswordChangeDialog() 
	{
		new PasswordChangeDialog (this, rAManager, language, login);
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Mostrar mensaje de error "Accion no permitida"
	 */
	public void showPermissionDeniedDialog ()
	{
		JOptionPane.showMessageDialog (mainFrame, language.get ("permissionDenied"), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
	}

	// --------------------------------------------------------------------------------------------
	protected void windowClosing ()
	{
		rAManager.close ();
		dispose ();
	}
	
	// --------------------------------------------------------------------------------------------
	// COMPLEMENTOS
	// --------------------------------------------------------------------------------------------

	// --------------------------------------------------------------------------------------------
	/***
	 * Mostrar mensaje "No Implementado todavia" en el formulario de impresion al darle a imprimir
	 */
	public void notImplementedYet ()
	{
		JOptionPane.showMessageDialog(mainFrame, "Not implemented yet!");
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Actualizar encargo seleccionado
	 * @param order
	 */
	private void updateSelectedOrder (BOrder order)
	{
		currentOrder = order;
		
		toolBarOrder.setOrderText (currentOrder);
		tabbedOrder.updateSelectedOrder (currentOrder);
	}
	// --------------------------------------------------------------------------------------------
	/***
	 * Actualiza la orden seleccionada a partir del identificador de la misma
	 * @param selectedOrderId	Identificador de la orden seleccionada
	 */
	private void updateCurrentdOrder (int selectedOrderId) 
	{
		try 
		{
			updateSelectedOrder (rAManager.getBOrderById (selectedOrderId));
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Actualizar tabla de Operaciones
	 */
	public void updateOperations() 
	{
		tabbedOrder.updateOperations();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Actualiza el cliente
	 */
	private void updateCurrentClient ()
	{
		if (currentClient != null)
		{
			setSelectedClientById (currentClient.getId ());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta el cliente seleccionado por DNI
	 * 
	 * @param clientDNI	DNI del cliente
	 */
	private void setSelectedClientById (int idClient) 
	{
		if (idClient > RAManager.NO_ID)
		{
			reloadClient (idClient);
		}
		else
		{
			if (currentClient != null)
			{
				try 
				{
					BClient client = rAManager.getBClientByDNI(currentClient.getNIF());
					if (client == null)
					{
						toolBarOrder.setClientText(null);
						panelEntities.updateSelectedClient(null);
						currentClient = null;
					}
					else
					{
						reloadClient (currentClient.getId());
					}
				} 
				catch (bll.RAManagerException e) 
				{
					rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
					gui.ValueChecks.showExceptionMessage(this, e);
				}
			}
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Recarga el cliente según su DNI
	 * 
	 * @param clientDNI	DNI del cliente
	 */
	private void reloadClient (int idClient)
	{
		BClient client = null;
		
		try 
		{
			client = rAManager.getBClientById (idClient);
			toolBarOrder.setClientText (client);
			panelEntities.updateSelectedClient(client);
		
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
		}
		
		// mismo cliente que el anteriormente seleccionado
		if (currentClient != null && client != null && currentClient.getNIF().equals(client.getNIF()))
		{
			if (currentOrder != null)
			{
				updateCurrentdOrder (currentOrder.getId());
			}
			else
			{
				updateSelectedOrder (client.getFirstBOrder());
			}
			
		}
		else
		{
			updateSelectedOrder (client == null ? null : client.getFirstBOrder());
		}
		
		currentClient = client;
		
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 *  Actualiza el cliente actual
	 * @return Cliente actualizado
	 */
	public BClient refreshCurrentClient()
	{
		if (currentClient != null)
		{
			try 
			{
				currentClient = rAManager.getBClientByDNI(currentClient.getNIF());
			} 
			catch (bll.RAManagerException e) 
			{
				rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
				gui.ValueChecks.showExceptionMessage(this, e);
			}
		}
		
		return currentClient;
	}
	
	// --------------------------------------------------------------------------------------------
	// IDIOMAS
	// --------------------------------------------------------------------------------------------

	// --------------------------------------------------------------------------------------------
	/***
	 * Aplicar diferentes idiomas al formulario
	 */
	public void applyLanguage ()
	{
		lblStatus.setText(RAConfig.getStatus (login));
		
		mainFrameMenuBar.applyLanguage ();
		panelEntities.applyLanguage ();
		toolBarOrder.applyLanguage ();
		tabbedOrder.applyLanguage ();
	}
	
	// --------------------------------------------------------------------------------------------
	// GETTERS
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el objeto de idioma
	 * 
	 * @return Objeto de idioma
	 */
	public Language getLanguage ()
	{
		return language;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el gestor de la aplicación
	 * 
	 * @return Gestor de la aplicación
	 */
	public RAManager getRAManager ()
	{
		return rAManager;
	}

	
	public void setCurrentOrder (BOrder order)
	{
		currentOrder = order;
	}
	
	/**
	 * @return the currentOrder
	 */
	public BOrder getCurrentOrder() {
		return currentOrder;
	}

	/**
	 * @return the config
	 */
	public RAConfig getConfig() {
		return config;
	}

	/**
	 * @return the login
	 */
	public BEmployee getLogin ()
	{
		return login;
	}
	
	/**
	 * @return the currentClient
	 */
	public BClient getCurrentClient ()
	{
		return currentClient;
	}

}
