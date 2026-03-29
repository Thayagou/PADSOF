package exceptions;

/**
 * Excepción checked que se usa como estándar en el proyecto para
 * alertar de argumentos inválidos en los métodos
 */
public class InvalidArgumentException extends CustomException {
	private static final long serialVersionUID = 1L;

	public InvalidArgumentException(String message) {
		super(message);
	}
}
