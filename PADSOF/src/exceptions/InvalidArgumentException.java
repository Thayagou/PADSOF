package exceptions;

/**
 * Excepción checked que se usa como estándar en el proyecto para alertar de argumentos inválidos en los métodos
 */
public class InvalidArgumentException extends CustomException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepción InvalidArgumentException
	 * @param message Mensaje principal de la excepción
	 */
	public InvalidArgumentException(String message) {
		super(message);
	}
	
	/**
	 * Constructor de la excepción InvalidArgumentException
	 * @param message Mensaje principal de la excepción
	 * @param metodo Método en el que se ha lanzado la excepción
	 */
	public InvalidArgumentException(String message, String metodo) {
		super(message, metodo);
	}
}
