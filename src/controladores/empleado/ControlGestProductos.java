package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import vistas.common.TiendaFrame;
import vistas.empleado.VentanaGestProductos;

public class ControlGestProductos implements ActionListener{
	
	private Tienda tienda;
	private VentanaGestProductos vista;

	public ControlGestProductos(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaGestProductos();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
