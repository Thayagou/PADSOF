package vistas;

import javax.swing.*;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

public class VentanaInicioSinRegistrar extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton botonLogin;
	private JButton botonRegistrar;
	private JButton botonBuscar;

	public VentanaInicioSinRegistrar(TiendaFrame frame) {
		
		//Poner el layout
		this.setLayout(new GridLayout(0, 2));

		//Crear componentes
		JLabel title = new JLabel("Tienda mega friki (just for onion smelling fat twatts...)");
		title.setFont(frame.getTitleFont());
		botonLogin = new JButton("Iniciar sesión");
		botonLogin.setFont(frame.getTitle3Font());
		botonRegistrar = new JButton("Registrarse");
		botonRegistrar.setFont(frame.getTitle3Font());
		botonBuscar = new JButton("Buscar");
		botonBuscar.setFont(frame.getTitle3Font());
		
		//Añadir al panel
		this.add(title);
		this.add(botonLogin);
		this.add(botonRegistrar);
		this.add(botonBuscar);
		
	}
	
	//Asignar controlador a los botones
	public void setControlador(ActionListener c) {
		botonLogin.addActionListener(c);
		botonRegistrar.addActionListener(c);
		botonBuscar.addActionListener(c);
	}
}
