package venta.pedidos;

import java.io.Serializable;

/**
 * Clase enum con los posibles estados de un pedido
 */
public enum EstadoPedido implements Serializable {
	/** El pedido ha sido pagado */
    PAGADO,

    /** El pedido se encuentra en preparación */
    EN_PREPARACION,

    /** El pedido está listo para ser recogido */
    LISTO,

    /** El pedido ya ha sido recogido por el cliente */
    RECOGIDO;

	/**
	 * Devuelve el siguiente estado del pedido
	 * @return Siguiente estado
	 */
	public EstadoPedido getSiguienteEstado() {
		EstadoPedido[] valores = EstadoPedido.values();
		int pos = this.ordinal();
		
		if (pos + 1 < valores.length) {
			return valores[pos + 1];
		} else return valores[valores.length - 1];
		
	}
}