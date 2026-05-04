package controladores.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Notificacion;
import vistas.cliente.VentanaNotificacionesCliente;
import vistas.common.*;

public class ControlNotificacionesCliente implements ActionListener {

	private Tienda tienda;
	private VentanaNotificacionesCliente vista;
	private ClienteRegistrado cliente;

	public ControlNotificacionesCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;

		this.vista = new VentanaNotificacionesCliente();

		for (Notificacion n : cliente.getNotificaciones()) {
			if (!n.isBorrada()) {
				new ControlPanelNotificacion(tienda, cliente, n, vista);
			}
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().setVistaActual(vista);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
