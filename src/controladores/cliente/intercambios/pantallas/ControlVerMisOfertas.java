package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.intercambios.ControlPanelOferta;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.Intercambio;
import vistas.cliente.intercambios.pantallas.VentanaVerMisOfertas;
import vistas.common.app.TiendaFrame;

public class ControlVerMisOfertas implements ControladorPantalla {
	
	Tienda tienda;
	ClienteRegistrado cliente;
	VentanaVerMisOfertas vista;
	
	public ControlVerMisOfertas(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		vista = new VentanaVerMisOfertas();
		vista.setControlador(this);
		
		for(Intercambio i : cliente.getCartera().getIntercambiosPendientes()) {
			new ControlPanelOferta(tienda, cliente, i, vista, this);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	public void refrescar() {
		vista = new VentanaVerMisOfertas();
		vista.setControlador(this);
		
		for(Intercambio i : cliente.getCartera().getIntercambiosPendientes()) {
			new ControlPanelOferta(tienda, cliente, i, vista, this);
		}
		
		TiendaFrame.getInstance().recargarPantallaActual(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Sin acciones para esta ventana */
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
