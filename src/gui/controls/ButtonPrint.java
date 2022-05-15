package gui.controls;

import javax.swing.ImageIcon;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
public class ButtonPrint extends ButtonTool 
{
	// --------------------------------------------------------------------------------------------
	public ButtonPrint (Language language)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource("/resources/printer.png")));
		applyLanguage ();
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	public ButtonPrint (Language language, String iconPath)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource (iconPath)));
		applyLanguage ();
	}

	
	// --------------------------------------------------------------------------------------------
	public void applyLanguage ()
	{
		setToolTipText(language.get("toolTipPrint"));
	}	
}
