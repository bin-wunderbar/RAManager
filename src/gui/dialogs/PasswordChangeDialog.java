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
<<<<<<< HEAD
=======
/**
 * Dialogo para el cambio de contrasenna
 * 
 * @author G4
 *
 */
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
=======
	/**
	 * Iniciliza el dialogo de cambio de contrasenna
	 * 
	 * @param owner			- Ventana propietaria
	 * @param rAManager		- Gestor de datos
	 * @param language		- Objeto de idioma
	 * @param login			- Empleado que ha iniciado sesion
	 */
>>>>>>> V3.1-alertas
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
<<<<<<< HEAD
				if (changePassword ())
				{
					dispose ();
				}
=======
				changePassword ();
>>>>>>> V3.1-alertas
			}
		});
		panelButtons.add (buttonOk);
		
		getRootPane ().setDefaultButton (buttonOk);
		
		setVisible (true);
	}

	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
	protected boolean changePassword () 
=======
	protected void changePassword ()
	{
		if (verifyChangePassword ())
		{
			try {
				login.setPassword(new String (passwordFieldNew.getPassword()));
				rAManager.save (login);
				JOptionPane.showMessageDialog(this, language.get("passwordChangeOk"));
				dispose ();
			} catch (RAManagerException exception) {
				ValueChecks.showExceptionMessage (this, language, exception);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve cierto si la contrasenna puede ser cambiada
	 * 
	 * @return Cierto si la contrasenna puede ser cambiada
	 */
	protected boolean verifyChangePassword () 
>>>>>>> V3.1-alertas
	{
		String passwordOld 			= new String (passwordFieldOld.getPassword());
		String passwordNew			= new String (passwordFieldNew.getPassword());
		String passwordConfirm		= new String (passwordFieldConfirm.getPassword());

		ValueChecks valueChecks = new ValueChecks (owner, language);
		
<<<<<<< HEAD
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
=======
		if (login.verifyPassword(passwordOld))
		{
			if (valueChecks.isValidPassword(passwordFieldNew, language.get("passwordLabelNew")))
			{
				if (passwordNew.equals(passwordConfirm))
				{
					return true;
>>>>>>> V3.1-alertas
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