package controladores.noRegistrado;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraLateral;
import modelo.sistema.Tienda;

public class ControlBarraNoRegistrado implements ControlBarraLateral {

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
			new ControlLogin(tienda);
		});
	}

	private void showRegistrarse() {
		SwingUtilities.invokeLater(() -> {
			new ControlRegistrarse(tienda);
		});
	}

	private void showBuscar() {
		SwingUtilities.invokeLater(() -> {
			new ControlBuscar(tienda);
		});
	}
}
