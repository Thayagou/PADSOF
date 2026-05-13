package vistas.cliente.venta;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelProducto;
import vistas.herramientas.*;

/**
 * Panel que representa un producto dentro del carrito de compras, mostrando su información y permitiendo eliminarlo.
 */
public class PanelItemCarrito extends PanelProducto {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Constante QUITAR_ACTION. Comando de acción para el botón de eliminar del carrito. */
	public static final String QUITAR_ACTION = "Quitar";
	
	/** Ancho en pixeles del boton de quitar */
	public static final int BTN_WIDTH = TiendaFrame.getInstance().getPixelsWidth(0.07);

	/** Campo quitar. Botón para eliminar el producto del carrito. */
	private JButton quitar;
	
	/** Campo unidadesLabel. Etiqueta que muestra la cantidad de unidades del producto. */
	private JLabel unidadesLabel;

	/** Constante SPACE_EAST_PANEL. Espacio horizontal entre componentes del panel este como porcentaje de la pantalla. */
	private static final double SPACE_EAST_PANEL = 0.01;

	/**
	 * Instancia un nuevo Objeto PanelItemCarrito.
	 * Construye el panel con la información del producto y los controles de cantidad y eliminación.
	 *
	 * @param nombre Nombre del producto.
	 * @param descripcion Descripción del producto.
	 * @param imageName Ruta de la imagen del producto.
	 * @param puntuacionMedia Puntuación media del producto (0-5).
	 * @param precio Precio del producto en euros.
	 * @param unidades Cantidad de unidades añadidas al carrito.
	 * @param actionName Comando de acción para el botón principal del producto.
	 * @param categorias Categorías a las que pertenece el producto.
	 */
	public PanelItemCarrito(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio,
			int unidades, String actionName, String... categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, actionName, categorias);

		TiendaFrame t = TiendaFrame.getInstance();

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout(t.getPixelsWidth(SPACE_EAST_PANEL), 0));

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.setOpaque(false);
		int maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		buttons.setPreferredSize(new Dimension(maxWidth, (int) (maxCompHeight * BOTON_PERC_H)));

		quitar = ButtonFactory.newRoundedButton("Quitar", (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.5f);
		quitar.setMaximumSize(new Dimension(BTN_WIDTH,  (int) (maxCompHeight * BOTON_PERC_H)));
		quitar.setActionCommand(QUITAR_ACTION);
		ButtonFactory.paintButton(quitar, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(quitar, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 3);
		buttons.add(Box.createVerticalStrut(gapSize));
		buttons.add(quitar);
		buttons.add(Box.createVerticalStrut(gapSize));

		eastPanel.add(buttons, BorderLayout.EAST);

		JPanel unitPanel = new JPanel();
		unidadesLabel = ButtonFactory.newLabel(String.format("x%d", unidades), Fonts.TEXT);
		unitPanel.add(unidadesLabel);
		unitPanel.setOpaque(false);

		JPanel centerWrapper = new JPanel();
		centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
		centerWrapper.setOpaque(false);
		centerWrapper.add(Box.createVerticalGlue());
		centerWrapper.add(unitPanel);
		centerWrapper.add(Box.createVerticalGlue());

		eastPanel.add(centerWrapper, BorderLayout.WEST);
		eastPanel.setOpaque(false);

		this.add(eastPanel, BorderLayout.EAST);
	}

	/**
	 * Establece Controlador.
	 *
	 * @param l controlador que manejará los eventos del botón de eliminar.
	 */
	@Override
	public void setControlador(ActionListener l) {
		super.setControlador(l);
		quitar.addActionListener(l);
	}

	/**
	 * Actualiza el número de unidades mostrado en el panel.
	 * 
	 * @param uds nuevas unidades
	 */
	public void actualizarUnidades(int uds) {
		if (unidadesLabel != null) {
			unidadesLabel.setText(String.format("x%d", uds));
		}
	}
}