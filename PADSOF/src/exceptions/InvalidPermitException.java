package exceptions;

import usuario.Permiso;
import usuario.Usuario;

/**
 * Excepción que se lanza en caso de que el usuario que intenta realizar una determinada acción carece de los permisos necesarios
 */
public class InvalidPermitException extends CustomException {
	private static final long serialVersionUID = 1L;
	/** Permiso que le ha faltado al usuario */
	private Permiso permiso;
	/** Usuario que ha intentado realizar la acción que requería el permiso */
	private Usuario user;

	/**
	 * Constructor de la excepción InvalidPermitException
	 * @param mensaje Mensaje principal de la excepción
	 * @param metodo Método en el que se ha lanzado la excepción
	 * @param permiso Permiso que le ha faltado al usuario para realizar la acción
	 * @param user Usuario que ha interntado realizar la acción
	 */
	public InvalidPermitException(String message, String metodo, Permiso permiso, Usuario user) {
		super(message, metodo);
		this.permiso = permiso;
	}
	
	/**
	 * Getter del permiso que le ha faltado al usuario
	 * @return permiso necesario
	 */
	public Permiso getPermiso() {
		return permiso;
	}
	
	/**
	 * Getter del usuario que ha intentado realizar la acción que requería el permiso 
	 * @return el usuario
	 */
	public Usuario getUsuario() {
		return user;
	}
	
	/**
	 * Getter del mensaje personalizado de la excepción
	 */
	@Override
	public String getMessage() {
		return super.getMessage() + "\nEl usuario con nombre \"" + user.getNombre() + "\" no tiene el permiso: " + permiso.name();
	}

}
