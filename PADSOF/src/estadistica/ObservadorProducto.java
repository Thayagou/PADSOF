package estadistica;

import venta.productos.Producto;

/**
 * Esta interfaz sirve para añadir productos a las estadísticas de la tienda
 */
public interface ObservadorProducto {
	void guardarProducto(Producto p);
}
