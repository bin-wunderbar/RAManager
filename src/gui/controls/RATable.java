package gui.controls;

import java.util.ArrayList;
import javax.swing.JTable;

import bll.IRAObject;

// ------------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
/**
 * Tabla especializada para la gestion de los objetos de RAManager
 * 
 * @author G4
 *
 * @param <T> - Objeto generico que representa una entidad de la capa de datos gestionable mediante tabla
 */
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class RATable <T> extends JTable 
{
	public static final int COLUMN_NAME = 0;
	
	private RATableModel <T> model;
	

<<<<<<< HEAD
	// ANULACIÓN DEL CONSTRUCTOR POR DEFECTO POR REQUISITO DE RÚBRICA
	public RATable ()
	{
	}
	
	// --------------------------------------------------------------------------------------------
=======
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa la tabla desde una matriz con los nombres de las columnas
	 * @param columnNames
	 */
>>>>>>> V3.1-alertas
	public RATable (String[] columnNames)
	{
		setModel (model = new RATableModel <> (columnNames));
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
		model.add(object);
		model.fireTableDataChanged();
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Carga en la tabla una lista de objetos
	 * @param list
	 */
>>>>>>> V3.1-alertas
	public void load (ArrayList <T> list)
	{
		model.setList (list);
		model.fireTableDataChanged();
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
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
>>>>>>> V3.1-alertas
	public ArrayList <T> getList() 
	{
		return model.getList();
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Devuelve cierto si algun dato ha sido cambiado por el usuario
	 * 
	 * @return - Cierto si algun dato ha sido cambiado
	 */
>>>>>>> V3.1-alertas
	public boolean isDataChanged ()
	{
		return model.isDataChanged();
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
		model.setColumnsClass(columnsClass);
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Devuelve la lista de ids de los objetos especificados por la matriz de filas seleccionadas
	 * 
	 * @param selectedRows - Matriz con las filas seleccionadas
	 * @return Lista de ids
	 */
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
	/**
	 * Devuelve el objeto especificado
	 * 
	 * @param row - Fila donde localizar el objeto
	 * @return - El objeto
	 */
>>>>>>> V3.1-alertas
	public T get (int row)
	{
		return model.get(row);
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Ajusta la tabla como editable
	 * 
	 * @param editable - Valor de cierto o falso para habilitar la edicion
	 */
>>>>>>> V3.1-alertas
	public void setEditable(boolean editable) 
	{
		model.setEditableTable(editable);
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Selecciona la fila que contenga el objeto con el identificador especificado
	 * 
	 * @param id - Identificador del objeto
	 */
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
	/**
	 * Ajusta la matriz con los nombres de columnas
	 * 
	 * @param columnNames - Matriz con los nombres de columnas
	 */
>>>>>>> V3.1-alertas
	public void setColumnNames(String[] columnNames) 
	{
		model.setColumnNames (columnNames);
	}
	
}

