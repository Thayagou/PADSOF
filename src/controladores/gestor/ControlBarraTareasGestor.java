package controladores.gestor;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraTareas;
import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;

public class ControlBarraTareasGestor implements ControlBarraTareas {

	private final Tienda tienda;
	private final Gestor gestor;
	
	public ControlBarraTareasGestor(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
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
