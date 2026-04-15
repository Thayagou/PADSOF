package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import sistema.*;

public class ControlInicioSinRegistrar implements ActionListener {

	private Tienda tienda;
	private VentanaInicioSinRegistrar vista;
	private JFrame frame;

	public ControlInicioSinRegistrar(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaInicioSinRegistrar();
		this.vista.setControlador(this);

		this.frame = new JFrame("Tienda");
		this.frame.setSize(400, 300);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		this.frame.add(vista);
		this.frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Iniciar sesión"))
			this.showLogin();
		else if (e.getActionCommand().equals("Registrarse"))
			this.showRegistrarse();
		else if (e.getActionCommand().equals("Buscar"))
			this.showBuscar();
	}

	private void showLogin() {
		VentanaLogin login = new VentanaLogin(tienda);
		login.setVisible(true);
		frame.setVisible(false);
	}

	private void showRegistrarse() {
		VentanaRegistrar registro = new VentanaRegistrar(tienda);
		registro.setVisible(true);
		frame.setVisible(false);
	}

	private void showBuscar() {
		VentanaBusqueda registro = new VentanaBusqueda(tienda);
		registro.setVisible(true);
		frame.setVisible(false);
	}
}
