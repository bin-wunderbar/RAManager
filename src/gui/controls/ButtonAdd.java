package gui.controls;

import javax.swing.ImageIcon;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
public class ButtonAdd extends ButtonTool 
{
	// --------------------------------------------------------------------------------------------
	public ButtonAdd (Language language)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource("/resources/add.png")));
		applyLanguage ();
	}

	// --------------------------------------------------------------------------------------------
	// SOBRECARGA POR REQUISITO DE RÚBRICA
	public ButtonAdd (Language language, String iconPath)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource(iconPath)));
		applyLanguage ();
	}
	
	// --------------------------------------------------------------------------------------------
	public void applyLanguage ()
	{
		setToolTipText(language.get("toolTipAdd"));
	}
	
}
