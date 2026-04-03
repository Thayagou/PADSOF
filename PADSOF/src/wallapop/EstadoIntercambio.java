package wallapop;

import java.io.Serializable;

/**
 * Enum con los posibles valores de estados de un intercambio
 */
public enum EstadoIntercambio implements Serializable {
	/** El intercambio se ha ofertado pero aún no ha tenido respuesta */
	OFERTADO,
	/** El intercambio se ha ofertado y el emisor lo ha cancelado */
	CANCELADO,
	/** El intercambio se ha ofertado y el receptor lo ha rechazado */
	RECHAZADO,
	/** EL intercambio se ha ofertado y por falta de respuesta se ha caducado */
	CADUCADO,
	/** El intercambio se ha ofertado y el receptor lo ha aceptado */
	ACEPTADO,
	/** El intercambio se ha ofertado, aceptado por el receptor y se ha realizado físicamente */
	CONFIRMADO,
	/** El intercambio se ha ofertado pero el receptor utilizó algún artículo en otro intercambio, por lo que se ha invalidado */
	INVALIDADO
}
