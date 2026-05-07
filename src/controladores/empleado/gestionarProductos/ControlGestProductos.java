package controladores.empleado.gestionarProductos;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.common.app.TiendaFrame;
import vistas.empleado.gestionarProductos.VentanaGestProductos;

public class ControlGestProductos implements ControladorPantalla {
	private VentanaGestProductos vista;

	public ControlGestProductos(Tienda tienda, Empleado empleado) {
		this.vista = new VentanaGestProductos();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}
