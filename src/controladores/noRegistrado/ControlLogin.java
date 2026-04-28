package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.gestor.ControlInicioGestor;
import modelo.exceptions.CustomException;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import vistas.*;
import vistas.cliente.VentanaInicioCliente;
import vistas.empleado.VentanaInicioEmpleado;
import vistas.noRegistrado.VentanaLogin;

public class ControlLogin implements ActionListener {

	private Tienda tienda;
	private VentanaLogin vista;

	public ControlLogin(Tienda tienda, ControlBarraNoRegistrado ctrlBarra) {
		this.tienda = tienda;
		this.vista = new VentanaLogin(ctrlBarra);
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Entrar"))
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
		    	new VentanaInicioEmpleado(tienda);
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
}
