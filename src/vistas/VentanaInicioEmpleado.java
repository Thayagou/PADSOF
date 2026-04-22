package vistas;

import javax.swing.*;

import modelo.sistema.Tienda;

public class VentanaInicioEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaInicioEmpleado(Tienda tienda) {
		setTitle("EMPLEADO");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}
