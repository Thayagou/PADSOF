package controladores.empleado;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraTareas;
import controladores.noRegistrado.ControlBuscar;
import controladores.noRegistrado.ControlLoginRegistro;
import modelo.sistema.Tienda;

public class ControlBarraTareasEmpleado implements ControlBarraTareas {

	private final Tienda tienda;
	
	public ControlBarraTareasEmpleado(Tienda tienda) {
		this.tienda = tienda;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Buscar productos" ->
			SwingUtilities.invokeLater(() -> new ControlBuscar(tienda));
		case "Iniciar sesión" -> SwingUtilities.invokeLater(() -> new ControlLoginRegistro(tienda));
		}
		
	}

}
