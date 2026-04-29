package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import vistas.common.TiendaFrame;
import vistas.empleado.VentanaGestPedidos;

public class ControlGestPedidos implements ActionListener{

	private Tienda tienda;
	private VentanaGestPedidos vista;

	public ControlGestPedidos(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaGestPedidos();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
