package vistas.cliente;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import vistas.common.PanelArticulo;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelArticuloEnCartera extends PanelArticulo{
	private static final long serialVersionUID = 1L;
	
	public PanelArticuloEnCartera (String nombreUsuario, String fotoDePerfil,  String nombre, String descripcion, String interesadoEn, double estimacion, String estado, String actionName, String...categorias) {
		super(nombreUsuario, fotoDePerfil, nombre, descripcion, interesadoEn, estimacion, estado, actionName, categorias);
		
		if (estimacion < 0) inicializarBoton("Solicitar valoracion");
	}
}
