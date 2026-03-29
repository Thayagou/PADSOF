package venta.descuentos;

import java.io.Serializable;

/**
 * Enum con las posibles condiciones del cumplimiento de un descuento
 * 
 * @author Juan Ibáñez
 */
public enum CondicionDescuento implements Serializable {
	SIN_CONDICION,
	CANTIDAD,
	VOLUMEN;
}
