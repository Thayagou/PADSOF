package vistas.noRegistrado;

import javax.swing.*;

import vistas.Fonts;
import vistas.TiendaFrame;

import java.awt.BorderLayout;

public class VentanaInicioSinRegistrar extends JPanel {

	private static final long serialVersionUID = 1L;
	private static double GAP_PERC = 0.01;

	public VentanaInicioSinRegistrar() {
		super();

		JLabel title = new JLabel("Tienda mega friki (just for onion smelling fat twatts...)");
		title.setFont(Fonts.TITLE.getFont());
		int vertGap = TiendaFrame.getInstance().getPixelsHeight(GAP_PERC);
		int horGap = TiendaFrame.getInstance().getPixelsWidth(GAP_PERC);
		setLayout(new BorderLayout(horGap, vertGap));
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(title, BorderLayout.NORTH);
		
		
	}
}
