package controladores.cliente.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.usuario.*;
import vistas.common.*;

public class ControlPanelNotificacion implements ActionListener {
	protected Notificacion notificacion;
	protected Tienda tienda;
	protected PanelNotificacion panel;
	protected VentanaConDisplay<? super PanelNotificacion> vista;
	protected ClienteRegistrado cliente;

	protected static final String DF_PRODUCT_IMAGE = "producto.png";

	public ControlPanelNotificacion(Tienda tienda, ClienteRegistrado cliente, Notificacion notificacion,
			VentanaConDisplay<? super PanelNotificacion> vista) {
		this.notificacion = notificacion;
		this.tienda = tienda;
		this.cliente = cliente;
		this.vista = vista;

		panel = new PanelNotificacion(notificacion.getTipo().name(), notificacion.getContenido(), notificacion.getFecha(), notificacion.isLeida());

		vista.anadirDisplay(panel);

		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "leido":
			this.notificacion.marcarLeida();
			new VentanaMensaje("hola");
			SwingUtilities.invokeLater(() -> new ControlNotificacionesCliente(tienda, cliente));
			break;
		case "borrar":
			this.notificacion.borrar();
			SwingUtilities.invokeLater(() -> new ControlNotificacionesCliente(tienda, cliente));
			break;
		}
	}
}
