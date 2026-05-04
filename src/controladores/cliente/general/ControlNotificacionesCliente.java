package controladores.cliente.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Notificacion;
import vistas.cliente.general.VentanaNotificacionesCliente;
import vistas.common.*;

public class ControlNotificacionesCliente implements ActionListener, ControladorPantalla {

	@SuppressWarnings("unused")
	private Tienda tienda;
	private VentanaNotificacionesCliente vista;
	@SuppressWarnings("unused")
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
