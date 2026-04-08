package venta.descuentos;

import java.io.Serializable;

/**
 * Enum con las posibles condiciones del cumplimiento de un descuento
 * 
 * @author Juan Ibáñez
 */
public enum CondicionDescuento implements Serializable {
	/** Sin ninguna condición para aplicar el descuento */
	SIN_CONDICION,

	/** Descuento basado en la cantidad de productos */
	CANTIDAD,

	/** Descuento basado en el volumen de compra */
	VOLUMEN;
}
