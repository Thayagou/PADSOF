package vistas.cliente.venta;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelProducto;
import vistas.herramientas.*;

/**
 * Fila de un producto dentro de la vista de detalle de un pedido.
 * Muestra la info del producto (heredada de PanelProducto) más las unidades
 * compradas. No tiene botón de acción, es solo informativo.
 */
public class PanelItemPedido extends PanelProducto {

	private static final long serialVersionUID = 1L;

	private static final double SPACE_EAST_PANEL = 0.01;
	private static final double BTN_WIDTH = 100.0/1876.0;
	
	public static final String VALORAR_ACTION = "Valorar";
	
	private JButton valorar;

	public PanelItemPedido(String nombre, String descripcion, String imageName,
			double puntuacionMedia, double precio, int unidades,
			String actionName, String... categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, actionName, categorias);

		TiendaFrame t = TiendaFrame.getInstance();
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout(t.getPixelsWidth(SPACE_EAST_PANEL), 0));

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.setOpaque(false);
		buttons.setPreferredSize(new Dimension((int)t.getPixelsWidth(BTN_WIDTH), (int) (maxCompHeight * BOTON_PERC_H)));

		valorar = ButtonFactory.newRoundedButton("Valorar", (int) (maxCompHeight), maxCompHeight, 0.5);
		valorar.setActionCommand(VALORAR_ACTION);
		ButtonFactory.paintButton(valorar, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(valorar, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 3);
		buttons.add(Box.createVerticalStrut(gapSize));
		buttons.add(valorar);
		buttons.add(Box.createVerticalStrut(gapSize));

		eastPanel.add(buttons, BorderLayout.EAST);
		
		JPanel unitPanel = new JPanel();
		unitPanel.add(ButtonFactory.newLabel(String.format("x%d", unidades), Fonts.TEXT));
		unitPanel.setOpaque(false);
		
		JPanel centerWrapper = new JPanel();
		centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
		centerWrapper.setOpaque(false);
		centerWrapper.add(Box.createVerticalGlue());
		centerWrapper.add(unitPanel);
		centerWrapper.add(Box.createVerticalGlue());
		
		eastPanel.add(centerWrapper, BorderLayout.CENTER);
		eastPanel.setOpaque(false);
		
		this.add(eastPanel, BorderLayout.EAST);
	}

	@Override
	public void setControlador(ActionListener l) {
		super.setControlador(l);
		valorar.addActionListener(l);
	}
}
