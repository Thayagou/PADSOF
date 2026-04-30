package vistas.empleado;

import java.awt.BorderLayout;

import javax.swing.JButton;

import vistas.common.PanelProducto;
import vistas.herramientas.ButtonFactory;

public class PanelProductoGestionarProducto extends PanelProducto{
	private static double BOTON_PERC = 0.5;
	
	public PanelProductoGestionarProducto(String nombre, String descripcion, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, puntuacionMedia, precio, categorias);
		
		ButtonFactory factory = new ButtonFactory();
		
		JButton button = factory.newButton("Modificar");
				//newRoundedButton("Modificar", (int) (this.getHeight() * BOTON_PERC), 0, 1);
		this.add(button, BorderLayout.EAST);
	}
}
