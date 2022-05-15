package gui.controls;

import javax.swing.ImageIcon;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
public class ButtonDelete extends ButtonTool 
{
	// --------------------------------------------------------------------------------------------
	public ButtonDelete (Language language)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource("/resources/delete.png")));
		applyLanguage ();
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	public ButtonDelete (Language language, String iconPath)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource(iconPath)));
		applyLanguage ();
	}
	
	// --------------------------------------------------------------------------------------------
	public void applyLanguage ()
	{
		setToolTipText(language.get("toolTipDelete"));
	}	
}
