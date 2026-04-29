package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.exceptions.CustomException;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import vistas.*;
import vistas.cliente.VentanaInicioCliente;
import vistas.empleado.VentanaInicioEmpleado;
import vistas.gestor.VentanaInicioGestor;
import vistas.noRegistrado.VentanaRegistrar;

public class ControlRegistrarse implements ActionListener {

	private Tienda tienda;
	private VentanaRegistrar vista;

	public ControlRegistrarse(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaRegistrar();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Crear cuenta"))
			this.intentarRegistrarse();
	}
	
	private void intentarRegistrarse() {
		
		String nombre = vista.getNombreUsuario();
		String pass = new String(vista.getPassword());
		String conf = new String(vista.getConfirmation());

		try {
			Usuario usuario = tienda.registrarse(nombre, pass, conf);
			
			if (usuario instanceof Gestor) {
		        new VentanaInicioGestor(tienda);
		    } else if (usuario instanceof Empleado) {
		    	new VentanaInicioEmpleado(tienda);
		    } else if (usuario instanceof ClienteRegistrado) {
		    	new VentanaInicioCliente(tienda);
		    }
		} catch (CustomException ex) {
			new VentanaMensaje(ex.getMessage());
		}
	}
}
