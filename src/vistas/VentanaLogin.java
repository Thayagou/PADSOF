package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField usuarioField;
	private JPasswordField passField;
	private JButton botonEntrar;

	public VentanaLogin() {

		JLabel title = new JLabel("Login");

		usuarioField = new JTextField(15);
		passField = new JPasswordField(15);
		botonEntrar = new JButton("Entrar");

		JPanel panel = new JPanel(new GridLayout(3, 2));
		panel.add(title);
		panel.add(new JLabel("Usuario:"));
		panel.add(usuarioField);
		panel.add(new JLabel("Contraseña:"));
		panel.add(passField);
		panel.add(botonEntrar);

		add(panel);
		setVisible(true);
	}
	
	//Asignar controlador a los botones
	public void setControlador(ActionListener c) {
		usuarioField.addActionListener(c);
		passField.addActionListener(c);
		botonEntrar.addActionListener(c);
	}
	
	public String getNombreUsuario() {
		return usuarioField.getText();
	}
	
	public char[] getPassword() {
		return passField.getPassword();
	}
}
