package exceptions;

public class InvalidPermitException extends CustomException {
	private static final long serialVersionUID = 1L;
	private String permiso;

	public InvalidPermitException(String message, String metodo, String permiso) {
		super(message, metodo);
		this.permiso = permiso;
	}
	
	public String getPermiso() {
		return permiso;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + "\nNecesitas el permiso: " + permiso;
	}

}
