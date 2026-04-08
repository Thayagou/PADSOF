package exceptions;

/**
 * Excepción que se lanza en caso de que se trate de introducir en la tienda un nombre de usuario no válido
 */
public class NotValidUserException extends CustomException{
	private static final long serialVersionUID = 1L;
	/** Nombre del usuario inválido */
	private String nombre;

	/**
	 * Constructor de la excepción NotValidUserException
	 * @param mensaje Mensaje principal de la excepción
	 * @param metodo Método en el que se ha lanzado la excepción
	 * @param nombre Nombre del usuario inválido introducido
	 */
	public NotValidUserException(String mensaje, String metodo, String nombre) {
		super(mensaje, metodo);
		this.nombre = nombre;
	}

	/**
	 * Getter del nombre del usuario inválido
	 * @return el nombre del usuario en cuestión
	 */
	public String getUsuario() {
		return nombre;
	}
	
	/**
	 * Getter del mensaje personalizado de la excepción
	 */
	@Override
	public String getMessage() {
		return super.getMessage() + " con el nombre de usuario " + nombre;
	}
}
