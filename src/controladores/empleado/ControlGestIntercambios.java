package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.wallapop.Intercambio;
import vistas.common.TiendaFrame;
import vistas.empleado.VentanaGestIntercambios;

public class ControlGestIntercambios implements ActionListener{
	private Tienda tienda;
	private VentanaGestIntercambios vista;

	public ControlGestIntercambios(Tienda tienda) {
		this.tienda = tienda;
		
		// Coger intercambios
		Intercambio[] intercambios = tienda.getHistorial().getIntercambiosPendientes();
		for(Intercambio i : intercambios) {
			
		}
		
		this.vista = new VentanaGestIntercambios();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
