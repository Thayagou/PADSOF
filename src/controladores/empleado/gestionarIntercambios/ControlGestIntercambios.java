package controladores.empleado.gestionarIntercambios;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.wallapop.Intercambio;
import vistas.common.TiendaFrame;
import vistas.empleado.gestionarIntercambios.VentanaGestIntercambios;

public class ControlGestIntercambios implements ControladorPantalla {
	private VentanaGestIntercambios vista;

	public ControlGestIntercambios(Tienda tienda, Empleado empleado) {
		this.vista = new VentanaGestIntercambios();
		Intercambio[] intercambios = tienda.getHistorial().getIntercambiosPendientes();
		for(Intercambio i : intercambios) {
			new ControlPanelIntercambioConBoton(tienda, empleado, i, vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}
