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
		case "Volver" -> TiendaFrame.getInstance().volverAtras();
		case "Home" -> SwingUtilities.invokeLater(() -> new ControlInicioCliente(tienda, cliente));
		case "Notificaciones" -> SwingUtilities.invokeLater(() -> new ControlNotificacionesCliente(tienda, cliente));
		case "Cuenta" -> SwingUtilities.invokeLater(() -> new ControlManejoCuenta(tienda, cliente));
		case "Buscar productos" -> SwingUtilities.invokeLater(() -> new ControlBuscarCliente(tienda, cliente));
		case "Carrito" -> SwingUtilities.invokeLater(() -> new ControlManejoCarrito(tienda, cliente));
		}
	}
}