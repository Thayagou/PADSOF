package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlPanelItemPedido;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.pedidos.Pedido;
import modelo.venta.productos.StockExterno;
import vistas.cliente.venta.pantallas.VentanaInfoPedidoCliente;
import vistas.common.app.TiendaFrame;

public class ControlVerInfoPedidoCliente implements ControladorPantalla {

	private Tienda tienda;
	private ClienteRegistrado cliente;
	@SuppressWarnings("unused")
	private Pedido pedido;
	private VentanaInfoPedidoCliente vista;

	public ControlVerInfoPedidoCliente(Tienda tienda, ClienteRegistrado cliente, Pedido pedido) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.pedido = pedido;

		this.vista = new VentanaInfoPedidoCliente();

		for (StockExterno st : pedido.getItemsPedido()) {
			new ControlPanelItemPedido(tienda, cliente, st, vista);
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Reservado para acciones globales futuras (ej. "Volver a mis compras")
		switch (e.getActionCommand()) {
		case "Volver":
			new ControlVerCompras(tienda, cliente);
			break;
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "Aquí se muestra la información de un pedido que realizó usted. Puede valorar los productos que compró pinchando en \"Valorar\"";
	}
}
