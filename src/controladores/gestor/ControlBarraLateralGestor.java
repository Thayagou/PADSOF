package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

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
import vistas.common.app.TiendaFrame;
import vistas.gestor.BarraGestor;

/**
 * Clase controladora de la barra de tareas lateral del gestor.
 */
public class ControlBarraLateralGestor implements ActionListener{
	
	/** Tienda sobre la que se actúa y muestran datos. */
	private final Tienda tienda;
	
	/** Gestor de la tienda sobre la que estamos actuando. */
	private final Gestor gestor;
	
	/** Vista de la barra lateral del gestor. */
	private BarraGestor barraLateral;
	
	/**
	 * Instancia un nuevo Controlador de la Barra lateral del cliente.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param gestor Gestor de la tienda sobre la que estamos actuando.
	 */
	public ControlBarraLateralGestor(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		
		barraLateral = new BarraGestor();
        barraLateral.setControlador(this);
        TiendaFrame.getInstance().setBarraLateral(barraLateral);
        
	}
	
	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la barra de tareas
	 * 
	 * Aquí se encuentran todas las posibles acciones del gestor.
	 *
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case BarraGestor.ANADIR_DESCUENTO_ACTION-> anadirDescuento();
		case BarraGestor.CONFIGURAR_SISTEMA_ACTION -> configurarSistema();
		
		case BarraGestor.ANADIR_PRODUCTO_ACTION -> anadirNuevoProducto();
		case BarraGestor.GESTIONAR_PRODUCTOS_ACTION -> gestionarProductos();
		case BarraGestor.GESTIONAR_CATEGORIAS_ACTION -> gestionarCategorias();
		
		case BarraGestor.GESTIONAR_EMPLEADOS_ACTION -> gestionarEmpleados();
		
		case BarraGestor.STATS_CLIENTES_ACTION -> consultarEstadisticasClientes();
		case BarraGestor.STATS_PRODUCTOS_ACTION -> consultarEstadisticasProductos();
		case BarraGestor.STATS_VENTAS_ACTION -> consultarEstadisticasVentas();
		case BarraGestor.STATS_INTERCAMBIO_ACTION -> consultarEstadisticasIntercambio();
			
			
		}
		
	}
	
	/**
	 * Método para ir a la ventana de añadir nuevo producto.
	 */
	private void anadirNuevoProducto() {
		SwingUtilities.invokeLater(() -> {
			new ControlAnadirProductos(tienda, gestor);
		});
	}

	/**
	 * Método para ir a la ventana de anadir un nuevo descuento.
	 */
	private void anadirDescuento() {
		SwingUtilities.invokeLater(()->
			new ControlAnadirDescuento(tienda, gestor)
		);
	}
	
	/**
	 * Método para ir a la ventana de configurar el sistema.
	 */
	private void configurarSistema() {
		SwingUtilities.invokeLater(()->
			new ControlConfigurarSistema(tienda, gestor)
		);
	}
	
	/**
	 * Método para ir a la ventana de gestionar productos.
	 */
	private void gestionarProductos() {
		SwingUtilities.invokeLater(()->
			new ControlGestionarExistentes(tienda, gestor)
		);
	}
	
	/**
	 * Método para ir a la ventana de gestionar categorias.
	 */
	private void gestionarCategorias() {
		SwingUtilities.invokeLater(()->
			new ControlGestionarCategorias(tienda, gestor)
		);
	}	
	
	
	/**
	 * Método para ir a la ventana de gestionar empleados.
	 */
	private void gestionarEmpleados() {
		SwingUtilities.invokeLater(()->
			new ControlGestionarEmpleados(tienda, gestor)
		);
	}
	
	/**
	 * Método para ir a la ventana de consultar estadisticas clientes.
	 */
	private void consultarEstadisticasClientes() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasClientes(tienda)
		);
	}
	
	/**
	 * Método para ir a la ventana de consultar estadisticas productos.
	 */
	private void consultarEstadisticasProductos() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasProductos(tienda)
		);
	}
	
	/**
	 * Método para ir a la ventana de consultar estadisticas ventas.
	 */
	private void consultarEstadisticasVentas() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasVentas(tienda)
		);
	}
	
	/**
	 * Método para ir a la ventana de consultar estadisticas de intercambio.
	 */
	private void consultarEstadisticasIntercambio() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasWallapop(tienda)
		);
	}
}
