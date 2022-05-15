package fromScratchTest;

// ------------------------------------------------------------------------------------------------
/***
 * Clase abstracta que define la funcionalidad base de una prueba unitaria.
 * 
 * @author G1
 *
 */
public abstract class UnitTest 
{
	/***
	 * Resultado de la prueba.
	 */
	private String textResult;
	
	/***
	 * Formato de tabla de las pruebas.
	 */
	public static String fieldsFormat = "%-8s %-20s %-20s %-12s %-12s\n";
			
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa la prueba.
	 */
	public UnitTest ()
	{
		this.textResult = "";
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Agrega los valores de la prueba al resultado en formato de texto 
	 * 
	 * @param success		Valor de exito o fallo
	 * @param functionName	Nombre de la función empleada
	 * @param values		Valores de entrada
	 * @param waitedValue	Valor esperado
	 * @param returnValue	Valor obtenido de la prueba
	 */
	public void appendResult (String success, String functionName, String values, String waitedValue, String returnValue)
	{
		textResult += String.format (fieldsFormat, "[" + success + "]", functionName, values, waitedValue, returnValue);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ejecuta las operaciones definidas en la prueba
	 */
	public abstract void run ();
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve la cabecera de la tabla de resultados
	 * 
	 * @return cabecera formateada en columnas de texto 
	 */
	public static String getHeader ()
	{
		return String.format(fieldsFormat, "Success", "Function", "Values", "WaitedValue", "ReturnValue");
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public String toString ()
	{
		return textResult;
	}
	
	/**
	 * @return the textResult
	 */
	public String getTextResult() {
		return textResult;
	}

	/**
	 * @param textResult the textResult to set
	 */
	public void setTextResult(String textResult) {
		this.textResult = textResult;
	}


}
