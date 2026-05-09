package controladores.empleado.gestionarProductos.gestionarExistentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Stock;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.empleado.gestionarProductos.gestionarExistentes.PanelProductoGestionarProducto;

public class ControlPanelProductoGestionar implements ActionListener {
	private final Stock stock;
	private final Usuario usuario;
	private final Tienda tienda;
	private final PanelProductoGestionarProducto panel;
	private final ControlGestionarExistentes padre;
	
	public ControlPanelProductoGestionar(Tienda tienda, Usuario usuario, Stock stock, VentanaConDisplay<? super PanelProducto> vista, ControlGestionarExistentes padre) {
		this.tienda = tienda;
		this.usuario = usuario;
		this.stock = stock;
		Producto producto = stock.getProducto();
		this.padre = padre;
		
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
			intentarBorrar();
			break;
		case PanelProductoGestionarProducto.MODIFICAR_ACTION:
			intentarModificar();
			break;
		}
	}
	
	private void intentarBorrar() {
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas borrar este producto?")) {
			try {
				tienda.getAlmacen().eliminarProducto(usuario, stock.getProducto());
			} catch (InvalidArgumentException | InvalidPermitException e) {
				new VentanaMensaje(e.getMessage());
				return;
			}
			padre.mostrar();
			new VentanaMensaje("El producto se elimino correctamente");
		}
	}
	
	private void intentarModificar() {
		SwingUtilities.invokeLater(() -> new ControlModificarProductos(tienda, usuario, stock));
	}

}
