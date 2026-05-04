package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.pedidos.Pedido;
import modelo.venta.productos.Producto;
import modelo.venta.productos.StockExterno;
import vistas.cliente.venta.VentanaInfoPedidoCliente;
import vistas.common.TiendaFrame;

public class ControlVerInfoPedidoCliente implements ActionListener {

	private Tienda tienda;
	private ClienteRegistrado cliente;
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

		TiendaFrame.getInstance().setVistaActual(vista);
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
}
