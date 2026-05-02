package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.cliente.PanelArticuloEnCartera;
import vistas.common.PanelArticulo;
import vistas.common.PanelCategoriaSeleccion;
import vistas.common.PanelDisplay;
import vistas.common.PanelProducto;
import vistas.common.VentanaConDisplay;
import vistas.common.VentanaMensaje;
import vistas.empleado.PanelArticuloPendienteValoracion;
import vistas.empleado.PanelCategoriaGestionarCategoria;
import vistas.empleado.PanelProductoAnadirAPack;
import vistas.empleado.PanelProductoGestionarProducto;
import vistas.gestor.PanelEmpleado;
import vistas.gestor.PanelProductoAplicarDescuento;
import vistas.gestor.PanelProductoEstadisticas;

public class ControlPanelProductoNoRegistrado implements ActionListener {
	private Producto producto;
	private Tienda tienda;
	private PanelDisplay panel;
	
	public ControlPanelProductoNoRegistrado(Tienda tienda, Producto producto, VentanaConDisplay<? super PanelDisplay> vista) {
		this.producto = producto;
		this.tienda = tienda;
		
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		panel =  //new PanelCategoriaGestionarCategoria(producto.getNombre());
				//new PanelEmpleado(producto.getNombre(), ((int) producto.getPrecio()) % 2 == 1 ? true : false, "Pedidos", "Productos");
				//new PanelCategoriaSeleccion(producto.getNombre());
				//new PanelDisplay(1.01*0.1, 0.1, 0.09, "producto.png", "Ver producto:");
				//new PanelProducto(producto.getNombre(), producto.getDescripcion(), producto.getPuntuacionMedia(), producto.getPrecio(), "Ver producto", categorias.toArray(new String[0]));
				//new PanelProductoGestionarProducto(producto.getNombre(), producto.getDescripcion(), producto.getPuntuacionMedia(), producto.getPrecio(), categorias.toArray(new String[0]));
				//new PanelProductoAplicarDescuento(producto.getNombre(), producto.getDescripcion(), producto.getPuntuacionMedia(), producto.getPrecio(), categorias.toArray(new String[0]));
				//new PanelProductoAnadirAPack(producto.getNombre(), producto.getDescripcion(), producto.getPuntuacionMedia(), producto.getPrecio(), categorias.toArray(new String[0]));
				//new PanelProductoEstadisticas(producto.getNombre(), producto.getDescripcion(), producto.getPuntuacionMedia(), producto.getPrecio(), 10f, 25, 15f, categorias.toArray(new String[0]));
				//new PanelArticulo("Juan de Lara", "producto.png", producto.getNombre(), producto.getDescripcion(), "Cosas de One Piece", producto.getPrecio(), "Muy bueno", "Ver preoducto", categorias.toArray(new String[0]));
				//new PanelArticuloPendienteValoracion("Juan de Lara", "producto.png", producto.getNombre(), producto.getDescripcion(), "Cosas de One Piece", -1, "Muy bueno", "Ver preoducto", categorias.toArray(new String[0]));
				new PanelArticuloEnCartera("Juan de Lara", "producto.png", producto.getNombre(), producto.getDescripcion(), "Cosas de One Piece", -1, "Muy bueno", "Ver preoducto", categorias.toArray(new String[0]));
		
		
		vista.anadirDisplay(panel);
		
		//panel = vista.anadirProductoRecomendado(producto.getNombre(), producto.getDescripcion(), producto.getPuntuacionMedia(), producto.getPrecio(), categorias.toArray(new String[0]));
		
		panel.setControlador(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Ver producto":
			SwingUtilities.invokeLater(() -> {
				try {
					new ControlProductoSinRegistrar(tienda, producto);
				} catch (Exception ex) {
					new VentanaMensaje("Producto no encontrado: " + producto.getNombre());
				}
			});
		}		
	}
}
