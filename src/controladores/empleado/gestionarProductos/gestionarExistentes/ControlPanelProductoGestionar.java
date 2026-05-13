package controladores.empleado.gestionarProductos.gestionarExistentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladores.TiendaFrame;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Stock;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.empleado.gestionarProductos.gestionarExistentes.PanelProductoGestionarProducto;

/**
 * Esta clase representa el controlador de un panel para gestionar producto
 */
public class ControlPanelProductoGestionar implements ActionListener {
	/** Producto que se está gestionando */
	private final Stock stock;
	/** Usuario que realiza la acción */
	private final Usuario usuario;
	/** Modelo de la tienda sobre el que se actúa */
	private final Tienda tienda;
	/** Panel que se controla */
	private final PanelProductoGestionarProducto panel;
	/** Controlador de la ventana en la que se muestra el panel */
	private final ControlGestionarExistentes padre;
	
	/**
	 * Constructor del controlador del panel de gestionar productos
	 * @param tienda Modelo de la tienda
	 * @param usuario Usuario que realiza la acción
	 * @param stock Producto que se gestiona
	 * @param vista Ventana sobre la que se muestra el panel
	 * @param padre Controlador de la ventana sobre la que se muestra el panel
	 */
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
	
	/**
	 * Acción que se realiza al intentar borrar un producto
	 */
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
	
	/**
	 * Acción que se realiza al intentar modificar un producto
	 */
	private void intentarModificar() {
		SwingUtilities.invokeLater(() -> new ControlModificarProductos(tienda, usuario, stock));
	}

}
