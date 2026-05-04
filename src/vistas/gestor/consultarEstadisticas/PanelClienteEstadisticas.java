package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.common.PanelDisplay;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelClienteEstadisticas extends PanelDisplay{
	private static final long serialVersionUID = 1L;
	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;
	private static final double NAME_HEIGHT = 0.3;
	
	public PanelClienteEstadisticas(String userName, String imageName, double totalGastado, long udsCompradas, long artsIntercambiados) {
		super(MAX_HEIGHT, FOTO_H_PERC*MAX_HEIGHT, FOTO_W_PERC, imageName, "");
		
		TiendaFrame t = TiendaFrame.getInstance();
		int hComps = (int)(maxCompHeight * BOTON_PERC_H);
		int wComps = t.getPixelsWidth(BOTON_PERC_W);
		int gap = t.getPixelsHeight((MAX_HEIGHT*(1 - NAME_HEIGHT))/2);
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
		JPanel statsPanel = new JPanel();
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.X_AXIS));
		statsPanel.setOpaque(false);
		
		// Panel total gastado
		JPanel panelRecaudacion = new JPanel();
		panelRecaudacion.setLayout(new BoxLayout(panelRecaudacion, BoxLayout.Y_AXIS));
		panelRecaudacion.setOpaque(false);
		
		JLabel labelRecaudacion = ButtonFactory.newLeftAlignedLabel("Total recaudado:", Fonts.BOLD);
		labelRecaudacion.setMaximumSize(maxSize);
		labelRecaudacion.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel labelValorRecaudacion =ButtonFactory.newLeftAlignedLabel(String.format("%.2f €", totalGastado), Fonts.BOLD);
		labelValorRecaudacion.setMaximumSize(maxSize);
		labelValorRecaudacion.setAlignmentX(LEFT_ALIGNMENT);
		
		int gapSize = (int) (maxCompHeight * (1 - 2*BOTON_PERC_H) / 2);
		panelRecaudacion.add(Box.createVerticalStrut(gapSize));
		panelRecaudacion.add(labelRecaudacion);
		panelRecaudacion.add(labelValorRecaudacion);
		panelRecaudacion.add(Box.createVerticalStrut(gapSize));
		statsPanel.add(panelRecaudacion);
		statsPanel.add(Box.createHorizontalStrut(gapSize));
		
		JPanel panelUds = new JPanel();
		panelUds.setLayout(new BoxLayout(panelUds, BoxLayout.Y_AXIS));
		panelUds.setOpaque(false);
		
		JLabel labelUds = ButtonFactory.newLeftAlignedLabel("Unidades vendidas:", Fonts.BOLD);
		labelUds.setMaximumSize(maxSize);
		labelUds.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel labelValorUds = ButtonFactory.newLeftAlignedLabel(String.format("%d uds", udsCompradas), Fonts.BOLD);
		labelValorUds.setMaximumSize(maxSize);
		labelValorUds.setAlignmentX(LEFT_ALIGNMENT);
		
		panelUds.add(Box.createVerticalStrut(gapSize));
		panelUds.add(labelUds);
		panelUds.add(labelValorUds);
		panelUds.add(Box.createVerticalStrut(gapSize));
		statsPanel.add(panelUds);
		statsPanel.add(Box.createHorizontalStrut(gapSize));
		
		JPanel panelPorcentaje = new JPanel();
		panelPorcentaje.setLayout(new BoxLayout(panelPorcentaje, BoxLayout.Y_AXIS));
		panelPorcentaje.setOpaque(false);
		
		JLabel labelPorcentaje = ButtonFactory.newLeftAlignedLabel("Unidades intercambiadas:", Fonts.BOLD);
		labelPorcentaje.setMaximumSize(maxSize);
		labelPorcentaje.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel labelValorPorcentaje = ButtonFactory.newLeftAlignedLabel(String.format("%d uds", artsIntercambiados), Fonts.BOLD);
		labelValorPorcentaje.setMaximumSize(maxSize);
		labelValorPorcentaje.setAlignmentX(LEFT_ALIGNMENT);
		
		panelPorcentaje.add(Box.createVerticalStrut(gapSize));
		panelPorcentaje.add(labelPorcentaje);
		panelPorcentaje.add(labelValorPorcentaje);
		panelPorcentaje.add(Box.createVerticalStrut(gapSize));
		statsPanel.add(panelPorcentaje);
		statsPanel.add(Box.createHorizontalStrut(gapSize));
		
		add(statsPanel, BorderLayout.EAST);	
	}
}
