package gui.dialogs.printdialog;

// ------------------------------------------------------------------------------------------------
/**
 * Pagina un texto a partir de las especificaciones de la altura de cada página y el tamaño de la fuente
 * @author dani
 *
 */
public class PageText
{
    private String[] textLines;
    private int pageHeight;
    private int textSize;

    // --------------------------------------------------------------------------------------------
    /**
     * Inicializa el paginador a partir del texto indicado
     * 
     * @param text - Texto a paginar
     * @param pageHeight - Altura de la página
     * @param textSize - Tamaño / altura de la fuente
     */
    public PageText (String text, int pageHeight, int textSize)
    {
    	initPageText (text, pageHeight, textSize, 0);    }

    // --------------------------------------------------------------------------------------------
    /**
     * Inicializa el paginador a partir del texto indicado
     * 
     * @param text - Texto a paginar
     * @param pageHeight - Altura de la página
     * @param textSize - Tamaño / altura de la fuente
     * @param initialOffsetLines - Líneas de desplazamiento vacías iniciales, este parámetro es útil para dejar sitio a una posible cabecera en la primera página
     */
    public PageText (String text, int pageHeight, int textSize, int initialOffsetLines)
    {
    	initPageText (text, pageHeight, textSize, initialOffsetLines);
    }

    // --------------------------------------------------------------------------------------------
    /**
     * Inicializa el paginador a partir del texto indicado
     * 
     * @param text - Texto a paginar
     * @param pageHeight - Altura de la página
     * @param textSize - Tamaño / altura de la fuente
     * @param initialOffsetLines - Líneas de desplazamiento vacías iniciales, este parámetro es útil para dejar sitio a una posible cabecera en la primera página
     */
    private final void initPageText (String text, int pageHeight, int textSize, int initialOffsetLines)
    {
        this.pageHeight = pageHeight;
        this.textSize   = textSize;
        textLines		= (getNewLines (initialOffsetLines) + text).split("\n");
    }
    
    // --------------------------------------------------------------------------------------------
    /**
     * Devuelve la página correspondiente al índice de página indicado
     * 
<<<<<<< HEAD
     * @param pageIndex	Índice de página
=======
     * @param pageIndex	Indice de página
>>>>>>> V3.1-alertas
     * @return Líneas de la página indicada <strong>null</strong> en caso que la página no exista
     */
    public String[] getPage (int pageIndex)
    {
    	String[] pageLines  = null;

        int lineOffset      = pageIndex * pageHeight / textSize;
        
        if (lineOffset < textLines.length)
        {
            int maxPageLines    = pageHeight / textSize;

            int linesOfPage;
            
            if (textLines.length - lineOffset > maxPageLines)
            {
            	linesOfPage = maxPageLines;
            }
            else
            {
            	linesOfPage = textLines.length % maxPageLines;
            }

            if (linesOfPage > 0)
            {
                pageLines = new String[linesOfPage];

                for (int i = 0; i < pageLines.length; i++)
                {
                    pageLines[i] = textLines[lineOffset + i];
                }
            }
        }

        return pageLines;
    }

    // --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
    /**
     * Devuelve un numero de lineas vacias para desplazar la pagina
     * 
     * @param newLines - Numero de lineas a desplazar
     * @return Cadena de texto con las lineas vacias
     */
>>>>>>> V3.1-alertas
    private String getNewLines (int newLines)
    {
    	String lines = "";
    	
    	for (int i = 0; i < newLines; i++)
    	{
    		lines += "\n";
    	}
    	
    	return lines;
    }
    
    // --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
    /**
     * Devuelve el total de paginas disponibles
     * 
     * @return numero total de paginas disponibles
     */
>>>>>>> V3.1-alertas
	public int getTotalPages() 
	{
		return textLines.length / linesPerPage () + 1;
	}

    // --------------------------------------------------------------------------------------------
<<<<<<< HEAD
=======
	/**
	 * Devuelve el numero de lineas por pagina
	 * 
	 * @return Numero de lineas por pagina
	 */
>>>>>>> V3.1-alertas
	private int linesPerPage ()
	{
		return pageHeight / textSize;
	}
	
}
