package fromScratchTest;

import java.util.ArrayList;

// ------------------------------------------------------------------------------------------------
/***
 * Carga y ejecuta todas las pruebas definidas en el proyecto.
 * 
 * @author G1
 *
 */
public class RunTest 
{
	/***
	 * Lista con las pruebas unitarias.
	 */
	private ArrayList <UnitTest> list;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa y ejecuta las pruebas existentes.
	 */
	public RunTest ()
	{
		list = new ArrayList <> ();
		
		list.add(new UnitTestNumberToString ());
		list.add(new UnitTestGetSum ());
		
		run ();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ejecuta las pruebas.
	 */
	private final void run ()
	{
		for (UnitTest unitTest : list)
		{
			unitTest.run ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Devuelve los resultados de las pruebas en formato de tabla de texto.
	 * 
	 * @return	Texto encolumnado con los resultados de las pruebas.
	 */
	public String getResults ()
	{
		String results = UnitTest.getHeader();
		
		for (UnitTest unitTest : list)
		{
			results += unitTest;
		}
		
		return results;
	}

}
