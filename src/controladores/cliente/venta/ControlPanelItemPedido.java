package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.cliente.venta.pantallas.ControlAnadirResena;
import controladores.cliente.venta.pantallas.ControlInfoProductoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.StockExterno;
import vistas.cliente.venta.PanelItemPedido;
import vistas.common.displays.VentanaConDisplay;

/**
 * Controlador del panel de un producto dentro de un pedido del cliente.
 */
public class ControlPanelItemPedido implements ActionListener {

	/** Campo tienda. Referencia al modelo de la tienda. */
	protected Tienda tienda;
	
	/** Campo cliente. Cliente registrado propietario del pedido. */
	protected ClienteRegistrado cliente;
	
	/** Campo item. Stock del producto incluido en el pedido. */
	protected StockExterno item;
	
	/** Campo panel. Panel del ítem del pedido asociado a este controlador. */
	protected PanelItemPedido panel;
	
	/** Campo vista. Contenedor donde se muestra el panel del ítem. */
	protected VentanaConDisplay<? super PanelItemPedido> vista;
	
	/** Constante seeProduct. Comando de acción para ver el producto. */
	private static final String seeProduct = "Ver producto";

	/** Constante DF_PRODUCT_IMAGE. Ruta de la imagen por defecto del producto. */
	protected static final String DF_PRODUCT_IMAGE = "producto.png";

	/**
	 * Instancia un nuevo Objeto ControlPanelItemPedido.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado propietario del pedido.
	 * @param item Stock del producto incluido en el pedido.
	 * @param vista Contenedor donde se añadirá el panel del ítem.
	 */
	public ControlPanelItemPedido(Tienda tienda, ClienteRegistrado cliente, StockExterno item,
			VentanaConDisplay<? super PanelItemPedido> vista) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.item = item;
		this.vista = vista;

		int unidades = item.getUdsEnStock();
		
		String imageRoute;
		if (item.getProducto().getImagen() == null || item.getProducto().getImagen().isBlank())
			imageRoute = DF_PRODUCT_IMAGE;
		else
			imageRoute = item.getProducto().getImagen();

		String[] categorias = java.util.Arrays.stream(item.getProducto().getCategorias()).map(c -> c.getNombre())
				.toArray(String[]::new);

		panel = new PanelItemPedido(item.getProducto().getNombre(), item.getProducto().getDescripcion(),
				imageRoute, item.getProducto().getPuntuacionMedia(), item.getProducto().getPrecio(),
				unidades, seeProduct, categorias);

		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	/**
	 * actionPerformed.
	 * Gestiona la visualización del producto o la adición de una reseña.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case seeProduct:
			SwingUtilities.invokeLater(() -> new ControlInfoProductoCliente(tienda, cliente, item.getProducto()));
			break;
		case PanelItemPedido.VALORAR_ACTION:
			SwingUtilities.invokeLater(() -> new ControlAnadirResena(tienda, cliente, item.getProducto()));
			break;
		}
	}
}