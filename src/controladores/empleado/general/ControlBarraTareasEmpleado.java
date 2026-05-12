package controladores.empleado.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

/**
 * Esta clase representa la barra de tareas (superior) de un empleado
 */
public class ControlBarraTareasEmpleado implements ActionListener {

	/** Modelo de la tienda sobre la que se actúa */
	private final Tienda tienda;
	/** Empleado que realiza las acciones */
	private final Empleado empleado;
	
	/**
	 * Constructor de controlador de la barra de tareas
	 * @param tienda Modelo de la tienda
	 * @param empleado Empleado que realiza las acciones
	 */
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
		case "Cerrar sesión" -> intentarCerrarSesion();
		case "Info" -> new VentanaMensaje(TiendaFrame.getInstance().getInfo());
		}
	}
	
	/**
	 * Acción que se realiza al pulsar el botón Cerrar sesión
	 */
	private void intentarCerrarSesion() {
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas cerrar la sesión?")) {
			SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
		}
	}

}
