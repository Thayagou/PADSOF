package controladores.cliente.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controladores.cliente.general.pantallas.ControlNotificacionesCliente;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import vistas.common.displays.PanelNotificacion;
import vistas.common.displays.VentanaConDisplay;

/**
 * Tipo: Class ControlPanelNotificacion.
 */
public class ControlPanelNotificacion implements ActionListener {
	
	/** Campo notificacion. */
	protected Notificacion notificacion;
	
	/** Campo tienda. */
	protected Tienda tienda;
	
	/** Campo panel. */
	protected PanelNotificacion panel;
	
	/** Campo vista. */
	protected VentanaConDisplay<? super PanelNotificacion> vista;
	
	/** Campo cliente. */
	protected ClienteRegistrado cliente;
	
	/** Campo controlador. */
	private ControlNotificacionesCliente controlador;

	/**
	 * Instancia un nuevo Objeto ControlPanelNotificacion.
	 *
	 * @param tienda parámetro tienda
	 * @param cliente parámetro cliente
	 * @param notificacion parámetro notificacion
	 * @param vista parámetro vista
	 * @param controlador parámetro controlador
	 */
	public ControlPanelNotificacion(Tienda tienda, ClienteRegistrado cliente, Notificacion notificacion,
			VentanaConDisplay<? super PanelNotificacion> vista, ControlNotificacionesCliente controlador) {
		this.notificacion = notificacion;
		this.tienda = tienda;
		this.cliente = cliente;
		this.vista = vista;
		this.controlador = controlador;

		panel = new PanelNotificacion(notificacion.getTipo().name(), notificacion.getContenido(),
				notificacion.getFecha(), notificacion.isLeida());

		vista.anadirDisplay(panel);

		panel.setControlador(this);
	}

	/**
	 * actionPerformed.
	 *
	 * @param e parámetro e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelNotificacion.READ_ACTION:
			this.notificacion.marcarLeida();
			controlador.recargarPantalla();
			break;
		case PanelNotificacion.DELETE_ACTION:
			this.notificacion.borrar();
			controlador.recargarPantalla();
			break;
		}
	}
}
