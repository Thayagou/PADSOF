package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import modelo.sistema.*;
import vistas.*;

public class ControlInicioSinRegistrar implements ActionListener {

	private Tienda tienda;
	private VentanaInicioSinRegistrar vista;
	private TiendaFrame frame;

	public ControlInicioSinRegistrar(Tienda tienda, TiendaFrame frame) {
		this.tienda = tienda;
		this.vista = new VentanaInicioSinRegistrar(frame);
		this.vista.setControlador(this);

		this.frame = frame;
		this.frame.add(vista);
		this.frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Iniciar sesión"))
			this.showLogin();
		else if (e.getActionCommand().equals("Registrarse"))
			this.showRegistrarse();
		else if (e.getActionCommand().equals("Buscar"))
			this.showBuscar();
	}

	private void showLogin() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(() -> {
		    new ControlLogin(tienda, frame);
		});
	}

	private void showRegistrarse() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(() -> {
		    new ControlRegistrarse(tienda, frame);
		});
	}

	private void showBuscar() {
		this.frame.remove(vista);
		SwingUtilities.invokeLater(() -> {
		    new ControlBuscar(tienda, frame);
		});
	}
}
