package vistas.empleado.valorarArticulos;

import vistas.common.PanelArticulo;

public class PanelArticuloPendienteValoracion extends PanelArticulo{
	private static final long serialVersionUID = 1L;

	public PanelArticuloPendienteValoracion (String nombre, String descripcion, String interesadoEn, double estimacion, String estado, String actionName, String...categorias) {
		super(nombre, descripcion, interesadoEn, estimacion, estado, actionName, categorias);
		
		if (estimacion < 0) inicializarBoton(actionName);
	}
}
