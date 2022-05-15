package racrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/*
 * Clase que provee de operaciones de cifrado y codificacion
 */
public class RACrypt 
{
	// --------------------------------------------------------------------------------------------
	/**
	 * Genera el valor MD5 de una cadena de datos y lo devuelve como expresion hexadecimal de texto
	 * 
	 * @param data - Datos a procesar como md5
	 * 
	 * @return Representacion MD5
	 * @throws NoSuchAlgorithmException 
	 */
	public static String parseToMD5 (String data) throws NoSuchAlgorithmException
	{
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(data.getBytes());
		
		return toHexString (messageDigest.digest());
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Codifica un array de bytes en un representacion de texto hexadecimal
	 * 
	 * @param values - Array de bytes con los valores a codificar
	 * 
	 * @return Cadena de texto con los valores en formato hexadecimal
	 */
	public static String toHexString (byte[] values)
	{
		String hexString = "";
		
		for (byte value : values)
		{
			hexString += String.format("%02x", value);
		}
		
		return hexString;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Codifica una cadena de caracteres con representacion hexadecimal de informacion como array de bytes
	 * 
	 * @param data - Datos hexadecimales en formato de texto
	 * @return Array de bytes con los datos
	 */
	public static byte[] fromHexString (String data)
	{
		byte[] values = new byte[data.length() / 2];
		
		for (int i = 0; i < values.length; i++)
		{
			values[i] = (byte) 
					((Character.digit (data.charAt(i * 2), 16) << 4) |
					(Character.digit (data.charAt(i * 2 + 1), 16)));
		}
		
		return values;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Ofusca una cadena de datos para dificultar su comprension junto con otra informacion de texto plano
	 * 
	 * @param data - Datos a ofuscar
	 * @return Array de bytes con los datos ofuscados
	 */
	public static byte[] parseToObfuscatedData (String data)
	{
		Random random = new Random (data.length());
		byte[] values = data.getBytes();
	
		for (int i = 0; i < values.length; i++)
		{
			values[i] = (byte)(values[i] ^ random.nextInt (256));
		}
		
		return values;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Recupera una cadena de datos a partir de los valores ofuscados mediante el metodo parseToObfuscatedData
	 * 
	 * @param values - Valores ofuscados
	 * @return Cadena de datos recuperados
	 */
	public static String parseFromObfuscatedData (byte[] values)
	{
		Random random = new Random (values.length);
		String data = "";
		
		for (int i = 0; i < values.length; i++)
		{
			data += (char)((byte)(values[i] ^ random.nextInt (256)));
		}
		
		return data;
	}
	
}
