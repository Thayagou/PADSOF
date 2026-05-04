package controladores.empleado.gestionarProductos.gestionarExistentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelProducto;
import vistas.common.VentanaConDisplay;
import vistas.empleado.gestionarProductos.gestionarExistentes.PanelProductoGestionarProducto;

public class ControlPanelProductoGestionar implements ActionListener {
	private Producto producto;
	private Tienda tienda;
	private PanelProductoGestionarProducto panel;
	
	public ControlPanelProductoGestionar(Tienda tienda, Producto producto, VentanaConDisplay<? super PanelProducto> vista) {
		this.tienda = tienda;
		this.producto = producto;
		
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		
		String imageRoute;
		if(producto.getImagen() == null || producto.getImagen().isBlank()) imageRoute = "producto.png";
		else imageRoute = producto.getImagen();
		
		panel = new PanelProductoGestionarProducto(producto.getNombre(), producto.getDescripcion(), imageRoute, producto.getPuntuacionMedia(), producto.getPrecio(), categorias.toArray(new String[0]));
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelProductoGestionarProducto.BORRAR_ACTION:
			break;
		case PanelProductoGestionarProducto.MODIFICAR_ACTION:
			break;
		}
	}

}
