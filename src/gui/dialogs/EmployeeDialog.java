package gui.dialogs;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bll.BEmployee;
import bll.BRole;
import bll.IRAObject;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;
import gui.controls.RAComboBox;

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo de gestión de empleados
 * 
 * @author G1
 *
 */
@SuppressWarnings("serial")
public class EmployeeDialog extends GenericEntityDialog <BEmployee>
{
	private ValueChecks valueChecks;
	private JPasswordField passwordField;
	private RAComboBox <BRole> rAComboBoxRole;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo de empleado
	 *  
	 * @param owner					- Ventana propietaria
	 * @param rAManager				- Gestor de datos
	 * @param language				- Objeto de idioma
	 * @throws RAManagerException
	 */
	public EmployeeDialog(java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getEmployeeColumnNames(), rAManager.getAllBEmployees (""), language.get ("employeeDialog"));
		
		setVisible (true);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	/**
	 * Inicializa el dialogo de empleado
	 *  
	 * @param owner					- Ventana propietaria
	 * @param rAManager				- Gestor de datos
	 * @param language				- Objeto de idioma
	 * @param employees				- Lista de empleados
	 * @throws RAManagerException
	 */
	public EmployeeDialog(java.awt.Window owner, RAManager rAManager, Language language, ArrayList <BEmployee> employees) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getEmployeeColumnNames(), employees, language.get ("employeeDialog"));
		
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	protected void initTableDialog() 
	{
		Rectangle bounds = getBounds ();
		
		bounds.height += 150;
		
		valueChecks = new ValueChecks (this, language);
		panelFieldsRebuild ();
		
		setBounds (bounds);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Reconstruye el panel de campos de texto
	 */
	private void panelFieldsRebuild ()
	{
		passwordField 	= new JPasswordField ();
		rAComboBoxRole	= new RAComboBox <> ();
		
		try 
		{
			rAComboBoxRole.loadItems(rAManager.getAllBRoles(""));
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, language, e);
		}

		
		getPanelFields ().removeAll ();
		
		JTextField[] textFields = getTextFields ();
		JPanel panelFields = getPanelFields ();
		
		for (int i = 0; i < textFields.length; i++)
		{
			panelFields.add (new JLabel (columnNames[i]));
			switch (i)
			{
				case BEmployee.COLUMN_PASSWORD:
					panelFields.add (passwordField);
					break;
					
				case BEmployee.COLUMN_ROLE:
					panelFields.add (rAComboBoxRole);
					break;
					
				default:
					panelFields.add (textFields[i]);
			}
		}

		setTextFieldsDefaults ();		
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	protected void deleteEvent (ArrayList <Integer> ids) 
	{
		try {
			rAManager.deleteEmployeesByIds (ids);
		} catch (bll.RAManagerException e) {
			ValueChecks.showExceptionMessage(this, language, e);
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve cierto si el empleado que ha inciado sesion no se encuentra en la lista
	 *  
	 * @param login 	- Empleado que ha iniciado sesion
	 * @param ids 		- Lista de ids 
	 * @return Cierto si el empleado no esta en la lista
	 */
	public boolean employeesIsntLogin (BEmployee login, ArrayList <Integer> ids)
	{
		for (int employeeId : ids)
		{
			if (employeeId == login.getId ())
			{
				JOptionPane.showMessageDialog (this, 
						language.get ("loginCannotRemoved"), 
						language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
				return false;
			}

		}
		
		return true;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public void setTextFieldsOnEditEvent (IRAObject iRAObject)
	{
		BEmployee employee = (BEmployee)iRAObject;
		
		for (int i = 0; i < columnNames.length; i++)
		{
			switch (i)
			{
				case BEmployee.COLUMN_PASSWORD:
					passwordField.setText(employee.getPassword());
					break;
					
				case BEmployee.COLUMN_ROLE:
					rAComboBoxRole.selectById (employee.getIdRole());
					break;
					
				default:
					getTextFields()[i].setText("" + employee.getValue(i));
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Crear un empleado nuevo a partir de los campos del formulario
	 * 
	 * @return Devuelve el empleado verificado o <strong>null</strong> en caso fallo en la verificación
	 */
	private BEmployee buildEmployee ()
	{
		setLockChanged (true);
		
		BEmployee returnEmployee = null;
		BEmployee employee = new BEmployee ();
	
		JTextField textFieldDNI = getTextFields()[BEmployee.COLUMN_NIF];
		JTextField textFieldName = getTextFields ()[BEmployee.COLUMN_NAME];
		JTextField textFieldSurnames = getTextFields ()[BEmployee.COLUMN_SURNAMES];
		JTextField textFieldProvince = getTextFields ()[BEmployee.COLUMN_PROVINCE];
		JTextField textFieldDirection = getTextFields ()[BEmployee.COLUMN_DIRECTION];
		JTextField textFieldEmail = getTextFields ()[BEmployee.COLUMN_EMAIL];
		JTextField textFieldPhone = getTextFields ()[BEmployee.COLUMN_PHONE];

		if (selectedObjectId > RAManager.NO_ID)
		{
			employee.setId(selectedObjectId);
		}
		
		if (valueChecks.isValidNIF(textFieldDNI, columnNames[BEmployee.COLUMN_NIF]))
		{
			employee.setNiF(textFieldDNI.getText());
			
			if (valueChecks.isValidTextField (textFieldName, columnNames[BEmployee.COLUMN_NAME]))
			{
				employee.setName(textFieldName.getText ());
			
				if (valueChecks.isValidTextField(textFieldSurnames, columnNames[BEmployee.COLUMN_SURNAMES]))
				{
					employee.setSurnames(textFieldSurnames.getText());
					
					if (valueChecks.isValidTextField(textFieldProvince, columnNames[BEmployee.COLUMN_PROVINCE]))
					{
						employee.setProvince(textFieldProvince.getText ());
						
						
						if (valueChecks.isValidTextField(textFieldDirection, columnNames[BEmployee.COLUMN_DIRECTION]))
						{
							employee.setDirection(textFieldDirection.getText());
							
							if (valueChecks.isValidEmail (textFieldEmail, columnNames[BEmployee.COLUMN_EMAIL]))
							{
								employee.setEmail(textFieldEmail.getText ());
								
								if (valueChecks.isValidPhone (textFieldPhone, columnNames[BEmployee.COLUMN_PHONE]))
								{
									employee.setPhone(textFieldPhone.getText ());
									
									if (valueChecks.isValidPassword (passwordField, columnNames[BEmployee.COLUMN_PASSWORD]))
									{
										employee.setPassword(new String (passwordField.getPassword()));
										BRole role = (BRole)rAComboBoxRole.getSelectedItem();
										employee.setRole(role);
										employee.setIdRole(role.getId());

										selectedObjectId = employee.getId();
										returnEmployee = employee;
									}
								} 
							} 
						}
					} 
				} 
			} 
		} 
		
		setLockChanged (false);
		
		return returnEmployee;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Guarda los campos como un cliente nuevo
	 */
	protected void saveFields ()
	{
		BEmployee employee = buildEmployee ();

		if (employee != null)
		{
			try {
				rAManager.save (employee);
				refreshTable ();
				setChanged (false);
			} catch (bll.RAManagerException exception) {
				ValueChecks.showExceptionMessage(this, language, exception);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta los campos de texto con valores por defecto
	 */
	public void setTextFieldsDefaults ()
	{
		setLockChanged (true);
		
		if (rAManager.getActiveLogin().isPermissionWrite())
		{
			JTextField[] textFields = getTextFields ();
			
			for (int i = 0; i < textFields.length; i++)
			{
				switch (i)
				{
					case BEmployee.COLUMN_EMAIL: 	textFields[i].setText("email@domain.net"); break;
					case BEmployee.COLUMN_PHONE: 	textFields[i].setText("99111222333");break;
				}
			}
		}
		else
		{
			textFieldsClean ();
			textFieldsReadOnly ();
			lockButtons ();
		}
		
		setLockChanged (false);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Actualiza los datos de la tabla
	 */
	protected void refreshTable (String filter)
	{
		try 
		{
			loadItems (rAManager.getAllBEmployees (filter));
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, language, e);
		}

	}


}
