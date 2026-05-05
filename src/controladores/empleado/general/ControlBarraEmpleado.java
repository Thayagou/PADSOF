package controladores.empleado.general;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraLateral;
import controladores.empleado.gestionarIntercambios.ControlGestIntercambios;
import controladores.empleado.gestionarPedidos.ControlGestPedidos;
import controladores.empleado.gestionarProductos.cargarFichero.ControlCargarFichero;
import controladores.empleado.gestionarProductos.gestionarCategorias.ControlGestionarCategorias;
import controladores.empleado.gestionarProductos.gestionarExistentes.ControlGestionarExistentes;
import controladores.empleado.valorarArticulos.ControlValorarObjetos;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.empleado.general.BarraEmpleado;

public class ControlBarraEmpleado implements ControlBarraLateral {

	private final Tienda tienda;
	private final Empleado empleado;
	private final BarraEmpleado barra;

	public ControlBarraEmpleado(Tienda tienda, Empleado empleado, BarraEmpleado barra) {
		this.tienda = tienda;
		this.empleado = empleado;
		this.barra = barra;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Gestionar productos y categorías" -> showGProductos();
		case "Añadir nuevo producto" -> showAnadirProducto();
		case "Cargar fichero de productos" -> showCargarFichero();
		case "Gestionar productos existentes" -> showProductosExistentes();
		case "Gestionar categorías" -> showCategoriasExistentes();
		case "Crear packs de productos" -> showCrearPacks();
		case "Gestionar pedidos" -> showGPedidos();
		case "Valorar objetos de segunda mano" -> showValorar();
		case "Gestionar intercambios" -> showGIntercambios();
		}
	}

	private void showGProductos() {
		barra.setVisibleGestProductos();
	}
	
	private void showAnadirProducto() {
		
	}
	
	private void showCargarFichero() {
		SwingUtilities.invokeLater(() -> {
			new ControlCargarFichero(tienda, empleado);
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
	
	private void showCrearPacks() {
		
	}

	private void showGPedidos() {
		barra.setInvisibleGestProductos();
		SwingUtilities.invokeLater(() -> {
			new ControlGestPedidos(tienda, empleado);
		});
	}

	private void showValorar() {
		barra.setInvisibleGestProductos();
		SwingUtilities.invokeLater(() -> {
			new ControlValorarObjetos(tienda, empleado);
		});
	}
	
	private void showGIntercambios() {
		barra.setInvisibleGestProductos();
		SwingUtilities.invokeLater(() -> {
			new ControlGestIntercambios(tienda, empleado);
		});
	}

}
