package gui.controls;

import javax.swing.ImageIcon;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
public class ButtonCancel extends ButtonTool 
{
	// --------------------------------------------------------------------------------------------
	public ButtonCancel (Language language)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource("/resources/cancel.png")));
		applyLanguage ();
	}
	
	// SOBRECARGA POR REQUISITO DE RÚBRICA
	public ButtonCancel (Language language, String iconPath)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource (iconPath)));
		applyLanguage ();
	}
	
	// --------------------------------------------------------------------------------------------
	public void applyLanguage ()
	{
		setToolTipText(language.get("toolTipCancel"));
	}	
}
