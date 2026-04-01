package exceptions;

public class ArticuloSinValoracionException extends CustomException {
	private static final long serialVersionUID = 1L;
	String articulo;

	public ArticuloSinValoracionException(String message, String metodo, String articulo) {
		super(message, metodo);
		this.articulo = articulo;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + " Nombre artículo: " + articulo;
	}

}
