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

		setLayout(new GridBagLayout());
		add(botonLogin);

		setVisible(true);
	}
}
