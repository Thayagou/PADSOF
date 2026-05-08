package controladores.empleado.general;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraTareas;
import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.common.app.TiendaFrame;

public class ControlBarraTareasEmpleado implements ControlBarraTareas {

	private final Tienda tienda;
	private final Empleado empleado;
	
	public ControlBarraTareasEmpleado(Tienda tienda, Empleado empleado) {
		this.tienda = tienda;
		this.empleado = empleado;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Home" -> SwingUtilities.invokeLater(() -> new ControlInicioEmpleado(tienda, empleado));
		case "Volver" -> TiendaFrame.getInstance().volverAtras();
		case "Notificaciones" -> SwingUtilities.invokeLater(() -> new ControlNotificacionesEmpleado(tienda, empleado));
		case "Cerrar sesión" -> SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
		}
		
	}

}
