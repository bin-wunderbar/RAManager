package gui.controls;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
/**
 * Boton especializado de la aplicacion, usa una imagen y lenguaje
 * 
 * @author G4
 *
 */
@SuppressWarnings("serial")
public class ButtonTool extends JButton 
{
	protected Language language;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa mediante un objeto de lenguaje
	 * @param language - Lenguaje
	 */
	public ButtonTool (Language language)
	{
		this.language = language;
	}
	
	// SOBRECARGA POR REQUISITO DE RÚBRICA
	/**
	 * Inicializa mediante un objeto de lenguaje e icono
	 * @param language - Lenguaje
	 * @param iconPath - Ruta del icono
	 */
	public ButtonTool (Language language, String iconPath)
	{
		this.language = language;
		setIcon(new ImageIcon(MainFrame.class.getResource (iconPath)));
	}

}
