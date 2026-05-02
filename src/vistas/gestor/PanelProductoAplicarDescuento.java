package vistas.gestor;

import java.awt.BorderLayout;


import vistas.common.InvisibleCheckBox;
import vistas.common.PanelProducto;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelProductoAplicarDescuento extends PanelProducto{
	private static final long serialVersionUID = 1L;
	private InvisibleCheckBox checkBox;
	
	public PanelProductoAplicarDescuento(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, "Incluir", categorias);
		
		// CheckBox como display (no interactivo por sí solo)
		checkBox = ButtonFactory.newInvisibleCheckBox("Descontado", "Descontar", ColorPalette.BLACK, ColorPalette.GREY);
		
		this.add(checkBox, BorderLayout.EAST);
		setControlador(p->checkBox.toggleSelection());
	}
	
	public boolean isSeleccionado() {
		return checkBox.isSelected();
	}
}

