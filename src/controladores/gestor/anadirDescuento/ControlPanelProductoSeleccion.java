package controladores.gestor.anadirDescuento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelCategoriaSeleccion;
import vistas.common.PanelProducto;
import vistas.common.PanelProductoSeleccion;
import vistas.common.VentanaConDisplay;

public class ControlPanelProductoSeleccion implements ActionListener {
	private Producto producto;
	private Tienda tienda;
	private PanelProductoSeleccion panel;
	
	public ControlPanelProductoSeleccion(Tienda tienda, Producto producto, VentanaConDisplay<? super PanelProducto> vista) {
		this.tienda = tienda;
		this.producto = producto;
		
		String imageName = producto.getImagen();
		if (imageName == null || imageName.isBlank()) imageName = "producto.png";
		
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		
		panel = new PanelProductoSeleccion(producto.getNombre(), producto.getDescripcion(), imageName, producto.getPuntuacionMedia(), producto.getPrecio(), categorias.toArray(new String[0]));
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCategoriaSeleccion.INCLUIR_ACTION:
			panel.toggleCheckBox();
			break;
		}
	}

}
