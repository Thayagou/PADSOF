package exceptions;

import venta.productos.*;

public class ProductoNoDisponibleException extends Exception {
	private Producto producto;
	
	public ProductoNoDisponibleException(String message, Producto p) {
		super(message);
		this.producto = p;
	}
	
	public Producto getProducto() {
		return producto;
	}
}
