package controladores.empleado.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlBarraTareasEmpleado implements ActionListener {

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
		case "Cerrar sesión" -> intentarCerrarSesion();
		case "Info" -> new VentanaMensaje(TiendaFrame.getInstance().getInfo());
		}
	}
	
	private void intentarCerrarSesion() {
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas cerrar la sesión?")) {
			SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
		}
	}

}
