package gui.dialogs.printdialog;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Font;

import java.io.IOException;

import javax.imageio.ImageIO;

import gui.Language;
import gui.forms.mainframe.MainFrame;

// ------------------------------------------------------------------------------------------------
/***
 * Clase de canvas para el logo de la factura
 * @author G1
 */

@SuppressWarnings("serial")
public class CanvasLogo extends Canvas 
{
	private Image image;
	private Language language;
	private BufferedImage imageHeader;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el objeto de dibujo
	 * 
	 * @param language - Idioma
	 */
	public CanvasLogo (Language language)
	{
		initCanvasLogo (language, "/resources/rekord_autoak_logo.png");
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	/***
	 * Inicializa el objeto de dibujo
	 * 
	 * @param language 		- Idioma
	 * @param logoFileName 	- Nombre de archivo con la imagen del logo 
	 */
	public CanvasLogo (Language language, String logoFileName)
	{
		initCanvasLogo (language, logoFileName);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa el objeto de dibujo
	 * 
	 * @param language 		- Idioma
	 * @param logoFileName 	- Nombre de archivo con la imagen del logo 
	 */
	private final void initCanvasLogo (Language language, String logoFileName)
	{
		this.language = language;
		
		try {
			image = ImageIO.read(MainFrame.class.getResource (logoFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		setSize (new Dimension (400, 128));
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Evento de redibujado del componente
	 */
	public void paint (Graphics g)
	{
		g.drawImage (getHeader (getWidth (), getHeight ()), 0, 0, this);
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve una imagen con la cabecera segun el ancho y alto especificados
	 * 
	 * @param width		- Ancho de la cabecera
	 * @param height	- Alto de la cabecera
	 * @return La imagen que representa a la cabecera
	 */
	public BufferedImage getHeader (int width, int height)
	{

		imageHeader = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = imageHeader.getGraphics ();

		graphics.setColor (Color.WHITE);
		graphics.fillRect (0, 0, width, height);
		graphics.drawImage (image, 0, 0, this);
		graphics.setColor (Color.CYAN);
		graphics.drawRect (0, 0, width - 1, height - 1);
		graphics.setColor (Color.BLACK);
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 40));
		graphics.drawString (language.get ("companyName"), 250, 60);
		
		return imageHeader;
	}
	
}
