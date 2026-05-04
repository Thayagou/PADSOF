package vistas.gestor.configurarSistema;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import modelo.sistema.Tienda;

public class VentanaConfigurarSistema extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public VentanaConfigurarSistema(Tienda tienda) {
		setOpaque(false);
		setLayout(new BorderLayout());
	}

}
