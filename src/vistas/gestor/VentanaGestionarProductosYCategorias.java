package vistas.gestor;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import modelo.sistema.Tienda;

public class VentanaGestionarProductosYCategorias extends JPanel{
	
	public VentanaGestionarProductosYCategorias(Tienda tienda) {
		setOpaque(false);
		setLayout(new BorderLayout());
	}
}
