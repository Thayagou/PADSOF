package vistas.empleado.valorarArticulos;

import vistas.common.displays.PanelArticulo;

/**
 * Esta clase representa un panel de un artículo pendiente de valoración
 */
public class PanelArticuloPendienteValoracion extends PanelArticulo{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de un panel de artículo pendiente de valoración
	 * @param nombreUsuario Nombre del usuario propietario
	 * @param nombre Nombre del artículo
	 * @param foto Foto del artículo
	 * @param descripcion Descripción del artículo
	 * @param interesadoEn Información sobre en que está interesado el usuario
	 * @param estimacion Estimación de precio del artículo
	 * @param estado Estado físico del artículo
	 * @param actionName Nombre de la acción asociada al panel
	 * @param categorias Categorías del artículo
	 */
	public PanelArticuloPendienteValoracion (String nombreUsuario, String nombre, String foto, String descripcion, String interesadoEn, double estimacion, String estado, String actionName, String...categorias) {
		super(nombreUsuario, "pfp.png", nombre, foto, descripcion, interesadoEn, estimacion, estado, actionName, categorias);
		
		if (estimacion < 0) inicializarBoton(actionName);
	}
}
