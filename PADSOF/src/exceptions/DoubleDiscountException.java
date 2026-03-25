package exceptions;

/**
 * Excepción que salta cuando a un producto se le intentan asignar dos descuentos
 * o cuando una acción no se puede llevar a cabo por mantener la unicidad de descuentos
 */
public class DoubleDiscountException extends Exception {

	public DoubleDiscountException(String mensaje) {
		super(mensaje);
	}
}
