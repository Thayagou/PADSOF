package controladores.empleado.general;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.common.app.TiendaFrame;
import vistas.empleado.general.BarraEmpleado;
import vistas.empleado.general.BarraTareasEmpleado;
import vistas.empleado.general.VentanaInicioEmpleado;

public class ControlInicioEmpleado implements ControladorPantalla {
	private VentanaInicioEmpleado vista;

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
}
