package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.cliente.intercambios.ControlPanelOferta;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.Intercambio;

public class ControlIntercambiosPendientes extends ControlVerMisOfertas {
	
	public ControlIntercambiosPendientes(Tienda tienda, ClienteRegistrado cliente) {
		super(tienda, cliente, "Intercambios pendientes de realizar");
	}
	
	@Override
	public void crearPaneles() {
		Intercambio[] ofertas = cliente.getCartera().getIntercambiosAceptados();
		for(int i = ofertas.length - 1; i >= 0 ; i--) {
			new ControlPanelOferta(tienda, cliente, ofertas[i], vista, this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "Aquí se muestran los intercambios que han sido aceptados entre usted y otros usuarios que aún no han sido verificados por un empleado de la tienda.";
	}

}
