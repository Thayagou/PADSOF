package gui;

import javax.swing.*;
import java.awt.*;

import sistema.*;
import usuario.*;
import exceptions.*;

public class VentanaLogin extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaLogin(Tienda tienda) {

		setTitle("Login");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JTextField usuarioField = new JTextField(15);
		JPasswordField passField = new JPasswordField(15);
		JButton boton = new JButton("Entrar");

		JPanel panel = new JPanel(new GridLayout(3, 2));
		panel.add(new JLabel("Usuario:"));
		panel.add(usuarioField);
		panel.add(new JLabel("Contraseña:"));
		panel.add(passField);
		panel.add(boton);

		boton.addActionListener(e -> {
			String nombre = usuarioField.getText();
			String pass = new String(passField.getPassword());

			try {
				Usuario usuario = tienda.iniciarSesion(nombre, pass);
				
				if (usuario instanceof Gestor) {
			        new VentanaInicioGestor(tienda);
			    } else if (usuario instanceof Empleado) {
			    	new VentanaInicioEmpleado(tienda);
			    } else if (usuario instanceof ClienteRegistrado) {
			    	new VentanaInicioCliente(tienda);
			    }
				dispose();
			} catch (CustomException ex) {
				new VentanaMensaje(ex.getMessage());
			}
		});

		add(panel);
		setVisible(true);
	}
}
