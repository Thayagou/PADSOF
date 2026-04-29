package vistas.empleado;

import java.awt.BorderLayout;

import javax.swing.*;

import modelo.sistema.Tienda;
import vistas.ColorPalette;
import vistas.TiendaFrame;

public class VentanaInicioEmpleado extends JPanel {
	private static final long serialVersionUID = 1L;

	public VentanaInicioEmpleado(Tienda tienda) {
		TiendaFrame t = TiendaFrame.getInstance();

		setOpaque(false);
		setLayout(new BorderLayout());

		// ── Cabecera ──────────────────────────────────────────────
		JLabel cabecera = new JLabel("  Menú de empleado   Seleccione una tarea para continuar");
		cabecera.setFont(t.getTitle3Font());
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setOpaque(true);
		cabecera.setBackground(ColorPalette.DARK_BLUE.getColor());
		cabecera.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		add(cabecera, BorderLayout.NORTH);
	}
}
