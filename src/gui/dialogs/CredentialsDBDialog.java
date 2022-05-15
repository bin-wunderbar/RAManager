package gui.dialogs;

import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import gui.Language;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// ------------------------------------------------------------------------------------------------
@SuppressWarnings("serial")
public class CredentialsDBDialog extends JDialog 
{
	private JTextField textFieldServer;
	private JTextField textFieldUser;
	private JPasswordField passwordFieldPassword;
	private String server;
	private String user;
	private String password;
	
	// --------------------------------------------------------------------------------------------
	public CredentialsDBDialog (JFrame owner, Language language, String message, String server, String user, String password)
	{
		super (owner, true);
	
		setTitle (language.get("credentials"));
		
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		initComponents (language, message);
		
		Rectangle bounds = owner.getBounds ();
		setBounds (bounds.x + 50, bounds.y + 50, 600, 250);
		load (server, user, password);
		setVisible (true);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void initComponents (Language language, String message)
	{
		JPanel panelMessage				= new JPanel ();
		JPanel panelFields 				= new JPanel ();
		JPanel panelButtons 			= new JPanel ();
		textFieldServer					= new JTextField ();
		textFieldUser					= new JTextField ();
		passwordFieldPassword			= new JPasswordField ();
		JButton buttonOk				= new JButton (language.get ("okButton"));
		JLabel labelMessage 			= new JLabel (message);
		
		labelMessage.setForeground (new Color (0xB03A2E));
		
		panelMessage.setBorder(new EmptyBorder (5, 5, 5, 5));
		panelMessage.setLayout (new BorderLayout ());
		panelFields.setBorder(new EmptyBorder (5, 5, 5, 5));
		panelFields.setLayout (new GridLayout (0, 2));
		panelButtons.setBorder(new BevelBorder (BevelBorder.LOWERED));
		panelButtons.setLayout (new FlowLayout (FlowLayout.RIGHT));
		
		panelMessage.add (labelMessage, BorderLayout.CENTER);
		panelFields.add (new JLabel (language.get ("sqlServer")));
		panelFields.add (textFieldServer);
		panelFields.add (new JLabel (language.get ("sqlUser")));
		panelFields.add (textFieldUser);
		panelFields.add (new JLabel (language.get ("sqlPassword")));
		panelFields.add (passwordFieldPassword);
		panelButtons.add (buttonOk);
		
		buttonOk.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				acceptAction ();
			}
		});
		
		getRootPane ().setDefaultButton(buttonOk);
		
		getContentPane ().add (panelMessage, BorderLayout.NORTH);
		getContentPane ().add (panelFields, BorderLayout.CENTER);
		getContentPane ().add (panelButtons, BorderLayout.SOUTH);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void load (String server, String user, String password)
	{
		textFieldServer.setText(server);
		textFieldUser.setText(user);
		passwordFieldPassword.setText (password);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void acceptAction() 
	{
		server		= textFieldServer.getText();
		user 		= textFieldUser.getText();
		password 	= new String (passwordFieldPassword.getPassword());
		
		dispose ();
	}

	// --------------------------------------------------------------------------------------------
	public boolean isAccept() {
		return server != null && user != null && password != null;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	public String getServer() {
		return server;
	}

	
	
}
