package gui.controls;

import java.util.ArrayList;

import javax.swing.JComboBox;

import bll.IRAObject;

// ------------------------------------------------------------------------------------------------
/***
 *	Clase de lista desplegable para el proyecto Rekord Autoak (RA)
 *	Dispone de los métodos comunes para todos las listas desplegables de la solucion. 
 * @author G1
 *
 * @param <E>	Tipo de elementos de esta lista desplegable.
 */
@SuppressWarnings("serial")
public class RAComboBox <E> extends JComboBox<E> 
{
	// ANULACIÓN POR REQUISITO DE RÚBRICA
	/**
	 * Inicializa un objeto nuevo
	 */
	public RAComboBox ()
	{
		
	}
	
	// SOBRECARGA POR REQUISO DE RÚBRICA
	/**
	 * Inicializa desde una lista de items
	 * @param list
	 */
	public RAComboBox (ArrayList <E> list)
	{
		loadItems (list);
	}
	
	//---------------------------------------------------------------------------------------------
	/***
	 * Carga elementos en el ComboBox desde una lista
	 * @param list	- Lista con los elementos para agregar al ComboBox
	 */
	public void loadItems (ArrayList <E> list)
	{
		removeAllItems ();
		for (E item : list)
		{
			addItem(item);
		}
	}
	
	
	//---------------------------------------------------------------------------------------------
	/***
	 * Selecciona un elemento del ComboBox
	 * @param selectItem  -  Elemento seleccionado
	 */
	public void selectBy (E selectItem)
	{
		for (int i = 0; i < dataModel.getSize(); i++)
		{
			if (dataModel.getElementAt(i).equals(selectItem))
			{
				setSelectedIndex(i);
				break;
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------
	/**
	 * Selecciona un elemento de la lista desplegable por el identificador del item
	 * @param id - Identifica al item
	 */
	public void selectById (int id)
	{
		for (int i = 0; i < dataModel.getSize(); i++)
		{
			IRAObject iRAObject = (IRAObject) dataModel.getElementAt(i);
			if (iRAObject.getId () == id)
			{
				setSelectedIndex(i);
				break;
			}
		}
	}

}


