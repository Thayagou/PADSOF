package controladores.cliente.venta;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import vistas.noRegistrado.*;
import vistas.common.*;

public class ControlResultadosCliente {
	
	private Tienda tienda;
	private VentanaResultadosNoRegistrado vista;

	public ControlResultadosCliente(Tienda tienda, ClienteRegistrado cliente, Producto[] productos) {
		this.tienda = tienda;
		this.vista = new VentanaResultadosNoRegistrado();
		
		for(Producto p : productos) {
			new ControlPanelProductoCliente(tienda, cliente, p, vista);
		}
		
		TiendaFrame.getInstance().setVistaActual(vista);
	}
}
