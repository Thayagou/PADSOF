package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import modelo.exceptions.CustomException;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import vistas.*;

public class ControlLogin implements ActionListener {

	private Tienda tienda;
	private VentanaLogin vista;
	private JFrame frame;

	public ControlLogin(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaLogin();
		this.vista.setControlador(this);

		this.frame = new JFrame("Tienda");
		this.frame.setSize(400, 300);
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
