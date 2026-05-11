package controladores.empleado.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.empleado.gestionarIntercambios.ControlGestIntercambios;
import controladores.empleado.gestionarPedidos.ControlGestPedidos;
import controladores.empleado.gestionarProductos.anadirProductos.ControlAnadirProductos;
import controladores.empleado.gestionarProductos.gestionarCategorias.ControlGestionarCategorias;
import controladores.empleado.gestionarProductos.gestionarExistentes.ControlGestionarExistentes;
import controladores.empleado.valorarArticulos.ControlValorarObjetos;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.empleado.general.BarraEmpleado;

public class ControlBarraEmpleado implements ActionListener {

	private final Tienda tienda;
	private final Empleado empleado;

	public ControlBarraEmpleado(Tienda tienda, Empleado empleado) {
		this.tienda = tienda;
		this.empleado = empleado;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case BarraEmpleado.ANADIR_ACTION -> showAnadirProducto();
		case BarraEmpleado.GEST_PRODUCTOS_ACTION -> showProductosExistentes();
		case BarraEmpleado.GEST_CAT_ACTION -> showCategoriasExistentes();
		case BarraEmpleado.GEST_PEDIDOS_ACTION -> showGPedidos();
		case BarraEmpleado.VALORAR_ACTION -> showValorar();
		case BarraEmpleado.GEST_INT_ACTION -> showGIntercambios();
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
