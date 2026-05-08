package vistas.empleado.general;

import java.awt.BorderLayout;

import javax.swing.*;

import modelo.sistema.Tienda;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

/**
 * Esta clase representa la ventana de inicio de los empleados
 */
public class VentanaInicioEmpleado extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Cosntructor de la ventana de inicio de empleado
	 * @param tienda Modelo tienda
	 */
	public VentanaInicioEmpleado(Tienda tienda) {
		setOpaque(false);
		setLayout(new BorderLayout());

		// ── Cabecera ──────────────────────────────────────────────
		JLabel cabecera = new JLabel("  Menú de empleado   Seleccione una tarea para continuar");
		cabecera.setFont(Fonts.TITLE3.getFont());
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setOpaque(true);
		cabecera.setBackground(ColorPalette.DARK_BLUE.getColor());
		cabecera.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		add(cabecera, BorderLayout.NORTH);
	}
}
