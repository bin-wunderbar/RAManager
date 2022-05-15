package gui.controls;

import java.util.ArrayList;

import javax.swing.JComboBox;

import bll.IRAObject;

// ------------------------------------------------------------------------------------------------
/***
 *	Clase de lista desplegable para el proyecto Rekord Autoak (RA)
<<<<<<< HEAD
 *	Dispone de los métodos comunes para todos las listas desplegables de la solución. 
=======
 *	Dispone de los métodos comunes para todos las listas desplegables de la solucion. 
>>>>>>> V3.1-alertas
 * @author G1
 *
 * @param <E>	Tipo de elementos de esta lista desplegable.
 */
<<<<<<< HEAD
public class RAComboBox <E> extends JComboBox<E> 
{
	// ANULACIÓN POR REQUISITO DE RÚBRICA
=======
@SuppressWarnings("serial")
public class RAComboBox <E> extends JComboBox<E> 
{
	// ANULACIÓN POR REQUISITO DE RÚBRICA
	/**
	 * Inicializa un objeto nuevo
	 */
>>>>>>> V3.1-alertas
	public RAComboBox ()
	{
		
	}
	
	// SOBRECARGA POR REQUISO DE RÚBRICA
<<<<<<< HEAD
=======
	/**
	 * Inicializa desde una lista de items
	 * @param list
	 */
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
	/**
	 * Selecciona un elemento de la lista desplegable por el identificador del item
	 * @param id - Identifica al item
	 */
>>>>>>> V3.1-alertas
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


