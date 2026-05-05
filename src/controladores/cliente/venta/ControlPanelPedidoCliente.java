package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.pedidos.Pedido;
import modelo.venta.productos.StockExterno;
import vistas.common.*;

public class ControlPanelPedidoCliente implements ActionListener {

	protected Tienda tienda;
	protected ClienteRegistrado cliente;
	protected Pedido pedido;
	protected PanelPedido panel;
	protected VentanaConDisplay<? super PanelPedido> vista;

	public ControlPanelPedidoCliente(Tienda tienda, ClienteRegistrado cliente, Pedido pedido,
			VentanaConDisplay<? super PanelPedido> vista) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.pedido = pedido;
		this.vista = vista;

		// Obtener los nombres de los productos del pedido para mostrarlos en el panel
		ArrayList<String> nombreProductos = new ArrayList<>();
		for(StockExterno st : pedido.getItemsPedido()) {
			nombreProductos.add(st.getProducto().getNombre());
		}

		// Usar el id/nombre del pedido como actionCommand para identificarlo al hacer clic
		panel = new PanelPedido("Ver pedido", pedido.getEstado().name(), nombreProductos.toArray(new String[0]));

		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Ver pedido")) {
			SwingUtilities.invokeLater(() -> new ControlVerInfoPedidoCliente(tienda, cliente, pedido));
		}
	}
}