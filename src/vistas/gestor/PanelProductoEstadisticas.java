package vistas.gestor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.Fonts;

public class PanelProductoEstadisticas extends PanelProducto{
	
	private static final long serialVersionUID = 1L;

	public PanelProductoEstadisticas(String nombre, String descripcion, double puntuacionMedia, double precio, double recaudacion, int udsVendidas, double porcentaje, String...categorias) {
		super(nombre, descripcion, puntuacionMedia, precio, "Ver producto", categorias);
		
		TiendaFrame t = TiendaFrame.getInstance();
		int hComps = (int)(maxCompHeight * BOTON_PERC_H);
		int wComps = t.getPixelsWidth(BOTON_PERC_W);
		Dimension maxSize = new Dimension(wComps, hComps);
		ButtonFactory f = new ButtonFactory();
		
		// Panel final que se coloca
		JPanel statsPanel = new JPanel();
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.X_AXIS));
		statsPanel.setOpaque(false);
		
		// Panel recaudación
		JPanel panelRecaudacion = new JPanel();
		panelRecaudacion.setLayout(new BoxLayout(panelRecaudacion, BoxLayout.Y_AXIS));
		//panelRecaudacion.setOpaque(false);
		
		JLabel labelRecaudacion = f.newLeftAlignedLabel("Total recaudado:", Fonts.BOLD);
		labelRecaudacion.setMaximumSize(maxSize);
		labelRecaudacion.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel labelValorRecaudacion = f.newLeftAlignedLabel(String.format("%.2f €", recaudacion), Fonts.BOLD);
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
		//panelUds.setOpaque(false);
		
		JLabel labelUds = f.newLeftAlignedLabel("Unidades vendidas:", Fonts.BOLD);
		labelUds.setMaximumSize(maxSize);
		labelUds.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel labelValorUds = f.newLeftAlignedLabel(String.format("%d.0 €", udsVendidas), Fonts.BOLD);
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
		//panelPorcentaje.setOpaque(false);
		
		JLabel labelPorcentaje = f.newLeftAlignedLabel("Porcentaje de ventas:", Fonts.BOLD);
		labelPorcentaje.setMaximumSize(maxSize);
		labelPorcentaje.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel labelValorPorcentaje = f.newLeftAlignedLabel(String.format("%.2f", porcentaje), Fonts.BOLD);
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
