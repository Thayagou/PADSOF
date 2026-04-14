package gui;

import javax.swing.*;

import sistema.Tienda;

//import java.awt.*;

public class VentanaInicioGestor extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaInicioGestor(Tienda tienda) {
		setTitle("GESTOR");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}
