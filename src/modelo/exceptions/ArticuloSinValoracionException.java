package modelo.exceptions;

import modelo.wallapop.ArticuloSegundaMano;

/**
 * Se lanza en caso de que se intente valorar un artículo de segunda mano que no tuviera una valoración pagada
 */
public class ArticuloSinValoracionException extends CustomException {
	private static final long serialVersionUID = 1L;
	/** Artículo que se ha intentado valorar sin éxito */
	ArticuloSegundaMano articulo;

	/**
	 * Constructor de la excepción ArticuloSinValoracionException
	 * @param message Mensaje principal de la excepción
	 * @param metodo Método en el que se ha lanzado la excepción
	 * @param articulo Artículo que se ha intentado valorar
	 */
	public ArticuloSinValoracionException(String message, String metodo, ArticuloSegundaMano articulo) {
		super(message, metodo);
		this.articulo = articulo;
	}
	
	/**
	 * Getter del mensaje personalizado de la excepción
	 */
	@Override
	public String getMessage() {
		return super.getMessage() + " Nombre artículo: " + articulo.getNombre();
	}

}
