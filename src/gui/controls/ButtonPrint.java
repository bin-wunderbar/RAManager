package gui.controls;

import javax.swing.ImageIcon;

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
public class ButtonPrint extends ButtonTool 
{
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa mediante un objeto de lenguaje
	 * @param language - Lenguaje
	 */
	public ButtonPrint (Language language)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource("/resources/printer.png")));
		applyLanguage ();
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	/**
	 * Inicializa mediante un objeto de lenguaje e icono
	 * @param language - Lenguaje
	 * @param iconPath - Ruta del icono
	 */
	public ButtonPrint (Language language, String iconPath)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource (iconPath)));
		applyLanguage ();
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Aplica el idioma
	 */
	public void applyLanguage ()
	{
		setToolTipText(language.get("toolTipPrint"));
	}	
}
