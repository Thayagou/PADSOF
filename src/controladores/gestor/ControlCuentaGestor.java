package controladores.gestor;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.app.TiendaFrame;
import vistas.gestor.VentanaCuentaGestor;

public class ControlCuentaGestor implements ControladorPantalla {
	private VentanaCuentaGestor vista;

	public ControlCuentaGestor(Tienda tienda, Gestor gestor) {
		this.vista = new VentanaCuentaGestor();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
