package vistas.gestor;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

import modelo.sistema.Tienda;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

/**
 * Subclase de panel que se usa para mostrar por pantalla la ventana de inicio del gestor.
 */
public class VentanaInicioGestor extends JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instancia una nueva ventana que incluye toda la información necesaria para actuar sobre ella.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 */
	public VentanaInicioGestor(Tienda tienda) {
		setOpaque(false);
		setLayout(new BorderLayout());

		// ── Cabecera ──────────────────────────────────────────────
		JLabel cabecera = new JLabel("  Menú de gestor   Seleccione una tarea para continuar");
		cabecera.setFont(Fonts.TITLE3.getFont());
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setOpaque(true);
		cabecera.setBackground(ColorPalette.DARK_BLUE.getColor());
		cabecera.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		add(cabecera, BorderLayout.NORTH);
	}

	/**
	 * Añade un ActionListener a los componentes que lo necesiten, en este caso ninguno ya que es una ventana de bienvenida.
	 *
	 * @param l ActionListener a añadir
	 */
	public void setControlador(ActionListener l) { }
}
