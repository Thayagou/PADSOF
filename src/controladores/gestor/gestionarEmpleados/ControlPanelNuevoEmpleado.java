package controladores.gestor.gestionarEmpleados;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import modelo.usuario.Permiso;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.gestor.gestionarEmpleados.PanelNuevoEmpleado;
import vistas.gestor.gestionarEmpleados.VentanaGestionarEmpleados;


public class ControlPanelNuevoEmpleado implements ActionListener {
	
	private final Tienda tienda;
	
	private final Gestor gestor;
	
	private final PanelNuevoEmpleado panel;


	public ControlPanelNuevoEmpleado(Tienda tienda, Gestor gestor, VentanaGestionarEmpleados vista) {
		this.tienda = tienda;
		this.gestor = gestor;
		
		panel = new PanelNuevoEmpleado();
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelNuevoEmpleado.CONFIRMAR_ACTION -> intentarCrear();
		case PanelNuevoEmpleado.CREAR_ACTION -> panel.toggleExpand();
		}
	}
	
	/**
	 * Método que intenta crear un empleado a partir de los datos introducidos en el panel creado
	 */
	private void intentarCrear() {
		String nombre = panel.getNombreEmpleado();
		if(nombre.equals(PanelNuevoEmpleado.DF_NOMBRE) || nombre.length() < 1) {
			new VentanaMensaje("Introduzca un nombre para el nuevo empleado", 1);
			return;
		}
		
		String contrasena = panel.getContrasenaEmpleado();
		if(contrasena.equals(PanelNuevoEmpleado.DF_CONTRASENA) || nombre.length() < 1) {
			new VentanaMensaje("Introduzca una contraseña para el nuevo empleado", 1);
			return;
		}
		
		List<String> listaPermisosString = panel.getPermisos();
		List<Permiso> listaPermisos = new ArrayList<>();
		if (listaPermisosString.contains(PanelNuevoEmpleado.PERM_PRODUCTOS)) listaPermisos.add(Permiso.PRODUCTOS);
		if (listaPermisosString.contains(PanelNuevoEmpleado.PERM_PEDIDOS)) listaPermisos.add(Permiso.PEDIDOS);
		if (listaPermisosString.contains(PanelNuevoEmpleado.PERM_INTERCAMBIOS)) listaPermisos.add(Permiso.INTERCAMBIOS);
		
		try {
			tienda.darDeAltaEmpleado(nombre, contrasena, listaPermisos.toArray(new Permiso[0]));
		} catch (InvalidArgumentException e) {
			new VentanaMensaje(e.getMessage());
		}
		
		new VentanaMensaje("El empleado se ha añadido correctamente");
		
		TiendaFrame.getInstance().recargarPantallaActual(new ControlGestionarEmpleados(tienda, gestor));
	}

}
