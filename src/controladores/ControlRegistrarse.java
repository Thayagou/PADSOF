package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import modelo.exceptions.CustomException;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import vistas.*;

public class ControlRegistrarse implements ActionListener {

	private Tienda tienda;
	private VentanaRegistrar vista;
	private TiendaFrame frame;

	public ControlRegistrarse(Tienda tienda, TiendaFrame frame) {
		this.tienda = tienda;
		this.vista = new VentanaRegistrar();
		this.vista.setControlador(this);

		this.frame = frame;
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		this.frame.add(vista);
		this.frame.setVisible(true);
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
