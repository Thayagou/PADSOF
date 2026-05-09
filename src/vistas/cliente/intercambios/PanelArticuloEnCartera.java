package vistas.cliente.intercambios;

import vistas.common.displays.PanelArticulo;

public class PanelArticuloEnCartera extends PanelArticulo{
	private static final long serialVersionUID = 1L;
	
	public static final String SOLICITAR_BTN = "Solicitar valoración";
	
	public PanelArticuloEnCartera (String nombreUsuario, String fotoDePerfil,  String nombre, String foto, String descripcion, String interesadoEn, double estimacion, String estado, String actionName, String...categorias) {
		super(nombreUsuario, fotoDePerfil, nombre, foto, descripcion, interesadoEn, estimacion, estado, actionName, categorias);
		
		if (estado.equals("Sin valorar")) inicializarBoton(SOLICITAR_BTN);
	}
}
