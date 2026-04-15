package gui;

import javax.swing.*;

import modelo.exceptions.*;
import modelo.sistema.Tienda;
import modelo.usuario.*;

import java.awt.*;

public class VentanaRegistrar extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaRegistrar(Tienda tienda) {
		setTitle("Registrarse");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JTextField usuarioField = new JTextField(15);
		JPasswordField passField = new JPasswordField(15);
		JPasswordField confirmationField = new JPasswordField(15);
		JButton boton = new JButton("Crear cuenta");

		JPanel panel = new JPanel(new GridLayout(3, 2));
		panel.add(new JLabel("Usuario:"));
		panel.add(usuarioField);
		panel.add(new JLabel("Contraseña:"));
		panel.add(passField);
		panel.add(new JLabel("Confirmar contraseña:"));
		panel.add(confirmationField);
		panel.add(boton);

		boton.addActionListener(e -> {
			String nombre = usuarioField.getText();
			String pass = new String(passField.getPassword());
			String conf = new String(confirmationField.getPassword());

			try {
				Usuario usuario = tienda.registrarse(nombre, pass, conf);
				
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
