package modelo.exceptions;

/**
 * Excepción que se lanza cuando a un producto/categoría se le intentan asignar dos descuentos o cuando una acción no se puede llevar a cabo por mantener la unicidad de descuentos
 */
public class DoubleDiscountException extends CustomException {
	private static final long serialVersionUID = 1L;
	/** Nombre de producto/categoría en el que se produce el conflicto*/
	private String nombre;
	
	/**
	 * Constructor de la excepción DoubleDiscountException
	 * @param mensaje Mensaje principal de la excepción
	 * @param metodo Método en el que se ha lanzado la excepción
	 * @param nombre Nombre del producto/categoría en el que se produce el conflicto
	 */
	public DoubleDiscountException(String mensaje, String metodo, String nombre) {
		super(mensaje, metodo);
		this.nombre = nombre;
	}
	
	/**
	 * Getter del mensaje personalizado de la excepción
	 */
	@Override
	public String getMessage() {
		return super.toString() + ". Conflicto en \"" + nombre + "\"";
	}
}
