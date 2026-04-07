package sistema;

import usuario.Carrito;

/**
 * Interfaz para devolver stock a la tienda al caducar un carrito
 */
public interface CarritoCaducadoObserver {
	
	/**
	 * Devuelve el stock a la tienda al caducar un carrito
	 * @param carrito Carrito caducado
	 */
	void carritoCaducado(Carrito carrito);
}