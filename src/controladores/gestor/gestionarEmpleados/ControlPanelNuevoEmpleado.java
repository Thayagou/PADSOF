package controladores.gestor.gestionarEmpleados;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.empleado.gestionarProductos.gestionarCategorias.ControlGestionarCategorias;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import modelo.usuario.Permiso;
import modelo.usuario.Usuario;
import vistas.common.InvisibleCheckBox;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;
import vistas.empleado.gestionarProductos.gestionarCategorias.PanelCrearCategoria;
import vistas.empleado.gestionarProductos.gestionarCategorias.VentanaGestionarCategorias;
import vistas.gestor.gestionarEmpleados.PanelNuevoEmpleado;
import vistas.gestor.gestionarEmpleados.VentanaGestionarEmpleados;

public class ControlPanelNuevoEmpleado implements ActionListener {
	private final Tienda tienda;
	private final Gestor gestor;
	private final PanelNuevoEmpleado panel;
	private List<InvisibleCheckBox> checkPermisos = new ArrayList<>();
	
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
	
	private void intentarCrear() {
		String nombre = panel.getNombreEmpleado();
		if(nombre.equals(PanelNuevoEmpleado.DF_NOMBRE) || nombre.length() < 1) {
			new VentanaMensaje("Introduzca un nombre para el nuevo empleado");
			return;
		}
		
		String contrasena = panel.getContrasenaEmpleado();
		if(contrasena.equals(PanelNuevoEmpleado.DF_CONTRASENA) || nombre.length() < 1) {
			new VentanaMensaje("Introduzca una contraseña para el nuevo empleado");
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
