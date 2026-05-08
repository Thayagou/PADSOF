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
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlBarraTareasCliente implements ControlBarraTareas {

	private final Tienda tienda;
	private final ClienteRegistrado cliente;
	
	private static final String INFO_ACTION = "Info";
	private static final String VOLVER_ACTION = "Volver";
	private static final String HOME_ACTION = "Home";
	private static final String NOTIFICACIONES_ACTION = "Notificaciones";
	private static final String CUENTA_ACTION = "Cuenta";
	private static final String BUSCAR_PRODUCTOS_ACTION = "Buscar productos";
	private static final String CARRITO_ACTION = "Carrito";

	public ControlBarraTareasCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case VOLVER_ACTION:
			TiendaFrame.getInstance().volverAtras();
			break;
		case HOME_ACTION:
			SwingUtilities.invokeLater(() -> new ControlInicioCliente(tienda, cliente));
			break;
		case NOTIFICACIONES_ACTION:
			SwingUtilities.invokeLater(() -> new ControlNotificacionesCliente(tienda, cliente));
			break;
		case CUENTA_ACTION:
			SwingUtilities.invokeLater(() -> new ControlManejoCuenta(tienda, cliente));
			break;
		case BUSCAR_PRODUCTOS_ACTION:
			SwingUtilities.invokeLater(() -> new ControlBuscarCliente(tienda, cliente));
			break;
		case CARRITO_ACTION:
			SwingUtilities.invokeLater(() -> new ControlManejoCarrito(tienda, cliente));
			break;
		case INFO_ACTION:
			new VentanaMensaje(TiendaFrame.getInstance().getInfo(), 0);
			break;
		}
	}
}