package vistas;

import javax.swing.*;

import modelo.sistema.Tienda;

public class VentanaInicioCliente extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaInicioCliente(Tienda tienda) {
		setTitle("CLIENTE");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}
