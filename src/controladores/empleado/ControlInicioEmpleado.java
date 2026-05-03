package controladores.empleado;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.common.BarraLateral;
import vistas.common.TiendaFrame;
import vistas.empleado.*;

public class ControlInicioEmpleado {
	private VentanaInicioEmpleado vista;

	public ControlInicioEmpleado(Tienda tienda, Empleado empleado) {
		TiendaFrame tiendaFrame = TiendaFrame.getInstance();
		
		// Barra lateral
		BarraEmpleado barraLateral = new BarraEmpleado();
		ControlBarraEmpleado ctrlBarraLateral = new ControlBarraEmpleado(tienda, empleado, barraLateral);
		barraLateral.setControlador(ctrlBarraLateral);
		tiendaFrame.setBarraLateral(barraLateral);

		// Barra de tareas superior
		ControlBarraTareasEmpleado ctrlBarraTareas = new ControlBarraTareasEmpleado(tienda, empleado);
		BarraTareasEmpleado barraTareas = new BarraTareasEmpleado();
		barraTareas.setControlador(ctrlBarraTareas);
		tiendaFrame.setBarraTareas(barraTareas);
		
		this.vista = new VentanaInicioEmpleado(tienda);
		tiendaFrame.setVistaActual(this.vista);
	}
}
