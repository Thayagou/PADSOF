package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;

public class ControlBarraNoRegistrado implements ActionListener {

	private Tienda tienda;

	public ControlBarraNoRegistrado(Tienda tienda) {
		this.tienda = tienda;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Iniciar Sesión" -> showLogin();
		case "Registrarse" -> showRegistrarse();
		case "Buscar productos" -> showBuscar();
		}
	}

	private void showLogin() {
		SwingUtilities.invokeLater(() -> {
			new ControlLogin(tienda, this);
		});
	}

	private void showRegistrarse() {
		SwingUtilities.invokeLater(() -> {
			new ControlRegistrarse(tienda, this);
		});
	}

	private void showBuscar() {
		SwingUtilities.invokeLater(() -> {
			new ControlBuscar(tienda, this);
		});
	}
}
