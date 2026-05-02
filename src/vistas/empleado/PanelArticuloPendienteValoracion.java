package vistas.empleado;

import vistas.common.PanelArticulo;

public class PanelArticuloPendienteValoracion extends PanelArticulo{
	private static final long serialVersionUID = 1L;

	public PanelArticuloPendienteValoracion (String nombreUsuario, String fotoDePerfil,  String nombre, String descripcion, String interesadoEn, double estimacion, String estado, String actionName, String...categorias) {
		super(nombreUsuario, fotoDePerfil, nombre, descripcion, interesadoEn, estimacion, estado, actionName, categorias);
		
		if (estimacion < 0) inicializarBoton("Valorar");
	}
}
