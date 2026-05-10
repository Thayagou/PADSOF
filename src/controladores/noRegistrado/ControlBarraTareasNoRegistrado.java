package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import modelo.sistema.Tienda;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.noRegistrado.BarraTareasNoRegistrado;

/**
 * Controlador de la barra de tareas superior cuando no hay sesión iniciada.
 * Gestiona "Buscar productos" e "Iniciar sesión".
 */
public class ControlBarraTareasNoRegistrado implements ActionListener {

	private final Tienda tienda;

	public ControlBarraTareasNoRegistrado(Tienda tienda) {
		this.tienda = tienda;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case BarraTareasNoRegistrado.BUSCAR -> SwingUtilities.invokeLater(() -> new ControlBuscar(tienda));
		case BarraTareasNoRegistrado.ATRAS -> TiendaFrame.getInstance().volverAtras();
		case BarraTareasNoRegistrado.INICIAR -> SwingUtilities.invokeLater(() -> new ControlLoginRegistro(tienda));
		case BarraTareasNoRegistrado.HOME -> SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
		case BarraTareasNoRegistrado.INFO -> new VentanaMensaje(TiendaFrame.getInstance().getInfo());
		}
	}
}
