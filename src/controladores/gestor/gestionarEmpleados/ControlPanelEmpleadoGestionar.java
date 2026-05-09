package controladores.gestor.gestionarEmpleados;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Permiso;
import vistas.common.displays.VentanaConDisplay;
import vistas.gestor.gestionarEmpleados.PanelEmpleado;
import vistas.gestor.gestionarEmpleados.PanelNuevoEmpleado;

// TODO: Auto-generated Javadoc
/**
 * Clase controladora de la vista correspondiente a.
 */
public class ControlPanelEmpleadoGestionar implements ActionListener{
	
	/** Campo empleado. */
	private Empleado empleado;
	
	/** Tienda sobre la que se actúa y muestran datos. */
	private Tienda tienda;
	
	/** Panel que se muestra por pantalla y del que se obtiene la información pertinente. */
	private PanelEmpleado panel;
	
	
	/**
	 * Instancia un nuevo Controlador del panel de empleados para permitir su gestión.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param empleado parámetro empleado
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
	 * .Estas acciones pueden ser dar de alta/baja a un empleado, modificar sus permisos y confirmar los cambios
	 *
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelEmpleado.DE_ALTA_ACTION:
			
			boolean deAlta = empleado.estaDeAlta();
			if (deAlta) tienda.darDeBajaEmpleado(empleado.getNombre());
			else tienda.darDeAltaEmpleado(empleado.getNombre());
			panel.setEstadoDeAlta(!deAlta);
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
			panel.refreshDisplay();
			break;
		}
		
	}
	
	
}
