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
	 * Creador de un StockExterno sin especificar precio final aun
	 * @param p Producto del stock
	 * @param uds Número de unidades en el stock
	 */
	public StockExterno(Producto p, int uds) {
		super(p, uds);
		this.precioFinal = getProducto().getPrecio();
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
	
	@Override
	public String toString() {
		return getProducto().getNombre() + "x" + getUdsEnStock() + ". Precio por unidad: " + precioFinal;
	}
}
