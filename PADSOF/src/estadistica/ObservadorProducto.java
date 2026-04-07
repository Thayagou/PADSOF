package estadistica;

import venta.productos.Producto;

/**
 * Esta interfaz sirve para añadir productos a las estadísticas de la tienda
 */
public interface ObservadorProducto {
	/**
	 * Guarda un producto en las estadísticas de la tienda
	 * @param p Producto que se guarda
	 */
	void guardarProducto(Producto p);
}