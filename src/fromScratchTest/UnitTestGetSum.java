package fromScratchTest;

import bll.Util;

// ----------------------------------------------------------------------------------------------------
/***
 * Define una prueba para el método getTotalBillWithIva
 * 
 * @author G1
 *
 */
public class UnitTestGetSum extends UnitTest
{

	// ------------------------------------------------------------------------------------------------
	/***
	 * Ejecuta una prueba
	 * 
	 * @param values		Array con los valores de entrada
	 * @param waitedValue	Valor esperado
	 */
	public void test (double[] values, String waitedValue)
	{
		
		String returnValue = "" + Util.getSum(values);
	
		String success = waitedValue.equals(returnValue) ? "ok" : "fail";
		
		String valuesText = "";
		for (int i = 0; i < values.length; i++) valuesText += values[i] + " "; 
		
		appendResult (success, "getSum", "" + valuesText, waitedValue, returnValue);
	}
	
	// ------------------------------------------------------------------------------------------------
	/***
	 * Ejecuta varias pruebas
	 */
	@Override
	public void run() 
	{
		test (new double[] {12.5, 5, 60.2}, "77.7");
		test (new double[] {20.1, 30.2, 40.3}, "90.6");
		test (new double[] {1.5, 2.6, 3.7}, "7.8");
	}
}
