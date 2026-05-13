package controladores.empleado.valorarArticulos;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Permiso;
import modelo.wallapop.Valoracion;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.valorarArticulos.VentanaValorarObjetos;

/**
 * Esta clase representa el controlador de la ventana para valorar artículos
 */
public class ControlValorarObjetos implements ControladorPantalla {
	/** Modelo de la tienda sobre el que se actúa */
	private final Tienda tienda;
	/** Empleado que realiza la acción */
	private final Empleado empleado;
	/** Ventana que se muestra */
	private VentanaValorarObjetos vista;
	/** Permiso requerido para realizar esta acción */
	private static Permiso requerido = Permiso.INTERCAMBIOS;

	/**
	 * Constructor del controlador de valorar artículos de segunda mano
	 * @param tienda Modelo de la tienda
	 * @param empleado Empleado que realiza la acción
	 */
	public ControlValorarObjetos(Tienda tienda, Empleado empleado) {
		this.tienda = tienda;
		this.empleado = empleado;
		if(!empleado.tienePermiso(requerido)) {
			new VentanaMensaje("No tiene el permiso para realizar esta acción", 1);
			return;
		}
		this.vista = new VentanaValorarObjetos();

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
		return "En esta ventana puedes valorar los artículos pendientes de valoración";
	}
	
	@Override
	public void mostrar() {
		this.vista.vaciar();
		for (Valoracion v : tienda.getHistorial().getValoracionesPendientes()) {
			new ControlPanelValorarObjetos(tienda, v.getArticulo(), empleado, vista);
		}
	}
}
