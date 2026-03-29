package usuario;

import java.io.Serializable;

public enum TipoNotificacion implements Serializable {
	PEDIDO, CADUCIDAD, VALORACION, INTERCAMBIO, PRODUCTO_AGOTADO;
}
