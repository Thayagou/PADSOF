package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.cliente.venta.pantallas.VentanaPago;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlVentanaPago implements ControladorPantalla {
	
	private Tienda tienda;
	private VentanaPago vista;
	private ClienteRegistrado cliente;
	
	public ControlVentanaPago(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		vista = new VentanaPago();
		vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(VentanaPago.PAY_ACTION)) {
			try{
				if(tienda.pagarCarritoDe(cliente, vista.getNumeroTarjeta()) == true) {
					new VentanaMensaje("El carrito se ha pagado. Puedes ver tu código de pedido en las notificaciones.");
					SwingUtilities.invokeLater(() -> new ControlVerCompras(tienda, cliente));
				} else {
					new VentanaMensaje("El número de tarjeta introducido es erróneo. Introduce un número de 16 dígitos.", VentanaMensaje.ERROR);
				}
			} catch(Exception ex) {
				new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
			}
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
	
	@Override
	public boolean puedeVolver() {return false;}

	@Override
	public String getExplicacion() {
		return "Introduce los datos para realizar el pago.";
	}
}
