package gui.forms.mainframe;

import java.awt.BorderLayout;


import java.awt.FlowLayout;
import java.io.IOException;
<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> V3.1-alertas

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
<<<<<<< HEAD
=======
import gui.ValueChecks;
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
import sgbdoo.Alert;
import sgbdoo.AlertsDialog;
import sgbdoo.AlertsManager;
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
	private AlertsManager alertsManager;
>>>>>>> V3.1-alertas
	
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el formulario principal
	 * 
	 * @param rAManager		Gestor operativo de la aplicaci??n
	 * @param config		Configuraci??n de la aplicaci??n
	 * @param language		Objeto de idioma de la aplicaci??n
	 */
	public MainFrame (RAManager rAManager, RAConfig config, Language language) 
	{
		mainFrame		= this;
		this.rAManager	= rAManager;
		this.language	= language;
		this.config		= config; 
		login 			= rAManager.getActiveLogin();
<<<<<<< HEAD
=======
		
		alertsManager 	= new AlertsManager (config);
>>>>>>> V3.1-alertas

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
<<<<<<< HEAD
=======
			
			public void windowOpened(WindowEvent e) {
				mainFrame.expiredAlertsCheck ();
			}
>>>>>>> V3.1-alertas
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
		
<<<<<<< HEAD
		lblStatus			= new JLabel (RAConfig.getStatus (login));
=======
		lblStatus			= new JLabel (RAConfig.getStatus (login, rAManager.getInfoDB()));
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
	
	// --------------------------------------------------------------------------------------------
=======
	/**
	 * Muestra el dialogo de alertas de usuario 
	 */
	public void showAlertsDialog() {
		
		try 
		{
			new AlertsDialog (this, rAManager, alertsManager, alertsManager.getAlerts (""), false, language);
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, language, e);
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Muestra el dialogo de configuracion 
	 */
>>>>>>> V3.1-alertas
	public void showConfigurationDialog() 
	{
		new ConfigurationDialog (mainFrame, mainFrame.getRAManager(), mainFrame.getConfig (), language);
		panelEntities.setCompanyFields();
		applyLanguage ();
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de roles
	 */
>>>>>>> V3.1-alertas
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

<<<<<<< HEAD
	// --------------------------------------------------------------------------------------------	
=======
	// --------------------------------------------------------------------------------------------
	/**
	 * Muestra el dialogo de roles
	 */
>>>>>>> V3.1-alertas
	public void showProviderDialog() 
	{
		try 
		{
			new ProviderDialog (mainFrame, rAManager, language);
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

	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de materiales
	 */
>>>>>>> V3.1-alertas
	public void showMaterialDialog() 
	{
		try 
		{
			new MaterialDialog (mainFrame, rAManager, language);
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

	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de servicios 
	 */
>>>>>>> V3.1-alertas
	public void showServiceDialog() 
	{
		try 
		{
			new ServiceDialog (mainFrame, rAManager, language);
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
<<<<<<< HEAD
			gui.ValueChecks.showExceptionMessage(this, e);
=======
			gui.ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
			gui.ValueChecks.showExceptionMessage(this, e);
=======
			gui.ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
		}
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de empleados 
	 */
>>>>>>> V3.1-alertas
	public void showEmployeeDialog() 
	{
		try 
		{
			new EmployeeDialog (this, rAManager, language);
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
	 * Mostrar el di??logo de operaciones en modo modificaci??n
	 * @param operationId	Identificador de operaci??n a modificar
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
<<<<<<< HEAD
				gui.ValueChecks.showExceptionMessage(this, e);
=======
				gui.ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
			}
		}
		else
		{
			showPermissionDeniedDialog ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * A??adir nueva operacion al JDialog de Operaciones
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
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de ordenes por empleado 
	 */
>>>>>>> V3.1-alertas
	public void showOrderEmployeeDialog() 
	{
		new OrderByLoginForm (this, rAManager, login, config, language);
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de todas las ordenes 
	 */
>>>>>>> V3.1-alertas
	public void showOrderAllDialog() 
	{
		try 
		{
			new OrderDialog (mainFrame, rAManager, language);
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
<<<<<<< HEAD
=======
	/**
	 * Muestra el dialogo de cambio de password 
	 */
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
	/**
	 * Gestiona el cerrado de ventana
	 */
>>>>>>> V3.1-alertas
	protected void windowClosing ()
	{
		rAManager.close ();
		dispose ();
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Verifica si hay alertas de usuario que hayan vencido y notifica al usuario
	 */
	protected void expiredAlertsCheck ()
	{
		try 
		{
			ArrayList <Alert> expiredAlerts = alertsManager.getExpiredAlerts("");
			if (expiredAlerts.size() > 0)
			{
				if (JOptionPane.showConfirmDialog(this, language.get ("expiredAlerts"), language.get ("warningText"), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
				{
					new AlertsDialog (this, rAManager, alertsManager, expiredAlerts, true, language);
				}
			}
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, language, e);
		}
	}
	
	// --------------------------------------------------------------------------------------------
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
			gui.ValueChecks.showExceptionMessage(this, e);
=======
			gui.ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
					gui.ValueChecks.showExceptionMessage(this, e);
=======
					gui.ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
				}
			}
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Recarga el cliente seg??n su DNI
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
<<<<<<< HEAD
			gui.ValueChecks.showExceptionMessage(this, e);
=======
			gui.ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
				gui.ValueChecks.showExceptionMessage(this, e);
=======
				ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
			}
		}
		
		return currentClient;
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
	// IDIOMAS
=======
	// IDIOMA
>>>>>>> V3.1-alertas
	// --------------------------------------------------------------------------------------------

	// --------------------------------------------------------------------------------------------
	/***
	 * Aplicar diferentes idiomas al formulario
	 */
	public void applyLanguage ()
	{
<<<<<<< HEAD
		lblStatus.setText(RAConfig.getStatus (login));
=======
		lblStatus.setText(RAConfig.getStatus (login, rAManager.getInfoDB()));
>>>>>>> V3.1-alertas
		
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
	 * Devuelve el gestor de la aplicaci??n
	 * 
	 * @return Gestor de la aplicaci??n
	 */
	public RAManager getRAManager ()
	{
		return rAManager;
	}

<<<<<<< HEAD
	
=======
	/**
	 * ajusta la orden actual
	 * 
	 * @param order - Orden actual
	 */
>>>>>>> V3.1-alertas
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

<<<<<<< HEAD
=======


>>>>>>> V3.1-alertas
}
