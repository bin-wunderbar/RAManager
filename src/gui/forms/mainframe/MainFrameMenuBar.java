package gui.forms.mainframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import gui.Language;

// ------------------------------------------------------------------------------------------------
/***
 * Barra de menu con todas las operaciones del formulario principal
 * @author G1
 *
 */
public class MainFrameMenuBar extends JMenuBar 
{
	private Language language;
	
	private JMenu menuFile;
	private JMenuItem menuItemExit;
	private JMenuItem menuItemPrint;
	private JMenu menuManagement;
	private JMenuItem menuItemOrderClient;
	private JMenuItem menuItemVehicle;
	private JMenuItem menuItemClient;
	private JMenuItem menuItemOperationAdd;
	private JMenuItem menuItemOperationDelete;
	private JMenu menuPreferences;
	private JMenuItem menuItemPasswordChange;
	private JMenuItem menuItemConfiguration;
	private JMenu menuHelp;
	private JMenuItem menuItemAbout;
	private JMenuItem menuItemProvider;
	private JMenuItem menuItemMaterial;
	private JMenuItem menuItemRole;
	private JMenuItem menuItemService;
	private JMenuItem menuItemEmployee;
	private JMenuItem menuItemOrderEmployee;
	private JMenuItem menuItemOrderAll;

	// --------------------------------------------------------------------------------------------
	/***
	 * Constructor de la barra de menu e Implementacion de la truduccion en el formulario
	 * 
	 * @param mainFrame	Formulario principal de la aplicación
	 * @param language	Objeto de idioma
	 */
	public MainFrameMenuBar (MainFrame mainFrame, Language language)
	{
		this.language = language;
		
		menuFile = new JMenu(language.get ("menuFile"));
		add(menuFile);
		
		menuItemExit = new JMenuItem(language.get ("menuItemExit"));
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.windowClosing ();
			}
		});
		
		menuItemPrint = new JMenuItem(language.get ("menuPrint"));
		menuItemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		menuItemPrint.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/printer.png")));
		menuItemPrint.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent) {
				mainFrame.showPrintDialog();
			}
		});
		
		menuFile.add(menuItemPrint);
		menuFile.addSeparator();
		menuFile.add(menuItemExit);
		
		menuManagement = new JMenu(language.get ("menuManagement"));
		add(menuManagement);

		menuItemRole = new JMenuItem(language.get ("roleTitle"));
		menuItemRole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showRoleDialog ();
			}
		});
		menuItemRole.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/role.png")));
		menuManagement.add(menuItemRole);

		
		menuManagement.addSeparator();
		menuItemProvider = new JMenuItem(language.get ("providerTitle"));
		menuItemProvider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showProviderDialog ();
			}
		});
		menuItemProvider.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/provider.png")));
		menuManagement.add(menuItemProvider);

		
		menuItemMaterial = new JMenuItem(language.get ("materialTitle"));
		menuItemMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showMaterialDialog ();
			}
		});
		menuItemMaterial.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/material.png")));
		menuManagement.add(menuItemMaterial);
		
		menuItemService = new JMenuItem(language.get ("serviceTitle"));
		menuItemService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showServiceDialog ();
			}
		});
		menuItemService.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/service.png")));
		menuManagement.add(menuItemService);
				
		menuManagement.addSeparator();
		
		menuItemVehicle = new JMenuItem(language.get ("vehicleDialogTitle"));
		menuItemVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showVehicleDialog ();
			}
		});
		menuItemVehicle.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/vehicle_dialog.png")));
		menuManagement.add(menuItemVehicle);

		menuItemClient = new JMenuItem (language.get ("clientDialog"));
		menuItemClient.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent) {
				mainFrame.showClientDialog();
			}
		});
		menuItemClient.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/customer.png")));
		menuManagement.add(menuItemClient);

		
		
		menuItemEmployee = new JMenuItem (language.get ("employeeDialog"));
		menuItemEmployee.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent) {
				mainFrame.showEmployeeDialog();
			}
		});
		menuItemEmployee.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/employee.png")));
		menuManagement.add(menuItemEmployee);
		
		menuManagement.addSeparator();

		menuItemOrderAll = new JMenuItem(language.get ("menuItemOrdersAll"));
		menuItemOrderAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showOrderAllDialog ();
			}
		});
		menuItemOrderAll.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/order_add.png")));
		menuManagement.add(menuItemOrderAll);
		
		menuItemOrderEmployee = new JMenuItem(language.get ("menuItemOrdersByEmployee"));
		menuItemOrderEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showOrderEmployeeDialog ();
			}
		});
		menuItemOrderEmployee.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/order_add.png")));
		menuManagement.add(menuItemOrderEmployee);

		
		menuManagement.addSeparator();
		
		menuItemOrderClient = new JMenuItem(language.get ("menuItemOrdersByClient"));
		menuItemOrderClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showOrderClientDialog ();
			}
		});
		menuItemOrderClient.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/order_add.png")));
		menuManagement.add(menuItemOrderClient);

		
		menuItemOperationAdd = new JMenuItem(language.get ("actionAdd"));
		menuItemOperationAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showNewOperationDialog ();
			}
		});
		menuItemOperationAdd.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/add.png")));
		menuItemOperationAdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		menuManagement.add(menuItemOperationAdd);
		
		menuItemOperationDelete = new JMenuItem(language.get ("actionDelete"));
		menuItemOperationDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.deleteOperations();
			}
		});
		menuItemOperationDelete.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/delete.png")));
		menuItemOperationDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
		menuManagement.add(menuItemOperationDelete);
		
		menuPreferences = new JMenu(language.get ("menuPreferences"));
		add (menuPreferences);
		
		menuItemPasswordChange = new JMenuItem (language.get ("menuItemPasswordChange"));
		menuItemPasswordChange.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e)
			{
				mainFrame.showPasswordChangeDialog ();
			}
		});
		menuPreferences.add (menuItemPasswordChange);
		
		menuItemConfiguration = new JMenuItem(language.get ("menuItemConfiguration"));
		menuItemConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				mainFrame.showConfigurationDialog ();
			}
		});
		menuItemConfiguration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		menuPreferences.add(menuItemConfiguration);
		
		menuHelp = new JMenu(language.get ("menuHelp"));
		add (menuHelp);
		
		menuItemAbout = new JMenuItem(language.get ("menuItemAbout"));
		menuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showAboutDialog ();
			}
		});
		
		menuHelp.add(menuItemAbout);
	}

	// --------------------------------------------------------------------------------------------
	// IDIOMA
	// --------------------------------------------------------------------------------------------
	
	// ---------------------------------------------------------------------------------------------------
	/***
	 * Aplicar diferentes idiomas
	 */
	public void applyLanguage() 
	{
		menuFile.setText (language.get ("menuFile"));
		menuItemExit.setText (language.get ("menuItemExit"));
		menuItemPrint.setText (language.get ("menuPrint"));
		menuManagement.setText(language.get ("menuManagement"));
		menuItemRole.setText (language.get ("roleTitle"));
		menuItemProvider.setText (language.get ("providerTitle"));
		menuItemMaterial.setText (language.get ("materialTitle"));
		menuItemOrderClient.setText(language.get ("orderAdd"));
		menuItemClient.setText (language.get ("clientDialog"));
		menuItemEmployee.setText (language.get ("employeeDialog"));
		menuItemService.setText (language.get ("serviceTitle"));
		menuItemVehicle.setText (language.get ("vehicleDialogTitle"));
		menuItemOperationAdd.setText(language.get ("actionAdd"));
		menuItemOperationDelete.setText(language.get ("actionDelete"));
		menuItemOrderEmployee.setText (language.get ("menuItemOrdersByEmployee"));
		menuItemOrderAll.setText (language.get ("menuItemOrdersAll"));		
		menuPreferences.setText(language.get ("menuPreferences"));
		menuItemPasswordChange.setText (language.get ("menuItemPasswordChange"));
		menuItemConfiguration.setText(language.get ("menuItemConfiguration"));
		menuHelp.setText(language.get ("menuHelp"));
		menuItemAbout.setText(language.get ("menuItemAbout"));		
	}
	
}
