package controladores.cliente.venta;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladores.cliente.venta.pantallas.ControlInfoProductoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;

/**
 * Controlador del panel de un producto para clientes registrados.
 */
public class ControlPanelProductoCliente implements ActionListener {
	
	/** Campo producto. Producto asociado a este panel. */
	protected Producto producto;
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	protected Tienda tienda;
	
	/** Campo panel. Panel del producto asociado a este controlador. */
	protected PanelProducto panel;
	
	/** Campo vista. Contenedor donde se muestra el panel del producto. */
	protected VentanaConDisplay<? super PanelProducto> vista;
	
	/** Campo cliente. Cliente registrado que visualiza el producto. */
	protected ClienteRegistrado cliente;

	/** Constante DF_PRODUCT_IMAGE. Ruta de la imagen por defecto del producto. */
	protected static final String DF_PRODUCT_IMAGE = "producto.png";
	
	/** Constante actionName. Comando de acción para ver el producto. */
	private static final String actionName = "Ver producto";

	/**
	 * Instancia un nuevo Objeto ControlPanelProductoCliente.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza el producto.
	 * @param producto Producto asociado a este panel.
	 * @param vista Contenedor donde se añadirá el panel del producto.
	 */
	public ControlPanelProductoCliente(Tienda tienda, ClienteRegistrado cliente, Producto producto,
			VentanaConDisplay<? super PanelProducto> vista) {
		this.producto = producto;
		this.tienda = tienda;
		this.cliente = cliente;
		this.vista = vista;

		ArrayList<String> categorias = new ArrayList<>();
		for (Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}

		String imageRoute;
		if (producto.getImagen() == null || producto.getImagen().isBlank())
			imageRoute = DF_PRODUCT_IMAGE;
		else
			imageRoute = producto.getImagen();

		panel = new PanelProducto(producto.getNombre(), producto.getDescripcion(), imageRoute,
				producto.getPuntuacionMedia(), producto.getPrecio(), actionName, categorias.toArray(new String[0]));

		vista.anadirDisplay(panel);

		panel.setControlador(this);
	}

	/**
	 * actionPerformed.
	 * Abre la ventana de información detallada del producto al hacer clic.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case actionName:
			SwingUtilities.invokeLater(() -> {
				try {
					new ControlInfoProductoCliente(tienda, cliente, producto);
				} catch (Exception ex) {
					new VentanaMensaje("Producto no encontrado: " + producto.getNombre(), VentanaMensaje.ERROR);
				}
			});
		}
	}
}