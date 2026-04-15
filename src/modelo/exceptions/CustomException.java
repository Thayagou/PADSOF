package modelo.exceptions;

/**
 * Excepción checked que es la clase padre de todas las excepciones que creamos para el proyecto
 */
public abstract class CustomException extends Exception {
	private static final long serialVersionUID = 1L;
	/** Método en el que se lanza la excepción*/
	private String metodo;
	
	/**
	 * Constructor padre de las excepciones
	 * @param message Mensaje asignado a la excepción
	 * @param metodo Método en el que se ha lanzado la excepción
	 */
	public CustomException(String message, String metodo) {
		super(message);
		this.metodo = metodo;
	}
	
	/**
	 * Getter del método en el que se lanzó la excepción
	 * @return String del método
	 */
	public String getMetodo() {
		return metodo;
	}
	
	/**
	 * Getter del mensaje personalizado de la excepción
	 */
	@Override
	public String getMessage() {
		return  "Error en " + metodo + ": " + super.getMessage();
	}
}
