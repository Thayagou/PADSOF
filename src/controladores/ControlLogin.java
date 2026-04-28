package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
	private TiendaFrame frame;

	public ControlLogin(Tienda tienda, TiendaFrame frame) {
		this.tienda = tienda;
		this.vista = new VentanaLogin();
		this.vista.setControlador(this);

		this.frame = frame;
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		this.frame.add(vista);
		this.frame.setVisible(true);
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
		this.frame.remove(vista);
		SwingUtilities.invokeLater(()->
			new ControlInicioGestor(tienda)
		);
		
	}
}
