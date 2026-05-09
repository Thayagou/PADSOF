package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.venta.productos.Producto;
import vistas.common.app.TiendaFrame;
import vistas.noRegistrado.VentanaResultadosNoRegistrado;

public class ControlResultadosNoRegistrado implements ActionListener, ControladorPantalla {

	private VentanaResultadosNoRegistrado vista;

	public ControlResultadosNoRegistrado(Tienda tienda, Producto[] productos) {
		this.vista = new VentanaResultadosNoRegistrado();
		
		for(Producto p : productos) {
			new ControlPanelProductoNoRegistrado(tienda, p, vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Sin acciones para esta ventana */
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana puedes ver los resultados de una búsqueda. Para añadir productos al carrito, inicia sesión o registrate como cliente.";
	}
}
