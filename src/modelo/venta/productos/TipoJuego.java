package modelo.venta.productos;

import java.io.Serializable;

/**
 * Enum con los tipos de juegos
 * 
 * @author Juan Ibáñez
 */
public enum TipoJuego implements Serializable {

	/** Juegos de cartas */
	CARTAS,

	/** Juegos de dados */
	DADOS,

	/** Juegos de tablero */
	TABLERO,

	/** Juegos con miniaturas */
	MINIATURAS,

	/** Juegos de rol */
	ROL;
}
