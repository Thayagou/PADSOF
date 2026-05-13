package controladores.empleado.gestionarIntercambios;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Permiso;
import modelo.wallapop.Intercambio;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.gestionarIntercambios.VentanaGestIntercambios;

/**
 * Esta clase representa un controlador para la ventana de gestión de intercambios
 */
public class ControlGestIntercambios implements ControladorPantalla {
	/** Tienda sobre la que se basa la maqueta */
	private final Tienda tienda;
	
	/** Empleado que realiaz la acción */
	private final Empleado empleado;
	
	/** Ventana que se controla */
	private VentanaGestIntercambios vista;
	
	/** Permiso requerido para realizar esta acción */
	private static Permiso requerido = Permiso.INTERCAMBIOS;

	/**
	 * Constructor del controlador de la ventana de intercambios
	 * @param tienda Modelo de la tienda
	 * @param empleado Empleado que realiza la acción
	 */
	public ControlGestIntercambios(Tienda tienda, Empleado empleado) {
		this.tienda = tienda;
		this.empleado = empleado;
		if(!empleado.tienePermiso(requerido)) {
			new VentanaMensaje("No tiene el permiso para realizar esta acción", 1);
			return;
		}
		
		this.vista = new VentanaGestIntercambios();
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
	
	@Override
	public void mostrar() {
		this.vista.vaciar();
		Intercambio[] intercambios = tienda.getHistorial().getIntercambiosPendientes();
		for(Intercambio i : intercambios) {
			new ControlPanelIntercambioConBoton(tienda, empleado, i, vista, this);
		}
	}
}
