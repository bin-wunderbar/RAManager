package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import bll.BEmployee;
import bll.BRole;
import bll.RAManager;
import gui.Language;
import gui.ValueChecks;
import gui.controls.ButtonNew;
import gui.controls.ButtonDelete;
import gui.controls.RATable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JToolBar;

/**
 * Diálogo para la administración de Roles y sus permisos 
 * @author G4
 *
 */
@SuppressWarnings("serial")
public class RoleDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private RATable <BRole> roleTable;
	private RAManager rAManager;
	private Language language;

	/**
	 * Inicializa el dialogo de gestion de roles
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 */
	public RoleDialog (JFrame owner, RAManager rAManager, Language language) {
		super (owner, true);
		
		initDialog (owner, rAManager, language);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA, modal configurable
	/**
	 * Inicializa el dialogo de gestion de roles
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param modal			- Especifica si el dialogo es modal
	 */	
	public RoleDialog (JFrame owner, RAManager rAManager, Language language, boolean modal) {
		super (owner, modal);
		
		initDialog (owner, rAManager, language);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo de gestion de roles
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 */
	private final void initDialog (JFrame owner, RAManager rAManager, Language language)
	{
		
		this.rAManager 		= rAManager;
		this.language		= language;
		
		setTitle (language.get ("roleTitle"));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				verifyWindowClosing ();
			}
		});
		
		setDefaultCloseOperation (HIDE_ON_CLOSE);
		
		Rectangle bounds = owner.getBounds ();

		setBounds(bounds.x + 50, bounds.y + 50, 848, 262);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton (language.get ("closeButton"));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancel ();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton (language.get ("save"));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						save ();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		JScrollPane scrollPane = new JScrollPane (roleTable = new RATable <> (language.getRoleColumnNames ()));
		roleTable.setColumnsClass(new Class<?>[] {String.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class});
		roleTable.setEditable (true);
		getContentPane ().add (scrollPane);
		{
			JToolBar toolBar = new JToolBar();
			{
				ButtonNew buttonAdd = new ButtonNew (language);
				buttonAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						newRol ();
					}
				});
				toolBar.add(buttonAdd);
			}
			{
				ButtonDelete buttonDelete = new ButtonDelete (language);
				buttonDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						deleteRole ();
					}
				});
				toolBar.add(buttonDelete);
			}
			
			getContentPane ().add (toolBar, BorderLayout.NORTH);
		}
		
		try 
		{
			roleTable.load(rAManager.getAllBRoles (""));
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
	 * Elimina un rol
	 */
	protected void deleteRole () 
	{
		int[] selectedRows = roleTable.getSelectedRows();
		
		if (selectedRows.length > 0)
		{
			if (JOptionPane.showConfirmDialog(this, 
					language.get ("deleteConfirm"), language.get ("delete"), JOptionPane.YES_NO_OPTION) 
					== JOptionPane.YES_OPTION)
			{
				ArrayList <Integer> ids = roleTable.getSelectedIds(selectedRows);
				
				for (int id : ids)
				{
					
					
					try {
						ArrayList<BEmployee> employees = rAManager.deleteRoleRestrict (id);
						
						if (!employees.isEmpty())
						{
							JOptionPane.showMessageDialog(this, 
									language.get ("deleteRestrictRole") + getEmployeesAsText (employees), 
									language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							roleTable.load(rAManager.getAllBRoles (""));
						}
						
					} catch (bll.RAManagerException e) {
						ValueChecks.showExceptionMessage(this, language, e);					
					} 
				}
			}
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una lista de empleados como texto
	 * 
	 * @param employees - Lista de empleados
	 * @return Texto formateado con la lista de empleados
	 */
	protected String getEmployeesAsText (ArrayList <BEmployee> employees)
	{
		String employeesText = "\n";
		
		for (BEmployee employee : employees)
		{
			employeesText += "    " + employee.getNIF() + ", " + employee.getName() + ", " + employee.getSurnames() + "\n";
		}
		
		return employeesText;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Crea un nuevo rol
	 */
	protected void newRol() 
	{
		BRole bRole = new BRole ();
		bRole.setName (language.get ("newRole"));
		roleTable.add (bRole);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Guarda los datos de roles
	 * 
	 */
	protected void save() 
	{
		if (roleTable.isDataChanged())
		{
			if (dataChecks ())
			{
				try {
					rAManager.save (roleTable.getList ());
					dispose ();
				} catch (bll.RAManagerException e) {
					ValueChecks.showExceptionMessage(this, language, e);
				}
			}
		}
		else
		{
			dispose ();
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Verifica que los datos son correctos
	 * 
	 * @return Cierto si los datos son correctos
	 */
	private boolean dataChecks() 
	{
		ArrayList <BRole> roles = roleTable.getList();
		
		int i = 0;
		for (BRole role : roles)
		{
			if (role.getName().isEmpty())
			{
				JOptionPane.showMessageDialog(this, language.get ("emptyTextField"), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			if (duplicateName (role.getName (), i)) 
			{
				JOptionPane.showMessageDialog(this, language.get ("duplicateNames"), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
				return false;
			}
			i++;
		}
		
		return true;
	}

	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve cierto si un nombre de rol ya existe
	 * 
	 * @param name		- Nombre del rol
	 * @param nameIndex	- Indice del nombre
	 * @return Cierto si el rol ya existe
	 */
	public boolean duplicateName (String name, int nameIndex)
	{
		int rowIndex = 0;
		for (BRole role : roleTable.getList())
		{
			if (rowIndex != nameIndex && role.getName().toLowerCase().equals(name.toLowerCase())) return true;
			rowIndex++;
		}
		
		return false;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Cierra el dialogo
	 */
	protected void cancel ()
	{
		verifyWindowClosing ();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Verifica si hay datos a guardar al cerrar la ventana
	 */
	protected void verifyWindowClosing ()
	{
		if (roleTable.isDataChanged())
		{
			switch (JOptionPane.showConfirmDialog(this, language.get ("saveChanges"), language.get ("save"), JOptionPane.YES_NO_CANCEL_OPTION))
			{
				case JOptionPane.OK_OPTION:
					save ();
					break;
				
				case JOptionPane.NO_OPTION:
					dispose ();
					break;
					
				case JOptionPane.CANCEL_OPTION:
				default:
					break;
			}
		}
		else
		{
			dispose ();
		}
	}
}
