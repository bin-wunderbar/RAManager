package gui.controls;

import javax.swing.ImageIcon;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
public class ButtonSave extends ButtonTool 
{
	// --------------------------------------------------------------------------------------------
	public ButtonSave (Language language)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource("/resources/save.png")));
		applyLanguage ();
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	public ButtonSave (Language language, String iconPath)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource (iconPath)));
		applyLanguage ();
	}
	
	// --------------------------------------------------------------------------------------------
	public void applyLanguage ()
	{
		setToolTipText(language.get("toolTipSave"));
	}	
}
