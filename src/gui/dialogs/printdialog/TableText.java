package gui.dialogs.printdialog;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

// ------------------------------------------------------------------------------------------------
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
	public void setColumnCurrency (int column, boolean currency)
	{
		isCurrency[column] = currency;
	}
	
	// --------------------------------------------------------------------------------------------
	public void setColumnAlignLeft (int column, boolean align)
	{
		columnsAlignLeft[column] = align;
	}
	
	// --------------------------------------------------------------------------------------------
	public void setColumnSize (int column, int length)
	{
		columnSizes[column] = length;
	}
	
	// --------------------------------------------------------------------------------------------
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
	public String getText ()
	{
		return 
				getHeader () + 
				getUnderLine () + "\n" +
				getData () + 
				getUnderLine ();
	}
	
	// --------------------------------------------------------------------------------------------
	private String getLeftFormat (int column, String value)
	{
		return String.format("%-" + columnSizes[column] + "s ", value);
	}

	// --------------------------------------------------------------------------------------------
	private String getFormat (int column, String value)
	{
		return String.format("%" + (columnsAlignLeft[column] ? "-" : "") + columnSizes[column] + "s ", 
				value + (isCurrency[column] ? currencyChar : ""));
	}
	
	// --------------------------------------------------------------------------------------------
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
	private String getValueCut (String value, int columnWidth)
	{
		return value.length() >= columnWidth ? value.substring(0, columnWidth) : value;
	}
	
	
}
