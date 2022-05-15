package gui.forms.mainframe;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bll.BOperation;
import bll.BOrder;
import bll.BService;
import bll.RAManager;
import gui.Language;
import gui.ValueChecks;

// ------------------------------------------------------------------------------------------------
/***
 * Clase de la tabla de operaciones
 * @author G1
 *
 */
@SuppressWarnings("serial")
public class RATableOperations extends JTable 
{
	private String[] columnNames;
	private DefaultTableModel tableModel;
	private Language language;
	private RAManager rAManager;
	
	public static int COLUMN_ID = 0;

	// ------------------------------------------------------------------------------------------------
/***
 * Constructor de la tabla de operaciones
 * @param language	Objeto de idioma
 * @param rAManager	Gestor de la aplicación
 */
	public RATableOperations (Language language, RAManager rAManager)
	{
		this.language 	= language;
		this.rAManager 	= rAManager;
		
		columnNames = new String[] {"Id", "Id Order", "DNI Mechanic", "Id Service", "Service", "Dedicated minutes", "Hourly price applied"};

		setModel (tableModel = getReadOnlyDataModel (columnNames));
		applyLanguage ();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve un modelo de tabla de solo lectura
	 * 
	 * @param columnNames	Nombres de las columnas de la tabla
	 * @return	Modelo de tabla de solo lectura
	 */
	private DefaultTableModel getReadOnlyDataModel (String[] columnNames)
	{
		return new DefaultTableModel (columnNames, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
	}
	
	// ------------------------------------------------------------------------------------------------
	/***
	 * Ajustar la orden de trabajo indicada
	 * 
	 * @param order	Orden de trabajo
	 */
	public void setOrder (BOrder order)
	{
		clearData ();
		
		if (order != null)
		{
			for (BOperation operation : order.getBOperations())
			{
				addOperation (operation);
			}
		}
	}
	
	// ------------------------------------------------------------------------------------------------
	/***
	 * Agregar una operación
	 * 
	 * @param operation Operación para agregar
	 */
	public void addOperation (BOperation operation)
	{
		try 
		{
			String serviceDescription = "";
			BService service = rAManager.getBServiceById (operation.getIdService ());
			if (service != null)
			{
				serviceDescription = service.getDescription();
			}
			
			Object[] operationRow = new Object[] {
					operation.getId(),
					operation.getIdOrder(),
					operation.getIdMechanic(),
					operation.getIdService(),
					serviceDescription,
					operation.getDedicatedTime(),
					operation.getHourlyPriceApplied()
				};
			
			tableModel.addRow(operationRow);
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
			ValueChecks.showExceptionMessage (null, language, e);
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Elimina las lineas especificadas
	 * 
	 * @param selectedRows - Matriz con las lineas a eliminar
	 */
	public void removeRows(int[] selectedRows) 
	{
		for (int i = 0; i < selectedRows.length; i++)
		{
			tableModel.removeRow (selectedRows[i]);
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Vaciar datos
	 */
	public void clearData ()
	{
		tableModel.setRowCount(0);
	}
	
	// --------------------------------------------------------------------------------------------
	// IDIOMA
	// --------------------------------------------------------------------------------------------
	
	// ------------------------------------------------------------------------------------------------
	/***
	 * Aplicar  lenguaje a la tabla
	 */
	public void applyLanguage ()
	{
		columnNames = new String[] {
				language.get ("columnId"), 
				language.get ("columnIdOrder"), 
				language.get ("columnDniOperator"), 
				language.get ("columnIdService"), 
				language.get ("columnService"), 
				language.get ("columnDedicatedMinutes"), 
				language.get ("columnHourlyPriceApplied")
				};
		tableModel.setColumnIdentifiers(columnNames);
	}

}
