package controladores.gestor;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraTareas;
import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.BarraTareasGestor;

public class ControlBarraTareasGestor implements ControlBarraTareas {

	private final Tienda tienda;
	private final Gestor gestor;
	private TiendaFrame frame;
	private BarraTareasGestor barraTareas;
	
	public ControlBarraTareasGestor(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		this.frame = TiendaFrame.getInstance();
		
		barraTareas = new BarraTareasGestor();
		barraTareas.setControlador(this);
		frame.setBarraTareas(barraTareas);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Home" -> SwingUtilities.invokeLater(() -> new ControlInicioGestor(tienda, gestor));
		case "Cuenta" -> SwingUtilities.invokeLater(() -> new ControlCuentaGestor(tienda, gestor));
		case "Cerrar sesión" -> SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
		}
		
	}

}
