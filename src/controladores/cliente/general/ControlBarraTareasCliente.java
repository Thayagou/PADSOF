package controladores.cliente.general;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraTareas;
import controladores.cliente.general.pantallas.ControlInicioCliente;
import controladores.cliente.general.pantallas.ControlManejoCuenta;
import controladores.cliente.general.pantallas.ControlNotificacionesCliente;
import controladores.cliente.venta.pantallas.ControlBuscarCliente;
import controladores.cliente.venta.pantallas.ControlManejoCarrito;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.cliente.general.BarraTareasCliente;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlBarraTareasCliente implements ControlBarraTareas {

	private final Tienda tienda;
	private final ClienteRegistrado cliente;

	public ControlBarraTareasCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case BarraTareasCliente.VOLVER_ACTION:
			TiendaFrame.getInstance().volverAtras();
			break;
		case BarraTareasCliente.HOME_ACTION:
			SwingUtilities.invokeLater(() -> new ControlInicioCliente(tienda, cliente));
			break;
		case BarraTareasCliente.NOTIFICACIONES_ACTION:
			SwingUtilities.invokeLater(() -> new ControlNotificacionesCliente(tienda, cliente));
			break;
		case BarraTareasCliente.CUENTA_ACTION:
			SwingUtilities.invokeLater(() -> new ControlManejoCuenta(tienda, cliente));
			break;
		case BarraTareasCliente.BUSCAR_PRODUCTOS_ACTION:
			SwingUtilities.invokeLater(() -> new ControlBuscarCliente(tienda, cliente));
			break;
		case BarraTareasCliente.CARRITO_ACTION:
			SwingUtilities.invokeLater(() -> new ControlManejoCarrito(tienda, cliente));
			break;
		case BarraTareasCliente.INFO_ACTION:
			new VentanaMensaje(TiendaFrame.getInstance().getInfo(), 0);
			break;
		}
	}
}