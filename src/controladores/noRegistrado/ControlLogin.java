package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.empleado.ControlInicioEmpleado;
import controladores.gestor.ControlInicioGestor;
import modelo.exceptions.CustomException;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import vistas.*;
import vistas.cliente.VentanaInicioCliente;
import vistas.noRegistrado.VentanaLogin;

public class ControlLogin implements ActionListener {

	private Tienda tienda;
	private VentanaLogin vista;

	public ControlLogin(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaLogin();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Log In"))
			this.intentarLogin();
	}
	
	private void intentarLogin() {
		String nombre = vista.getNombreUsuario();
		String pass = new String(vista.getPassword());

		try {
			Usuario usuario = tienda.iniciarSesion(nombre, pass);
			
			if (usuario instanceof Gestor) {
				this.inicioGestor();
		    } else if (usuario instanceof Empleado) {
		    	this.inicioEmpleado();
		    } else if (usuario instanceof ClienteRegistrado) {
		    	new VentanaInicioCliente(tienda);
		    }
		} catch (CustomException ex) {
			new VentanaMensaje(ex.getMessage());
		}
	}

	private void inicioGestor() {
		SwingUtilities.invokeLater(()->
			new ControlInicioGestor(tienda)
		);
	}
	
	private void inicioEmpleado() {
		SwingUtilities.invokeLater(()->
			new ControlInicioEmpleado(tienda)
		);
	}
}
