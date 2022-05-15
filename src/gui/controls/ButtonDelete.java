package gui.controls;

import javax.swing.ImageIcon;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
<<<<<<< HEAD
public class ButtonDelete extends ButtonTool 
{
	// --------------------------------------------------------------------------------------------
=======
/**
 * Boton especializado de la aplicacion, usa una imagen y lenguaje
 * 
 * @author G4
 *
 */
@SuppressWarnings("serial")
public class ButtonDelete extends ButtonTool 
{
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa mediante un objeto de lenguaje
	 * @param language - Lenguaje
	 */
>>>>>>> V3.1-alertas
	public ButtonDelete (Language language)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource("/resources/delete.png")));
		applyLanguage ();
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
<<<<<<< HEAD
=======
	/**
	 * Inicializa mediante un objeto de lenguaje e icono
	 * @param language - Lenguaje
	 * @param iconPath - Ruta del icono
	 */
>>>>>>> V3.1-alertas
	public ButtonDelete (Language language, String iconPath)
	{
		super (language);
		
		setIcon(new ImageIcon(MainFrame.class.getResource(iconPath)));
		applyLanguage ();
	}
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Aplica el idioma
	 */
>>>>>>> V3.1-alertas
	public void applyLanguage ()
	{
		setToolTipText(language.get("toolTipDelete"));
	}	
}
