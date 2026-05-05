package controladores.gestor;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.empleado.gestionarProductos.gestionarExistentes.ControlGestionarExistentes;
import controladores.gestor.anadirDescuento.ControlAnadirDescuento;
import controladores.gestor.configurarSistema.ControlConfigurarSistema;
import controladores.gestor.consultarEstadisticas.ControlConsultarEstadisticas;
import controladores.gestor.gestionarEmpleados.ControlGestionarEmpleados;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.VentanaInicioGestor;

public class ControlInicioGestor implements ControladorPantalla {
	private Tienda tienda;
	private Gestor gestor;
	private TiendaFrame frame;
	private VentanaInicioGestor vista;

	public ControlInicioGestor(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;

		this.vista = new VentanaInicioGestor(tienda);
		this.vista.setControlador(this);

		/* Se crean las barras que se autogestionan y añaden al frame */
		new ControlBarraLateralGestor(tienda, gestor);
		new ControlBarraTareasGestor(tienda, gestor);
		
		this.frame = TiendaFrame.getInstance();
		frame.resetearNavegacion(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
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
		SwingUtilities.invokeLater(() -> new ControlAnadirDescuento(tienda, gestor));
	}

	private void configurarSistema() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(() -> new ControlConfigurarSistema(tienda, gestor));
	}

	private void gestionarProdsYCats() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(() -> new ControlGestionarExistentes(tienda, gestor)
		// new ControlGestionarCategorias(tienda, gestor)
		);
	}

	private void gestionarEmpleados() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(() -> new ControlGestionarEmpleados(tienda, gestor));
	}

	private void consultarEstadisticas() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(() -> new ControlConsultarEstadisticas(tienda, gestor));
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
