package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladores.cliente.venta.pantallas.ControlVerInfoPedidoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.pedidos.Pedido;
import modelo.venta.productos.StockExterno;
import vistas.common.displays.PanelPedido;
import vistas.common.displays.VentanaConDisplay;

/**
 * Controlador del panel de un pedido del cliente.
 */
public class ControlPanelPedidoCliente implements ActionListener {

	/** Campo tienda. Referencia al modelo de la tienda. */
	protected Tienda tienda;
	
	/** Campo cliente. Cliente registrado propietario del pedido. */
	protected ClienteRegistrado cliente;
	
	/** Campo pedido. Pedido asociado a este panel. */
	protected Pedido pedido;
	
	/** Campo panel. Panel del pedido asociado a este controlador. */
	protected PanelPedido panel;
	
	/** Campo vista. Contenedor donde se muestra el panel del pedido. */
	protected VentanaConDisplay<? super PanelPedido> vista;
	
	/** Constante actionName. Comando de acción para ver el pedido. */
	private static final String actionName = "Ver pedido";

	/**
	 * Instancia un nuevo Objeto ControlPanelPedidoCliente.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado propietario del pedido.
	 * @param pedido Pedido asociado a este panel.
	 * @param vista Contenedor donde se añadirá el panel del pedido.
	 */
	public ControlPanelPedidoCliente(Tienda tienda, ClienteRegistrado cliente, Pedido pedido,
			VentanaConDisplay<? super PanelPedido> vista) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.pedido = pedido;
		this.vista = vista;

		ArrayList<String> nombreProductos = new ArrayList<>();
		for(StockExterno st : pedido.getItemsPedido()) {
			nombreProductos.add(st.getProducto().getNombre());
		}
		
		String estado;
		switch(pedido.getEstado()) {
		case EN_PREPARACION:
			estado = "En preparación";
			break;
		case LISTO:
			estado = "Listo para recoger";
			break;
		case PAGADO:
			estado = "Pagado";
			break;
		case RECOGIDO:
			estado = "Recogido";
			break;
		default:
			estado = "Sin estado";
			break;
		}

		panel = new PanelPedido(actionName, estado, ""+pedido.getId(), nombreProductos.toArray(new String[0]));

		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	/**
	 * actionPerformed.
	 * Abre la ventana de información detallada del pedido al hacer clic.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(actionName)) {
			SwingUtilities.invokeLater(() -> new ControlVerInfoPedidoCliente(tienda, cliente, pedido));
		}
	}
}