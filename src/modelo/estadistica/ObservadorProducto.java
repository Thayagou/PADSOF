package modelo.estadistica;

import modelo.venta.productos.Producto;

/**
 * Esta interfaz sirve para añadir productos a las estadísticas de la tienda
 */
public interface ObservadorProducto {
	/**
	 * Guarda un producto en las estadísticas de la tienda
	 * @param p Producto que se guarda
	 */
	void guardarProducto(Producto p);
	
	/**
	 * Elimina un producto de las estadísticas en caso de que haya habido un error al crearlo
	 * @param p Produto a borrar
	 */
	void eliminarProducto(Producto p);
}