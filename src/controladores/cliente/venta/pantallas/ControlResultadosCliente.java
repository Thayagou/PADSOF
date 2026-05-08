package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlPanelProductoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import vistas.noRegistrado.*;
import vistas.common.app.TiendaFrame;

public class ControlResultadosCliente implements ControladorPantalla {
	
	@SuppressWarnings("unused")
	private Tienda tienda;
	private VentanaResultadosNoRegistrado vista;

	public ControlResultadosCliente(Tienda tienda, ClienteRegistrado cliente, Producto[] productos) {
		this.tienda = tienda;
		this.vista = new VentanaResultadosNoRegistrado();
		
		for(Producto p : productos) {
			new ControlPanelProductoCliente(tienda, cliente, p, vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Sin acciones en esta ventana */
	}

	@Override
	public String getExplicacion() {
		return "Aquí se muestran los resultados de una búsqueda. Para añadir un producto al carrito, haz clic sobre él y luego pincha en \"Añadir al carrito\"";
	}
}
