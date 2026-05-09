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

public class ControlPanelPedidoCliente implements ActionListener {

	protected Tienda tienda;
	protected ClienteRegistrado cliente;
	protected Pedido pedido;
	protected PanelPedido panel;
	protected VentanaConDisplay<? super PanelPedido> vista;
	
	private static final String actionName = "Ver pedido";

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

		panel = new PanelPedido(actionName, pedido.getEstado().name(), nombreProductos.toArray(new String[0]));

		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(actionName)) {
			SwingUtilities.invokeLater(() -> new ControlVerInfoPedidoCliente(tienda, cliente, pedido));
		}
	}
}