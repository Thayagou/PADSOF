package vistas.common.displays;

import java.awt.BorderLayout;

import vistas.common.components.InvisibleCheckBox;
import vistas.common.components.PanelSeleccion;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelProductoSeleccion extends PanelProducto implements PanelSeleccion{
	private static final long serialVersionUID = 1L;
	public static final String INCLUIR_ACTION= "Incluir";
	
	private InvisibleCheckBox checkBox;
	
	
	
	public PanelProductoSeleccion(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, INCLUIR_ACTION, categorias);
		
		checkBox = ButtonFactory.newInvisibleCheckBox("Descontado", "Descontar", ColorPalette.BLACK, ColorPalette.GREY);
		
		this.add(checkBox, BorderLayout.EAST);
	}
	
	public PanelProductoSeleccion(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String selected, String unselected, String...categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, INCLUIR_ACTION, categorias);
		
		checkBox = ButtonFactory.newInvisibleCheckBox(selected, unselected, ColorPalette.BLACK, ColorPalette.GREY);
		
		this.add(checkBox, BorderLayout.EAST);
	}
	
	@Override
	public boolean isSeleccionado() {
		return checkBox.isSelected();
	}
	
	@Override
	public void toggleCheckBox() {
		checkBox.toggleSelection();
	}
}

