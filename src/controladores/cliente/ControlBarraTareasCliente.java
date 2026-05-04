package controladores.cliente;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.*;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;

public class ControlBarraTareasCliente implements ControlBarraTareas{

	private final Tienda tienda;
	private final ClienteRegistrado cliente;

	public ControlBarraTareasCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Buscar productos" -> SwingUtilities.invokeLater(() -> new ControlBuscarCliente(tienda, cliente));
		case "Home" -> SwingUtilities.invokeLater(() -> new ControlInicioCliente(tienda, cliente));
		case "Notificaciones" -> SwingUtilities.invokeLater(() -> new ControlNotificacionesCliente(tienda, cliente));
		case "Cuenta" -> SwingUtilities.invokeLater(() -> new ControlManejoCuenta(tienda, cliente));
		case "Carrito" -> SwingUtilities.invokeLater(() -> new ControlManejoCarrito(tienda, cliente));
		}
	}
}
