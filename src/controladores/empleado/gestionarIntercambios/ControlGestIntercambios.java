package controladores.empleado.gestionarIntercambios;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.wallapop.Intercambio;
import vistas.common.app.TiendaFrame;
import vistas.empleado.gestionarIntercambios.VentanaGestIntercambios;

/**
 * Esta clase representa un controlador para la ventana de gestión de intercambios
 */
public class ControlGestIntercambios implements ControladorPantalla {
	/** Ventana que se controla */
	private VentanaGestIntercambios vista;

	/**
	 * Constructor del controlador de la ventana de intercambios
	 * @param tienda Modelo de la tienda
	 * @param empleado Empleado que realiza la acción
	 */
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

	@Override
	public String getExplicacion() {
		return "En esta ventana puedes confirmar los intercambios que hayan sido realizados";
	}
}
