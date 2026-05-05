package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlPanelProductoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import vistas.noRegistrado.*;
import vistas.common.*;

public class ControlResultadosCliente implements ActionListener, ControladorPantalla {
	
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
}
