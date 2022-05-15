package gui.dialogs.printdialog;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import bll.BOrder;
import bll.RAConfig;
import bll.RAManager;
import bll.Util;
import gui.Language;

import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

// ------------------------------------------------------------------------------------------------
/***
 * Vista previa de impresión de la factura
 * 
 * @author G1
 *
 */
@SuppressWarnings("serial")
public class PrintDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel panelBill;
	private JPanel panelTotals;
	private RATableBill rATableBill;
	private Language language;
	private JTextArea textAreaBillHeader;
	private JTextField textFieldTotal;
	private JTextField textFieldTotalWidthIVA;
	private CanvasLogo canvasLogo;
	private String entitiesText;
	private String orderText;
	private RAManager rAManager;
	private RAConfig config;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo de impresion
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param config		- Objeto de datos de configuracion
	 * @param language		- Objeto de idioma
	 * @param order			- Orden de trabajo a imprimir
	 * @param entitiesText	- Datos de texto de la entidad emisora de la factura
	 * @param orderText		- Datos de la orden en formato de texto
	 */
	public PrintDialog (JFrame owner, RAManager rAManager, RAConfig config, Language language, BOrder order, String entitiesText, String orderText) 
	{
		super (owner, true);
		
		initDialog (owner, rAManager, config, language, order, entitiesText, orderText);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA, MODAL CONFIGURABLE
	/**
	 * Inicializa el dialogo de impresion
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param config		- Objeto de datos de configuracion
	 * @param language		- Objeto de idioma
	 * @param order			- Orden de trabajo a imprimir
	 * @param entitiesText	- Datos de texto de la entidad emisora de la factura
	 * @param orderText		- Datos de la orden en formato de texto
	 * @param modal			- Especifica si el dialogo es modal
	 */
	public PrintDialog (JFrame owner, RAManager rAManager, RAConfig config, Language language, BOrder order, String entitiesText, String orderText, boolean modal) 
	{
		super (owner, modal);
		
		initDialog (owner, rAManager, config, language, order, entitiesText, orderText);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo de impresion
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param config		- Objeto de datos de configuracion
	 * @param language		- Objeto de idioma
	 * @param order			- Orden de trabajo a imprimir
	 * @param entitiesText	- Datos de texto de la entidad emisora de la factura
	 * @param orderText		- Datos de la orden en formato de texto
	 */
	private final void initDialog (JFrame owner, RAManager rAManager, RAConfig config, Language language, BOrder order, String entitiesText, String orderText)
	{
		this.rAManager		= rAManager;
		this.config			= config;
		this.language		= language;
		this.entitiesText 	= entitiesText;
		this.orderText		= orderText;
		
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 911, 800);
		getContentPane().setLayout(new BorderLayout());
		getContentPane ().add (canvasLogo = new CanvasLogo (language), BorderLayout.NORTH);
		
		panelBill = new JPanel ();
		panelBill.setLayout (new BorderLayout ());
		getContentPane ().add (panelBill, BorderLayout.CENTER);
		
		textAreaBillHeader = new JTextArea ();
		textAreaBillHeader.setEditable (false);
		Font font = textAreaBillHeader.getFont ();
		textAreaBillHeader.setFont(new Font ("Monospaced", font.getStyle (), font.getSize ()));
		panelBill.add (textAreaBillHeader, BorderLayout.NORTH);
		
		rATableBill = new RATableBill (language);
		panelBill.add (new JScrollPane (rATableBill), BorderLayout.CENTER);

		textFieldTotalWidthIVA = new JTextField ();
		textFieldTotalWidthIVA.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldTotalWidthIVA.setEditable(false);
		
		textFieldTotal = new JTextField ();
		textFieldTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldTotal.setEditable(false);
		
		panelTotals = new JPanel ();
		panelTotals.setLayout (new BorderLayout ());
		panelTotals.add (textFieldTotal, BorderLayout.CENTER);
		panelTotals.add (textFieldTotalWidthIVA, BorderLayout.SOUTH);
		
		panelBill.add (panelTotals, BorderLayout.SOUTH);
		
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.SOUTH);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				{
					JButton cancelButton = new JButton(language.get ("cancelButton"));
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							close ();
						}
					});
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
			
				JButton buttonPrintToPdf = new JButton (language.get ("printToPdf"));
				buttonPrintToPdf.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent actionEvent) {
						printToPdf ();
					}
				});
				//buttonPane.add (buttonPrintToPdf);
				
				
				JButton okButton = new JButton(language.get ("printText"));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						print ();
					}
				});

				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		loadBill (order);
		
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Imprime directamente en pdf
	 */
	public void printToPdf ()
	{
		JOptionPane.showMessageDialog (this, "Not implemented yet!");
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Carga la factura a partir de una órden de trabajo
	 * 
	 * @param order	Orden de trabajo con los datos a presentar en la factura
	 */
	public void loadBill (BOrder order)
	{
		textAreaBillHeader.setText (entitiesText);
		textAreaBillHeader.append ("\n\n" + orderText);
		rATableBill.updateOrder (rAManager, order);
		double iva = config.getIva();
		
		double[] totalArray = Util.toDoubleArray(order.getTotalArray());
		
		BigDecimal bigDecimalTotal 	= totalArray == null ? new BigDecimal (0) : new BigDecimal (Util.getSum (totalArray));
		
		BigDecimal bigDecimalTotalWithIva = new BigDecimal (bigDecimalTotal.toString());
		bigDecimalTotalWithIva = bigDecimalTotalWithIva.add (bigDecimalTotal.multiply(new BigDecimal (iva)));

		bigDecimalTotalWithIva = bigDecimalTotalWithIva.setScale(2, RoundingMode.HALF_UP);		
		
		textFieldTotal.setText(language.get ("totalText") + ":         " + String.format ("%.02f", bigDecimalTotal.doubleValue()) + config.getCurrency ());
		textFieldTotalWidthIVA.setText(
			language.get ("totalText") + " + " + language.get ("ivaText") + " (" + Util.doubleToString(iva * 100) + "%):         " +  
			String.format ("%.02f", bigDecimalTotalWithIva.doubleValue()) + 
			config.getCurrency ()
			);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Imprime la factura
	 */
	public void print ()
	{
		try {
			PrintDataBill printDataBill = new PrintDataBill (config.getPrintFontSize ());
			
			printDataBill.setImageHeader(canvasLogo.getHeader(800, 128));
			printDataBill.setText (getPrintText ());
			
			if (printDataBill.printDialog ())
			{
				JOptionPane.showMessageDialog(this, language.get ("printSend"), language.get ("printText"), JOptionPane.INFORMATION_MESSAGE);
			}
			
		} catch (PrinterException e) {
			JOptionPane.showMessageDialog(this, language.get ("printingError") + "\n" + e.getMessage(), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el texto a imprimir
	 * 
	 * @return - Texto a imprimir
	 */
	private String getPrintText ()
	{
		final int CONCEPT_WIDTH = 65;
		final int TOTALS_OFFSET = 125;
		
		TableText tableText = new TableText (rATableBill, config.getPrintFontSize(), config.getCurrency());
		tableText.setColumnSize(RATableBill.COLUMN_CONCEPT, CONCEPT_WIDTH);
		tableText.setColumnAlignLeft(RATableBill.COLUMN_QUANTITY, false);
		tableText.setColumnAlignLeft(RATableBill.COLUMN_UNIT_PRICE, false);
		tableText.setColumnAlignLeft(RATableBill.COLUMN_TOTAL, false);
		
		tableText.setColumnCurrency(RATableBill.COLUMN_UNIT_PRICE, true);
		tableText.setColumnCurrency(RATableBill.COLUMN_TOTAL, true);
		
		return	entitiesText +
				"\n\n" + 
				orderText +
				"\n\n" +
				tableText + 
				"\n\n" +
				String.format ("%" + TOTALS_OFFSET + "s", textFieldTotal.getText ()) + "\n" +
				String.format ("%" + TOTALS_OFFSET + "s", textFieldTotalWidthIVA.getText ())
				;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 *  Cierra el dialogo
	 */
	public void close ()
	{
		dispose ();
	}

}
