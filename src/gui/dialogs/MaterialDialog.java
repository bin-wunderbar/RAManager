package gui.dialogs;

import java.awt.Rectangle;

<<<<<<< HEAD
=======

>>>>>>> V3.1-alertas
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

<<<<<<< HEAD
import javax.swing.JTextField;

import bll.BMaterial;
=======
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bll.BMaterial;
import bll.BProvider;
import bll.BVehicle;
import bll.IRAObject;
>>>>>>> V3.1-alertas
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;
<<<<<<< HEAD
=======
import gui.controls.DBTextField;
>>>>>>> V3.1-alertas

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo de gestión de materiales
 * 
 * @author G1
 *
 */
<<<<<<< HEAD
=======
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class MaterialDialog extends GenericEntityDialog <BMaterial>
{

	private ValueChecks valueChecks;
<<<<<<< HEAD
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo de materiales
	 * 
	 * @param mainFrame		Formulario principal de la aplicación
	 * @param list			Lista de materiales para cargar en la tabla
	 * @param windowBounds	
	 * @throws RAManagerException 
=======
	private DBTextField <BProvider> dbTextFieldProvider;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa un dialogo de materiales
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @throws RAManagerException
>>>>>>> V3.1-alertas
	 */
	public MaterialDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getMaterialColumnNames(), rAManager.getAllBMaterials (""), language.get ("materialTitle"));
<<<<<<< HEAD
		setVisible (true);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	public MaterialDialog (java.awt.Window owner, RAManager rAManager, Language language, ArrayList <BMaterial> materials) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getMaterialColumnNames(), materials, language.get ("materialTitle"));
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo de materiales
=======
		
		panelFieldsRebuild();
		
		setVisible (true);
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el dialogo de materiales
>>>>>>> V3.1-alertas
	 */
	@Override
	protected void initTableDialog() 
	{
		Rectangle bounds = getBounds ();
<<<<<<< HEAD
		bounds.height -= 25;
=======
		bounds.height += 50;
>>>>>>> V3.1-alertas
		setBounds (bounds);
		
		valueChecks = new ValueChecks (this, language);
		
		
		enableButton (BMaterial.COLUMN_ID_PROVIDER, new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				showProviderDialog ();
			}
		});	

		setTextFieldsDefaults ();
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	 /**
	  * Reconstruye el panel de campos de texto
	  */
	public void panelFieldsRebuild ()
	{
		dbTextFieldProvider 	= new DBTextField <> ();
		
		getPanelFields ().removeAll ();
		
		JTextField[] textFields = getTextFields ();
		JPanel panelFields = getPanelFields ();
		
		for (int i = 0; i < textFields.length; i++)
		{
			panelFields.add (new JLabel (columnNames[i]));
			switch (i)
			{
				case BMaterial.COLUMN_ID_PROVIDER:
					dbTextFieldProvider.setEditable(false);
					panelFields.add (dbTextFieldProvider);
					break;
					
				default:
					panelFields.add (textFields[i]);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
>>>>>>> V3.1-alertas
	/***
	 * Muestra el diálogo de gestión y selección de clientes
	 */
	private void showProviderDialog() 
	{
		try 
		{
			ProviderDialog providerDialog = new ProviderDialog (this, rAManager, language);
			int idProvider = providerDialog.getSelectedObjectId ();
			
			if (idProvider > RAManager.NO_ID)
			{
<<<<<<< HEAD
				getTextFields ()[BMaterial.COLUMN_ID_PROVIDER].setText("" + idProvider);
=======
				dbTextFieldProvider.setEntity(rAManager.getBProviderById (idProvider));
				setChanged (true);
>>>>>>> V3.1-alertas
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
	/***
	 * Guarda los datos de los campos como un registro de material nuevo
	 */
	protected void saveFields ()
	{
		BMaterial material = buildMaterial ();

		if (material != null)
		{
			try {
				rAManager.save (material);
				refreshTable ();
				setChanged (false);
			} catch (bll.RAManagerException e) {
<<<<<<< HEAD
				ValueChecks.showExceptionMessage (this, e);
=======
				ValueChecks.showExceptionMessage (this, language, e);
>>>>>>> V3.1-alertas
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Construye un objeto de tipo material a partir de los campos de texto del formulario
	 * 
	 * @return El material creado o <strong>null</strong> en caso de que las verificaciones fallen
	 */
	private BMaterial buildMaterial ()
	{
		setLockChanged (true);
		
		BMaterial returnMaterial = null;
		BMaterial material = new BMaterial ();
		
		JTextField textFieldName 		= getTextFields ()[BMaterial.COLUMN_NAME];
		JTextField textFieldDescription	= getTextFields ()[BMaterial.COLUMN_DESCRIPTION];
		JTextField textFieldUnitPrice	= getTextFields ()[BMaterial.COLUMN_UNIT_PRICE];
<<<<<<< HEAD
		JTextField textFieldIdProvider	= getTextFields ()[BMaterial.COLUMN_ID_PROVIDER];
=======
>>>>>>> V3.1-alertas
		
		if (selectedObjectId > RAManager.NO_ID)
		{
			material.setId(selectedObjectId);
		}

<<<<<<< HEAD
		if (valueChecks.isValidTextField(textFieldName))
		{
			material.setName(textFieldName.getText());
			
			if (valueChecks.isValidTextField(textFieldDescription))
=======
		if (valueChecks.isValidTextField(textFieldName, columnNames[BMaterial.COLUMN_NAME]))
		{
			material.setName(textFieldName.getText());
			
			if (valueChecks.isValidTextField(textFieldDescription, columnNames[BMaterial.COLUMN_DESCRIPTION]))
>>>>>>> V3.1-alertas
			{
				material.setDescription(textFieldDescription.getText());
				
				if (valueChecks.rangeOfMaterialPriceApplied(textFieldUnitPrice.getText()))
				{
					material.setUnitPrice(Double.parseDouble(textFieldUnitPrice.getText ()));
					
<<<<<<< HEAD
					try
					{
						int idProvider = Integer.parseInt (textFieldIdProvider.getText ());
						material.setIdProvider(idProvider);
=======

					if (valueChecks.isSelectedObject (dbTextFieldProvider.getEntity(), columnNames[BMaterial.COLUMN_ID_PROVIDER]))
					{
						material.setIdProvider(dbTextFieldProvider.getEntity ().getId());
>>>>>>> V3.1-alertas

						selectedObjectId = material.getId();
						returnMaterial = material;
					}
<<<<<<< HEAD
					catch (NumberFormatException e)
					{
						valueChecks.integerFormatError(textFieldIdProvider.getText());
					}
=======
>>>>>>> V3.1-alertas
				}
			}
		}
	
		setLockChanged (false);
		
		return returnMaterial;
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	@Override
	public void setTextFieldsOnEditEvent (IRAObject iRAObject)
	{
		BMaterial material = (BMaterial)iRAObject;
		
		for (int i = 0; i < columnNames.length; i++)
		{
			switch (i)
			{
				case BVehicle.COLUMN_ID_CLIENT:
					try {
						dbTextFieldProvider.setEntity(rAManager.getBProviderById(material.getIdProvider()));
					} catch (RAManagerException e) {
						rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
						ValueChecks.showExceptionMessage(this, language, e);
					}
					break;
					
				default:
					Object value = material.getValue(i);
					getTextFields()[i].setText ("" + (value == null ? "" : value));
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
>>>>>>> V3.1-alertas
	/***
	 * Actualiza la tabla a partir de los materiales procedentes de la base de datos
	 */
	protected void refreshTable (String filter)
	{
		try 
		{
			this.loadItems(rAManager.getAllBMaterials (filter));
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
				textFields[i].setText("");
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
	 * Elimina los registros seleccionados
	 */
	@Override
	protected void deleteEvent(ArrayList <Integer> ids) 
	{
		try {
			rAManager.deleteMaterialsByIds (ids);
		} catch (bll.RAManagerException e) {
<<<<<<< HEAD
			ValueChecks.showExceptionMessage(this, e);
=======
			ValueChecks.showExceptionMessage(this, language, e);
>>>>>>> V3.1-alertas
		}
	}
	

}
