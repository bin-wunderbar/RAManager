package gui.dialogs.printdialog;

import java.awt.print.PrinterJob;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.image.BufferedImage;

// ------------------------------------------------------------------------------------------------
/**
 * Gestiona la impresion de la factura
 * 
 * @author G4
 *
 */
public class PrintDataBill 
{
	private int fontSize;
	private PrinterJob printerJob;
	private Font font;
	private String text;
	private BufferedImage imageHeader;
	private static final int MARGIN_LINES = 2;
	
	private static final int HORIZONTAL_MARGIN = 72;
	private static final int VERTICAL_MARGIN = 72;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicializa la impresion con un tamanno de fuente
	 * 
	 * @param fontSize - Tamanno de fuente
	 */
	PrintDataBill (int fontSize)
	{
		printerJob 		= PrinterJob.getPrinterJob();
		this.fontSize	= fontSize;
		font 			= new Font (Font.MONOSPACED, Font.PLAIN, fontSize);
	}

	// SOBRECARGA POR REQUISITO DE RÚBRICA
	/**
	 * Inicializa la impresion 
	 */
	PrintDataBill ()
	{
		printerJob 		= PrinterJob.getPrinterJob();
		fontSize		= 6;
		font 			= new Font (Font.MONOSPACED, Font.PLAIN, fontSize);
	}

	
	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve cierto si se puede lanzar el dialogo de seleccion de impresion del sistema
	 * 
	 * @return Cierto si se puede lanzar el dialog de seleccion de impresion del sistema
	 * @throws PrinterException
	 */
	public boolean printDialog () throws PrinterException
	{
		
		printerJob.setPrintable (getPrintable ());
		if (printerJob.printDialog ())
		{
		   printerJob.print ();
		   return true;
		}
		
		return false;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Ajusta el texto a imprimir
	 * 
	 * @param text - Texto a imprimir
	 */
	public void setText (String text)
	{
		this.text = text;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve un objeto imprimible para controlador de impresion
	 * 
	 * @return Objeto imprimible
	 */
	private Printable getPrintable ()
	{
		return new Printable () {
			@Override
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

				// int imageableX =  (int)pageFormat.getImageableX ();
				// int imageableY =  (int)pageFormat.getImageableY ();
				int imageableX =  HORIZONTAL_MARGIN;
				int imageableY =  VERTICAL_MARGIN;
				int pageHeight =  (int)pageFormat.getImageableHeight ();
				int pageWidth  =  (int)pageFormat.getImageableWidth () - 2;
				
				int initialOffset = 0;
				
				if (imageHeader != null)
				{

					int imageWidth = (int)(pageFormat.getWidth () - HORIZONTAL_MARGIN * 2);
					double widthRatio = (double)pageWidth / imageHeader.getWidth ();
					int imageHeight = (int)(imageHeader.getHeight () * widthRatio);

					if (pageIndex == 0)
					{
						graphics.drawImage (imageHeader, imageableX, imageableY, imageWidth, imageHeight, null);
					}

					initialOffset = imageHeight / fontSize + MARGIN_LINES;

				}
				
				PageText pageText = new PageText (text, pageHeight, fontSize, initialOffset);
				
				if (pageIndex < pageText.getTotalPages ())
				{
					
					graphics.setFont (font);
					
					String[] pageLines = pageText.getPage(pageIndex);
					
					if (pageLines != null)
					{
						for (int y = 0; y < pageLines.length; y++)
						{
							graphics.drawString (pageLines[y], imageableX, imageableY + y * fontSize);
						}
					}
					
					return PAGE_EXISTS;
				} 
				else 
				{
					return NO_SUCH_PAGE;
				}
			}
		};
	}

	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------
	/**
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the imageHeader
	 */
	public BufferedImage getImageHeader() {
		return imageHeader;
	}

	/**
	 * @param imageHeader the imageHeader to set
	 */
	public void setImageHeader(BufferedImage imageHeader) {
		this.imageHeader = imageHeader;
	}	
	
}
