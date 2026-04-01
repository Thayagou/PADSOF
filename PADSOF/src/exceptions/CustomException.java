package exceptions;

/**
 * Clase padre de todas las excepciones que creamos para el proyecto
 */
public abstract class CustomException extends Exception {
	private static final long serialVersionUID = 1L;
	private String metodo;

	public CustomException(String message) {
		super(message);
		this.metodo = "AÑADIR METODO";
	}
	
	public CustomException(String message, String metodo) {
		super(message);
		this.metodo = metodo;
	}
	
	public String getMetodo() {
		return metodo;
	}
	
	@Override
	public String getMessage() {
		return  metodo + ": " + super.getMessage();
	}
}
