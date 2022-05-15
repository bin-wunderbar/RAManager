package fromScratchTest;

import bll.Util;

// ------------------------------------------------------------------------------------------------
/***
 * Define una prueba para el método numberToString 
 * 
 * @author G1
 *
 */
public class UnitTestNumberToString extends UnitTest 
{
	// ------------------------------------------------------------------------------------------------
	/***
	 * Ejecuta una prueba
	 * 
	 * @param value			Valor de entrada
	 * @param waitedValue	Valor esperado
	 */
	public void test (double value, String waitedValue)
	{
		
		String returnValue = Util.doubleToString (value);
		
		String success = waitedValue.equals(returnValue) ? "ok" : "fail";
		
		appendResult (success, "numberToString", "" + value, waitedValue, returnValue);
	}
	
	// ------------------------------------------------------------------------------------------------
	/***
	 * Ejecuta varias pruebas
	 */
	@Override
	public void run() 
	{
		test (12.5, "12.5");
		test (82, "82");
		test (7712.512, "7712.512");
	}
}
