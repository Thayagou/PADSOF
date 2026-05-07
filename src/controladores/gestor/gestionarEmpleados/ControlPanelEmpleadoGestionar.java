package controladores.gestor.gestionarEmpleados;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Permiso;
import vistas.common.VentanaConDisplay;
import vistas.gestor.gestionarEmpleados.PanelEmpleado;
import vistas.gestor.gestionarEmpleados.PanelNuevoEmpleado;

public class ControlPanelEmpleadoGestionar implements ActionListener{
	private Empleado empleado;
	private Tienda tienda;
	private PanelEmpleado panel;
	private VentanaConDisplay<? super PanelEmpleado> vista;
	
	public ControlPanelEmpleadoGestionar(Tienda tienda, Empleado empleado, VentanaConDisplay<? super PanelEmpleado> vista) {
		this.tienda = tienda;
		this.empleado = empleado;
		this.vista = vista;
		
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
			panel.refreshDisplay();
			break;
		}
		
	}
	
	
}
