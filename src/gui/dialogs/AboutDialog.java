package gui.dialogs;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import gui.Language;
import gui.controls.BackgroundTextPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.awt.Color;

// --------------------------------------------------------------------------------------------------
/**
 * Dialogo de acerca de la aplicacion
 * @author G4
 *
 */
@SuppressWarnings("serial")
public class AboutDialog extends JDialog 
{
	private JPanel panelButtons;
	private JButton buttonOk;
	private JTextPane textPane;
	private Language language;
	
	
	// ----------------------------------------------------------------------------------------------
	/**
	 * Inicializa el dialogo
	 * 
	 * @param owner - Ventana propietaria
	 * @param language - Objeto de idioma
	 */
	public AboutDialog (JFrame owner, Language language)
	{
		super (owner, true);
	
		this.language = language;
		
		setTitle (language.get("menuItemAbout"));
		Rectangle bounds = owner.getBounds ();
		setBounds (bounds.x + 50, bounds.y + 50, 800, 800);
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		
		
		textPane = new BackgroundTextPane ("/resources/about_background.jpg", Color.BLACK);
		getContentPane ().add (new JScrollPane (textPane), BorderLayout.CENTER);
		
		getContentPane ().add (panelButtons = new JPanel (), BorderLayout.SOUTH);
		panelButtons.setLayout (new FlowLayout (FlowLayout.RIGHT));
		
		buttonOk 		= new JButton (language.get ("okButton"));
		buttonOk.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				dispose ();
			}
		});
		panelButtons.add (buttonOk);
		panelButtons.setBorder(new BevelBorder (BevelBorder.LOWERED));
	
		getRootPane ().setDefaultButton(buttonOk);
		
		loadHtml ();
		
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Carga la pagina html
	 */
	public void loadHtml ()
	{
		InputStream inputStream = AboutDialog.class.getResourceAsStream("/resources/html/" + language.getSelectedLanguage () + ".html");
		textPane.setContentType("text/html;charset=UTF-8");
		
		if (inputStream != null)
		{
			try {
				byte[] contentStream = new byte [inputStream.available()];
				inputStream.read (contentStream);
				textPane.setText (new String (contentStream));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			
			textPane.setText ("<p style='color: white; text-align: center;'>" + language.get ("about") + "</p>");
		}
		
	}
}
