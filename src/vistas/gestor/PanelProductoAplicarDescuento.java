package vistas.gestor;

import javax.swing.JCheckBox;

import vistas.common.PanelProducto;
import vistas.herramientas.ColorPalette;

public class PanelProductoAplicarDescuento extends PanelProducto{
	private boolean seleccionada;
	private JCheckBox checkBox;
	
	public PanelProductoAplicarDescuento(String nombre, String descripcion, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, puntuacionMedia, precio, categorias);
		
		
	}
	private void toggleSeleccion() {
	    seleccionada = !seleccionada;
	    checkBox.setSelected(seleccionada);
	    checkBox.setForeground(seleccionada 
	        ? ColorPalette.BLACK.getColor() 
	        : ColorPalette.GREY.getColor());
	}
}

