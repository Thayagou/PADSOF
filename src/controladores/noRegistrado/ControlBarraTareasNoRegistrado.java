package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import controladores.ControlBarraTareas;
import modelo.sistema.Tienda;
import vistas.noRegistrado.BarraTareasNoRegistrado;

/**
 * Controlador de la barra de tareas superior cuando no hay sesión iniciada.
 * Gestiona "Buscar productos" e "Iniciar sesión".
 */
public class ControlBarraTareasNoRegistrado implements ControlBarraTareas {

	private final Tienda tienda;

	public ControlBarraTareasNoRegistrado(Tienda tienda) {
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
