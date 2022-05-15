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
public class EmployeeDialog extends GenericEntityDialog <BEmployee>
{
	private ValueChecks valueChecks;
	private JPasswordField passwordField;
	private RAComboBox <BRole> rAComboBoxRole;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo
	 * 
	 * @param mainFrame		Formulario principal de la aplicacíon
	 * @param list			Lista de empleados
	 * @param windowBounds	Rectángulo con la posición del formulario invocador
	 * @throws RAManagerException 
	 */
	public EmployeeDialog(java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getEmployeeColumnNames(), rAManager.getAllBEmployees (""), language.get ("employeeDialog"));
		
		setVisible (true);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	public EmployeeDialog(java.awt.Window owner, RAManager rAManager, Language language, ArrayList <BEmployee> employees) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getEmployeeColumnNames(), employees, language.get ("employeeDialog"));
		
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo de tabla
	 */
	@Override
	protected void initTableDialog() 
	{
		Rectangle bounds = getBounds ();
		
		bounds.height += 125;
		
		valueChecks = new ValueChecks (this, language);
		panelFieldsRebuild ();
		
		setBounds (bounds);
	}

	// --------------------------------------------------------------------------------------------
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
			ValueChecks.showExceptionMessage(this, e);
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
	/***
	 * Elimina los empleados seleccionados
	 */
	@Override
	protected void deleteEvent (ArrayList <Integer> ids) 
	{
		try {
			rAManager.deleteEmployeesByIds (ids);
		} catch (bll.RAManagerException e) {
			ValueChecks.showExceptionMessage(this, e);
		}
	}

	// --------------------------------------------------------------------------------------------
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
		
		if (valueChecks.isValidNIF(textFieldDNI))
		{
			employee.setNiF(textFieldDNI.getText());
			
			if (valueChecks.isValidTextField (textFieldName))
			{
				employee.setName(textFieldName.getText ());
			
				if (valueChecks.isValidTextField(textFieldSurnames))
				{
					employee.setSurnames(textFieldSurnames.getText());
					
					if (valueChecks.isValidTextField(textFieldProvince))
					{
						employee.setProvince(textFieldProvince.getText ());
						
						
						if (valueChecks.isValidTextField(textFieldDirection))
						{
							employee.setDirection(textFieldDirection.getText());
							
							if (valueChecks.isValidEmail (textFieldEmail))
							{
								employee.setEmail(textFieldEmail.getText ());
								
								if (valueChecks.isValidPhone (textFieldPhone))
								{
									employee.setPhone(textFieldPhone.getText ());
									
									if (valueChecks.isValidPassword (passwordField))
									{
										employee.setPassword(new String (passwordField.getPassword()));
										BRole role = (BRole)rAComboBoxRole.getSelectedItem();
										employee.setRole(role);
										employee.setIdRol(role.getId());

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
				
				if (exception.getErrorCode() == bll.RAManagerException.ERROR_UNIQUE)
				{
					JOptionPane.showMessageDialog(this, language.get ("errorEmployeeNIFUNIQUE"), language.get("errorText"), JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					ValueChecks.showExceptionMessage(this, exception);
				}
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
			ValueChecks.showExceptionMessage(this, e);
		}

	}


}
