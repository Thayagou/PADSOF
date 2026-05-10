package vistas.common.displays;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.common.components.InvisibleCheckBox;
import vistas.common.components.PanelSeleccion;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

/**
 * Subclase de PanelDisplay que usamos para mostrar dentro de un scroll.
 */
public class PanelProductoSeleccion extends PanelProducto implements PanelSeleccion{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** ActionCommand de la acción de. */
	public static final String INCLUIR_ACTION= "Incluir";
	
	/** Campo checkBox. */
	private InvisibleCheckBox checkBox;
	
	
	
	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param nombre parámetro nombre
	 * @param descripcion parámetro descripcion
	 * @param imageName parámetro imageName
	 * @param puntuacionMedia parámetro puntuacionMedia
	 * @param precio parámetro precio
	 * @param categorias parámetro categorias
	 */
	public PanelProductoSeleccion(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, INCLUIR_ACTION, categorias);
		
		checkBox = ButtonFactory.newInvisibleCheckBox("Descontado", "Descontar", ColorPalette.BLACK, ColorPalette.GREY);
		
		this.add(checkBox, BorderLayout.EAST);
	}
	
	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param nombre parámetro nombre
	 * @param descripcion parámetro descripcion
	 * @param imageName parámetro imageName
	 * @param puntuacionMedia parámetro puntuacionMedia
	 * @param precio parámetro precio
	 * @param selected parámetro selected
	 * @param unselected parámetro unselected
	 * @param categorias parámetro categorias
	 */
	public PanelProductoSeleccion(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String selected, String unselected, String...categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, INCLUIR_ACTION, categorias);
		
		JPanel panelCheckBox = new JPanel();
		panelCheckBox.setOpaque(false);
		panelCheckBox.setLayout(new BoxLayout(panelCheckBox, BoxLayout.Y_AXIS));
		checkBox = ButtonFactory.newInvisibleCheckBox(selected, unselected, ColorPalette.BLACK, ColorPalette.GREY);
		
		panelCheckBox.add(Box.createVerticalGlue());
		panelCheckBox.add(checkBox);
		panelCheckBox.add(Box.createVerticalGlue());
		panelCheckBox.setPreferredSize(new Dimension(TiendaFrame.getInstance().getPixelsWidth(0.07), maxCompHeight));
		this.add(panelCheckBox, BorderLayout.EAST);
		
	}
	
	/**
	 * Comprueba si es Seleccionado.
	 *
	 * @return true si es Seleccionado, falso en caso contrario
	 */
	@Override
	public boolean isSeleccionado() {
		return checkBox.isSelected();
	}
	
	/**
	 * toggleCheckBox.
	 */
	@Override
	public void toggleCheckBox() {
		checkBox.toggleSelection();
	}
}

