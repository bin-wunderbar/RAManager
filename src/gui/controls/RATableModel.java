package gui.controls;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import bll.IRAObject;

//------------------------------------------------------------------------------------------------
public class RATableModel <T> extends AbstractTableModel
{
	protected ArrayList <T> list;
	protected String[] columnNames;
	protected boolean dataChanged;
	protected boolean editableTable;
	private Class<?>[] columnsClass;

	// --------------------------------------------------------------------------------------------
	public RATableModel (String[] columnNames)
	{
		this.columnNames	= columnNames;
		list 				= new ArrayList <> ();
		dataChanged			= false;
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	public RATableModel (String[] columnNames, ArrayList <T> list)
	{
		this.columnNames	= columnNames;
		setList (list);
	}
	
	// --------------------------------------------------------------------------------------------
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
	public void setList (ArrayList <T> list)
	{
		this.list = list;
		dataChanged = false;
	}
	
	// --------------------------------------------------------------------------------------------
	public ArrayList <T> getList ()
	{
		return list;
	}

	// --------------------------------------------------------------------------------------------
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

