package controladores.gestor.gestionarEmpleados;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import vistas.common.VentanaConDisplay;
import vistas.gestor.gestionarEmpleados.PanelEmpleado;

public class ControlPanelEmpleadoGestionar implements ActionListener{
	private Empleado empleado;
	private Tienda tienda;
	private PanelEmpleado panel;
	private VentanaConDisplay<? super PanelEmpleado> vista;
	
	public ControlPanelEmpleadoGestionar(Tienda tienda, Empleado empleado, VentanaConDisplay<? super PanelEmpleado> vista) {
		this.tienda = tienda;
		this.empleado = empleado;
		this.vista = vista;
		String[] permisos = empleado.getPermisos().stream().map(p->p.name()).toArray(String[]::new);
		
		panel = new PanelEmpleado(empleado.getNombre(), empleado.estaDeAlta(), permisos);
		
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
			break;
			
		default:
			throw new IllegalArgumentException("Unexpected value: " + e.getActionCommand());
		}
		
	}
	
	
}
