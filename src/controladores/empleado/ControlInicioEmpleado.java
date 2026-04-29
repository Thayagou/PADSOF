package controladores.empleado;

import modelo.sistema.Tienda;
import vistas.BarraLateral;
import vistas.TiendaFrame;
import vistas.empleado.*;

public class ControlInicioEmpleado {

	private Tienda tienda;
	private VentanaInicioEmpleado vista;

	public ControlInicioEmpleado(Tienda tienda) {
		this.tienda = tienda;
		TiendaFrame tiendaFrame = TiendaFrame.getInstance();
		
		// Barra lateral
		ControlBarraEmpleado ctrlBarraLateral = new ControlBarraEmpleado(tienda);
		BarraLateral barraLateral = new BarraEmpleado();
		barraLateral.setControlador(ctrlBarraLateral);
		tiendaFrame.setBarraLateral(barraLateral);

		// Barra de tareas superior
		ControlBarraTareasEmpleado ctrlBarraTareas = new ControlBarraTareasEmpleado(tienda);
		BarraTareasEmpleado barraTareas = new BarraTareasEmpleado();
		barraTareas.setControlador(ctrlBarraTareas);
		tiendaFrame.setBarraTareas(barraTareas);
		
		this.vista = new VentanaInicioEmpleado(tienda);
		tiendaFrame.setVistaActual(this.vista);
	}
}
