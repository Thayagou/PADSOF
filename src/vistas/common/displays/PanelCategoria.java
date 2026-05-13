package vistas.common.displays;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controladores.TiendaFrame;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

/**
 * Subclase de PanelDisplay que usamos para mostrar las categorías dentro de un scroll.
 */
public class PanelCategoria extends PanelDisplay {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Porcentaje de altura de pantalla que ocupa el panel de categoría. */
	private static double MAX_H = 0.08;
	
	/** Porcentaje del panel que ocupan los componentes. */
	private static double MAX_H_COMP = 0.75;
	
	
	/**
	 * Instancia un nuevo panel de categoría que se añadirá a una ventana y permite su selección.
	 *
	 * @param nombreCategoria Nombre de la categoría
	 * @param actionName Nombre de la acción asociada al botón del panel
	 */
	public PanelCategoria(String nombreCategoria, String actionName) {
		super(MAX_H, MAX_H * MAX_H_COMP, actionName);
		
		anadirLabel(nombreCategoria);
	}
	
	/**
	 * Instancia un nuevo panel que se añadirá a una ventana sin acción de botón
	 *
	 * @param nombreCategoria parámetro nombreCategoria
	 */
	public PanelCategoria(String nombreCategoria) {
		super(MAX_H, MAX_H * MAX_H_COMP);
		
		anadirLabel(nombreCategoria);
	}
	
	/**
	 * Añade el label con el nombre de la categoría al panel
	 *
	 * @param nombreCategoria Nombre de la categoría
	 */
	private void anadirLabel(String nombreCategoria) {
		TiendaFrame t = TiendaFrame.getInstance();
		
		int height = t.getPixelsHeight(MAX_H*MAX_H_COMP);
		int gap = t.getPixelsHeight(MAX_H * (1 - MAX_H_COMP)/2);
		
		JLabel nombreLabel = new JLabel(nombreCategoria);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		add(nombreLabel, BorderLayout.WEST);
		
		JPanel panelNombre = new JPanel();
		panelNombre.setLayout(new BoxLayout(panelNombre, BoxLayout.Y_AXIS));
		panelNombre.setOpaque(false);
		panelNombre.setPreferredSize(new Dimension(Integer.MAX_VALUE, height));
		panelNombre.add(Box.createVerticalStrut(gap));
		panelNombre.add(nombreLabel);
		panelNombre.add(Box.createVerticalStrut(gap));
		
		add(panelNombre, BorderLayout.WEST);
	}
}
