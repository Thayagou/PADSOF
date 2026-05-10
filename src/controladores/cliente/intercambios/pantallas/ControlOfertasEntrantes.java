package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.cliente.intercambios.ControlPanelOferta;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.Intercambio;

public class ControlOfertasEntrantes extends ControlVerMisOfertas {
	
	public ControlOfertasEntrantes(Tienda tienda, ClienteRegistrado cliente) {
		super(tienda, cliente, "Ofertas de intercambio entrantes");
	}
	
	@Override
	public void crearPaneles() {
		Intercambio[] ofertas = cliente.getCartera().getIntercambiosPendientesRecibidos();
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
		return "Aquí se muestran las ofertas que usted ha recibido. Para aceptar/rechazar una oferta, haz clic sobre ella y continúa en la nueva ventana o usa los botones que se muestran a la derecha.";
	}

}
