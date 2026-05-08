package vistas.gestor;

import java.awt.BorderLayout;

import javax.swing.*;

import controladores.gestor.ControlInicioGestor;
import modelo.sistema.Tienda;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

//import java.awt.*;

public class VentanaInicioGestor extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton descuentos;
	private JButton sistema;
	private JButton estadisticas;
	private JButton prodYCats;
	private JButton empleados;
	
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

	public void setControlador(ControlInicioGestor controlInicioGestor) {
		descuentos.addActionListener(controlInicioGestor);
		sistema.addActionListener(controlInicioGestor);
		estadisticas.addActionListener(controlInicioGestor);
		prodYCats.addActionListener(controlInicioGestor);
		empleados.addActionListener(controlInicioGestor);
	}
}
