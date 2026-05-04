package controladores.empleado.gestionarPedidos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.venta.pedidos.Pedido;
import modelo.venta.productos.StockExterno;
import vistas.common.VentanaMensaje;
import vistas.empleado.gestionarPedidos.PanelPedidoGestionarPedido;
import vistas.empleado.gestionarPedidos.VentanaGestPedidos;

public class ControlPanelGestionarPedido implements ActionListener {
	private final Tienda tienda;
	private final Empleado empleado;
	private final Pedido pedido;
	private final String ACTION_NAME = "Avanzar estado del pedido";
	
	public ControlPanelGestionarPedido(Tienda tienda, Empleado empleado, Pedido pedido, VentanaGestPedidos vista) {
		this.tienda = tienda;
		this.empleado = empleado;
		this.pedido = pedido;
		
		List<String> productos = new LinkedList<>();
		for(StockExterno s : pedido.getItemsPedido()) {
			productos.add(s.getProducto().getNombre());
		}
		
		PanelPedidoGestionarPedido panel = new PanelPedidoGestionarPedido(pedido.getCliente().getNombre(), pedido.getEstado().name(), "pfp.png", ACTION_NAME, productos.toArray(new String[0]));
		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ACTION_NAME:
			intentarAvanzar();
			break;
		}
	}
	
	private void intentarAvanzar() {
		try {
			tienda.getHistorial().avanzarEstadoPedido(empleado, pedido);
		} catch (InvalidArgumentException | InvalidPermitException e) {
			new VentanaMensaje(e.getMessage());
		}
		
		new VentanaMensaje("El pedido se ha avanzado correctamente");
		SwingUtilities.invokeLater(() -> new ControlGestPedidos(tienda, empleado));
	}
}
