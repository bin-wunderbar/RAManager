package gui.forms.mainframe;

import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import bll.BClient;
import bll.BOrder;
import gui.Language;

// ------------------------------------------------------------------------------------------------
/***
 * Barra de herramientas de la orden de trabajo
 * 
 * @author G1
 *
 */
public class ToolBarOrder extends JToolBar
{
	private Language language;
	
	private JButton btnPrint;
	
	private JTextField textFieldClient;
	private JTextField textFieldOrder;
	
	private JButton buttonOrder;
	private JButton buttonClient;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa la barra de herramientas
	 * 
	 * @param mainFrame	Formulario principal de la aplicación
	 */
	public ToolBarOrder (MainFrame mainFrame)
	{
		this.language 	= mainFrame.getLanguage ();
		
		btnPrint = new JButton("");
		btnPrint.setToolTipText(language.get ("toolTipPrint"));
		btnPrint.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/printer.png")));
		btnPrint.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent) {
				mainFrame.showPrintDialog ();
			}
		});
		add(btnPrint);
		addSeparator();
		
		addSeparator ();
		
		buttonClient = new JButton (language.get ("menuItemClient"));
		buttonClient.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/customer.png")));
		buttonClient.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent) {
				mainFrame.showClientDialog ();
			}
		});
		add (buttonClient);

		textFieldClient = new JTextField ();
		textFieldClient.setEditable(false);
		textFieldClient.setColumns(10);
		
		add(textFieldClient);
		
		
		buttonOrder = new JButton (language.get ("menuItemOrder"));
		buttonOrder.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/order_add.png")));
		buttonOrder.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent) {
				mainFrame.showOrderClientDialog ();
			}
		});
		add (buttonOrder);

		textFieldOrder	= new JTextField ();
		textFieldOrder.setEditable (false);
		add(textFieldOrder);

	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Ajustar la vista de texto del cliente indicado
	 * 
	 * @param client	Cliente indicado
	 */
	public void setClientText(BClient client) 
	{
		if (client != null)
		{
			textFieldClient.setText(client.getName());
		}
		else
		{
			textFieldClient.setText("");
		}
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Ajustar la vista de texto de la orden de trabajo indicada
	 * 
	 * @param order	Orden de trabajo indicada
	 */
	public void setOrderText(BOrder order) 
	{
		if (order != null)
		{
			textFieldOrder.setText(order.getDescription());
		}
		else
		{
			textFieldOrder.setText("");
		}
	}
	
	// --------------------------------------------------------------------------------------------
	// IDIOMAS
	// --------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Aplica el lenguaje configurado en los controles de la barra de herramientas
	 */
	protected void applyLanguage ()
	{
		btnPrint.setToolTipText (language.get ("toolTipPrint"));
		buttonOrder.setText (language.get ("menuItemOrder"));
		buttonClient.setText (language.get ("menuItemClient"));
	}

}
