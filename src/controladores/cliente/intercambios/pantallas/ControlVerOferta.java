package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.intercambios.ControlPanelArticuloEnOferta;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import modelo.wallapop.Intercambio;
import vistas.cliente.intercambios.pantallas.VentanaOfertaIntercambio;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlVerOferta implements ControladorPantalla {
	
	Tienda tienda;
	ClienteRegistrado cliente;
	Intercambio intercambio;
	VentanaOfertaIntercambio vista;
	
	private static final String BTN_ACCEPT = "Aceptar";
	private static final String BTN_REJECT = "Rechazar";
	private static final String BTN_CANCEL = "Cancelar";
	
	public ControlVerOferta(Tienda tienda, ClienteRegistrado cliente, Intercambio intercambio) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.intercambio = intercambio;
		
		if(!cliente.equals(intercambio.getEmisor().getDueno()))
			this.vista = new VentanaOfertaIntercambio(BTN_REJECT, BTN_ACCEPT);
		else 
			this.vista = new VentanaOfertaIntercambio(BTN_CANCEL);
		
		vista.setControlador(this);
		
		anadirArticulos(intercambio.getOfrecidos());
		anadirArticulos(intercambio.getSolicitados());
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	private void anadirArticulos(ArticuloSegundaMano[] articulos) {
		for(ArticuloSegundaMano a : articulos)
			new ControlPanelArticuloEnOferta(tienda, this.cliente, a, vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case BTN_ACCEPT:
			try {
				cliente.getCartera().aceptarIntercambio(intercambio);
				new VentanaMensaje("Has aceptado la oferta");
				TiendaFrame.getInstance().volverAtras();
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage(), 2);
			}
			break;
		case BTN_REJECT:
			try {
				cliente.getCartera().rechazarIntercambio(intercambio);
				new VentanaMensaje("Has rechazado la oferta");
				TiendaFrame.getInstance().volverAtras();
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage(), 2);
			}
			break;
		case BTN_CANCEL:
			try{
				intercambio.cancelarIntercambio();
			} catch(Exception ex) {
				new VentanaMensaje(ex.getMessage(), 1);
			}
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
