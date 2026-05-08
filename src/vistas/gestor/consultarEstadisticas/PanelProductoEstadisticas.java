package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelProducto;
import vistas.herramientas.ColorPalette;

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
		JPanel statsPanel = new JPanel(new GridLayout(1, 3));
		statsPanel.setOpaque(false);
		
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat(String.format("%.2f €", recaudacion), maxSize, ColorPalette.DARK_GREY));
	    statsPanel.add(PanelEstadisticasTienda.crearColumnaStat(String.format("%d uds", udsVendidas), maxSize, ColorPalette.DARK_GREY));
	    statsPanel.add(PanelEstadisticasTienda.crearColumnaStat(String.format("%.3f %%", porcentaje), maxSize, ColorPalette.DARK_GREY));
	    statsPanel.setMaximumSize(new Dimension(3*wComps, hComps));
	    add(statsPanel, BorderLayout.EAST);
	}
}
