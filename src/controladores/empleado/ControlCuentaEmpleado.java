package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.common.TiendaFrame;
import vistas.empleado.VentanaCuentaEmpleado;

public class ControlCuentaEmpleado implements ActionListener{
	private Tienda tienda;
	private Empleado empleado;
	private VentanaCuentaEmpleado vista;

	public ControlCuentaEmpleado(Tienda tienda, Empleado empleado) {
		this.tienda = tienda;
		this.empleado = empleado;
		
		this.vista = new VentanaCuentaEmpleado();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
