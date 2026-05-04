package controladores.empleado.gestionarProductos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.common.TiendaFrame;
import vistas.empleado.gestionarProductos.VentanaGestProductos;

public class ControlGestProductos implements ActionListener{
	
	private final Tienda tienda;
	private final Empleado empleado;
	private VentanaGestProductos vista;

	public ControlGestProductos(Tienda tienda, Empleado empleado) {
		this.tienda = tienda;
		this.empleado = empleado;
		this.vista = new VentanaGestProductos();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
