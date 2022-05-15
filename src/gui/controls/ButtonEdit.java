package gui.controls;

import javax.swing.ImageIcon;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
public class ButtonEdit extends ButtonTool 
{
	// --------------------------------------------------------------------------------------------
	public ButtonEdit (Language language)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource("/resources/edit.png")));
		applyLanguage ();
	}
	
	// SOBRECARGA POR REQUISITO DE RÚBRICA
	public ButtonEdit (Language language, String iconPath)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource(iconPath)));
		applyLanguage ();
	}
	
	// --------------------------------------------------------------------------------------------
	public void applyLanguage ()
	{
		setToolTipText(language.get("toolTipEdit"));
	}	
}
