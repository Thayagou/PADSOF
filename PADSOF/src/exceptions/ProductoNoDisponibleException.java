package exceptions;

import venta.productos.*;

/**
 * Excepción que se lanza cuando no hay unidades disponibles de un producto
 */
public class ProductoNoDisponibleException extends CustomException {
	private static final long serialVersionUID = 1L;
	/** Producto del que no quedan unidades */
	private Producto producto;
	
	/**
	 * Creador de la excepción
	 * @param message Mensaje de error
	 * @param p Producto que se intentó adquirir
	 */
	public ProductoNoDisponibleException(String message, Producto p) {
		super(message);
		this.producto = p;
	}
	
	/**
	 * Getter del producto que causó la excepción
	 * @return Producto que no estaba disponible
	 */
	public Producto getProducto() {
		return producto;
	}
}
