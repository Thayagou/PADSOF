package venta.productos;

public class StockExterno extends Stock {
	private double precioFinal;
	
	/**
	 * Creador de un StockExterno
	 * @param p Producto del stock
	 * @param uds Número de unidades en el stock
	 * @param precio Precio final unitario del producto en el contexto de un carrito o pedido
	 */
	public StockExterno(Producto p, int uds, double precio) {
		super(p, uds);
		this.precioFinal = precio;
	}

	/**
	 * Getter del precio final del producto
	 * @return Precio final de venta de un producto en el contexto de un carrito o pedido
	 */
	public double getPrecioFinal() {
		return precioFinal;
	}

	/**
	 * Setter del precio final del producto
	 * @param precioFinal Precio final de venta de un producto en el contexto de un carrito o pedido
	 */
	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}
	
}
