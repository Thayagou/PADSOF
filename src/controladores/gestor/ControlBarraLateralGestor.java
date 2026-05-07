package controladores.gestor;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraLateral;
import controladores.empleado.gestionarProductos.anadirProductos.ControlAnadirProductos;
import controladores.empleado.gestionarProductos.gestionarCategorias.ControlGestionarCategorias;
import controladores.empleado.gestionarProductos.gestionarExistentes.ControlGestionarExistentes;
import controladores.gestor.anadirDescuento.ControlAnadirDescuento;
import controladores.gestor.configurarSistema.ControlConfigurarSistema;
import controladores.gestor.consultarEstadisticas.ControlEstadisticasClientes;
import controladores.gestor.consultarEstadisticas.ControlEstadisticasProductos;
import controladores.gestor.consultarEstadisticas.ControlEstadisticasVentas;
import controladores.gestor.consultarEstadisticas.ControlEstadisticasWallapop;
import controladores.gestor.gestionarEmpleados.ControlGestionarEmpleados;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.BarraGestor;

public class ControlBarraLateralGestor implements ControlBarraLateral{
	private final Tienda tienda;
	private final Gestor gestor;
	private TiendaFrame frame;
	private BarraGestor barraLateral;
	
	public ControlBarraLateralGestor(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		this.frame = TiendaFrame.getInstance();
		
		barraLateral = new BarraGestor();
        barraLateral.setControlador(this);
        frame.setBarraLateral(barraLateral);
        
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Añadir nuevo descuento"-> anadirDescuento();
		case "Configurar sistema" -> configurarSistema();
		
		//case "Gestionar productos y categorías" -> mostrarGestion();
		//case "Añadir nuevo producto" -> anadirNuevoProducto();
		case "Añadir nuevo producto" -> anadirNuevoProducto();
		case "Gestionar productos existentes" -> gestionarProductos();
		case "Gestionar categorías" -> gestionarCategorias();
		//case "Crear packs de productos" -> crearNuevoPack();
		
		case "Gestionar empleados" -> gestionarEmpleados();
		
		//case "Consultar estadísticas" -> consultarEstadisticas();
		case "Estadísticas clientes" -> consultarEstadisticasClientes();
		case "Estadísticas productos" -> consultarEstadisticasProductos();
		case "Estadísticas ventas" -> consultarEstadisticasVentas();
		case "Estadísticas intercambios" -> consultarEstadisticasWallapop();
			
			
		}
		
	}
	
	private void anadirNuevoProducto() {
		SwingUtilities.invokeLater(() -> {
			new ControlAnadirProductos(tienda, gestor);
		});
	}

	private void anadirDescuento() {
		SwingUtilities.invokeLater(()->
			new ControlAnadirDescuento(tienda, gestor)
		);
	}
	
	private void configurarSistema() {
		SwingUtilities.invokeLater(()->
			new ControlConfigurarSistema(tienda, gestor)
		);
	}
	
	/*private void mostrarGestion() {
		barraLateral.setVisiblesGestionarProdsYCats();
	}*/
	
	private void gestionarProductos() {
		SwingUtilities.invokeLater(()->
			new ControlGestionarExistentes(tienda, gestor)
		);
	}
	
	private void gestionarCategorias() {
		SwingUtilities.invokeLater(()->
			new ControlGestionarCategorias(tienda, gestor)
		);
	}	
	
	
	private void gestionarEmpleados() {
		SwingUtilities.invokeLater(()->
			new ControlGestionarEmpleados(tienda, gestor)
		);
	}
	
	/*private void consultarEstadisticas() {
		barraLateral.setVisiblesEstadisticas();
	}*/
	
	private void consultarEstadisticasClientes() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasClientes(tienda, gestor)
		);
	}
	
	private void consultarEstadisticasProductos() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasProductos(tienda, gestor)
		);
	}
	
	private void consultarEstadisticasVentas() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasVentas(tienda, gestor)
		);
	}
	
	private void consultarEstadisticasWallapop() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasWallapop(tienda, gestor)
		);
	}
}
