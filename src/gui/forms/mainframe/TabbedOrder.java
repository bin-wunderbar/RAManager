package gui.forms.mainframe;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import bll.BOrder;
import bll.RAManager;
import gui.Language;
import gui.ValueChecks;
import gui.controls.ButtonDelete;
import gui.controls.ButtonEdit;
import gui.controls.ButtonAdd;

// ------------------------------------------------------------------------------------------------
/***
 * Clase de panel de fichas
 * 
 * @author G1
 *
 */
public class TabbedOrder extends JTabbedPane
{
	private MainFrame mainFrame;
	private Language language;
	private RAManager rAManager;
	private BOrder currentOrder;

	private JPanel panelOperations;
	


	private RATableOperations rATableOperations;
	
	private ButtonAdd buttonOperationAdd;
	private ButtonDelete buttonOperationDelete;
	private ButtonEdit buttonOperationEdit;
	
	private static final int TAB_OPERATIONS		= 0;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Constructor de panel de fichas
	 * @param mainFrame
	 */
	public TabbedOrder (MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		language 	= mainFrame.getLanguage();
		rAManager 	= mainFrame.getRAManager();
		
		initComponents ();
	}

	// --------------------------------------------------------------------------------------------
	// INICIALIZACIONES
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicilizar componentes del panel
	 */
	private final void initComponents ()
	{
		initPanels ();
		initPanelOperations ();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializar paneles de fichas
	 */
	private final void initPanels ()
	{
		panelOperations = new JPanel ();
		panelOperations.setLayout (new BorderLayout ());
		addTab (language.get ("operationsTitle"), null, panelOperations, null);
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializar panel de operaciones
	 */
	private final void initPanelOperations ()
	{
		initToolBarOperations ();
		
		rATableOperations = new RATableOperations (language, rAManager);
		rATableOperations.addMouseListener (new MouseAdapter () {
			public void mouseClicked (MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount () == 2)
				{
					editOperation ();
				}
			}
		});
		panelOperations.add (new JScrollPane (rATableOperations), BorderLayout.CENTER);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializar barra de herramientas de operaciones
	 */
	private final void initToolBarOperations ()
	{
		JToolBar toolBarOperations = new JToolBar ();
		panelOperations.add (toolBarOperations, BorderLayout.NORTH);

		buttonOperationAdd 	= new ButtonAdd(language);
		buttonOperationAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showNewOperationDialog ();
			}
		});
		
		buttonOperationDelete	= new ButtonDelete (language);
		buttonOperationDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.deleteOperations ();
			}
		});
		
		buttonOperationEdit 	= new ButtonEdit (language);
		buttonOperationEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editOperation ();
			}
		});
		
		toolBarOperations.add (buttonOperationAdd);
		toolBarOperations.add (buttonOperationDelete);
		toolBarOperations.add (buttonOperationEdit);
		
		toolBarOperations.addSeparator ();
	}

	// --------------------------------------------------------------------------------------------
	// EVENTOS
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Actualiza desde el encargo indicado
	 * @param currentOrder	Encargo indicado
	 */
	public void updateSelectedOrder(BOrder currentOrder) 
	{
		this.currentOrder = currentOrder;
		rATableOperations.setOrder(currentOrder);
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Editar operación
	 */
	private void editOperation ()
	{
		int selectedRow = rATableOperations.getSelectedRow();

		if (selectedRow >= 0)
		{
			int operationId = (int) rATableOperations.getValueAt(selectedRow, RATableOperations.COLUMN_ID);
			mainFrame.showModifyOperationDialog (operationId);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Eliminar operación
	 */
	public void deleteOperations() 
	{
		
		int[] selectedRows = rATableOperations.getSelectedRows ();
		
		if (currentOrder != null && selectedRows.length > 0)
		{
			if (JOptionPane.showConfirmDialog (mainFrame, language.get ("deleteOperations"), language.get("delete"), JOptionPane.YES_NO_CANCEL_OPTION)
					== JOptionPane.YES_OPTION)
			{
				ArrayList <Integer> ids = new ArrayList <> ();
				
				for (int i = 0; i < selectedRows.length; i++)
				{
					ids.add ((int)rATableOperations.getValueAt(selectedRows[i], 0));
				}
				
				try {
					rAManager.deleteOperationsByIds(ids);
					currentOrder = rAManager.getBOrderById(currentOrder.getId());
					mainFrame.setCurrentOrder(currentOrder);
					rATableOperations.setOrder(currentOrder);
				} catch (bll.RAManagerException e) {
					ValueChecks.showExceptionMessage(this, e);
				}
				
			}
			
		}
	}

	// --------------------------------------------------------------------------------------------
	/***
	 * Actualizar Operacion
	 */
	public void updateOperations () 
	{
		if (currentOrder != null)
		{
			rATableOperations.setOrder(currentOrder);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	// IDIOMAS
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Aplicar idiomas
	 */
	public void applyLanguage ()
	{
	
		this.setTitleAt (TAB_OPERATIONS, language.get ("operationsTitle"));
		
		rATableOperations.applyLanguage();
	}


}
