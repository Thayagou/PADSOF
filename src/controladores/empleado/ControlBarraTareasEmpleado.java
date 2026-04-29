package controladores.empleado;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraTareas;
import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;

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
		case "Cerrar sesión" -> SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
		case "Home" -> SwingUtilities.invokeLater(() -> new ControlInicioEmpleado(tienda, empleado));
		}
		
	}

}
