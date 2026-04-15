package modelo.wallapop;

import java.io.Serializable;

/**
 * Enum con los posibles valores de estado físico de los artículos de segunda mano 
 */
public enum EstadoFisicoArticulo implements Serializable {
	/** El artículo se encuentra en perfecto estado */
	PERFECTO,
	/** El artículo se encuentra en muy buen estado */
	MUY_BUENO,
	/** El artículo tiene uso ligero */
	USO_LIGERO,
	/** El artículo tiene  uso evidente */
	USO_EVIDENTE,
	/** El artículo se encuentra muy usado */
	MUY_USADO,
	/** El artículo se enceuntra dañado */
	DANADO,
	/** El artículo está esperando a recibir una valoración */
	PENDIENTE
}
