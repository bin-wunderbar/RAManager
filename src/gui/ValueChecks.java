package gui;

import java.util.Locale;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Window;
import java.io.PrintWriter;
import java.io.StringWriter;

// ------------------------------------------------------------------------------------------------
/***
 * Clase de comprobación de los valores introducidos para el proyecto Rekord Autoak (RA)
 * @author G1
 *
 */
public class ValueChecks 
{
	private Language language;
	private Window owner;
	
	public final static int HOUR_PRICE_MIN 				= 5;
	public final static int HOUR_PRICE_MAX				= 100;
	
	public final static int DEDICATED_TIME_MIN			= 15;
	public final static int DEDICATED_TIME_MAX			= 115200;
	
	public final static int MATERIAL_UNITS_MIN			= 1;
	public final static int MATERIAL_UNITS_MAX			= 100;
	
	public final static int MATERIAL_PRICE_MIN			= 1;
	public final static int MATERIAL_PRICE_MAX			= 10000;
	
	public final static int TEXTFIELD_MIN_LENGTH		= 4;
	
	public final static int IVA_MIN_VALUE				= 0;
	public final static int IVA_MAX_VALUE				= 80;
	
	public final static int PRINT_FONT_SIZE_MIN_VALUE	= 1;
	public final static int PRINT_FONT_SIZE_MAX_VALUE	= 100;
	
	public final static int PASSWORDFIELD_MIN_LENGTH	= 8;
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Inicializa las comprobaciones
	 * @param owner - Ventana superior
	 * @param language - Idioma
	 */
	public ValueChecks (Window owner, Language language)
	{
		this.owner 		= owner;
		this.language 	= language;
	}

	
	// --------------------------------------------------------------------------------------------
	public boolean isValidPassword(JPasswordField passwordField) 
	{
		if (passwordField.getPassword().length < PASSWORDFIELD_MIN_LENGTH)
		{
			JOptionPane.showMessageDialog (owner, 
					String.format (language.get("minLengthPassword"), PASSWORDFIELD_MIN_LENGTH), 
					language.get ("ErrorText"), JOptionPane.ERROR_MESSAGE);
			
			passwordField.requestFocus();
			return false;
		}
		
		return true;
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación de la longitud del nombre
	 * @param textFieldName - Campo de texto que recoge el nombre
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean isValidTextField (JTextField textFieldName)
	{
		if (textFieldName.getText ().length() < TEXTFIELD_MIN_LENGTH)
		{
			JOptionPane.showMessageDialog (owner, String.format(language.get ("minLengthTextField"), TEXTFIELD_MIN_LENGTH), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
			setFocus (textFieldName);
			return false;
		}
		
		return true;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación de los caracteres del teléfono
	 * @param textFieldPhone - Campo de texto que recoge el teléfono
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean isValidPhone(JTextField textFieldPhone)
	{
		return isValidPattern (textFieldPhone, "^\\d{11}$", "No es un teléfono válido.");
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación de los caracteres del Email
	 * @param textFieldEmail - Campo de texto que recoge el Email
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean isValidEmail(JTextField textFieldEmail) 
	{
		return isValidPattern (textFieldEmail, "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$", language.get ("invalidEmail"));
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación de los caracteres del DNI
	 * @param textFieldDNI - Campo de texto que recoge el DNI
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean isValidNIF (JTextField textFieldDNI)
	{
		return isValidPattern (textFieldDNI, "[0-9]{7,8}[A-Z a-z]", language.get ("invalidDNI"));
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación del patrón
	 * @param textField - Campo de texto
	 * @param pattern - Patrón
	 * @param message - Mensaje
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean isValidPattern (JTextField textField, String pattern, String message)
	{
		boolean returnValue;
		
        Pattern patternDNI = Pattern.compile(pattern);
        Matcher matcher = patternDNI.matcher(textField.getText()); 
		
        returnValue = matcher.find();
        
		if (!returnValue)
		{
			JOptionPane.showMessageDialog(owner, message, language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
			setFocus (textField);
		}
		
		return returnValue;		
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Verifica que el campo tenga un único carácter
	 * @param textField - Campo de texto a verificar
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean isCharacterValue (JTextField textField)
	{
		boolean returnValue = false;
		
		String value = textField.getText();
		if (value.length() == 1)
		{
			returnValue = true;
		}
		else
		{
			JOptionPane.showMessageDialog (owner, language.get ("isNotValidCharacter"), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
			setFocus (textField);
		}
		
		return returnValue;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Comprueba que el valor del campo de texto de IVA esté dentro de el rango permitido
	 * @param textField - Campo de texto con el impuesto al valor añadido
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean isValidIVA (JTextField textField)
	{
		return rangeDoubleOf (textField, language.get ("rangeOfIVA"), IVA_MIN_VALUE, IVA_MAX_VALUE);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Comprueba que el valor del campo de texto del tamaño de la fuente a imprimir esté dentro del rango permitido
	 * @param textField - Campo de texto con el valor a comprobar 
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario 
	 */
	public boolean rangeOfPrintFontSize(JTextField textField) 
	{
		return rangeDoubleOf (textField, language.get("rangeOfPrintFontSize"), PRINT_FONT_SIZE_MIN_VALUE, PRINT_FONT_SIZE_MAX_VALUE);
	}

	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación del rango del precio aplicado del Material
	 * @param value - Valor del precio aplicado del material
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean rangeOfMaterialPriceApplied (String value)
	{
		return rangeDoubleOf (value, language.get ("rangeOfMaterialPriceApplied"), MATERIAL_PRICE_MIN, MATERIAL_PRICE_MAX);	
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación del rango de unidades del material
	 * @param value - unidades del material
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean rangeOfMaterialUnits (String value)
	{
		return rangeDoubleOf (value, language.get ("rangeOfMaterialUnits"), MATERIAL_UNITS_MIN, MATERIAL_UNITS_MAX);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación del rango del precio por Hora
	 * @param textFieldValue - Campo de texto que recoge el valor
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean rangeOfHourPriceApplied (JTextField textFieldValue)
	{
		return rangeDoubleOf (textFieldValue, language.get ("rangeOfHourPriceApplied"), HOUR_PRICE_MIN, HOUR_PRICE_MAX);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación del rango de Tiempo dedicado
	 * @param textFieldValue - Campo de texto que recoge el valor
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean rangeOfDedicatedTime(JTextField textFieldValue) 
	{

		return rangeIntegerOf (textFieldValue, language.get ("rangeOfDedicatedTime"), DEDICATED_TIME_MIN, DEDICATED_TIME_MAX);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación del rango de un entero especificado en un campo de texto
	 * @param textFieldValue - Campo de texto que recoge el valor
	 * @param rangeMessage - Mensaje de error de rango
	 * @param minValue - Valor mínimo
	 * @param maxValue  - Valor máximo
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean rangeIntegerOf (JTextField textFieldValue, String rangeMessage, int minValue, int maxValue)
	{
		boolean returnValue = false;
		String textValue = textFieldValue.getText();
		
		try
		{
			int intValue = Integer.parseInt(textValue);
			returnValue = checkRange (intValue, rangeMessage, minValue, maxValue);
		}
		catch (NumberFormatException exception)
		{
			integerFormatError (textValue);
			setFocus (textFieldValue);
		}
	
		return returnValue;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación del rango de un real especificado en un campo de texto
	 * @param value - Valor
	 * @param rangeMessage - Mensaje de error de rango
	 * @param minValue - Valor mínimo
	 * @param maxValue  - Valor máximo
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean rangeDoubleOf (String value, String rangeMessage, int minValue, int maxValue)
	{
		boolean returnValue = false;
		
		try
		{
			double doubleValue = Double.parseDouble(value);
			
			returnValue = checkRange (doubleValue, rangeMessage, minValue, maxValue); 
		}
		catch (NumberFormatException exception)
		{
			doubleFormatError (value);
		}
		
		return returnValue;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación del rango de un real especificado en un campo de texto
	 * @param textFieldValue - Campo de texto que recoge el valor
	 * @param rangeMessage - Mensaje de error de rango
	 * @param minValue - Valor mínimo
	 * @param maxValue  - Valor máximo
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean rangeDoubleOf (JTextField textFieldValue, String rangeMessage, int minValue, int maxValue)
	{
		boolean returnValue = false;
		String textValue = textFieldValue.getText();
		
		try
		{
			double doubleValue = Double.parseDouble(textValue);
			returnValue = checkRange (doubleValue, rangeMessage, minValue, maxValue);
		}
		catch (NumberFormatException exception)
		{
			doubleFormatError (textValue);
			setFocus (textFieldValue);
		}
		
		return returnValue;
	}
	
	// --------------------------------------------------------------------------------------------
	public boolean isInteger (JTextField textField) 
	{
		boolean returnValue = false;
		
		try
		{
			Integer.parseInt(textField.getText ());
			returnValue = true;
		}
		catch (NumberFormatException exception)
		{
			integerFormatError(textField.getText());
			setFocus (textField);
		}
		
		return returnValue;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Comprobación del rango
	 * @param value - Valor
	 * @param rangeMessage - Mensaje de error de rango
	 * @param minValue - Valor mínimo
	 * @param maxValue - Valor máximo
	 * @return - Cierto cuando la validación se cumpla y falso en caso contrario
	 */
	public boolean checkRange (double value, String rangeMessage, int minValue, int maxValue)
	{
		if (value >= minValue && value < maxValue) 
		{
			return true;
		}

		JOptionPane.showMessageDialog(owner, String.format(Locale.US, rangeMessage, value, minValue, maxValue), "Error", JOptionPane.ERROR_MESSAGE);
		return false;
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Ajusta el foco en el campo de texto
	 * @param textField - Campo de texto
	 */
	public void setFocus (JTextField textField)
	{
		textField.setSelectionStart(0);
		textField.setSelectionEnd(textField.getText().length());
		textField.requestFocus();
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Mensaje de error en el formato del valor real
	 * @param value - Valor
	 */
	public void doubleFormatError(String value) 
	{
		JOptionPane.showMessageDialog (owner, String.format (language.get ("doubleFormatError"), value), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Mensaje de error en el formato del valor entero
	 * @param value - Valor
	 */
	public void integerFormatError (String value)
	{
		JOptionPane.showMessageDialog (owner, String.format (language.get ("integerFormatError"), value), language.get ("errorText"), JOptionPane.ERROR_MESSAGE);
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Muestra una excepción en un dilálogo
	 * @param parent		Componente padre
	 * @param exception		Excepción a mostrar
	 */
	public static void showExceptionMessage (java.awt.Component parent, Exception exception)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace (printWriter);
		
		JOptionPane.showMessageDialog (parent, stringWriter.toString(), "Exception", JOptionPane.ERROR_MESSAGE);
	}
}
