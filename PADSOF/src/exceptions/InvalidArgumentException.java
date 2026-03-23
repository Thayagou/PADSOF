package exceptions;

/**
 * Excepción checked que se usa como estándar en el proyecto para
 * alertar de argumentos inválidos en los métodos
 */
public class InvalidArgumentException extends Exception {

	public InvalidArgumentException(String message) {
		super(message);
	}
}
