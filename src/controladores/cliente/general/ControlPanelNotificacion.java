package controladores.cliente.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controladores.cliente.general.pantallas.ControlNotificacionesCliente;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import vistas.common.displays.PanelNotificacion;
import vistas.common.displays.VentanaConDisplay;

/**
 * Controlador de un panel individual de notificación, gestiona las acciones de marcar como leída y eliminar.
 */
public class ControlPanelNotificacion implements ActionListener {
	
	/** Campo notificacion. Notificación asociada a este panel. */
	protected Notificacion notificacion;
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	protected Tienda tienda;
	
	/** Campo panel. Panel de visualización de la notificación. */
	protected PanelNotificacion panel;
	
	/** Campo vista. Contenedor donde se muestra el panel de notificación. */
	protected VentanaConDisplay<? super PanelNotificacion> vista;
	
	/** Campo cliente. Cliente registrado propietario de la notificación. */
	protected ClienteRegistrado cliente;
	
	/** Campo controlador. Controlador padre de notificaciones para refrescar la vista. */
	private ControlNotificacionesCliente controlador;

	/**
	 * Instancia un nuevo Objeto ControlPanelNotificacion.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado propietario de la notificación.
	 * @param notificacion Notificación a mostrar en el panel.
	 * @param vista Contenedor donde se añadirá el panel de notificación.
	 * @param controlador Controlador padre para refrescar la lista tras las acciones.
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
	 * Gestiona las acciones de marcar como leída o eliminar la notificación.
	 *
	 * @param e Evento de acción recibido.
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