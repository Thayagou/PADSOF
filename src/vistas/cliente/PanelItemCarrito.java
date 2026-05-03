package vistas.cliente;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.herramientas.*;

public class PanelItemCarrito extends PanelProducto {
	private static final long serialVersionUID = 1L;

	private JButton quitar;

	public PanelItemCarrito(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, int unidades,
			String actionName, String... categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, actionName, categorias);

		TiendaFrame t = TiendaFrame.getInstance();

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int) (maxCompHeight * BOTON_PERC_H)));

		ButtonFactory f = new ButtonFactory();

		quitar = f.newRoundedButton("Quitar", (int) (maxCompHeight), maxCompHeight, 0.5f);
		quitar.setActionCommand("quitar");
		f.paintButton(quitar, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		f.addMouseMecanics(quitar, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 3);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(quitar);
		eastPanel.add(Box.createVerticalStrut(gapSize));

		this.add(eastPanel, BorderLayout.EAST);
		
		JPanel westPanel = new JPanel();
		westPanel.add(new ButtonFactory().newLabel(String.format("x%d", unidades), Fonts.TEXT));
		
		this.add(westPanel, BorderLayout.WEST);
	}

	@Override
	public void setControlador(ActionListener l) {
		super.setControlador(l);
		quitar.addActionListener(l);
	}
}
