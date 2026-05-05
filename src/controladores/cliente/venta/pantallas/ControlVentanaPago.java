package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.cliente.general.pantallas.ControlInicioCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.cliente.venta.pantallas.VentanaPago;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;

public class ControlVentanaPago implements ActionListener, ControladorPantalla {
	
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
		if(e.getActionCommand().equals("Pagar")) {
			try{
				tienda.pagarCarritoDe(cliente, vista.getNumeroTarjeta());
				new VentanaMensaje("El carrito se ha pagado. Puedes ver tu código de pedido en las notificaciones.");
				SwingUtilities.invokeLater(() -> new ControlInicioCliente(tienda, cliente));
			} catch(Exception ex) {
				new VentanaMensaje(ex.getMessage());
			}
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
	
	@Override
	public boolean puedeVolver() {return false;}
}
