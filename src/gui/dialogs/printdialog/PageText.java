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
     * @param pageIndex	Índice de página
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
	public int getTotalPages() 
	{
		return textLines.length / linesPerPage () + 1;
	}

    // --------------------------------------------------------------------------------------------
	private int linesPerPage ()
	{
		return pageHeight / textSize;
	}
	
}
