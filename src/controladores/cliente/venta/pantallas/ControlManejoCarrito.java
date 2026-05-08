package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlItemCarrito;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.StockExterno;
import vistas.cliente.venta.pantallas.VentanaCarrito;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlManejoCarrito implements ControladorPantalla {
	
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

	@Override
	public String getExplicacion() {
		return "En esta ventana se muestran los productos de tu carrito. Puedes quitarlos, finalizar la compra o cancelarla.";
	}
}
