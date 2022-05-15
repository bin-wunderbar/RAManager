package gui.dialogs;

import java.awt.Rectangle;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bll.BMaterial;
import bll.BProvider;
import bll.BVehicle;
import bll.IRAObject;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;
import gui.controls.DBTextField;

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo de gestión de materiales
 * 
 * @author G1
 *
 */
@SuppressWarnings("serial")
public class MaterialDialog extends GenericEntityDialog <BMaterial>
{

	private ValueChecks valueChecks;
	private DBTextField <BProvider> dbTextFieldProvider;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa un dialogo de materiales
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @throws RAManagerException
	 */
	public MaterialDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getMaterialColumnNames(), rAManager.getAllBMaterials (""), language.get ("materialTitle"));
		
		panelFieldsRebuild();
		
		setVisible (true);
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el dialogo de materiales
	 */
	@Override
	protected void initTableDialog() 
	{
		Rectangle bounds = getBounds ();
		bounds.height += 50;
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
				dbTextFieldProvider.setEntity(rAManager.getBProviderById (idProvider));
				setChanged (true);
			}
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage(this, language, e);
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
				ValueChecks.showExceptionMessage (this, language, e);
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
		
		if (selectedObjectId > RAManager.NO_ID)
		{
			material.setId(selectedObjectId);
		}

		if (valueChecks.isValidTextField(textFieldName, columnNames[BMaterial.COLUMN_NAME]))
		{
			material.setName(textFieldName.getText());
			
			if (valueChecks.isValidTextField(textFieldDescription, columnNames[BMaterial.COLUMN_DESCRIPTION]))
			{
				material.setDescription(textFieldDescription.getText());
				
				if (valueChecks.rangeOfMaterialPriceApplied(textFieldUnitPrice.getText()))
				{
					material.setUnitPrice(Double.parseDouble(textFieldUnitPrice.getText ()));
					

					if (valueChecks.isSelectedObject (dbTextFieldProvider.getEntity(), columnNames[BMaterial.COLUMN_ID_PROVIDER]))
					{
						material.setIdProvider(dbTextFieldProvider.getEntity ().getId());

						selectedObjectId = material.getId();
						returnMaterial = material;
					}
				}
			}
		}
	
		setLockChanged (false);
		
		return returnMaterial;
	}
	
	// --------------------------------------------------------------------------------------------
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
			gui.ValueChecks.showExceptionMessage(this, language, e);
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
			ValueChecks.showExceptionMessage(this, language, e);
		}
	}
	

}
