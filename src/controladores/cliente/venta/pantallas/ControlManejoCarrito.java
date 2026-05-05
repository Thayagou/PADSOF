package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.cliente.intercambios.pantallas.ControlVentanaPago;
import controladores.cliente.venta.ControlItemCarrito;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.StockExterno;
import vistas.cliente.venta.pantallas.VentanaCarrito;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;

public class ControlManejoCarrito implements ActionListener, ControladorPantalla {
	
	private Tienda tienda;
	private VentanaCarrito vista;
	private ClienteRegistrado cliente;

	public ControlManejoCarrito(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		double precio = cliente.getCarrito().calcularCarrito();
		
		this.vista = new VentanaCarrito(precio);
		
		for(StockExterno st : cliente.getCarrito().getItems()) {
			new ControlItemCarrito(tienda, cliente, st, vista, this);
		}
		
		vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	public void recargarPantalla() {
		double precio = cliente.getCarrito().calcularCarrito();
		
		this.vista = new VentanaCarrito(precio);
		
		for(StockExterno st : cliente.getCarrito().getItems()) {
			new ControlItemCarrito(tienda, cliente, st, vista, this);
		}
		
		vista.setControlador(this);
		
		TiendaFrame.getInstance().recargarPantallaActual(this);
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
				recargarPantalla();
				new VentanaMensaje("Su carrito ha sido cancelado");
			} catch(Exception ex) {
				new VentanaMensaje(ex.getMessage());
			}
			break;
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}
