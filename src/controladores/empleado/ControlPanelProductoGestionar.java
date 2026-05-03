package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelCategoria;
import vistas.common.VentanaConDisplay;
import vistas.empleado.PanelCategoriaGestionarCategoria;
import vistas.empleado.PanelProductoGestionarProducto;

public class ControlPanelProductoGestionar implements ActionListener {
	private Producto producto;
	private Tienda tienda;
	private PanelProductoGestionarProducto panel;
	
	public ControlPanelProductoGestionar(Tienda tienda, Producto producto, VentanaConDisplay<? super PanelCategoria> vista) {
		this.tienda = tienda;
		this.producto = producto;
		
		panel = new PanelProductoGestionarProducto(producto.getNombre());
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCategoriaGestionarCategoria.BORRAR_ACTION:
			break;
		case PanelCategoriaGestionarCategoria.MODIFICAR_ACTION:
			break;
		}
	}

}
