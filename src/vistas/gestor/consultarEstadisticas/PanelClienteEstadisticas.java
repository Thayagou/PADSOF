package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import vistas.common.PanelDisplay;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelClienteEstadisticas extends PanelDisplay {
	private static final long serialVersionUID = 1L;
	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;
	private static final double NAME_HEIGHT = 0.3;
	public static final double LABEL_WIDTH = 0.15;

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

		statsPanel.add(crearColumnaStat(String.format("%.2f €", totalGastado), maxSize));
		statsPanel.add(crearColumnaStat(String.format("%d uds", udsCompradas), maxSize));
		statsPanel.add(crearColumnaStat(String.format("%d uds", artsIntercambiados), maxSize));
		statsPanel.setMaximumSize(new Dimension(3*wComps, hComps));
		
		add(statsPanel, BorderLayout.EAST);
	}

	private JPanel crearColumnaStat(String texto, Dimension maxSize) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);

		JLabel label = ButtonFactory.newLeftAlignedLabel(texto, Fonts.BOLD);
		label.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		label.setMaximumSize(maxSize);
		label.setPreferredSize(maxSize);
		// label.setVerticalTextPosition(SwingConstants.CENTER);
		// label.setAlignmentX(SwingConstants.CENTER); // Centro horizontal en BoxLayout
		// Y_AXIS

		panel.add(Box.createVerticalGlue()); // Empuja desde arriba
		panel.add(label);
		panel.add(Box.createVerticalGlue()); // Empuja desde abajo

		return panel;
	}
}
