package controladores.gestor.gestionarEmpleados;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import controladores.TiendaFrame;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Permiso;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.VentanaConDisplay;
import vistas.gestor.gestionarEmpleados.PanelEmpleado;
import vistas.gestor.gestionarEmpleados.PanelNuevoEmpleado;

/**
 * Clase controladora del panel correspondiente a un empleado que estamos gestionando.
 */
public class ControlPanelEmpleadoGestionar implements ActionListener{
	
	/** Empleado que estamos gestionando */
	private Empleado empleado;
	
	/** Tienda sobre la que se actúa y muestran datos. */
	private Tienda tienda;
	
	/** Panel que se muestra por pantalla y del que se obtiene la información pertinente. */
	private PanelEmpleado panel;
	
	
	/**
	 * Instancia un nuevo Controlador del panel de empleados para permitir su gestión.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param empleado Empleado a gestionar
	 * @param vista Ventana a la que se añaden el panel creado
	 */
	public ControlPanelEmpleadoGestionar(Tienda tienda, Empleado empleado, VentanaConDisplay<? super PanelEmpleado> vista) {
		this.empleado = empleado;
		this.tienda = tienda;
		
		List<String> permisos = new ArrayList<>();
		for (Permiso p: empleado.getPermisos()) {
			switch (p) {
			case Permiso.INTERCAMBIOS -> permisos.add(PanelNuevoEmpleado.PERM_INTERCAMBIOS);
			case Permiso.PRODUCTOS -> permisos.add(PanelNuevoEmpleado.PERM_PRODUCTOS);
			case Permiso.PEDIDOS -> permisos.add(PanelNuevoEmpleado.PERM_PEDIDOS);
			}
		}
		
		panel = new PanelEmpleado(empleado.getNombre(), empleado.estaDeAlta(), permisos.toArray(new String[0]));
		
		vista.anadirDisplay(panel);
		
		panel.setControlador(this);
	}
	
	/**
	 * Método que maneja todas las posibles acciones realizadas con el panel que maneja el controlador
	 * 
	 * Permite modificar los permisos del empleado y seleccionar si está de alta o no.
	 * 
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelEmpleado.DE_ALTA_ACTION:
			
			boolean deAlta = empleado.estaDeAlta();
			String deAltaString = deAlta ? "dar de baja" : "dar de alta";
			
			if (!TiendaFrame.getConfirmacionUsuario("Estás seguro de que desea " + deAltaString + " a " + empleado.getNombre() + "?")) return;
			
			if (deAlta) tienda.darDeBajaEmpleado(empleado.getNombre());
			else tienda.darDeAltaEmpleado(empleado.getNombre());
			panel.setEstadoDeAlta(!deAlta);

			String deAltaStringParticipio = deAlta ? "dado de baja" : "dado de alta";
			new VentanaMensaje("Se ha " + deAltaStringParticipio + " a " + empleado.getNombre(), VentanaMensaje.INFO);
			
			panel.refreshDisplay();
			break;
		case PanelEmpleado.MODIFICAR_ACTION:
			panel.toggleExpand();
			break;
		case PanelEmpleado.CONFIRMAR_ACTION:
			List<String> listaPermisosString = panel.getPermisos();
			List<Permiso> listaPermisos = new ArrayList<>();
			
			if (listaPermisosString.contains(PanelNuevoEmpleado.PERM_PRODUCTOS)) listaPermisos.add(Permiso.PRODUCTOS);
			if (listaPermisosString.contains(PanelNuevoEmpleado.PERM_PEDIDOS)) listaPermisos.add(Permiso.PEDIDOS);
			if (listaPermisosString.contains(PanelNuevoEmpleado.PERM_INTERCAMBIOS)) listaPermisos.add(Permiso.INTERCAMBIOS);
			
			empleado.setPermisos(listaPermisos.toArray(new Permiso[0]));
			panel.setPermisos(listaPermisosString);
			
			new VentanaMensaje("Se han modificado los permisos correctamente", VentanaMensaje.INFO);
			
			panel.refreshDisplay();
			break;
		}
		
	}
	
	
}
