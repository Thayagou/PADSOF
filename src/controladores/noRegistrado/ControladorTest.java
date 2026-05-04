package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelDisplay;
import vistas.common.PanelIntercambio;
import vistas.common.PanelIntercambioConBoton;
import vistas.common.PanelPedido;
import vistas.common.PanelProducto;
import vistas.common.PanelProductoSeleccion;
import vistas.common.VentanaConDisplay;
import vistas.common.VentanaMensaje;
import vistas.empleado.PanelPedidoGestionarPedido;
import vistas.empleado.PanelProductoGestionarProducto;
import vistas.gestor.consultarEstadisticas.PanelClienteEstadisticas;

public class ControladorTest implements ActionListener {
	private Producto producto;
	private Tienda tienda;
	private PanelDisplay panel;
	
	private static final String DF_PRODUCT_IMAGE = "producto.png";
	
	public ControladorTest(Tienda tienda, Producto producto, VentanaConDisplay<? super PanelDisplay> vista) {
		this.producto = producto;
		this.tienda = tienda;
		
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		
		String imageRoute;
		if(producto.getImagen() == null || producto.getImagen().isBlank()) imageRoute = DF_PRODUCT_IMAGE;
		else imageRoute = producto.getImagen();

		panel =  //new PanelCategoriaGestionarCategoria(producto.getNombre());
				//new PanelEmpleado(producto.getNombre(), ((int) producto.getPrecio()) % 2 == 1 ? true : false, "Pedidos", "Productos");
				//new PanelCategoriaSeleccion(producto.getNombre());
				//new PanelDisplay(1.01*0.1, 0.1, 0.09, "producto.png", "Ver producto:");
				//new PanelProducto(producto.getNombre(), producto.getDescripcion(), imageRoute, producto.getPuntuacionMedia(), producto.getPrecio(), "Ver producto", categorias.toArray(new String[0]));
				//new PanelProductoGestionarProducto(producto.getNombre(), producto.getDescripcion(), imageRoute, producto.getPuntuacionMedia(), producto.getPrecio(), categorias.toArray(new String[0]));
				//new PanelProducto(producto.getNombre(), producto.getDescripcion(), imageRoute, producto.getPuntuacionMedia(), producto.getPrecio(), "Ver producto", categorias.toArray(new String[0]));
				//new PanelClienteEstadisticas("Juan de Lara", DF_PRODUCT_IMAGE, 20.75f, 3, 15);
				//new PanelProductoAplicarDescuento(producto.getNombre(), producto.getDescripcion(), imageRoute, producto.getPuntuacionMedia(), producto.getPrecio(), categorias.toArray(new String[0]));
				//new PanelProductoAnadirAPack(producto.getNombre(), producto.getDescripcion(), imageRoute, producto.getPuntuacionMedia(), producto.getPrecio(), categorias.toArray(new String[0]));
				//new PanelProductoEstadisticas(producto.getNombre(), producto.getDescripcion(), producto.getPuntuacionMedia(), producto.getPrecio(), 10f, 25, 15f, categorias.toArray(new String[0]));
				//new PanelArticulo("Juan de Lara", "producto.png", producto.getNombre(), producto.getDescripcion(), "Cosas de One Piece", producto.getPrecio(), "Muy bueno", "Ver preoducto", categorias.toArray(new String[0]));
				//new PanelArticuloPendienteValoracion("Juan de Lara", "producto.png", producto.getNombre(), producto.getDescripcion(), "Cosas de One Piece", -1, "Muy bueno", "Ver preoducto", categorias.toArray(new String[0]));
				//new PanelArticuloEnCartera("Juan de Lara", "producto.png", producto.getNombre(), producto.getDescripcion(), "Cosas de One Piece", -1, "Muy bueno", "Ver preoducto", categorias.toArray(new String[0]));
				//new PanelArticuloSeleccion(producto.getNombre(), producto.getDescripcion(), "Cosas de One Piece", -1, "Muy bueno", "Ver preoducto", categorias.toArray(new String[0]));
				//new PanelIntercambio("Tiago", "producto.png", new String[]{"Comic1", "Comic1", "Comic1", "Comic1", "Comic1", "Comic1", "Comic1"}, "Ver intercambio", "Claudia", "producto.png", new String[]{"Comic2"});
				//new PanelIntercambioConBoton ("Tiago", "producto.png", new String[]{"Comic1", "Comic1", "Comic1", "Comic1", "Comic1", "Comic1", "Comic1"}, "Ver intercambio", "Aceptar intercambio", "Claudia", "producto.png", new String[]{"Comic2"});
				//new PanelPedido("Tiago", "producto.png", "Ver intercambio", new String[]{"Comic1", "Comic1", "Comic1", "Comic1", "Comic1", "Comic1", "Comic1"});
				//new PanelPedido("Ver intercambio", new String[]{"Comic1", "Comic1", "Comic1", "Comic1", "Comic1", "Comic1", "Comic1"});
				new PanelPedidoGestionarPedido("Tiago", "producto.png", "Ver intercambio", new String[]{"Comic1", "Comic1", "Comic1", "Comic1", "Comic1", "Comic1", "Comic1"});
		
				
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
