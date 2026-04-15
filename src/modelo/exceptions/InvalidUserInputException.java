package modelo.exceptions;

/**
 * Excepción que se lanza cuando un usuario introduce un input que no es válido para la 
 */
public class InvalidUserInputException extends CustomException{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepción InvalidUserInputException
	 * @param message Mensaje asignado a la excepción
	 * @param metodo Método en el que se ha lanzado la excepción
	 */
	public InvalidUserInputException(String message, String metodo) {
		super(message, metodo);
	}

}
