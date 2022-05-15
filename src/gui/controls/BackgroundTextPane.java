package gui.controls;

import javax.imageio.ImageIO;
import javax.swing.JTextPane;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

// ------------------------------------------------------------------------------------------------
public class BackgroundTextPane extends JTextPane 
{
	private Color color;
	private BufferedImage image;
	
	// --------------------------------------------------------------------------------------------
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
