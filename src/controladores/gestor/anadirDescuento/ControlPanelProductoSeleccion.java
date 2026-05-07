package controladores.gestor.anadirDescuento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.displays.PanelCategoriaSeleccion;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.PanelProductoSeleccion;
import vistas.common.displays.VentanaConDisplay;

public class ControlPanelProductoSeleccion implements ActionListener {
	private Producto producto;
	private Tienda tienda;
	private PanelProductoSeleccion panel;
	private ControlGestionSeleccion<? super Producto> superControl;
	
	public ControlPanelProductoSeleccion(Tienda tienda, Producto producto, String seleccionado, String desSeleccionado, ControlGestionSeleccion<? super Producto> superControl, VentanaConDisplay<? super PanelProducto> vista) {
		this.tienda = tienda;
		this.producto = producto;
		this.superControl = superControl;
		
		String imageName = producto.getImagen();
		if (imageName == null || imageName.isBlank()) imageName = "producto.png";
		
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		
		panel = new PanelProductoSeleccion(producto.getNombre(), producto.getDescripcion(), imageName, producto.getPuntuacionMedia(), producto.getPrecio(), seleccionado, desSeleccionado, categorias.toArray(new String[0]));
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	public PanelProductoSeleccion getPanel() { return panel; }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCategoriaSeleccion.INCLUIR_ACTION:
			superControl.setSeleccionado(producto, panel, !panel.isSeleccionado());
			break;
		}
	}

}
