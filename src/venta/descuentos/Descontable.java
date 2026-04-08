package venta.descuentos;

import exceptions.DoubleDiscountException;
import exceptions.InvalidArgumentException;

/**
 * Interfaz para objetos a los que se les puede aplicar un descuento
 */
public interface Descontable {
	/**
	 * Añade un descuento
	 * @param descuento Descuento que se añade
	 * @return True si se ha podido añadir correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 * @throws DoubleDiscountException Se lanza si el descontable ya tenía un descuento
	 */
	public boolean anadirDescuento(Descuento descuento) throws InvalidArgumentException, DoubleDiscountException;
	
	/**
	 * Quita un descuento
	 */
	public void quitarDescuento();
	
	/**
	 * Comprueba si ya tiene un descuento
	 * @return true si ya lo tiene, false en caso contrario
	 */
	public boolean tieneDescuento();
}
