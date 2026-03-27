package exceptions;

/**
 * Clase padre de todas las excepciones que creamos para el proyecto
 */
public abstract class CustomException extends Exception {

	public CustomException(String message) {
		super(message);
	}
}
