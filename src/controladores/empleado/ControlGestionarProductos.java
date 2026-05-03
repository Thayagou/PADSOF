package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import modelo.usuario.Usuario;
import modelo.venta.productos.Producto;
import vistas.common.TiendaFrame;
import vistas.empleado.VentanaGestionarProductos;

public class ControlGestionarProductos implements ActionListener{
	private Tienda tienda;
	private Usuario usuario;
	private TiendaFrame frame;
	private VentanaGestionarProductos vista;
	
	public ControlGestionarProductos(Tienda tienda, Usuario usuario) {
		this.tienda = tienda;
		this.usuario = usuario;
		this.frame = TiendaFrame.getInstance();
		this.vista = new VentanaGestionarProductos();
		
		Producto[] productos = tienda.getAlmacen().getProductosCoincidentes("");
		
		for (Producto p: productos) {
			new ControlPanelProductoGestionar(tienda, p, vista);
		}
		
		vista.revalidate();
		vista.repaint();
		
		frame.setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
