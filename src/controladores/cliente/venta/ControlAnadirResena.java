package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Resena;
import vistas.cliente.venta.VentanaAnadirResena;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;

public class ControlAnadirResena implements ActionListener, ControladorPantalla {
	
	Tienda tienda;
	ClienteRegistrado cliente;
	Producto producto;
	VentanaAnadirResena vista;

	public ControlAnadirResena(Tienda tienda, ClienteRegistrado cliente, Producto producto) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.producto = producto;
		
		this.vista = new VentanaAnadirResena();
		vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "enviar":
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

