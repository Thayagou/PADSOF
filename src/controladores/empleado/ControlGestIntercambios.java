package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import vistas.common.TiendaFrame;
import vistas.empleado.VentanaGestIntercambios;

public class ControlGestIntercambios implements ActionListener{
	private Tienda tienda;
	private VentanaGestIntercambios vista;

	public ControlGestIntercambios(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaGestIntercambios();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
