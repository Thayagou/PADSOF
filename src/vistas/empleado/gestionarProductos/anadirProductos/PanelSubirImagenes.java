package vistas.empleado.gestionarProductos.anadirProductos;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import vistas.common.displays.PanelDisplay;
import vistas.herramientas.Fonts;

/**
 * Esta clase representa un panel para subir imágenes al archivo de la tienda
 */
public class PanelSubirImagenes extends PanelDisplay {
	private static final long serialVersionUID = 1L;
	/** Nombre de la acción del panel */
	public static final String ARCHIVOS_ACTION = "Subir imagen al archivo de la tienda...";
	
	/**
	 * Constructor de un panel de subir imágenes a los archivos de la aplicación
	 */
	public PanelSubirImagenes() {
		super(0.08, 0.06, ARCHIVOS_ACTION);
		JLabel label = new JLabel(ARCHIVOS_ACTION);
		label.setFont(Fonts.TITLE3.getFont());
		add(label, BorderLayout.CENTER);

		setOpaque(false);
	}
	
	/**
	 * Asigna un controlador a los componentes de la tienda
	 * @param c Controlador que se asigna
	 */
	public void setControlador(ActionListener c) {
		super.setControlador(c);
	}
}
