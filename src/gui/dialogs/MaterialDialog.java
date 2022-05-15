package gui.dialogs;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;

import bll.BMaterial;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;

// ------------------------------------------------------------------------------------------------
/***
 * Diálogo de gestión de materiales
 * 
 * @author G1
 *
 */
public class MaterialDialog extends GenericEntityDialog <BMaterial>
{

	private ValueChecks valueChecks;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el diálogo de materiales
	 * 
	 * @param mainFrame		Formulario principal de la aplicación
	 * @param list			Lista de materiales para cargar en la tabla
	 * @param windowBounds	
	 * @throws RAManagerException 
	 */
	public MaterialDialog (java.awt.Window owner, RAManager rAManager, Language language) throws RAManagerException 
	{
		super (owner, rAManager, language, language.getMaterialColumnNames(), rAManager.getAllBMaterials (""), language.get ("materialTitle"));
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
	 */
	@Override
	protected void initTableDialog() 
	{
		Rectangle bounds = getBounds ();
		bounds.height -= 25;
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
				getTextFields ()[BMaterial.COLUMN_ID_PROVIDER].setText("" + idProvider);
			}
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			gui.ValueChecks.showExceptionMessage(this, e);
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
				ValueChecks.showExceptionMessage (this, e);
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
		JTextField textFieldIdProvider	= getTextFields ()[BMaterial.COLUMN_ID_PROVIDER];
		
		if (selectedObjectId > RAManager.NO_ID)
		{
			material.setId(selectedObjectId);
		}

		if (valueChecks.isValidTextField(textFieldName))
		{
			material.setName(textFieldName.getText());
			
			if (valueChecks.isValidTextField(textFieldDescription))
			{
				material.setDescription(textFieldDescription.getText());
				
				if (valueChecks.rangeOfMaterialPriceApplied(textFieldUnitPrice.getText()))
				{
					material.setUnitPrice(Double.parseDouble(textFieldUnitPrice.getText ()));
					
					try
					{
						int idProvider = Integer.parseInt (textFieldIdProvider.getText ());
						material.setIdProvider(idProvider);

						selectedObjectId = material.getId();
						returnMaterial = material;
					}
					catch (NumberFormatException e)
					{
						valueChecks.integerFormatError(textFieldIdProvider.getText());
					}
				}
			}
		}
	
		setLockChanged (false);
		
		return returnMaterial;
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
			gui.ValueChecks.showExceptionMessage(this, e);
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
			ValueChecks.showExceptionMessage(this, e);
		}
	}
	

}
