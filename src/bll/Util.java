package bll;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;

import gui.Language;

// ------------------------------------------------------------------------------------------------
/***
 * 	Clase de utilidades para la aplicación
 *  
 * @author G1
 *
 */
public abstract class Util 
{
	// NO REQUIERE CONSTRUCTORES, CLASE ABSTRACTA PARA PROPORCIONAR FUNCIONES VARIADAS A LA APLICACIÓN
	
	// ------------------------------------------------------------------------------------------------
	/***
	 *  Convierte un número real en cadena.
	 *  Si el número no requiere decimales se eliminan de la conversión (ejemplo: 52.0 => "52")
	 * @param value		- Valor a convertir a texto
	 * @return			- Valor convertido a texto
	 */
	public static String numberToString (double value)
	{
		return value > (int)value ? "" + value : "" + (int)value;
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
    public static String[] splitForSize (String text, int size)
    {
        String[] lines = new String [text.length() / size + 1];

        int i;
        for (i = 0; i < lines.length - 1; i++)
        {
            lines[i] = text.substring (i * size, i * size + size);
        }

        lines[i] = text.substring (i* size);

        return lines;
    }

    // --------------------------------------------------------------------------------------------
    public static String getIsoFormat (LocalDateTime localDateTime)
    {
    	return String.format("%04d-%02d-%02d %02d:%02d:%02d", 
    			localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(),
    			localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()
    			);
    }
    
    // --------------------------------------------------------------------------------------------
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

