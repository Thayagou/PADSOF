package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.pedidos.Pedido;
import vistas.cliente.venta.VentanaCompras;
import vistas.common.TiendaFrame;

public class ControlVerCompras implements ActionListener {

	private Tienda tienda;
	private ClienteRegistrado cliente;
	private VentanaCompras vista;

	public ControlVerCompras(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;

		this.vista = new VentanaCompras();

		for (Pedido pedido : cliente.getPedidos()) {
			new ControlPanelPedidoCliente(tienda, cliente, pedido, vista);
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Reservado para acciones globales futuras (ej. filtrar por fecha, estado...)
	}
}