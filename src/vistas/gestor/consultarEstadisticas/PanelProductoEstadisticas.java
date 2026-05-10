package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelProducto;
import vistas.herramientas.ColorPalette;

/**
 * Subclase de PanelDisplay que usamos para mostrar dentro de un scroll.
 */
public class PanelProductoEstadisticas extends PanelProducto{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Porcentaje de anchura de pantalla que ocupa el label del producto. */
	public static final double LABEL_WIDTH = 0.15;

	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param nombre Nombre del producto
	 * @param descripcion Descripción del produto
	 * @param imageName Imagen del producto
	 * @param puntuacionMedia Puntuación media del producto
	 * @param precio Precio del producto
	 * @param recaudacion Recaudación individual
	 * @param udsVendidas Unidades vendidas 
	 * @param porcentaje Porcentaje de ganancias respecto al total
	 * @param categorias Categorías a las que pertenece el producto
	 */
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
