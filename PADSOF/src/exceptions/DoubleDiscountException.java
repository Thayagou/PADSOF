package exceptions;

/**
 * Excepción que salta cuando a un producto se le intentan asignar dos descuentos
 * o cuando una acción no se puede llevar a cabo por mantener la unicidad de descuentos
 */
public class DoubleDiscountException extends CustomException {
	private static final long serialVersionUID = 1L;

	public DoubleDiscountException(String mensaje) {
		super(mensaje);
	}
}
