package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.venta.pantallas.VentanaPago;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlVentanaPagoValoracion implements ControladorPantalla {
	
	Tienda tienda;
	ClienteRegistrado cliente;
	ArticuloSegundaMano articulo;
	VentanaPago vista;

	public ControlVentanaPagoValoracion(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.articulo = articulo;
		
		this.vista = new VentanaPago();
		this.vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Pagar":
			try{
				tienda.solicitarValoracion(cliente, articulo, vista.getNumeroTarjeta());
				TiendaFrame.getInstance().volverAtras();
				new VentanaMensaje("Su valoración ha sido solicitada con éxito");
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage());
			}
			break;
		}
	}
	
	@Override
	public boolean puedeVolver() {return false;}

	@Override
	public String getExplicacion() {
		return "Introduce tus datos para realizar el pago y que el pequeño Timmy pueda comer hoy.";
	}

}
