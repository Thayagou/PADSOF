package vistas.empleado.gestionarProductos.anadirProductos;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelProducto;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.Fonts;

/**
 * Panel para seleccionar los productos que se añaden a un pack
 */
public class PanelProductoAnadirAPack extends PanelProducto {
	private static final long serialVersionUID = 1L;
	/** Seleccionador del número de unidades */
	private JSpinner numUds;
	
	/**
	 * Constructor del panel de seleccionar los productos de un pack
	 * @param nombre Nombre del producto
	 * @param descripcion Descripción del producto
	 * @param imageName Nombre de la imagen del producto
	 * @param puntuacionMedia Puntuación media del producto
	 * @param precio Precio del producto
	 * @param categorias Categorías del producto
	 */
	public PanelProductoAnadirAPack (String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, "", categorias);
		
		TiendaFrame t = TiendaFrame.getInstance();
		int hSpinner = (int)(maxCompHeight * BOTON_PERC_H);
		int wSpinner = t.getPixelsWidth(BOTON_PERC_W);
		Dimension maxSize = new Dimension(wSpinner, hSpinner);
		
		numUds = ButtonFactory.spinnerEntero(Fonts.BOLD, hSpinner, wSpinner);
		numUds.setMaximumSize(maxSize);
		numUds.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel udsLabel = ButtonFactory.newLeftAlignedLabel("Unidades a añadir:", Fonts.BOLD);
		udsLabel.setMaximumSize(maxSize);
		udsLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		
		int gapSize = (int) (maxCompHeight * (1 - 2*BOTON_PERC_H) / 2);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(udsLabel);
		eastPanel.add(numUds);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		
		add(eastPanel, BorderLayout.EAST);
		
	}
	
	public int getUds() {
		return (int) numUds.getValue();
	}
}
