package vistas.cliente.intercambios;

import vistas.common.displays.PanelArticulo;

/**
 * Tipo: Class PanelArticuloEnCartera.
 */
public class PanelArticuloEnCartera extends PanelArticulo{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Constante SOLICITAR_BTN. */
	public static final String SOLICITAR_BTN = "Solicitar valoración";
	
	/**
	 * Instancia un nuevo Objeto PanelArticuloEnCartera.
	 *
	 * @param nombreUsuario parámetro nombreUsuario
	 * @param fotoDePerfil parámetro fotoDePerfil
	 * @param nombre parámetro nombre
	 * @param foto parámetro foto
	 * @param descripcion parámetro descripcion
	 * @param interesadoEn parámetro interesadoEn
	 * @param estimacion parámetro estimacion
	 * @param estado parámetro estado
	 * @param actionName parámetro actionName
	 * @param categorias parámetro categorias
	 */
	public PanelArticuloEnCartera (String nombreUsuario, String fotoDePerfil,  String nombre, String foto, String descripcion, String interesadoEn, double estimacion, String estado, String actionName, String...categorias) {
		super(nombreUsuario, fotoDePerfil, nombre, foto, descripcion, interesadoEn, estimacion, estado, actionName, categorias);
		
		if (estado.equals("Sin valorar")) inicializarBoton(SOLICITAR_BTN);
	}
}
