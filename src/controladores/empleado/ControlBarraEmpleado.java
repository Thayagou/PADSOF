package controladores.empleado;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraLateral;
import modelo.sistema.Tienda;

public class ControlBarraEmpleado implements ControlBarraLateral {

	private Tienda tienda;

	public ControlBarraEmpleado(Tienda tienda) {
		this.tienda = tienda;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Gestionar productos y categorías" -> showGProductos();
		case "Gestionar pedidos" -> showGPedidos();
		case "Valorar objetos de segunda mano" -> showValorar();
		case "Gestionar intercambios" -> showGIntercambios();
		}
	}

	private void showGProductos() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestProductos(tienda);
		});
	}

	private void showGPedidos() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestPedidos(tienda);
		});
	}

	private void showValorar() {
		SwingUtilities.invokeLater(() -> {
			new ControlValorarObjetos(tienda);
		});
	}
	
	private void showGIntercambios() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestIntercambios(tienda);
		});
	}

}
