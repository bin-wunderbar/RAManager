package gui.controls;

import javax.imageio.ImageIO;
import javax.swing.JTextPane;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

// ------------------------------------------------------------------------------------------------
/**
 * Especializa un objeto JTextPane permitiendo indicar una imagen de fondo
 * 
 * @author G4
 *
 */
@SuppressWarnings("serial")
public class BackgroundTextPane extends JTextPane 
{
	private Color color;
	private BufferedImage image;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa el objeto con la ruta de la imagen procedente de recursos internos y un color
	 * 
	 * @param imageInnerResourcePath - Imagen procedente de recursos internos
	 * @param color - Color
	 */
	public BackgroundTextPane (String imageInnerResourcePath, Color color)
	{
		this.color = color;
		setOpaque (false);
		
		try {
			image = ImageIO.read(getClass ().getResourceAsStream(imageInnerResourcePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// --------------------------------------------------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor (color);
        g.fillRect (0, 0, getWidth (), getHeight ());

        int width = getWidth ();
        int height = (int)(getHeight () * ((double)width / getHeight ()));
        
        g.drawImage(image, 0, 0, width, height, this);
        super.paintComponent(g);
    }
    	
}
