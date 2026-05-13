package controladores.empleado.general;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.empleado.general.BarraEmpleado;
import vistas.empleado.general.BarraTareasEmpleado;
import vistas.empleado.general.VentanaInicioEmpleado;

/**
 * Esta clase representa el controlador de la ventana de inicio de un empleado
 */
public class ControlInicioEmpleado implements ControladorPantalla {
	/** Ventana que se muestra */
	private VentanaInicioEmpleado vista;

	/**
	 * Constructor del controlador de la ventana de inicio de empleado
	 * @param tienda Modelo de la tienda sobre el que se actúa
	 * @param empleado Empleado que ha iniciado sesión
	 */
	public ControlInicioEmpleado(Tienda tienda, Empleado empleado) {
		// Barra lateral
		BarraEmpleado barraLateral = new BarraEmpleado();
		ControlBarraEmpleado ctrlBarraLateral = new ControlBarraEmpleado(tienda, empleado);
		barraLateral.setControlador(ctrlBarraLateral);
		TiendaFrame.getInstance().setBarraLateral(barraLateral);

		// Barra de tareas superior
		ControlBarraTareasEmpleado ctrlBarraTareas = new ControlBarraTareasEmpleado(tienda, empleado);
		BarraTareasEmpleado barraTareas = new BarraTareasEmpleado();
		barraTareas.setControlador(ctrlBarraTareas);
		TiendaFrame.getInstance().setBarraTareas(barraTareas);
		
		this.vista = new VentanaInicioEmpleado(tienda);
		TiendaFrame.getInstance().resetearNavegacion(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "Este es el menú principal para empleados, seleccione una tarea para continuar";
	}
}
