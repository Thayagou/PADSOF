package controladores.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.StockExterno;
import vistas.cliente.VentanaCarrito;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;

public class ControlManejoCarrito implements ActionListener {
	
	private Tienda tienda;
	private VentanaCarrito vista;
	private ClienteRegistrado cliente;

	public ControlManejoCarrito(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		double precio = cliente.getCarrito().calcularCarrito();
		
		this.vista = new VentanaCarrito(precio);
		
		for(StockExterno st : cliente.getCarrito().getItems()) {
			new ControlItemCarrito(tienda, cliente, st, vista);
		}
		
		vista.setControlador(this);
		
		TiendaFrame.getInstance().setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "pagar":
			SwingUtilities.invokeLater(() -> new ControlVentanaPago(tienda, cliente));
			break;
		case "cancelar":
			try{
				tienda.cancelarCarritoDe(cliente);
				
				SwingUtilities.invokeLater(() -> new ControlManejoCarrito(tienda, cliente));
				new VentanaMensaje("Su carrito ha sido cancelado");
			} catch(Exception ex) {
				new VentanaMensaje(ex.getMessage());
			}
			break;
		}
	}
}
