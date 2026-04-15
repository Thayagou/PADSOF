package gui;

import javax.swing.*;

import sistema.Tienda;

import java.awt.*;

public class VentanaInicioSinRegistrar extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaInicioSinRegistrar(Tienda tienda) {

		setTitle("Tienda mega friki (just for onion smelling fat twatts...)");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JButton botonLogin = new JButton("Iniciar sesión");

		botonLogin.addActionListener(e -> {
			new VentanaLogin(tienda);
			dispose();
		});
		
		JButton buttonRegistrar = new JButton("Registrarse");
		
		buttonRegistrar.addActionListener(e -> {
			new VentanaRegistrar(tienda);
			dispose();
		});
		
		JButton buttonBuscar = new JButton("Buscar");
		
		buttonBuscar.addActionListener(e -> {
			new VentanaBusqueda(tienda);
			dispose();
		});
		
		

		setLayout(new GridBagLayout());
		add(botonLogin);
		add(buttonRegistrar);
		add(buttonBuscar);

		setVisible(true);
	}
}
