package controladores.empleado.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Notificacion;
import vistas.common.displays.PanelNotificacion;
import vistas.empleado.general.VentanaNotificacionesEmpleado;

/**
 * Esta clase representa el controlador de un panel de notificaciones de empleado
 */
public class ControlPanelNotificacionesEmpleado implements ActionListener {
	
	/**
	 * Constructor del controlador del panel de notificación de empleado
	 * @param tienda Modelo de la tienda
	 * @param empleado Empleado que realiza la acción
	 * @param notificacion Notificacion que se muestra
	 * @param vista Ventana sobre la que se muestra el panel
	 */
	public ControlPanelNotificacionesEmpleado(Tienda tienda, Empleado empleado, Notificacion notificacion, VentanaNotificacionesEmpleado vista) {
		PanelNotificacion panel = new PanelNotificacion(notificacion.getTipo().name(), notificacion.getContenido(), notificacion.getFecha(), false, false);
		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
