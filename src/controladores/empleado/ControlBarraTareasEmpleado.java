package controladores.empleado;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraTareas;
import controladores.noRegistrado.ControlInicioSinRegistrar;
import controladores.empleado.ControlInicioEmpleado;
import modelo.sistema.Tienda;

public class ControlBarraTareasEmpleado implements ControlBarraTareas {

	private final Tienda tienda;
	
	public ControlBarraTareasEmpleado(Tienda tienda) {
		this.tienda = tienda;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Cerrar sesión" -> SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
		case "Home" -> SwingUtilities.invokeLater(() -> new ControlInicioEmpleado(tienda));
		}
		
	}

}
