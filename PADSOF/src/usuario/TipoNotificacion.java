package usuario;

import java.io.Serializable;

/**
 * Enum que representa los tipos de notificaciones
 */
public enum TipoNotificacion implements Serializable {

	/** Notificación relacionada con pedidos */
	PEDIDO,

	/** Notificación de caducidad */
	CADUCIDAD,

	/** Notificación de valoración */
	VALORACION,

	/** Notificación de intercambio */
	INTERCAMBIO,

	/** Notificación de producto agotado */
	PRODUCTO_AGOTADO;
}