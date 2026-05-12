package controladores.empleado.general;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Notificacion;
import vistas.common.app.TiendaFrame;
import vistas.empleado.general.VentanaNotificacionesEmpleado;

/**
 * Esta clase representa el cotrolador de la ventana de notificaciones de empleado
 */
public class ControlNotificacionesEmpleado implements ControladorPantalla {
	/** Ventana que se muestra */
	private final VentanaNotificacionesEmpleado vista;

	/**
	 * Constructor del controlador de la ventana de notificaciones
	 * @param tienda Modelo de tienda
	 * @param empleado Empleado que ha iniciado sesión
	 */
	public ControlNotificacionesEmpleado(Tienda tienda, Empleado empleado) {
		this.vista = new VentanaNotificacionesEmpleado();
		
		Notificacion[] notificaciones = empleado.getNotificaciones().toArray(new Notificacion[0]);
		for(int i = notificaciones.length - 1; i >= 0; i--) {
			new ControlPanelNotificacionesEmpleado(tienda, empleado, notificaciones[i], vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "Aquí puedes ver todas las notificaciones sobre posibles tareas pendientes";
	}
}
