package gui.dialogs;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import bll.BEmployee;
import bll.RAManager;
import bll.RAManagerException;
import gui.Language;
import gui.ValueChecks;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;

// ------------------------------------------------------------------------------------------------
public class PasswordChangeDialog extends JDialog 
{
	private RAManager rAManager;
	private Language language;
	private JPasswordField passwordFieldOld;
	private JPasswordField passwordFieldNew;
	private JPasswordField passwordFieldConfirm;
	
	private JPanel panelPasswords;
	private JPanel panelButtons;
	private JButton buttonOk;
	private JButton buttonCancel;
	
	private BEmployee login;
	private JFrame owner;
	
	// ------------------------------------------------------------------------------------------------
	public PasswordChangeDialog (JFrame owner, RAManager rAManager, Language language, BEmployee login)
	{
		super (owner, true);
		this.owner		= owner;
		this.rAManager	= rAManager;
		this.language	= language;
		this.login 		= login;
	
		setTitle (language.get("menuItemPasswordChange"));
		
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
	
		Rectangle bounds = owner.getBounds ();
		setBounds (bounds.x + 50, bounds.y + 50, 500, 150);
		
		getContentPane ().setLayout (new BorderLayout ());
		getContentPane ().add (panelPasswords = new JPanel (), BorderLayout.CENTER);
		
		panelPasswords.setLayout (new GridLayout (0, 2));
		panelPasswords.setBorder (new EmptyBorder (5, 5, 5, 5));
		
		passwordFieldOld 		= new JPasswordField ();
		passwordFieldNew 		= new JPasswordField ();
		passwordFieldConfirm 	= new JPasswordField ();
		
		panelPasswords.add (new JLabel (language.get ("passwordLabelOld")));
		panelPasswords.add (passwordFieldOld);
		panelPasswords.add (new JLabel (language.get ("passwordLabelNew")));
		panelPasswords.add (passwordFieldNew);
		panelPasswords.add (new JLabel (language.get ("passwordLabelConfirm")));
		panelPasswords.add (passwordFieldConfirm);
		
		panelButtons = new JPanel ();
		panelButtons.setLayout (new FlowLayout (FlowLayout.RIGHT));
		panelButtons.setBorder(new BevelBorder (BevelBorder.LOWERED));
		getContentPane ().add (panelButtons, BorderLayout.SOUTH);
		
		buttonCancel = new JButton (language.get("cancelButton"));
		buttonCancel.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				dispose ();
			}
		});
		panelButtons.add (buttonCancel);
		
		buttonOk = new JButton (language.get("okButton"));
		buttonOk.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent actionEvent)
			{
				if (changePassword ())
				{
					dispose ();
				}
			}
		});
		panelButtons.add (buttonOk);
		
		getRootPane ().setDefaultButton (buttonOk);
		
		setVisible (true);
	}

	// --------------------------------------------------------------------------------------------
	protected boolean changePassword () 
	{
		String passwordOld 			= new String (passwordFieldOld.getPassword());
		String passwordNew			= new String (passwordFieldNew.getPassword());
		String passwordConfirm		= new String (passwordFieldConfirm.getPassword());

		ValueChecks valueChecks = new ValueChecks (owner, language);
		
		if (login.getPassword().equals (passwordOld))
		{
			if (valueChecks.isValidPassword(passwordFieldNew))
			{
				if (passwordNew.equals(passwordConfirm))
				{
					login.setPassword(passwordNew);
					try {
						rAManager.save (login);
						return true;
					} catch (RAManagerException exception) {
						ValueChecks.showExceptionMessage (this, exception);
					}
				}
				else
				{
					JOptionPane.showMessageDialog (this, language.get ("badPasswordEquals"), language.get("errorText"), JOptionPane.ERROR_MESSAGE);
					passwordFieldNew.requestFocus();
					passwordFieldNew.selectAll();
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog (this, language.get ("badOldPassword"), language.get("errorText"), JOptionPane.ERROR_MESSAGE);
			passwordFieldOld.requestFocus();
			passwordFieldOld.selectAll();
		}

		return false;
	}
	
}