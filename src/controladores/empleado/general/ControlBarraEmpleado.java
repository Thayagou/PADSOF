package controladores.empleado.general;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraLateral;
import controladores.empleado.gestionarIntercambios.ControlGestIntercambios;
import controladores.empleado.gestionarPedidos.ControlGestPedidos;
import controladores.empleado.gestionarProductos.anadirProductos.ControlAnadirProductos;
import controladores.empleado.gestionarProductos.gestionarCategorias.ControlGestionarCategorias;
import controladores.empleado.gestionarProductos.gestionarExistentes.ControlGestionarExistentes;
import controladores.empleado.valorarArticulos.ControlValorarObjetos;
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
		case "Añadir productos" -> showAnadirProducto();
		case "Gestionar productos existentes" -> showProductosExistentes();
		case "Gestionar categorías" -> showCategoriasExistentes();
		case "Gestionar pedidos" -> showGPedidos();
		case "Valorar artículos de segunda mano" -> showValorar();
		case "Gestionar intercambios" -> showGIntercambios();
		}
	}
	
	private void showAnadirProducto() {
		SwingUtilities.invokeLater(() -> {
			new ControlAnadirProductos(tienda, empleado);
		});
	}
	
	private void showProductosExistentes() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestionarExistentes(tienda, empleado);
		});
	}
	
	private void showCategoriasExistentes() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestionarCategorias(tienda, empleado);
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
			new ControlGestIntercambios(tienda, empleado);
		});
	}

}
