package controladores.cliente.general.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.general.ControlPanelNotificacion;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Notificacion;
import vistas.cliente.general.pantallas.VentanaNotificacionesCliente;
import vistas.common.*;

public class ControlNotificacionesCliente implements ActionListener, ControladorPantalla {

	private Tienda tienda;
	private VentanaNotificacionesCliente vista;
	private ClienteRegistrado cliente;

	public ControlNotificacionesCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;

		this.vista = new VentanaNotificacionesCliente();

		for (Notificacion n : cliente.getNotificaciones()) {
			if (!n.isBorrada()) {
				new ControlPanelNotificacion(tienda, cliente, n, vista, this);
			}
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().navegarA(this);

	}
	
	public void recargarPantalla() {
		this.vista = new VentanaNotificacionesCliente();

		for (Notificacion n : cliente.getNotificaciones()) {
			if (!n.isBorrada()) {
				new ControlPanelNotificacion(tienda, cliente, n, vista, this);
			}
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().recargarPantallaActual(this);
	}
	
	public void refreshVista() {
		vista.refreshList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
