package gui.controls;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
/**
 * Boton especializado de la aplicacion, usa una imagen y lenguaje
 * 
 * @author G4
 *
 */
@SuppressWarnings("serial")
>>>>>>> V3.1-alertas
public class ButtonTool extends JButton 
{
	protected Language language;
	
	// --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Inicializa mediante un objeto de lenguaje
	 * @param language - Lenguaje
	 */
>>>>>>> V3.1-alertas
	public ButtonTool (Language language)
	{
		this.language = language;
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
	public ButtonTool (Language language, String iconPath)
	{
		this.language = language;
		setIcon(new ImageIcon(MainFrame.class.getResource (iconPath)));
	}

}
