package vistas.cliente;

import java.awt.*;
import javax.swing.*;

import controladores.ControlBarraLateral;
import vistas.*;
import vistas.herramientas.ColorPalette;

public class BarraCliente extends BarraLateral {
	private static final long serialVersionUID = 1L;

	public BarraCliente() {

		TiendaFrame frame = TiendaFrame.getInstance();
		int distFromLeft = frame.optionBarDistFromLeft();

		setBackground(ColorPalette.BLUE.getColor());
		setPreferredSize(new Dimension(distFromLeft, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

		agregarBoton("Gestionar productos y categorías");
		agregarBoton("Gestionar pedidos");
		agregarBoton("Valorar objetos de segunda mano");
		agregarBoton("Gestionar inntercambios");
	}

	private void agregarBoton(String texto) {
		JButton btn = new JButton(texto);
		btn.setAlignmentX(Component.LEFT_ALIGNMENT);
		btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
		btn.setFont(btn.getFont().deriveFont(14f));
		btn.setForeground(ColorPalette.WHITE.getColor());
		btn.setBackground(ColorPalette.BLUE.getColor());
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e) {
				btn.setBackground(ColorPalette.DARK_BLUE.getColor());
			}

			public void mouseExited(java.awt.event.MouseEvent e) {
				btn.setBackground(ColorPalette.BLUE.getColor());
			}
		});

		add(btn);
		add(Box.createVerticalStrut(8));
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
				
	}

}
