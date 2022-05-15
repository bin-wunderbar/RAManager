package gui.controls;

import java.util.ArrayList;
import javax.swing.JTable;

import bll.IRAObject;

// ------------------------------------------------------------------------------------------------
/**
 * Tabla especializada para la gestion de los objetos de RAManager
 * 
 * @author G4
 *
 * @param <T> - Objeto generico que representa una entidad de la capa de datos gestionable mediante tabla
 */
@SuppressWarnings("serial")
public class RATable <T> extends JTable 
{
	public static final int COLUMN_NAME = 0;
	
	private RATableModel <T> model;
	

	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa la tabla desde una matriz con los nombres de las columnas
	 * @param columnNames
	 */
	public RATable (String[] columnNames)
	{
		setModel (model = new RATableModel <> (columnNames));
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Agrega un objeto al modelo
	 * 
	 * @param object - Objeto a agregar
	 */
	public void add (T object)
	{
		model.add(object);
		model.fireTableDataChanged();
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Carga en la tabla una lista de objetos
	 * @param list
	 */
	public void load (ArrayList <T> list)
	{
		model.setList (list);
		model.fireTableDataChanged();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Vacia la tabla de contenidos
	 * 
	 */
	public void clear ()
	{
		model.getList ().clear();
		model.fireTableDataChanged();
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la lista de objetos gestionada
	 * @return
	 */
	public ArrayList <T> getList() 
	{
		return model.getList();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve cierto si algun dato ha sido cambiado por el usuario
	 * 
	 * @return - Cierto si algun dato ha sido cambiado
	 */
	public boolean isDataChanged ()
	{
		return model.isDataChanged();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta la clase que gestionara cada columna para adecuar el editor correcto
	 * 
	 * @param columnsClass - Clase de objeto que gestionara la columna
	 */
	public void setColumnsClass (Class<?>[] columnsClass)
	{
		model.setColumnsClass(columnsClass);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve la lista de ids de los objetos especificados por la matriz de filas seleccionadas
	 * 
	 * @param selectedRows - Matriz con las filas seleccionadas
	 * @return Lista de ids
	 */
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
	/**
	 * Devuelve el objeto especificado
	 * 
	 * @param row - Fila donde localizar el objeto
	 * @return - El objeto
	 */
	public T get (int row)
	{
		return model.get(row);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta la tabla como editable
	 * 
	 * @param editable - Valor de cierto o falso para habilitar la edicion
	 */
	public void setEditable(boolean editable) 
	{
		model.setEditableTable(editable);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Selecciona la fila que contenga el objeto con el identificador especificado
	 * 
	 * @param id - Identificador del objeto
	 */
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
	/**
	 * Ajusta la matriz con los nombres de columnas
	 * 
	 * @param columnNames - Matriz con los nombres de columnas
	 */
	public void setColumnNames(String[] columnNames) 
	{
		model.setColumnNames (columnNames);
	}
	
}

