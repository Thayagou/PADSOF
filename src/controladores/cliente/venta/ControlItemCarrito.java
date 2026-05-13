package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladores.cliente.venta.pantallas.ControlInfoProductoCliente;
import controladores.cliente.venta.pantallas.ControlManejoCarrito;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import modelo.venta.productos.StockExterno;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.VentanaConDisplay;
import vistas.cliente.venta.PanelItemCarrito;

/**
 * Controlador del panel de un producto dentro del carrito de compras.
 */
public class ControlItemCarrito implements ActionListener {
	
	/** Campo producto. Producto asociado a este ítem del carrito. */
	protected Producto producto;
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	protected Tienda tienda;
	
	/** Campo panel. Panel del ítem del carrito asociado a este controlador. */
	protected PanelItemCarrito panel;
	
	/** Campo vista. Contenedor donde se muestra el panel del ítem. */
	protected VentanaConDisplay<? super PanelItemCarrito> vista;
	
	/** Campo cliente. Cliente registrado propietario del carrito. */
	protected ClienteRegistrado cliente;
	
	/** Campo unidades. Cantidad de unidades del producto en el carrito. */
	private int unidades;
	
	/** Campo controlador. Controlador padre del carrito para refrescar la vista. */
	private ControlManejoCarrito controlador;

	/** Constante DF_PRODUCT_IMAGE. Ruta de la imagen por defecto del producto. */
	protected static final String DF_PRODUCT_IMAGE = "producto.png";
	
	/** Constante seeProduct. Comando de acción para ver el producto. */
	private static final String seeProduct = "Ver producto";

	/**
	 * Instancia un nuevo Objeto ControlItemCarrito.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado propietario del carrito.
	 * @param stock Stock del producto en el carrito.
	 * @param vista Contenedor donde se añadirá el panel del ítem.
	 * @param controlador Controlador padre del carrito para refrescar la vista.
	 */
	public ControlItemCarrito(Tienda tienda, ClienteRegistrado cliente, StockExterno stock,
			VentanaConDisplay<? super PanelItemCarrito> vista, ControlManejoCarrito controlador) {
		this.producto = stock.getProducto();
		this.unidades = stock.getUdsEnStock();
		this.tienda = tienda;
		this.cliente = cliente;
		this.vista = vista;
		this.controlador = controlador;

		ArrayList<String> categorias = new ArrayList<>();
		for (Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}

		String imageRoute;
		if (producto.getImagen() == null || producto.getImagen().isBlank())
			imageRoute = DF_PRODUCT_IMAGE;
		else
			imageRoute = producto.getImagen();

		panel = new PanelItemCarrito(producto.getNombre(), producto.getDescripcion(), imageRoute,
				producto.getPuntuacionMedia(), producto.getPrecio(), unidades, seeProduct,
				categorias.toArray(new String[0]));

		vista.anadirDisplay(panel);

		panel.setControlador(this);
	}

	/**
	 * actionPerformed.
	 * Gestiona la eliminación del producto del carrito o la visualización de su información.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelItemCarrito.QUITAR_ACTION:
			try {
				tienda.quitarDeCarritoDe(cliente, producto);
				controlador.recargarPantalla();
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
			}
			break;
		case seeProduct:
			SwingUtilities.invokeLater(() -> new ControlInfoProductoCliente(tienda, cliente, producto));
		}
	}
}