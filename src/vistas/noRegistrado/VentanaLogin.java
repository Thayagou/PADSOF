package vistas.noRegistrado;

import javax.swing.*;

import controladores.ControlBarraNoRegistrado;
import controladores.ControlLogin;
import vistas.ButtonFactory;

import java.awt.*;

public class VentanaLogin extends FondoNoRegistrado {

	private static final long serialVersionUID = 1L;
	private JTextField usuarioField;
	private JPasswordField passField;
	private JButton botonEntrar;

	public VentanaLogin(ControlBarraNoRegistrado ctrlBarra) {
		super();

		JLabel title = new JLabel("Login");
		ButtonFactory factory = new ButtonFactory();

		usuarioField = new JTextField(15);
		passField = new JPasswordField(15);
		botonEntrar = factory.newButton("Entrar");

		JPanel contenido = new JPanel(new GridLayout(3, 2));
		contenido.add(title);
		contenido.add(new JLabel("Usuario:"));
		contenido.add(usuarioField);
		contenido.add(new JLabel("Contraseña:"));
		contenido.add(passField);
		contenido.add(botonEntrar);

		add(contenido, BorderLayout.CENTER);
		setVisible(true);
		
		initBarra(ctrlBarra);
		
	}
	
	//Asignar controlador a los botones
	public void setControlador(ControlLogin c) {
		botonEntrar.addActionListener(c);
	}
	
	public String getNombreUsuario() {
		return usuarioField.getText();
	}
	
	public char[] getPassword() {
		return passField.getPassword();
	}
}
