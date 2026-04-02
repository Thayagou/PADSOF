package exceptions;

public class NotValidUserException extends CustomException{
	private static final long serialVersionUID = 1L;
	private String nombre;

	public NotValidUserException(String mensaje, String metodo, String nombre) {
		super(mensaje, metodo);
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getMessage() {
		return super.getMessage() + " con el nombre de usuario " + nombre;
	}
}
