package gui.dialogs;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import bll.RAManagerException;
import gui.Language;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("serial")
public class ExceptionDialog extends JDialog {

	private JTabbedPane tabbedPane;
	private JTextArea textAreaMessage;
	private JTextArea textAreaStackTrace;

	/**
	 * Create the dialog.
	 */
	public ExceptionDialog (java.awt.Window parent, Language language, RAManagerException exception) {
		
		super (parent);
		setModal (true);
		
		setTitle (language.get("errorText"));
		
		setBounds(100, 100, 720, 223);
		getContentPane().setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane ();
		
		getContentPane ().add (new JLabel (language.get ("errorOcurred")), BorderLayout.NORTH);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelMessage 		= new JPanel();
		JPanel panelStackTrace 		= new JPanel();
		textAreaMessage 			= new JTextArea ();
		textAreaMessage.setFont(new Font("Dialog", Font.BOLD, 12));
		textAreaStackTrace 			= new JTextArea ();

		tabbedPane.addTab(language.get("messageText"), new ImageIcon(ExceptionDialog.class.getResource("/resources/error.png")), panelMessage);
		tabbedPane.addTab(language.get ("stackTrace"), panelStackTrace);
		
		panelMessage.setLayout (new BorderLayout ());
		panelStackTrace.setLayout (new BorderLayout ());
		textAreaMessage.setEditable(false);
		textAreaStackTrace.setEditable(false);
		textAreaMessage.setBackground(new Color(245, 245, 220));
		textAreaMessage.setForeground(new Color(165, 42, 42));
		textAreaStackTrace.setBackground(new Color(245, 245, 220));

		panelMessage.add (new JScrollPane (textAreaMessage), BorderLayout.CENTER);
		panelStackTrace.add (new JScrollPane (textAreaStackTrace), BorderLayout.CENTER);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton buttonClose = new JButton(language.get ("closeButton"));
				buttonClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog ();
					}
				});
				buttonClose.setActionCommand("OK");
				buttonPane.add(buttonClose);
				getRootPane().setDefaultButton(buttonClose);
			}
		}
		
		showData (exception, language);
		setVisible (true);
	}

	
	// --------------------------------------------------------------------------------------------
	protected void showData (RAManagerException exception, Language language)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace (printWriter);

		String translateError = language.getFromErrorCode(exception.getErrorCode());
		textAreaMessage.setText (translateError != null ? translateError : exception.getMessage());
		textAreaStackTrace.setText ("" + stringWriter);
		textAreaStackTrace.setCaretPosition(0);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void closeDialog() 
	{
		dispose ();
	}

}
