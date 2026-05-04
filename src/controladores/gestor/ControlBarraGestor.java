package controladores.gestor;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraLateral;
import controladores.empleado.gestionarProductos.ControlGestionarCategorias;
import controladores.empleado.gestionarProductos.ControlGestionarProductos;
import controladores.gestor.anadirDescuento.ControlAnadirDescuento;
import controladores.gestor.configurarSistema.ControlConfigurarSistema;
import controladores.gestor.consultarEstadisticas.ControlEstadisticasClientes;
import controladores.gestor.consultarEstadisticas.ControlEstadisticasProductos;
import controladores.gestor.consultarEstadisticas.ControlEstadisticasVentas;
import controladores.gestor.gestionarEmpleados.ControlGestionarEmpleados;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.BarraGestor;

public class ControlBarraGestor implements ControlBarraLateral{
	private final Tienda tienda;
	private final Gestor gestor;
	private TiendaFrame frame;
	private BarraGestor barraLateral;
	
	public ControlBarraGestor(Tienda tienda, Gestor gestor) {
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
		case "Añadir nuevo descuento":
			this.anadirDescuento();
			break;
		case "Configurar sistema":
			this.configurarSistema();
			break;
		case "Consultar estadísticas":
			this.consultarEstadisticas();
			break;
		case "Gestionar productos":
			this.gestionarProductos();
			break;
		case "Gestionar categorías":
			this.gestionarCategorias();
			break;
		case "Gestionar empleados":
			this.gestionarEmpleados();
			break;	
		case "Consultar estadísticas productos":
			this.consultarEstadisticasProductos();
			break;
		case "Consultar estadísticas tienda":
			this.consultarEstadisticasTienda();
			break;
			
		}
		
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
	
	private void gestionarProductos() {
		SwingUtilities.invokeLater(()->
			new ControlGestionarProductos(tienda, gestor)
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
	
	private void consultarEstadisticas() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasClientes(tienda, gestor)
		);
	}
	
	private void consultarEstadisticasProductos() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasProductos(tienda, gestor)
		);
	}
	
	private void consultarEstadisticasTienda() {
		SwingUtilities.invokeLater(()->
			new ControlEstadisticasVentas(tienda, gestor)
		);
	}
}
