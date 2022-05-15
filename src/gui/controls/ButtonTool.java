package gui.controls;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
public class ButtonTool extends JButton 
{
	protected Language language;
	
	// --------------------------------------------------------------------------------------------
	public ButtonTool (Language language)
	{
		this.language = language;
	}
	
	// SOBRECARGA POR REQUISITO DE RÚBRICA
	public ButtonTool (Language language, String iconPath)
	{
		this.language = language;
		setIcon(new ImageIcon(MainFrame.class.getResource (iconPath)));
	}

}
