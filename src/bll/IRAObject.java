package bll;

// ------------------------------------------------------------------------------------------------

/***
 * Generaliza la comunicación de los objetos como filas de datos de tabla
 * @author G1
 *
 */
public interface IRAObject 
{
	/**
	 * Devuelve el ID del objeto
<<<<<<< HEAD
	 * @return
=======
	 * @return El id
>>>>>>> V3.1-alertas
	 */
	public int getId ();
	
	/**
	 * Devuelve el valor de un atributo indexado
<<<<<<< HEAD
	 * @param column - Índice de atributo
=======
>>>>>>> V3.1-alertas
	 * @return - Valor del atributo indicado
	 */
	public Object getValue (int index);
	
	/**
	 * Devuelve el valor de un atributo indexado como columna
<<<<<<< HEAD
	 * @param index - Índice de atributo
=======
	 * @param index - Indice de atributo
>>>>>>> V3.1-alertas
	 * @return - Valor útil a la hora de ser mostrado, por ejemplo en lugar del id de un vehículo muestra su matrícula
	 */
	public Object getShowValue (int index);
	
	/**
	 * Ajusta el valor de un atributo según su índice
<<<<<<< HEAD
	 * @param index - Índice de atributo
=======
	 * @param index - Indice de atributo
>>>>>>> V3.1-alertas
	 * @param object - Valor a ajustar en el atributo indicado
	 */
	public void setValue (int index, Object object);
	
	/**
	 * Devuelve cierto cuando alguno de los campos útiles contengan el texto de filtrado
	 * @param filter - Texto de filtrado
	 * @return - Cierto cuando contenga el texto de filtrado, falso en caso contrario
	 */
	public boolean containsFilter (String filter);
}
