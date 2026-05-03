package controladores.gestor;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraLateral;
import controladores.empleado.ControlGestionarCategorias;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;

public class ControlBarraGestor implements ControlBarraLateral{
	private final Tienda tienda;
	private final Gestor gestor;
	
	public ControlBarraGestor(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
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
		case "Gestionar productos y categorías":
			this.gestionarProdsYCats();
			break;
		case "Gestionar empleados":
			this.gestionarEmpleados();
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
	
	private void gestionarProdsYCats() {
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
			new ControlConsultarEstadisticas(tienda, gestor)
		);
	}
}
