package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controladores.cliente.ControlBarraTareasCliente;
import controladores.noRegistrado.ControlBarraNoRegistrado;
import modelo.sistema.Tienda;
import vistas.BarraLateral;
import vistas.BarraTareasCliente;
import vistas.TiendaFrame;
import vistas.gestor.BarraGestor;
import vistas.gestor.VentanaInicioGestor;
import vistas.noRegistrado.BarraNoRegistrado;

public class ControlInicioGestor implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	private VentanaInicioGestor vista;
	
	public ControlInicioGestor(Tienda tienda) {
		this.tienda = tienda;
		
		this.vista = new VentanaInicioGestor(tienda);
		this.vista.setControlador(this);
		
		this.frame = TiendaFrame.getInstance();
		this.frame.setVistaActual(vista);
		this.frame.setVisible(true);
		
		ControlBarraNoRegistrado ctrlBarraLateral = new ControlBarraNoRegistrado(tienda);
        BarraLateral barraLatera = new BarraGestor();
        barraLatera.setControlador(ctrlBarraLateral);
        frame.setBarraLateral(barraLatera);
        
        ControlBarraTareasCliente ctrlBarraTareas = new ControlBarraTareasCliente();
        BarraTareasCliente barraTareas = new BarraTareasCliente();
        barraTareas.setControlador(ctrlBarraTareas);
        frame.setBarraTareas(barraTareas);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Añadir descuento":
			this.anadirDescuento();
			break;
		case "Configurar sistema":
			this.configurarSistema();
			break;
		case "Consultar estadísticas":
			this.consultarEstadisticas();
			break;
		case "Gestionar productos y categorías":
			this.gestionarProdsYCats();
			break;
		case "Gestionar empleados":
			this.gestionarEmpleados();
			break;		
		}
		
	}
	
	private void anadirDescuento() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(()->
			new ControlAnadirDescuento(tienda)
		);
	}
	
	private void configurarSistema() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(()->
			new ControlConfigurarSistema(tienda)
		);
	}
	
	private void gestionarProdsYCats() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(()->
			new ControlGestionarProductosYCategorias(tienda)
		);
	}
	
	private void gestionarEmpleados() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(()->
			new ControlGestionarEmpleados(tienda)
		);
	}
	
	private void consultarEstadisticas() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(()->
			new ControlConsultarEstadisticas(tienda)
		);
	}

}
