package controladores.empleado.gestionarPedidos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.venta.pedidos.Pedido;
import vistas.common.TiendaFrame;
import vistas.empleado.gestionarPedidos.VentanaGestPedidos;

public class ControlGestPedidos implements ActionListener{

	private VentanaGestPedidos vista;

	public ControlGestPedidos(Tienda tienda, Empleado empleado) {
		this.vista = new VentanaGestPedidos();
		Pedido[] pedidos = tienda.getHistorial().getPedidosPendientes();
		for(Pedido p : pedidos) {
			new ControlPanelGestionarPedido(tienda, empleado, p, vista);
		}
		
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
