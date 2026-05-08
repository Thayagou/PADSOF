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
 * Tipo: Class ControlPanelEmpleadoGestionar.
 */
public class ControlPanelEmpleadoGestionar implements ActionListener{
	
	/** Campo empleado. */
	private Empleado empleado;
<<<<<<< HEAD
	
	/** Campo tienda. */
	private Tienda tienda;
	
	/** Campo panel. */
=======
>>>>>>> branch 'main' of https://github.com/Thayagou/PADSOF.git
	private PanelEmpleado panel;
<<<<<<< HEAD
	
	/** Campo vista. */
	private VentanaConDisplay<? super PanelEmpleado> vista;
=======
>>>>>>> branch 'main' of https://github.com/Thayagou/PADSOF.git
	
	/**
	 * Instancia un nuevo Objeto ControlPanelEmpleadoGestionar.
	 *
	 * @param tienda parámetro tienda
	 * @param empleado parámetro empleado
	 * @param vista parámetro vista
	 */
	public ControlPanelEmpleadoGestionar(Tienda tienda, Empleado empleado, VentanaConDisplay<? super PanelEmpleado> vista) {
		this.empleado = empleado;
		
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
	 * actionPerformed.
	 *
	 * @param e parámetro e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelEmpleado.DE_ALTA_ACTION:
			boolean deAlta = empleado.estaDeAlta();
			if (deAlta) empleado.darDeBaja();
			else empleado.darDeAlta();
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
