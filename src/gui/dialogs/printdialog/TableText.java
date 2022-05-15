package gui.dialogs.printdialog;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

// ------------------------------------------------------------------------------------------------
/**
 * Convierte una tabla en formato de texto
 * 
 * @author G4
 *
 */
public class TableText 
{
	private JTable table;
	private int[] columnSizes;
	private String[] columnNames;
	private boolean[] columnsAlignLeft;
	private boolean[] isCurrency;
	private String currencyChar;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el conversor de tabla a texto
	 * 
	 * @param table - Componente de tabla con los datos
	 * @param fontSize - Tamaño de fuente
	 * @param currencyChar - Tipo de moneda
	 */
	public TableText (JTable table, int fontSize, String currencyChar)
	{
		initTableText (table, fontSize, currencyChar);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el conversor de tabla a texto (SOBRECARGA POR REQUISITO DE RÚBRICA)
	 * 
	 * @param table - Componente de tabla con los datos
	 */
	public TableText (JTable table)
	{
		initTableText (table, 6, "€");
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el conversor de tabla a texto
	 * 
	 * @param table - Componente de tabla con los datos
	 * @param fontSize - Tamaño de fuente
	 * @param currencyChar - Tipo de moneda
	 */
	private final void initTableText (JTable table, int fontSize, String currencyChar)
	{
		this.table 			= table;
		this.currencyChar	= currencyChar;
		
		TableColumnModel columnModel = table.getColumnModel();
		
		columnSizes			= new int[columnModel.getColumnCount()];
		columnNames			= new String[columnSizes.length];
		columnsAlignLeft	= new boolean[columnSizes.length];
		isCurrency			= new boolean[columnSizes.length];	
		
		for (int i = 0; i < columnSizes.length; i++)
		{
			columnSizes[i] 		= columnModel.getColumn (i).getWidth() / fontSize + 1;
			columnNames[i] 		= "" + columnModel.getColumn(i).getHeaderValue();
			columnsAlignLeft[i] = true;
			isCurrency[i]		= false;
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta la columna si usa moneda
	 * 
	 * @param column		- Posicion de la columna	
	 * @param currency		- Especifica si usa moneda
	 */
	public void setColumnCurrency (int column, boolean currency)
	{
		isCurrency[column] = currency;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta la alineacion de la columna a la izquierda
	 * 
	 * @param column		- Posicion de la columna
	 * @param align			- Especifica que debe de ir a la izquierda
	 */
	public void setColumnAlignLeft (int column, boolean align)
	{
		columnsAlignLeft[column] = align;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta el tamanno de columna
	 * 
	 * @param column		- Posicion de la columna
	 * @param length		- Tamanno de la columna
	 */
	public void setColumnSize (int column, int length)
	{
		columnSizes[column] = length;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el texto correspondiente a la cabecera
	 * 
	 * @return Texto de la cabecera
	 */
	private String getHeader ()
	{
		String text = "";

		for (int column = 0; column < columnNames.length; column++)
		{
			text += getLeftFormat (column, getValueCut (columnNames[column], columnSizes[column]));
		}
		text += "\n";
		
		return text;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una linea horizontal a modo de subrayado
	 * 
	 * @return Linea horizontal como texto
	 */
	private String getUnderLine ()
	{
		String text = "";
		
		for (int column = 0; column < columnNames.length; column++)
		{
			text += getLeftFormat (column, getUnderLine (columnSizes[column]));
		}
		text += "\n";
		
		return text;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve los datos de la tabla como texto
	 * 
	 * @return Datos internos de la tabla como texto
	 */
	private String getData ()
	{
		String text 		= "";
		TableModel model 	= table.getModel();
		int rowCount 		= model.getRowCount();

		for (int row = 0; row < rowCount; row++)
		{
			for (int column = 0; column < columnNames.length; column++)
			{
				text += getFormat (column, getValueCut ("" + model.getValueAt(row, column), columnSizes[column]));
			}
			
			text += "\n";
		}
		
		return text;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve los datos completos de la tabla como texto
	 * @return
	 */
	public String getText ()
	{
		return 
				getHeader () + 
				getUnderLine () + "\n" +
				getData () + 
				getUnderLine ();
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un texto con el valor acotado a la columna alineado a la izquierda
	 * 
	 * @param column		- Posicion de la columna
	 * @param value			- Valor a formatear
	 * @return Texto formateado
	 */
	private String getLeftFormat (int column, String value)
	{
		return String.format("%-" + columnSizes[column] + "s ", value);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un texto formateado acotado a la columna
	 * 
	 * @param column		- Posicion de la columna
	 * @param value			- Valor a formatear
	 * @return Texto formateado
	 */
	private String getFormat (int column, String value)
	{
		return String.format("%" + (columnsAlignLeft[column] ? "-" : "") + columnSizes[column] + "s ", 
				value + (isCurrency[column] ? currencyChar : ""));
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una linea horizontal a modo de subrayado segun las columnas especificadas
	 * 
	 * @param columns 		- Numero de columnas para generar la linea horizontal
	 * @return Linea horizontal
	 */
	private String getUnderLine (int columns)
	{
		String underLine = "";
		
		for (int i = 0; i < columns; i++)
		{
			underLine += "_";
		}
		
		return underLine;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el valor recortado segun el ancho de la columna
	 * 
	 * @param value				- Valor a recortar
	 * @param columnWidth		- Ancho de la columna
	 * @return Valor recortado
	 */
	private String getValueCut (String value, int columnWidth)
	{
		return value.length() >= columnWidth ? value.substring(0, columnWidth) : value;
	}
	
	
}
