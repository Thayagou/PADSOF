package controladores.empleado.gestionarPedidos;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.venta.pedidos.Pedido;
import vistas.common.app.TiendaFrame;
import vistas.empleado.gestionarPedidos.VentanaGestPedidos;

public class ControlGestPedidos implements ControladorPantalla {

	private VentanaGestPedidos vista;

	public ControlGestPedidos(Tienda tienda, Empleado empleado) {
		this.vista = new VentanaGestPedidos();
		Pedido[] pedidos = tienda.getHistorial().getPedidosPendientes();
		for(Pedido p : pedidos) {
			new ControlPanelGestionarPedido(tienda, empleado, p, vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}
