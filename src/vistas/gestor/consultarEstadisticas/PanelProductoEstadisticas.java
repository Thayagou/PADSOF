package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelProductoEstadisticas extends PanelProducto{
	
	private static final long serialVersionUID = 1L;
	public static final double LABEL_WIDTH = 0.15;

	public PanelProductoEstadisticas(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, double recaudacion, int udsVendidas, double porcentaje, String...categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, "Ver producto", categorias);
		
		TiendaFrame t = TiendaFrame.getInstance();
		int hComps = (int)(maxCompHeight * BOTON_PERC_H);
		int wComps = t.getPixelsWidth(LABEL_WIDTH);
		Dimension maxSize = new Dimension(wComps, hComps);
		
		// Panel final que se coloca
		JPanel statsPanel = new JPanel();
		statsPanel.setLayout(new GridLayout(1, 3));
		statsPanel.setOpaque(false);
		
		statsPanel.add(crearColumnaStat(String.format("%.2f €", recaudacion), maxSize));
	    statsPanel.add(crearColumnaStat(String.format("%d uds", udsVendidas), maxSize));
	    statsPanel.add(crearColumnaStat(String.format("%.3f %%", porcentaje), maxSize));
	    statsPanel.setMaximumSize(new Dimension(3*wComps, hComps));
	    add(statsPanel, BorderLayout.EAST);
	}

	private JPanel crearColumnaStat(String texto, Dimension maxSize) {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    
	    JLabel label = ButtonFactory.newLabel(texto, Fonts.BOLD);
	    label.setForeground(ColorPalette.DARK_GREY.getColor());
	    label.setMaximumSize(maxSize);
	    label.setPreferredSize(maxSize);
	    //label.setVerticalTextPosition(SwingConstants.CENTER);
	    //label.setAlignmentX(SwingConstants.CENTER);  // Centro horizontal en BoxLayout Y_AXIS
	    
	    panel.add(Box.createVerticalGlue());      // Empuja desde arriba
	    panel.add(label);
	    panel.add(Box.createVerticalGlue());      // Empuja desde abajo
	    
	    return panel;
	}
}
