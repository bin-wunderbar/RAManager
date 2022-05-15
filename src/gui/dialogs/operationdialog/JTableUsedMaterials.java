package gui.dialogs.operationdialog;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import bll.BMaterial;
import bll.BUsedMaterial;
import bll.RAManager;
import gui.Language;
import gui.ValueChecks;

// ------------------------------------------------------------------------------------------------
/***
 * Mantiene la tabla de histórico de materiales usados en el encargo
 * 
 * @author G1
 *
 */
@SuppressWarnings("serial")
public class JTableUsedMaterials extends JTable 
{
	public final static int COLUMN_ID 				= 0;
	public final static int COLUMN_ID_MATERIAL		= 1;
	public final static int COLUMN_NAME				= 2;
	public final static int COLUMN_UNITS			= 3;
	public final static int COLUMN_PRICE_APPLIED	= 4;

	private Language language;
	private DefaultTableModel tableModel;
	private String[] columnNames;
	private ValueChecks valueChecks;
	private RAManager rAManager;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa la tabla de materiales usados
	 * 
	 * @param language		Idioma a aplicar en la tabla
	 * @param rAManager		Sistema operativo de la aplicación
	 * @param valueChecks	Gestor de verificaciones de datos y sus rangos
	 */
	public JTableUsedMaterials (Language language, RAManager rAManager, ValueChecks valueChecks)
	{
		initTable (language, rAManager, valueChecks, new String[] {"Id", "Id Material", "Name", "Units", "Price applied"});
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	/***
	 * Inicializa la tabla de materiales usados
	 * 
	 * @param language		Idioma a aplicar en la tabla
	 * @param rAManager		Sistema operativo de la aplicación
	 * @param valueChecks	Gestor de verificaciones de datos y sus rangos
	 * @param columnNames	Matriz con los nombres de columnas
	 */
	public JTableUsedMaterials (Language language, RAManager rAManager, ValueChecks valueChecks, String[] columnNames)
	{
		initTable (language, rAManager, valueChecks, columnNames);
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa la tabla de materiales usados
	 * 
	 * @param language		Idioma a aplicar en la tabla
	 * @param rAManager		Sistema operativo de la aplicación
	 * @param valueChecks	Gestor de verificaciones de datos y sus rangos
	 * @param columnNames	Matriz con los nombres de columnas
	 */
	private final void initTable (Language language, RAManager rAManager, ValueChecks valueChecks, String[] columnNames)
	{
		this.rAManager		= rAManager;
		this.language		= language;
		this.valueChecks	= valueChecks;
		this.columnNames 	= columnNames;
		
		tableModel 			= getReadOnlyIdNameDataModel (columnNames);
		setModel (tableModel);
		
		tableModel.addTableModelListener(new TableModelListener() {
				    public void tableChanged(TableModelEvent tableModelEvent) {
				    	if (tableModelEvent.getFirstRow() >= 0 && tableModelEvent.getColumn() >= 0)
				    	{
					    	cellChanged (
					    			tableModelEvent.getFirstRow(),
					    			tableModelEvent.getColumn(),
					    			tableModel.getValueAt (tableModelEvent.getFirstRow(), tableModelEvent.getColumn())
					    			);
				    	}
				    }});
		
		applyLanguage ();	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Opera con cada celda cuando esta ha sufrido un cambio
	 * 
	 * @param row		Fila en la que se ha producido el cambio
	 * @param column	Columna en la que se ha producido el cambio
	 * @param value		Valor con los datos cambiados
	 */
	private void cellChanged (int row, int column, Object value)
	{
		switch (column)
		{
			case COLUMN_UNITS:
				
				if (!valueChecks.rangeOfMaterialUnits("" + value))
				{
					tableModel.setValueAt ("" + ValueChecks.MATERIAL_UNITS_MIN, row, column);
				}
				break;
				
			case COLUMN_PRICE_APPLIED:
				
				if (!valueChecks.rangeOfMaterialPriceApplied("" + value))
				{
					try 
					{
						BMaterial material = rAManager.getBMaterialById((int)tableModel.getValueAt(row, COLUMN_ID));
						String valueString = "" + ValueChecks.MATERIAL_PRICE_MIN; 
						if (material != null)
						{
							valueString = "" + material.getUnitPrice();
						}
						tableModel.setValueAt(valueString, row, column);
					} 
					catch (bll.RAManagerException e) 
					{
						rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
						ValueChecks.showExceptionMessage(null, language, e);
					}

				}
				
				break;
		}
	}
	
	// --------------------------------------------------------------------------------------------

	/***
	 * Devuelve un modelo de datos de tabla por defecto en solo lectura
	 * 
	 * @param columns	Lista de columnas de la tabla
	 * @return			Modelo de tabla de solo lectura
	 */
	private DefaultTableModel getReadOnlyIdNameDataModel (String[] columns)
	{
		return new DefaultTableModel (columns, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return column != COLUMN_ID && column != COLUMN_ID_MATERIAL && column != COLUMN_NAME ;
		    }
		};
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Agrega un material a la tabla 
	 * @param material	Material para agregar
	 */
	@SuppressWarnings("unchecked")
	public void addMaterial(BMaterial material) 
	{
		int indexOfRow = getIndexOfRowByMaterialId (material.getId()); 
		if (indexOfRow >= 0)
		{
			increaseMaterialUnits (indexOfRow, material, (Vector <Object>)(tableModel.getDataVector()).get(indexOfRow));
		}
		else
		{
			Object[] rowMaterial = new Object[] {RAManager.NO_ID, material.getId(), material.getName(),  "1.0", "" + material.getUnitPrice()};
			tableModel.addRow(rowMaterial);
		}
		
	}

	// --------------------------------------------------------------------------------------------

	/***
	 * Incrementa las unidades del material
	 * 
	 * @param indexOfRow	Indice de la fila donde incrementar
	 * @param material		Material para agregar a la lista
	 * @param vectorRow		Vector de datos de la fila
	 */
	private void increaseMaterialUnits (int indexOfRow, BMaterial material, Vector <Object> vectorRow)
	{
		Object[] rowMaterial = new Object[] {
				vectorRow.get (COLUMN_ID),
				vectorRow.get (COLUMN_ID_MATERIAL),
				vectorRow.get (COLUMN_NAME),
				vectorRow.get (COLUMN_UNITS),
				vectorRow.get (COLUMN_PRICE_APPLIED)
				};
		
		Double units = Double.parseDouble ("" + rowMaterial[COLUMN_UNITS]);
		if (units < ValueChecks.MATERIAL_UNITS_MAX)
		{
			units++;
			rowMaterial[COLUMN_UNITS] = "" + units;
			tableModel.removeRow(indexOfRow);
			tableModel.insertRow(indexOfRow, rowMaterial);		
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Obtiene el índice de fila según el <strong>id</strong> del material indicado
	 * 
	 * @param id	Identificador del material
	 * @return		Indice de la fila cuando es localizada.<br>
	 * -1 en caso de que no sea encontrada
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int getIndexOfRowByMaterialId (int id)
	{
		Integer idMaterial = id;
		
		Vector <Vector> dataVector = tableModel.getDataVector();
		int indexOfMaterial = 0;
		for (Vector vector : dataVector)
		{
			if (vector.get(COLUMN_ID_MATERIAL) == idMaterial)
			{
				return indexOfMaterial;
			}
			
			indexOfMaterial++;
		}
		
		return -1;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el nombre de un material por su identificador
	 * 
	 * @param idMaterial - Identificador del material
	 * @return El nombre del material
	 */
	private String getMaterialNameById (int idMaterial)
	{
		BMaterial material = null;
		
		try 
		{
			material = rAManager.getBMaterialById(idMaterial);
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage (null, language, e);
		}
		
		return material != null ? material.getName() : language.get ("delete") + idMaterial;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta la tabla con los materiales usados
	 * 
	 * @param usedMaterials	Objetos materiales para mostrar en la lista
	 */
	public void loadUsedMaterials (ArrayList <BUsedMaterial> usedMaterials)
	{
		clearData ();
		
		for (BUsedMaterial usedMaterial : usedMaterials)
		{
			addUsedMaterial (usedMaterial);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Agrega un registro de material usado a la tabla 
	 * @param usedMaterial	Material usado para agregar
	 */
	public void addUsedMaterial(BUsedMaterial usedMaterial) 
	{
		Object[] rowUsedMaterial = new Object[] {
				usedMaterial.getId(), 
				usedMaterial.getIdMaterial(), 
				getMaterialNameById(usedMaterial.getIdMaterial()),  
				"" + usedMaterial.getUnits(), 
				"" + usedMaterial.getUnitPriceApplied()
				};
		
		tableModel.addRow(rowUsedMaterial);
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve la lista de materiales usados
	 * 
	 * @return Lista con los materiales usados
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList <BUsedMaterial> getBUsedMaterials ()
	{
		ArrayList <BUsedMaterial> bUsedMaterials = new ArrayList <> ();
		
		Vector <Vector> dataVector = tableModel.getDataVector();
				
		for (Vector rowMaterial : dataVector)
		{
			int id = (int)rowMaterial.get (COLUMN_ID);
			
			/*
			 * id				- Si el ID ya existe se utiliza, en caso contrario se asigna un ID automático
			 * unidades			- Unidades del material asignado
			 * idMaterial		- ID del material asignado
			 * IdOperacion		- Sin ID, la operación que guarde los materiales se encarga de asignar su ID
			 * PrecioAplicado	- Precio del material asignado en esta operación
			 */
			
			BUsedMaterial bUsedMaterial = new BUsedMaterial (
					id <= RAManager.NO_ID ? RAManager.ID_NEW : id,
					Double.parseDouble ("" + rowMaterial.get (COLUMN_UNITS)),
					(int)rowMaterial.get (COLUMN_ID_MATERIAL),
					RAManager.NO_ID,
					Double.parseDouble ("" + rowMaterial.get(COLUMN_PRICE_APPLIED))
					);
			
			bUsedMaterials.add(bUsedMaterial);
		}
		
		return bUsedMaterials;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve los materiales seleccionados en formato de texto imprimible
	 * 
	 * @param selectedMaterials		Array con los indices de los materiales
	 * @return						Cadena de texto multilínea con los materiales seleccionados 
	 */
	@SuppressWarnings("unchecked")
	public String getItemsText(int[] selectedMaterials) 
	{
		String selectedMaterialsText = "\n\n";
		
		@SuppressWarnings("rawtypes")
		Vector <Vector> tableVector = tableModel.getDataVector();
		
		for (int i = 0; i < selectedMaterials.length; i++)
		{
			Vector <?> rowMaterial = tableVector.get(selectedMaterials[i]);
			selectedMaterialsText += "- " + rowMaterial.get(COLUMN_NAME) + ", " + rowMaterial.get(COLUMN_UNITS) + "\n";
		}
		
		
		return selectedMaterialsText + "\n";
	}

	// --------------------------------------------------------------------------------------------
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList <Integer> getSelectedRegisterIds (int[] selectedItems)
	{
		ArrayList <Integer> ids = new ArrayList <> ();
		
		Vector <Vector> dataVector = tableModel.getDataVector();
		
		for (int position : selectedItems)
		{
			int id = (int)(dataVector.get(position).get(COLUMN_ID));
			if (id > RAManager.NO_ID) ids.add(id);
		}
		
		return ids;
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Elimina los materiales seleccionados
	 * @param selectedMaterials	Array con los índices de los materiales
	 */
	public void removeMaterials(int[] selectedMaterials) 
	{
		for (int i = selectedMaterials.length - 1; i >= 0; i--)
		{
			tableModel.removeRow(selectedMaterials[i]);
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Vacia la tabla 
	 */
	public void clearData ()
	{
		tableModel.setRowCount(0);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Aplica en la aplicación el lenguaje previamente selecionado 
	 */
	public void applyLanguage ()
	{
		columnNames = new String[] {
				language.get ("usedMaterialId"),
				language.get ("usedMaterialIdMaterial"),
				language.get ("usedMaterialName"),
				language.get ("usedMaterialUnits"),
				language.get ("usedMaterialPriceApplied")
				};
		
		tableModel.setColumnIdentifiers(columnNames);
	}

}
