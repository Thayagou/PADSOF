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

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante SPACE_EAST_PANEL. Espacio horizontal entre componentes del panel este como porcentaje de la pantalla. */
	private static final double SPACE_EAST_PANEL = 0.01;
	
	/** Constante BTN_WIDTH. Anchura del botón de valorar como porcentaje de la anchura de la pantalla. */
	private static final double BTN_WIDTH = 100.0/1876.0;
	
	/** Constante VALORAR_ACTION. Comando de acción para el botón de valorar el producto. */
	public static final String VALORAR_ACTION = "Valorar";
	
	/** Campo valorar. Botón para valorar el producto adquirido. */
	private JButton valorar;

	/**
	 * Instancia un nuevo Objeto PanelItemPedido.
	 * Construye el panel con la información del producto y el botón de valoración.
	 *
	 * @param nombre Nombre del producto.
	 * @param descripcion Descripción del producto.
	 * @param imageName Ruta de la imagen del producto.
	 * @param puntuacionMedia Puntuación media del producto (0-5).
	 * @param precio Precio del producto en euros.
	 * @param unidades Cantidad de unidades compradas.
	 * @param actionName Comando de acción para el botón principal del producto.
	 * @param categorias Categorías a las que pertenece el producto.
	 */
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

	/**
	 * Establece Controlador.
	 *
	 * @param l controlador que manejará los eventos del botón de valorar.
	 */
	@Override
	public void setControlador(ActionListener l) {
		super.setControlador(l);
		valorar.addActionListener(l);
	}
}