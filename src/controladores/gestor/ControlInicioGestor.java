package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import vistas.TiendaFrame;
import vistas.gestor.VentanaInicioGestor;

public class ControlInicioGestor implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	private VentanaInicioGestor vista;
	
	public ControlInicioGestor(Tienda tienda) {
		this.tienda = tienda;
		
		this.vista = new VentanaInicioGestor(tienda);
		this.vista.setControlador(this);
		
		this.frame = TiendaFrame.getInstance();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVistaActual(vista);
		this.frame.setVisible(true);
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
