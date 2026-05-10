package controladores.empleado.general;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Notificacion;
import vistas.common.app.TiendaFrame;
import vistas.empleado.general.VentanaNotificacionesEmpleado;

public class ControlNotificacionesEmpleado implements ControladorPantalla {
	private final VentanaNotificacionesEmpleado vista;

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
