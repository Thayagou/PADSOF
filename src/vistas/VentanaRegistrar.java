package vistas;

import javax.swing.*;

import controladores.ControlRegistrarse;

import java.awt.*;

public class VentanaRegistrar extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField usuarioField;
	private JPasswordField passField;
	private JPasswordField confirmationField;
	private JButton botonRegistrar;

	public VentanaRegistrar() {
		JLabel title = new JLabel("Registrarse");

		usuarioField = new JTextField(15);
		passField = new JPasswordField(15);
		confirmationField = new JPasswordField(15);
		botonRegistrar = new JButton("Crear cuenta");

		JPanel panel = new JPanel(new GridLayout(3, 2));
		panel.add(title);
		panel.add(new JLabel("Usuario:"));
		panel.add(usuarioField);
		panel.add(new JLabel("Contraseña:"));
		panel.add(passField);
		panel.add(new JLabel("Confirmar contraseña:"));
		panel.add(confirmationField);
		panel.add(botonRegistrar);

		add(panel);
		setVisible(true);
	}

	// Asignar controlador a los botones
	public void setControlador(ControlRegistrarse c) {
		botonRegistrar.addActionListener(c);
	}

	public String getNombreUsuario() {
		return usuarioField.getText();
	}

	public char[] getPassword() {
		return passField.getPassword();
	}
	
	public char[] getConfirmation() {
		return confirmationField.getPassword();
	}
}
