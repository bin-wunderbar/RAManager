package bll;

import java.math.BigDecimal;

import dal.UsedMaterial;

// ------------------------------------------------------------------------------------------------
/***
 * Representa un elemento del histórico de materiales usados en la capa de negocio
 * 
 * @author G1
 *
 */
public class BUsedMaterial extends UsedMaterial 
{
	private BMaterial bMaterial;

	// --------------------------------------------------------------------------------------------
	/***
	 * Inicialización Material
	 * @param id											- Numero de identidad del histórico del material
	 * @param units										- Unidades del material
	 * @param idMaterial							- Número de identificación del material
	 * @param idOperation							- Número de identificación de la operación
	 * @param unitPriceApplied				- Precio por unidad 
	 */
	public BUsedMaterial (int id, double units, int idMaterial, int idOperation, double unitPriceApplied)
	{
		super (id, units, idMaterial, idOperation, unitPriceApplied);
	}
	
	// --------------------------------------------------------------------------------------------
	/***
	 * Traspasar datos del material usado
	 * @param usedMaterial	- Material usado en la reparacion
	 * @param bMaterial	- Material en la capa de negocio
	 */
	public BUsedMaterial(UsedMaterial usedMaterial, BMaterial bMaterial) 
	{
		super (
			usedMaterial.getId(), 
			usedMaterial.getUnits(), 
			usedMaterial.getIdMaterial(),
			usedMaterial.getIdOperation(), 
			usedMaterial.getUnitPriceApplied()
			);
		
		this.bMaterial = bMaterial;
	}

	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------
	
	/**
	 * @return the bMaterial
	 */
	public BMaterial getbMaterial() {
		return bMaterial;
	}

	/**
	 * @param bMaterial the bMaterial to set
	 */
	public void setbMaterial(BMaterial bMaterial) {
		this.bMaterial = bMaterial;

		// TODO: Controlar mediante excepción la introducción de materiales nulos
		setIdMaterial (bMaterial.getId());
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * Devuelve el cálculo total de multiplicar unidades por el precio aplicado al material
	 * @return El total
	 */
	public Double getTotal() 
	{
		BigDecimal bigDecimalUnits 				= new BigDecimal (getUnits ());
		BigDecimal bigDecimalUnitPriceApplied 	= new BigDecimal (getUnitPriceApplied());
		return bigDecimalUnits.multiply(bigDecimalUnitPriceApplied).doubleValue();
	}
	

}
