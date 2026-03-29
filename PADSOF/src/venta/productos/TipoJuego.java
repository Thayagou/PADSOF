package venta.productos;

import java.io.Serializable;

/**
 * Enum con los tipos de juegos
 * 
 * @author Juan Ibáñez
 */
public enum TipoJuego implements Serializable {
	CARTAS,
	DADOS,
	TABLERO,
	MINIATURAS,
	ROL;
}
