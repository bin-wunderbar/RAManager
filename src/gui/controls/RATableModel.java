package gui.controls;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import bll.IRAObject;

//------------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
/**
 * Modelo de datos para la tabla de RAManager
 * 
 * @author G4
 *
 * @param <T> - Entidad generica
 */
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class RATableModel <T> extends AbstractTableModel
{
	protected ArrayList <T> list;
	protected String[] columnNames;
	protected boolean dataChanged;
	protected boolean editableTable;
	private Class<?>[] columnsClass;

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa el modelo desde una matriz con los nombres de las columnas
	 * 
	 * @param columnNames - Matriz con los nombres de columnas
	 */
>>>>>>> V3.1-alertas
	public RATableModel (String[] columnNames)
	{
		this.columnNames	= columnNames;
		list 				= new ArrayList <> ();
		dataChanged			= false;
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
<<<<<<< HEAD
=======
	/**
	 * Inicializa el modelo desde una matriz con los nombres de las columnas y la lista de objetos
	 * 
	 * @param columnNames - Matriz con los nombres de columnas
	 * @param list - Lista con los objetos a gestionar
	 */
>>>>>>> V3.1-alertas
	public RATableModel (String[] columnNames, ArrayList <T> list)
	{
		this.columnNames	= columnNames;
		setList (list);
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Agrega un objeto al modelo
	 * 
	 * @param object - Objeto a agregar
	 */
>>>>>>> V3.1-alertas
	public void add (T object)
	{
		list.add(object);
		dataChanged = true;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public int getRowCount() 
	{
		return list.size();
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public int getColumnCount() 
	{
		return columnNames.length;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public String getColumnName(int columnIndex) 
	{
		return columnNames[columnIndex];
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Ajusta la clase que gestionara cada columna para adecuar el editor correcto
	 * 
	 * @param columnsClass - Clase de objeto que gestionara la columna
	 */
>>>>>>> V3.1-alertas
	public void setColumnsClass (Class<?>[] columnsClass)
	{
		this.columnsClass = columnsClass;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public Class<?> getColumnClass(int columnIndex) 
	{
		return columnsClass != null ? columnsClass[columnIndex] : String.class;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) 
	{
		return editableTable;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public Object getValueAt (int rowIndex, int columnIndex)
	{
		T object = list.get(rowIndex);
		
		return ((IRAObject)object).getShowValue(columnIndex);
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		T object = list.get(rowIndex);
		
		((IRAObject)object).setValue (columnIndex, value);
		dataChanged = true;
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Ajusta la lista de objetos del modelo
	 * 
	 * @param list - Lista de objetos
	 */
>>>>>>> V3.1-alertas
	public void setList (ArrayList <T> list)
	{
		this.list = list;
		dataChanged = false;
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Devuelve la lista de objetos gestionada por el modelo
	 * 
	 * @return Lista de objetos
	 */
>>>>>>> V3.1-alertas
	public ArrayList <T> getList ()
	{
		return list;
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Devuelve un objeto segun su indice 
	 * 
	 * @param index - Indice del objeto en la lista
	 * @return El objeto
	 */
>>>>>>> V3.1-alertas
	public T get (int index)
	{
		return list.get(index);
	}
	
	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------
	/**
	 * @return the dataChanged
	 */
	public boolean isDataChanged() {
		return dataChanged;
	}

	/**
	 * @param dataChanged the dataChanged to set
	 */
	public void setDataChanged(boolean dataChanged) {
		this.dataChanged = dataChanged;
	}

<<<<<<< HEAD
=======
	/**
	 * 
	 * @param editableTable
	 */
>>>>>>> V3.1-alertas
	public void setEditableTable (boolean editableTable)
	{
		this.editableTable = editableTable;
	}

	/**
	 * @return the columnNames
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * @param columnNames the columnNames to set
	 */
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
		fireTableStructureChanged();
	}
	
}

