package controladores.empleado;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraLateral;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;

public class ControlBarraEmpleado implements ControlBarraLateral {

	private final Tienda tienda;
	private final Empleado empleado;

	public ControlBarraEmpleado(Tienda tienda, Empleado empleado) {
		this.tienda = tienda;
		this.empleado = empleado;
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
			new ControlGestProductos(tienda, empleado);
		});
	}

	private void showGPedidos() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestPedidos(tienda, empleado);
		});
	}

	private void showValorar() {
		SwingUtilities.invokeLater(() -> {
			new ControlValorarObjetos(tienda, empleado);
		});
	}
	
	private void showGIntercambios() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestIntercambios(tienda);
		});
	}

}
