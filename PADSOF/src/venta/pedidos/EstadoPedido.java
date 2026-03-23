package venta.pedidos;

/**
 * Clase enum con los posibles estados de un pedido
 * 
 * @author Juan Ibáñez
 */
public enum EstadoPedido {
	PAGADO,
	EN_PREPARACION,
	LISTO,
	RECOGIDO;

	public EstadoPedido getSiguienteEstado() {
		EstadoPedido[] valores = EstadoPedido.values();
		int pos = this.ordinal();
		
		if (pos + 1 < valores.length) {
			return valores[pos + 1];
		} else return valores[valores.length - 1];
		
	}
}