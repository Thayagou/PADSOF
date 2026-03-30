package estadistica;

import venta.productos.Producto;

public interface ObservadorProducto {
	void guardarProducto(Producto p);
	void actualizarVector(Producto p);
}
