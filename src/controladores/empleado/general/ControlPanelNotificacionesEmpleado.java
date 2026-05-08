package controladores.empleado.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Notificacion;
import vistas.common.displays.PanelNotificacion;
import vistas.empleado.general.VentanaNotificacionesEmpleado;

public class ControlPanelNotificacionesEmpleado implements ActionListener {
	
	public ControlPanelNotificacionesEmpleado(Tienda tienda, Empleado empleado, Notificacion notificacion, VentanaNotificacionesEmpleado vista) {
		
		PanelNotificacion panel = new PanelNotificacion(notificacion.getTipo().name(), notificacion.getContenido(), notificacion.getFecha(), false, false);
		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
