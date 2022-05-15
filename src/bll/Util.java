package bll;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;

import gui.Language;

// ------------------------------------------------------------------------------------------------
/***
 * 	Clase de utilidades para la aplicacion
 *  
 * @author G1
 *
 */
public abstract class Util 
{
	// NO REQUIERE CONSTRUCTORES, CLASE ABSTRACTA PARA PROPORCIONAR FUNCIONES VARIADAS A LA APLICACIÓN
	
	// ------------------------------------------------------------------------------------------------
	/***
	 *  Convierte un número real en cadena con 2 decimales
	 * @param value - Valor a convertir a texto
	 * @return Valor convertido a texto
	 */
	public static String doubleToString (double value)
	{
		return String.format ("%.2f", value);
	}
	
	// ------------------------------------------------------------------------------------------------
	/***
	 * Obtiene el sumatorio de un array de valores.
	 * 
	 * @param values	- Valores para el cálculo de la factura.
	 * @return			- Total del cálculo.
	 */
	public static double getSum (double[] values)
	{
		BigDecimal total = new BigDecimal (0);
		
		for (int i = 0; i < values.length; i++)
		{
			total = total.add(new BigDecimal (values[i])); 
		}
		
		total = total.setScale(8, RoundingMode.HALF_UP);
		return total.doubleValue();
	}
	
    // --------------------------------------------------------------------------------------------
	/**
	 * Divide una cadena en subcadenas segun el ancho de columna y evitar sobresalir de la misma
	 * 
	 * @param text - Texto a dividir
	 * @param columnWidth - Ancho de columna
	 * @return Matriz de cadenas cortadas
	 */
    public static String[] splitForSize (String text, int columnWidth)
    {
        String[] lines = new String [text.length() / columnWidth + 1];

        int i;
        for (i = 0; i < lines.length - 1; i++)
        {
            lines[i] = text.substring (i * columnWidth, i * columnWidth + columnWidth);
        }

        lines[i] = text.substring (i* columnWidth);

        return lines;
    }

    // --------------------------------------------------------------------------------------------
    /**
     * Devuelve la rempresentacion de fecha y hora segun el formato iso 8601
     * @param localDateTime - Objeto de fecha y hora
     * @return Cadena de texto con la representacion en formato iso
     */
    public static String getIsoFormat (LocalDateTime localDateTime)
    {
    	return String.format("%04d-%02d-%02d %02d:%02d:%02d", 
    			localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(),
    			localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()
    			);
    }
    
    // --------------------------------------------------------------------------------------------
    /**
     * Devuelve datos de entidad segun su etiqueta y valor en formato de 4 columnas y ancho de columna
     * 
     * @param labelA - Etiqueta del primer valor
     * @param labelASize - Ancho de la etiqueta
     * @param textA - Texto del primer valor
     * @param textASize - Ancho del primer valor
     * @param labelB - Etiqueta del segundo valor
     * @param labelBSize - Ancho de la etiqueta
     * @param textB - Texto del segundo valor
     * @param textBSize - Ancho del segundo valor
     * @return Cadena de texto con los datos formateados
     */
    public static String getFormatedData (
        String labelA,  int labelASize,
        String textA,   int textASize,
        String labelB,  int labelBSize,
        String textB,   int textBSize
        )
    {
        String[] linesA = splitForSize (textA, textASize);
        String[] linesB = splitForSize (textB, textBSize);

        String resultText = "";
        int length = Math.max(linesA.length,  linesB.length);

        for (int i = 0; i < length; i++)
        {
            if (i == 0)
            {
                resultText += String.format (
                    "%-" + labelASize + "s" +
                    "%-" + textASize + "s" +
                    " %-" + labelBSize + "s" +
                    "%-" + textBSize + "s\n",
                    labelA + ":",
                    linesA[i],
                    labelB + ":",
                    linesB[i]
                    );
            }
            else
            {
                resultText += String.format (
                    "%-" + labelASize + "s" +
                    "%-" + textASize + "s" +
                    " %-" + labelBSize + "s" +
                    "%-" + textBSize + "s\n",
                    "",
                    i < linesA.length ? linesA[i] : "",
                    "",
                    i < linesB.length ? linesB[i] : ""
                    );
            }
        }

        return resultText;
    }

	// --------------------------------------------------------------------------------------------
    /**
     * Devuelve los datos de la orden de trabajo en formato de texto
     * 
     * @param language - Objeto de idioma
     * @param order - Objeto de orden de trabajo
     * @return Texto con los datos de la orden
     */
	public static String getOrderText (Language language, BOrder order) 
	{

		String orderText = "";
		
		if (order != null)
		{
			orderText += language.get ("menuItemOrder") + "\n";
			orderText += String.format ("%-20s %s\n", language.get ("billColumnRef") + ":", order.getId());
			orderText += String.format ("%-20s %s\n",language.get ("columnDateTime") + ":", order.getInputDate()); 
			orderText += String.format ("%-20s %s\n",language.get ("columnDescription") + ":", order.getDescription()); 
			orderText += String.format ("%-20s %s\n",language.get ("vehicle") + ":", order.getBVehicle().getRegistrationNumber()); 
			orderText += String.format ("%-20s %s\n",language.get ("columnOperator") + ":", order.getBEvaluator());
		}
		
		return orderText;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Convierte un arraylist tipo Double en un array convencional de tiop double[]
	 * 
	 * @param doubleArrayListValues ArrayList con los valores a convertir
	 * @return El array con los doubles
	 */
	public static double[] toDoubleArray (ArrayList <Double> doubleArrayListValues)
	{
		double[] doubleValues = doubleArrayListValues.isEmpty () ? null : new double[doubleArrayListValues.size()];
		
		int i = 0;
		for (double value : doubleArrayListValues)
		{
			doubleValues [i++] = value;
		}
	
		return doubleValues;
	}
	
}

