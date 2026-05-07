package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Resena;
import vistas.cliente.venta.pantallas.VentanaAnadirResena;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlAnadirResena implements ActionListener, ControladorPantalla {
	
	Tienda tienda;
	ClienteRegistrado cliente;
	Producto producto;
	VentanaAnadirResena vista;
	
	private static final String actionName = "enviar";

	public ControlAnadirResena(Tienda tienda, ClienteRegistrado cliente, Producto producto) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.producto = producto;
		
		this.vista = new VentanaAnadirResena(actionName);
		vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case actionName:
			try{
				producto.anadirResena(new Resena(vista.getValoracion(), vista.getComentario(), cliente));
				TiendaFrame.getInstance().volverAtras();
			} catch(Exception ex) {
				new VentanaMensaje(ex.getMessage());
			}
		}
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}

