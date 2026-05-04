package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Resena;
import vistas.cliente.venta.VentanaAnadirResena;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;

public class ControlAnadirResena implements ActionListener {
	
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
		
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "enviar":
			try{
				producto.anadirResena(new Resena(vista.getValoracion(), vista.getComentario(), cliente));
				new VentanaMensaje("Su reseña se ha añadido");
			} catch(Exception ex) {
				new VentanaMensaje(ex.getMessage());
			}
		}
		
	}
}

