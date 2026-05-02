package vistas.cliente;

import vistas.common.PanelArticulo;

public class PanelArticuloEnCartera extends PanelArticulo{
	private static final long serialVersionUID = 1L;
	
	public PanelArticuloEnCartera (String nombreUsuario, String fotoDePerfil,  String nombre, String descripcion, String interesadoEn, double estimacion, String estado, String actionName, String...categorias) {
		super(nombreUsuario, fotoDePerfil, nombre, descripcion, interesadoEn, estimacion, estado, actionName, categorias);
		
		if (estimacion < 0) inicializarBoton("Solicitar valoracion");
	}
}
