package gui.dialogs.printdialog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import bll.BMaterial;
import bll.BOperation;
import bll.BOrder;
import bll.BService;
import bll.BUsedMaterial;
import bll.RAManager;
import bll.Util;
import gui.Language;
<<<<<<< HEAD
=======
import gui.ValueChecks;
>>>>>>> V3.1-alertas

// ------------------------------------------------------------------------------------------------
/**
 * Tabla para la vista de factura
 * 
 * @author soporte
 *
 */
<<<<<<< HEAD
=======
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class RATableBill extends JTable
{
	private Language language;
	private DefaultTableModel tableModel;
	private DefaultTableCellRenderer cellRender;

	public static final String PREFIX_OPERATION 	= "RA";
	public static final String PREFIX_MATERIAL		= "RM";
	
	public static final int NO_ID					= -1;
	
	public static final int COLUMN_REF				= 0;
	public static final int COLUMN_CONCEPT			= 1;
	public static final int COLUMN_QUANTITY			= 2;
	public static final int COLUMN_UNIT_PRICE		= 3;
	public static final int COLUMN_TOTAL			= 4;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa la tabla de factura
	 * 
	 * @param language	Idioma para ajustar la vista
	 */
	public RATableBill (Language language)
	{
		initTable (language, new String[] {"Ref", "Concept", "Quantity", "Unit price", "Total"});
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
<<<<<<< HEAD
=======
	/**
	 * Inicializa la tabla de factura
	 * 
	 * @param language			- Idioma para ajustar la vista
	 * @param columnNames		- Nombres de columnas
	 */
>>>>>>> V3.1-alertas
	public RATableBill (Language language, String[] columnNames)
	{
		initTable (language, columnNames);
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa la tabla de factura
	 * 
	 * @param language			- Idioma para ajustar la vista
	 * @param columnNames 		- Nombres de columnas
	 */
>>>>>>> V3.1-alertas
	private final void initTable (Language language, String[] columnNames)
	{
		this.language = language;
		
		tableModel = getReadOnlyCellsDataModel (columnNames);
		
		setModel (tableModel);
		
		applyLanguage ();
		setColumnsWidth ();
		createCellAlignment ();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve un modelo de tabla en solo lectura
	 * 
	 * @param columns
	 * @return
	 */
	private DefaultTableModel getReadOnlyCellsDataModel (String[] columns)
	{
		return new DefaultTableModel (columns, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta el ancho de las columnas 
	 */
	private void setColumnsWidth() 
	{
		TableColumnModel columnModel = getColumnModel ();
		columnModel.getColumn (COLUMN_REF).setMaxWidth(50);
		columnModel.getColumn (COLUMN_QUANTITY).setMinWidth(100);
		columnModel.getColumn (COLUMN_QUANTITY).setMaxWidth(150);
		columnModel.getColumn (COLUMN_UNIT_PRICE).setMinWidth(100);
		columnModel.getColumn (COLUMN_UNIT_PRICE).setMaxWidth(150);
		columnModel.getColumn (COLUMN_TOTAL).setMaxWidth(100);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Crea un objeto de alineación de celdas
	 */
	private void createCellAlignment ()
	{
		cellRender = new DefaultTableCellRenderer();
	}

	// --------------------------------------------------------------------------------------------
	@Override
<<<<<<< HEAD
	/***
	 *  Configura la renderización de las celdas de la tabla
	 */
=======
>>>>>>> V3.1-alertas
    public TableCellRenderer getCellRenderer (int row, int column) 
	{
		/*
		 * 	Columnas de referencia y concepto alineadas a la izquierda (2 primeras)
		 *  Columnas restantes alineadas a la derecha 
		 */
		if (column < 2)
		{
			cellRender.setHorizontalAlignment(SwingConstants.LEFT);
		}
		else
		{
			cellRender.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		
        return cellRender;
    }

	// --------------------------------------------------------------------------------------------
	/**
	 * Actualiza la orden de trabajo
	 * 
	 * @param rAManager	Sistema operativo de la aplicación
	 * @param order		Orden de trabajo
	 */
	public void updateOrder (RAManager rAManager, BOrder order)
	{
		if (order != null)
		{
			clearData ();
			
			for (BOperation operation : order.getBOperations())
			{
				addOperation (rAManager, operation);
			}
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 *	Agrega una operación individual a la factura
	 * 
	 * @param rAManager	Sistema operativo de la aplicación
	 * @param operation	Operación para agregar
	 */
	private void addOperation(RAManager rAManager, BOperation operation) 
	{
		try 
		{
			BService service = rAManager.getBServiceById (operation.getIdService());
			BigDecimal bigDecimalDedicatedTime = new BigDecimal (operation.getDedicatedTime());
			
			// Convert minutes to hour units
			BigDecimal bigDecimalHourUnits = bigDecimalDedicatedTime.divide(new BigDecimal (60), 2, RoundingMode.HALF_UP);

			
			Object[] rowOperation = new Object[] {
				PREFIX_OPERATION + operation.getId(),
				service != null ? service.getDescription() : NO_ID,
<<<<<<< HEAD
				Util.numberToString (bigDecimalHourUnits.doubleValue()),
				Util.numberToString (operation.getHourlyPriceApplied()),
				Util.numberToString (operation.getTotal())
=======
				Util.doubleToString (bigDecimalHourUnits.doubleValue()),
				Util.doubleToString (operation.getHourlyPriceApplied()),
				Util.doubleToString (operation.getTotal())
>>>>>>> V3.1-alertas
				};
			
			tableModel.addRow(rowOperation);
			
			ArrayList <BMaterial> materials = rAManager.getAllBMaterials ("");

			for (BUsedMaterial usedMaterial : operation.getBUsedMaterials())
			{
				BMaterial material = rAManager.getBMaterialById (materials, usedMaterial.getIdMaterial());

				Object[] rowMaterial = new Object[] {
					PREFIX_MATERIAL + usedMaterial.getIdMaterial(),
					material != null ? material.getName() : NO_ID,
<<<<<<< HEAD
					Util.numberToString (usedMaterial.getUnits ()),
					Util.numberToString (usedMaterial.getUnitPriceApplied()),
					Util.numberToString (usedMaterial.getTotal())
=======
					Util.doubleToString (usedMaterial.getUnits ()),
					Util.doubleToString (usedMaterial.getUnitPriceApplied()),
					Util.doubleToString (usedMaterial.getTotal())
>>>>>>> V3.1-alertas
				};
				
				tableModel.addRow(rowMaterial);
			}
		} 
		catch (bll.RAManagerException e) 
		{
			rAManager.getRALogging ().println(bll.RALogging.LEVEL_ERROR, e.getMessage());
<<<<<<< HEAD
			gui.ValueChecks.showExceptionMessage(this, e);
=======
			ValueChecks.showExceptionMessage (null, language, e);
>>>>>>> V3.1-alertas
		}

	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Vacía la tabla
	 */
	private void clearData ()
	{
		tableModel.setRowCount(0);
	}

	// --------------------------------------------------------------------------------------------
	/** 
	 * aplicar el lenfuaje seleccionado
	 */
	private void applyLanguage ()
	{
		String[] columnNames = new String[] {
				language.get ("billColumnRef"),
				language.get ("billColumnConcept"), 
				language.get ("billColumnQuantity"),
				language.get ("billColumnUnitPrice"),
				language.get ("billColumnTotalRow")
				};
 
		tableModel.setColumnIdentifiers(columnNames);
	}

}
