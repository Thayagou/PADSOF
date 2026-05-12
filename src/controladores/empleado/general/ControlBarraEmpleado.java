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

/**
 * Esta clase representa el controlador de la barra lateral de empleados
 */
public class ControlBarraEmpleado implements ActionListener {

	/** Modelo de la tienda sobre el que se actúa */
	private final Tienda tienda;
	/** Empleado que realiza la acción */
	private final Empleado empleado;

	/**
	 * Cosntructor del controlador de barra empleado
	 * @param tienda Modelo de la tienda
	 * @param empleado Empleado que realiza acciones
	 */
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
	
	/**
	 * Acción al pulsar el botón añadir producto
	 */
	private void showAnadirProducto() {
		SwingUtilities.invokeLater(() -> {
			new ControlAnadirProductos(tienda, empleado);
		});
	}
	
	/**
	 * Acción al pulsar el botón gestionar productos
	 */
	private void showProductosExistentes() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestionarExistentes(tienda, empleado);
		});
	}
	
	/**
	 * Acción al pulsar el botón gestionar categorías
	 */
	private void showCategoriasExistentes() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestionarCategorias(tienda, empleado);
		});
	}

	/**
	 * Acción al pulsar el botón gestionar pedidos
	 */
	private void showGPedidos() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestPedidos(tienda, empleado);
		});
	}

	/**
	 * Acción al pulsar el botón añadir producto
	 */
	private void showValorar() {
		SwingUtilities.invokeLater(() -> {
			new ControlValorarObjetos(tienda, empleado);
		});
	}
	
	/**
	 * Acción al pulsar el botón gestionar intercambios
	 */
	private void showGIntercambios() {
		SwingUtilities.invokeLater(() -> {
			new ControlGestIntercambios(tienda, empleado);
		});
	}

}
