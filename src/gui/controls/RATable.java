package gui.controls;

import java.util.ArrayList;
import javax.swing.JTable;

import bll.IRAObject;

// ------------------------------------------------------------------------------------------------
public class RATable <T> extends JTable 
{
	public static final int COLUMN_NAME = 0;
	
	private RATableModel <T> model;
	

	// ANULACIÓN DEL CONSTRUCTOR POR DEFECTO POR REQUISITO DE RÚBRICA
	public RATable ()
	{
	}
	
	// --------------------------------------------------------------------------------------------
	public RATable (String[] columnNames)
	{
		setModel (model = new RATableModel <> (columnNames));
	}
	
	// --------------------------------------------------------------------------------------------
	public void add (T object)
	{
		model.add(object);
		model.fireTableDataChanged();
	}
	
	// --------------------------------------------------------------------------------------------
	public void load (ArrayList <T> list)
	{
		model.setList (list);
		model.fireTableDataChanged();
	}

	// --------------------------------------------------------------------------------------------
	public ArrayList <T> getList() 
	{
		return model.getList();
	}

	// --------------------------------------------------------------------------------------------
	public boolean isDataChanged ()
	{
		return model.isDataChanged();
	}

	// --------------------------------------------------------------------------------------------
	public void setColumnsClass (Class<?>[] columnsClass)
	{
		model.setColumnsClass(columnsClass);
	}

	// --------------------------------------------------------------------------------------------
	public ArrayList <Integer> getSelectedIds (int[] selectedRows) 
	{
		ArrayList <Integer> ids = new ArrayList <> ();
		
		for (int i = 0; i < selectedRows.length; i++)
		{
			IRAObject iRAObject = (IRAObject) model.getList().get(selectedRows[i]); 
			ids.add (iRAObject.getId());
		}
		
		return ids;
	}
	
	// --------------------------------------------------------------------------------------------
	public T get (int row)
	{
		return model.get(row);
	}

	// --------------------------------------------------------------------------------------------
	public void setEditable(boolean editable) 
	{
		model.setEditableTable(editable);
	}

	// --------------------------------------------------------------------------------------------
	public void selectById(int id) 
	{
		int index = 0;
		for (T object : model.getList())
		{
			if (((IRAObject)object).getId () == id)
			{
				this.setRowSelectionInterval(index, index);
			}
			
			index++;
		}
	}

	// --------------------------------------------------------------------------------------------
	public void setColumnNames(String[] columnNames) 
	{
		model.setColumnNames (columnNames);
	}
	
}

