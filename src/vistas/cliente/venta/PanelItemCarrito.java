package vistas.cliente.venta;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelProducto;
import vistas.herramientas.*;

/**
 * Tipo: Class PanelItemCarrito.
 */
public class PanelItemCarrito extends PanelProducto {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Constante QUITAR_ACTION. */
	public static final String QUITAR_ACTION = "Quitar";
	
	/** Ancho en pixeles del boton de quitar */
	public static final int BTN_WIDTH = TiendaFrame.getInstance().getPixelsWidth(0.07);

	/** Campo quitar. */
	private JButton quitar;
	
	/** Campo unidadesLabel. */
	private JLabel unidadesLabel;

	/** Constante SPACE_EAST_PANEL. */
	private static final double SPACE_EAST_PANEL = 0.01;

	/**
	 * Instancia un nuevo Objeto PanelItemCarrito.
	 *
	 * @param nombre parámetro nombre
	 * @param descripcion parámetro descripcion
	 * @param imageName parámetro imageName
	 * @param puntuacionMedia parámetro puntuacionMedia
	 * @param precio parámetro precio
	 * @param unidades parámetro unidades
	 * @param actionName parámetro actionName
	 * @param categorias parámetro categorias
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
	 * @param l nuevo valor
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