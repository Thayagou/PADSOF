package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controladores.TiendaFrame;
import vistas.common.displays.PanelDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

/**
 * Subclase de PanelDisplay que usamos para mostrar las estadísticas de un cliente dentro de un scroll.
 */
public class PanelClienteEstadisticas extends PanelDisplay {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Porcentaje de anchura del panel utilizado para la "foto" del cliente. */
	private static final double FOTO_W_PERC = 0.09;
	
	/** Porcentaje del panel utilizado para la "foto" del clinte. */
	private static final double FOTO_H_PERC = 0.99;
	
	/** Porcentaje de altura de pantalla que ocupa el panel. */
	private static final double MAX_HEIGHT = 0.16;
	
	/** Porcentaje de altura del panel que ocupa el nombre. */
	private static final double NAME_HEIGHT = 0.3;
	
	/** Porcentaje de anchura de pantalla que ocupa el label. */
	public static final double LABEL_WIDTH = 0.15;

	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param userName Nombre del usuario
	 * @param imageName Nombre de la foto a utilizar
	 * @param totalGastado total gastado por el cliente en la tienda
	 * @param udsCompradas número de unidades de productos compradas por el cliente
	 * @param artsIntercambiados número de unidades de artículos intercambiadas por el cliente
	 */
	public PanelClienteEstadisticas(String userName, String imageName, double totalGastado, long udsCompradas,
			long artsIntercambiados) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, FOTO_W_PERC, imageName, "");

		TiendaFrame t = TiendaFrame.getInstance();
		int hComps = (int) (maxCompHeight * BOTON_PERC_H);
		int wComps = t.getPixelsWidth(LABEL_WIDTH);
		int gap = t.getPixelsHeight((MAX_HEIGHT * (1 - NAME_HEIGHT)) / 2);
		Dimension maxSize = new Dimension(wComps, hComps);

		JPanel info = new JPanel();
		info.setOpaque(false);
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

		JLabel nombreLabel = new JLabel(userName);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		info.add(Box.createVerticalStrut(gap));
		info.add(nombreLabel);
		info.add(Box.createVerticalStrut(gap));

		add(info, BorderLayout.CENTER);
		// Panel final que se coloca
		JPanel statsPanel = new JPanel(new GridLayout(1, 3));
		statsPanel.setOpaque(false);

		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat(String.format("%.2f €", totalGastado), maxSize, ColorPalette.DARK_GREY));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat(String.format("%d uds", udsCompradas), maxSize, ColorPalette.DARK_GREY));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat(String.format("%d uds", artsIntercambiados), maxSize, ColorPalette.DARK_GREY));
		statsPanel.setMaximumSize(new Dimension(3*wComps, hComps));
		
		add(statsPanel, BorderLayout.EAST);
	}
}
