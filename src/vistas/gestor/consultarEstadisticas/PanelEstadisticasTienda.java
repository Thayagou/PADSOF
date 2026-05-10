package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.YearMonth;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

/**
 * Subclase de PanelDisplay que usamos para mostrar dentro de un scroll las estadísticas de la tienda.
 */
public class PanelEstadisticasTienda extends PanelDisplay {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Porcentaje de altura de la pantalla que ocupa el panel */
	private static double MAX_H = 0.06;

	/** Porcentaje de altura del panel que pueden ocupar los componentes */
	private static double MAX_H_COMP = 0.9;

	/** Porcentaje de anchura de pantalla que ocupa el label. */
	public static final double LABEL_WIDTH = 0.15;

	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la
	 * información necesaria para actuar sobre este.
	 *
	 * @param mes         Més al que están asociadas las estadísticas
	 * @param recaudacion Recaudación en el mes despecto a las ventas o intercambios
	 * @param uds         Unidades de productos vendidos o artículos intercambiados
	 * @param porcentaje  Porcentaje repecto al resto de meses mostrados
	 */
	public PanelEstadisticasTienda(YearMonth mes, double recaudacion, int uds, double porcentaje) {
		super(MAX_H, MAX_H * MAX_H_COMP);

		TiendaFrame t = TiendaFrame.getInstance();
		int hComps = (int) (maxCompHeight * BOTON_PERC_H);
		int wComps = t.getPixelsWidth(LABEL_WIDTH);
		Dimension maxSize = new Dimension(wComps, hComps);

		String mesString = mes.getMonth().toString() + "/ " + mes.getYear();

		JLabel nombreLabel = new JLabel(mesString);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		add(nombreLabel, BorderLayout.WEST);

		JPanel statsPanel = new JPanel(new GridLayout(1, 3));
		statsPanel.setOpaque(false);

		statsPanel.add(crearColumnaStat(String.format("%.2f €", recaudacion), maxSize, ColorPalette.DARK_GREY));
		statsPanel.add(crearColumnaStat(String.format("%d uds", uds), maxSize, ColorPalette.DARK_GREY));
		statsPanel.add(crearColumnaStat(String.format("%.3f %%", porcentaje), maxSize, ColorPalette.DARK_GREY));
		add(statsPanel, BorderLayout.EAST);

	}

	/**
	 * Crea un panel formateado para mostrar cada una de las estadísticas o columnas que se deseen
	 *
	 * @param texto      Texto principal a mostrar
	 * @param maxSize    Tamaño máximo del panel
	 * @param colorTexto Color del texto mostrado
	 * @return JPanel creado que muestra lo deseado
	 */
	public static JPanel crearColumnaStat(String texto, Dimension maxSize, ColorPalette colorTexto) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);

		JLabel label = ButtonFactory.newLabel(texto, Fonts.BOLD);
		label.setForeground(colorTexto.getColor());
		label.setMaximumSize(maxSize);
		label.setPreferredSize(maxSize);

		// Centra el label
		panel.add(Box.createVerticalGlue());
		panel.add(label);
		panel.add(Box.createVerticalGlue());

		return panel;
	}
}
